package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkReMenzPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkReMenzCallBack;

public class IMarkReMenzPresenterImpl implements IMarkReMenzPresenter {

    private IMarkReMenzCallBack iMarkReMenzCallBack = null;

    @Override
    public void getData(int page,int pageSum,String uniqueAdress,String type) {
        Log.e("FF3332", "1111:" + page + "---" + pageSum);
        OkGo.get(UrlConstant.baseUrl + "api/company/hotCompany")
//                .params("pageSize",pageSum)
//                .params("pageNum",page)
//                .params("type",type)
                .readTimeOut(10000)
                .execute(new JsonCallback<ReDatabase>() {
                    @Override
                    public void onSuccess(ReDatabase jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332", "1111:" + jsonObject);
                        iMarkReMenzCallBack.loadReMenzData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMarkReMenzCallBack.loadReMenzFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkReMenzCallBack callback) {
        iMarkReMenzCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkReMenzCallBack callback) {
        iMarkReMenzCallBack = null;
    }
}
