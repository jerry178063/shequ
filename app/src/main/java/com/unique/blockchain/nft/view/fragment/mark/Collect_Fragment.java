package com.unique.blockchain.nft.view.fragment.mark;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.markAdapter.ShoucangAdapter;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.PiaowuDatabase;
import com.unique.blockchain.nft.view.activity.database.QingDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.database.ShoucangDatabase;
import com.unique.blockchain.nft.view.activity.database.YishuDatabase;
import com.unique.blockchain.nft.view.activity.mark.presenter.RePresenter;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseMvpFragment;
import com.unique.blockchain.nft.view.activity.mark.view.Re_view;


public class Collect_Fragment extends BaseMvpFragment<Re_view, RePresenter> implements Re_view {
    private RecyclerView collect_rec;
    private ShoucangAdapter shoucangAdapter;

    @Override
    public void onEut(String onEut) {

    }

    @Override
    protected void initData() {
        presenter.initShou();
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_collect_;
    }

    @Override
    protected RePresenter initPresenter() {
        return new RePresenter();
    }

    @Override
    public void success(BannerDatabase bannerDatabase) {

    }

    @Override
    public void List(ReDatabase reDatabase) {

    }

    @Override
    public void Quan(QuanDatabase quanDatabase) {

    }

    @Override
    public void yishu(YishuDatabase yishuDatabase) {

    }

    @Override
    public void shoucang(ShoucangDatabase shoucangDatabase) {
        shoucangAdapter.setList(shoucangDatabase.getRows());
    }

    @Override
    public void piaowu(PiaowuDatabase piaowuDatabase) {

    }

    @Override
    public void qingshi(QingDatabase qingDatabase) {

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        collect_rec = view.findViewById(R.id.collect_rec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        collect_rec.setLayoutManager(gridLayoutManager);

        shoucangAdapter = new ShoucangAdapter(getActivity());
        collect_rec.setAdapter(shoucangAdapter);
    }
}