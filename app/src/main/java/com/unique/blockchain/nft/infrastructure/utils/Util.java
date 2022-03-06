package com.unique.blockchain.nft.infrastructure.utils;

import android.content.Context;
import android.widget.Toast;

public class Util {
//    public static String ws2 = "ws://192.168.3.20:8098/ws";//websocket开发地址
//    public static String ws2 = "ws://192.168.2.195:8090/ws";//websocket开发地址--不用了
    public static String ws2 = "ws://192.168.3.21:8098/ws";//websocket测试地址
//    public static String ws2 = "ws://120.79.14.55:30098/ws";//websocket外网地址

//    public static String ws2 = Util.ws +  "/" + MyApplication.dvelop_id + "/" + 0 + "," + "-1";
//    public static String ws2 = "wss://82.157.123.54:9010/ajaxchattest";


    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
