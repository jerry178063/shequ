package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.MarkOutPriceBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPricePresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceCallBack;

public class IMarkOutPricePresenterImpl implements IMarkOutPricePresenter {

    private IMarkOutPriceCallBack iMarkOutPriceCallBack = null;

    @Override
    public void getData(String nftId,String uniqueAdress) {
        Log.e("FF33325", "nftId:" + nftId);
        Log.e("FF33325", "uniqueAdress:" + uniqueAdress);

        OkGo.get(UrlConstant.baseUrl + "api/transaction/nftOfferInfo")
                .params("nftId",nftId)
                .readTimeOut(10000)
                .execute(new JsonCallback<MarkOutPriceBean>() {
                    @Override
                    public void onSuccess(MarkOutPriceBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMarkOutPriceCallBack.loadOutPriceData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkOutPriceCallBack.loadOutPriceFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkOutPriceCallBack callback) {
        iMarkOutPriceCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkOutPriceCallBack callback) {
        iMarkOutPriceCallBack = null;
    }
}
