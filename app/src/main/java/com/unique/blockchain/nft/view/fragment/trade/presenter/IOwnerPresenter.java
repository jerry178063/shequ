package com.unique.blockchain.nft.view.fragment.trade.presenter;

import com.unique.blockchain.nft.view.fragment.trade.view.ITradeOwnerCallbask;

public interface IOwnerPresenter {
    void getData(int page,String nftId);

    void registerViewCallback(ITradeOwnerCallbask callback);

    void unRegisterViewCallback(ITradeOwnerCallbask callback);
}
