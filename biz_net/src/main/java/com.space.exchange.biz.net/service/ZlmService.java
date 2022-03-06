package com.space.exchange.biz.net.service;

import com.space.exchange.biz.net.bean.AddressBean;
import com.space.exchange.biz.net.bean.AssectBean;
import com.space.exchange.biz.net.bean.AssetsBillBean;
import com.space.exchange.biz.net.bean.BaseResponse;
import com.space.exchange.biz.net.bean.LoginNewBean;
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

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ZlmService {
    /**
     * 注册
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/user/register")
    Observable<RegisterBean> register(@FieldMap Map<String, String> map);

    /***
     * 发送短信
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/user/sendSms")
    Observable<BaseResponse> sendSms(@FieldMap Map<String, String> map);

    /***
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/user/login")
    Observable<LoginNewBean> login(@FieldMap Map<String, String> map);


    /***
     *
     * 根据币种获取可划转的数量
     * */
    @GET("api/v1/user/findCurrency")
    Observable<AssectBean> findCurrency(@Query("currency_id") String currencyId);


    /***
     *
     * 根据币种获取可划转的数量
     * */
    @GET("api/v1/currency/available/get")
    Observable<BaseResponse> getUsableNumberByCurrencyId(@Query("currency_id") String currencyId, @Query("wallet_type") String walletType);

    /***
     *
     * 文章列表
     * */
    @GET("api/v1/article/list")
    Observable<ArticleResponse> getArticleList(@QueryMap Map<String, String> map);

    /***
     *
     * 首页banner
     * */
    @GET("api/v1/user/banner")
    Observable<HomeBannerResponse> getUseBanner();

    /***
     *
     * 获取首页精选理财
     * */
    @GET("api/v1/finance/product/recommend")
    Observable<ProductRecommendResponse> getProductRecommend();

    /***
     *
     * 问答分类
     * */
    @GET("api/v1/question/class")
    Observable<QuestionResponse> getQuestion();

    /***
     *
     * 问答列表
     * */
    @GET("api/v1/question/list")
    Observable<QuestionListResponse> getQuestionList(@QueryMap Map<String, String> map);

    /**
     * 获取资产列表
     *
     * @return
     */
    @GET("api/v1/user/listUserCurrency")
    Observable<UserListCurrentResponse> listUserCurrency();


    //获取充币地址
    @GET("api/v1/user/getRechargeAddress")
    Observable<AddressBean> getRechargeAddress(@Query("label_name") String label_name);

    //获取充币地址
    @GET("api/v1/user/getAssetsBills")
    Observable<AssetsBillBean> getAssetsBills(@QueryMap Map<String, String> map);

    /**
     * 获取文章详情
     *
     * @return
     */
    @GET("api/v1/article/detail")
    Observable<ArticleDetailResponse> articleDetail(@QueryMap Map<String, String> map);

    /**
     * 用户登出
     *
     * @return
     */
    @POST("api/v1/user/loginOut")
    Observable<BaseResponse> loginOut();

    /**
     * 获取文章详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/user/drawCreate")
    Observable<BaseResponse> drawCreate(@FieldMap Map<String, String> map);

    /**
     * 提币校验
     *
     * @return
     */
    @POST("api/v1/user/check2fa")
    Observable<CheckToFaResponse> check2fa();

    /**
     * 获取充币订单
     *
     * @return
     */
    @GET("api/v1/user/getRechargeOrder")
    Observable<ChargeDetailResponse> getRechargeOrder(@QueryMap Map<String, String> map);

    /**
     * 获取提币订单
     *
     * @return
     */
    @GET("api/v1/user/getDrawOrder")
    Observable<MentionDetailResponse> getDrawOrder(@QueryMap Map<String, String> map);

    /**
     * 查询账户所有币种余额
     *
     * @return
     */
    @GET("gpb/getBalanceAll")
    Observable<LoginNewBean> getBalanceAll(@QueryMap Map<String, String> map);
}
