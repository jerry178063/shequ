package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceTwoPostCallBack;

public interface IMarkOutPriceTwoPostPresenter {
    void getData(String nftId,String orderId,String uniqueAdress,String price,String chainInfo);

    void registerViewCallback(IMarkOutPriceTwoPostCallBack callback);

    void unRegisterViewCallback(IMarkOutPriceTwoPostCallBack callback);
}
