package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.HelpCenterAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 帮组中心
 * */
public class HelpCenterActivity extends BaseActivity {


    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private UserDao userDao;
    private List<User> users_list;
    private HelpCenterAdapter helpCenterAdapter=new HelpCenterAdapter(R.layout.help_center_item,this,new ArrayList<>());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("帮助中心");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        getData();
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                getData();
                smr.finishRefresh();
            }
        });


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(helpCenterAdapter);
//        helpCenterAdapter.setNewData(users_list);
        helpCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(HelpCenterActivity.this, AdWebActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        if(users_list != null){
            users_list.clear();
        }

        userDao = daoSession.getUserDao();
        users_list = userDao.loadAll();
    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
