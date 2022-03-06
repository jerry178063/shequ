package com.space.exchange.biz.common.bean;

public class PingDataBean {


    /**
     * action : ping
     * data : {"fd":"ID","time":"1599101980"}
     */

    private String action;
    private DataBean data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fd : ID
         * time : 1599101980
         */

        private String fd;
        private String time;

        public String getFd() {
            return fd;
        }

        public void setFd(String fd) {
            this.fd = fd;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
