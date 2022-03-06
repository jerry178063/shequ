package com.space.exchange.biz.net.bean;

import java.io.Serializable;

/**
 * 请求回复基类
 * Author: Evan
 * Date: 2019/5/29
 */
public class BaseResponse implements Serializable {
    // 状态码
    public int code;
    // 中文回复描述
    public String msg = "";
}
