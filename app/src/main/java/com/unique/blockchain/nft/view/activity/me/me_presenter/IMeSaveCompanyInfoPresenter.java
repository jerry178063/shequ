package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.view.activity.me.me_view.IMeSaveCompanyInfoCallBack;

public interface IMeSaveCompanyInfoPresenter {
    void getData(String did);

    void registerViewCallback(IMeSaveCompanyInfoCallBack callback);

    void unRegisterViewCallback(IMeSaveCompanyInfoCallBack callback);
}
