package com.space.exchange.biz.net.baseconnect;


import com.space.exchange.biz.common.util.java.StringUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 拦截获取服务端返回的Cookie
 * Author: Evan
 * Data: 2018/10/29
 */

public class CookieInterceptor implements Interceptor {

    public static volatile String cookie;
    private CookieListener listener;

    public CookieInterceptor(CookieListener listener) {
        this.listener = listener;
    }

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Response resp = chain.proceed(chain.request());
        List<String> cookies = resp.headers("Set-Cookie");
        String cookieStr = "";
        if (cookies != null && cookies.size() > 0) {
            for (int i = 0; i < cookies.size(); i++) {
                cookieStr += cookies.get(i) + ";";
            }
            /**
             * 截取Cookie的逻辑：cookie有只有cfduid cookie的情况，而只当包含ps的Cookie时才截取
             */
            if (listener != null && shouldSaveCookie(cookieStr)) {
                cookie = cookieStr;
                listener.onReceive(cookieStr);
            }
        }
        return resp;
    }

    public interface CookieListener {
        void onReceive(String cookie);
    }

    /**
     * 判断Cookie有效性，是否应该保存Cookie
     */
    public static boolean shouldSaveCookie(String cookie) {
        if (StringUtil.isNotEmpty(cookie) && cookie.contains("ps=") && cookie.contains("pu=")) {
            return true;
        }
        return false;
    }
}





























