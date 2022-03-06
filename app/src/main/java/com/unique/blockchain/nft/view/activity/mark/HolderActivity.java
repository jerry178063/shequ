package com.unique.blockchain.nft.view.activity.mark;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MarkListRecordAdapter;
import com.unique.blockchain.nft.adapter.TradeListJingBiaoAdapter;
import com.unique.blockchain.nft.adapter.TradeListPowerAdapter;
import com.unique.blockchain.nft.domain.Skbuff;
import com.unique.blockchain.nft.domain.SkbuffTwo;
import com.unique.blockchain.nft.domain.mark.AuctionMarkBean;
import com.unique.blockchain.nft.domain.mark.HolderMarkBean;
import com.unique.blockchain.nft.domain.mark.MarkDetailSocketNewBean;
import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.infrastructure.net.NetClient;
import com.unique.blockchain.nft.infrastructure.net.NetConstant;
import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.infrastructure.utils.IsDestroy;
import com.unique.blockchain.nft.view.fragment.trade.presenter.IOwnerPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 持有者记录更多
 * */
public class HolderActivity extends BaseActivity implements INetUpper {


    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclew_holder)
    RecyclerView recyclew_holder;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    private String nftId,address,did;
    private int  page = 1;
    private int isFirst = 0;
    private List<HolderMarkBean> rows = new ArrayList<>();
    private MarkListRecordAdapter tradeListRecordAdapter = new MarkListRecordAdapter(R.layout.fragment_trade_record_item, rows);
    @Override
    protected int getLayoutId() {
        return R.layout.activity_holder;
    }

    @Override
    public void initUI() {
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        nftId = getIntent().getStringExtra("nftId");
        address = getIntent().getStringExtra("address");
        did =  getIntent().getStringExtra("did");
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        recyclew_holder.setLayoutManager(linearLayoutManager3);
        recyclew_holder.setAdapter(tradeListRecordAdapter);

        sendMeg("open",page);
    }
    private void sendMeg(String cmd,int page) {
        NetClient.getInstance().register(this);
        SkbuffTwo skbuff = new SkbuffTwo();
        skbuff.setCmd(cmd);
        skbuff.setType("nft");
        skbuff.setFlags(NetConstant.NETHDR_FLAG_NONE);
        skbuff.setReserve(NetConstant.NETHDR_RESERVER);
        skbuff.setVersion(NetConstant.NETHDR_VERSION);
        skbuff.setMagic(NetConstant.NETHDR_MAGIC);

        SkbuffTwo.Data data = new SkbuffTwo.Data();
        SkbuffTwo.Data.Group group = new SkbuffTwo.Data.Group();
        group.setCoupleId(nftId);
        group.setWalletAddr(address);
        group.setDid(did);
        group.setGid("g_nft_info");
        List<SkbuffTwo.Data.Group.ModuleList> moduleLists = new ArrayList<>();
        SkbuffTwo.Data.Group.ModuleList moduleList = new SkbuffTwo.Data.Group.ModuleList();
        moduleList.setModuleId("m_nft_holder_log");//持有者记录
        moduleList.setPageSize(5);
        moduleList.setPage(page);

        moduleLists.add(moduleList);

        group.setModuleList(moduleLists);
        data.setGroup(group);
        skbuff.setData(data);
        Log.e("F34EE55", "sendData:" + skbuff.toString());

        NetClient.getInstance().sendTwo(skbuff);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);

            MarkDetailSocketNewBean markDetailSocketBean = new Gson().fromJson(msg.getData().getString("skb"), MarkDetailSocketNewBean.class);
            Log.e("FF3442355", "markDetailSocketBean:" + new Gson().toJson(markDetailSocketBean));
            if (markDetailSocketBean != null && markDetailSocketBean.getCode() == 200) {
                if (markDetailSocketBean.getData() != null && markDetailSocketBean.getData().getData() != null) {
                    for (int i = 0; i < markDetailSocketBean.getData().getData().size(); i++) {
                        //竞标价格
                        if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_nft_holder_log")) {
                            String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                            List<HolderMarkBean> resultBean = new Gson().fromJson(json, new TypeToken<List<HolderMarkBean>>() {
                            }.getType());
                            Log.e("FF2344ff3", "竞标价格:" + new Gson().toJson(resultBean));
//                            Log.e("FF2344ff","持有者记录:" + json);


                            if(page == 1){//第一页
                                if(isFirst == 0) {
                                    if (rows != null) {
                                        rows.clear();
                                    }
                                    if (resultBean != null && resultBean.size() > 0) {
                                        rows.addAll(resultBean);
                                        page++;
                                        tradeListRecordAdapter.setNewData(rows);
                                    } else {

                                    }
                                    if (rows != null && rows.size() > 0) {
                                        if(tv_no_data != null) {
                                            tv_no_data.setVisibility(View.GONE);
                                        }
                                    } else {
                                        if(tv_no_data != null) {
                                            tv_no_data.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (smr != null) {
                                        smr.finishRefresh();
                                    }
                                    Log.e("FF2344ff34", "size:" + rows.size());
                                }else {
                                    rows.addAll(0,resultBean);
                                    tradeListRecordAdapter.setNewData(rows);
                                }
                            }else {
                                if(tv_no_data != null) {
                                    tv_no_data.setVisibility(View.GONE);
                                }
                                if(isFirst == 0) {
                                    if (resultBean != null && resultBean.size() > 0 && resultBean.size() == 15) {
                                        rows.addAll(resultBean);
                                        tradeListRecordAdapter.notifyDataSetChanged();
                                        page++;
                                        tradeListRecordAdapter.setNewData(rows);
                                    } else if (resultBean != null && resultBean.size() > 0 && resultBean.size() < 10) {
                                        rows.addAll(resultBean);
                                        tradeListRecordAdapter.notifyDataSetChanged();
                                        page++;
                                        tradeListRecordAdapter.setNewData(rows);
                                    } else {

                                    }
                                    if (smr != null) {
                                        smr.finishLoadMore();
                                    }
                                }
//                                else {
//                                    rows.addAll(0,resultBean);
//                                    tradeListRecordAdapter.setNewData(rows);
//                                }
                            }


                        }
                        isFirst = 1;
                    }
                }
            }
        }
    };
    @Override
    public void initData() {
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                page = 1;
                isFirst = 0;
                sendMeg("open",page);
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                sendMeg("open",page);
            }
        });
    }
    @Override
    public void recv(String skb) {
        //判断页面是否在显示
        if (!IsDestroy.isDestroy(HolderActivity.this)) {
            Log.e("F34EE55", "skb:" + skb);
            if (smr != null) {
                smr.finishRefresh();
                smr.finishLoadMore();
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("skb", skb);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }
    @OnClick({R.id.img_back})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
        }
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public String id() {
        return null;
    }



    @Override
    public void notify(NetEvent event) {

    }

    @Override
    protected void onStop() {
        super.onStop();
//        sendMeg("closegid", page);
    }
}
