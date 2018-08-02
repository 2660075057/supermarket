package com.grape.supermarket.event;

import com.grape.supermarket.entity.db.CommodityEntity;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 库存订阅消息推送实体
 */
public class DepotMessageEvent extends ApplicationEvent {
    private int shopId;
    private List<DepotEventData> source;
    private List<Integer> noticeOperatorId;
    public DepotMessageEvent(List<DepotEventData> source) {
        super(source);
        this.source = source;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public List<DepotEventData> getSource() {
        return source;
    }

    public void setSource(List<DepotEventData> source) {
        this.source = source;
    }

    public List<Integer> getNoticeOperatorId() {
        return noticeOperatorId;
    }

    public void setNoticeOperatorId(List<Integer> noticeOperatorId) {
        this.noticeOperatorId = noticeOperatorId;
    }

    public static class DepotEventData extends CommodityEntity{
        private int amount;

        private int threshold;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getThreshold() {
            return threshold;
        }
        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

    }
}
