package com.unique.blockchain.nft.view.activity.mark.view;


import com.unique.blockchain.nft.domain.mark.JingBiaoBean;

public interface IJingBiaoCallBack {
    void loadJingBiaoData(JingBiaoBean jingBiaoBean);
    void loadJingBiaoFail();
}
