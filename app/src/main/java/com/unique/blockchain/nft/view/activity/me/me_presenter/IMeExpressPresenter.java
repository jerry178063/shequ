package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeAdminShouHuoExpressCallBack;

public interface IMeExpressPresenter {
    void getData(String collection);

    void registerViewCallback(IMeAdminShouHuoExpressCallBack callback);

    void unRegisterViewCallback(IMeAdminShouHuoExpressCallBack callback);
}
