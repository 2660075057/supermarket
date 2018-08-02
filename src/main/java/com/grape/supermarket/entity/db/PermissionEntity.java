package com.grape.supermarket.entity.db;

import java.io.Serializable;

public class PermissionEntity implements Serializable {
    /**
     * <pre>
     * 
     * 表字段 : permission.id
     * </pre>
     */
    private Integer id;

    /**
     * 权限代码
     */
    private String code;

    /**
     * <pre>
     * 权限名
     * 表字段 : permission.name
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 权限url，多个url逗号分隔
     * 表字段 : permission.url
     * </pre>
     */
    private String url;

    /**
     *
     */
    public PermissionEntity(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    /**
     *
     */
    public PermissionEntity() {
        super();
    }

    /**
     * <pre>
     * 获取：
     * 表字段：permission.id
     * </pre>
     *
     * @return permission.id：
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：
     * 表字段：permission.id
     * </pre>
     *
     * @param id
     *            permission.id：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：权限名
     * 表字段：permission.name
     * </pre>
     *
     * @return permission.name：权限名
     */
    public String getName() {
        return name;
    }

    /**
     * <pre>
     * 设置：权限名
     * 表字段：permission.name
     * </pre>
     *
     * @param name
     *            permission.name：权限名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <pre>
     * 获取：权限url，多个url逗号分隔
     * 表字段：permission.url
     * </pre>
     *
     * @return permission.url：权限url，多个url逗号分隔
     */
    public String getUrl() {
        return url;
    }

    /**
     * <pre>
     * 设置：权限url，多个url逗号分隔
     * 表字段：permission.url
     * </pre>
     *
     * @param url
     *            permission.url：权限url，多个url逗号分隔
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}