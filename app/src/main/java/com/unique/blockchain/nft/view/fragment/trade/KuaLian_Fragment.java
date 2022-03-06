package com.unique.blockchain.nft.view.fragment.trade;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.utils.TextUtils;
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
import com.space.exchange.biz.net.utils.ToastUtil;
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
 * 跨链资产
 */
public class KuaLian_Fragment extends BaseFragment {

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
    String wallet_name;

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
        wallet_name = SpUtil.getInstance(getContext()).getString("wallet_name");
        heYueAdapter = new HeYueAdapter(R.layout.heyue_item, getContext(), balances);
        heYueUniqueAdapter = new HeYueUniqueAdapter(R.layout.heyue_item, getContext(), unBalances);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        all_rec.setLayoutManager(manager);
        if (SpUtil.getInstance(getContext()).getString("wallet_name") != null) {
            if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("ETH")) {
                all_rec.setAdapter(heYueAdapter);
            }else if (SpUtil.getInstance(getContext()).getString("wallet_name").equals("UNIQUE")) {
                all_rec.setAdapter(heYueUniqueAdapter);
            }else {
                tv_no_data.setVisibility(View.VISIBLE);
            }
        }
        heYueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Intent intent1 = new Intent(getContext(), DigitalAssetsActivity.class);
//                intent1.putExtra("zichan_type","3");
//                intent1.putExtra("bizhong_type","ETH");
//                intent1.putExtra("account",balances.get(i).getBalance() + "");
//                intent1.putExtra("wallet_name",wallet_name);
//                startActivity(intent1);
                ToastUtil.showShort(getContext(),"敬请期待!");
            }
        });
        heYueUniqueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Intent intent1 = new Intent(getContext(), DigitalAssetsActivity.class);
//                intent1.putExtra("zichan_type","3");
//                intent1.putExtra("bizhong_type","UNIQUE");
//                intent1.putExtra("account",unBalances.get(i).getAmount() + "");
//                intent1.putExtra("wallet_name",wallet_name);
//                startActivity(intent1);
                ToastUtil.showShort(getContext(),"敬请期待!");
            }
        });

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
                                                if (!homeAsstesBean.getResult().getBalances().get(i).getCoinType().equals("ETH") && homeAsstesBean.getResult().getBalances().get(i).getCoinType().contains("ibc/")) {
                                                    balances.add(homeAsstesBean.getResult().getBalances().get(i));
                                                }
                                            }


                                            heYueAdapter.setData(getContext(), balances);

                                            if(balances != null && balances.size() > 0){
                                                if(tv_no_data != null) {
                                                    tv_no_data.setVisibility(View.GONE);
                                                }
                                            }else {
                                                if(tv_no_data != null) {
                                                    tv_no_data.setVisibility(View.VISIBLE);
                                                }
                                            }
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
                                                if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("ibc/")) {
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