package com.unique.blockchain.nft.view.activity.trade;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ShareRecordAdapter;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 分享收益记录
 * */
public class ShareIncomeRecordActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;

    private ShareRecordAdapter shareRecordAdapter=new ShareRecordAdapter(R.layout.share_record__item,new ArrayList<>());
    private ArrayList <String>list=new ArrayList();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_record;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("分享收益记录");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                smr.finishRefresh();
            }
        });
        if(list != null){
            list.clear();
        }
//        list.add("分享委托佣金收益");
//        list.add("分享委托佣金收益");
//        list.add("分享委托佣金收益");
//        list.add("分享委托佣金收益");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(shareRecordAdapter);
        shareRecordAdapter.setNewData(list);

        if(list != null && list.size() > 0){
            if(lin_no_data != null) {
                lin_no_data.setVisibility(View.GONE);
            }
        }else {
            if(lin_no_data != null) {
                lin_no_data.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
