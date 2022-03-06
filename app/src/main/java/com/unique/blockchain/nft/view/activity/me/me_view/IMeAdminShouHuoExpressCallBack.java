package com.unique.blockchain.nft.view.activity.me.me_view;

import com.unique.blockchain.nft.domain.me.ExpressBean;

public interface IMeAdminShouHuoExpressCallBack {
    void loadShouHuoExpressData(ExpressBean expressBean);
    void loadShouHuoExpressFail();
}
