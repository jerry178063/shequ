package com.space.exchange.biz.net.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
*/
public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context context, String text) {
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}