package com.unique.blockchain.nft.view.fragment.mark.view;

import com.unique.blockchain.nft.view.activity.database.BannerDatabase;

public interface IMarkBannerCallBack {
    void loadBannerData(BannerDatabase bannerDatabase);
    void loadBannerFail();
}
