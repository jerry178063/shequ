package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkUnCollectionPresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkUnCollectionCallBack;

public class IMarkUnCollectionPresenterImpl implements IMarkUnCollectionPresenter {

    private IMarkUnCollectionCallBack iMarkUnCollectionCallBack = null;

    @Override
    public void getData(String nftId,String uniqueAdress) {
        OkGo.post(UrlConstant.baseUrl + "api/collection/del")
                .params("nftId",nftId)
                .params("walletAddr",uniqueAdress)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMarkUnCollectionCallBack.loadUnCollectionPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkUnCollectionCallBack.loadUnCollectionPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkUnCollectionCallBack callback) {
        iMarkUnCollectionCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkUnCollectionCallBack callback) {
        iMarkUnCollectionCallBack = null;
    }
}
