package com.grape.supermarket.wechat.service.impl;

import com.grape.supermarket.common.BasicService;
import com.grape.supermarket.common.util.*;
import com.grape.supermarket.dao.CustomerDao;
import com.grape.supermarket.dao.OrderDao;
import com.grape.supermarket.dao.PaymentLogDao;
import com.grape.supermarket.entity.PayEntity;
import com.grape.supermarket.entity.PayOrderInfoEntity;
import com.grape.supermarket.entity.db.CustomerEntity;
import com.grape.supermarket.entity.db.OrderEntity;
import com.grape.supermarket.entity.db.PaymentLogEntity;
import com.grape.supermarket.event.PayCancelEvent;
import com.grape.supermarket.event.PaySuccessEvent;
import com.grape.supermarket.wechat.service.PayService;
import com.grape.supermarket.wechat.service.WechatApiUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by LXP on 2017/10/12.
 */
@Service("WechatPayService")
public class WechatPayServiceImpl extends BasicService implements PayService {
    private final String appId;//公众号id
    private final String mchId;//商户号id
    private final String key;//微信平台设置的key
    private final String notifyUrl;//通知url
    private final String ip;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PaymentLogDao paymentLogDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    public WechatPayServiceImpl(@Qualifier("wechatProperties")PropertiesLoader pl) {
        this.appId = pl.getProperty("appId");
//        this.appId = "wx48648e81152e24b1";//TO DO 删除ilas公众号桩
        this.mchId = pl.getProperty("mchId");
        this.key = pl.getProperty("key");
        this.notifyUrl = pl.getProperty("domain")+"/wechat/payNotify";
        this.ip = pl.getProperty("ip");
    }

    @Override
    public PayOrderInfoEntity payBarcode(PayEntity payEntity) {
        if(payEntity.getPayment()-payEntity.getPreferential() < 1){
            throw new IllegalArgumentException(payEntity+" 支付金额低于1");
        }
        Map<String, String> param = new HashMap<>(16, 0.9f);
        param.put("appid", appId);
        param.put("mch_id", mchId);
        param.put("notify_url", notifyUrl);
        param.put("body", "葡萄无人超市支付");
        String nonce_str = StringUtils.randomStr((int) (System.currentTimeMillis() % 32 + 1));
        param.put("nonce_str", nonce_str);
        param.put("out_trade_no", payEntity.getPaymentId());
        param.put("total_fee", Integer.valueOf(payEntity.getPayment()-payEntity.getPreferential()).toString());
        param.put("trade_type", "NATIVE");
        param.put("spbill_create_ip", ip);
        param.put("sign_type", "MD5");
        String sign = getSign(param, 1);
        param.put("sign", sign);
        String xml = XmlUtils.toXml(param, "xml");
        if (xml == null) {
            throw new RuntimeException("转换xml出错,原始信息->" + JsonUtils.toJson(param));
        }
        getLogger().info("WechatPayServiceImpl.paybarcode xml->" + xml);

        HttpResponce hr = HttpClientUtil.doPost(WechatApiUrl.unifiedorder, xml, "utf-8");
        if (hr.getState() == 200) {
            String body = hr.getBody();
            getLogger().info("WechatPayServiceImpl.paybarcode request " +
                    ",PaymentId->" + payEntity.getPaymentId() +
                    ",body->" + body);
            Map<String, Object> responceMap = XmlUtils.toMap(body,"utf-8");
            if (!"SUCCESS".equals(responceMap.get("return_code"))) {//验证响应结果
                throw new RuntimeException("WechatPayServiceImpl.paybarcode return_code error" +
                        ",PaymentId" + payEntity.getPaymentId() +
                        ",message->" + responceMap.get("return_msg"));
            }
            if (!"SUCCESS".equals(responceMap.get("result_code"))) {//验证操作结果
                throw new RuntimeException("WechatPayServiceImpl.paybarcode result_code error" +
                        ",PaymentId" + payEntity.getPaymentId() +
                        ",message->" + responceMap.get("err_code"));
            }
            boolean b = verifySign(responceMap);
            if (!b) {
                throw new RuntimeException("WechatPayServiceImpl.paybarcode verifySign error" +
                        ",PaymentId" + payEntity.getPaymentId() +
                        ",body->" + body);
            }
            PayOrderInfoEntity poi = new PayOrderInfoEntity();
            poi.setType(PayOrderInfoEntity.PAY_ORDER_TYPE.WECHAT_SCAN);
            poi.setBarcode((String) responceMap.get("code_url"));
            poi.setPrepayId((String) responceMap.get("prepay_id"));
            poi.setPaymentId(payEntity.getPaymentId());
            return poi;
        }
        throw new RuntimeException("WechatPayServiceImpl.paybarcode https error,hr->" + hr);
    }

