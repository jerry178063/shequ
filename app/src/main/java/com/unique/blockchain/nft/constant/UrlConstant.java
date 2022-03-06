package com.unique.blockchain.nft.constant;

/**
 * 常用常量类
 * @author 潘浩
 */
public class UrlConstant {
    /**
     * Android前端与后台服务对接的URL
     *
     */
//    public static final String baseUrl="http://192.168.1.156:8080/nft-app-api/";//本地
//    public static final String baseUrl="http://192.168.2.13:30061/nft-app-api/";//开发环境
    public static final String baseUrl="http://192.168.2.13:31061/nft-app-api/";//测试环境
//    public static final String baseUrl="http://120.79.14.55:31061/nft-app-api/";//外网环境



//        public static final String baseUrlFile="http://192.168.1.156:8080/";//本地
//    public static final String baseUrlFile="http://192.168.2.13:30061/";//开发环境
    public static final String baseUrlFile="http://192.168.2.13:31061/";//测试环境
//    public static final String baseUrlFile="http://120.79.14.55:31061/";//外网环境


    public static final String TRADE_DETAIL = "api/nft/nftInfo";

    public static String baseUrlGauss = "http://192.168.2.126:4317/";

//    public static String baseCreateInportWallet = "http://192.168.1.156:8080/nft-app-api/";//本地
//    public static String baseCreateInportWallet = "http://192.168.2.13:30061/nft-app-api/";//节点后台开发
    public static String baseCreateInportWallet = "http://192.168.2.13:31061/nft-app-api/";//节点后台测试
//    public static String baseCreateInportWallet = "http://120.79.14.55:31061/nft-app-api/";//外网环境

//    public static String baseUrlLian = "http://192.168.2.125:18080/V3/";//节点链上数据服务后台开发
    public static String baseUrlLian = "http://192.168.2.13:31080/V3/";//节点链上数据服务后台测试
//    public static String baseUrlLian = "http://120.79.14.55:31080/V3/";//节点链上数据服务后台外网

    public static String baseGetValiAddUrl2 = "http://192.168.2.15:18080/unique/";//节点链上后台测试
//    public static String baseGetValiAddUrl2 = "http://120.79.14.55:18080/unique/";//节点链上后台外网

    public static String baseQueryLastDealAmount = "https://walletapi.gaussdex.io/";//接口正式环境

    public static final String ap = "api/wallet/updateWalletAndAddress";

    public static String baseWap = "http://192.168.2.13:8017/";//h5测试地址
//    public static String baseWap = "http://120.79.14.55:28017/";//h5测试外网






}
