package com.grape.supermarket.operator.service;

import java.util.Map;

/**
 * Created by LXP on 2018/3/26.
 */

public interface ElecTagDefinition {
    String createElec(String barcode, Map<String, String> userData, int seq);

    String disLock(String tag);

    int analysisSeq(String tag);

    String sqlWhere(String barcode, Map<String, String> userData);

    int maxSeq();
}
