package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeGetCompanyInfoCallBack;

public interface IMeGetCompanyInfoPresenter {
    void getData(String did);

    void registerViewCallback(IMeGetCompanyInfoCallBack callback);

    void unRegisterViewCallback(IMeGetCompanyInfoCallBack callback);
}
