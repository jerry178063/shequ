package com.unique.blockchain.nft.view.activity.mark.presenter;

import com.unique.blockchain.nft.view.activity.database.PopularDatabase;
import com.unique.blockchain.nft.view.activity.mark.base.BasePresenter;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.mark.model.popular_M;
import com.unique.blockchain.nft.view.activity.mark.view.popular_view;


public class popular_P extends BasePresenter<popular_view> {
    private final popular_M popular_m;

    public popular_P() {
        popular_m = new popular_M();
    }

    public void initList(String companyWalletAddr) {
        popular_m.init_popular(companyWalletAddr, new IBaseCall<PopularDatabase>() {

            @Override
            public void success(PopularDatabase popularDatabase) {
                view.inquireList(popularDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }


}
