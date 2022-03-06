package com.unique.blockchain.nft.view.fragment.trade;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import com.unique.blockchain.nft.domain.node.GetRootInvitationBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 委托记录-赎回
 */
public class CommissionRedemptionFragment extends BaseFragment {

    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    User unique;
    private UserDao userDao;
    private String gaussAdress,rootAddress;
    private int page = 1;
    private CommissionRecordAllAdapter commissionRecordAllAdapter = new CommissionRecordAllAdapter(R.layout.commission_record_all_item, new ArrayList<>());
    private List<DelegtionBeanRecord.Result.Records> lists = new ArrayList<>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);

            //获取当前网络时间
            String webUrl="http://www.baidu.com";//百度时间
            new Thread(new Runnable(){
                @Override
                public void run() {
                    //获取当前网络时间
                    String webUrl="http://www.baidu.com";//百度时间
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            String webTime=getNetworkTime(webUrl);
                            commissionRecordAllAdapter.setTime(webTime);
                            commissionRecordAllAdapter.notifyDataSetChanged();
                        }
                    }).start();
                }
            }).start();

        }
    };

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
                if (NetworkUtil.isConnected(getContext())) {
                    postData(gaussAdress);
                } else {
                    ToastUtil.showShort(getContext(), getResources().getString(R.string.the_network_is_not_open));
                }
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if (NetworkUtil.isConnected(getContext())) {
                    postData(gaussAdress);
                } else {
                    ToastUtil.showShort(getContext(), getResources().getString(R.string.the_network_is_not_open));
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commissionRecordAllAdapter);
        commissionRecordAllAdapter.setNewData(lists);
        postYzData(gaussAdress);
        postData(gaussAdress);

    }

    private void postData(String gaussAdress) {
        Log.e("FFGKK2", "gaussAdress:" + gaussAdress);
//        showDialog();
        OkGo.post(UrlConstant.baseUrlLian + "gpb/searchTransactions")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address", gaussAdress)
                .params("pageIndex", page)
                .params("pageSize", "10")
                .params("event", "begin_unbonding")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<DelegtionBeanRecord>() {
                    @Override
                    public void onSuccess(DelegtionBeanRecord jsonObject, Call call, Response response) {
//                        dismissDialog();
                        Log.e("FF8876785","赎回:" + new Gson().toJson(jsonObject));

                        if (jsonObject.getCode() == 200) {
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
//                              handler.sendEmptyMessage(0);
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
                        Log.e("FF887678","赎回_onFailure:" + code);
//                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        dismissDialog();
                    }
                });


    }
    /*
      * 获取当前网络时间
     */
    public static String getNetworkTime(String webUrl) {
        try {
            URL url=new URL(webUrl);
            URLConnection conn=url.openConnection();
            conn.connect();
            long dateL=conn.getDate();
            Date date=new Date(dateL);

        //以下是将时间转换成String类型并返回
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm");
            return dateFormat.format(date);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";
    }
    //获取验证人地址
    private void postYzData(String address) {
        OkGo.get(UrlConstant.baseCreateInportWallet + "system/setting/getRootInvitationCode")
                .execute(new JsonCallback<GetRootInvitationBean>() {
                    @Override
                    public void onSuccess(GetRootInvitationBean getRootInvitationBean, Call call, Response response) {
                        Log.e("FF8876789", "查询验证人:" + getRootInvitationBean);
                        smr.finishRefresh();
                        //请求成功
                        if (getRootInvitationBean != null && getRootInvitationBean.getData() != null) {

                            rootAddress = getRootInvitationBean.getData().getValidatoraddr();
                            postData2(rootAddress);
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        smr.finishRefresh();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                        smr.finishRefresh();
                    }
                });

    }
    private void postData2(String gaussAdress) {
        Log.e("FFGKK2", "gaussAdress:" + gaussAdress);
//        showDialog();
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators/" + gaussAdress + "/unbonding_delegations")
//                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
//                .params("address", gaussAdress)
//                .params("pageIndex", page)
//                .params("pageSize", "10")
//                .params("event", "begin_unbonding")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
//                        dismissDialog();
                        Log.e("FF8876789","赎回2:" + jsonObject);
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF8876789","赎回_onFailure:" + code);
//                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        dismissDialog();
                    }
                });


    }


}
