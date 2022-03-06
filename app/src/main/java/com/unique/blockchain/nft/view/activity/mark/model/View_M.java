package com.unique.blockchain.nft.view.activity.mark.model;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.domain.trade.GoTradeBean;
import com.unique.blockchain.nft.view.activity.database.QarticularsDatabase;
import com.unique.blockchain.nft.view.activity.mark.Apiservers;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseM;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_M implements IBaseM {

    public void initList(String stringNftId, IBaseCall<QarticularsDatabase> qarticularsDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .QarticularsDatabase(stringNftId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QarticularsDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull QarticularsDatabase qarticularsDatabase) {
                        qarticularsDatabaseIBaseCall.success(qarticularsDatabase);
                        //Log.e("哈哈", "displayImage666: "+ qarticularsDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        qarticularsDatabaseIBaseCall.onEut(throwable.getMessage());
                       // Log.e("哈哈", "displayImage失败: "+ new Gson().toJson(throwable));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initpp(IBaseCall<GoTradeBean> goTradeBeanIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .DATABASE_OBSERVABLE()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoTradeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull GoTradeBean goTradeBean) {
                        goTradeBeanIBaseCall.success(goTradeBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        goTradeBeanIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
