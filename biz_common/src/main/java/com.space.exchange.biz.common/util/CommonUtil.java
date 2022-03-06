package com.space.exchange.biz.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.space.exchange.biz.common.GlobalField;
import com.space.exchange.biz.common.R;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: Evan
 * Date: 2019/6/5
 */
public class CommonUtil {

    /*
     *   为adapter设置通用EmptyView
     * */
    public static void setEmptyView(Context context, BaseQuickAdapter adapter, String str) {
        if (adapter == null) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.empty_layout, null);
        if (!TextUtils.isEmpty(str)) {
            TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
            tv_empty.setText(str);
        }
        adapter.setEmptyView(view);
    }

    /*
     *   为adapter设置通用EmptyView  白色背景
     * */
    public static void setWhiteEmptyView(Context context, BaseQuickAdapter adapter, String str) {
        if (adapter == null) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.fabi_empty_layout, null);
        if (!TextUtils.isEmpty(str)) {
            TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
            tv_empty.setText(str);
        }
        adapter.setEmptyView(view);
    }

    /*
     *   为adapter设置通用EmptyView,适用于嵌套列表
     * */
    public static void setEmptyView2(Context context, BaseQuickAdapter adapter, String str) {
        if (adapter == null) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.empty_layout2, null);
        if (!TextUtils.isEmpty(str)) {
            TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
            tv_empty.setText(str);
        }
        adapter.setEmptyView(view);
    }

    //	base64加密
// 加密
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = Base64.encodeToString(b, Base64.NO_WRAP).toString();
            s = s.replace("+", "-");
            s = s.replace("/", "_");
//		s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public static String stringToSign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(
                    GlobalField.SECRET_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            return Base64.encodeToString(mac.doFinal(data.getBytes()), Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            Log.e("CommonUtil", "Hash algorithm SHA-1 is not supported", e);
        } catch (UnsupportedEncodingException e) {
            Log.e("CommonUtil", "Encoding UTF-8 is not supported", e);
        } catch (InvalidKeyException e) {
            Log.e("CommonUtil", "Invalid key", e);
        }
        return "";
    }


    /*
     *   为adapter设置通用EmptyView,适用于嵌套列表
     * */
    public static void setEmptyView3(Context context, BaseQuickAdapter adapter, String str, int resid) {
        if (adapter == null) {
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.empty_layout3, null);
        if (!TextUtils.isEmpty(str)) {
            TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
            tv_empty.setText(str);
        }

        if(resid > 0){
            ImageView iv_empty = (ImageView) view.findViewById(R.id.iv_empty);
            iv_empty.setImageResource(resid);
        }

        adapter.setEmptyView(view);
    }

    public static String getStringFromAssert(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return new String(buffer, 0, buffer.length, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 毫秒数转化为日期格式1
     * */
    public static String getDateFromSeconds1(String seconds){
        if(seconds==null)
            return " ";
        else{
            Date date=new Date();
            try{
                if(seconds.length() == 10){
                    date.setTime(Long.parseLong(seconds) * 1000);
                } else {
                    date.setTime(Long.parseLong(seconds));
                }
            }catch(NumberFormatException nfe){

            }
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }

    /**
     * 毫秒数转化为日期格式2
     * */
    public static String getDateFromSeconds2(String seconds){
        if(seconds==null)
            return " ";
        else{
            Date date=new Date();
            try{
                if(seconds.length() == 10){
                    date.setTime(Long.parseLong(seconds) * 1000);
                } else {
                    date.setTime(Long.parseLong(seconds));
                }
            }catch(NumberFormatException nfe){

            }
            SimpleDateFormat sdf= new SimpleDateFormat("MM-dd HH:mm");
            return sdf.format(date);
        }
    }

    /**
     * 秒数转化为日期格式1
     * */
    public static String getDateFromSeconds3(String seconds){
        if(seconds==null)
            return " ";
        else{
            Date date=new Date();
            try{
                date.setTime(Long.parseLong(seconds) * 1000);
            }catch(NumberFormatException nfe){

            }
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }

    /**
     * 秒数转化为日期格式2
     * */
    public static String getDateFromSeconds4(String seconds){
        if(seconds==null)
            return " ";
        else{
            Date date=new Date();
            try{
                date.setTime(Long.parseLong(seconds)*1000);
            }catch(NumberFormatException nfe){

            }
            SimpleDateFormat sdf= new SimpleDateFormat("MM-dd HH:mm");
            return sdf.format(date);
        }
    }
    /**
     * 秒数转化为日期格式2
     * */
    public static String getDateFromSeconds5(String seconds){
        if(seconds==null)
            return " ";
        else{
            Date date=new Date();
            try{
                date.setTime(Long.parseLong(seconds)*1000);
            }catch(NumberFormatException nfe){

            }
            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
            return sdf.format(date);
        }
    }
    public static boolean isListEmpty(List infos) {
        if (infos == null || infos.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号号段校验，
     第1位：1；
     第2位：{3、4、5、6、7、8}任意数字；
     第3—11位：0—9任意数字
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /***
     * 邮箱的正则
     * */
    public static boolean isEmailAccount(String email){
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    /*
    *   设置沉浸式状态栏
    *   type 1状态栏文字白色  2状态栏文字黑色
    * */
    public static void setStatusBarColor(Activity act, int type){
        Window window = act.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusBarTextColor(act, type);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /*
     *   设置沉浸式状态栏
     *   type 1状态栏文字白色  2状态栏文字黑色
     * */
    public static void setStatusBarColor(Activity act, int type, int color){


        Window window = act.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusBarTextColor(act, type);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public static void setStatusBarTextColor(Activity act, int type){
        Window window = act.getWindow();
        if(type == 1){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
        } else if(type == 2){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
