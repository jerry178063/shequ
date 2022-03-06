package com.unique.blockchain.nft.view.activity.me.me_view;


import com.unique.blockchain.nft.domain.me.CheckCompanyBean;

public interface IMeCheckCompanyCallBack {
    void loadCheckCompanyPostData(CheckCompanyBean collectionBean);
    void loadCheckCompanyPostFail();
}
