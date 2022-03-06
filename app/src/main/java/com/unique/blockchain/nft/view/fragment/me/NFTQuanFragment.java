package com.unique.blockchain.nft.view.fragment.me;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseMvpFragment;
import com.unique.blockchain.nft.view.activity.me.baen.JiaonftDatabase;
import com.unique.blockchain.nft.view.activity.me.meAdapter.NFTAdapter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.All_presenter;
import com.unique.blockchain.nft.view.activity.me.me_view.All_view;


public class NFTQuanFragment extends BaseMvpFragment<All_view, All_presenter> implements All_view {


    private RecyclerView mNftQuanRec;
    private SmartRefreshLayout nft_quan_smartRefresh;
    private NFTAdapter nftAdapter;
    private String all_ownerWallet="0ad08f66-88af-4346-aca0-063b8c0f28b5";
    @Override
    protected void initView(View view) {
        super.initView(view);
        mNftQuanRec = view.findViewById(R.id.nft_Quan_rec);
        nft_quan_smartRefresh = view.findViewById(R.id.nft_quan_smartRefresh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mNftQuanRec.setLayoutManager(linearLayoutManager);
        nftAdapter = new NFTAdapter(getContext());
        mNftQuanRec.setAdapter(nftAdapter);
        initSet();
    }

    private void initSet() {
        nft_quan_smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                nft_quan_smartRefresh.finishLoadMore();
                nft_quan_smartRefresh.finishRefresh();
            }

        });
        nft_quan_smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initData();
                nft_quan_smartRefresh.finishLoadMore();
                nft_quan_smartRefresh.finishRefresh();
            }
        }) ;
//开始下拉
        /*nft_quan_smartRefresh.setEnableRefresh(true);//启用刷新
        //nft_quan_smartRefresh.setEnableLoadmore(true);//启用加载
//关闭下拉
        nft_quan_smartRefresh.finishRefresh();
        //nft_quan_smartRefresh.finishLoadmore();
//自动下拉
        nft_quan_smartRefresh.autoRefresh();
        nft_quan_smartRefresh.autoLoadMore();*/
    }

    @Override
    public void onEut(String onEut) {

    }

    @Override
    protected void initData() {
        presenter.initList(all_ownerWallet);

    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_quan2;
    }

    @Override
    protected All_presenter initPresenter() {
        return new All_presenter();
    }

    @Override
    public void List(JiaonftDatabase jiaonftDatabase) {
        nftAdapter.setList(jiaonftDatabase.getRows());
    }
}