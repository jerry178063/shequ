package com.unique.blockchain.nft.view.activity.me.presenter.impl;

import android.util.Log;

import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.presenter.IPledgeCallBack;
import com.unique.blockchain.nft.view.activity.me.presenter.IPledgeService;

/**
 * 质押保证金实现类
 * @author panhao
 */
public class IPledgeServiceImpl implements IPledgeService {

    /**
     * 质押保证金回调接口类.
     */
    private IPledgeCallBack iPledgeCallBack;

    /**
     * 获取当前用户质押的保证金方法
     */
    @Override
    public void getCurrentUserPledge(String walletAddr) {
        OkGo.get(UrlConstant.baseUrl + "api/companyAuth/checkCompanyInfoIfpay")
                .params("walletAddr",walletAddr)
                .readTimeOut(10000)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("Pledge200",  jsonObject.toString());
                        iPledgeCallBack.succ(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("PledgeError", message);
                        iPledgeCallBack.fail(code, message);
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("PledgeError", e.getMessage());
                    }
                });

    }

    /**
     *  注册回调服务接口对象方法
     * @param callBack
     */
    @Override
    public void setViewCallback(IPledgeCallBack callBack) {
        this.iPledgeCallBack = callBack;
    }

    /**
     *  销毁回调服务接口对象方法
     * @param callBack
     */
    @Override
    public void destroyViewCallback(IPledgeCallBack callBack) {
        this.iPledgeCallBack = null;
    }
}
