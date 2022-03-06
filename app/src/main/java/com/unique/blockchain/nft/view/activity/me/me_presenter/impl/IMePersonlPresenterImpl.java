package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.PersonlInfoBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMePersonlPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMePersonlInfoCallBack;

public class IMePersonlPresenterImpl implements IMePersonlPresenter {

    private IMePersonlInfoCallBack iMePersonlInfoCallBack = null;

    @Override
    public void getData(String did) {
        Log.e("FF4322","did:" + did);
        OkGo.get(UrlConstant.baseUrl + "api/wallet/getUserWallet")
                .params("did",did)
                .readTimeOut(10000)
                .execute(new JsonCallback<PersonlInfoBean>() {
                    @Override
                    public void onSuccess(PersonlInfoBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMePersonlInfoCallBack.loadPersonlInfoPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMePersonlInfoCallBack.loadPersonlInfoPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMePersonlInfoCallBack callback) {
        iMePersonlInfoCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMePersonlInfoCallBack callback) {
        iMePersonlInfoCallBack = null;
    }
}
