package com.unique.blockchain.nft.view.fragment.trade;

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
import com.space.exchange.biz.net.utils.NetworkUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.CommissionRecordAllAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.DelegtionBeanRecord;
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

/**
 * 委托记录-委托
 * */
public class CommissionFragment extends BaseFragment {

    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    User unique;
    private UserDao userDao;
    private String gaussAdress;
    private CommissionRecordAllAdapter commissionRecordAllAdapter=new CommissionRecordAllAdapter(R.layout.commission_record_all_item,new ArrayList<>());
    private ArrayList <String>list=new ArrayList();
    private List<DelegtionBeanRecord.Result.Records> lists = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_assets;
    }

    @Override
    public void initView() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    gaussAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                smr.finishRefresh();
                page = 1;
                if(NetworkUtil.isConnected(getContext())) {
                    postData(gaussAdress);
                }else {
                    ToastUtil.showShort(getContext(),getResources().getString(R.string.the_network_is_not_open));
                }
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(NetworkUtil.isConnected(getContext())) {
                    postData(gaussAdress);
                }else {
                    ToastUtil.showShort(getContext(),getResources().getString(R.string.the_network_is_not_open));
                }
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commissionRecordAllAdapter);
        commissionRecordAllAdapter.setNewData(lists);



        postData(gaussAdress);
    }
    private void postData(String gaussAdress) {
//        showDialog();
        OkGo.post(UrlConstant.baseUrlLian + "gpb/searchTransactions")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address",gaussAdress)
                .params("pageIndex",page)
                .params("pageSize","10")
                .params("event","delegate")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<DelegtionBeanRecord>() {
                    @Override
                    public void onSuccess(DelegtionBeanRecord jsonObject, Call call, Response response) {
                        Log.e("FFGKK2","jsonObject:" + new Gson().toJson(jsonObject));
//                        dismissDialog();
                        if(jsonObject.getCode() == 200) {
                            if (jsonObject != null && jsonObject.getResult() != null && jsonObject.getResult().getRecords() != null && jsonObject.getResult().getRecords().size() > 0) {
                                if (page == 1) {
                                    if (lists != null) {
                                        lists.clear();
                                    }
                                    lists.addAll(jsonObject.getResult().getRecords());
                                    commissionRecordAllAdapter.notifyDataSetChanged();
                                    page++;
                                    if (!TextUtils.isEmpty(lists.toString()) && lists.size() > 0) {
                                        if (lin_no_data != null) {
                                            lin_no_data.setVisibility(View.GONE);
                                        }
                                    } else {
                                        if (lin_no_data != null) {
                                            lin_no_data.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (smr != null) {
                                        smr.finishLoadMore();
                                    }
                                    if (page <= jsonObject.getResult().getPages()) {
                                        lists.addAll(jsonObject.getResult().getRecords());
                                        commissionRecordAllAdapter.notifyDataSetChanged();
                                    }
                                    page++;
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
                        }else {
                            if (page == 1) {
                                if (TextUtils.isEmpty(lists.toString())) {
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
//                        dismissDialog();
                        Log.e("FFGKK2","onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        dismissDialog();
                    }
                });


    }



}
