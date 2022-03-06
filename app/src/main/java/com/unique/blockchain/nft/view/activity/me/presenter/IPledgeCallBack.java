package com.unique.blockchain.nft.view.activity.me.presenter;

import com.google.gson.JsonObject;

/**
 * 质押保证金回调服务接口
 * @author panhao
 */
public interface IPledgeCallBack {
    /**
     * 回调处理成功方法
     */
    void succ(JsonObject jsonObject);

    /**
     * 回调处理失败方法
     */
    void fail(String code, String message);
}
