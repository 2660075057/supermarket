package com.grape.supermarket.entity.db;

public class PictureEntity {
    /**
     * <pre>
     * id
     * 表字段 : picture.id
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 图片地址
     * 表字段 : picture.img_url
     * </pre>
     */
    private String imgUrl;

    /**
     * <pre>
     * 图片类型，1商品图片
     * 表字段 : picture.type
     * </pre>
     */
    private Byte type;

    /**
     * <pre>
     * 关联的记录号
     * 表字段 : picture.recno
     * </pre>
     */
    private Integer recno;

    private Byte sort;

    /**
     * <pre>
     * 获取：id
     * 表字段：picture.id
     * </pre>
     *
     * @return picture.id：id
     */
    public Integer getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：id
     * 表字段：picture.id
     * </pre>
     *
     * @param id
     *            picture.id：id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：图片地址
     * 表字段：picture.img_url
     * </pre>
     *
     * @return picture.img_url：图片地址
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * <pre>
     * 设置：图片地址
     * 表字段：picture.img_url
     * </pre>
     *
     * @param imgUrl
     *            picture.img_url：图片地址
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * <pre>
     * 获取：图片类型，1商品图片
     * 表字段：picture.type
     * </pre>
     *
     * @return picture.type：图片类型，1商品图片
     */
    public Byte getType() {
        return type;
    }

    /**
     * <pre>
     * 设置：图片类型，1商品图片
     * 表字段：picture.type
     * </pre>
     *
     * @param type
     *            picture.type：图片类型，1商品图片
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * <pre>
     * 获取：关联的记录号
     * 表字段：picture.recno
     * </pre>
     *
     * @return picture.recno：关联的记录号
     */
    public Integer getRecno() {
        return recno;
    }

    /**
     * <pre>
     * 设置：关联的记录号
     * 表字段：picture.recno
     * </pre>
     *
     * @param recno
     *            picture.recno：关联的记录号
     */
    public void setRecno(Integer recno) {
        this.recno = recno;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }
}