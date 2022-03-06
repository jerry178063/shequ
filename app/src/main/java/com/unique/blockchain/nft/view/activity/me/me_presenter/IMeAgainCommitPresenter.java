package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.domain.me.UpdateCompanyBean;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAgainCommitCallBack;

public interface IMeAgainCommitPresenter {
    void getData(UpdateCompanyBean updateCompanyBean);

    void registerViewCallback(IMeAgainCommitCallBack callback);

    void unRegisterViewCallback(IMeAgainCommitCallBack callback);
}
