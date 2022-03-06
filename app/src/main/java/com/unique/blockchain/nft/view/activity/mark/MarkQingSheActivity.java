package com.unique.blockchain.nft.view.activity.mark;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.unique.blockchain.nft.adapter.MarkQingSheAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkQingShePresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkQingShePresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkQingSheCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 轻奢品---更多
 * */
public class MarkQingSheActivity extends BaseActivity implements IMarkQingSheCallBack {
    private RecyclerView all_rec;
    private MarkQingSheAdapter markQingSheAdapter;
    private IMarkQingShePresenter iMarkQingShePresenter;
    private List<QuanDatabase.RowsBean> rows;
    private int page = 1;
    LinearLayout img_back;
    SmartRefreshLayout smr;
    private TextView tv_title;
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
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("轻奢品");
        all_rec = findViewById(R.id.All_rec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        all_rec.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        markQingSheAdapter = new MarkQingSheAdapter(R.layout.search,rows);
        all_rec.setAdapter(markQingSheAdapter);

        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(MarkQingSheActivity.this, Tools.name, ""))).build().unique();
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
        markQingSheAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                Log.e("FF3331","position:" + position + "-----" + rows.get(position).getSellMode());
                if(rows.get(position).getSellMode() == 1){//购买
                    Intent intent = new Intent(MarkQingSheActivity.this, BuyMarkActivity.class);
                    intent.putExtra("title",rows.get(position).getNftName());
                    intent.putExtra("nftId",rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress",uniqueAdress);
                    intent.putExtra("productType",rows.get(position).getProductType() + "");
                    startActivity(intent);
                    Log.e("FF3331","购买");
                }else if(rows.get(position).getSellMode() == 2){//出价
                    Intent intent = new Intent(MarkQingSheActivity.this, OutPriceActivity.class);
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
                if(iMarkQingShePresenter != null) {
                    showDialog();
                    page = 1;
                    iMarkQingShePresenter.getData(page, 10, "","4");
                }
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMarkQingShePresenter != null) {
                    showDialog();
                    iMarkQingShePresenter.getData(page, 10, "","4");
                }
            }
        });
    }

    @Override
    public void initData() {
        showDialog();
        iMarkQingShePresenter = new IMarkQingShePresenterImpl();
        iMarkQingShePresenter.registerViewCallback(this);
        iMarkQingShePresenter.getData(page,10,"","4");
    }

    @Override
    public void bindView(Bundle bundle) {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_artwork;
    }
    @Override
    public void loadQingSheData(QuanDatabase quanDatabase) {
        dismissDialog();
        if(quanDatabase != null && quanDatabase.getCode() == 200){


            if (page == 1) {//首页
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                    if (rows != null) {
                        rows.clear();
                    }
                    rows.addAll(quanDatabase.getRows());
                    markQingSheAdapter.notifyDataSetChanged();

                    page++;
                    tv_no_data.setVisibility(View.GONE);
                }else {
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                if(smr != null){
                    smr.finishRefresh();
                }
                markQingSheAdapter.setNewData(rows);
            }else {//多页
                Log.e("收到刷新","多页:" + page + "--size-:" + quanDatabase.getRows().size());
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() == 10) {
                    rows.addAll(quanDatabase.getRows());
                    markQingSheAdapter.notifyDataSetChanged();
                    page++;
                    markQingSheAdapter.setNewData(rows);
                }else if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() < 10) {
                    rows.addAll(quanDatabase.getRows());
                    markQingSheAdapter.notifyDataSetChanged();
                    page++;
                    markQingSheAdapter.setNewData(rows);
                }else{

                }
                if(smr != null){
                    smr.finishLoadMore();
                }
            }
        }

    }

    @Override
    public void loadQingSheFail() {
        dismissDialog();
        if(smr != null){
            smr.finishRefresh();
        }
        if(smr != null){
            smr.finishLoadMore();
        }
    }


}
