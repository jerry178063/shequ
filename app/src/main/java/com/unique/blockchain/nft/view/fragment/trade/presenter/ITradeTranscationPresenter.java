package com.unique.blockchain.nft.view.fragment.trade.presenter;

import com.unique.blockchain.nft.view.fragment.trade.view.ITradeTransactionCallbask;

public interface ITradeTranscationPresenter {
    void getData(String nftId,String sellMode,String miniDidprice,String startTime,
                String endTime,String timeInterval,String priceInterval,String currency,String chainInfo);

    void registerViewCallback(ITradeTransactionCallbask callback);

    void unRegisterViewCallback(ITradeTransactionCallbask callback);
}
