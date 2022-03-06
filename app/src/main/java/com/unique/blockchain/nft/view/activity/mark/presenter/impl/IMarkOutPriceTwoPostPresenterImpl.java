package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPriceTwoPostPresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceTwoPostCallBack;

public class IMarkOutPriceTwoPostPresenterImpl implements IMarkOutPriceTwoPostPresenter {

    private IMarkOutPriceTwoPostCallBack iMarkOutPriceTwoPostCallBack = null;

    @Override
    public void getData(String nftId,String orderId,String uniqueAdress,String price,String chainInfo) {
        OkGo.post(UrlConstant.baseUrl + "api/transaction/auction")
                .params("nftId",nftId)
                .params("price",price)
                .params("orderId",orderId)
                .params("chainInfo",chainInfo)
                .params("walletAddr",uniqueAdress)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332544", "1111:" + jsonObject);
                        iMarkOutPriceTwoPostCallBack.loadOutPriceTwoPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkOutPriceTwoPostCallBack.loadOutPriceTwoPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkOutPriceTwoPostCallBack callback) {
        iMarkOutPriceTwoPostCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkOutPriceTwoPostCallBack callback) {
        iMarkOutPriceTwoPostCallBack = null;
    }
}
