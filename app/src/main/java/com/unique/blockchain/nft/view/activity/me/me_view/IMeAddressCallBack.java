package com.unique.blockchain.nft.view.activity.me.me_view;


import com.unique.blockchain.nft.domain.mark.CollectionBean;

public interface IMeAddressCallBack {
    void loadAddressPostData(CollectionBean personlInfoBean);
    void loadAddressPostFail();
}
