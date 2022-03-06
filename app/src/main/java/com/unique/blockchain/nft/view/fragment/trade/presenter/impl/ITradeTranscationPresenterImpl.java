package com.unique.blockchain.nft.view.fragment.trade.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.trade.TradeDetailPriceBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeTranscationPresenter;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeTransactionCallbask;

public class ITradeTranscationPresenterImpl implements ITradeTranscationPresenter {

    private ITradeTransactionCallbask iTradeTransactionCallbask = null;

    @Override
    public void getData(String nftId, String sellMode, String miniDidprice, String startTime, String endTime, String timeInterval, String priceInterval, String currency, String chainInfo) {
        Log.e("FFF44455","nftId:" + nftId);
        Log.e("FFF44455","sellMode:" + sellMode);
        Log.e("FFF44455","miniDidPrice:" + miniDidprice);
        Log.e("FFF44455","startTime:" + startTime);
        Log.e("FFF44455","endTime:" + endTime);
        Log.e("FFF44455","auctionTimeInterval:" + timeInterval);
        Log.e("FFF44455","auctionPriceInterval:" + priceInterval);
        Log.e("FFF44455","currency:" + currency);
        Log.e("FFF44455","chainInfo:" + chainInfo);
        OkGo.post(UrlConstant.baseUrl + "api/transaction/transaction")
                .params("nftId", nftId)
                .params("sellMode", sellMode)
                .params("miniDidPrice", miniDidprice)
                .params("startTime", startTime)
                .params("endTime", endTime)
                .params("auctionTimeInterval", timeInterval)
                .params("auctionPriceInterval", priceInterval)
                .params("currency", currency)
                .params("chainInfo", chainInfo)
                .readTimeOut(10000)
                .execute(new JsonCallback<TradeDetailPriceBean>() {
                    @Override
                    public void onSuccess(TradeDetailPriceBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF44455", "1111:" + jsonObject);
                        if(jsonObject.getCode() == 200) {
                            iTradeTransactionCallbask.loadTradeData(jsonObject);
                        }else {
                            iTradeTransactionCallbask.loadTradeFail(jsonObject.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF44455", "4444");
                        iTradeTransactionCallbask.loadTradeFail(message);
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF44455", "onError");
                    }
                });
    }


    @Override
    public void registerViewCallback(ITradeTransactionCallbask callback) {
        iTradeTransactionCallbask = callback;
    }

    @Override
    public void unRegisterViewCallback(ITradeTransactionCallbask callback) {
        iTradeTransactionCallbask = null;
    }
}
