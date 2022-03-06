package com.unique.blockchain.nft.infrastructure.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.unique.blockchain.nft.MyApplication;

import java.util.Locale;

public class LocalUtils {
    public static final String LOCAL_DEFAULT_LANGUAGE = "LOCAL_DEFAULT_LANGUAGE";
    public static final String LOCAL_SIMPLE_CHINESE_LANGUAGE = "CN";
    public static final String LOCAL_TRADITIONAL_CHINESE_LANGUAGE = "TW";
    public static final String LOCAL_ENGLISH_LANGUAGE = "EN";
    private static final String TAG = LocalUtils.class.getSimpleName();
    private static boolean isCommit = false;


    /***
     * 设置语言环境
     * @param act
     * @param loc 比如，Locale.ENGLISH
     */
    public static void setLanguage(Activity act, Locale loc) {
        if (act == null) {
            throw new NullPointerException("activity cannot be null");
        }

        Resources res = act.getApplication().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = act.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(loc);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
        } else {
            config.locale = loc;
        }
        res.updateConfiguration(config, dm);
    }

    /****
     * 获取 SharedPreferences 保存的语言环境标识
     * @return 保存的语言环境标识
     */
    public static String getLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getString(LOCAL_DEFAULT_LANGUAGE, "TW");
    }

    /***
     * 保存语言标识
     * @param language
     * @return
     */
    public static boolean saveLanguage(String language) {

        /***
         * 通过sharepreference保存标识，
         * 中文-LOCAL_SIMPLE_CHINESE_LANGUAGE，
         * 英文-LOCAL_ENGLISH_LANGUAGE
         * 繁体-LOCAL_TRADITIONAL_CHINESE_LANGUAGE
         */
        if (language.equals(LOCAL_SIMPLE_CHINESE_LANGUAGE)) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(LocalUtils.LOCAL_DEFAULT_LANGUAGE);
            editor.putString(LocalUtils.LOCAL_DEFAULT_LANGUAGE, LOCAL_SIMPLE_CHINESE_LANGUAGE);
            isCommit = editor.commit();
        } else if (language.equals(LocalUtils.LOCAL_ENGLISH_LANGUAGE)) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(LocalUtils.LOCAL_DEFAULT_LANGUAGE);
            editor.putString(LocalUtils.LOCAL_DEFAULT_LANGUAGE, LOCAL_ENGLISH_LANGUAGE);
            isCommit = editor.commit();
        } else if (language.equals(LOCAL_TRADITIONAL_CHINESE_LANGUAGE)) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(LocalUtils.LOCAL_DEFAULT_LANGUAGE);
            editor.putString(LocalUtils.LOCAL_DEFAULT_LANGUAGE, LOCAL_TRADITIONAL_CHINESE_LANGUAGE);
            isCommit = editor.commit();
        }

        Log.e(TAG, language);
        return isCommit;
    }
}
