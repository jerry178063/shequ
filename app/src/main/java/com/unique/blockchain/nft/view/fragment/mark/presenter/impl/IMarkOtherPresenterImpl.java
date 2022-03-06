package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkOtherPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkOtherCallBack;

public class IMarkOtherPresenterImpl implements IMarkOtherPresenter {

    private IMarkOtherCallBack iMarkOtherCallBack = null;

    @Override
    public void getData(int page,int pageSum,String uniqueAdress,String type) {
        OkGo.get(UrlConstant.baseUrl + "api/nft/sellList")
                .params("pageSize",pageSum)
                .params("pageNum",page)
                .params("type",type)
                .readTimeOut(10000)
                .execute(new JsonCallback<QuanDatabase>() {
                    @Override
                    public void onSuccess(QuanDatabase jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332", "1111:" + jsonObject);
                        iMarkOtherCallBack.loadData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkOtherCallBack.loadFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkOtherCallBack callback) {
        iMarkOtherCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkOtherCallBack callback) {
        iMarkOtherCallBack = null;
    }
}
