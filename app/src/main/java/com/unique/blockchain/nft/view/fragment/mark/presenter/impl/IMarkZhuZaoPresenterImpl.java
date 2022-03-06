package com.unique.blockchain.nft.view.fragment.mark.presenter.impl;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkZhuZaoPresenter;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkZhuZaoCallBack;

public class IMarkZhuZaoPresenterImpl implements IMarkZhuZaoPresenter {

    private IMarkZhuZaoCallBack iMarkZhuZaoCallBack = null;

    @Override
    public void getData(int page,int pageSum,String companyWalletAddr) {
        Log.e("FF3332g", "companyWalletAddr:" + companyWalletAddr);
        OkGo.get(UrlConstant.baseUrl + "api/nft/sellList")
                .params("pageSize",pageSum)
                .params("pageNum",page)
                .params("companyWalletAddr",companyWalletAddr)
                .readTimeOut(10000)
                .execute(new JsonCallback<MarkHotBean>() {
                    @Override
                    public void onSuccess(MarkHotBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF3332g", "1111:" + jsonObject);
                        iMarkZhuZaoCallBack.loadZhuZaoData(jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332g", "4444");
                        iMarkZhuZaoCallBack.loadZhuZaoFail();
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332g", "onError");
                    }
                });
    }

    @Override
    public void registerViewCallback(IMarkZhuZaoCallBack callback) {
        iMarkZhuZaoCallBack = callback;
    }

    @Override
    public void unRegisterViewCallback(IMarkZhuZaoCallBack callback) {
        iMarkZhuZaoCallBack = null;
    }
}
