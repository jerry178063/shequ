package com.unique.blockchain.nft.view.activity.mark;

import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.domain.trade.GoTradeBean;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.InquireDatabase;
import com.unique.blockchain.nft.view.activity.database.PiaowuDatabase;
import com.unique.blockchain.nft.view.activity.database.PopularDatabase;
import com.unique.blockchain.nft.view.activity.database.QarticularsDatabase;
import com.unique.blockchain.nft.view.activity.database.QingDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.database.ShoucangDatabase;
import com.unique.blockchain.nft.view.activity.database.YishuDatabase;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiservers {

    String banner= UrlConstant.baseUrl;
    @GET("api/advertManagement/hotCompany")
    Observable<BannerDatabase> getBanner();
    @GET("api/company/hotCompany")
    Observable<ReDatabase>ReDatabase();
    @GET("api/nft/sellList?")
    Observable<QuanDatabase>QuanDatabase();
    @GET("nft/sellList?type=3")
    Observable<YishuDatabase>YishuDatabase();
    @GET("api/nft/sellList?type=2")
    Observable<ShoucangDatabase>ShoucangDatabase();
    @GET("api/nft/sellList?type=1")
    Observable<PiaowuDatabase>piaowuDatabase();
    @GET("api/nft/sellList?type=4")
    Observable<QingDatabase>QingDatabase();
    @GET("api/nft/nftInfo?")
    Observable<QarticularsDatabase>QarticularsDatabase(@Query("nftId") String stringNftId);

    @GET("api/nft/sellList?type=4")
    Observable<InquireDatabase>InquireDatabase();
    @GET("api/nft/sellList?")
    Observable<PopularDatabase>PopularDatabase(@Query("companyWalletAddr") String companyWalletAddr);

    @POST("api/push/data/initialize")
    Observable<GoTradeBean>DATABASE_OBSERVABLE();
}
