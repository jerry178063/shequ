package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

public class NftSalesTwoBean extends BaseBean {

    private String sender;
    private String token_id;
    private Price start_price;
    private MinPrice min_step;
    private String pool_address;
    private String start_time;
    private String end_time;
    private String auto_agree_period;

    public String getAuto_agree_period() {
        return auto_agree_period;
    }

    public void setAuto_agree_period(String auto_agree_period) {
        this.auto_agree_period = auto_agree_period;
    }

    public MinPrice getMin_step() {
        return min_step;
    }

    public void setMin_step(MinPrice min_step) {
        this.min_step = min_step;
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

    public Price getStart_price() {
        return start_price;
    }

    public void setStart_price(Price start_price) {
        this.start_price = start_price;
    }

    public String getPool_address() {
        return pool_address;
    }

    public void setPool_address(String pool_address) {
        this.pool_address = pool_address;
    }


    public static class Price{
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
    public static class MinPrice{
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
