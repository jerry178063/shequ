package com.unique.blockchain.nft.view.fragment.mark.presenter;


import com.unique.blockchain.nft.view.fragment.mark.view.IMarkYiShuCallBack;

public interface IMarkYishuPresenter {
    void getData(int page,int pageSum,String sellMode,String type);

    void registerViewCallback(IMarkYiShuCallBack callback);

    void unRegisterViewCallback(IMarkYiShuCallBack callback);
}
