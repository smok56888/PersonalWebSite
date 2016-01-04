package com.smok.web.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smok on 2015/12/28.
 */
public class MD5Util {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            // System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static String string2MD5(String inStr, String charset) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] byteArray = inStr.getBytes(charset);

            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    public static String toMd5(byte[] paramArrayOfByte) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.reset();
            localMessageDigest.update(paramArrayOfByte);
            byte[] bys = localMessageDigest.digest();
            return byteArrayToHexString(bys);
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
            throw new RuntimeException(localNoSuchAlgorithmException);
        }
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * J 转换byte到16进制
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 车牌转成16位数字串，模拟imei -- 供chexingyi_app用
     */
    public static String carnum2IntegerStr(String carnum) {
        if (carnum != null && carnum.length() == 7) {
            String md5Value = string2MD5(carnum.toUpperCase());
            StringBuilder returnValue = new StringBuilder();
            for (int i = 0; i < md5Value.length(); i += 2) {
                int a = char2Integer(md5Value.charAt(i));
                int b = char2Integer(md5Value.charAt(i + 1));

                int value = (a * 31 + b) % 10;
                if (i == 0 && value == 0) { // 首位为0
                    value = 1 + ((a * 31 + b) % 9);
                }
                returnValue.append(value);
            }
            return returnValue.toString();
        }
        return null;
    }

    private static Integer char2Integer(Character c) {
        int salt = 48;
        if (HEX_CHARACTER_SET.contains(c)) {
            salt = 87;
        }
        return Integer.valueOf((int) c - salt);
    }

    private static final Set<Character> HEX_CHARACTER_SET = new HashSet<Character>();

    static {
        HEX_CHARACTER_SET.addAll(Arrays.asList(new Character[]{'a', 'b', 'c', 'd', 'e', 'f'}));
    }
}
