package com.space.exchange.biz.common.sp;

import android.content.Context;
import android.text.TextUtils;


/**
 * 用户账户相关配置存取
 * Author: Evan
 * Date: 2019/5/29
 */
public class AccountPrefHelper {
    private static SharedPreferenceHelper pref;

    /**
     * 保存Token
     */
    public static void saveToken(Context context, String token) {
        if (pref == null) {
            pref = new SharedPreferenceHelper(context, PrefConst.SP_NAME_USER);
        }
        pref.putValues(new SharedPreferenceHelper.ContentValue(PrefConst.KEY_USER_TOKEN, token));
    }

    /**
     * 读取Token
     */
    public static String getToken(Context context) {
        if (pref == null) {
            pref = new SharedPreferenceHelper(context, PrefConst.SP_NAME_USER);
        }
        if (TextUtils.isEmpty(pref.getString(PrefConst.KEY_USER_TOKEN))){
            return "";
        }else{
            return pref.getString(PrefConst.KEY_USER_TOKEN);
        }
    }


}
