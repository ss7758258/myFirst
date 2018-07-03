package com.xz.framework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //获取当前时间到7天前的时间，返回值为：yyyy-MM-dd HH:mm:ss
    public static String getPrevWeekDateTime() {
        String currentDateTime = DateUtil.getDatetime();
        return DateUtil.getPrevDateByMins(currentDateTime, 7 * 24 * 60);
    }

    //获取当前时间到30天前的时间，返回值为：yyyy-MM-dd HH:mm:ss
    public static String getPrevMonthDateTime() {
        String currentDateTime = DateUtil.getDatetime();
        return DateUtil.getPrevDateByMins(currentDateTime, 30 * 24 * 60);
    }

    //获取当前时间到半年前的时间，返回值为：yyyy-MM-dd HH:mm:ss
    public static String getPrevHalfYearDateTime() {
        String currentDateTime = DateUtil.getDatetime();
        return DateUtil.getPrevDateByMins(currentDateTime, 6 * 30 * 24 * 60);
    }

    /**
     * 输入时间点与分钟数得到上一时间点的分钟数
     *
     * @param inputDate 时间参数 1 格式：1990-01-01 12:00:00
     * @param mins      分钟参数 2 格式：mm
     * @return String 返回值为：yyyy-MM-dd HH:mm:ss
     */
    public static String getPrevDateByMins(String inputDate, int mins) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MINUTE, -mins);
        return sdf.format(ca.getTime());
    }

    public static String stringDateToDateFormat2(String string) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
        String str = "";
        try {
            Date data = format1.parse(string);
            str = format2.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static Date stringDateToDate(String string) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String stringDateToDateFormat(String string) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        String str = "";
        try {
            Date data = format1.parse(string);
            str = format2.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
	/*public static Date getTime() {
		return new Date();
	}*/

    public static String getDateToString8(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static int getTimeDiffSeconds(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            if ((d1.getTime() - d2.getTime()) < 0) {
                return -1;
            } else {
                int diff = (int) (d1.getTime() - d2.getTime());
                int seconds = diff / (1000);
                return seconds;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 得到毫秒级别差
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long getTimeDifMilliseconds(Date d1, Date d2) {
        long diff = -1;
        try {
            diff = (d1.getTime() - d2.getTime());
        } catch (Exception e) {
        }
        return diff;
    }


    public static long getTimeDiffSeconds(Date d1, Date d2) {
        long diff = -1;
        try {
            diff = (d1.getTime() - d2.getTime()) / (1000 * 60);//这样得到的差值是微秒级别
        } catch (Exception e) {
        }
        return diff;
    }

    public static long getTimeDiffMins(Date d1, Date d2) {
        long diff = -1;
        try {
            diff = (d1.getTime() - d2.getTime()) / (1000 * 3600);//这样得到的差值是微秒级别
        } catch (Exception e) {
        }
        return diff;
    }

    public static long getTimeDiffMins(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = -1;
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            diff = (d1.getTime() - d2.getTime()) / (1000 * 3600);//这样得到的差值是微秒级别
        } catch (Exception e) {
        }
        return diff;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }
}
