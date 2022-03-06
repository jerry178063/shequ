package com.unique.blockchain.nft.view.fragment.mark.presenter;


import com.unique.blockchain.nft.view.fragment.mark.view.IMeMyNftSearchCallBack;

public interface IMeMyNftSearchPresenter {
    void getData(int page,int pageSum,String key,String companyWalletAddr);

    void registerViewCallback(IMeMyNftSearchCallBack callback);

    void unRegisterViewCallback(IMeMyNftSearchCallBack callback);
}