    @Override
    public PAY_STATE cancelBarcode(String paymentId) {
        Map<String, String> param = new HashMap<>();
        param.put("appid", appId);
        param.put("mch_id", mchId);
        param.put("out_trade_no", paymentId);
        String nonce_str = StringUtils.randomStr((int) (System.currentTimeMillis() % 32 + 1));
        param.put("nonce_str", nonce_str);
        param.put("sign_type", "MD5");
        String sign = getSign(param, 1);
        param.put("sign", sign);
        String xml = XmlUtils.toXml(param, "xml");
        if (xml == null) {
            throw new RuntimeException("转换xml出错,原始信息->" + JsonUtils.toJson(param));
        }
        HttpResponce hr = HttpClientUtil.doPost(WechatApiUrl.closeorder, xml, "utf-8");
        if (hr.getState() == 200) {
            String body = hr.getBody();
            getLogger().info("WechatPayServiceImpl.cancelBarcode request " +
                    ",PaymentId" + paymentId +
                    ",body->" + body);
            Map<String, Object> responceMap = XmlUtils.toMap(body,"utf-8");
            if (!"SUCCESS".equals(responceMap.get("return_code"))) {
                getLogger().error("WechatPayServiceImpl.cancelBarcode return_code error" +
                        ",PaymentId" + paymentId +
                        ",message->" + responceMap.get("return_msg"));
                return PAY_STATE.FAIL;
            }
            boolean b = verifySign(responceMap);
            if (!b) {
                getLogger().error("WechatPayServiceImpl.cancelBarcode verifySign error" +
                        ",PaymentId" + paymentId +
                        ",body->" + body);
                return PAY_STATE.FAIL;
            }

            String logCode;
            String note;
            PAY_STATE state;
            if ("SUCCESS".equals(responceMap.get("result_code"))) {//验证操作结果
                logCode = "100003001";
                note = "取消成功";
                state = PAY_STATE.SUCCESS;
            } else {
                Object err_code = responceMap.get("err_code");
                if ("ORDERPAID".equals(err_code)) {
                    logCode = "100003003";
                    note = "订单已经支付,err_code->"+err_code;
                    state = PAY_STATE.ORDERPAID;//已经支付
                } else if ("SYSTEMERROR".equals(err_code)) {
                    logCode = "100003005";
                    note = "微信系统错误,err_code->"+err_code;
                    state = PAY_STATE.SYSTEMERROR;//系统错误
                } else if ("ORDERCLOSED".equals(err_code)) {
                    logCode = "100003004";
                    note = "订单已经关闭,err_code->"+err_code;
                    state = PAY_STATE.SUCCESS;//已经关闭，算成功
                } else {
                    logCode = "100003002";
                    note = "取消失败,err_code->"+err_code;
                    state = PAY_STATE.FAIL;
                }
            }
            //写入支付日志
            PaymentLogEntity plRecord = new PaymentLogEntity();
            plRecord.setTransactionType((byte) 1);
            plRecord.setLogCode(logCode);//日志码
            plRecord.setPaymentId(paymentId);
            plRecord.setPaymentData(body);
            plRecord.setNote(note);
            paymentLogDao.insertSelective(plRecord);
            return state;
        }
        throw new RuntimeException("WechatPayServiceImpl.cancelBarcode https error,hr->" + JsonUtils.toJson(hr));
    }

//    @Override
//    public PAY_STATE payState(String paymentId) {
//        // T ODO: 2017/10/12 等待测试
//        Map<String, String> param = new HashMap<>();
//        param.put("appId", appId);
//        param.put("mch_id", mchId);
//        param.put("out_trade_no", paymentId);
//        String nonce_str = StringUtils.randomStr((int) (System.currentTimeMillis() % 32 + 1));
//        param.put("nonce_str", nonce_str);
//        String sign = getSign(param, 1);
//        param.put("sign_type", "MD5");
//        param.put("sign", sign);
//        String xml = XmlUtils.toXml(param, "xml");
//        if (xml == null) {
//            throw new RuntimeException("转换xml出错,原始信息->" + JsonUtils.toJson(param));
//        }
//        HttpResponce hr = HttpClientUtil.doPost(WechatApiUrl.closeorder, xml, "utf-8");
//        if(hr.getState() == 200){
//            String body = hr.getBody();
//            getLogger().info("WechatPayServiceImpl.payState request " +
//                    ",PaymentId" + paymentId +
//                    ",body->" + body);
//            Map<String, Object> responceMap = XmlUtils.toMap(body);
//            if (!"SUCCESS".equals(responceMap.get("return_code"))) {
//                throw new RuntimeException("WechatPayServiceImpl.paybarcode return_code error" +
//                        ",PaymentId" + paymentId +
//                        ",message->" + responceMap.get("return_msg"));
//            }
//        }
//        throw new RuntimeException("WechatPayServiceImpl.payState https error,hr->" + JsonUtils.toJson(hr));
//    }


