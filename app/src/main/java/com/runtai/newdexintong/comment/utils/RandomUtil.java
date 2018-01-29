package com.runtai.newdexintong.comment.utils;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/3.
 */
public class RandomUtil {

    public static int getPassword() {
        Random rd = new Random(); // 创建随机对象  
        String n = ""; // 保存随机数  
        int rdGet; // 取得随机数  
        do {
            if (rd.nextInt() % 2 == 1) {
                rdGet = Math.abs(rd.nextInt()) % 10 + 48; // 产生48到57的随机数(0-9的键位值)  
            } else {
                rdGet = Math.abs(rd.nextInt()) % 26 + 97; // 产生97到122的随机数(a-z的键位值)  
            }
            char num1 = (char) rdGet; // int转换char  
            String dd = Character.toString(num1);
            n += dd;
        } while (n.length() < 8);// 设定长度，此处假定长度小于8  
        return Integer.parseInt(n);

    }

    /**
     * 获取一个十位的随机数
     *
     * @return
     */
    public static String getRandomNumberTen() {
        
        Random rm = new Random();
        // 获得随机数  
        double pross = (1 + rm.nextDouble()) * Math.pow(10, 11);
        // 将获得的获得随机数转化为字符串  
        String fixLenthString = String.valueOf(pross).replace(".","");

        // 返回固定的长度的随机数  
        return fixLenthString.substring(0, 10);
    }

}
