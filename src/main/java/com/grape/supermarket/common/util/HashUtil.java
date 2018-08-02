package com.grape.supermarket.common.util;

import java.security.MessageDigest;

public class HashUtil {
    /**
     * 返回字符串小写的32位MD5
     *
     * @return 失败返回null
     */
    public static String md5Low(String str) {
        return md5(str, true, true);
    }

    /**
     * 返回字符串大写的32位MD5
     *
     * @return 失败返回null
     */
    public static String md5Upper(String str) {
        return md5(str, true, false);
    }

    /**
     * 返回字符串的MD5
     *
     * @param model true为32位 false为16为md5
     * @param isLow 是否为小写串
     * @return 失败返回null
     */
    public static String md5(String str, boolean model/*true为32位 false为16为md5*/, boolean isLow/*是否为小写串*/) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("utf-8"));
            byte b[] = md.digest();

            String m = toHexString(b);
            if (model) {// 32位的加密
                return isLow ? m : m.toUpperCase();
            } else {// 16位的加密
                return isLow ? m.substring(8, 24) : m.substring(8, 24).toUpperCase();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(str.getBytes("utf-8"));
            byte b[] = md.digest();

            return toHexString(b);
        } catch (Exception e) {
            return null;
        }
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(64);
        for (byte b1 : b) {
            int i = b1;
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }
}
