package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
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
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkQingShePresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkQingShePresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkQingSheCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Grade_Home_Fragment extends BaseFragment implements IMarkQingSheCallBack {

    @BindView(R.id.swipeRefresh)
    SmartRefreshLayout swipeRefresh;
    @BindView(R.id.all_rec)
    RecyclerView all_rec;
    private MarkQingSheAdapter markQingSheAdapter;
    private IMarkQingShePresenter iMarkQingShePresenter;
    private List<QuanDatabase.RowsBean> rows;
    private int page = 1;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    public User unique;
    public UserDao userDao;
    public  String uniqueAdress;
    @Override
    protected void initData() {
        iMarkQingShePresenter = new IMarkQingShePresenterImpl();
        iMarkQingShePresenter.registerViewCallback(this);
        iMarkQingShePresenter.getData(page,10,"2","4");

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
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_;
    }

    @Override
    public void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        all_rec.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        markQingSheAdapter = new MarkQingSheAdapter(R.layout.search,rows);
        all_rec.setAdapter(markQingSheAdapter);


        markQingSheAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    Log.e("FF3331", "position:" + position + "-----" + rows.get(position).getSellMode());
                    if (rows.get(position).getSellMode() == 1) {//购买
                        Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                        intent.putExtra("title", rows.get(position).getNftName());
                        intent.putExtra("nftId", rows.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rows.get(position).getWalletAddr());
                        intent.putExtra("productType", rows.get(position).getProductType() + "");
                        startActivity(intent);
                        Log.e("FF3331", "购买");
                    } else if (rows.get(position).getSellMode() == 2) {//出价
                        Intent intent = new Intent(getContext(), OutPriceActivity.class);
                        intent.putExtra("title", rows.get(position).getNftName());
                        intent.putExtra("nftId", rows.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rows.get(position).getWalletAddr());
                        intent.putExtra("productType", rows.get(position).getProductType() + "");
                        startActivity(intent);
                        Log.e("FF3331", "出价");
                    }
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMarkQingShePresenter != null) {
                    showDialog();
                    page = 1;
                    iMarkQingShePresenter.getData(page,10,"2","4");
                    markQingSheAdapter.cancelAllTimers();
                }
            }
        });
        swipeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMarkQingShePresenter != null) {
                    showDialog();
                    iMarkQingShePresenter.getData(page,10,"2","4");
                }
            }
        });
    }

    @Override
    public void loadQingSheData(QuanDatabase quanDatabase) {
        dismissDialog();
        if(quanDatabase != null && quanDatabase.getCode() == 200){


            if (page == 1) {//首页
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                    all_rec.setVisibility(View.VISIBLE);
                    tv_no_data.setVisibility(View.GONE);
                    if (rows != null) {
                        rows.clear();
                    }
                    rows.addAll(quanDatabase.getRows());
                    markQingSheAdapter.notifyDataSetChanged();

                    page++;
                }else {
                    if(swipeRefresh != null){
                        swipeRefresh.finishLoadMore();
                    }
                    all_rec.setVisibility(View.GONE);
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                if(swipeRefresh != null){
                    swipeRefresh.finishRefresh();
                }
            }else {//多页
                all_rec.setVisibility(View.VISIBLE);
                tv_no_data.setVisibility(View.GONE);
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
                if(swipeRefresh != null){
                    swipeRefresh.finishLoadMore();
                }
            }
        }else {
            swipeRefresh.finishLoadMore();
            all_rec.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadQingSheFail() {
        dismissDialog();
        if(swipeRefresh != null){
            swipeRefresh.finishRefresh();
        }
        if(swipeRefresh != null){
            swipeRefresh.finishLoadMore();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        markQingSheAdapter.cancelAllTimers();
    }
}