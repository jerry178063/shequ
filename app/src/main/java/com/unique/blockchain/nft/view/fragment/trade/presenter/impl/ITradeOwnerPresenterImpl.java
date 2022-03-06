package com.unique.blockchain.nft.view.fragment.trade.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.trade.GoTradeOwnerBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.trade.presenter.IOwnerPresenter;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeOwnerCallbask;

public class ITradeOwnerPresenterImpl implements IOwnerPresenter {

    private ITradeOwnerCallbask iTradeOwnerCallbask = null;

    @Override
    public void getData(int page,String ntfId) {
        OkGo.get(UrlConstant.baseUrl + "api/user/ownerList")
                .params("nftId", ntfId)
                .readTimeOut(10000)
                .execute(new JsonCallback<GoTradeOwnerBean>() {
                    @Override
                    public void onSuccess(GoTradeOwnerBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF555", "1111:" + jsonObject);
                        iTradeOwnerCallbask.loadData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF555", "4444");
                        iTradeOwnerCallbask.loadFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF555", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(ITradeOwnerCallbask callback) {
        iTradeOwnerCallbask = callback;
    }

    @Override
    public void unRegisterViewCallback(ITradeOwnerCallbask callback) {
        iTradeOwnerCallbask = null;
    }

}
