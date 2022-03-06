package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressCallBack;

public interface IMeAddressPresenter {
    void getData(String did);

    void registerViewCallback(IMeAddressCallBack callback);

    void unRegisterViewCallback(IMeAddressCallBack callback);
}
