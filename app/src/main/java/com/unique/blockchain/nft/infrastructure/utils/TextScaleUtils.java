package com.unique.blockchain.nft.infrastructure.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * 设置字体放大缩小
 * */
public class TextScaleUtils {
    public static void scaleTextSize(Activity activity, boolean isScale) {
        float size;
        Configuration configuration = activity.getResources().getConfiguration();
        if (isScale) {
            size = 1.25f;
        } else {
            size = 1f;
        }
        configuration.fontScale = size; //0.85 small size, 1 normal size, 1.15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, metrics);//更新全局的配置
    }
}
