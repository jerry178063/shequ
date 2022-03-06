package com.unique.blockchain.nft.view.activity.me.presenter;

/**
 * 质押保证金接口类
 * @author panhao
 */
public interface IPledgeService {

    /**
     * 获取当前用户质押的保证金方法
     * @param walletAddr 钱包地扯
     */
    void getCurrentUserPledge(String walletAddr);


    /**
     *  设置回调服务接口对象方法
     * @param callBack
     */
    void setViewCallback(IPledgeCallBack callBack);

    /**
     *  销毁回调服务接口对象方法
     * @param callBack
     */
    void destroyViewCallback(IPledgeCallBack callBack);

}
