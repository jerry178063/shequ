package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.IMarkCollectionCallBack;

public interface IMarkCollectionPresenter {
    void getData(String nftId,String uniqueAdress);

    void registerViewCallback(IMarkCollectionCallBack callback);

    void unRegisterViewCallback(IMarkCollectionCallBack callback);
}
