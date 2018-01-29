package com.runtai.newdexintong.comment.utils.des;

import java.io.UnsupportedEncodingException;

/**
 * 字符串和utf-8字节码互相转换
 */
public class UTF8Util {

    private static byte[] bytes;
    private static String str;

    /**
     * 字符串转为字节utf-8码
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {

        try {
            bytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * utf-8字节码转为字符串
     *
     * @param bytes
     * @return
     */
    public static String getString(byte[] bytes) {

        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }

}
