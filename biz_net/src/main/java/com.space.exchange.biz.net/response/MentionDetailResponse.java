package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

public class MentionDetailResponse  extends BaseResponse {

    /**
     * order_no : null
     * user_id : null
     * real_name : null
     * currency_id : null
     * english_abbreviation : null
     * chain_name : null
     * num : null
     * fee : null
     * actual : null
     * address : null
     * hash : null
     * draw_status : null
     * app_id : null
     * created_at :
     * updated_at :
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String order_no;
        private String user_id;
        private String real_name;
        private String currency_id;
        private String english_abbreviation;
        private String chain_name;
        private String num;
        private String fee;
        private String actual;
        private String address;
        private String hash;
        private int draw_status;
        private String app_id;
        private String created_at;
        private String updated_at;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
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

        public String getChain_name() {
            return chain_name;
        }

        public void setChain_name(String chain_name) {
            this.chain_name = chain_name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public int getDraw_status() {
            return draw_status;
        }

        public void setDraw_status(int draw_status) {
            this.draw_status = draw_status;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
