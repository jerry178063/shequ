package com.unique.blockchain.nft.view.fragment.trade.view;

import com.unique.blockchain.nft.domain.trade.GoTradeCationBean;

public interface ITradeCationCallbask {
    void loadData(GoTradeCationBean goTradeCationBean);
    void loadFail();
}
