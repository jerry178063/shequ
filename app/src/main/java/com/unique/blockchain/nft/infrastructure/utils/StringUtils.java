package com.unique.blockchain.nft.infrastructure.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    public static boolean rexCheckPassword(String input) {
        // 6-15 位，字母、数字、字符
//        String regStr = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{6,15}$";
        String regStr = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,15}";
//        String regStr = "\n" +
//                "17:17\n" +
//                "([a-zA-Z]+[0-9]+[!@#$%^&,.*]+)|([a-zA-Z]+[!@#$%^&,.*]+[0-9]+)|([0-9]+[!@#$%^&,.*]+[a-zA-Z]+)|([0-9]+[a-zA-Z]+[!@#$%^&,.*]+)|([!@#$%^&,.*]+[a-zA-Z]+[0-9]+)|([!@#$%^&,.*]+[0-9]+[a-zA-Z]{6,15}+$)";

        return input.matches(regStr);
    }

    public static void  CopyText(Activity context,String text){
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }




    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern;
            pattern = Pattern.compile("\t|\r|\n");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }
    public static boolean isContainNumber(String company) {

        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 四舍五入算法
     *
     * @param str   输入值
     * @param scale 精度,保留几位小数 0, 1 ,2 , 3.....
     * @return 返回值
     */
    public static double roundHalfUp(String str, int scale) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        if (!isNumeric(str)) {
            return 0;
        }
        BigDecimal decimal = new BigDecimal(str);
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static boolean isNumeric(String str){

        for (int i = str.length();--i>=0;){

            if (!Character.isDigit(str.charAt(i))){

                return false;

            }

        }

        return true;

    }
    public static double formatDouble2(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        double bg = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return bg;
    }

    public static String subStringWithCenterDot(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > 25) {
                result = origin.substring(0, 15) + "..." + origin.substring(length-10, length);
            } else {
                result = origin;
            }
        }
        return result;
    }
    public static String subStringWithCenter2Dot(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > 18) {
                result = origin.substring(0, 10) + "..." + origin.substring(length-8, length);
            } else {
                result = origin;
            }
        }
        return result;
    }
    public static String subStringWithEndDot(String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > 25) {
                result = origin.substring(0, 9) + "...";
            } else {
                result = origin;
            }
        }
        return result;
    }
    public static String subStringWithEnd14Dot(String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > 14) {
                result = origin.substring(0, 15) + "...";
            } else {
                result = origin;
            }
        }
        return result;
    }
}
