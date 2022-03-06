package com.unique.blockchain.nft.infrastructure.utils;

import java.util.Calendar;

public class MoreClick {

    public static final int MIN_CLICK_TIME = 1000;
    private static long lastTime = 0;

    public static boolean MoreClicks(){
        long cTime = Calendar.getInstance().getTimeInMillis();
        if(cTime - lastTime > MIN_CLICK_TIME){
            lastTime = cTime;
            return true;
        }
        return false;
    }

}
