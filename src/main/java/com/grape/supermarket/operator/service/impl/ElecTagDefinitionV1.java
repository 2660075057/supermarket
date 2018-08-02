package com.grape.supermarket.operator.service.impl;

import com.grape.supermarket.operator.service.ElecTagDefinition;

import java.util.Map;

/**
 * Created by LXP on 2018/3/26.
 */

public class ElecTagDefinitionV1 implements ElecTagDefinition {
    @Override
    public String createElec(String barcode, Map<String, String> userData, int seq) {
        String seqStr = Integer.toHexString(seq);
        while (seqStr.length() < 3) {
            seqStr = "0" + seqStr;
        }
        while (barcode.length() < 13) {
            barcode = "0" + barcode;
        }
        String elec = barcode + 'A' + seqStr + userData.get("ud1") + '1';
        if (elec.length() == 24) {
            return elec.toUpperCase();
        }
        return null;
    }

    @Override
    public String disLock(String tag) {
        if (tag.length() < 24) {
            return null;
        }
        if (tag.length() == 24) {
            return (tag.substring(0, 23) + '0').toUpperCase();
        } else {
            return (tag.substring(0, 23) + '0' + tag.substring(24)).toUpperCase();
        }
    }

    @Override
    public int analysisSeq(String tag) {
        return Integer.parseInt(tag.substring(14, 17), 16);
    }

    @Override
    public String sqlWhere(String barcode, Map<String, String> userData) {
        while (barcode.length() < 13) {
            barcode = "0" + barcode;
        }
        return (barcode + "A___" + userData.get("ud1") + '_').toUpperCase();
    }

    @Override
    public int maxSeq() {
        return 0xFFF;
    }
}
