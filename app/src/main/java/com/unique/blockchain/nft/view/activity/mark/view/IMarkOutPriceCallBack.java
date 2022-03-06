package com.unique.blockchain.nft.view.activity.mark.view;


import com.unique.blockchain.nft.domain.mark.MarkOutPriceBean;

public interface IMarkOutPriceCallBack {
    void loadOutPriceData(MarkOutPriceBean jingBiaoBean);
    void loadOutPriceFail();
}
