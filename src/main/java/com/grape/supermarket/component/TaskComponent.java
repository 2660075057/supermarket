package com.grape.supermarket.component;

import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.DepotDao;
import com.grape.supermarket.dao.ElecTagDao;
import com.grape.supermarket.dao.OperatorShopDao;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.DepotEntity;
import com.grape.supermarket.entity.db.OperatorShopKey;
import com.grape.supermarket.event.DepotMessageEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LXP on 2017/10/25.
 */
@Component("taskComponent")
public class TaskComponent {
    private Logger logger = Logger.getLogger(getClass());
    @Value("${elecTag.removeDay}")
    private int elecRemoveDay;
    @Autowired
    private DepotDao depotDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private OperatorShopDao operatorShopDao;
    @Autowired
    private ApplicationEventPublisher publish;
    @Autowired
    private ElecTagDao elecTagDao;

    private Map<Integer, Integer[]> alermMap = new ConcurrentHashMap<>();

    public void checkDepot() {
        List<DepotEntity> depotEntities = depotDao.selectAlarmDepot();
        if(depotEntities.isEmpty()){
            alermMap.clear();
        }
        String id = ""+System.currentTimeMillis();
        logger.debug(id+" 查询到报警商品数量->"+depotEntities.size());
        Iterator<DepotEntity> iterator = depotEntities.iterator();
        while (iterator.hasNext()) {
            DepotEntity de = iterator.next();
            Integer[] integer = alermMap.get(de.getDepotId());
            if (integer != null
                    && integer[0].equals(de.getAmount())
                    && integer[1].equals(de.getThreshold())) {
                iterator.remove();
            } else {
                Integer[] record = new Integer[]{de.getAmount(), de.getThreshold()};
                alermMap.put(de.getDepotId(), record);
            }
        }
        logger.debug(id+" 过滤后的报警商品数量->"+depotEntities.size());
        Map<Integer,List<DepotMessageEvent.DepotEventData>> temp = new HashMap<>();
        for (DepotEntity depotEntity : depotEntities) {
            List<DepotMessageEvent.DepotEventData> depotEventDatas = temp.get(depotEntity.getShopId());
            if(depotEventDatas == null){
                depotEventDatas = new ArrayList<>(6);
                temp.put(depotEntity.getShopId(),depotEventDatas);
            }
            DepotMessageEvent.DepotEventData ded = new DepotMessageEvent.DepotEventData();
            ded.setAmount(depotEntity.getAmount());
            ded.setThreshold(depotEntity.getThreshold());
            //查询商品数据
            CommodityEntity commodityEntity = commodityDao.selectByPrimaryKey(depotEntity.getCommId());
            if (commodityEntity != null) {
                ded.setBarcode(commodityEntity.getBarcode());
                ded.setCommName(commodityEntity.getCommName());
            }
            depotEventDatas.add(ded);

            logger.info(id+" 发现报警库存商品,barcode->"+ded.getBarcode()+" name->"+ded.getCommName()
                    +" shopId->"+depotEntity.getShopId()
                    +" amount->"+ded.getAmount()
                    +" threshold->"+ded.getThreshold());
        }

        for (Map.Entry<Integer, List<DepotMessageEvent.DepotEventData>> integerListEntry : temp.entrySet()) {
            DepotMessageEvent dme = new DepotMessageEvent(integerListEntry.getValue());
            dme.setShopId(integerListEntry.getKey());
            List<OperatorShopKey> temp2 = operatorShopDao.getOperatorIdList(integerListEntry.getKey());
            dme.setNoticeOperatorId(toOperatorId(temp2,0));
            publish.publishEvent(dme);
        }
    }

    public void checkElec(){
        int timeout = elecRemoveDay>1?elecRemoveDay:1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -timeout);
        int row = elecTagDao.deleteTimeout(calendar.getTime());
        logger.info("删除过期电子标签数["+row+"]");
    }

    private List<Integer> toOperatorId(List<OperatorShopKey> osk, Integer... addId) {
        List<Integer> result = new ArrayList<>(osk.size() + (addId != null ? addId.length : 0));
        for (OperatorShopKey operatorShopKey : osk) {
            result.add(operatorShopKey.getOperId());
        }
        if(addId != null){
            result.addAll(Arrays.asList(addId));
        }
        return result;
    }
}
