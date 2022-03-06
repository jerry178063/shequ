package com.space.exchange.biz.common.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class CoinBean extends BaseIndexTagBean implements Serializable {

    private String currency_id;//币种id
    private String english_abbreviation;//币种名称
    private String chinese_name;//币种中文名称
    private int client_display_precision;//数据精度

    public int getClient_display_precision() {
        return client_display_precision;
    }

    public void setClient_display_precision(int client_display_precision) {
        this.client_display_precision = client_display_precision;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getEnglish_abbreviation() {
        return english_abbreviation;
    }

    public void setEnglish_abbreviation(String english_abbreviation) {
        this.english_abbreviation = english_abbreviation;
    }

    private String currency_type;

    private String currency_name;

    private String id;

    private String name;

    private String buy_price;

    private String sell_price;

    private String num;

    private int is_chongzhi;
    private int is_tixian;

    public String getChinese_name() {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }

    public int getIs_chongzhi() {
        return is_chongzhi;
    }

    public void setIs_chongzhi(int is_chongzhi) {
        this.is_chongzhi = is_chongzhi;
    }

    public int getIs_tixian() {
        return is_tixian;
    }

    public void setIs_tixian(int is_tixian) {
        this.is_tixian = is_tixian;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getBaseIndexTag() {
        if (!TextUtils.isEmpty(currency_type)){
            return currency_type.substring(0,1);
        }else{
            return "";
        }
    }

    public CoinBean(String currency_type, String id, String name) {
        this.currency_type = currency_type;
        this.id = id;
        this.name = name;
    }

    public CoinBean(){

    }
}
