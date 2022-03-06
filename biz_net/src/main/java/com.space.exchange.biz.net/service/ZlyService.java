package com.space.exchange.biz.net.service;

import com.space.exchange.biz.net.bean.NewsFlashResponse;
import com.space.exchange.biz.net.bean.TopicListItem;
import com.space.exchange.biz.net.response.AppVersionResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ZlyService {

    /**
     * 获取app端版本号
     */
    @GET("api/v1/version")
    Observable<AppVersionResponse> getAppVersion(@QueryMap Map<String, String> map);


    /***
     *  新闻资讯
     * @param
     * @return
     */
    @GET("topic/list")
    Observable<List<TopicListItem>> getTopicList(@QueryMap Map<String, String> map);

    /***
     *  行情快讯
     * @param
     * @return
     */
    @GET("live/list")
    Observable<NewsFlashResponse> getLiveList(@QueryMap Map<String, String> map);

}
