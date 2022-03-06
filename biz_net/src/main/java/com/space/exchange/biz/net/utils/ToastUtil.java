package com.space.exchange.biz.net.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类
 * Author: Evan
 * Data: 2018/7/14
 */
public final class ToastUtil {

    public static Toast mToast;

    /**
     * Toast短暂显示
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast长显示
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Toast 错误提示 多次只显示一条
     *
     */
    public static void showError(Context context, String message) {
        if(mToast == null){
            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        }
        mToast.setText(message);
        mToast.show();
    }

    /**
     * Toast布局中间短暂显示
     *
     * @param context
     * @param message
     */
    public static void showShortCenter(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setText(message);
        toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.show();
    }

    /**
     * Toast布局中间长显示
     *
     * @param context
     * @param message
     */
    public static void showLongCenter(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setText(message);
        toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.show();
    }

    public static void showShort(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }
}
