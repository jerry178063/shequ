package com.unique.blockchain.nft.domain.home;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class FlashRecordBean extends BaseBean {

    private int total;
    private int code;
    private String msg;
    private List<Rows> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public static class Rows{
        private int id;
        private String orderNo;
        private String exchangeNum;
        private String exchangeRate;
        private String fromWalletAddress;
        private String fee;
        private String completeTime;
        private String tradingPair;
        private int exchangeStatus;

        public int getExchangeStatus() {
            return exchangeStatus;
        }

        public void setExchangeStatus(int exchangeStatus) {
            this.exchangeStatus = exchangeStatus;
        }

        public String getTradingPair() {
            return tradingPair;
        }

        public void setTradingPair(String tradingPair) {
            this.tradingPair = tradingPair;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getExchangeNum() {
            return exchangeNum;
        }

        public void setExchangeNum(String exchangeNum) {
            this.exchangeNum = exchangeNum;
        }

        public String getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(String exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getFromWalletAddress() {
            return fromWalletAddress;
        }

        public void setFromWalletAddress(String fromWalletAddress) {
            this.fromWalletAddress = fromWalletAddress;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }
    }

}
