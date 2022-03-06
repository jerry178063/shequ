package com.space.exchange.biz.net.model.zly_bean;

import com.space.exchange.biz.net.bean.BaseResponse;

public class LoginBean extends BaseResponse {

    /**
     * success : true
     * data : {"code":"RIRX578183","token":"kMFwOlM2ksuW5ObQBhAI3ZhjrY5Hd5QU"}
     * logs :
     */

    private boolean success;
    /**
     * code : RIRX578183
     * token : kMFwOlM2ksuW5ObQBhAI3ZhjrY5Hd5QU
     */

    private DataBean data;
    private String logs;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public static class DataBean {
        private String code;
        private String token;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
