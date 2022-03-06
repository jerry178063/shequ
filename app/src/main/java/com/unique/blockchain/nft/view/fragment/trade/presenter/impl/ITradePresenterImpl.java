package com.unique.blockchain.nft.view.fragment.trade.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.trade.GoTradeBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradePresenter;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeHomeCallbask;

public class ITradePresenterImpl implements ITradePresenter {

    private ITradeHomeCallbask iTradeHomeCallbask = null;

    @Override
    public void getData(int page,int pageSum,String key,String uniqueAdress) {
        Log.e("FFF444", "uniqueAdress:" + uniqueAdress + "---key:" + key);
        OkGo.get(UrlConstant.baseUrl + "api/nft/transactionList")
//                .params("walletAddr", uniqueAdress)
                .params("pageSize",pageSum)
                .params("pageNum",page)
                .params("key",key)
                .params("walletAddr",uniqueAdress)
                .readTimeOut(10000)
                .execute(new JsonCallback<GoTradeBean>() {
                    @Override
                    public void onSuccess(GoTradeBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF444", "jsonObject:" + new Gson().toJson(jsonObject));
                        iTradeHomeCallbask.loadData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF444", "4444");
                        iTradeHomeCallbask.loadFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF444", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(ITradeHomeCallbask callback) {
        iTradeHomeCallbask = callback;
    }

    @Override
    public void unRegisterViewCallback(ITradeHomeCallbask callback) {
        iTradeHomeCallbask = null;
    }
}
