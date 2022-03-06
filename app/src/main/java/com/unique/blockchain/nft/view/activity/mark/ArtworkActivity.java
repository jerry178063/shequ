package com.unique.blockchain.nft.view.activity.mark;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MarkAllAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkYishuPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkYishuPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkYiShuCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 艺术品---更多
 * */
public class ArtworkActivity extends BaseActivity implements IMarkYiShuCallBack {
    private RecyclerView all_rec;
    private MarkAllAdapter markAllAdapter;
    private IMarkYishuPresenter iMarkYishuPresenter;
    private List<QuanDatabase.RowsBean> rows;
    private int page = 1;
    LinearLayout img_back;
    SmartRefreshLayout smr;
    LinearLayout tv_no_data;
    public User unique;
    public UserDao userDao;
    public  String uniqueAdress;

    @Override
    public void initUI() {
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        tv_no_data = findViewById(R.id.tv_no_data);
        all_rec = findViewById(R.id.All_rec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        all_rec.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        markAllAdapter = new MarkAllAdapter(R.layout.search,rows);
        all_rec.setAdapter(markAllAdapter);

        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(ArtworkActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
        markAllAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if(rows.get(position).getSellMode() == 1){//购买
                    Intent intent = new Intent(ArtworkActivity.this, BuyMarkActivity.class);
                    intent.putExtra("title",rows.get(position).getNftName());
                    intent.putExtra("nftId",rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress",uniqueAdress);
                    intent.putExtra("productType",rows.get(position).getProductType() + "");
                    startActivity(intent);
                    Log.e("FF3331","购买");
                }else if(rows.get(position).getSellMode() == 2){//出价
                    Intent intent = new Intent(ArtworkActivity.this, OutPriceActivity.class);
                    intent.putExtra("title",rows.get(position).getNftName());
                    intent.putExtra("nftId",rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress",uniqueAdress);
                    intent.putExtra("productType",rows.get(position).getProductType() + "");
                    startActivity(intent);
                    Log.e("FF3331","出价");
                }
            }
        });
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        smr = findViewById(R.id.smr);
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMarkYishuPresenter != null) {
                    showDialog();
                    page = 1;
                    iMarkYishuPresenter.getData(page, 10, "","3");
                }
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMarkYishuPresenter != null) {
                    showDialog();
                    iMarkYishuPresenter.getData(page, 10, "","3");
                }
            }
        });
    }

    @Override
    public void initData() {
        showDialog();
        iMarkYishuPresenter = new IMarkYishuPresenterImpl();
        iMarkYishuPresenter.registerViewCallback(this);
        iMarkYishuPresenter.getData(page,10,"","3");
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_artwork;
    }
    @Override
    public void loadYishuData(QuanDatabase quanDatabase) {
        dismissDialog();
        if(quanDatabase != null && quanDatabase.getCode() == 200){


            if (page == 1) {//首页
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                    if (rows != null) {
                        rows.clear();
                    }
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();

                    page++;
                    tv_no_data.setVisibility(View.GONE);
                }else {
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                if(smr != null){
                    smr.finishRefresh();
                }
                markAllAdapter.setNewData(rows);
            }else {//多页
                Log.e("收到刷新","多页:" + page + "--size-:" + quanDatabase.getRows().size());
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() == 10) {
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    page++;
                    markAllAdapter.setNewData(rows);
                }else if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() < 10) {
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    page++;
                    markAllAdapter.setNewData(rows);
                }else{

                }
                if(smr != null){
                    smr.finishLoadMore();
                }
            }
        }

    }

    @Override
    public void loadYishuFail() {
        dismissDialog();
        if(smr != null){
            smr.finishRefresh();
        }
        if(smr != null){
            smr.finishLoadMore();
        }
    }


}
