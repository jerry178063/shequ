package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ReMenAllAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkZhuZaoPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkZhuZaoPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkZhuZaoCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PopularallFragment extends BaseFragment implements IMarkZhuZaoCallBack {
    static {
        System.loadLibrary("TrustWalletCore");
    }

    SmartRefreshLayout swipeRefresh;
    RecyclerView all_rec2;
    private ReMenAllAdapter markAllAdapter;
    private IMarkZhuZaoPresenter iMarkPresenter;
    private List<MarkHotBean.Rows> rows;
    private int page = 1;
    LinearLayout tv_no_data;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress, walletAdd;

    @Override
    protected void initData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        iMarkPresenter = new IMarkZhuZaoPresenterImpl();
        iMarkPresenter.registerViewCallback(PopularallFragment.this);
        if(SpUtil.getInstance(getContext()).getString("walletAdd") != null) {
            iMarkPresenter.getData(page, 10, SpUtil.getInstance(getContext()).getString("walletAdd") + "");
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_popular_all;
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
        all_rec2 = (RecyclerView) findViewById(R.id.all_rec2);
        swipeRefresh = (SmartRefreshLayout) findViewById(R.id.swipeRefresh);
        tv_no_data = (LinearLayout) findViewById(R.id.tv_no_data);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        all_rec2.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        markAllAdapter = new ReMenAllAdapter(R.layout.popular_all_item, rows);
        all_rec2.setAdapter(markAllAdapter);
        all_rec2.setLayoutManager(gridLayoutManager);

        markAllAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (rows.get(position).getSellMode() == 1) {//购买
                    Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                    intent.putExtra("title", rows.get(position).getNftName());
                    intent.putExtra("nftId", rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress", uniqueAdress);
                    intent.putExtra("productType", rows.get(position).getProductType() + "");
                    startActivity(intent);
                } else if (rows.get(position).getSellMode() == 2) {//出价
                    Intent intent = new Intent(getContext(), OutPriceActivity.class);
                    intent.putExtra("title", rows.get(position).getNftName());
                    intent.putExtra("nftId", rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress", uniqueAdress);
                    intent.putExtra("productType", rows.get(position).getProductType() + "");
                    startActivity(intent);
                }
            }
        });
        markAllAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), PaiActivity.class);
                intent.putExtra("nftId", rows.get(i).getNftId() + "");
                intent.putExtra("surplus",rows.get(i).getSurplus() + "");
                if (rows.get(i).getSellMode() == 1) {//购买
                    intent.putExtra("nftState", "5");
                }else if (rows.get(i).getSellMode() == 2) {//出价
                    intent.putExtra("nftState", "6");
                }
                getContext().startActivity(intent);
            }
        });
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                page = 1;
                if(!TextUtils.isEmpty(SpUtil.getInstance(getContext()).getString("walletAdd"))) {
                    iMarkPresenter.getData(page, 10, SpUtil.getInstance(getContext()).getString("walletAdd"));
                }
                markAllAdapter.cancelAllTimers();
            }
        });
        swipeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(!TextUtils.isEmpty(SpUtil.getInstance(getContext()).getString("walletAdd"))) {
                    iMarkPresenter.getData(page, 10, SpUtil.getInstance(getContext()).getString("walletAdd"));
                }
            }
        });
    }


    @Override
    public void loadZhuZaoData(MarkHotBean quanDatabase) {
        if (quanDatabase != null && quanDatabase.getCode() == 200) {

            if (page == 1) {//首页
                if (rows != null) {
                    rows.clear();
                }
                if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                    all_rec2.setVisibility(View.VISIBLE);
                    tv_no_data.setVisibility(View.GONE);

                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();

                    page++;
                } else {
                    Log.e("FFD33", "没有数据:" + quanDatabase.getRows().size());
                    if (swipeRefresh != null) {
                        swipeRefresh.finishLoadMore();
                    }
                    all_rec2.setVisibility(View.GONE);
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                if (swipeRefresh != null) {
                    swipeRefresh.finishRefresh();
                }
            } else {//多页
                all_rec2.setVisibility(View.VISIBLE);
                tv_no_data.setVisibility(View.GONE);
                Log.e("收到刷新", "多页:" + page + "--size-:" + quanDatabase.getRows().size());
                if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() == 10) {
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    page++;
                    markAllAdapter.setNewData(rows);
                } else if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() < 10) {
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    page++;
                    markAllAdapter.setNewData(rows);
                } else {

                }
                if (swipeRefresh != null) {
                    swipeRefresh.finishLoadMore();
                }
            }
        } else {
            swipeRefresh.finishLoadMore();
            all_rec2.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadZhuZaoFail() {
        if (swipeRefresh != null) {
            swipeRefresh.finishRefresh();
        }
        if (swipeRefresh != null) {
            swipeRefresh.finishLoadMore();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (markAllAdapter != null) {
            markAllAdapter.cancelAllTimers();
        }
    }
}