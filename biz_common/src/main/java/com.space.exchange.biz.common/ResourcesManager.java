package com.space.exchange.biz.common;

public class ResourcesManager {

    public static String getString(int res) {
        return ContextManager.getAppContext().getResources().getString(res);
    }

    public static boolean getBool(int res) {
        return ContextManager.getAppContext().getResources().getBoolean(res);
    }

    public static int getColor(int res) {
        return ContextManager.getAppContext().getResources().getColor(res);
    }
}
