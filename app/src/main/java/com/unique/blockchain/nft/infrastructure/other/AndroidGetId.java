package com.unique.blockchain.nft.infrastructure.other;

import android.content.Context;
import android.provider.Settings;

public class AndroidGetId {

    public static String getAndroidId(Context context){
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }
}
