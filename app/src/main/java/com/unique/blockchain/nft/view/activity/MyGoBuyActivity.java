package com.unique.blockchain.nft.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.space.exchange.biz.common.util.SpUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.BuyNftAdapter;
import com.unique.blockchain.nft.adapter.NftZichanAdapter;
import com.unique.blockchain.nft.adapter.TradeListAdapter;
import com.unique.blockchain.nft.adapter.TradingAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.domain.trade.GoTradeBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.me.NftCancelBackActivity;
import com.unique.blockchain.nft.view.activity.me.NftTiHuoActivity;
import com.unique.blockchain.nft.view.activity.me.NftTransferActivity;
import com.unique.blockchain.nft.view.activity.me.SouMeActivity;
import com.unique.blockchain.nft.view.activity.trade.TradeListDetailActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMeMyNftAllPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMeMyNftAllPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMeMyNftAllCallBack;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradePresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradePresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeHomeCallbask;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyGoBuyActivity extends BaseActivity implements ITradeHomeCallbask, INetUpper, IMeMyNftAllCallBack {
//    @BindView(R.id.recyclew_trade)
//    RecyclerView recyclew_trade;
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
//    @BindView(R.id.lin_no_data)
//    LinearLayout lin_no_data;
    @BindView(R.id.liu_souyy)
    LinearLayout liu_souyy;
    @BindView(R.id.edit_search)
    TextView edit_search;
    private String nextKey;

    //------------
    @BindView(R.id.all_rec2)
    RecyclerView all_rec2;
    private BuyNftAdapter allMyTradeAdapter;
    private IMeMyNftAllPresenter iMeMyNftAllPresenter;
    private List<MarkHotBean.Rows> rows;
    private int page = 1;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    @BindView(R.id.lin_nft_name)
    LinearLayout lin_nft_name;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_go_buy;
    }

    @Override
    public void initUI() {
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(MyGoBuyActivity.this, Tools.name, ""))).build().unique();
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
        CommonUtil.setStatusBarTextColor(MyGoBuyActivity.this, 1);


//        tradeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                if (MoreClick.MoreClicks()) {
//                    Intent intent = new Intent(MyGoBuyActivity.this, PaiActivity.class);
//                    if (list != null && list.size() > 0) {
//                        intent.putExtra("nftId", list.get(i).getNftId() + "");
//                    }
//                    intent.putExtra("companyId", uniqueAdress + "");
//                    intent.putExtra("nftState", "4");
//                    startActivity(intent);
//                }
//            }
//        });
//        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
//                showDialog();
//                pageNum = 1;
//                iTradePresenter.getData(pageNum, pageSize,edit_search.getText().toString(), uniqueAdress);
//            }
//        });
//        swipeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
//                if(!TextUtils.isEmpty(nextKey)) {
//                    showDialog();
//                    iTradePresenter.getData(pageNum, pageSize, edit_search.getText().toString(), uniqueAdress);
//                }else {
//                    swipeRefresh.finishLoadMore();
//                }
//            }
//        });
//        tradeListAdapter.setGoTrade(new TradeListAdapter.GoTradeListener() {
//            @Override
//            public void goTrade(int position) {
//                if (MoreClick.MoreClicks()) {
//                    if (list.get(position).getGetChain() == 0) {
//                        Intent intent = new Intent(MyGoBuyActivity.this, TradeListDetailActivity.class);
//                        intent.putExtra("productType", list.get(position).getProductType() + "");
//                        intent.putExtra("productName", list.get(position).getProductName() + "");
//                        intent.putExtra("nftId", list.get(position).getNftId() + "");
//                        intent.putExtra("companyId", list.get(position).getCompanyId() + "");
//                        intent.putExtra("uniqueAdress", uniqueAdress);
//                        startActivity(intent);
//                    } else {
//                        ToastUtil.showShort(MyGoBuyActivity.this, "正在交易确认中!");
//                    }
//                }
//            }
//        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (MoreClick.MoreClicks()) {
                    BaseEvent baseEvent = BaseEvent.obtainMessage();
                    baseEvent.what = "go_scan";
                    EventBus.getDefault().post(baseEvent);
                    finish();
//                }

            }
        });
        liu_souyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
