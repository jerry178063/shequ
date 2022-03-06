package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkCollectCallBack;

public interface IMarkSearchPresenter {
    void getData(int page,int pageSum,String key,String collect,String walletAdd,String sellMode);

    void registerViewCallback(IMarkCollectCallBack callback);

    void unRegisterViewCallback(IMarkCollectCallBack callback);
}
