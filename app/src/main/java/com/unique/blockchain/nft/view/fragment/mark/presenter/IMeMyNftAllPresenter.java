package com.unique.blockchain.nft.view.fragment.mark.presenter;


import com.unique.blockchain.nft.view.fragment.mark.view.IMeMyNftAllCallBack;

public interface IMeMyNftAllPresenter {
    void getData(int page,int pageSum,String nftType,String companyWalletAddr);

    void registerViewCallback(IMeMyNftAllCallBack callback);

    void unRegisterViewCallback(IMeMyNftAllCallBack callback);
}