    @Override
    public String noticeCheck(String body) {
        String logId = UUID.randomUUID().toString().replace("-","");
        getLogger().info(logId + "收到微信支付通知,message->" + body);
        Map<String,Object> bodyMap = XmlUtils.toMap(body,"utf-8");

        boolean b = verifySign(bodyMap);
        if (!b) {
            getLogger().info(logId + "微信支付通知验证签名失败,message->" + body);
            return "<xml><return_code>FAIL</return_code><return_msg>verify sign fail</return_msg></xml>";
        }
        String transactionId = (String) bodyMap.get("transaction_id");//微信订单号
        String openid = (String) bodyMap.get("openid");//用户openId
        String paymentId = (String) bodyMap.get("out_trade_no");//本地订单号
        Date paySuccessDate = new Date();//支付成功时间

        if(!"SUCCESS".equals(bodyMap.get("return_code"))
                || !"SUCCESS".equals(bodyMap.get("result_code"))){
            PaymentLogEntity plRecord = new PaymentLogEntity();
            plRecord.setTransactionType((byte) 1);
            plRecord.setLogCode("100001002");//订单支付失败
            plRecord.setPaymentId(paymentId);
            plRecord.setPaymentData(body);
            plRecord.setNote("订单支付失败");
            paymentLogDao.insertSelective(plRecord);
            getLogger().error(logId + "订单" + paymentId + "支付失败,wechat message->" + body);
            return "<xml><return_code>FAIL</return_code><return_msg>return_code and result_code not SUCCESS</return_msg></xml>";
        }

        //查找用户id
        CustomerEntity customer = customerDao.selectByOpenid(openid);
        Integer customerId = null;
        if(customer != null){
            customerId = customer.getCoustId();
        }

        OrderEntity orderInfo = orderDao.selectByPaymentId(paymentId);
        if(orderInfo == null){
            PaymentLogEntity plRecord = new PaymentLogEntity();
            plRecord.setTransactionType((byte) 1);
            plRecord.setLogCode("100001004");//查询订单失败
            plRecord.setPaymentId(paymentId);
            plRecord.setPaymentData(body);
            plRecord.setNote("查询订单失败，找不到目标订单");
            paymentLogDao.insertSelective(plRecord);
            getLogger().error(logId + "查询订单"+paymentId+"失败,wechat message->"+body);
            return "<xml><return_code>FAIL</return_code><return_msg>not exists order</return_msg></xml>";
        }
        String total_fee = (String) bodyMap.get("total_fee");
        //验证支付金额是否正确
        if(!total_fee.equals(""+(orderInfo.getPayment() - orderInfo.getPreferential()))){
            //支付金额校验失败日志
            PaymentLogEntity plRecord = new PaymentLogEntity();
            plRecord.setTransactionType((byte) 1);
            plRecord.setLogCode("100001003");//支付失败，校验金额出错
            plRecord.setPaymentId(paymentId);
            plRecord.setPaymentData(body);
            plRecord.setNote("订单支付校验不通过");
            paymentLogDao.insertSelective(plRecord);
            getLogger().error(logId + "订单"+paymentId+"支付金额与实际不符,wechat message->"+body);
            return "<xml><return_code>FAIL</return_code><return_msg>total_fee not equals</return_msg></xml>";
        }

        getLogger().info(logId + "校验微信支付通知成功,wechat message->"+body);
        PaySuccessEvent.PayData payData = new PaySuccessEvent.PayData();
        payData.setExternalId(transactionId);
        payData.setPaymentType((byte) 1);//设置支付类型为微信
        payData.setCoustId(customerId);
        payData.setPaymentId(paymentId);
        payData.setPayment(orderInfo.getPayment());
        payData.setPreferential(orderInfo.getPreferential());
        payData.setShopId(orderInfo.getShopId());
        payData.setPayDate(paySuccessDate);
        publisher.publishEvent(new PaySuccessEvent(payData));//发布支付成功通知

        //写入支付日志
        PaymentLogEntity plRecord = new PaymentLogEntity();
        plRecord.setTransactionType((byte) 1);
        plRecord.setLogCode("100001001");//支付成功日志码
        plRecord.setPaymentId(paymentId);
        plRecord.setPaymentData(body);
        plRecord.setNote("订单支付成功");
        paymentLogDao.insertSelective(plRecord);

        return "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
    }

