package com.grape.supermarket.operator;

import com.grape.supermarket.entity.db.GroupEntity;
import com.grape.supermarket.entity.db.OperatorEntity;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.entity.db.ShopEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by LXP on 2017/9/25.
 */

public final class OperatorSession {
    public static final String SESSION_ID = "GRAPE_OPERATOR_SESSION_INFO_ID";
    public static final int upTime = 2000;
    private AtomicLong updateMark = new AtomicLong(System.currentTimeMillis());
    private OperatorEntity operatorInfo;
    private GroupEntity groupInfo;
    private List<PermissionEntity> permissions;
    private List<Integer> shopIds;

    public OperatorSession() {
    }

    public OperatorSession(OperatorEntity operatorInfo, GroupEntity groupInfo, List<PermissionEntity> permissions) {
        this.operatorInfo = operatorInfo;
        this.groupInfo = groupInfo;
        this.permissions = permissions;
        shopIds = new ArrayList<>();
        List<ShopEntity> shops = operatorInfo.getShops();
        if (shops != null) {
            for (ShopEntity shop : shops) {
                shopIds.add(shop.getShopId());
            }
        }else{
            operatorInfo.setShops(new ArrayList<ShopEntity>(0));
        }
        if(shopIds.isEmpty()){
            shopIds.add(-1);
        }
    }

    public long getUpdateTime(){
        return updateMark.get();
    }

    public boolean setUpdateTime(long ut){
        return updateMark.compareAndSet(ut,ut+upTime);
    }

    public OperatorEntity getOperatorInfo() {
        return operatorInfo;
    }

    public void setOperatorInfo(OperatorEntity operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    public GroupEntity getGroupInfo() {
        return groupInfo;
    }

    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setGroupInfo(GroupEntity groupInfo) {
        this.groupInfo = groupInfo;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public List<Integer> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<Integer> shopIds) {
        this.shopIds = shopIds;
    }
}
