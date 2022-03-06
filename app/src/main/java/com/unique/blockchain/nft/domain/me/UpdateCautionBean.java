package com.unique.blockchain.nft.domain.me;

import com.unique.blockchain.nft.domain.BaseBean;

public class UpdateCautionBean extends BaseBean {

    private String cautionMoney;
    private String chainInfo;
    private int type;
    private String walletAddr;


    public String getCautionMoney() {
        return cautionMoney;
    }

    public void setCautionMoney(String cautionMoney) {
        this.cautionMoney = cautionMoney;
    }

    public String getChainInfo() {
        return chainInfo;
    }

    public void setChainInfo(String chainInfo) {
        this.chainInfo = chainInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }
}