    @EventListener
    @Override
    public void cancelEvent(PayCancelEvent event) {
        PayCancelEvent.PayCancelData source = ((PayCancelEvent.PayCancelData) event.getSource());
        long seq = System.currentTimeMillis();
        getLogger().info(seq+" 收到取消订单通知，paymentId->"+source.getPaymentId());
        PAY_STATE pay_state;
        try {
            pay_state = cancelBarcode(source.getPaymentId());
        } catch (Exception e) {
            pay_state = PAY_STATE.FAIL;
        }
        getLogger().info(seq+" paymentId->["+source.getPaymentId()+"] 取消订单结果"+pay_state);
    }

    /**
     * 获取随机字符串
     *
     * @return 随机字符串
     */
    private String getSign(Map<String, String> params, int model/*模式 1：md5 2:HMAC-SHA256*/) {
        if (model != 1) {//暂时只支持md5
            throw new IllegalArgumentException("不支持的签名算法，当前支持值有1");
        }
        String[] strings = new String[params.size()];
        int index = 0;
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            strings[index] = stringStringEntry.getKey() + "=" + stringStringEntry.getValue();
            index++;
        }
        Arrays.sort(strings);
        StringBuilder sb = new StringBuilder(256);
        for (String string : strings) {
            sb.append(string).append('&');
        }
        sb.append("key=").append(key);
        return HashUtil.md5Upper(sb.toString());
    }

    private boolean verifySign(Map<String, Object> data) {
        String sign = String.valueOf(data.remove("sign"));
        List<String> strings = new ArrayList<>(data.size());
        for (Map.Entry<String, Object> stringStringEntry : data.entrySet()) {
            String value = String.valueOf(stringStringEntry.getValue());
            if(!StringUtils.isEmpty(value) && !"null".equalsIgnoreCase(value)) {
                String s = stringStringEntry.getKey() + "=" + stringStringEntry.getValue();
                strings.add(s);
            }
        }
        Collections.sort(strings);
        StringBuilder sb = new StringBuilder(256);
        for (String string : strings) {
            sb.append(string).append('&');
        }
        sb.append("key=").append(key);
        return HashUtil.md5Upper(sb.toString()).equals(sign);
    }
}
