package com.unique.blockchain.nft.view.activity.mark.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.unique.blockchain.nft.view.activity.database.PopularDatabase;
import com.unique.blockchain.nft.view.activity.mark.Apiservers;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class popular_M {

    public void init_popular(String companyWalletAddr, IBaseCall<PopularDatabase> popularDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .PopularDatabase(companyWalletAddr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PopularDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull PopularDatabase popularDatabase) {
                        popularDatabaseIBaseCall.success(popularDatabase);
                         Log.e("kkklll", "displayImage: "+ new Gson().toJson(popularDatabase));
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        popularDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
