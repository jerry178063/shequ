package com.space.exchange.biz.net;

/**
 * 字符串常量
 * Author: Evan
 * Date: 2019/5/29
 */
public interface Const {
    String HEADER_SIGN = "Sign";

    long TIME_OUT_REQUEST = 60;

    String SECRET_KEY = "0de8f782de92462c73144a824de93fd3";

    String ACCESS_KEY = "api";

    // 数据成功返回
    short CODE_OK = 0;
    // 通用错误
    short CODE_ERROR = 1;
    // Token 无效，需要跳转到登录界面
    short CODE_TOKEN_LOGIN = 401;
    // Token 无效，提示用户
    short CODE_TOKEN_PROMPT = 403;
    // 需要进行2FA校验
    short CODE_2FA = 400;
    short CODE_FABI = 406;
}
