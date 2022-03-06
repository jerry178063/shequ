package com.unique.blockchain.nft.domain.node;


import java.util.List;

public class HomeDealBean {

    private String code;
    private String message;
    private List<Data> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        private String buyToken;
        private double lastDealAmount;

        public double getLastDealAmount() {
            return lastDealAmount;
        }

        public void setLastDealAmount(double lastDealAmount) {
            this.lastDealAmount = lastDealAmount;
        }

        public String getBuyToken() {
            return buyToken;
        }

        public void setBuyToken(String buyToken) {
            this.buyToken = buyToken;
        }
    }

}
