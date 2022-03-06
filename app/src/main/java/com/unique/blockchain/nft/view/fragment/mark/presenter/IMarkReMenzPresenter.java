package com.unique.blockchain.nft.view.fragment.mark.presenter;


import com.unique.blockchain.nft.view.fragment.mark.view.IMarkReMenzCallBack;

public interface IMarkReMenzPresenter {
    void getData(int page,int pageSum,String wallAddress,String type);

    void registerViewCallback(IMarkReMenzCallBack callback);

    void unRegisterViewCallback(IMarkReMenzCallBack callback);
}
