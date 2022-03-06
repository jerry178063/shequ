package com.unique.blockchain.nft.view.activity.me.presenter.impl;

import android.util.Log;

import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.MerchantRegisterBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.presenter.IRegisterStoreCallBack;
import com.unique.blockchain.nft.view.activity.me.presenter.IRegisterStoreService;

/**
 * 注册商家服务实现类.
 * @author 潘浩
 * @Date 2021-09-15
 */
public class IRegisterStoreServiceImpl implements IRegisterStoreService {

    /**
     * 回调服务接口
     */
    private IRegisterStoreCallBack iRegisterStoreCallBack;

    /**
     * 注册商家接口实现方法
     * @param merchantRegisterBean
     */
    @Override
    public void registerStroe(MerchantRegisterBean merchantRegisterBean) {
        Log.e("RegisterInfo",merchantRegisterBean.toString());
        OkGo.post(UrlConstant.baseUrl + "api/companyAuth/saveCompanyInfo")
                .upJson(merchantRegisterBean.toString())
                .readTimeOut(10000)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("200",  jsonObject.toString());
                        iRegisterStoreCallBack.succ(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("RegisterError", message);
                        iRegisterStoreCallBack.fail(code,message);
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("RegisterError", e.getMessage());
                    }
                });
    }

    /**
     *  注册回调服务接口对象方法
     * @param callBack
     */
    @Override
    public void setViewCallback(IRegisterStoreCallBack callBack) {
        this.iRegisterStoreCallBack = callBack;
    }

    /**
     *  销毁回调服务接口对象方法
     * @param callBack
     */
    @Override
    public void destroyViewCallback(IRegisterStoreCallBack callBack) {
        this.iRegisterStoreCallBack = null;
    }
}
