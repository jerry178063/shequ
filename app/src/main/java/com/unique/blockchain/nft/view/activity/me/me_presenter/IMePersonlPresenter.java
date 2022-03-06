package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMePersonlInfoCallBack;

public interface IMePersonlPresenter {
    void getData(String did);

    void registerViewCallback(IMePersonlInfoCallBack callback);

    void unRegisterViewCallback(IMePersonlInfoCallBack callback);
}
