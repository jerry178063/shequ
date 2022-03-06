package com.unique.blockchain.nft.view.fragment.trade.view;

import com.unique.blockchain.nft.domain.trade.GoTradeOwnerBean;

public interface ITradeOwnerCallbask {
    void loadData(GoTradeOwnerBean goTradeOwnerBean);
    void loadFail();
}
