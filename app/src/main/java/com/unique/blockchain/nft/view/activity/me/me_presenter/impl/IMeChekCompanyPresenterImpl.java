package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.CheckCompanyBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeCheckCompanyPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeCheckCompanyCallBack;

public class IMeChekCompanyPresenterImpl implements IMeCheckCompanyPresenter {

    private IMeCheckCompanyCallBack iMeCheckCompanyCallBack = null;

    @Override
    public void getData(String walletAddr) {
        Log.e("FF4322","walletAdd:" + walletAddr);
        OkGo.get(UrlConstant.baseUrl + "api/companyAuth/checkCompanyInfoIfpay")
                .params("walletAddr",walletAddr)
                .readTimeOut(10000)
                .execute(new JsonCallback<CheckCompanyBean>() {
                    @Override
                    public void onSuccess(CheckCompanyBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF3333", "1111:" + jsonObject);
                        iMeCheckCompanyCallBack.loadCheckCompanyPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF3333", "4444");
                        iMeCheckCompanyCallBack.loadCheckCompanyPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF3333", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeCheckCompanyCallBack callback) {
        iMeCheckCompanyCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeCheckCompanyCallBack callback) {
        iMeCheckCompanyCallBack = null;
    }
}
