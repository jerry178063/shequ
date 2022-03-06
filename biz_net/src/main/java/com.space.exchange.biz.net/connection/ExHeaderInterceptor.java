package com.space.exchange.biz.net.connection;

import android.content.Context;

import com.space.exchange.biz.common.GlobalField;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.java.StringUtil;
import com.space.exchange.biz.net.baseconnect.Header;
import com.space.exchange.biz.net.baseconnect.HeaderInterceptor;

import java.util.ArrayList;
import java.util.List;



/**
 * 交易所网络请求头设置
 * Author: Evan
 * Date: 2019/5/29
 */
public class ExHeaderInterceptor extends HeaderInterceptor {
    private Context context;
    /* token */
    private String token;

    public ExHeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    protected List<Header> insertHeaders() {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new Header("Accept", "application/json"));
        headerList.add(new Header("Application", "ZLM"));
        headerList.add(new Header("Access-Source", "android"));
        /**
         * 理财平台区分
         */
        headerList.add(new Header("Access-Platform", "d8c1efc7b06cf24d7941bc4bb6a174bf"));
        /* Authorization */
        if (StringUtil.isNotEmpty(token)) {
            headerList.add(new Header("Access-Token", token));
        }
        String appId = SPUtils.getString(context, GlobalField.APP_ID, "");
        if (StringUtil.isNotEmpty(appId)) {
            headerList.add(new Header(GlobalField.APP_ID, appId));
        }
        return headerList;
    }

    public void setToken(String token) {
        this.token = token;
    }
}






