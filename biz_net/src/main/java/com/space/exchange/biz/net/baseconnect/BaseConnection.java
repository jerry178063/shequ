package com.space.exchange.biz.net.baseconnect;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.space.exchange.biz.common.util.java.StringUtil;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: Evan
 * Data: 2018/10/27
 */

public class BaseConnection {

    private static final int TIMEOUT_CONNECT = 10;
    private static final int TIMEOUT_READ = 30;
    private static final int TIMEOUT_WRITE = 30;

    public BaseConnection() {
    }

    /**
     * 获取网络连接器实例
     *
     * @param baseUrl e.g https://www.baidu.com
     */
    public Retrofit getRetrofit(OkHttpClient client, String baseUrl) {

        if (client == null || StringUtil.isEmpty(baseUrl))
            throw new RuntimeException("Param Error. The retrofit instance con't be created.");

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava
                .addConverterFactory(GsonConverterFactory.create()) // Gson
                .build();
    }

    /**
     * 创建OkHttpClient实例
     *
     * @param array 需要添加的配置
     */
    public OkHttpClient getClient(Interceptor... array) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);
        if (array != null) {
            for (int i = 0; i < array.length; i++)
                if (array[i] != null)
                    builder.addInterceptor(array[i]);
        }
        return builder.build();
    }

    /**
     * 构建信任任何HTTPS证书的客户端
     * @param array
     * @return
     */
    public OkHttpClient getUnsafeClient(Interceptor... array) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // 构建 Builder
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

            // 这地方用 lambda 表达式修改过
            builder.hostnameVerifier((hostname, session) -> true);

            // 按照业务增加
            builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);

            if (array != null) {
                for (int i = 0; i < array.length; i++)
                    if (array[i] != null)
                        builder.addInterceptor(array[i]);
            }

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

























