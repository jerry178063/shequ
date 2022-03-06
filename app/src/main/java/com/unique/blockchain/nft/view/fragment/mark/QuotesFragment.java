package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.QuotesAdapter;
import com.unique.blockchain.nft.view.activity.trade.SponsorDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 发现-行情
 * */
public class QuotesFragment extends BaseFragment {


    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    private QuotesAdapter quotesAdapter=new QuotesAdapter(R.layout.quotes_item,new ArrayList<>());
    private ArrayList <String>list=new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quotes;
    }

    @Override
    public void initView() {

        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                smr.finishRefresh();
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(quotesAdapter);
        quotesAdapter.setNewData(list);
        if(list != null){
            list.clear();
        }
//        list.add("储蓄比");
//        list.add("牛市的");
//        list.add("储蓄比");
//        list.add("牛市的");
        if(list != null && list.size() > 0){
            if(lin_no_data != null) {
                lin_no_data.setVisibility(View.GONE);
            }
        }else {
            if(lin_no_data != null) {
                lin_no_data.setVisibility(View.VISIBLE);
            }
        }
        quotesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), SponsorDetailActivity.class);
                startActivity(intent);
            }
        });
    }


}
