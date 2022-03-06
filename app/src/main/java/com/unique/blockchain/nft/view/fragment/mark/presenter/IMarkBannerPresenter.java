package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkBannerCallBack;

public interface IMarkBannerPresenter {
    void getData(int page,int pageSum,String wallAddress);

    void registerViewCallback(IMarkBannerCallBack callback);

    void unRegisterViewCallback(IMarkBannerCallBack callback);
}
