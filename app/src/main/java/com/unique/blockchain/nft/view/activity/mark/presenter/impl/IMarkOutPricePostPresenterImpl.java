package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPricePostPresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPricePostCallBack;

public class IMarkOutPricePostPresenterImpl implements IMarkOutPricePostPresenter {

    private IMarkOutPricePostCallBack iMarkOutPricePostCallBack = null;

    @Override
    public void getData(String nftId,String orderId,String uniqueAdress,String price,String chainInfo) {
        OkGo.post(UrlConstant.baseUrl + "api/transaction/fixedPrice")
                .params("nftId",nftId)
                .params("price",price)
                .params("chainInfo",chainInfo)
                .params("walletAddr",uniqueAdress)
                .params("orderId",orderId)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMarkOutPricePostCallBack.loadOutPricePostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkOutPricePostCallBack.loadOutPricePostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkOutPricePostCallBack callback) {
        iMarkOutPricePostCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkOutPricePostCallBack callback) {
        iMarkOutPricePostCallBack = null;
    }
}
