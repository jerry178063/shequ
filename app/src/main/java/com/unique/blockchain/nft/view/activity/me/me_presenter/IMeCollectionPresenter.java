package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeCollectionCallBack;

public interface IMeCollectionPresenter {
    void getData(String collection,String address,int page ,int pageNum);

    void registerViewCallback(IMeCollectionCallBack callback);

    void unRegisterViewCallback(IMeCollectionCallBack callback);
}
