package com.space.exchange.biz.net.connection;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class RetryInterceptor implements Interceptor {
    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        try {
            response = chain.proceed(request);
//            if(response.isSuccessful()){
//                Throwable throwable3 = new Throwable(response.body().string());
//                CrashReport.postCatchedException(throwable3);
//            }
//            while (!response.isSuccessful() && retryNum < maxRetry) {
//                retryNum++;
//                response = chain.proceed(request);
//            }
        } catch (SocketTimeoutException|UnknownHostException e) {
            while (retryNum < maxRetry) {
                retryNum++;
                response = chain.proceed(request);
            }
        }
        return response;
    }
}
