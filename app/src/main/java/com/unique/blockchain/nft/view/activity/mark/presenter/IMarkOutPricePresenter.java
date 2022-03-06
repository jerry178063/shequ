package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceCallBack;

public interface IMarkOutPricePresenter {
    void getData(String nftId,String uniqueAdress);

    void registerViewCallback(IMarkOutPriceCallBack callback);

    void unRegisterViewCallback(IMarkOutPriceCallBack callback);
}
