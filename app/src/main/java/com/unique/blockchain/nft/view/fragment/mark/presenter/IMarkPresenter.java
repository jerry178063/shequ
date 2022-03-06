package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkAllCallBack;

public interface IMarkPresenter {
    void getData(int page,int pageSum,String sellMode,String type);

    void registerViewCallback(IMarkAllCallBack callback);

    void unRegisterViewCallback(IMarkAllCallBack callback);
}
