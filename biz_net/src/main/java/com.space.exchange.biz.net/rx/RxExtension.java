package com.space.exchange.biz.net.rx;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx扩展方法
 * Author: Evan
 * Data: 2018/8/6
 */

public class RxExtension {

    /**
     * 切换线程
     */
    public static <T> ObservableTransformer<T, T> applySchedules() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 切换线程，并绑定组件生命周期
     */
    public static <T> ObservableTransformer<T, T> apply(RxAppCompatActivity activity) {
        return upstream -> upstream.compose(applySchedules()).compose(activity.bindToLifecycle());
    }

    /**
     * 释放Disposable资源
     */
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

}
