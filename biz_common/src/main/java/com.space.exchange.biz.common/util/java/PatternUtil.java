package com.space.exchange.biz.common.util.java;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DTL
 * @description: 正则工具类
 * @date :2019-11-12 15:10
 */
public class PatternUtil {

    /**
     * 验证邮箱输入是否合法
     *
     * @param strEmail 邮箱
     * @return true是邮箱 false不是
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证号码 手机号 固话均可 400 800也可
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "(((400)|(800)?-(\\d{3})?-(\\d{4}))|(^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断输入的string是否为全数字
     *
     * @param str 要判断的string
     * @return true 全数字 false 不是
     */
    public static boolean isAllNum(String str) {
        Pattern p = Pattern.compile("^[0-9]{6,20}$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断输入的string是否为全字母
     *
     * @param str 要判断的string
     * @return true 全字母 false 不是
     */
    public static boolean isAllChar(String str, Context mContext) {
        Pattern p = Pattern.compile("^[a-zA-Z]{6,20}$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }

    }

//    public static boolean isAllowHanzi(String str, Context mContext) {
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//            if (isChinese(c)) {
//                Tools.toastInCenter(mContext, "密码不能含有汉字，需至少数字或字母或符号组合");
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * 判断输入的string是否为全符号
     *
     * @param str 要判断的string
     * @return true 全符号 false 不是
     */
    public static boolean isNotNumChar(String str, Context mContext) {
        Pattern p = Pattern.compile("^[^0-9a-zA-Z]{6,20}$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
