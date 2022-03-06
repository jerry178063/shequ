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
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.AllAsstesOutAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordBean;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordNewBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class TransferOutFragment extends BaseFragment {



    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    private AllAsstesOutAdapter allAsstesAdapter=new AllAsstesOutAdapter(R.layout.all_out_assets_item,new ArrayList<>());
    private List<TransactionRecordNewBean.Result.Records> list=new ArrayList();
    User unique;
    private UserDao userDao;
    String address;
    int page = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_assets;
    }

    @Override
    public void initView() {
        initData(1);
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
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

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allAsstesAdapter);
        allAsstesAdapter.setNewData(list);

    }
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
        showDialog();
        OkGo.get(UrlConstant.baseUrlLian + "unique/getAssetsLog")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address", address)
                .params("pageSize", 5)
                .params("pageIndex", page)
                .params("status", "")
                .params("isInput",0)
                .params("sort",1)
//                .headers("token", "")
//                    .upJson(map.toString())
                .connTimeOut(10000)
                .execute(new JsonCallback<TransactionRecordNewBean>() {
                    @Override
                    public void onSuccess(TransactionRecordNewBean versionInfo, Call call, Response response) {
                        if(smr != null) {
                            smr.finishRefresh();
                        }
                        dismissDialog();
                        Log.e("FFKK3","转出:" + versionInfo.getResult().getRecords().size());
                        Log.e("FFKK3","转出:" + new Gson().toJson(versionInfo));
                        //请求成功
                        if(versionInfo.getCode() == 200) {
                            if(versionInfo.getResult() != null && versionInfo.getResult().getRecords().size() > 0) {
                                if(page == 1) {

                                    if(!TextUtils.isEmpty(list.toString())) {
                                        list.clear();
                                    }
                                    list.addAll(versionInfo.getResult().getRecords());
                                    allAsstesAdapter.notifyDataSetChanged();
                                    page++;
                                    if(!TextUtils.isEmpty(list.toString()) && list.size() > 0) {
                                        if (lin_no_data != null) {
                                            lin_no_data.setVisibility(View.GONE);
                                        }
                                    }else {
                                        if (lin_no_data != null) {
                                            lin_no_data.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }else {
                                    if(smr != null) {
                                        smr.finishLoadMore();
                                    }
                                    if(page <= versionInfo.getResult().getPages()) {
                                        list.addAll(versionInfo.getResult().getRecords());
                                        allAsstesAdapter.notifyDataSetChanged();
                                    }
                                    page++;
                                }
                                if (lin_no_data != null) {
                                    lin_no_data.setVisibility(View.GONE);
                                }
                            }else {
                                if (page == 1) {
                                    if (lin_no_data != null) {
                                        lin_no_data.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    if(smr != null) {
                                        smr.finishLoadMore();
                                    }
                                    ToastUtil.showShort(getActivity(), "已加载完成");
                                }
                            }
                        }else {
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
                        if(smr != null) {
                            smr.finishRefresh();
                        }
                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if(smr != null) {
                            smr.finishRefresh();
                        }
                        dismissDialog();
                    }
                });

    }

}
