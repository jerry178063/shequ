package com.unique.blockchain.nft.domain.me;

import com.unique.blockchain.nft.domain.BaseBean;

public class CheckCompanyBean extends BaseBean {

    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String cautionMoney;
        private String payCautionMoney;
        private String surplusCautionMoney;
        private int status;//是否置灰
        private int name;//显示按钮名字

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        public String getCautionMoney() {
            return cautionMoney;
        }

        public void setCautionMoney(String cautionMoney) {
            this.cautionMoney = cautionMoney;
        }

        public String getPayCautionMoney() {
            return payCautionMoney;
        }

        public void setPayCautionMoney(String payCautionMoney) {
            this.payCautionMoney = payCautionMoney;
        }

        public String getSurplusCautionMoney() {
            return surplusCautionMoney;
        }

        public void setSurplusCautionMoney(String surplusCautionMoney) {
            this.surplusCautionMoney = surplusCautionMoney;
        }
    }

}
