package com.space.exchange.biz.net.bean;

import java.math.BigDecimal;
import java.util.List;

public class GetAdressBean extends BaseResponse {


    /**
     * success : true
     * data : [{"id":27,"memberId":14,"coinId":-2,"currencyName":"ERC20","addr":"0xeaC8F58a381Bb414C2027c9C333DB32117f2aDAd","fee":"5","colocationFee":"0","balance":"4388.0500","minimalMoney":"5","waitColocationFee":null},{"id":28,"memberId":14,"coinId":-2,"currencyName":"TRC20","addr":"TMPipAfQDmD4hm6eDgwz2Rbv3Cakk6jA6a","fee":"0.5","colocationFee":"0","balance":"4388.0500","minimalMoney":"5","waitColocationFee":null}]
     * logs :
     */

    private boolean success;
    private String logs;
    /**
     * id : 27
     * memberId : 14
     * coinId : -2
     * currencyName : ERC20
     * addr : 0xeaC8F58a381Bb414C2027c9C333DB32117f2aDAd
     * fee : 5
     * colocationFee : 0
     * balance : 4388.0500
     * minimalMoney : 5
     * waitColocationFee : null
     */

    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private int memberId;
        private int coinId;
        private String currencyName;
        private String addr;
        private String fee;
        private String colocationFee;
        private String balance;
        private String minimalMoney;
        private BigDecimal waitColocationFee;
        public int getId() {
            return id;
        }
        private boolean isselected;

        public boolean isIsselected() {
            return isselected;
        }

        public void setIsselected(boolean isselected) {
            this.isselected = isselected;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getColocationFee() {
            return colocationFee;
        }

        public void setColocationFee(String colocationFee) {
            this.colocationFee = colocationFee;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getMinimalMoney() {
            return minimalMoney;
        }

        public void setMinimalMoney(String minimalMoney) {
            this.minimalMoney = minimalMoney;
        }

        public BigDecimal getWaitColocationFee() {
            return waitColocationFee;
        }

        public void setWaitColocationFee(BigDecimal waitColocationFee) {
            this.waitColocationFee = waitColocationFee;
        }
    }
}
