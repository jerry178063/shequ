package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkSearchPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkCollectCallBack;

public class IMarkSearchPresenterImpl implements IMarkSearchPresenter {

    private IMarkCollectCallBack iMarkCollectCallBack = null;

    @Override
    public void getData(int page,int pageSum,String key,String collect,String walletAdd,String sellMode) {
        Log.e("FF3332f", "collect:" + collect);
        Log.e("FF3332f", "walletAdd:" + walletAdd);
        Log.e("FF3332f", "key:" + key);
        OkGo.get(UrlConstant.baseUrl + "api/nft/sellList")
                .params("pageSize",pageSum)
                .params("pageNum",page)
                .params("key",key)
                .params("companyWalletAddr",walletAdd)
                .params("collection",collect)
                .params("sellMode",sellMode)
                .readTimeOut(10000)
                .execute(new JsonCallback<QuanDatabase>() {
                    @Override
                    public void onSuccess(QuanDatabase jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332f", "1111:" + jsonObject);
                        iMarkCollectCallBack.loadCollectData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332f", "4444");
                        iMarkCollectCallBack.loadCollectFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkCollectCallBack callback) {
        iMarkCollectCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkCollectCallBack callback) {
        iMarkCollectCallBack = null;
    }
}
