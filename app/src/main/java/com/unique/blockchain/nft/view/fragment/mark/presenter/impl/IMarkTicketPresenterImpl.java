package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkTicketPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkTicketCallBack;

public class IMarkTicketPresenterImpl implements IMarkTicketPresenter {

    private IMarkTicketCallBack iMarkTicketCallBack = null;

    @Override
    public void getData(int page,int pageSum,String sellMode,String type) {
        Log.e("FF3332", "1111:" + page + "---" + pageSum);
        OkGo.get(UrlConstant.baseUrl + "api/nft/sellList")
                .params("pageSize",pageSum)
                .params("pageNum",page)
                .params("type",type)
                .params("sellMode",sellMode)
                .readTimeOut(10000)
                .execute(new JsonCallback<QuanDatabase>() {
                    @Override
                    public void onSuccess(QuanDatabase jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332", "1111:" + jsonObject);
                        iMarkTicketCallBack.loadTicketData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkTicketCallBack.loadTicketFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkTicketCallBack callback) {
        iMarkTicketCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkTicketCallBack callback) {
        iMarkTicketCallBack = null;
    }
}
