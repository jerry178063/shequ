package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.INoticePresenter;
import com.unique.blockchain.nft.view.activity.mark.view.INoticeCallBack;

public class INoticePresenterImpl implements INoticePresenter {

    private INoticeCallBack iNoticeCallBack = null;

    @Override
    public void getData(int page,int pageSum,String walletAdd,String type) {
        Log.e("FF3332", "1111:" + page + "---" + pageSum);
        OkGo.get(UrlConstant.baseUrl + "api/message/sysmessageList")
                .params("pageSize",pageSum)
                .params("pageNum",page)
//                .params("type",type)
//                .params("walletAddress",walletAdd)
                .readTimeOut(10000)
                .execute(new JsonCallback<NoticeMessageBean>() {
                    @Override
                    public void onSuccess(NoticeMessageBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iNoticeCallBack.loadNoticeData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iNoticeCallBack.loadNoticeFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(INoticeCallBack callback) {
        iNoticeCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(INoticeCallBack callback) {
        iNoticeCallBack = null;
    }
}
