package com.grape.supermarket.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author shuang.gao  Date: 2012/1/28 Time: 12:26
 */
public class Pager<T> {

    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * 原集合
     */
    private List<T> data;

    private Pager(List<T> data, int pageSize) {
        if (data == null) {
            throw new IllegalArgumentException("data must be not null!");
        }
        assert pageSize > 0:"pageSize must > 0";
        this.data = data;
        this.pageSize = pageSize;
    }

    /**
     * 创建分页器
     *
     * @param data 需要分页的数据
     * @param pageSize 每页显示条数
     * @param <T> 业务对象
     * @return 分页器
     */
    public static <T> Pager<T> create(List<T> data, int pageSize) {
        return new Pager<>(data, pageSize);
    }

    /**
     * 得到分页后的数据
     *
     * @param pageNum 页码
     * @return 分页后结果
     */
    public List<T> getPagedList(int pageNum) {
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();
        }

        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getData() {
        return new ArrayList<>(data);
    }
}