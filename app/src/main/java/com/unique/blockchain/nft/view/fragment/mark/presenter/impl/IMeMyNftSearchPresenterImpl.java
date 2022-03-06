package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMeMyNftSearchPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMeMyNftSearchCallBack;

public class IMeMyNftSearchPresenterImpl implements IMeMyNftSearchPresenter {

    private IMeMyNftSearchCallBack iMeMyNftAllCallBack = null;

    @Override
    public void getData(int page,int pageSum,String key,String WalletAddr) {
        Log.e("FF3332", "1111:" + page + "---" + pageSum);
        OkGo.get(UrlConstant.baseUrl + "api/nft/nftAllList")
                .params("pageSize",pageSum)
                .params("pageNum",page)
                .params("key",key)
                .params("walletAddr",WalletAddr)
                .readTimeOut(10000)
                .execute(new JsonCallback<MarkHotBean>() {
                    @Override
                    public void onSuccess(MarkHotBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332", "1111:" + jsonObject);
                        iMeMyNftAllCallBack.loadMyNftSearchData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMeMyNftAllCallBack.loadMyNftSearchFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeMyNftSearchCallBack callback) {
        iMeMyNftAllCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeMyNftSearchCallBack callback) {
        iMeMyNftAllCallBack = null;
    }
}
