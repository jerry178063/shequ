package com.unique.blockchain.nft.view.fragment.trade.view;

import com.unique.blockchain.nft.domain.trade.GoTradeBean;

public interface ITradeHomeCallbask {
    void loadData(GoTradeBean goTradeBean);
    void loadFail();
}
