package com.chenxianyu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class MD5Util {

    private static final String ALGORITHM = "MD5";

    /**
     * 将给定的字符串转换为MD5哈希。
     *
     * @param input 要加密的字符串
     * @return MD5哈希后的16进制字符串
     */
    public static String toMD5(String input) {
        try {
            // 创建MessageDigest实例，指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);

            // 对输入的字符串进行处理，转换为字节数组
            byte[] messageDigest = md.digest(input.getBytes());

            // 将字节数组转换为16进制字符串
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            // 确保返回的字符串长度为32位，即完整的MD5哈希长度
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
