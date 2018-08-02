package com.grape.supermarket.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by LXP on 2017/10/26.
 */

public class WechatLocationEvent extends ApplicationEvent {
    public WechatLocationEvent(LocationData source) {
        super(source);
    }

    public static class LocationData{
        private String openId;
        private double latitude;
        private double longitude;
        private double precision;
        private long createTime;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getPrecision() {
            return precision;
        }

        public void setPrecision(double precision) {
            this.precision = precision;
        }
    }
}
