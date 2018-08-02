package com.grape.supermarket.operator.service;

import com.grape.supermarket.entity.db.AttributeEntity;

import java.util.List;

/**
 * Created by LQW on 2017/11/7.
 */

public interface AttributeService {

    List<AttributeEntity> selectAll();
}
