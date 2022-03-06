package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.IJingBiaoCallBack;

public interface IJingBiaoPresenter {
    void getData(String nftId);

    void registerViewCallback(IJingBiaoCallBack callback);

    void unRegisterViewCallback(IJingBiaoCallBack callback);
}
