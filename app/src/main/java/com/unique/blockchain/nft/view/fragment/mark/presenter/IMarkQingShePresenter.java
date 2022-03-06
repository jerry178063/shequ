package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkQingSheCallBack;

public interface IMarkQingShePresenter {
    void getData(int page,int pageSum,String sellMode,String type);

    void registerViewCallback(IMarkQingSheCallBack callback);

    void unRegisterViewCallback(IMarkQingSheCallBack callback);
}
