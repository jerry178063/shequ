package com.unique.blockchain.nft.view.fragment.trade.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.trade.GoTradeDetailBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeDetailPresenter;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeListDetailCallbask;

public class ITradeDetailPresenterImpl implements ITradeDetailPresenter {

    private ITradeListDetailCallbask iTradeListDetailCallbask = null;

    @Override
    public void getData(int page,String orderId,String nftId,String uniqueAdress) {
        Log.e("FFF44456", "nftId:" + nftId);
        Log.e("FFF44456", "uniqueAdress:" + uniqueAdress);
        Log.e("FFF44456", "orderId:" + orderId);
        OkGo.get(UrlConstant.baseUrl + UrlConstant.TRADE_DETAIL)
                .params("nftId", nftId + "")
                .params("sellSet", "sell")
                .params("walletAddr", uniqueAdress)
                .params("orderId",orderId + "")
                .readTimeOut(10000)
                .execute(new JsonCallback<GoTradeDetailBean>() {
                    @Override
                    public void onSuccess(GoTradeDetailBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF44456", "1111:" + jsonObject);
                        iTradeListDetailCallbask.loadData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF44456", "4444");
                        iTradeListDetailCallbask.loadFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF44456", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(ITradeListDetailCallbask callback) {
        iTradeListDetailCallbask = callback;
    }

    @Override
    public void unRegisterViewCallback(ITradeListDetailCallbask callback) {
        iTradeListDetailCallbask = null;
    }
}
