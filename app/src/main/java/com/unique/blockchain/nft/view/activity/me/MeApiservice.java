package com.unique.blockchain.nft.view.activity.me;

import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.view.activity.me.baen.JiaonftDatabase;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeApiservice {
    String All_nft_trade= UrlConstant.baseUrl;
    @GET("api/user/nftList?")
    Observable<JiaonftDatabase> All_nft_tradeDatabase(@Query("ownerWallet") String all_ownerWallet);
}
