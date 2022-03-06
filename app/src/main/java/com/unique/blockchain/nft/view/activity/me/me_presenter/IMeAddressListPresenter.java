package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressListCallBack;

public interface IMeAddressListPresenter {
    void getData(String did);

    void registerViewCallback(IMeAddressListCallBack callback);

    void unRegisterViewCallback(IMeAddressListCallBack callback);
}
