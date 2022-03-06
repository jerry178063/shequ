package com.unique.blockchain.nft.view.fragment.wallet;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.AllAsstesAdapter;
import com.unique.blockchain.nft.adapter.EthAllAsstesAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordEthBean;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordNewBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.DigitalHomeAssetsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class EthAllassetsFragment extends BaseFragment {

    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;

    private EthAllAsstesAdapter allAsstesAdapter = new EthAllAsstesAdapter(R.layout.all_assets_item, new ArrayList<>());
    private List<TransactionRecordEthBean.Result.Records> list = new ArrayList();
    User unique;
    private UserDao userDao;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_assets;
    }

    @Override
    public void initView() {

        initData(1);
        showDialog();
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                showDialog();
                page = 1;
                smr.finishRefresh();
                initData(2);
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {

                initData(2);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allAsstesAdapter);
        allAsstesAdapter.setNewData(list);

    }

    String address;
    int page = 1;

    public void initData(int pos) {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("ffff444", "e: " + e);
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    address = unique.getMObjectList().get(i).getCoin_psd();
                    allAsstesAdapter.setAddress(address);
                    break;
                }
            }
        }

        Log.e("FFKK34","address:" + SpUtil.getInstance(getContext()).getString("wallet_address"));
//        showDialog();
        OkGo.get(UrlConstant.baseUrlLian + "ethereum/getTransactionsByAddress")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address", SpUtil.getInstance(getContext()).getString("wallet_address"))
                .params("pageSize", 5)
                .params("pageIndex", page)
//                .params("status", "")
                .params("isInput","")
//                .params("sort",1)
//                .headers("token", "")
//                    .upJson(map.toString())
                .connTimeOut(10000)
                .execute(new JsonCallback<TransactionRecordEthBean>() {
                    @Override
                    public void onSuccess(TransactionRecordEthBean versionInfo, Call call, Response response) {
                        if (smr != null) {
                            smr.finishRefresh();
                        }
                        dismissDialog();
                        Log.e("FFKK34", "全部:" + new Gson().toJson(versionInfo));
                        //请求成功
                        if (versionInfo.getCode() == 200) {

                            if (versionInfo.getResult() != null && versionInfo.getResult().getRecords().size() > 0) {
                                if (page == 1) {
                                    if (!TextUtils.isEmpty(list.toString())) {
                                        list.clear();
                                    }
                                    Log.e("FFKK34", "size:" + list.size());
                                    list.addAll(versionInfo.getResult().getRecords());
                                    allAsstesAdapter.notifyDataSetChanged();
                                    page++;
                                } else {
                                    if (smr != null) {
                                        smr.finishLoadMore();
                                    }
                                    if (page <= versionInfo.getResult().getPages()) {
                                        list.addAll(versionInfo.getResult().getRecords());
                                        allAsstesAdapter.notifyDataSetChanged();
                                    }
                                    page++;
                                }
                                if(!TextUtils.isEmpty(list.toString()) && list.size() > 0) {
                                    if (lin_no_data != null) {
                                        lin_no_data.setVisibility(View.GONE);
                                    }
                                }else {
                                    if (lin_no_data != null) {
                                        lin_no_data.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {

                                if (page == 1) {
                                    if (lin_no_data != null) {
                                        lin_no_data.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    if (smr != null) {
                                        smr.finishLoadMore();
                                    }
                                    ToastUtil.showShort(getActivity(), "已加载完成");
                                }

                            }
                        } else {
                            if (page == 1) {
                                if (TextUtils.isEmpty(list.toString())) {
                                    if (lin_no_data != null) {
                                        lin_no_data.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        if (smr != null) {
                            smr.finishRefresh();
                            smr.finishLoadMore();
                        }
                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (smr != null) {
                            smr.finishRefresh();
                        }
                        dismissDialog();
                    }
                });

    }


}
