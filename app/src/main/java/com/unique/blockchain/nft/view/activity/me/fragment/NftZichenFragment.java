package com.unique.blockchain.nft.view.activity.me.fragment;

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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.AllMyTradeAdapter;
import com.unique.blockchain.nft.adapter.NftZichanAdapter;
import com.unique.blockchain.nft.adapter.TradingAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.me.NftCancelBackActivity;
import com.unique.blockchain.nft.view.activity.me.NftTiHuoActivity;
import com.unique.blockchain.nft.view.activity.me.NftTransferActivity;
import com.unique.blockchain.nft.view.activity.trade.TradeListDetailActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMeMyNftAllPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMeMyNftAllPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMeMyNftAllCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * NFT资产
 */
public class NftZichenFragment extends BaseFragment implements IMeMyNftAllCallBack {
    static {
        System.loadLibrary("TrustWalletCore");
    }

    @BindView(R.id.all_rec2)
    RecyclerView all_rec2;
    @BindView(R.id.swipeRefresh)
    SmartRefreshLayout swipeRefresh;
    private NftZichanAdapter allMyTradeAdapter;
    private IMeMyNftAllPresenter iMeMyNftAllPresenter;
    private List<MarkHotBean.Rows> rows;
    private int page = 1;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;

    @Override
    protected void initData() {

        iMeMyNftAllPresenter = new IMeMyNftAllPresenterImpl();
        iMeMyNftAllPresenter.registerViewCallback(this);
        iMeMyNftAllPresenter.getData(page, 10, "", uniqueAdress);
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
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        all_rec2.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        allMyTradeAdapter = new NftZichanAdapter(R.layout.nft_zichen_item, rows,getContext(), uniqueAdress);
        if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("UNIQUE")) {
            all_rec2.setAdapter(allMyTradeAdapter);
        }else {
            tv_no_data.setVisibility(View.VISIBLE);
        }

        allMyTradeAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
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
        allMyTradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getContext(), PaiActivity.class);
                    intent.putExtra("nftId", rows.get(i).getNftId() + "");
                    intent.putExtra("isTrasfer", rows.get(i).getIsTransfer() + "");
                    intent.putExtra("surplus", rows.get(i).getSurplus() + "");
                    intent.putExtra("my_bug","my_bug");
                    getContext().startActivity(intent);
                }
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
        allMyTradeAdapter.setTransfer(new NftZichanAdapter.Transfer() {
            @Override
            public void Transfer(int position, MarkHotBean.Rows item) {
                        Intent intent = new Intent(getContext(), NftTransferActivity.class);
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
                Intent intent = new Intent(getContext(), NftCancelBackActivity.class);
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
        allMyTradeAdapter.setGoTransfer(new NftZichanAdapter.GoTransfer() {
            @Override
            public void GoTransfer(int position, MarkHotBean.Rows item) {
                        Intent intent = new Intent(getContext(), TradeListDetailActivity.class);
                        intent.putExtra("nftId", item.getNftId() + "");
                        intent.putExtra("companyId", uniqueAdress + "");
                        startActivity(intent);
            }
        });
        //提货
        allMyTradeAdapter.setTiHuo(new NftZichanAdapter.TiHuo() {
            @Override
            public void TiHuo(int position, MarkHotBean.Rows item) {
                        Intent intent = new Intent(getContext(), NftTiHuoActivity.class);
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
                        Intent intent = new Intent(getContext(), PaiActivity.class);
                        intent.putExtra("nftId", rows.get(i).getNftId() + "");
                        intent.putExtra("nftState", rows.get(i).getNftState());
                        intent.putExtra("surplus",rows.get(i).getSurplus() + "");
                        getContext().startActivity(intent);

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (allMyTradeAdapter != null) {
            allMyTradeAdapter.cancelAllTimers();
        }
    }
    String nextKey;
    @Override
    public void loadMyNftAllData(MarkHotBean quanDatabase) {
        if (quanDatabase != null && quanDatabase.getCode() == 200) {

            if (page == 1) {//首页
                if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                    if(all_rec2 != null) {
                        all_rec2.setVisibility(View.VISIBLE);
                    }
                    if(tv_no_data != null) {
                        tv_no_data.setVisibility(View.GONE);
                    }
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
                    if(all_rec2 != null) {
                        all_rec2.setVisibility(View.GONE);
                    }
                    if(tv_no_data != null) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    }
                }
                if (swipeRefresh != null) {
                    swipeRefresh.finishRefresh();
                }
            } else {//多页
                if(all_rec2 != null) {
                    all_rec2.setVisibility(View.VISIBLE);
                }
                if(tv_no_data != null) {
                    tv_no_data.setVisibility(View.GONE);
                }
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
            if(tv_no_data != null) {
                tv_no_data.setVisibility(View.VISIBLE);
            }
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