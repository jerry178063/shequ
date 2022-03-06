package com.unique.blockchain.nft.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.TradeListAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.trade.GoTradeBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.trade.TradeListDetailActivity;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradePresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradePresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeHomeCallbask;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyGoBuySearchActivity extends BaseActivity implements ITradeHomeCallbask, INetUpper {
    @BindView(R.id.recyclew_trade)
    RecyclerView recyclew_trade;
    @BindView(R.id.swipeRefresh)
    SmartRefreshLayout swipeRefresh;
    @BindView(R.id.tv_go_scan)
    LinearLayout tv_go_scan;
    @BindView(R.id.img_back)
    LinearLayout img_back;
    private ITradePresenter iTradePresenter;
    private String TAG = "TradeFragment";
    private List<GoTradeBean.Rows> list = new ArrayList<>();
    private TradeListAdapter tradeListAdapter = new TradeListAdapter(R.layout.fragment_my_buy_list_item, list);

    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private int pageNum = 1;//页面
    private int pageSize = 10;//请求一页返回的数据数量
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    @BindView(R.id.img_search)
    ImageView img_search;
    @BindView(R.id.edit_search)
    EditText edit_search;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_go_buy_search;
    }

    @Override
    public void initUI() {
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(MyGoBuySearchActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
        CommonUtil.setStatusBarTextColor(MyGoBuySearchActivity.this, 1);


        tradeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(MyGoBuySearchActivity.this, PaiActivity.class);
                    if (list != null && list.size() > 0) {
                        intent.putExtra("nftId", list.get(i).getNftId() + "");
                    }
                    intent.putExtra("companyId", uniqueAdress + "");
                    intent.putExtra("nftState", "4");
                    startActivity(intent);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                showDialog();
                pageNum = 1;
                iTradePresenter.getData(pageNum, pageSize,edit_search.getText().toString(), uniqueAdress);
            }
        });
        swipeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                showDialog();
                iTradePresenter.getData(pageNum, pageSize,edit_search.getText().toString(), uniqueAdress);
            }
        });
        tradeListAdapter.setGoTrade(new TradeListAdapter.GoTradeListener() {
            @Override
            public void goTrade(int position) {
                if (MoreClick.MoreClicks()) {
                    if (list.get(position).getGetChain() == 0) {
                        Intent intent = new Intent(MyGoBuySearchActivity.this, TradeListDetailActivity.class);
                        intent.putExtra("productType", list.get(position).getProductType() + "");
                        intent.putExtra("productName", list.get(position).getProductName() + "");
                        intent.putExtra("nftId", list.get(position).getNftId() + "");
                        intent.putExtra("companyId", list.get(position).getCompanyId() + "");
                        intent.putExtra("uniqueAdress", uniqueAdress);
                        intent.putExtra("orderId",list.get(position).getId() + "");
                        startActivity(intent);
                    } else {
                        ToastUtil.showShort(MyGoBuySearchActivity.this, "正在交易确认中!");
                    }
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lin_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    BaseEvent baseEvent = BaseEvent.obtainMessage();
                    baseEvent.what = "go_scan";
                    EventBus.getDefault().post(baseEvent);
                    finish();
                }

            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    if(iTradePresenter != null){
                        pageNum = 1;
                        iTradePresenter.getData(pageNum, pageSize,edit_search.getText().toString(), uniqueAdress);
                    }
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclew_trade.setLayoutManager(linearLayoutManager);
        recyclew_trade.setAdapter(tradeListAdapter);
        tradeListAdapter.getContext(this);


        iTradePresenter = new ITradePresenterImpl();
        iTradePresenter.registerViewCallback(this);
        Log.e("FDS344", "onresume.....");
        pageNum = 1;
//        iTradePresenter.getData(pageNum, pageSize,edit_search.getText().toString(), uniqueAdress);

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public void recv(String skb) {

    }

    @Override
    public void notify(NetEvent event) {

    }

    @Override
    public void loadData(GoTradeBean jsonObject) {
        swipeRefresh.finishRefresh();
        dismissDialog();
        if (jsonObject != null) {
            if (jsonObject.getCode() == 200) {
                Log.e("fww433", "size:" + jsonObject.getRows().size());

                if (pageNum == 1) {//首页
                    if (list != null) {
                        list.clear();
                    }
                    if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0) {

                        list.addAll(jsonObject.getRows());
                        tradeListAdapter.notifyDataSetChanged();
                        pageNum++;
                        tv_go_scan.setVisibility(View.GONE);
                        recyclew_trade.setVisibility(View.VISIBLE);
                    } else {
                        tv_go_scan.setVisibility(View.VISIBLE);
                        recyclew_trade.setVisibility(View.GONE);
                    }
                    if (swipeRefresh != null) {
                        swipeRefresh.finishLoadMore();
                    }
                } else {//多页
                    tv_go_scan.setVisibility(View.GONE);
                    recyclew_trade.setVisibility(View.VISIBLE);
                    if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0 && jsonObject.getRows().size() == 10) {
                        list.addAll(jsonObject.getRows());
                        tradeListAdapter.notifyDataSetChanged();
                        pageNum++;
                    } else if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0 && jsonObject.getRows().size() < 10) {
                        list.addAll(jsonObject.getRows());
                        tradeListAdapter.notifyDataSetChanged();
                        pageNum++;
                    } else {

                    }
                    if (swipeRefresh != null) {
                        swipeRefresh.finishLoadMore();
                    }
                }
            } else {
                tv_go_scan.setVisibility(View.VISIBLE);
                recyclew_trade.setVisibility(View.GONE);
                Log.e("FDS344", "6666666");
                if (swipeRefresh != null) {
                    swipeRefresh.finishLoadMore();
                }

            }
        }
//        sendMeg();
    }

    @Override
    public void loadFail() {
        swipeRefresh.finishRefresh();
        dismissDialog();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iTradePresenter != null) {
            iTradePresenter.unRegisterViewCallback(null);
        }
    }
}
