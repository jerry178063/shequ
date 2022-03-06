package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.IMarkUnCollectionCallBack;

public interface IMarkUnCollectionPresenter {
    void getData(String nftId,String uniqueAdress);

    void registerViewCallback(IMarkUnCollectionCallBack callback);

    void unRegisterViewCallback(IMarkUnCollectionCallBack callback);
}
