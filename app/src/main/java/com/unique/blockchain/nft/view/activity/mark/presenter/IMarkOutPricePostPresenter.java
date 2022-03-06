package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPricePostCallBack;

public interface IMarkOutPricePostPresenter {
    void getData(String nftId,String orderId,String uniqueAdress,String price,String chainInfo);

    void registerViewCallback(IMarkOutPricePostCallBack callback);

    void unRegisterViewCallback(IMarkOutPricePostCallBack callback);
}
