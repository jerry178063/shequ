package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

public class ChargeDetailResponse  extends BaseResponse {

    /**
     * order_no : null
     * user_id : null
     * real_name : null
     * english_abbreviation : null
     * chain_name : null
     * num : null
     * type : null
     * confirm_count : null
     * address : null
     * hash : null
     * remark : null
     * recharge_status : null
     * smart_contract : null
     * timeline : null
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
        private String english_abbreviation;
        private String chain_name;
        private String num;
        private String type;
        private String confirm_count;
        private String address;
        private String hash;
        private String remark;
        private String recharge_status;
        private String smart_contract;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getConfirm_count() {
            return confirm_count;
        }

        public void setConfirm_count(String confirm_count) {
            this.confirm_count = confirm_count;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRecharge_status() {
            return recharge_status;
        }

        public void setRecharge_status(String recharge_status) {
            this.recharge_status = recharge_status;
        }

        public String getSmart_contract() {
            return smart_contract;
        }

        public void setSmart_contract(String smart_contract) {
            this.smart_contract = smart_contract;
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
