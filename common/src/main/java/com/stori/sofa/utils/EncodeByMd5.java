package com.stori.sofa.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 * 用于密码加盐加密
 *
 * @author Harvey Lu
 */
public class EncodeByMd5 {

    private static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 随机生成16位salt
     */
    public String salt() {
        Random random = new Random();
        StringBuilder saltString = new StringBuilder(16);
        for (int i = 0; i < saltString.capacity(); i++) {
            saltString.append(hex[random.nextInt(16)]);
        }
        return saltString.toString();
    }

    /**
     * 对字符串进行32位MD5加密
     *
     * @param str 待加密字符串
     * @return 加密后字符串，字母大写，共32位
     */
    public String encodeByMd5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF-8"));
            String md5 = new BigInteger(1, messageDigest.digest()).toString(16);
            return fillMd5(md5);
        } catch (Exception e) {
            throw new RuntimeException("Encode failed:" + e.getMessage(), e);
        }
    }

    /**
     * 字符串字符数不够32，高位补0
     *
     * @param md5
     * @return 32个字符的字符串
     */
    private static String fillMd5(String md5) {

        return md5.length() == 32 ? md5 : fillMd5("0" + md5);
    }

}
