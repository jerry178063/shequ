package com.space.exchange.biz.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SPUtils {

    private static SharedPreferences sp = null;
    public static final String FILE_NAME = "config";
    /**
     * 将一个boolean值存入sp文件中
     * @param ctx 上下文
     * @param key 存储节点名称
     * @param value 存储节点的值
     */
    public static void putBoolean(Context ctx, String key, boolean value){
        //如果sp为空，则获取创建一个sp对象
        if(sp == null){
            sp = ctx.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
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
            sp = ctx.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        }
        boolean b = sp.getBoolean(key, defvalue);
        return b;

    }

    /**
     * 将一个String值存入sp文件中
     * @param context 上下文
     * @param key 存储节点名称
     * @param value 存储节点的值
     */
    public static void putString(Context context,String key,String value){
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();

    }
    /**
     * 从sp中根据key取出String值
     * @param context 上下文
     * @param key 存储节点名称
     * @param defValue 存储节点默认值
     * @return string
     */
    public static String getString(Context context,String key,String defValue){
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        String string = sp.getString(key, defValue);
        return string;
    }
    /**
     * 移除sp中的一个节点
     * @param context 上下文环境
     * @param key 节点名称
     */
    public static void removeFromSP(Context context, String key) {
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        final SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);

    }
    /**
     *  从sp中根据key取出int值
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        int i = sp.getInt(key, defValue);
        return i;

    }
    /**
     * 将一个int值存入sp文件中
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context,String key,int value){
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();

    }

    /**
     *  从sp中根据key取出float值
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        float i = sp.getFloat(key, defValue);
        return i;

    }
    /**
     * 将一个float值存入sp文件中
     * @param context
     * @param key
     * @param value
     */
    public static void putFloat(Context context,String key,float value){
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putFloat(key,value).commit();

    }

    /**
     *  从sp中根据key取出int值
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Set<String> getStringSet(Context context, String key, Set<String> defValue) {
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        Set<String> sets = sp.getStringSet(key, defValue);
        return sets;
    }
    /**
     * 将一个int值存入sp文件中
     * @param context
     * @param key
     * @param sets
     */
    public static void putStringSet(Context context,String key,Set<String> sets){
        if(sp == null){//如果sp文件不存在，则创建该文件
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putStringSet(key,sets).commit();

    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
