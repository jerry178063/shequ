package com.unique.blockchain.nft.view.activity.mark.model;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.PiaowuDatabase;
import com.unique.blockchain.nft.view.activity.database.QingDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.database.ShoucangDatabase;
import com.unique.blockchain.nft.view.activity.database.YishuDatabase;
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

public class ReModel implements IBaseM {



    public void initData(IBaseCall<BannerDatabase> bannerDatabaseIBaseCall) {
       // Log.e("哈哈", "displayImage: ");
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {


                    }

                    @Override
                    public void onNext(@NonNull BannerDatabase bannerDatabase) {
                        bannerDatabaseIBaseCall.success(bannerDatabase);
                       // Log.e("哈哈", "displayImage: "+ new Gson().toJson(bannerDatabase));
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        bannerDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initList(IBaseCall<ReDatabase> reDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .ReDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull ReDatabase reDatabase) {
                        reDatabaseIBaseCall.success(reDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        reDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void initYi(IBaseCall<YishuDatabase> yishuDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .YishuDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YishuDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull YishuDatabase yishuDatabase) {
                        yishuDatabaseIBaseCall.success(yishuDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        yishuDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void initShou(IBaseCall<ShoucangDatabase> shoucangDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .ShoucangDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShoucangDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull ShoucangDatabase shoucangDatabase) {
                        shoucangDatabaseIBaseCall.success(shoucangDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        shoucangDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initpiao(IBaseCall<PiaowuDatabase> piaowuDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .piaowuDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PiaowuDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull PiaowuDatabase piaowuDatabase) {
                        piaowuDatabaseIBaseCall.success(piaowuDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        piaowuDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initqing(IBaseCall<QingDatabase> qingDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .QingDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QingDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull QingDatabase qingDatabase) {
                        qingDatabaseIBaseCall.success(qingDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        qingDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initQuan(IBaseCall<QuanDatabase> quanDatabaseIBaseCall) {
        new Retrofit.Builder()
                .baseUrl(Apiservers.banner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apiservers.class)
                .QuanDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuanDatabase>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull QuanDatabase quanDatabase) {
                        quanDatabaseIBaseCall.success(quanDatabase);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        quanDatabaseIBaseCall.onEut(throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
