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
import com.unique.blockchain.nft.adapter.SponsorIpartAreaAdapter;
import com.unique.blockchain.nft.adapter.VoteIpartAreaAdapter;
import com.unique.blockchain.nft.view.activity.trade.SponsorDetailActivity;
import com.unique.blockchain.nft.view.activity.trade.VoteDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * 我参与的
 * */
public class IparticipatedFragment extends BaseFragment {



    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView_sponsor)//赞助
    RecyclerView recyclerView_sponsor;
    @BindView(R.id.recyclerView_vote)//投票
    RecyclerView recyclerView_vote;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    private SponsorIpartAreaAdapter sponsorAreaAdapter=new SponsorIpartAreaAdapter(R.layout.sponsor_area_item,new ArrayList<>());
    private VoteIpartAreaAdapter voteAreaAdapter=new VoteIpartAreaAdapter(R.layout.vote_area_item,new ArrayList<>());
    private ArrayList <String>list=new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_initate;
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
        list.add("4444");
        list.add("ggggg");
        list.add("@@@@@");
        list.add("%%%%%");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView_sponsor.setLayoutManager(linearLayoutManager);
        recyclerView_sponsor.setAdapter(sponsorAreaAdapter);
        sponsorAreaAdapter.setNewData(list);

        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getActivity());
        recyclerView_vote.setLayoutManager(linearLayoutManager2);
        recyclerView_vote.setAdapter(voteAreaAdapter);
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
        sponsorAreaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), SponsorDetailActivity.class);
                startActivity(intent);
            }
        });

    }

}
