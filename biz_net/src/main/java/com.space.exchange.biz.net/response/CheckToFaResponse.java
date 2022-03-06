package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

public class CheckToFaResponse  extends BaseResponse {

    /**
     * phone_on : 1
     * email_on : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int phone_on;
        private int email_on;

        public int getPhone_on() {
            return phone_on;
        }

        public void setPhone_on(int phone_on) {
            this.phone_on = phone_on;
        }

        public int getEmail_on() {
            return email_on;
        }

        public void setEmail_on(int email_on) {
            this.email_on = email_on;
        }
    }
}
