package com.space.exchange.biz.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dumingwei on 2018/6/3 0003.
 */
public class SpUtil {
    private static SharedPreferences sp = null;
    public static final String LANGUAGE = "language";
    private static final String SP_NAME = "poemTripSpref";
    private static SpUtil spUtil;
    private static SharedPreferences hmSpref;
    private static SharedPreferences.Editor editor;

    private SpUtil(Context context) {
        hmSpref = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = hmSpref.edit();
    }

    public static SpUtil getInstance(Context context) {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil(context);
                }
            }
        }
        return spUtil;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return hmSpref.getString(key,"");
    }
    /**
     * 将一个boolean值存入sp文件中
     * @param ctx 上下文
     * @param key 存储节点名称
     * @param value 存储节点的值
     */
    public static void putBoolean(Context ctx, String key, boolean value){
        //如果sp为空，则获取创建一个sp对象
        if(sp == null){
            sp = ctx.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();//获取sp编辑器,放入bool值，并提交

    }

    /**
     * 根据key读取一个boolean值value，没有的话使用defvalue代替
     * @param ctx
     * @param key
     * @param defvalue
     */
    public static boolean getBoolean(Context ctx, String key, boolean defvalue){
        //如果sp为空，则获取创建一个sp对象
        if(sp == null){
            sp = ctx.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        }
        boolean b = sp.getBoolean(key, defvalue);
        return b;

    }
}
