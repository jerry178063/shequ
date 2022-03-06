package com.unique.blockchain.nft.view.fragment.trade.view;

import com.unique.blockchain.nft.domain.trade.GoTradeDetailBean;

public interface ITradeListDetailCallbask {
    void loadData(GoTradeDetailBean goTradeBean);
    void loadFail();
}
