package com.unique.blockchain.nft.view.activity.mark.presenter;

import com.unique.blockchain.nft.view.activity.database.InquireDatabase;
import com.unique.blockchain.nft.view.activity.mark.base.BasePresenter;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.mark.model.Inquire_M;
import com.unique.blockchain.nft.view.activity.mark.view.Inquire_view;

public class Inquire_P extends BasePresenter<Inquire_view> {
    private final Inquire_M inquire_m;

    public Inquire_P() {
        inquire_m = new Inquire_M();
    }
    public void initList() {
        inquire_m.initInquire( new IBaseCall<InquireDatabase>() {

            @Override
            public void success(InquireDatabase inquireDatabase) {
                view.inquireList(inquireDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
}
