package com.space.exchange.biz.common.util.java;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @author DTL
 * @description:
 * @date :2019-11-12 15:58
 */
public class StringUtil {

    /**
     * 将String首字母大写，in:deleteDate，out:DeleteDate
     */
    public static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }

    /**
     * 判断输入的字符串是否为中文
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 生成32位md5码
     *
     * @param string
     * @return
     */
    public static String md5Password(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */
    public static String getStarString(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    /**
     * 截取字符串 四舍五入
     */
    public static String subNumber(int rule, double price) {
        return String.format("%." + rule + "f", price);
    }

    /**
     * 截取字符串 不会进行四舍五入
     */
    public static String subNumber1(int rule, double price) {
        String result = "";
        try {
            BigDecimal bigDecimal = new BigDecimal(price);
            result = bigDecimal.setScale(rule, BigDecimal.ROUND_DOWN).toString();
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    //    截取字符串位数
    public static String subStringScaler(int i, BigDecimal bigDecimal) {
        String result = "";
        try {
            result = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toString();
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        if (str.toString().toUpperCase(Locale.getDefault()).equals("NULL")) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    //    按照规则截取字符串并在后面拼接...
    public static String subStringWithDot(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > length) {
                result = origin.substring(0, length) + "...";
            } else {
                result = origin;
            }
        }
        return result;
    }

    public static String subStringWithCenterDot(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > length) {
                result = origin.substring(0, 15) + "..." + origin.substring(16, length);
            } else {
                result = origin;
            }
        }
        return result;
    }

    public static String subStringNft(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > length) {
                result = origin.substring(0, 6);
            } else {
                result = origin;
            }
        }
        return result;
    }
    public static String subStringNft15(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > length) {
                result = origin.substring(0, 15);
            } else {
                result = origin;
            }
        }
        return result;
    }
    public static String subStringNftDot(int length, String origin) {
        String result = "";
        if (!TextUtils.isEmpty(origin)) {
            if (origin.length() > length) {
                result = origin.substring(0, 6) + "...";
            } else {
                result = origin;
            }
        }
        return result;
    }

    public static String getDefaultString(float number){
        String str = String.valueOf(number);
        if (str.contains("E") || str.contains("e")) {
            //  科学计数法
            return new BigDecimal(number).toString();
        } else {
            return new BigDecimal(str).toString();
        }
    }
}
