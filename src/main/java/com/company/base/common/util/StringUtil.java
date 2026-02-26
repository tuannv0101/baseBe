package com.company.base.common.util;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String capitalize(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
