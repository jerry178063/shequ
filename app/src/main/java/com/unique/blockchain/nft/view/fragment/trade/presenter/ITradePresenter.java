package com.unique.blockchain.nft.view.fragment.trade.presenter;

import com.unique.blockchain.nft.view.fragment.trade.view.ITradeHomeCallbask;

public interface ITradePresenter {
    void getData(int page,int pageSum,String key,String wallAddress);

    void registerViewCallback(ITradeHomeCallbask callback);

    void unRegisterViewCallback(ITradeHomeCallbask callback);
}
