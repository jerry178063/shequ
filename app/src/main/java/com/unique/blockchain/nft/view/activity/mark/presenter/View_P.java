package com.unique.blockchain.nft.view.activity.mark.presenter;

import com.unique.blockchain.nft.view.activity.database.QarticularsDatabase;
import com.unique.blockchain.nft.view.activity.mark.base.BasePresenter;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.mark.model.View_M;
import com.unique.blockchain.nft.view.activity.mark.view.View_view;

public class View_P extends BasePresenter<View_view> {

    private final View_M viewM;

    public View_P() {
        viewM = new View_M();
    }
    public void initList(String stringNftId) {
        viewM.initList(stringNftId, new IBaseCall<QarticularsDatabase>() {

            @Override
            public void success(QarticularsDatabase qarticularsDatabase) {
                view.List(qarticularsDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }

}
