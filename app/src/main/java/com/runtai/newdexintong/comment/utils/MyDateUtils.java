package com.runtai.newdexintong.comment.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MyDateUtils {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD2 = "yyyy-MM-dd";
    public static final String YYYY_MM_DD = "yyyy.MM.dd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd_HH_mm_ss";
    public static final String YYYY_MM_DD_HH_MM_SS1 = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 日期统一格式
     */
    private final static SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static Date getCurrentDateDay() {

        Date d = new Date(System.currentTimeMillis());
        String format = format(d, YYYYMMDD);

        return getString2Date(format);

    }

    /**
     * 获取时间
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getCurrentStringDateDay(long milliseconds, String pattern) {

        Date d = new Date(milliseconds);
        return format(d, pattern);

    }

    public static String getTodayTime() {
        Date d = new Date(System.currentTimeMillis());
        return format(d, YYYYMMDD2);
    }

    /**
     * 获取当前时间的时分秒
     *
     * @param currentDate
     * @return
     */
    public static String getNextDateTime(Date currentDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, +1); //今天的时间加一天
        return format(calendar.getTime(), YYYYMMDD2);
    }

    /**
     * 获取当前时间的下一天
     *
     * @param currentDate
     * @return
     */
    public static Date getNextDate(Date currentDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, +1); //今天的时间加一天
        return calendar.getTime();
    }

    /**
     * 获取当前日期六天前的时间
     *
     * @return
     */
    public static String getSixDaysBefore() {
        //获取7天以前时间
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD2);
        Date date = addDays(new Date(), -6);
        str = sdf.format(date.getTime());
        return str;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }


    /**
     * 获取当前日期六天前的时间
     *
     * @return
     */
    public static Date getSixDaysBeforeDate() {
        return addDays(new Date(), -6);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 将含有特殊字符的字符串转为指定格式的时间
     *
     * @param str
     * @return
     */
    public static String getStringDateDay(String str) {

        String newStr = str.replace("/Date(", "").replace(")/", "");
        Date d = new Date(Long.parseLong(newStr));
        return format(d, YYYY_MM_DD_HH_MM_SS1);

    }

    /**
     * 获取当前时间的毫秒值
     *
     * @return
     */
    public static String getCurrentMillisecond() {
        return String.valueOf(new Date().getTime());
    }


    public static String getCurrentStringDay() {
        Date d = new Date(System.currentTimeMillis());
        return format(d, YYYYMMDD);
    }

    public static String getCurrentStringMonth() {
        Date d = new Date(System.currentTimeMillis());
        return format(d, YYYYMM);
    }

    public static String getCurrentStringDay(String format) {

        Date d = new Date(System.currentTimeMillis());
        return format(d, format);
    }


    public static String getFormatCurrentTimeTime() {

        Date d = new Date(System.currentTimeMillis());
        String format = format(d, YYYY_MM_DD_HH_MM_SS1);

        return format;
    }

    public static String getFormatCurrentTimeTime2() {

        Date d = new Date(System.currentTimeMillis());
        String format = format(d, YYYY_MM_DD_HH_MM_SS);

        return format;
    }

    public static String getFormatCurrentTimeTime3() {

        Date d = new Date(System.currentTimeMillis());
        String format = format(d, YYYYMMDDHHMMSS);

        return format;
    }

    public static String format(Date date, String pattern) {
        String dateString = "";
        if (date != null && pattern != null) {
            try {
                dateString = new SimpleDateFormat(pattern).format(date);
            } catch (IllegalArgumentException ex) {
            }
        }
        return dateString;
    }

    /**
     * 两个时间的间隔天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        long time1 = startDate.getTime();
        long time2 = endDate.getTime();
        long time = time2 - time1;

        long l = time / (1000 * 60 * 60 * 24);

        return Integer.parseInt(String.valueOf(l));

    }

    public static final long MM = 1000 * 60;
    public static final long HH = MM * 60;//小时
    public static final long DD = HH * 24;//一天
    public static final long MO = DD * 30;//一月
    public static final long YY = 365 * DD;//一年

    public static String getTimeAfterToNow(String tiem) {

        if (TextUtils.isEmpty(tiem)) {
            return "";
        }

        String t = "";

        long newDate = System.currentTimeMillis();

        long oldDate = getDateOfLong("yyyy-MM-dd HH:mm", tiem);

        if (newDate < oldDate) {
            return t;
        }

        long gap = newDate - oldDate;

        if (gap < HH) {//一小时内,49分钟前

            long lt = gap / MM;

            if (lt == 0) {
                t = "刚刚";
            } else {
                t = lt + "分钟前";
            }

        } else if (gap >= HH && gap < DD) {//一天内,2小时前
            long lt = gap / HH;

            t = lt + "小时前";

        } else if (gap >= DD && gap < MO) {//一个月内
            long lt = gap / DD;
            t = lt + "天前";

        } else if (gap >= MO && gap < YY) {//一年内
            long lt = gap / MO;
            t = lt + "个月前";

        } else if (gap >= YY) {//一年后
            long lt = gap / YY;
            t = lt + "年前";
        } else {
            t = "--";
        }

        return t;
    }

    /**
     * 获取指定时间半个小时后的时间
     *
     * @param
     * @return
     */
    public static String getAfterHalfHourTime(String currentTime) {
        long dateOfLong = getDateOfLong(YYYY_MM_DD_HH_MM_SS1, currentTime);
        long time = 30 * 60 * 1000;//30分钟对应的毫秒值
        return format.format(new Date(dateOfLong + time));
    }


    /**
     * 将日期转为毫米值
     *
     * @param formate
     * @param sDate
     * @return
     */
    public static long getDateOfLong(String formate, String sDate) {

        SimpleDateFormat df = new SimpleDateFormat(formate);

        Date date = null;

        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long time = date.getTime();

        return time;
    }


    public static Date getDateOfLater(String sDate, int dayCount) {
        SimpleDateFormat df = new SimpleDateFormat(YYYYMMDD);

        Date date = null;

        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return getDateOfLater(date, dayCount);
    }

    public static String getDateOfLaterString(String sDate, int dayCount) {

        SimpleDateFormat df = new SimpleDateFormat(YYYYMMDD);
        Date date = null;

        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return getDate2String(getDateOfLater(date, dayCount));
    }


    public static String getMonthOfLaterString(String sDate, int monthCount) {

        SimpleDateFormat df = new SimpleDateFormat(YYYYMM);
        Date date = null;

        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return getDate2String(getMonthOfLater(date, monthCount), YYYYMM);
    }

    public static Date getDateOfLater(Date date, int dayCount) {

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);

        calendar.add(calendar.DATE, dayCount);

        return calendar.getTime();

    }


    public static Date getMonthOfLater(Date date, int monthCount) {

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);

        calendar.add(calendar.MONTH, monthCount);

        return calendar.getTime();

    }

    public static String date2String(Date date, String pattern) {

        String dateString = "";

        if (date != null && pattern != null) {

            try {

                dateString = new SimpleDateFormat(pattern).format(date);

            } catch (IllegalArgumentException ex) {

            }

        }

        return dateString;

    }

    public static String getDate2String(Date date) {

        SimpleDateFormat df = new SimpleDateFormat(YYYYMMDD);
        return df.format(date);
    }

    public static String getDate2String(Date date, String format) {

        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String getDate2String(String date) {
        if (date == null) {
            return "";
        }
        while (date.length() < 13) {
            date = date + "0";
        }

        long parseLong = Long.parseLong(date);

        return getDate2String(parseLong, "yyyy年MM月dd日 HH:mm");
    }

    public static String getDate2String(long date, String format) {
        Date d = new Date(date);
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    public static Date getString2Date(String sDate) {

        SimpleDateFormat df = new SimpleDateFormat(YYYYMMDD);
        Date date = null;

        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static String getStringDate2StringFormat(String sDate, String oldformat) {

        String newStringDate = "";

        Date string2Date = getString2Date(sDate, oldformat);

        if (string2Date != null) {

            newStringDate = getDate2String(string2Date, "yyyy年MM月dd日");
        }
        return newStringDate;

    }

    public static String getStringDate2StringFormat(String sDate, String oldformat, String newformat) {

        String newStringDate = "";

        Date string2Date = getString2Date(sDate, oldformat);

        if (string2Date != null) {

            newStringDate = getDate2String(string2Date, newformat);
        }
        return newStringDate;
    }

    public static Date getString2Date(String sDate, String format) {

        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static String getStringDateByYMH(int iyear, int imonth, int iday) {

        String year = String.valueOf(iyear);

        String month = String.valueOf(imonth);
        String day = String.valueOf(iday);

        if (imonth < 10) {
            month = "0" + month;
        }

        if (iday < 10) {
            day = "0" + day;
        }

        String date = year + month + day;

        return date;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取当前日期是几月<br>
     *
     * @param dt
     */
    public static String getMonthOfDate(Date dt) {
        String[] month = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.MONTH);
        if (w < 0)
            w = 0;
        return month[w];
    }

    /**
     * 获取下一秒的时间
     *
     * @param currentDate
     * @return
     */
    public static String getDateAddOneSecond(String currentDate) {

        String nextSecondDate = "";

        if (currentDate != null && !currentDate.equals("")) {

            try {
                Date date = format.parse(currentDate); // 将当前时间格式化
                // System.out.println("front:" + format.format(date)); //
                // 显示输入的日期
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.SECOND, 1); // 当前时间加1秒
                date = cal.getTime();
                // System.out.println("after:" + format.format(date));
                nextSecondDate = format.format(date); // 加一秒后的时间
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return nextSecondDate;
    }

    /**
     * 获取剩余时间 几天几时几分几秒
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getRemainTime(String startTime, String endTime) {

        String remainTime = "0"; // 剩余时间

        long dayMsec = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long hourMsec = 1000 * 60 * 60;// 一小时的毫秒数
        long minuteMsec = 1000 * 60;// 一分钟的毫秒数
        long secondMsec = 1000;// 一秒钟的毫秒数
        long diffMsec; // 毫秒差

        if (startTime != null && !startTime.equals("") && endTime != null
                && !endTime.equals("")) {
            try {
                // 获得两个时间的毫秒时间差异
                diffMsec = format.parse(endTime).getTime()
                        - format.parse(startTime).getTime();
                if (diffMsec > 0) {
                    /*判断结束时间是否大于开始时间*/
                    long diffDay = diffMsec / dayMsec;// 计算差多少天
                    long diffHour = diffMsec % dayMsec / hourMsec;// 计算差多少小时
                    long diffMin = diffMsec % dayMsec % hourMsec / minuteMsec;// 计算差多少分钟
                    long diffSec = diffMsec % dayMsec % dayMsec % minuteMsec
                            / secondMsec;// 计算差多少秒//输出结果
                    remainTime = diffDay + "天" + diffHour + "时" + diffMin + "分"
                            + diffSec + "秒";
                }

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return remainTime;
    }

    /**
     * 格式化日期格式
     *
     * @param dateTimeString
     * @return
     */
    public static String formatDateType(String dateTimeString) {

        String formatAfterDateTimeString = "";
        // System.out.println(dateTimeString);

        if (dateTimeString != null && !dateTimeString.equals("")) {
            /* 判断字符串是否有值 */
            formatAfterDateTimeString = dateTimeString;

            if (formatAfterDateTimeString.contains("/")) {
                /* 判断日期中是否包含'/' */
                formatAfterDateTimeString = formatAfterDateTimeString.replace(
                        "/", "-");
            }

            if ((formatAfterDateTimeString.lastIndexOf("-") - formatAfterDateTimeString
                    .indexOf("-")) == 2) {
                /* 判断月份格式是否是MM格式 */
                String frontSubString = formatAfterDateTimeString.substring(0,
                        formatAfterDateTimeString.indexOf("-") + 1);
                String afterSubString = "0" + formatAfterDateTimeString.substring(
                        formatAfterDateTimeString.indexOf("-") + 1,
                        formatAfterDateTimeString.length());

                formatAfterDateTimeString = frontSubString + afterSubString; //拼接字符串
            }
        }
        return formatAfterDateTimeString;
    }

}
