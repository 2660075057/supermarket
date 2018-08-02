package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.ImportResult;

/**
 * Created by LXP on 2018/4/20.
 * 导入回调
 */

public interface ImportCallback {
    void handle(ImportResult importResult);
}
