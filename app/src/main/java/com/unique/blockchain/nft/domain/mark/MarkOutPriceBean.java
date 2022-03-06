package com.unique.blockchain.nft.domain.mark;

import com.unique.blockchain.nft.domain.BaseBean;

public class MarkOutPriceBean extends BaseBean {

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

    public class Data {
        private String currentPrice;//当前NFT所需价格
        private String minersFee;//旷工费
        private String royalty;//版税
        private String balance;//您的余额
        private String currency;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getMinersFee() {
            return minersFee;
        }

        public void setMinersFee(String minersFee) {
            this.minersFee = minersFee;
        }

        public String getRoyalty() {
            return royalty;
        }

        public void setRoyalty(String royalty) {
            this.royalty = royalty;
        }
    }
}
