package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeGetCompanyInfoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeGetCompanyInfoCallBack;

public class IMeGetPanyInfoPresenterImpl implements IMeGetCompanyInfoPresenter {

    private IMeGetCompanyInfoCallBack iMeGetCompanyInfoCallBack = null;

    @Override
    public void getData(String walletAdd) {
        Log.e("FF33325","walletAdd:" + walletAdd);
        OkGo.get(UrlConstant.baseUrl + "api/companyAuth/getCompanyInfo")
                .params("walletAddr",walletAdd)
                .readTimeOut(10000)
                .execute(new JsonCallback<CompanyInfoBean>() {
                    @Override
                    public void onSuccess(CompanyInfoBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMeGetCompanyInfoCallBack.loadGetCompanyInfoPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMeGetCompanyInfoCallBack.loadGetCompanyInfoPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeGetCompanyInfoCallBack callback) {
        iMeGetCompanyInfoCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeGetCompanyInfoCallBack callback) {
        iMeGetCompanyInfoCallBack = null;
    }
}
