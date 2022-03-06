package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeCheckCompanyCallBack;

public interface IMeCheckCompanyPresenter {
    void getData(String did);

    void registerViewCallback(IMeCheckCompanyCallBack callback);

    void unRegisterViewCallback(IMeCheckCompanyCallBack callback);
}
