package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkCollectCallBack;

public interface IMarkCollectPresenter {
    void getData(int page,int pageSum,String sellMode,String type);

    void registerViewCallback(IMarkCollectCallBack callback);

    void unRegisterViewCallback(IMarkCollectCallBack callback);
}
