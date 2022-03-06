package com.unique.blockchain.nft.view.activity.me.me_view;


import com.unique.blockchain.nft.domain.mark.CollectionBean;

public interface IMeSaveCompanyInfoCallBack {
    void loadSaveCompanyInfoPostData(CollectionBean companyInfoBean);
    void loadSaveCompanyInfoPostFail();
}
