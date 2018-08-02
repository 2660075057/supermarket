package com.grape.supermarket.wechat.service;

import com.grape.supermarket.entity.PayEntity;
import com.grape.supermarket.entity.PayOrderInfoEntity;
import com.grape.supermarket.event.PayCancelEvent;

/**
 * Created by LXP on 2017/10/12.
 */

public interface PayService {

    enum PAY_STATE{SUCCESS/*成功*/,FAIL/*失败*/,SYSTEMERROR/*系统错误*/,NOTPAY/*未支付*/,REFUND/*退款*/,
        CLOSED/*已经关闭*/,PAYERROR/*支付失败*/,ORDERPAID/*订单已支付*/
        }

    /**
     * 生成支付二维码
     * @return 支付二维码
     */
    PayOrderInfoEntity payBarcode(PayEntity payEntity);

    /**
     * 取消订单
     * @param paymentId 内部订单id
     * @return 是否取消成功
     */
    PAY_STATE cancelBarcode(String paymentId);

//    /**
//     * 查询交易状态
//     * @param paymentId 内部订单id
//     * @return 交易状态
//     */
//    PAY_STATE payState(String paymentId);

    /**
     * 检查通知接口数据，并生成返回结果
     * @param body 接口数据
     * @return 返回结果
     */
    String noticeCheck(String body);

    void cancelEvent(PayCancelEvent event);
}
