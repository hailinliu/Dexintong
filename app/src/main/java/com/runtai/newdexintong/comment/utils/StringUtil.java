package com.runtai.newdexintong.comment.utils;

import android.content.ContentValues;
import android.text.TextUtils;
import android.widget.TextView;

import com.runtai.newdexintong.module.home.utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串辅助类 <br>
 * Description: <br>
 * Date: 2013-4-25 <br>
 * Copyright (c) 2011 LEYISoft <br>
 *
 * @author wwy
 */
public class StringUtil {

    public static char split = 0x01;// 分隔�?

    public static char feed = 0x0A;// 换行

    public static String KEY = "zgpg";

    /**
     * string 转为double保留两位小数
     */

    public static String strToDouble_new(String str) {

        Double cny = Double.parseDouble(str);// 6.2041
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(cny);
    }


    /**
     * 获取UrlEncode编码后的结果
     * @param str
     * @return
     */
    public static String getUrlEncodeResult(String str) {

        String encode = "";
        try {
            String encode1 = URLEncoder.encode(str, "UTF-8");
            encode = URLEncoder.encode(encode1,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }
    

    public static String getSignValue(Map<String, String> mapdata) {

        String str = sortKey(mapdata);
        return MD5Util.md5(str + "&AppSecret=" + AppConstant.appsercet_value).toLowerCase();
    }

    /**
     * map集合中数据按key进行排序，并拼接成指定格式的字符串
     *
     * @param mapdata
     * @return
     */
    public static String sortKey(Map<String, String> mapdata) {

        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(mapdata.entrySet());

        //排序方法  
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                //return (o2.getValue() - o1.getValue());   
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        StringBuilder sb = new StringBuilder();

        //排序后  
        for (Map.Entry<String, String> m : infoIds) {

            sb.append(m.getKey() + "=" + m.getValue() + "&");

        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);

    }


    /**
     * 判断字符串是否为 null/�?/无内�?
     *
     * @param str
     * @return
     * @author wwy
     */
    public static boolean isBlank(String str) {
        if (null == str)
            return true;
        if ("".equals(str.trim()))
            return true;
        if (str.equals("null"))
            return true;
        return false;
    }

    /**
     * 字符串相�? null和空字符串认为相等，忽略字符串前后空�?
     *
     * @param str1
     * @param str2
     * @return
     * @author wwy
     */
    public static boolean compareString(String str1, String str2) {
        if (null == str1) {
            str1 = "";
        }
        if (null == str2) {
            str2 = "";
        }
        if (str1.trim().equals(str2.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 将对象转成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    public static String encodePassword(String password, String algorithm) {
        if (algorithm == null)
            return password;
        byte unencodedPassword[] = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        md.reset();
        md.update(unencodedPassword);
        byte encodedPassword[] = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String getEncryptPassword(String password) {
        try {
            // return Des.encrypt(password, KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public static String getEncryptPasswordMD5(String password) {
        return encodePassword(password, "MD5");
    }

    /**
     * 获取json节点�?
     *
     * @param jsonObject
     * @param jsonNode
     * @return
     */
    public static String getJSONObject(JSONObject jsonObject, String jsonNode) {
        try {

            if (jsonObject.has(jsonNode))
                return jsonObject.get(jsonNode).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONObject getJSONNode(JSONObject jsonObject, String jsonNode) {
        try {
            if (jsonObject.has(jsonNode))
                return jsonObject.getJSONObject(jsonNode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向数据库插入字段
     */
    public static ContentValues pubValues(ContentValues values, String cloumn,
                                          String str_value) {
        if (str_value != null) {
            values.put(cloumn, str_value);
        }
        return values;
    }

    /**
     * 字符串转整数
     *
     * @param l_ser
     * @return
     */

    public static int strToInt(String l_ser) {
        int covs = 0;
        try {
            covs = new Integer(l_ser);
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 字符串转double
     *
     * @param gis
     * @return
     */

    public static double strToDouble(String gis) {
        double covs = 0d;
        try {
            covs = new Double(gis).doubleValue();
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 字符串转long
     *
     * @param time
     * @return
     */

    public static long strToLong(String time) {
        long covs = 0l;
        try {
            covs = new Long(time).longValue();
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 验证手机电话号码
     * <p>
     * 手机号码 移动�?134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     * 联�?�：130,131,132,152,155,156,185,186 电信�?133,1349,153,180,189
     *
     * @param ?要验证的手机号码
     * @return
     */
    public static boolean checkMobilephone(String mobilephone) {
        String cm = "^(1[34578]{1}[0-9]{9})$";// 中国移动正则
        // String cu = "^1(3[0-2]|5[256]|8[56])\\d{8}$";// 中国联�?�正�?
        // String ct = "^1((33|53|8[09])[0-9]|349)\\d{7}$";// 中国电信正则
        // String telRegex = "[1][358]\\d{9}";
        if (Pattern.matches(cm, mobilephone)) {
            return true;
        }
        return false;
    }

//    public static boolean checkWeb(String weburl) {
////		String cm = "^http://[\\w-\\.]+(?:/|(?:/[\\w\\.\\-]+)*(?:/[\\w\\.\\-]+\\.do))?$";
//        String cm = "^(https?://([\\w-]+\\.)+[\\w-]+([\\w-./?%&amp;#=]*)?)$";
//        if (weburl == null || "".equals(weburl)) {
//            return false;
//        }
//        if (Pattern.matches(cm, weburl)) {
//            return true;
//        }
//        return false;
//    }

    // 判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 验证身份证号码
     *
     * @param text
     * @return
     */
    public static boolean isIDcard(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 返回原型�?
     *
     * @param thumbnial
     * @return
     */
    public static String convertPrototype(String thumbnial) {
        try {
            if (null == thumbnial)
                return "";
            return (new StringBuilder())
                    .append(thumbnial.substring(0, thumbnial.lastIndexOf('.')))
                    .append("_prototype")
                    .append(thumbnial.substring(thumbnial.lastIndexOf('.')))
                    .toString();
        } catch (Exception e) {
            return thumbnial;
        }
    }

    /**
     * 验证QQ正则表达式(5-11位)
     */
    public static boolean checkQQ(String QQ) {
        String telRegex = "^([1-9][0-9]{4,10})$";
        if (Pattern.matches(telRegex, QQ)) {
            return true;
        }
        return false;
    }

    /**
     * 将多个对象进行组�?<br>
     *
     * @param parts
     * @return
     * @author wwy
     */
    // public static String assemblingString(Object... parts) {
    // StringBuilder builder = new StringBuilder();
    // if (CollectionUtil.isEmpty(parts)) {
    // return null;
    // }
    // for (Object part : parts) {
    // if(part == null){
    // part = Constant.EMPTY_STRING;
    // }
    // builder.append(part);
    // }
    // return builder.toString();
    // }

    /**
     * 转化时间字符�?
     * 类型：\/Date(1395396358000)\/
     */
    public static String date(String date) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？Date]";
        try {

            if (null == date || date.equals("")) {
                return "";
            } else {

                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(date);
                System.out.println(m.replaceAll(""));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String sd = sdf.format(new Date(Long.parseLong(m.replaceAll("")
                        .trim())));
                return sd;
            }
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 是否包含特殊字符
     */
    public static boolean containsAny(String str) {

        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#�?%…�??&*（）—�??+|{}【�?��?�；：�?��?��?��?�，、？]";

        // System.out.println("++++++++++++++++++++++++++++++++"+str.contains(regEx));
        if (str != null) {
            Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            return m.find();
        } else {
            return false;
        }

    }

    public static boolean isCardId(String str) {
        if (str != null && !str.equals("")) {
            Pattern idNumPattern = Pattern
                    .compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            Matcher idNumMatcher = idNumPattern.matcher(str);
            return idNumMatcher.matches();
        } else {
            return false;
        }

    }

    /**
     * 验证固定电话区号正则表达式(3-4位)
     */
    public static boolean checkquhao(String quhao) {
        String telRegex = "^(0[0-9]{2,3})$";
        if (Pattern.matches(telRegex, quhao)) {
            return true;
        }
        return false;
    }

    /**
     * 验证固定电话区号正则表达式(7-8位)
     */
    public static boolean checkguhua(String guhua) {
        String telRegex = "^([2-9][0-9]{6,7})$";
        if (Pattern.matches(telRegex, guhua)) {
            return true;
        }
        return false;
    }

    /**
     * 验证水电气正则表达式(7-10位)
     */
    public static boolean checksdq(String sdq) {
        String telRegex = "^((\\d){7,10})$";
        if (Pattern.matches(telRegex, sdq)) {
            return true;
        }
        return false;
    }

    /**
     * 半角转换为全�?
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)

                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 数据的显示格式处理
     *
     * @param s
     * @return
     */
    public static String spString(String s) {
        String[] splits = s.split("\\.");

        if (splits[1].length() == 1) {
            s = splits[0] + "." + splits[1] + "0";
        } else if (splits[1].length() == 3) {
            s = splits[0] + "." + splits[1].substring(0, 2);

        } else if (splits[1].length() == 2) {
            return s;

        } else {
            s = splits[0] + "." + splits[1].substring(0, 2);

        }
        return s;

    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

}
