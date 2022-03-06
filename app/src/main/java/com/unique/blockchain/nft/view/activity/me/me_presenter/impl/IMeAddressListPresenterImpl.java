package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressListPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressListCallBack;

public class IMeAddressListPresenterImpl implements IMeAddressListPresenter {

    private IMeAddressListCallBack iMeAddressListCallBack = null;

    @Override
    public void getData(String walletAddr) {
        Log.e("FF43222","did:" + walletAddr);
        OkGo.get(UrlConstant.baseUrl + "api/wallet/getUserAddressList")
                .params("walletAddr",walletAddr)
                .readTimeOut(10000)
                .execute(new JsonCallback<UserAdressListBean>() {
                    @Override
                    public void onSuccess(UserAdressListBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF43222", "1111:" + jsonObject);
                        iMeAddressListCallBack.loadAddressListPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF43222", "4444");
                        iMeAddressListCallBack.loadAddressListPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeAddressListCallBack callback) {
        iMeAddressListCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeAddressListCallBack callback) {
        iMeAddressListCallBack = null;
    }
}
