package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

/***
 * 获取资产的当前市值
 * */
public class BuyTokenListBean extends BaseBean {

    private List<String> tokenList;

    public List<String> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<String> tokenList) {
        this.tokenList = tokenList;
    }
}
