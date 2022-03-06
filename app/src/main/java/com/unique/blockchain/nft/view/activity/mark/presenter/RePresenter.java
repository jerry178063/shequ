package com.unique.blockchain.nft.view.activity.mark.presenter;


import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.PiaowuDatabase;
import com.unique.blockchain.nft.view.activity.database.QingDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.database.ShoucangDatabase;
import com.unique.blockchain.nft.view.activity.database.YishuDatabase;
import com.unique.blockchain.nft.view.activity.mark.base.BasePresenter;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseCall;
import com.unique.blockchain.nft.view.activity.mark.model.ReModel;
import com.unique.blockchain.nft.view.activity.mark.view.Re_view;

public class RePresenter extends BasePresenter<Re_view> {

    private final ReModel reModel;

    public RePresenter() {
        reModel = new ReModel();
    }
    public void initData(){
        reModel.initData(new IBaseCall<BannerDatabase>() {
            @Override
            public void success(BannerDatabase bannerDatabase) {
                view.success(bannerDatabase);
                //Log.e("哈哈", "displayImage222: "+bannerDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
    public void initList() {
        reModel.initList(new IBaseCall<ReDatabase>() {
            @Override
            public void success(ReDatabase reDatabase) {
                view.List(reDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
    public void initYi() {
        reModel.initYi(new IBaseCall<YishuDatabase>() {

            @Override
            public void success(YishuDatabase yishuDatabase) {
                view.yishu(yishuDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
    public void initShou(){
        reModel.initShou(new IBaseCall<ShoucangDatabase>() {
            @Override
            public void success(ShoucangDatabase shoucangDatabase) {
                view.shoucang(shoucangDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
    public void initpiao(){
        reModel.initpiao(new IBaseCall<PiaowuDatabase>() {
            @Override
            public void success(PiaowuDatabase piaowuDatabase) {
                view.piaowu(piaowuDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
    public void initqing() {
        reModel.initqing(new IBaseCall<QingDatabase>() {
            @Override
            public void success(QingDatabase qingDatabase) {
                view.qingshi(qingDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });
    }
    public void initQuan() {
        reModel.initQuan(new IBaseCall<QuanDatabase>() {
            @Override
            public void success(QuanDatabase quanDatabase) {
                view.Quan(quanDatabase);
            }

            @Override
            public void onEut(String onEut) {
                view.onEut(onEut);
            }
        });

    }
}