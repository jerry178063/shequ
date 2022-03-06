package com.unique.blockchain.nft.view.activity.me.presenter;

import com.google.gson.JsonObject;

/**
 * 这个是商家注册回调服务接口
 * @author panhao
 */
public interface IRegisterStoreCallBack {

    /**
     * 回调加载数据方法
     */
    void loadData();

    /**
     * 回调处理成功方法
     */
    void succ(JsonObject jsonObject);

    /**
     * 回调处理失败方法
     */
    void fail(String code, String message);

}
