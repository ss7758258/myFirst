package com.xz.web.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public final static String limitDate = "12/30/2099";

    public static String getToday12() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
    }

    public static String getCurrentTimestamp() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    public static String getCurrentTimestampSSS() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS")).format(date);
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getToday10() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    public static String getDateBeforeMonth(int months) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -months);//1月份前
        return sdf.format(c.getTime());
    }

    public static int getTimeDiff(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            int diff = (int) (d1.getTime() - d2.getTime());
            int mins = diff / (1000 * 60);
            return Math.abs(mins);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getTimeDiffSSS(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            int diff = (int) (d1.getTime() - d2.getTime());
            return Math.abs(diff);
        } catch (Exception e) {
            return -1;
        }
    }

    //"yyyy-MM-dd HH:mm:ss"
    public static int compareTime(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            if ((d1.getTime() - d2.getTime()) < 0) {
                return -1;
            } else {
                int diff = (int) (d1.getTime() - d2.getTime());
                int mins = diff / (1000 * 60);
                return mins;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static String getNowYearMonth() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy-MM")).format(date);
    }

    public static String getMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        firstDay += " 00:00:00";
        return firstDay;
    }

    public static String getMonthLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));// 设置为1号,当前日期既为本月第一天
        String lastDay = format.format(calendar.getTime());
        lastDay += " 23:59:59";
        return lastDay;
    }

    public static void main(String[] args) {
        //DateUtil dateUtil = new DateUtil();
        String s = DateUtil.getDateBeforeMonth(12);
        int diff = DateUtil.getTimeDiff("2004-03-26 13:30:40", "2004-03-26 13:31:40");
        System.out.println(s);
        System.out.println(diff);
//		System.out.println(DateUtil.getCurrentTimestamp());
//		System.out.println(DateUtil.getNowYearMonth());
//		System.out.println(dateUtil.getCurrentTimestamp());
//		System.out.println(dateUtil.getDateBeforeMin(1));
//		System.out.println(dateUtil.getDateBeforeHour(1));
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    public String getToday() {
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance();
        String s = df.format(date);
        return s;
    }

    public String deleteChar(String str, char del) {
        String s = "";
        for (int i = 0; i < s.length(); i++)
            if (del != str.charAt(i))
                s = s + str.charAt(i);
        return s;
    }

    public String getToday8() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyyMMdd")).format(date);
    }

    public String getTodayString() {
        return this.getToday8();
    }

    public String getToday19() {
        Date date = new Date();
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    public String getLastWeek10() {
        Date date = new Date();
        date.setDate(date.getDate() - 7);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    /**
     * 获取下一周
     *
     * @return
     */
    public String getNextWeek10() {
        Date date = new Date();
        date.setDate(date.getDate() + 7);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    public String getDateBeforeMin(int mins) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -mins);//1分钟前
        return sdf.format(c.getTime());
    }

    public String getDateBeforeHour(int hour) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -hour);//1小时前
        return sdf.format(c.getTime());
    }

    public Date str2datetime(String datetimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return sdf.parse(datetimeStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将传进来的字符串时间格式变为yyyy-MM
     *
     * @param datetimeStr
     * @return
     */
    public String str3datetime(String datetimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Date time = sdf.parse(datetimeStr);
            return sdf.format(time).toString();
        } catch (ParseException e) {
            return null;
        }
    }

    public String getBeforWeek10() {
        Date date = new Date();
        date.setDate(date.getDate() + 7);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }


}