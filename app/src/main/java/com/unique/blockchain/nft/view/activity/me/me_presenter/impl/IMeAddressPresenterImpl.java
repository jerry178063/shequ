package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressCallBack;

public class IMeAddressPresenterImpl implements IMeAddressPresenter {

    private IMeAddressCallBack iMeAddressCallBack = null;

    @Override
    public void getData(String data) {
        Log.e("FF4322","did:" + data);
        OkGo.post(UrlConstant.baseUrl + "api/wallet/addAddress")
                .upJson(data)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF4322", "1111:" + jsonObject);
                        iMeAddressCallBack.loadAddressPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF4322", "4444");
                        iMeAddressCallBack.loadAddressPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeAddressCallBack callback) {
        iMeAddressCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeAddressCallBack callback) {
        iMeAddressCallBack = null;
    }
}
