package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.UpdateCompanyBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAgainCommitPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAgainCommitCallBack;

public class IMeAgainCommitPresenterImpl implements IMeAgainCommitPresenter {

    private IMeAgainCommitCallBack iMeGetCompanyInfoCallBack = null;

    @Override
    public void getData(UpdateCompanyBean updateCompanyBean) {
        Log.e("FF4322","updateCompanyBean:" + new Gson().toJson(updateCompanyBean));
        OkGo.post(UrlConstant.baseUrl + "api/companyAuth/updateCompanyInfo")
                .upJson(updateCompanyBean.toString())
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMeGetCompanyInfoCallBack.loadAgainCommitPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMeGetCompanyInfoCallBack.loadAgainCommitPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeAgainCommitCallBack callback) {
        iMeGetCompanyInfoCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeAgainCommitCallBack callback) {
        iMeGetCompanyInfoCallBack = null;
    }
}
