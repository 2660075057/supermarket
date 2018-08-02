package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.dao.AttributeDao;
import com.grape.supermarket.entity.db.AttributeEntity;
import com.grape.supermarket.operator.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LQW on 2017/11/7.
 */
@Service
public class AttributeServiceImpl implements AttributeService {
    @Autowired
    private AttributeDao attributeDao;
    @Override
    public List<AttributeEntity> selectAll() {
        return attributeDao.selectAll();
    }
}