//                    if(iTradePresenter != null){
//                        pageNum = 1;
//                        iTradePresenter.getData(pageNum, pageSize,edit_search.getText().toString(), uniqueAdress);
//                    }
                    startActivity(new Intent(MyGoBuyActivity.this, SouMeActivity.class));
                }
            }
        });
        //--------------------
        iMeMyNftAllPresenter = new IMeMyNftAllPresenterImpl();
        iMeMyNftAllPresenter.registerViewCallback(this);
        iMeMyNftAllPresenter.getData(page, 10, "", uniqueAdress);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MyGoBuyActivity.this);
        all_rec2.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        allMyTradeAdapter = new BuyNftAdapter(R.layout.nft_buy_item, rows,MyGoBuyActivity.this, uniqueAdress);
        if (SpUtil.getInstance(MyGoBuyActivity.this).getString("wallet_name").equals("UNIQUE")) {
            all_rec2.setAdapter(allMyTradeAdapter);
        }else {
            tv_go_scan.setVisibility(View.VISIBLE);
        }

        allMyTradeAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (rows.get(position).getSellMode() == 1) {//购买
                    Intent intent = new Intent(MyGoBuyActivity.this, BuyMarkActivity.class);
                    intent.putExtra("title", rows.get(position).getNftName());
                    intent.putExtra("nftId", rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress", uniqueAdress);
                    intent.putExtra("productType", rows.get(position).getProductType() + "");
                    startActivity(intent);
                } else if (rows.get(position).getSellMode() == 2) {//出价
                    Intent intent = new Intent(MyGoBuyActivity.this, OutPriceActivity.class);
                    intent.putExtra("title", rows.get(position).getNftName());
                    intent.putExtra("nftId", rows.get(position).getNftId());
                    intent.putExtra("uniqueAdress", uniqueAdress);
                    intent.putExtra("productType", rows.get(position).getProductType() + "");
                    startActivity(intent);
                }
            }
        });
        allMyTradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(MyGoBuyActivity.this, PaiActivity.class);
                intent.putExtra("nftId", rows.get(i).getNftId() + "");
                intent.putExtra("isTrasfer",rows.get(i).getIsTransfer() + "");
                intent.putExtra("surplus",rows.get(i).getSurplus() + "");
                intent.putExtra("my_bug","my_bug");
                MyGoBuyActivity.this.startActivity(intent);
            }
        });
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                page = 1;
                iMeMyNftAllPresenter.getData(page, 10, "", uniqueAdress);
                allMyTradeAdapter.cancelAllTimers();
            }
        });
        swipeRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(TextUtils.isEmpty(nextKey)){
                    swipeRefresh.finishLoadMore();
                }else {
                    iMeMyNftAllPresenter.getData(page, 10, "", uniqueAdress);
                }
            }
        });
        //转移
        allMyTradeAdapter.setTransfer(new BuyNftAdapter.Transfer() {
            @Override
            public void Transfer(int position, MarkHotBean.Rows item) {
                Intent intent = new Intent(MyGoBuyActivity.this, NftTransferActivity.class);
                intent.putExtra("nftId", item.getNftId());
                intent.putExtra("cover", item.getProductCover());
                intent.putExtra("type", item.getProductType() + "");
                intent.putExtra("name", item.getProductName());
                intent.putExtra("intro", item.getProductIntro());
                startActivity(intent);
            }
        });
        //撤销交易
        allMyTradeAdapter.setCancelTrade(new TradingAdapter.CancelTrade() {
            @Override
            public void CancelTrade(int position, MarkHotBean.Rows item) {
                Log.e("FF3454","type:" + item.getProductType());
                Intent intent = new Intent(MyGoBuyActivity.this, NftCancelBackActivity.class);
                intent.putExtra("nftId", item.getNftId());
                intent.putExtra("cover", item.getProductCover());
                intent.putExtra("type", item.getProductType() + "");
                intent.putExtra("name", item.getProductName());
                intent.putExtra("intro", item.getProductIntro());
                intent.putExtra("orderId",item.getId() + "");
                startActivity(intent);
            }
        });
        //去交易
        allMyTradeAdapter.setGoTransfer(new BuyNftAdapter.GoTransfer() {
            @Override
            public void GoTransfer(int position, MarkHotBean.Rows item) {
                Intent intent = new Intent(MyGoBuyActivity.this, TradeListDetailActivity.class);
                intent.putExtra("nftId", item.getNftId() + "");
                intent.putExtra("companyId", uniqueAdress + "");
                startActivity(intent);
            }
        });
        //提货
        allMyTradeAdapter.setTiHuo(new BuyNftAdapter.TiHuo() {
            @Override
            public void TiHuo(int position, MarkHotBean.Rows item) {
                Intent intent = new Intent(MyGoBuyActivity.this, NftTiHuoActivity.class);
                intent.putExtra("img", item.getProductImage());
                intent.putExtra("name", item.getProductName());
                intent.putExtra("nftId", item.getNftId() + "");
                intent.putExtra("productType",item.getProductType() + "");
                intent.putExtra("orderId",item.getId() + "");
                startActivity(intent);
            }
        });
        allMyTradeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(MyGoBuyActivity.this, PaiActivity.class);
                intent.putExtra("nftId", rows.get(i).getNftId() + "");
                intent.putExtra("nftState", rows.get(i).getNftState());
                intent.putExtra("surplus",rows.get(i).getSurplus() + "");
                intent.putExtra("my_bug","my_bug");
                MyGoBuyActivity.this.startActivity(intent);

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
//        recyclew_trade.setLayoutManager(linearLayoutManager);
//        recyclew_trade.setAdapter(tradeListAdapter);
//        tradeListAdapter.getContext(this);
//
//
//        iTradePresenter = new ITradePresenterImpl();
//        iTradePresenter.registerViewCallback(this);
//        Log.e("FDS344", "onresume.....");
//        pageNum = 1;
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
//        if (jsonObject != null) {
//            if (jsonObject.getCode() == 200) {
//                Log.e("fww433", "size:" + jsonObject.getRows().size());
//
//                if (pageNum == 1) {//首页
//                    if (list != null) {
//                        list.clear();
//                    }
//                    if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0) {
//
//                        list.addAll(jsonObject.getRows());
//                        tradeListAdapter.notifyDataSetChanged();
//                        pageNum++;
//                        tv_go_scan.setVisibility(View.GONE);
//                        recyclew_trade.setVisibility(View.VISIBLE);
//
//                        nextKey = jsonObject.getRows().get(0).getNextKey();
//                    } else {
//                        tv_go_scan.setVisibility(View.VISIBLE);
//                        recyclew_trade.setVisibility(View.GONE);
//                    }
//                    if (swipeRefresh != null) {
//                        swipeRefresh.finishLoadMore();
//                    }
//                } else {//多页
//                    tv_go_scan.setVisibility(View.GONE);
//                    recyclew_trade.setVisibility(View.VISIBLE);
//                    if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0 && jsonObject.getRows().size() == 10) {
//                        list.addAll(jsonObject.getRows());
//                        tradeListAdapter.notifyDataSetChanged();
//                        pageNum++;
//                    } else if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0 && jsonObject.getRows().size() < 10) {
//                        list.addAll(jsonObject.getRows());
//                        tradeListAdapter.notifyDataSetChanged();
//                        pageNum++;
//                    } else {
//
//                    }
//                    if (swipeRefresh != null) {
//                        swipeRefresh.finishLoadMore();
//                    }
//                }
//            } else {
//                tv_go_scan.setVisibility(View.VISIBLE);
//                recyclew_trade.setVisibility(View.GONE);
//                Log.e("FDS344", "6666666");
//                if (swipeRefresh != null) {
//                    swipeRefresh.finishLoadMore();
//                }
//
//            }
//        }
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

    @Override
    public void loadMyNftAllData(MarkHotBean quanDatabase) {
        if (quanDatabase != null && quanDatabase.getCode() == 200) {

            if (page == 1) {//首页
                if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                    if(all_rec2 != null) {
                        all_rec2.setVisibility(View.VISIBLE);
                    }
                    if(tv_go_scan != null) {
                        tv_go_scan.setVisibility(View.GONE);
                    }
                    lin_nft_name.setVisibility(View.VISIBLE);
                    liu_souyy.setVisibility(View.VISIBLE);
                    if (rows != null) {
                        rows.clear();
                    }
                    rows.addAll(quanDatabase.getRows());
                    allMyTradeAdapter.notifyDataSetChanged();
                    nextKey = quanDatabase.getRows().get(0).getNextKey();

                    page++;
                } else {
                    if (swipeRefresh != null) {
                        swipeRefresh.finishLoadMore();
                    }
                    lin_nft_name.setVisibility(View.GONE);
                    liu_souyy.setVisibility(View.GONE);
                    if(all_rec2 != null) {
                        all_rec2.setVisibility(View.GONE);
                    }
                    if(tv_go_scan != null) {
                        tv_go_scan.setVisibility(View.VISIBLE);
                    }
                }
                if (swipeRefresh != null) {
                    swipeRefresh.finishRefresh();
                }
            } else {//多页
                if(all_rec2 != null) {
                    all_rec2.setVisibility(View.VISIBLE);
                }
                if(tv_go_scan != null) {
                    tv_go_scan.setVisibility(View.GONE);
                }
                lin_nft_name.setVisibility(View.VISIBLE);
                liu_souyy.setVisibility(View.VISIBLE);
                Log.e("收到刷新", "多页:" + page + "--size-:" + quanDatabase.getRows().size());
                if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() == 10) {
                    rows.addAll(quanDatabase.getRows());
                    allMyTradeAdapter.notifyDataSetChanged();
                    page++;
                } else if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() < 10) {
                    rows.addAll(quanDatabase.getRows());
                    allMyTradeAdapter.notifyDataSetChanged();
                    page++;
                } else {

                }
                if (swipeRefresh != null) {
                    swipeRefresh.finishLoadMore();
                }
            }
        } else {
            swipeRefresh.finishLoadMore();
            if(all_rec2 != null) {
                all_rec2.setVisibility(View.GONE);
            }
            if(tv_go_scan != null) {
                tv_go_scan.setVisibility(View.VISIBLE);
            }
            lin_nft_name.setVisibility(View.GONE);
            liu_souyy.setVisibility(View.GONE);
        }
        allMyTradeAdapter.setNewData(rows);
    }

    @Override
    public void loadMyNftAllFail() {
        if (swipeRefresh != null) {
            swipeRefresh.finishRefresh();
        }
        if (swipeRefresh != null) {
            swipeRefresh.finishLoadMore();
        }
    }
}
