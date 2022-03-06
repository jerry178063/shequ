package com.unique.blockchain.nft.view.activity.me.me_presenter;

import com.unique.blockchain.nft.view.activity.mark.base.BasePresenter;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.me.baen.JiaonftDatabase;
import com.unique.blockchain.nft.view.activity.me.me_model.All_model;
import com.unique.blockchain.nft.view.activity.me.me_view.All_view;

public class All_presenter extends BasePresenter<All_view> {
    private final All_model all_model;

    public All_presenter() {
        all_model = new All_model();
    }
    public void initList(String all_ownerWallet) {
        all_model.initnftAll( all_ownerWallet,new IBaseCall<JiaonftDatabase>() {
            @Override
            public void success(JiaonftDatabase jiaonftDatabase) {
                view.List(jiaonftDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
}
