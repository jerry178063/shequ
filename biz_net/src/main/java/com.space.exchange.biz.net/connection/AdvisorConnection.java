package com.space.exchange.biz.net.connection;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.space.exchange.biz.common.BuildConfig;
import com.space.exchange.biz.common.sp.AccountPrefHelper;
import com.space.exchange.biz.common.util.java.StringUtil;
import com.space.exchange.biz.net.baseconnect.BaseConnection;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 请求头设置
 * Date: 2019/7/24
 */
public class AdvisorConnection {
    private Context context;
    private static AdvisorConnection INSTANCE;
    private BaseConnection baseConnection;
    private static Retrofit retrofit;

    private AdvisorConnection() {
    }

    private static void initInstance(Context context, String baseUrl) {
        if (INSTANCE == null) {
            INSTANCE = new AdvisorConnection();
        }
        INSTANCE.initConnection(context, baseUrl);
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

        if (BuildConfig.DEBUG) {
            /* 状态栏日志 */
            Interceptor chuckInterceptor = new ChuckInterceptor(context);
            /* Logcat日志 */
            Interceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

            client = baseConnection.getClient(headerInterceptor, signInterceptor, chuckInterceptor, new LoggingInterceptor());
        } else {
            client = baseConnection.getClient(headerInterceptor, signInterceptor, new LoggingInterceptor());
        }

        retrofit = baseConnection.getRetrofit(client,baseUrl);
    }

    private Interceptor getHeaderInterceptor() {
        AdviserHeaderInterceptor headerInterceptor = new AdviserHeaderInterceptor(context);
        headerInterceptor.setClientType("Android");
        /* Token */


        if (context != null && StringUtil.isNotEmpty(AccountPrefHelper.getToken(context)))
            headerInterceptor.setToken(AccountPrefHelper.getToken(context));
        return headerInterceptor;
    }
}
