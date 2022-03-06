package com.unique.blockchain.nft.view.fragment.trade.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.trade.TradeDetailPriceBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeTranscationGuDingPresenter;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeTransactionGuDingCallbask;

public class ITradeTranscationGuDingPresenterImpl implements ITradeTranscationGuDingPresenter {

    private ITradeTransactionGuDingCallbask iTradeTransactionCallbask = null;

    @Override
    public void getData(String nftId, String startPrice, String endPrice, String startTime, String endTime, String currency, String chainInfo) {
        Log.e("FFF44455","nftId:" + nftId);
        Log.e("FFF44455","startPrice:" + startPrice);
        Log.e("FFF44455","endPrice:" + endPrice);
        Log.e("FFF44455","startTime:" + startTime);
        Log.e("FFF44455","endTime:" + endTime);
        Log.e("FFF44455","currency:" + currency);
        Log.e("FFF44455","chainInfo:" + chainInfo);
        Log.e("FFF44455","sellMode:" + "1");
        OkGo.post(UrlConstant.baseUrl + "api/transaction/transaction")
                .params("nftId", nftId)
                .params("startPrice", startPrice)
                .params("endPrice", endPrice)
                .params("startTime", startTime)
                .params("endTime", endTime)
                .params("currency", currency)
                .params("chainInfo", chainInfo)
                .params("sellMode","1")
                .readTimeOut(10000)
                .execute(new JsonCallback<TradeDetailPriceBean>() {
                    @Override
                    public void onSuccess(TradeDetailPriceBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF44455", "1111:" + jsonObject);
                        if(jsonObject.getCode() == 200) {
                            iTradeTransactionCallbask.loadTradeGuDingData(jsonObject);
                        }else {
                            iTradeTransactionCallbask.loadTradeGuDingFail(jsonObject.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF44455", "4444");
                        iTradeTransactionCallbask.loadTradeGuDingFail(message);
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF44455", "onError");
                    }
                });
    }



    @Override
    public void registerViewCallback(ITradeTransactionGuDingCallbask callback) {
        iTradeTransactionCallbask = callback;
    }

    @Override
    public void unRegisterViewCallback(ITradeTransactionGuDingCallbask callback) {
        iTradeTransactionCallbask = null;
    }
}
