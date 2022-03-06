package com.space.exchange.biz.net.connection;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.space.exchange.biz.common.ApiUrl;
import com.space.exchange.biz.common.sp.AccountPrefHelper;
import com.space.exchange.biz.common.util.AppUtil;
import com.space.exchange.biz.common.util.java.StringUtil;
import com.space.exchange.biz.net.baseconnect.BaseConnection;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 交易所网络请求头设置
 * Author: Evan
 * Date: 2019/5/29
 */
public class ExConnection {
    private Context context;
    private static ExConnection INSTANCE;
    private BaseConnection baseConnection;
    private static Retrofit retrofit;

    private ExConnection() {
    }

    private static void initInstance(Context context, String baseUrl) {
        if (INSTANCE == null) {
            INSTANCE = new ExConnection();
        }
        INSTANCE.initConnection(context, baseUrl);
    }

    /**
     * 获取配置后的Retrofit实例
     */
    public static Retrofit getClient(Context context) {
        initInstance(context, ApiUrl.url);
        return retrofit;
    }

    /**
     * 获取配置后的Retrofit实例
     */
    public static Retrofit getClient(Context context, String baseUrl) {
        initInstance(context, baseUrl);
        return retrofit;
    }

    private void initConnection(Context context, String baseUrl) {
        this.context = context;

        if (baseConnection == null)
            baseConnection = new BaseConnection();

        /* 初始化请求头 */
        Interceptor headerInterceptor = getHeaderInterceptor();
        Interceptor signInterceptor = new SignHeaderInterceptor();

        OkHttpClient client;

        if (AppUtil.isDebug()) {
            /* 状态栏日志 */
            Interceptor chuckInterceptor = new ChuckInterceptor(context);
            /* Logcat日志 */
            Interceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

            client = baseConnection.getClient(headerInterceptor, signInterceptor, chuckInterceptor, loggingInterceptor/*, new RetryInterceptor(2)*/);
        } else {
            client = baseConnection.getClient(headerInterceptor, signInterceptor/*, new RetryInterceptor(2)*/);
        }
        if (StringUtil.isNotEmpty(baseUrl)) {
            retrofit = baseConnection.getRetrofit(client, baseUrl);
        } else {
            retrofit = baseConnection.getRetrofit(client, ApiUrl.url);
        }
    }

    private Interceptor getHeaderInterceptor() {
        ExHeaderInterceptor headerInterceptor = new ExHeaderInterceptor(context);
        headerInterceptor.setClientType("Android");
        /* Token */
        if (context != null && StringUtil.isNotEmpty(AccountPrefHelper.getToken(context)))
            headerInterceptor.setToken(AccountPrefHelper.getToken(context));
        return headerInterceptor;
    }
}
