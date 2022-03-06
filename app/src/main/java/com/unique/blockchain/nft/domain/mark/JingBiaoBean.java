package com.unique.blockchain.nft.domain.mark;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class JingBiaoBean extends BaseBean {

    private int code;
    private String msg;
    private List<Rows> rows;


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

    public class Rows{

        private String walletAddr;
        private String completTime;
        private String currency;//单位
        private String price;//价格

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }

        public String getCompletTime() {
            return completTime;
        }

        public void setCompletTime(String completTime) {
            this.completTime = completTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
