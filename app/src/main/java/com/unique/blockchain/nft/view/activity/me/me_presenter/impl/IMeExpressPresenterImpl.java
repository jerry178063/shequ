package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.ExpressBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeExpressPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAdminShouHuoExpressCallBack;

public class IMeExpressPresenterImpl implements IMeExpressPresenter {

    private IMeAdminShouHuoExpressCallBack iMeAdminShouHuoExpressCallBack = null;

    @Override
    public void getData(String nftId) {
        Log.e("FF4322", "nftId:" + nftId);
        OkGo.get(UrlConstant.baseUrl + "api/user/express")
//                .params("page",page)
//                .params("pageNum",pageNum)
                .params("nftId",nftId)
                .readTimeOut(10000)
                .execute(new JsonCallback<ExpressBean>() {
                    @Override
                    public void onSuccess(ExpressBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF4322", "1111:" + jsonObject);
                        iMeAdminShouHuoExpressCallBack.loadShouHuoExpressData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF4322", "4444");
                        iMeAdminShouHuoExpressCallBack.loadShouHuoExpressFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeAdminShouHuoExpressCallBack callback) {
        iMeAdminShouHuoExpressCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeAdminShouHuoExpressCallBack callback) {
        iMeAdminShouHuoExpressCallBack = null;
    }
}
