package com.unique.blockchain.nft.view.activity.mark.model;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.view.activity.database.InquireDatabase;
import com.unique.blockchain.nft.view.activity.mark.Apiservers;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inquire_M {
    public void initInquire(IBaseCall<InquireDatabase> inquireDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .InquireDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InquireDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull InquireDatabase inquireDatabase) {
                        inquireDatabaseIBaseCall.success(inquireDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        inquireDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
