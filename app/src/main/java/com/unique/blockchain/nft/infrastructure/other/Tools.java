/**
 * @项目名称:友德医网络医院(Android个人端)
 * @(#)Tools.java Created on 2015-8-1
 * Copyright © 2015 深圳友德医科技有限公司  版权所有
 */
package com.unique.blockchain.nft.infrastructure.other;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class <code>Class Tools</code>. 一些常用的工具类
 *
 * @author Tonghu Lei
 * @version 1.0
 */
public final class Tools {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;

    /**
     * 手势密码
     */
    public static String PSASSWORD = "password";

    //身份证正面
    public static String IMG_64 = "id_card_img01";

    //身份证反面
    public static String IMG_64_three = "id_card_img02";

    /**
     * EventBean
     */
    public static String message = "222";
    public static String token = "token";
    public static String name = "name";
    public static String phone = "phone";
    public static String status = "status";
    public static String user_id = "userId";
    public static final String TAG = "Tools";

    /**
     * 验证邮箱输入是否合法
     *  
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public final static String isfirst = "IS_FIRST";

    /***
     *
     * @deprecated 该方法会造成快速点击功能无法使用 不建议使用
     * */
    public static void startActivity(Activity activity,
                                     Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /*
     * 获取当前程序的版本名
     */
    public static String getVersionName(Context context) throws Exception {
        String versionName = "";
        try {
            //获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            versionName = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 弹出Toast的提示
     *
     * @param mContext
     * @param resId
     */
    public static final void toast(Context mContext, String resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出带图片的Toast提示,并在屏幕中间提示
     *
     * @param mContext
     * @param resId
     * @param drawable
     */
    public static final void toast(Context mContext, String resId,
                                   Drawable drawable) {
        Toast toast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(mContext);
        imageView.setImageDrawable(drawable);
        toastView.addView(imageView, 0);
        toast.show();
    }

    /**
     * 弹出Toast,在屏幕中间显示
     *
     * @param mContext
     * @param resId
     */

    public static final void toastInCenter(Context mContext, String resId) {
        Toast toast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 弹出Toast的提示
     *
     * @param mContext
     * @param resId    string id
     */
    public static final void toast(Context mContext, int resId) {
        if (mContext != null)
            toast(mContext, mContext.getString(resId));
    }

    public static final void toast(Context mContext, int resId,
                                   Drawable drawable) {
        if (mContext != null) {
            toast(mContext, mContext.getString(resId), drawable);
        }
    }

    public static boolean editTextIsNullOrEmty(EditText str) {
        if (null == str || "".equals(str.getEditableText().toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断输入的字符串是否为中文
     *
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

    public static boolean isAllNum(String str, Context mContext) {
        Pattern p = Pattern.compile("^[0-9]{6,20}$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            Tools.toastInCenter(mContext, "密码不能全是数字，需至少数字或字母或符号组合");
            return true;
        } else {
            return false;
        }

    }

    public static boolean isAllChar(String str, Context mContext) {
        Pattern p = Pattern.compile("^[a-zA-Z]{6,20}$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            Tools.toastInCenter(mContext, "密码不能全是字母，需至少数字或字母或符号组合");
            return true;
        } else {
            return false;
        }

    }

    public static boolean isAllowHanzi(String str, Context mContext) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isChinese(c)) {
                Tools.toastInCenter(mContext, "密码不能含有汉字，需至少数字或字母或符号组合");
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNumChar(String str, Context mContext) {
        Pattern p = Pattern.compile("^[^0-9a-zA-Z]{6,20}$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            Tools.toastInCenter(mContext, "密码不能纯符号，需至少数字或字母或符号组合");
            return true;
        } else {
            return false;
        }
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


    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static String getHideEmail(String email) {
        if (email == null || TextUtils.isEmpty(email)) {
            return "";
        } else {
            String result = "";
            String[] split = email.split("@");
            if (split != null && split.length > 1) {
                String emailNum = split[0].toString();
                String resultEmail = getStarString(emailNum, 2, 0);
                result = resultEmail + "@" + split[1];
            }
            return result;
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
     * 跳转到文章详情
     *
     * @param articleId 文章id
     * @param context   上下文
     ***/
    public static void gotoArtcleDetail(Context context, String articleId) {
//        Intent intent = new Intent(context, TgContentDetailActivity.class);
//        intent.putExtra(TgContentDetailActivity.ARTICLE_ID, articleId);
//        context.startActivity(intent);
    }

    /**
     * 跳转到文章详情
     *
     * @param userid  用户id
     * @param context 上下文
     ***/
    public static void gotoPersonalPage(Context context, int userid) {
//        Intent intent = new Intent(context, PersonalHomepageActivity.class);
//        intent.putExtra("user_id", userid + "");
//        context.startActivity(intent);
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static boolean isPasswordNO(String password) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }


    public static void startDropAnimation(ImageView target) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, "rotation", 0f, -180f);
        rotation.setDuration(300);
        rotation.start();
    }

    public static void startUpAnimation(ImageView target) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, "rotation", -180f, 0);
        rotation.setDuration(300);
        rotation.start();
    }

    public static String subNumber(int rule, double price) {
        return String.format("%." + rule + "f", price);
    }

    //    截取字符串  该方法对于小数位少于截取位数的小数会出现精度问题(double精度问题 比如 1.00000300 会变换为1.00000299)
    public static String subNumber1(int rule, double price) {
        String result = "";
        try {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(price));
            result = bigDecimal.setScale(rule, BigDecimal.ROUND_DOWN).toPlainString();
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    //    截取字符串  该方法对于小数位少于截取位数的小数会出现精度问题
    public static String subNumber2(int rule, String price) {
        String result = "";
        try {
            BigDecimal bigDecimal = new BigDecimal(price);
            result = bigDecimal.setScale(rule, BigDecimal.ROUND_DOWN).toPlainString();
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    //    截取字符串位数
    public static String subStringScaler(int i, BigDecimal bigDecimal) {
        String result = "";
        try {
            result = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    public static String decimalPlace(double value) {

        DecimalFormat decimalFormat = new DecimalFormat("#0.00000000");
        return decimalFormat.format(value);
    }


    public static void setPagerMargin(Banner banner, int leftMargin, int rightMargin) {
        try {
            Class clazz = Banner.class;
            Field field = clazz.getDeclaredField("viewPager");
            field.setAccessible(true);
            ViewPager viewPager = (ViewPager) field.get(banner);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
            params.setMargins(leftMargin, 0, rightMargin, 0);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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


    public static String adaptTextUrl(String content) {
        return "<div id=\"content\">\n" +
                "    <style>\n" +
                "    #content{width:100%;overflow-x:hidden;font-size: 36px !important}\n" +
                "    #content p{font-size: 36px !important;margin: 0;}\n" +
                "    body{margin:0;padding:0 !important}\n" +
                "    img{width:100% !important;}\n" +
                "    </style>" + content + "</div>";
    }

//    public static void manageRefreshLayoutStatus(int page, SmartRefreshLayout refreshLayout, List info) {
//        if (refreshLayout != null) {
//            if (!CollectionsUtil.isListEmpty(info)) {
//                refreshLayout.finishRefresh();
//                refreshLayout.finishLoadMore();
//            } else {
//                if (1 == page) {
//                    refreshLayout.finishLoadMore();
//                } else {
//                    refreshLayout.finishLoadMoreWithNoMoreData();
//                }
//            }
//        }
//    }

    public static boolean notNullObject(@Nullable Object object) {
        if (null != object && !new Gson().toJson(object).equals("{}") && !new Gson().toJson(object).equals("[]")) {
            return true;
        }
        return false;
    }
}

