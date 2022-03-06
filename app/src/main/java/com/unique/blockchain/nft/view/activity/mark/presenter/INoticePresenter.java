package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.view.activity.mark.view.INoticeCallBack;

public interface INoticePresenter {
    void getData(int page,int pageSum,String walletAdd,String type);

    void registerViewCallback(INoticeCallBack callback);

    void unRegisterViewCallback(INoticeCallBack callback);
}
