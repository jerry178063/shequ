package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkBannerPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkBannerCallBack;

public class IMarkBannerPresenterImpl implements IMarkBannerPresenter {

    private IMarkBannerCallBack iMarkBannerCallBack = null;

    @Override
    public void getData(int page,int pageSum,String uniqueAdress) {
        OkGo.get(UrlConstant.baseUrl + "api/advertManagement/hotCompany")
                .params("type",Integer.valueOf("1"))
                .readTimeOut(10000)
                .execute(new JsonCallback<BannerDatabase>() {
                    @Override
                    public void onSuccess(BannerDatabase jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332gg", "1111:" + new Gson().toJson(jsonObject));
                        iMarkBannerCallBack.loadBannerData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332gg", "4444");
                        iMarkBannerCallBack.loadBannerFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332gg", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkBannerCallBack callback) {
        iMarkBannerCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkBannerCallBack callback) {
        iMarkBannerCallBack = null;
    }
}
