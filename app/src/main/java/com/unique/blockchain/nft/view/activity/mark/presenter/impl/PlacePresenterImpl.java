package com.unique.blockchain.nft.view.activity.mark.presenter.impl;

import android.util.Log;

import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IPlacePresenter;
import com.unique.blockchain.nft.view.activity.mark.view.IProvinceCallbask;

public class PlacePresenterImpl implements IPlacePresenter {

    private IProvinceCallbask mCallbask = null;
    @Override
    public void getProvinList() {


        OkGo.get(UrlConstant.baseUrl + "system/rel/getInvitationCode")
                .params("recommendInvitationCode", "qy7he5")
                .readTimeOut(10000)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FFF444", "1111:" + jsonObject);
                        mCallbask.loadedData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF444", "4444");
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

}
