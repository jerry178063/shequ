package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

public class NftOutPriceBean extends BaseBean {

    private String sender;
    private String token_id;
    private Price price;
    private String pool_address;

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

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
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

}
