package com.unique.blockchain.nft.view.activity.mark.view;


import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.PiaowuDatabase;
import com.unique.blockchain.nft.view.activity.database.QingDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.database.ShoucangDatabase;
import com.unique.blockchain.nft.view.activity.database.YishuDatabase;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseV;

public interface Re_view extends IBaseV {
    void success(BannerDatabase bannerDatabase);
    void List(ReDatabase reDatabase);
    void Quan(QuanDatabase quanDatabase);
    void yishu(YishuDatabase yishuDatabase);
    void shoucang(ShoucangDatabase shoucangDatabase);
    void piaowu(PiaowuDatabase piaowuDatabase);
    void qingshi(QingDatabase qingDatabase);
}
