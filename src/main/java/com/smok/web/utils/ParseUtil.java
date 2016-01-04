package com.smok.web.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by smok on 2015/12/28.
 */
public class ParseUtil {
    public static double parseDouble(String str, double defValue) {
        try {
            if (StringUtils.isEmpty(str)) {
                return defValue;
            }
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long parseLong(String str, long defValue) {
        try {
            if (StringUtils.isEmpty(str)) {
                return defValue;
            }
            return Long.parseLong(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static int parseInt(String str, int defValue) {
        try {
            if (StringUtils.isEmpty(str)) {
                return defValue;
            }
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static boolean parseBoolean(String str, boolean defValue) {
        try {
            if (StringUtils.isEmpty(str)) {
                return defValue;
            }
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static int[] parseInt(String[] arrStr) {
        if (arrStr == null)
            return null;
        int[] arrInt = new int[arrStr.length];
        for (int i = 0; i < arrStr.length; i++) {
            arrInt[i] = parseInt(arrStr[i], 0);
        }
        return arrInt;
    }
}
