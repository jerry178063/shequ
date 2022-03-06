package com.unique.blockchain.nft.view.fragment.mark.presenter;


import com.unique.blockchain.nft.view.fragment.mark.view.IMarkZhuZaoCallBack;

public interface IMarkZhuZaoPresenter {
    void getData(int page,int pageSum,String companyWalletAddr);

    void registerViewCallback(IMarkZhuZaoCallBack callback);

    void unRegisterViewCallback(IMarkZhuZaoCallBack callback);
}
