package com.unique.blockchain.nft.view.fragment.trade;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.HeYueAdapter;
import com.unique.blockchain.nft.adapter.HeYueUniqueAdapter;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.WalletAsstesBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.DigitalAssetsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 合约资产
 */
public class HeYue_Fragment extends BaseFragment {

    @BindView(R.id.swipeRefresh)
    SmartRefreshLayout swipeRefresh;
    @BindView(R.id.all_rec)
    RecyclerView all_rec;
    private HeYueAdapter heYueAdapter;
    private HeYueUniqueAdapter heYueUniqueAdapter;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    List<WalletAsstesBean.Result.Balances> balances = new ArrayList<>();
    List<HomeAsstesBean.Result.Balances> unBalances = new ArrayList<>();

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
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_heyue;
    }

    @Override
    public void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        all_rec.setLayoutManager(manager);
        if (SpUtil.getInstance(getContext()).getString("wallet_name") != null) {
            if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("ETH")) {
                heYueAdapter = new HeYueAdapter(R.layout.heyue_item, getContext(), balances);
                all_rec.setAdapter(heYueAdapter);
                //ETH的item点击事件
                heYueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                        Intent intent1 = new Intent(getContext(), DigitalAssetsActivity.class);
                        intent1.putExtra("zichan_type","2");
                        intent1.putExtra("heyue_type",balances.get(i).getCoinType());
                        intent1.putExtra("account",balances.get(i).getBalance() + "");
                        intent1.putExtra("wallet_name",SpUtil.getInstance(getContext()).getString("wallet_name"));
                        intent1.putExtra("wallet_name_title",balances.get(i).getCoinType());
                        startActivity(intent1);
                    }
                });
            }else if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("UNIQUE")) {
                heYueUniqueAdapter = new HeYueUniqueAdapter(R.layout.heyue_item, getContext(), unBalances);
                all_rec.setAdapter(heYueUniqueAdapter);
                //ETH的item点击事件
                heYueUniqueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                        Intent intent1 = new Intent(getContext(), DigitalAssetsActivity.class);
                        intent1.putExtra("zichan_type","2");
                        intent1.putExtra("account",balances.get(i).getBalance() + "");
                        intent1.putExtra("wallet_name",SpUtil.getInstance(getContext()).getString("wallet_name"));
                        startActivity(intent1);
                    }
                });
            }else {
                tv_no_data.setVisibility(View.VISIBLE);
            }
        }
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                getMonery();
            }
        });

        getMonery();
    }

    private void getMonery() {
        Log.e("FDD43", "wallet_address:" + SpUtil.getInstance(getContext()).getString("wallet_address"));
        if (SpUtil.getInstance(getContext()).getString("wallet_name") != null) {
            if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("ETH")) {
                if (SpUtil.getInstance(getContext()).getString("wallet_address") != null) {
//                OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                    OkGo.get(UrlConstant.baseUrlLian + "ethereum/getBalanceAll")
                            .params("address", SpUtil.getInstance(getContext()).getString("wallet_address"))
                            .headers("projectId", Constants.ETH_HEADRE)
                            .execute(new JsonCallback<JsonObject>() {
                                @Override
                                public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                                    if (swipeRefresh != null) {
                                        swipeRefresh.finishRefresh();
                                        swipeRefresh.finishLoadMore();
                                    }
                                    WalletAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), WalletAsstesBean.class);
                                    Log.e("FDD43", "homeAsstesBean222:" + new Gson().toJson(homeAsstesBean));
                                    if (balances != null) {
                                        balances.clear();
                                    }
                                    if (homeAsstesBean != null && homeAsstesBean.getResult() != null) {
                                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                                if(!TextUtils.isEmpty(homeAsstesBean.getResult().getBalances().get(i).getCoinType())) {
                                                    if (!homeAsstesBean.getResult().getBalances().get(i).getCoinType().equals("ETH")
                                                            && !homeAsstesBean.getResult().getBalances().get(i).getCoinType().contains("ibc/")) {
                                                        balances.add(homeAsstesBean.getResult().getBalances().get(i));
                                                    }
                                                }
                                            }


                                            heYueAdapter.setData(getContext(), balances);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(String code, String message) {
                                    super.onFailure(code, message);
                                    if (swipeRefresh != null) {
                                        swipeRefresh.finishRefresh();
                                        swipeRefresh.finishLoadMore();
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    if (swipeRefresh != null) {
                                        swipeRefresh.finishRefresh();
                                        swipeRefresh.finishLoadMore();
                                    }
                                }
                            });
                }
            } else if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("UNIQUE")) {
                if (SpUtil.getInstance(getContext()).getString("wallet_address") != null) {
                OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
//                    OkGo.get(UrlConstant.baseUrlLian + "ethereum/getBalanceAll")
                            .params("account", SpUtil.getInstance(getContext()).getString("wallet_address"))
                            .headers("projectId", Constants.UNIQUE_HEADRE)
                            .execute(new JsonCallback<JsonObject>() {
                                @Override
                                public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                                    if (swipeRefresh != null) {
                                        swipeRefresh.finishRefresh();
                                        swipeRefresh.finishLoadMore();
                                    }
                                    HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                                    Log.e("FDD43gg", "homeAsstesBean222:" + new Gson().toJson(homeAsstesBean));
                                    if (unBalances != null) {
                                        unBalances.clear();
                                    }
                                    if (homeAsstesBean != null && homeAsstesBean.getResult() != null) {
                                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                                if (!homeAsstesBean.getResult().getBalances().get(i).getDenom().equals("uunique")
                                                        &&!homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("ibc/")) {
                                                    unBalances.add(homeAsstesBean.getResult().getBalances().get(i));
                                                }
                                            }


                                            heYueUniqueAdapter.setData(getContext(), unBalances);
                                        }
                                        Log.e("FDD43gg", "size:" + unBalances.size());
                                        if(unBalances != null && unBalances.size() > 0){
                                            tv_no_data.setVisibility(View.GONE);
                                        }else {
                                            tv_no_data.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(String code, String message) {
                                    super.onFailure(code, message);
                                    if (swipeRefresh != null) {
                                        swipeRefresh.finishRefresh();
                                        swipeRefresh.finishLoadMore();
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    if (swipeRefresh != null) {
                                        swipeRefresh.finishRefresh();
                                        swipeRefresh.finishLoadMore();
                                    }
                                }
                            });
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}