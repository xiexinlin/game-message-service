package com.xiedapao.utils;

/**
 * @preserve public
 */
public class StringUtil {
    /**
     * 判断相同
     */
    public static boolean equal(Object obj, String str) {
        return toString(obj).equals(str.trim());
    }

    /**
     * 判断不同
     */
    public static boolean noEqual(Object obj, String str) {
        return !toString(obj).equals(str.trim());
    }

    /**
     * format string
     */
    public static String toString(Object obj) {
        if (obj == null) return "";
        return obj.toString().trim();
    }

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        String temp = obj.toString().trim().toLowerCase();
        if ("".equals(temp) || "null".equals(temp))
            return true;
        else
            return false;
    }

    /**
     * 判断是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static String toNum(String str) {
        char[] chars = str.toCharArray();
        String numStr = "";
        for (char c : chars) {
            if (c >= '0' && c <= '9') {
                numStr += c;
            }
        }
        return numStr;
    }

}