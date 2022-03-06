package com.unique.blockchain.nft.infrastructure.utils;
/**
 * 倒计时
 * */
public class CountTimeUtil {

    public static String formatLongToTimeStr(Long date) {
        long day = date / (60 * 60 * 24);
        long hour = (date / (60 * 60) - day * 24);
        long min = ((date / 60) - day * 24 * 60 - hour * 60);
        long s = (date - day*24*60*60 - hour*60*60 - min*60);
       String strtime = "剩余："+day+"天"+hour+"小时"+min+"分"+s+"秒";
        return strtime;
    }
    public static String formatLongToTimeStrNo(Long date) {
        long day = date / (60 * 60 * 24);
        long hour = (date / (60 * 60) - day * 24);
        long min = ((date / 60) - day * 24 * 60 - hour * 60);
        long s = (date - day*24*60*60 - hour*60*60 - min*60);
        String strtime = day+"天"+hour+"小时"+min+"分"+s+"秒";
        return strtime;
    }
}
