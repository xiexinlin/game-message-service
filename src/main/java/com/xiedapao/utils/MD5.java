package com.xiedapao.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

public class MD5 {

    private static final String KEY = "xiedapao";

    public static String encrypt(String str) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encrypt(Map<String, String> keys, long timestamp) {
        StringBuilder signKey = new StringBuilder();
        StringBuilder paramStr = new StringBuilder();
        keys.forEach((key, value) -> {
            if (paramStr.length() > 0) {
                paramStr.append("&");
            }
            paramStr.append(key).append("=").append(value);
        });
        signKey.append(KEY).append(paramStr).append(timestamp);
        return encrypt(signKey.toString());
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456").toUpperCase());
    }

}
