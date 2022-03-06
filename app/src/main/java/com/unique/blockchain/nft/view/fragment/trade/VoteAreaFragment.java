package com.unique.blockchain.nft.view.fragment.trade;

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
import com.unique.blockchain.nft.adapter.VoteAreaAdapter;
import com.unique.blockchain.nft.view.activity.trade.VoteDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * 投票区
 * */
public class VoteAreaFragment extends BaseFragment {

    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;

    private VoteAreaAdapter voteAreaAdapter=new VoteAreaAdapter(R.layout.vote_area_item,new ArrayList<>());
    private ArrayList <String>list=new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_assets;
    }

    @Override
    public void initView() {

        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                smr.finishRefresh();
            }
        });
        if(list != null){
            list.clear();
        }
        list.add("储蓄比特币给你财务自由储蓄比特币给你财务自由储蓄比特币给你财务自由储蓄比特币给你财务自由");
        list.add("牛市的三个阶段");
        list.add("储蓄比特币给你财务自由储蓄比特币给你财务自由储蓄比特币给你财务自由储蓄比特币给你财务自由");
        list.add("牛市的三个阶段");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voteAreaAdapter);
        voteAreaAdapter.setNewData(list);

        if(list != null && list.size() > 0){
            lin_no_data.setVisibility(View.GONE);
        }else {
            lin_no_data.setVisibility(View.VISIBLE);
        }
        voteAreaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), VoteDetailActivity.class);
                startActivity(intent);
            }
        });


    }


}
