package com.space.exchange.biz.common.util.java;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    public static String getStanderTime() {
        Date date = new Date();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-DD  HH:mm");
        String format = form.format(date);
        return format;
    }

    /*
     *   获取挡墙时间戳
     * */
    public static String getTimeStamp() {
        String timeStamp = System.currentTimeMillis() + "";
        if (timeStamp.length() > 10) {
            return timeStamp.substring(0, 10);
        }
        return timeStamp;
    }

    /**
     * 时间戳转字符串
     */
    public static String getStringTime(long fromTimestamp, long endTimestamp) {

        SimpleDateFormat formTime = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        SimpleDateFormat toTime = new SimpleDateFormat("MM-dd  HH:mm");
        Date date1 = new Date(fromTimestamp * 1000);
        Date date2 = new Date(endTimestamp * 1000);

        return formTime.format(date1) + " — " + toTime.format(date2);
    }

    /**
     * 时间差转中文字符串
     */
    public static String getFormatTime(long timestamp) {
        StringBuffer sb = new StringBuffer();
        long hour = timestamp / 3600;
        long minute = (timestamp - hour * 3600) / 60;
        long second = timestamp - hour * 3600 - minute * 60;
        sb.append(hour);
        sb.append("小时");
        if (minute < 10 && minute >= 0) {
            sb.append(0);
        }
        sb.append(minute);
        sb.append("分钟");
        if (second < 10 && second >= 0) {
            sb.append(0);
        }
        sb.append(second);
        sb.append("秒");
        return sb.toString();
    }

    public static String fomatTime(long second) {

        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            long minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        long hour = second / 3600;
        long minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + ":" + minute + ":0" + second;
            }
            return "0" + hour + ":" + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + ":" + minute + ":0" + second;
        }
        return hour + ":" + minute + ":" + second;
    }

    public static String getStanderTimeBydate(Date date) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String format = form.format(date);
        return format;
    }
    public static String getStanderTimeBydate(String date) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd  HH:mm");

        String format = form.format(new Date(date));
        return format;
    }
    public static String getStanderTimeBydate1(Date date) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String format = form.format(date);
        return format;
    }

    public static String getMonthTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getHourMinute(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String format = simpleDateFormat.format(date);
        return format;
    }

    //    获取月份
    public static String getCurrentMonth() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.MONTH) + 1 + "";
    }

    //    获取当前几号
    public static String getCurrentMonthDay() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.DAY_OF_MONTH);
        return i + "";
    }
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            currentTime = formatter.parse(sformat);
            String dateString = formatter.format(currentTime);
            return dateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;

    }

    /**
     *
     * 将yyyy-MM-dd HH:mm:ss 转换为 yyyy-MM-dd
     * **/
    public static String getMonthDay(String time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(time, pos);
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String format = form.format(date);
        return format;
    }
    public static String getWeekByPosition(int position) {
        String week = "星期一";
        switch (position) {
            case 1:
                week = "星期天";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }
}
