package com.xiedapao.utils;

public class NumberUtil {

    public static int parseInt(Object obj) {
        if(obj == null || "".equals(StringUtil.toString(obj)) || "false".equals(StringUtil.toString(obj)))
            return 0;
        return Integer.parseInt(StringUtil.toString(obj));
    }

    public static long parseLong(Object obj) {
        if (obj == null || "".equals(StringUtil.toString(obj)))
            return 0;
        return Long.parseLong(StringUtil.toString(obj));
    }

    public static int count(int num) {
        int total = 0;
        for (int i = 1; i <= num; i++) {
            total += i;
        }
        return total;
    }

}
