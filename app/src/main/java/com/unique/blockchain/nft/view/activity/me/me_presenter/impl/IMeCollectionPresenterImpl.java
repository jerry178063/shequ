package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeCollectionPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeCollectionCallBack;

public class IMeCollectionPresenterImpl implements IMeCollectionPresenter {

    private IMeCollectionCallBack iMeCollectionCallBack = null;

    @Override
    public void getData(String collection,String address,int page,int pageNum) {
        Log.e("FF4322","collection:" + collection);
        Log.e("FF4322","page:" + page);
        Log.e("FF4322","pageNum:" + pageNum);
        Log.e("FF4322","companyWalletAddr:" + address);
        OkGo.get(UrlConstant.baseUrl + "api/collection/list")
                .params("pageSize",pageNum)
                .params("pageNum",page)
                .params("key",collection)
                .params("walletAddr",address)
                .readTimeOut(10000)
                .execute(new JsonCallback<QuanDatabase>() {
                    @Override
                    public void onSuccess(QuanDatabase jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF4322", "1111:" + jsonObject);
                        iMeCollectionCallBack.loadCollectionData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF4322", "4444");
                        iMeCollectionCallBack.loadCollectionFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeCollectionCallBack callback) {
        iMeCollectionCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeCollectionCallBack callback) {
        iMeCollectionCallBack = null;
    }
}
