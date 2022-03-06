package com.space.widget;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceUtil {

    /**
     * 获取DinBold字体
     */
    public static Typeface getDinBoldFace(Context context) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "din_bold.ttf");
        return typeFace;
    }

    /**
     * 获取DinMedium字体
     */
    public static Typeface getDinMediumFace(Context context) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "din_medium.ttf");
        return typeFace;
    }

}
