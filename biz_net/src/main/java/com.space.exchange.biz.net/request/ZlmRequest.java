package com.space.exchange.biz.net.request;

import com.space.exchange.biz.net.bean.AddressBean;
import com.space.exchange.biz.net.bean.AssectBean;
import com.space.exchange.biz.net.bean.AssetsBillBean;
import com.space.exchange.biz.net.bean.BalanceAllBean;
import com.space.exchange.biz.net.bean.BaseResponse;
import com.space.exchange.biz.net.bean.LoginNewBean;
import com.space.exchange.biz.net.connection.ExConnection;
import com.space.exchange.biz.net.model.zly_bean.RegisterBean;
import com.space.exchange.biz.net.response.ArticleDetailResponse;
import com.space.exchange.biz.net.response.ArticleResponse;
import com.space.exchange.biz.net.response.ChargeDetailResponse;
import com.space.exchange.biz.net.response.CheckToFaResponse;
import com.space.exchange.biz.net.response.HomeBannerResponse;
import com.space.exchange.biz.net.response.MentionDetailResponse;
import com.space.exchange.biz.net.response.ProductRecommendResponse;
import com.space.exchange.biz.net.response.QuestionListResponse;
import com.space.exchange.biz.net.response.QuestionResponse;
import com.space.exchange.biz.net.response.UserListCurrentResponse;
import com.space.exchange.biz.net.rx.RxExtension;
import com.space.exchange.biz.net.service.ZlmService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;

import io.reactivex.Observable;

public class ZlmRequest {

    private static ZlmService getService(RxAppCompatActivity activity, String baseUrl) {
        return ExConnection.getClient(activity, baseUrl).create(ZlmService.class);
    }

    private static ZlmService getService(RxAppCompatActivity activity) {
        return ExConnection.getClient(activity).create(ZlmService.class);
    }

    /**
     * 注册
     */
    public static Observable<RegisterBean> register(RxAppCompatActivity activity, Map map) {
        return getService(activity).register(map).compose(RxExtension.apply(activity));
    }

    /**
     * 发送短信
     */
    public static Observable<BaseResponse> sendSms(RxAppCompatActivity activity, Map map) {
        return getService(activity).sendSms(map).compose(RxExtension.apply(activity));
    }

    /**
     * 登录
     */
    public static Observable<LoginNewBean> login(RxAppCompatActivity activity, Map map) {
        return getService(activity).login(map).compose(RxExtension.apply(activity));
    }
    /**
     * 查询所有币种金额
     */
    public static Observable<BalanceAllBean> getBalanceAll(RxAppCompatActivity activity, Map map) {
        return getService(activity).getBalanceAll(map).compose(RxExtension.apply(activity));
    }
    /**
     * 根据币种id获取可用的划转余额
     *
     * @param currencyId 币种id
     * @param walletType 钱包类型 coin 币币账户  legal 法币账户
     */
    public static Observable<AssectBean> findCurrency(RxAppCompatActivity activity, String currencyId) {
        return getService(activity).findCurrency(currencyId).compose(RxExtension.apply(activity));
    }

    /**
     * 文章列表
     */
    public static Observable<ArticleResponse> getArticleList(RxAppCompatActivity activity, Map map) {
        return getService(activity).getArticleList(map).compose(RxExtension.apply(activity));
    }

    /**
     * 文章列表
     */
    public static Observable<HomeBannerResponse> getUseBanner(RxAppCompatActivity activity) {
        return getService(activity).getUseBanner().compose(RxExtension.apply(activity));
    }

    /**
     * 首页精选理财
     */
    public static Observable<ProductRecommendResponse> getProductRecommend(RxAppCompatActivity activity) {
        return getService(activity).getProductRecommend().compose(RxExtension.apply(activity));
    }

    /**
     * 问答分类
     */
    public static Observable<QuestionResponse> getQuestion(RxAppCompatActivity activity) {
        return getService(activity).getQuestion().compose(RxExtension.apply(activity));
    }

    /**
     * 问答列表
     */
    public static Observable<QuestionListResponse> getQuestionList(RxAppCompatActivity activity, Map map) {
        return getService(activity).getQuestionList(map).compose(RxExtension.apply(activity));
    }

    /**
     * 资产列表
     */
    public static Observable<UserListCurrentResponse> listUserCurrency(RxAppCompatActivity activity) {
        return getService(activity).listUserCurrency().compose(RxExtension.apply(activity));
    }

    /**
     * 获取充币地址
     */
    public static Observable<AddressBean> getRechargeAddress(RxAppCompatActivity activity, String label_name) {
        return getService(activity).getRechargeAddress(label_name).compose(RxExtension.apply(activity));
    }

    /**
     * 获取充币地址
     */
    public static Observable<AssetsBillBean> getAssetsBills(RxAppCompatActivity activity, Map map) {
        return getService(activity).getAssetsBills(map).compose(RxExtension.apply(activity));
    }

    /**
     * 获取文章详情
     */
    public static Observable<ArticleDetailResponse> articleDetail(RxAppCompatActivity activity, Map map) {
        return getService(activity).articleDetail(map).compose(RxExtension.apply(activity));
    }


    /**
     * 获取文章详情
     */
    public static Observable<BaseResponse> loginOut(RxAppCompatActivity activity) {
        return getService(activity).loginOut().compose(RxExtension.apply(activity));
    }

    public static Observable<BaseResponse> drawCreate(RxAppCompatActivity activity, Map map) {
        return getService(activity).drawCreate(map).compose(RxExtension.apply(activity));
    }

    /**
     * 提币校验
     *
     * @param activity
     * @return
     */
    public static Observable<CheckToFaResponse> check2fa(RxAppCompatActivity activity) {
        return getService(activity).check2fa().compose(RxExtension.apply(activity));
    }

    public static Observable<ChargeDetailResponse> getRechargeOrder(RxAppCompatActivity activity, Map map) {
        return getService(activity).getRechargeOrder(map).compose(RxExtension.apply(activity));
    }

    public static Observable<MentionDetailResponse> getDrawOrder(RxAppCompatActivity activity, Map map) {
        return getService(activity).getDrawOrder(map).compose(RxExtension.apply(activity));
    }
}
