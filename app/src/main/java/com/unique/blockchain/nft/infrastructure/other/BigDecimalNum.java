package com.unique.blockchain.nft.infrastructure.other;

import java.math.BigDecimal;
/**
 * 保留6位小数点
 * */
public class BigDecimalNum {
    static StringBuilder stringBuilder;
    public static String  setDouble(String str){
        BigDecimal    b   =   new BigDecimal(str);
        double   f1   =   b.setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();
        String str1 = f1 + "";

        if(str1.substring(str1.indexOf(".")).length() == 1){
            stringBuilder =  new StringBuilder(str1).append("000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 2){
            stringBuilder =  new StringBuilder(str1).append("00000");
        }else if(str1.substring(str1.indexOf(".")).length() == 3){
            stringBuilder =   new StringBuilder(str1).append("0000");
        }else if(str1.substring(str1.indexOf(".")).length() == 4){
            stringBuilder =   new StringBuilder(str1).append("000");
        }else if(str1.substring(str1.indexOf(".")).length() == 5){
            stringBuilder =  new StringBuilder(str1).append("00");
        }else {
            stringBuilder = new StringBuilder(String.format("%.6f", Double.valueOf(str1)));
        }
        return stringBuilder.toString();
    }
    public static String  setDoubleEight(String str){
        BigDecimal    b   =   new BigDecimal(str);
        double   f1   =   b.setScale(18,   BigDecimal.ROUND_HALF_UP).doubleValue();
        String str1 = f1 + "";

        if(str1.substring(str1.indexOf(".")).length() == 1){
            stringBuilder =  new StringBuilder(str1).append("000000000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 2){
            stringBuilder =  new StringBuilder(str1).append("00000000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 3){
            stringBuilder =   new StringBuilder(str1).append("000000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 4){
            stringBuilder =   new StringBuilder(str1).append("00000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 5){
            stringBuilder =  new StringBuilder(str1).append("0000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 6){
            stringBuilder =  new StringBuilder(str1).append("000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 7){
            stringBuilder =  new StringBuilder(str1).append("00000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 8){
            stringBuilder =  new StringBuilder(str1).append("0000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 9){
            stringBuilder =  new StringBuilder(str1).append("000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 10){
            stringBuilder =  new StringBuilder(str1).append("00000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 11){
            stringBuilder =  new StringBuilder(str1).append("0000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 12){
            stringBuilder =  new StringBuilder(str1).append("000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 13){
            stringBuilder =  new StringBuilder(str1).append("00000");
        }else if(str1.substring(str1.indexOf(".")).length() == 14){
            stringBuilder =  new StringBuilder(str1).append("0000");
        }else if(str1.substring(str1.indexOf(".")).length() == 15){
            stringBuilder =  new StringBuilder(str1).append("000");
        }else if(str1.substring(str1.indexOf(".")).length() == 16){
            stringBuilder =  new StringBuilder(str1).append("00");
        }else if(str1.substring(str1.indexOf(".")).length() == 17){
            stringBuilder =  new StringBuilder(str1).append("0");
        }else {
            stringBuilder = new StringBuilder(String.format("%.6f", Double.valueOf(str1)));
        }
        return stringBuilder.toString();
    }
    public static String  setDoubleSix(String str){
        BigDecimal    b   =   new BigDecimal(str);
        double   f1   =   b.setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();
        String str1 = f1 + "";

        if(str1.substring(str1.indexOf(".")).length() == 1){
            stringBuilder =  new StringBuilder(str1).append("000000000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 2){
            stringBuilder =  new StringBuilder(str1).append("00000000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 3){
            stringBuilder =   new StringBuilder(str1).append("000000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 4){
            stringBuilder =   new StringBuilder(str1).append("00000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 5){
            stringBuilder =  new StringBuilder(str1).append("0000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 6){
            stringBuilder =  new StringBuilder(str1).append("000000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 7){
            stringBuilder =  new StringBuilder(str1).append("00000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 8){
            stringBuilder =  new StringBuilder(str1).append("0000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 9){
            stringBuilder =  new StringBuilder(str1).append("000000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 10){
            stringBuilder =  new StringBuilder(str1).append("00000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 11){
            stringBuilder =  new StringBuilder(str1).append("0000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 12){
            stringBuilder =  new StringBuilder(str1).append("000000");
        }else if(str1.substring(str1.indexOf(".")).length() == 13){
            stringBuilder =  new StringBuilder(str1).append("00000");
        }else if(str1.substring(str1.indexOf(".")).length() == 14){
            stringBuilder =  new StringBuilder(str1).append("0000");
        }else if(str1.substring(str1.indexOf(".")).length() == 15){
            stringBuilder =  new StringBuilder(str1).append("000");
        }else if(str1.substring(str1.indexOf(".")).length() == 16){
            stringBuilder =  new StringBuilder(str1).append("00");
        }else if(str1.substring(str1.indexOf(".")).length() == 17){
            stringBuilder =  new StringBuilder(str1).append("0");
        }else {
            stringBuilder = new StringBuilder(String.format("%.6f", Double.valueOf(str1)));
        }
        return stringBuilder.toString();
    }
    public static void  setDecimal(String str) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
        df.format(str);

    }

}
