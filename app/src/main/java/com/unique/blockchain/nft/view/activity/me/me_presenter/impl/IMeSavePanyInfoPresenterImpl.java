package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeSaveCompanyInfoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeSaveCompanyInfoCallBack;

public class IMeSavePanyInfoPresenterImpl implements IMeSaveCompanyInfoPresenter {

    private IMeSaveCompanyInfoCallBack iMeSaveCompanyInfoCallBack = null;

    @Override
    public void getData(String data) {
        Log.e("FF4322","walletAdd:" + data);
        OkGo.post(UrlConstant.baseUrl + "api/companyAuth/saveCompanyInfo")
                .upJson(data)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF3333", "1111:" + jsonObject);
                        iMeSaveCompanyInfoCallBack.loadSaveCompanyInfoPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF3333", "4444");
                        iMeSaveCompanyInfoCallBack.loadSaveCompanyInfoPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF3333", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeSaveCompanyInfoCallBack callback) {
        iMeSaveCompanyInfoCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeSaveCompanyInfoCallBack callback) {
        iMeSaveCompanyInfoCallBack = null;
    }
}
