package com.unique.blockchain.nft.view.fragment.trade.view;

import com.unique.blockchain.nft.domain.trade.TradeDetailPriceBean;

public interface ITradeTransactionCallbask {
    void loadTradeData(TradeDetailPriceBean goTradeCationBean);
    void loadTradeFail(String msg);
}
