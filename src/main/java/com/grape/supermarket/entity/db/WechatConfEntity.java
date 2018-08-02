package com.grape.supermarket.entity.db;

public class WechatConfEntity {
    /**
     * <pre>
     * 主键
     * 表字段 : wechat_conf.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 设置类型，1主屏幕设置
     * 表字段 : wechat_conf.type
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * 自定义数据，存取解析由业务决定
     * 表字段 : wechat_conf.data
     * </pre>
     */
    private String data;

    /**
     * <pre>
     * 获取：主键
     * 表字段：wechat_conf.id
     * </pre>
     *
     * @return wechat_conf.id：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：wechat_conf.id
     * </pre>
     *
     * @param id
     *            wechat_conf.id：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：设置类型，1主屏幕设置
     * 表字段：wechat_conf.type
     * </pre>
     *
     * @return wechat_conf.type：设置类型，1主屏幕设置
     */
    public Integer getType() {
        return type;
    }

    /**
     * <pre>
     * 设置：设置类型，1主屏幕设置
     * 表字段：wechat_conf.type
     * </pre>
     *
     * @param type
     *            wechat_conf.type：设置类型，1主屏幕设置
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * <pre>
     * 获取：自定义数据，存取解析由业务决定
     * 表字段：wechat_conf.data
     * </pre>
     *
     * @return wechat_conf.data：自定义数据，存取解析由业务决定
     */
    public String getData() {
        return data;
    }

    /**
     * <pre>
     * 设置：自定义数据，存取解析由业务决定
     * 表字段：wechat_conf.data
     * </pre>
     *
     * @param data
     *            wechat_conf.data：自定义数据，存取解析由业务决定
     */
    public void setData(String data) {
        this.data = data;
    }
}