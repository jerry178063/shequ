package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

public class TradeDetailPriceBean extends BaseBean {

    private int code;
    private String msg;

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
}
