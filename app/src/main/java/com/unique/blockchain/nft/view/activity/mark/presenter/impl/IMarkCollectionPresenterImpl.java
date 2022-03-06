package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkCollectionPresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkCollectionCallBack;

public class IMarkCollectionPresenterImpl implements IMarkCollectionPresenter {

    private IMarkCollectionCallBack iMarkCollectionCallBack = null;

    @Override
    public void getData(String nftId,String uniqueAdress) {
        OkGo.post(UrlConstant.baseUrl + "api/collection/add")
                .params("nftId",nftId)
                .params("walletAddr",uniqueAdress)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMarkCollectionCallBack.loadCollectionPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkCollectionCallBack.loadCollectionPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkCollectionCallBack callback) {
        iMarkCollectionCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkCollectionCallBack callback) {
        iMarkCollectionCallBack = null;
    }
}
