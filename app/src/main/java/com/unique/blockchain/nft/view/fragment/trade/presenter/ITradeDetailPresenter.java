package com.unique.blockchain.nft.view.fragment.trade.presenter;

import com.unique.blockchain.nft.view.fragment.trade.view.ITradeListDetailCallbask;

public interface ITradeDetailPresenter {
    void getData(int page,String orderId,String nftId,String walletAddr);

    void registerViewCallback(ITradeListDetailCallbask callback);

    void unRegisterViewCallback(ITradeListDetailCallbask callback);
}
