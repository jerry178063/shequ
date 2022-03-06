package com.unique.blockchain.nft.view.activity.me.me_presenter;


import com.unique.blockchain.nft.domain.me.UpdateCautionBean;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeUpdateCautionCallBack;

public interface IMeUpdateCautionTwoPresenter {
    void getData(UpdateCautionBean updateCautionBean);

    void registerViewCallback(IMeUpdateCautionCallBack callback);

    void unRegisterViewCallback(IMeUpdateCautionCallBack callback);
}
