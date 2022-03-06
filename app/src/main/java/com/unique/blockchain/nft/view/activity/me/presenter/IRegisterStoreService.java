package com.unique.blockchain.nft.view.activity.me.presenter;

import com.unique.blockchain.nft.domain.me.MerchantRegisterBean;

/**
 * 注册商家服务接口类.
 * @author 潘浩
 * @Date 2021-09-15
 */
public interface IRegisterStoreService {

    /**
     * 注册商家服务接口
     * @param merchantRegisterBean
     */
    void registerStroe(MerchantRegisterBean merchantRegisterBean);

    /**
     *  设置回调服务接口对象方法
     * @param callBack
     */
    void setViewCallback(IRegisterStoreCallBack callBack);

    /**
     *  销毁回调服务接口对象方法
     * @param callBack
     */
    void destroyViewCallback(IRegisterStoreCallBack callBack);

}
