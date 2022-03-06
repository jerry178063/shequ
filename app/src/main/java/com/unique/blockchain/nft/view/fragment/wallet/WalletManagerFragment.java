package com.unique.blockchain.nft.view.fragment.wallet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.WalletTypeListAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.me.DigitalHomeAssetsActivity;
import com.unique.blockchain.nft.view.activity.me.WalletManagerActivity;
import com.unique.blockchain.nft.view.activity.wallet.AddDigitalAssetsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletManagerFragment extends BaseFragment{

    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.img_wallet)
    LinearLayout img_wallet;
    @BindView(R.id.img_add)
    LinearLayout img_add;
    private UserDao userDao;
    private List<User> users_list;
    private WalletTypeListAdapter voteAreaAdapter = new WalletTypeListAdapter(R.layout.wallet_fragment_list_item, getContext(), new ArrayList<>());
    @BindView(R.id.tv_name)
    TextView tv_name;
    public String uniqueAdress;
    public String unique_name;
    public User unique;
    List<UserInfoItem> mObjectList = new ArrayList<>();
    public static WalletManagerFragment newInstance() {
        Bundle args = new Bundle();
        WalletManagerFragment fragment = new WalletManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_list;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        CommonUtil.setStatusBarColor(getActivity(), 1);
        CommonUtil.setStatusBarTextColor(getActivity(), 1);


        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                getData();
                smr.finishRefresh();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voteAreaAdapter);
        voteAreaAdapter.setContext(getContext());

        voteAreaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                if(!mObjectList.get(i).getCoin_name().equals("UNIQUE") && !mObjectList.get(i).getCoin_name().equals("ETH")){
                    if(!mObjectList.get(i).getCoin_name().equals("UNIQUE")){
                    ToastUtil.showShort(getContext(),"敬请期待!");
                }else {
                    SpUtil.getInstance(getContext()).putString("wallet_name", mObjectList.get(i).getCoin_name());
                    SpUtil.getInstance(getContext()).putString("wallet_address", mObjectList.get(i).getCoin_psd());
                    Intent intent2 = new Intent(getContext(), DigitalHomeAssetsActivity.class);
                    startActivity(intent2);
                }
            }
        });
    }
    private void getData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        users_list = userDao.loadAll();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("FF543","NAME:" + SPUtils.getString(getContext(), Tools.name, ""));
        }
        if (unique != null) {
            if(unique.getMObjectList() != null && unique.getMObjectList().size() > 0) {
                if(mObjectList != null){
                    mObjectList.clear();
                }
                mObjectList.addAll(unique.getMObjectList());
                for (int i = 0; i < unique.getMObjectList().size(); i++) {
                    Log.e("FF322", "mobject:" + new Gson().toJson(unique.getMObjectList().get(i)));
                    if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                        uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    }
                }
                if(voteAreaAdapter != null){
                    voteAreaAdapter.setNewData(mObjectList);
                }
                tv_name.setText(unique.getName());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtil.setStatusBarColor(getActivity(), 1);
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        getData();

    }

    @OnClick({R.id.img_wallet,R.id.img_add})
    public void setOnclick(View v) {
        switch (v.getId()) {
            case R.id.img_wallet://钱包管理
                Intent intent = new Intent(getContext(), WalletManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.img_add://添加
                Intent intent2 = new Intent(getContext(), AddDigitalAssetsActivity.class);
                startActivity(intent2);
                break;
        }

    }
}
