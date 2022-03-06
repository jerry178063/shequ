package com.unique.blockchain.nft.view.fragment.mark;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.markAdapter.QingAdapter;
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

public class Grade_Fragment extends BaseMvpFragment<Re_view, RePresenter> implements Re_view {
    private RecyclerView ticket_rec;
    private QingAdapter piaowuAdapter;

    @Override
    public void onEut(String onEut) {

    }

    @Override
    protected void initData() {
        presenter.initqing();
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_grade;
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

    }

    @Override
    public void piaowu(PiaowuDatabase piaowuDatabase) {

    }

    @Override
    public void qingshi(QingDatabase qingDatabase) {
        piaowuAdapter.setList(qingDatabase.getRows());
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ticket_rec = view.findViewById(R.id.qingppp_rec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        ticket_rec.setLayoutManager(gridLayoutManager);

        piaowuAdapter = new QingAdapter(getActivity());
        ticket_rec.setAdapter(piaowuAdapter);
    }
}