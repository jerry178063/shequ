package com.unique.blockchain.nft.view.activity.me.me_model;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.me.MeApiservice;
import com.unique.blockchain.nft.view.activity.me.baen.JiaonftDatabase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class All_model {
    public void initnftAll(String all_ownerWallet, IBaseCall<JiaonftDatabase> jiaonftDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(MeApiservice.All_nft_trade)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MeApiservice.class)
                .All_nft_tradeDatabase(all_ownerWallet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JiaonftDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull JiaonftDatabase jiaonftDatabase) {
                        jiaonftDatabaseIBaseCall.success(jiaonftDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        jiaonftDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
