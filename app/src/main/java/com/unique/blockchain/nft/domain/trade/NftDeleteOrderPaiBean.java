package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

public class NftDeleteOrderPaiBean extends BaseBean {

    private String creator;
    private String token_id;
    private String pool_address;


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
}
