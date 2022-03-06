package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

public class NftSalesBean extends BaseBean {
    private String sender;
    private String token_id;
    private String pool_address;
    private StartPrice start_price;
    private EndPrice end_price;
    private String start_time;
    private String end_time;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getPool_address() {
        return pool_address;
    }

    public void setPool_address(String pool_address) {
        this.pool_address = pool_address;
    }

    public StartPrice getStart_price() {
        return start_price;
    }

    public void setStart_price(StartPrice start_price) {
        this.start_price = start_price;
    }

    public EndPrice getEnd_price() {
        return end_price;
    }

    public void setEnd_price(EndPrice end_price) {
        this.end_price = end_price;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public static class StartPrice{
        private String denom;
        private String amount;

        public String getDenom() {
            return denom;
        }

        public void setDenom(String denom) {
            this.denom = denom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
    public static class EndPrice{
        private String denom;
        private String amount;

        public String getDenom() {
            return denom;
        }

        public void setDenom(String denom) {
            this.denom = denom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
