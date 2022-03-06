package com.unique.blockchain.nft.view.activity.me.me_presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.UpdateCautionBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeUpdateCautionPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeUpdateCautionTwoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeUpdateCautionCallBack;

public class IMeUpdateCautionPresenterImpl implements IMeUpdateCautionPresenter {

    private IMeUpdateCautionCallBack iMeUpdateCautionCallBack = null;

    @Override
    public void getData(UpdateCautionBean data) {
        Log.e("FF4322","walletAdd:" + data);
        OkGo.get(UrlConstant.baseUrl + "api/companyAuth/updateCautionMoney")
                .params("walletAddr",data.getWalletAddr())
                .params("type",data.getType())
                .params("cautionMoney",data.getCautionMoney())
                .params("chainInfo",data.getChainInfo())
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);
                        iMeUpdateCautionCallBack.loadUpdateCautionPostData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");
                        iMeUpdateCautionCallBack.loadUpdateCautionPostFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMeUpdateCautionCallBack callback) {
        iMeUpdateCautionCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMeUpdateCautionCallBack callback) {
        iMeUpdateCautionCallBack = null;
    }
}
