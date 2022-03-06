package com.space.exchange.biz.net.baseconnect;


import com.space.exchange.biz.common.util.java.StringUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通用网络请求头设置
 * Author: Evan
 * Data: 2018/7/12
 */

public abstract class HeaderInterceptor implements Interceptor {

    /* Accept */
    private String accept;
    /* Content-Type */
    private String contentType;
    /* User-Agent */
    private String userAgent;
    /* Referer */
    private String referrer;
    /* Client */
    private String client;
    /* Device-Id */
    private String deviceId;

    public HeaderInterceptor() {
    }

    protected abstract List<Header> insertHeaders();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (StringUtil.isNotEmpty(accept))
            requestBuilder.addHeader("Accept", accept);
        if (StringUtil.isNotEmpty(contentType))
            requestBuilder.addHeader("Content-Type", contentType);
        if (StringUtil.isNotEmpty(client))
            requestBuilder.addHeader("Client-Type", client);
        if (StringUtil.isNotEmpty(deviceId))
            requestBuilder.addHeader("Device-Id", deviceId);
        if (StringUtil.isNotEmpty(userAgent))
            requestBuilder.addHeader("User-Agent", userAgent);
        if (StringUtil.isNotEmpty(referrer))
            requestBuilder.addHeader("Referer", referrer);

        List<Header> headerList = insertHeaders();
        for (Header header : headerList) {
            requestBuilder.addHeader(header.name, header.value);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public void setClientType(String client) {
        this.client = client;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}












