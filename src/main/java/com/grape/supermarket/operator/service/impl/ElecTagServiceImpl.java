package com.grape.supermarket.operator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.common.util.ExcelUtil;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.dao.CommodityDao;
import com.grape.supermarket.dao.ElecTagDao;
import com.grape.supermarket.entity.db.CommodityEntity;
import com.grape.supermarket.entity.db.ElecTagEntity;
import com.grape.supermarket.entity.db.OperatorLogEntity;
import com.grape.supermarket.entity.page.ElecTagPageEntity;
import com.grape.supermarket.operator.OperatorSession;
import com.grape.supermarket.operator.service.ElecTagDefinition;
import com.grape.supermarket.operator.service.ElecTagDefinitionFactory;
import com.grape.supermarket.operator.service.ElecTagService;
import com.grape.supermarket.operator.service.OperatorLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2017/12/2.
 */
@Service("operatorElectagService")
public class ElecTagServiceImpl implements ElecTagService {
    private Logger logger = Logger.getLogger(getClass());
    @Value("${elecTag.version}")
    private int elecTagVersion;
    @Autowired
    private ElecTagDao elecTagDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private OperatorLogService logService;

    @Override
    public List<ElecTagEntity> createElecTag(Integer commId, Map<String, String> userData, int num) {
        CommodityEntity ce = commodityDao.selectByPrimaryKey(commId);
        if (ce == null || !ce.getBarcode().matches("^[0-9A-Fa-f]*$")) {
            logger.error("商品[" + commId + "]条码格式错误,detail->" + ce);
            return new ArrayList<>();
        }
        try {
            ElecTagDefinition elecTagDefinition = ElecTagDefinitionFactory.getInstance().getDefinition(elecTagVersion);
            if(elecTagDefinition == null){
                throw new RuntimeException("未定义的标签格式");
            }
            //查找下一个序列号和最大序列号
            int seq = 0;
            String sqlWhere = elecTagDefinition.sqlWhere(ce.getBarcode(), userData);
            ElecTagEntity nowSeq = elecTagDao.selectMaxSeq(commId, sqlWhere);
            int maxSeq = elecTagDefinition.maxSeq();
            if (nowSeq != null) {
                seq = elecTagDefinition.analysisSeq(nowSeq.getData()) + 1;
                if (seq > maxSeq) {
                    seq = 0;
                }
            }

            List<ElecTagEntity> tag = new ArrayList<>(num);
            int mark = seq;
            boolean b = false;
            while (true) {
                if (b && seq == mark) {
                    logger.warn("商品id[" + commId + "]达到最大序列值,detail->"+ce);
                    break;
                }
                b = true;

                String elec = elecTagDefinition.createElec(ce.getBarcode(), userData, seq);
                if (elec == null) {
                    throw new RuntimeException("无法创建电子标签,detail->"+ce);
                }
                ElecTagEntity elecTagEntity = elecTagDao.selectByData(elec);
                if (elecTagEntity == null) {
                    ElecTagEntity temp = new ElecTagEntity();
                    temp.setData(elec);
                    temp.setVersion(((byte) elecTagVersion));
                    temp.setCommId(commId);
                    tag.add(temp);
                }

                if (tag.size() == num) {
                    break;
                }
                seq++;
                if (seq > maxSeq) {
                    seq = 0;
                }
            }
            return tag;
        } catch (Exception e) {
            logger.error("创建电子标签失败,detail->"+ce, e);
        }

        return new ArrayList<>();
    }

    @Override
    public Integer importElecTag(List<ElecTagPageEntity> elecTag) {
        OperatorSession session = OperatorSessionHelper.getSessionOrThrow();

        List<ElecTagPageEntity> success = new ArrayList<>(elecTag.size());
        for (ElecTagPageEntity tagEntity : elecTag) {
            if (tagEntity != null && tagEntity.getCommId() != null
                    && tagEntity.getData() != null && tagEntity.getVersion() != null) {
                try {
                    ElecTagEntity temp = elecTagDao.selectByData(tagEntity.getData().toUpperCase());
                    if (temp == null) {//检查是否存在条码
                        tagEntity.setCreateTime(new Date());
                        tagEntity.setData(tagEntity.getData().toUpperCase());
                        tagEntity.setState((byte) 0);
                        elecTagDao.insertSelective(tagEntity);
                        success.add(tagEntity);//加入成功标记
                    }
                } catch (Exception e) {
                    logger.warn("导入电子标签发生异常", e);
                }
            }
        }
        if (!success.isEmpty()) {
            for (ElecTagPageEntity elecTagPageEntity : success) {
                elecTagPageEntity.setState(null);
                elecTagPageEntity.setCreateTime(null);
                elecTagPageEntity.setVersion(null);
            }
            //写入日志
            OperatorLogEntity operatorLog = logService.createOperatorLog(session.getOperatorInfo().getOperId(),
                    OperatorSessionHelper.getClientIp(), OperatorLogService.ELEC_TAG_IMPORT);
            operatorLog.setData(JsonUtils.toJsonNonNull(success));
            return logService.addLog(operatorLog);
        }
        return null;
    }

    @Override
    public void downLoad(Integer downloadHandle, HttpServletResponse response) {
        OperatorLogEntity log = logService.selectByPrimaryKey(downloadHandle);
        if (log == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String logData = log.getData();
        List<ElecTagPageEntity> tagData = JsonUtils.fromJson(logData, new TypeReference<List<ElecTagPageEntity>>() {
        });
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("content-disposition", "attachment; filename=epc.xls");
        OutputStream out =null;
        try {
			out = new BufferedOutputStream(response.getOutputStream());
			List<String> header = new ArrayList<String>();
	        header.add("序号");
	        header.add("商品名称");
	        header.add("EPC");
	        List<String[]> data = new ArrayList<>(tagData.size());
	        for(int i = 0;i<tagData.size();i++){
	        	ElecTagPageEntity tag = tagData.get(i);
	        	String[] s = {""+(i+1),tag.getCommName(),tag.getData().toUpperCase()};
	        	data.add(s);
	        }
	        ExcelUtil.createExcel(header,data,out);
		} catch (IOException e) {
			logger.info("下载epc数据文件异常", e);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
