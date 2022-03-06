package com.space.exchange.biz.common.bean;

import java.io.Serializable;

public class User implements Serializable {

    private int user_id;

    private String token;
    private String rmb_total;
    private String total;
    private String ck1;
    private int has_google_verify;   //	是否有谷歌验证  0 无 1 有
    private int google_verify_status;   //谷歌验证开启状态   0 关闭 1开启
    private int push_notify_status;//极光推送开启关闭装填  0关闭 1开启

    public int getHas_google_verify() {
        return has_google_verify;
    }

    public void setHas_google_verify(int has_google_verify) {
        this.has_google_verify = has_google_verify;
    }

    public int getGoogle_verify_status() {
        return google_verify_status;
    }

    public void setGoogle_verify_status(int google_verify_status) {
        this.google_verify_status = google_verify_status;
    }

    public String getRmb_total() {
        return rmb_total;
    }

    public void setRmb_total(String rmb_total) {
        this.rmb_total = rmb_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCk1() {
        return ck1;
    }

    public void setCk1(String ck1) {
        this.ck1 = ck1;
    }

    private String google;

    private String ck;

    private String city;

    private String phone;

    private String province;

    private String country;//国家代号

    private String id_card;//省份证号

    private String email;

    private String name;

    private String user_name;

    private int is_attention;

    private String ck_award;

    private int auth_level_one_status;//身份认证级别1

    private int has_trade_pass;//是否有资金密码  1为设置过资金密码  0为未设置资金密码

    public int isIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public int getHas_trade_pass() {
        return has_trade_pass;
    }

    public void setHas_trade_pass(int has_trade_pass) {
        this.has_trade_pass = has_trade_pass;
    }

    private String sign;

    private int status;//-1-1待机器审核 0 未审核 待人工审核 1通过过 2驳回

    private String user_img;

    private int articles_num;

    private int attentions_num;

    private int collections_num;

    private int fans_num;

    private int is_counselor;

    private String share_qrcode;

    private String share_url;

    public String getCk_award() {
        return ck_award;
    }

    public void setCk_award(String ck_award) {
        this.ck_award = ck_award;
    }

    public String getShare_qrcode() {
        return share_qrcode;
    }

    public void setShare_qrcode(String share_qrcode) {
        this.share_qrcode = share_qrcode;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getIs_attention() {
        return is_attention;
    }

    public int getIs_counselor() {
        return is_counselor;
    }

    public void setIs_counselor(int is_counselor) {
        this.is_counselor = is_counselor;
    }

    public int getArticles_num() {
        return articles_num;
    }

    public void setArticles_num(int articles_num) {
        this.articles_num = articles_num;
    }

    public int getAttentions_num() {
        return attentions_num;
    }

    public void setAttentions_num(int attentions_num) {
        this.attentions_num = attentions_num;
    }

    public int getCollections_num() {
        return collections_num;
    }

    public void setCollections_num(int collections_num) {
        this.collections_num = collections_num;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public int getAuth_level_one_status() {
        return auth_level_one_status;
    }

    public void setAuth_level_one_status(int auth_level_one_status) {
        this.auth_level_one_status = auth_level_one_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isUserAttention(){
        return is_attention == 1;
    }

    public int getPush_notify_status() {
        return push_notify_status;
    }

    public void setPush_notify_status(int push_notify_status) {
        this.push_notify_status = push_notify_status;
    }
}
