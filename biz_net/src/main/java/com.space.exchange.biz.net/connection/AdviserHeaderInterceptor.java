package com.space.exchange.biz.net.connection;

import android.content.Context;

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
public class AdviserHeaderInterceptor extends HeaderInterceptor {
    private Context context;
    /* token */
    private String token;

    public AdviserHeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    protected List<Header> insertHeaders() {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new Header("Accept", "application/json"));
        headerList.add(new Header("Application", "CoinAdviser"));
        /* Authorization */
        if (StringUtil.isNotEmpty(token)) {
            headerList.add(new Header("Authorization", token));
        }
        return headerList;
    }

    public void setToken(String token) {
        this.token = token;
    }
}






