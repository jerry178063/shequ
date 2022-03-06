package com.unique.blockchain.nft.view.fragment.trade.view;

import com.unique.blockchain.nft.domain.trade.TradeDetailPriceBean;

public interface ITradeTransactionGuDingCallbask {
    void loadTradeGuDingData(TradeDetailPriceBean goTradeCationBean);
    void loadTradeGuDingFail(String msg);
}
