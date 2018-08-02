package com.grape.supermarket.common.util;


import com.grape.supermarket.common.exception.SignVerifyException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by LXP on 2017/12/11.
 */

public class SignUtil {

    public static void fillMap(Map<String, Object> m) {
        int max = 6 - m.size();
        String key = StringUtils.randomStr(3);
        Set<String> temp = new HashSet<>();
        for (int i = 0; i < max; i++) {
            String value;
            do {
                value = StringUtils.randomStr(6);
            } while (!temp.add(value));
            m.put(key + "@" + i, value);
        }
        m.put("random", StringUtils.randomStr(20));
    }

    public static String sign(Map<String, Object> m, String secret) {
        List<String> param = new ArrayList<>(m.size());
        for (Map.Entry<String, Object> stringObjectEntry : m.entrySet()) {
            String value = String.valueOf(stringObjectEntry.getValue());
            if (!value.isEmpty() && !value.equals("null")) {
                param.add(stringObjectEntry.getKey() + "=" + value);
            }
        }
        return sign(param, secret);
    }

    public static String sign(List<String> param, String secret) {
        Collections.sort(param, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        StringBuilder sb = new StringBuilder(param.size() * 15);
        for (String s : param) {
            sb.append(s).append('&');
        }
        sb.append("ilasSign=").append(secret);

        return HashUtil.md5Upper(sb.toString());
    }

    public static boolean verify(List<String> param, String secret, String sign) {
        String temp = sign(param, secret);
        return temp.equalsIgnoreCase(sign);
    }

    public static boolean verify(Map<String, String> p, String secret) {
        Map<String, String> param = new HashMap<>(p);
        String sign = param.remove("sign");
        List<String> paramList = new ArrayList<>(param.size());
        for (Map.Entry<String, String> stringStringEntry : param.entrySet()) {
            String value = stringStringEntry.getValue();
            if (!StringUtils.isEmpty(value)) {
                paramList.add(stringStringEntry.getKey() + "=" + stringStringEntry.getValue());
            }
        }
        String temp = sign(paramList, secret);
        return temp.equalsIgnoreCase(sign);
    }

    public static boolean verify(HttpServletRequest request, String secret) {
        Enumeration parameterNames = request.getParameterNames();
        List<String> paramList = new ArrayList<>();
        while (parameterNames.hasMoreElements()) {
            String key = ((String) parameterNames.nextElement());
            String value = request.getParameter(key);
            if (value != null && !value.isEmpty()) {
                paramList.add(key + "=" + value);
            }
        }
        String temp = sign(paramList, secret);
        return verify(paramList, secret,temp);
    }

    public static void verifyThrow(HttpServletRequest request, String secret) throws SignVerifyException {
        if (!verify(request, secret)) {
            throw new SignVerifyException();
        }
    }
}
