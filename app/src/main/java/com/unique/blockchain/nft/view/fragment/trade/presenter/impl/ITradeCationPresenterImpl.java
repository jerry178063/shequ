package com.unique.blockchain.nft.view.fragment.trade.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.trade.GoTradeCationBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeCationPresenter;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeCationCallbask;

public class ITradeCationPresenterImpl implements ITradeCationPresenter {

    private ITradeCationCallbask iTradeCationCallbask = null;

    @Override
    public void getData(int page,String id) {
        Log.e("FFF555", "id:" + id);
        OkGo.get(UrlConstant.baseUrl + "api/company/cautionMoney")
                .params("nftId", id)
                .readTimeOut(10000)
                .execute(new JsonCallback<GoTradeCationBean>() {
                    @Override
                    public void onSuccess(GoTradeCationBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF555", "1111:" + jsonObject);
                        iTradeCationCallbask.loadData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF555", "4444");
                        iTradeCationCallbask.loadFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF555", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(ITradeCationCallbask callback) {
        iTradeCationCallbask = callback;
    }

    @Override
    public void unRegisterViewCallback(ITradeCationCallbask callback) {
        iTradeCationCallbask = null;
    }


}
