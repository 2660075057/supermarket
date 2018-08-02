package com.grape.supermarket.wechat.service.impl;

import com.grape.supermarket.dao.CustomerDao;
import com.grape.supermarket.dao.OperatorDao;
import com.grape.supermarket.dao.ShopDao;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.ShopEntity;
import com.grape.supermarket.entity.page.OrderDetailPageEntity;
import com.grape.supermarket.event.DepotMessageEvent;
import com.grape.supermarket.event.FireMessageEvent;
import com.grape.supermarket.event.PaySuccessEvent;
import com.grape.supermarket.operator.service.OrderDetailService;
import com.grape.supermarket.wechat.entity.TemplateMessageEntity;
import com.grape.supermarket.wechat.service.TemplateMessageHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LXP on 2018/4/24.
 */
public class PtTemplateMessageHandleService implements TemplateMessageHandleService {

    @Value("${depotMessageId}")
    private String deportMessageId;
    @Value("${fireMessageId}")
    private String fireMessageId;
    @Value("${paySuccessMessageId}")
    private String paySuccessMessageId;
    @Value("${domain}")
    private String domain;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OperatorDao operatorDao;
    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public List<TemplateMessageEntity> depotMessageMessage(DepotMessageEvent event) {
        List<TemplateMessageEntity> msg = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        ShopEntity shop = shopDao.selectByPrimaryKey(event.getShopId());
        List<String> openId = getOpenId(event.getNoticeOperatorId());

        for (String s : openId) {
            List<DepotMessageEvent.DepotEventData> deds = event.getSource();
            for (DepotMessageEvent.DepotEventData ded : deds) {
                Map<String, Object> messageData = new HashMap<>();
                messageData.put("first", getTextData(shop.getShopName() + "库存不足"));
                messageData.put("keyword1", getTextData(ded.getCommName() + "(" + ded.getBarcode() + ")"));
                messageData.put("keyword2", getTextData(ded.getAmount() + ""));
                messageData.put("keyword3", getTextData(time));
                messageData.put("remark", getTextData("请及时处理"));

                TemplateMessageEntity templateMessage = new TemplateMessageEntity();
                templateMessage.setData(messageData);
                templateMessage.setTemplateId(deportMessageId);
                templateMessage.setOpenId(s);
                msg.add(templateMessage);
            }
        }
        return msg;
    }

    @Override
    public List<TemplateMessageEntity> fireMessageMessage(FireMessageEvent event) {
        List<TemplateMessageEntity> msg = new ArrayList<>();
        List<String> openId = getOpenId(event.getNoticeOperatorId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(event.getTime());
        ShopEntity shop = shopDao.selectByPrimaryKey(event.getShopId());

        for (String s : openId) {
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("first", getTextData(shop.getShopName() + "触发了火警信号"));
            messageData.put("keyword1", getTextData(timeStr));
            messageData.put("keyword2", getTextData(shop.getAddress()));
            messageData.put("remark", getTextData("请及时确认处理"));

            TemplateMessageEntity templateMessage = new TemplateMessageEntity();
            templateMessage.setTemplateId(fireMessageId);
            templateMessage.setData(messageData);
            templateMessage.setOpenId(s);

            msg.add(templateMessage);
        }

        return msg;
    }

    @Override
    public TemplateMessageEntity paySuccessMessage(PaySuccessEvent event) {
        PaySuccessEvent.PayData payData = (PaySuccessEvent.PayData) event.getSource();
        if (payData.getCoustId() != null) {
            CustomerEntity customer = customerDao.selectByPrimaryKey(payData.getCoustId());
            if (customer != null && customer.getOpenid() != null) {
                List<OrderDetailPageEntity> orderDetail = orderDetailService.selectOrderDetailByOrderId(payData.getPaymentId());
                if (orderDetail != null && !orderDetail.isEmpty()) {
                    int p = payData.getPayment() - payData.getPreferential();
                    String commodityContent = "";
                    int total = 0;
                    for (OrderDetailPageEntity orderDetailPageEntity : orderDetail) {
                        total += orderDetailPageEntity.getCommCount();
                        commodityContent += orderDetailPageEntity.getCommName() + ',';
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Map<String, Object> messageData = new HashMap<>();
                    messageData.put("first", getTextData("您好，您的订单已成功支付"));
                    messageData.put("keyword1", getTextData(payData.getPaymentId()));
                    messageData.put("keyword2", getTextData(commodityContent.substring(0, commodityContent.length() - 1)));
                    messageData.put("keyword3", getTextData(total + ""));
                    messageData.put("keyword4", getTextData("￥" + (p / 100.0)));
                    messageData.put("keyword5", getTextData(sdf.format(payData.getPayDate())));
                    messageData.put("remark", getTextData("您购买的商品已支付成功，感谢您的支持"));

                    TemplateMessageEntity templateMessage = new TemplateMessageEntity();
                    templateMessage.setOpenId(customer.getOpenid());
                    templateMessage.setData(messageData);
                    templateMessage.setTemplateId(paySuccessMessageId);
                    templateMessage.setUrl(domain + "/wechat/myOrder?openId=" + customer.getOpenid());
                    return templateMessage;
                }
            }
        }

        return null;
    }

    private Map<String, Object> getTextData(String value) {
        Map<String, Object> m = new HashMap<>(8);
        m.put("value", value);
        m.put("color", "#000000");
        return m;
    }

    private Map<String, Object> getTextData(String value, String color) {
        Map<String, Object> m = new HashMap<>(8);
        m.put("value", value);
        m.put("color", color);
        return m;
    }

    private List<String> getOpenId(List<Integer> operIds){
        List<String> openIds = new ArrayList<>(operIds.size());
        for (Integer noticeOperId : operIds) {
            OperatorEntity operator = operatorDao.selectByPrimaryKey(noticeOperId);
            //仅发送正常状态下的用户
            if (operator != null && operator.getState() == 0 && operator.getOpenId() != null) {
                openIds.add(operator.getOpenId());
            }
        }
        return openIds;
    }
}
