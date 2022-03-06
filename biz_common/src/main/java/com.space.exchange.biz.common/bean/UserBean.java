package com.space.exchange.biz.common.bean;

import android.text.TextUtils;

import java.io.Serializable;

/*
 *  用户信息
 * */
public class UserBean implements Serializable {

    private String user_id;

    private String city;//用户地址--市

    private String device_id;//设备id

    private String email;//邮箱

    private String email_on;//是否打开邮箱验证

    private String google;//为空则没有绑定谷歌验证

    private int google_on;//是否开启谷歌验证

    private String nickname;//昵称

    private String phone;//手机号

    private String phone_on;//是否打开手机验证

    private String province;//用户地址--省

    private String real_name;//真实姓名

    private String user_img;//用户头像

    private String username;//用户名

    private int  is_set_trade_pwd;//是否设置资金密码

    private int is_merchant;//是否是商户

    private String registration_id;//极光推送id

    private int push_notify_status;//是否接受推送

    private KYCBean kyc_list;

    private Agreement agreement;

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public KYCBean getKyc_list() {
        return kyc_list;
    }

    public void setKyc_list(KYCBean kyc_list) {
        this.kyc_list = kyc_list;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public int getPush_notify_status() {
        return push_notify_status;
    }

    public void setPush_notify_status(int push_notify_status) {
        this.push_notify_status = push_notify_status;
    }

    public int getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(int is_merchant) {
        this.is_merchant = is_merchant;
    }

    public int getIs_set_trade_pwd() {
        return is_set_trade_pwd;
    }

    public void setIs_set_trade_pwd(int is_set_trade_pwd) {
        this.is_set_trade_pwd = is_set_trade_pwd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_on() {
        return email_on;
    }

    public void setEmail_on(String email_on) {
        this.email_on = email_on;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public int getGoogle_on() {
        return google_on;
    }

    public void setGoogle_on(int google_on) {
        this.google_on = google_on;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_on() {
        return phone_on;
    }

    public void setPhone_on(String phone_on) {
        this.phone_on = phone_on;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     *
     * 是否绑定谷歌验证 true 为已绑定
     * */
    public boolean isBindGoogleVerify(){
        return !TextUtils.isEmpty(google);
    }

    /**
     *
     * 谷歌验证是否开启
     * **/
    public boolean isOpenGoogleVerify(){
        return 1 == google_on;
    }

    public class Agreement implements Serializable {
        private String ieo;//IEO协议是否同意  1已同意

        public String getIeo() {
            return ieo;
        }

        public void setIeo(String ieo) {
            this.ieo = ieo;
        }
    }

}
