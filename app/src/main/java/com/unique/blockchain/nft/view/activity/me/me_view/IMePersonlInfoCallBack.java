package com.unique.blockchain.nft.view.activity.me.me_view;


import com.unique.blockchain.nft.domain.me.PersonlInfoBean;

public interface IMePersonlInfoCallBack {
    void loadPersonlInfoPostData(PersonlInfoBean personlInfoBean);
    void loadPersonlInfoPostFail();
}
