package com.unique.blockchain.nft.view.activity.me.me_view;


import com.unique.blockchain.nft.domain.me.CompanyInfoBean;

public interface IMeGetCompanyInfoCallBack {
    void loadGetCompanyInfoPostData(CompanyInfoBean companyInfoBean);
    void loadGetCompanyInfoPostFail();
}
