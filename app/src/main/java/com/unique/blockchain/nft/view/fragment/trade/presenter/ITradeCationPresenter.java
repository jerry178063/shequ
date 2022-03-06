package com.unique.blockchain.nft.view.fragment.trade.presenter;

import com.unique.blockchain.nft.view.fragment.trade.view.ITradeCationCallbask;

public interface ITradeCationPresenter {
    void getData(int page,String companyId);

    void registerViewCallback(ITradeCationCallbask callback);

    void unRegisterViewCallback(ITradeCationCallbask callback);
}
