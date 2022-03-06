package com.unique.blockchain.nft.view.fragment.trade.presenter;

import com.unique.blockchain.nft.view.fragment.trade.view.ITradeTransactionGuDingCallbask;

public interface ITradeTranscationGuDingPresenter {
    void getData(String nftId,String startPrice,String endPrice,String startTime,
                String endTime,String currency,String chainInfo);

    void registerViewCallback(ITradeTransactionGuDingCallbask callback);

    void unRegisterViewCallback(ITradeTransactionGuDingCallbask callback);
}
