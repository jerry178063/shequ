package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkOtherCallBack;

public interface IMarkOtherPresenter {
    void getData(int page,int pageSum,String wallAddress,String type);

    void registerViewCallback(IMarkOtherCallBack callback);

    void unRegisterViewCallback(IMarkOtherCallBack callback);
}
