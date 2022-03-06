package com.space.exchange.biz.net.baseconnect;


import java.io.Serializable;

/**
 * 请求头封装
 * Author: Evan
 * Data: 2018/10/31
 */

public class Header implements Serializable {
    public String name;
    public String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
