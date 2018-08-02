package com.grape.supermarket.operator.service;

import com.grape.supermarket.operator.service.impl.ElecTagDefinitionV1;

/**
 * Created by LXP on 2018/3/26.
 */

public class ElecTagDefinitionFactory {
    private static ElecTagDefinitionFactory instance = new ElecTagDefinitionFactory();

    private ElecTagDefinitionFactory() { }

    public static ElecTagDefinitionFactory getInstance() {
        return instance;
    }

    public ElecTagDefinition getDefinition(int version){
        if(version == 1){
            return new ElecTagDefinitionV1();
        }
        return null;
    }
}
