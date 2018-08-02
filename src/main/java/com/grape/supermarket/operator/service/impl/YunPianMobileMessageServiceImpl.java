package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.common.util.HttpClientUtil;
import com.grape.supermarket.common.util.HttpResponce;
import com.grape.supermarket.common.util.JsonUtils;
import com.grape.supermarket.common.util.PropertiesLoader;
import com.grape.supermarket.dao.MessageRecordDao;
import com.grape.supermarket.entity.db.MessageRecordEntity;
import com.grape.supermarket.operator.service.MobileMessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2018/1/11.
 */

public class YunPianMobileMessageServiceImpl implements MobileMessageService {
    private Logger logger = Logger.getLogger(getClass());
    private final String apiKey;
    private final String singleSendurl;
    private final String singleContent;
    private final String charset;

    @Autowired
    private MessageRecordDao messageRecordDao;

    public YunPianMobileMessageServiceImpl() throws UnsupportedEncodingException {
        PropertiesLoader pl = new PropertiesLoader("message.properties");
        apiKey = pl.getProperty("yunpian_apiKey");
        singleSendurl = pl.getProperty("yunpian_singleSendurl");
        singleContent = new String(pl.getProperty("yunpian_singleContent").getBytes("ISO-8859-1"), "utf-8");
        charset = pl.getProperty("yunpian_charset");
    }


    @Override
    public int sendRegisterMessage(String mobile, String vcode) {
        //验证是否达到最大发信次数
        boolean isNew = false;
        MessageRecordEntity mre = messageRecordDao.selectByPrimaryKey(mobile);
        if (mre == null) {
            isNew = true;
            mre = new MessageRecordEntity();
            mre.setPhone(mobile);
            mre.setFrequency(1);
            mre.setCode(vcode);
            mre.setLastSend(new Date());
        } else {
            mre.setFrequency(mre.getFrequency() + 1);
            mre.setCode(vcode);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        String testDay = sdf.format(mre.getLastSend());
        if (today.equals(testDay) && mre.getFrequency() > 20) {
            return 1;
        } else if (!today.equals(testDay)) {//最后发信日期为昨天，清空记录
            mre.setFrequency(1);
        }

        //验证通过，开始发送短信
        Map<String, String> map = new HashMap<>();
        map.put("apikey", apiKey);
        map.put("mobile", mobile);
        map.put("text", singleContent.replace("[code]", vcode));
        HttpResponce hr = HttpClientUtil.doPost(singleSendurl, map, charset);

        String c = "";
        if (hr.getState() == 200) {
            try {
                Map<String, Object> m = JsonUtils.fromJson(hr.getBody(), Map.class);
                if (String.valueOf(m.get("code")).equals("0")) {
                    updateMessageRecord(mre, isNew);
                    return 0;
                }
            } catch (Exception e) {
                c = System.currentTimeMillis() + " ";
                logger.error(c + "识别云片返回信息出错,返回信息==>" + hr);
            }
        }

        logger.error(c + "发送云片短信失败,发送内容==>" + JsonUtils.toJson(map) + "  返回信息==>" + hr);

        return -1;
    }

    private void updateMessageRecord(MessageRecordEntity record, boolean isNew) {
        record.setLastSend(new Date());
        if (isNew) {
            try {
                messageRecordDao.insertSelective(record);
            } catch (Exception e) {
                logger.error("插入短信记录失败,recore->" + record + " isNew->" + isNew);
            }
        } else {
            messageRecordDao.updateByPrimaryKeySelective(record);
        }
    }
}
