package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.JingBiaoBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IJingBiaoPresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IJingBiaoCallBack;

public class IJingBiaoPresenterImpl implements IJingBiaoPresenter {

    private IJingBiaoCallBack iJingBiaoCallBack = null;

    @Override
    public void getData(String nftId) {
        OkGo.get(UrlConstant.baseUrl + "api/transaction/auction")
                .params("nftId",nftId)
                .readTimeOut(10000)
                .execute(new JsonCallback<JingBiaoBean>() {
                    @Override
                    public void onSuccess(JingBiaoBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iJingBiaoCallBack.loadJingBiaoData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iJingBiaoCallBack.loadJingBiaoFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IJingBiaoCallBack callback) {
        iJingBiaoCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IJingBiaoCallBack callback) {
        iJingBiaoCallBack = null;
    }
}
