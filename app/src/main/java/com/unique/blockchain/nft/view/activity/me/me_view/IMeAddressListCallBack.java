package com.unique.blockchain.nft.view.activity.me.me_view;


import com.unique.blockchain.nft.domain.me.UserAdressListBean;

public interface IMeAddressListCallBack {
    void loadAddressListPostData(UserAdressListBean userAdressListBean);
    void loadAddressListPostFail();
}
