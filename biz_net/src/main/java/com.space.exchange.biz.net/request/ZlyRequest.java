package com.space.exchange.biz.net.request;

import com.space.exchange.biz.net.bean.NewsFlashResponse;
import com.space.exchange.biz.net.bean.TopicListItem;
import com.space.exchange.biz.net.connection.ExConnection;
import com.space.exchange.biz.net.response.AppVersionResponse;
import com.space.exchange.biz.net.rx.RxExtension;
import com.space.exchange.biz.net.service.ZlyService;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class ZlyRequest {


    private static ZlyService getService(RxAppCompatActivity activity, String baseUrl) {
        return ExConnection.getClient(activity, baseUrl).create(ZlyService.class);
    }

    private static ZlyService getService(RxAppCompatActivity activity) {
        return ExConnection.getClient(activity).create(ZlyService.class);
    }


    /**
     * 获取app版本号
     */
    public static Observable<AppVersionResponse> getAppVersion(RxAppCompatActivity activity, Map<String, String> map) {
        return getService(activity).getAppVersion(map).compose(RxExtension.apply(activity));
    }


    /**
     * 新闻资讯
     */
    public static Observable<List<TopicListItem>> getTopicList(RxAppCompatActivity activity, Map<String, String> map) {
        return getService(activity, "http://api.coindog.com/").getTopicList(map).compose(RxExtension.apply(activity));
    }

    /**
     * 行情快讯
     */
    public static Observable<NewsFlashResponse> getLiveList(RxAppCompatActivity activity, Map<String, String> map) {
        return getService(activity, "http://api.coindog.com/").getLiveList(map).compose(RxExtension.apply(activity));
    }

}
