package com.unique.blockchain.nft.view.fragment.mark;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.NoticeAdapter;
import com.unique.blockchain.nft.adapter.PersonalNoticeAdapter;
import com.unique.blockchain.nft.domain.Skbuff;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.AuctionMarkBean;
import com.unique.blockchain.nft.domain.mark.MarkDetailSocketNewBean;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;
import com.unique.blockchain.nft.domain.mark.PersonalMessBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.infrastructure.net.NetClient;
import com.unique.blockchain.nft.infrastructure.net.NetConstant;
import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.messagedb.DBHelper;
import com.unique.blockchain.nft.view.activity.mark.SystemNoticeContentActivity;
import com.unique.blockchain.nft.view.activity.mark.presenter.INoticePresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.INoticePresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.view.INoticeCallBack;
import com.unique.blockchain.nft.websocket.Skbuffs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人通知
 */
public class MyFragment extends BaseFragment implements INoticeCallBack, INetUpper {

    private List<PersonalMessBean> list;

    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private String TAG = "TradeFragment";

    RecyclerView recyclerView_personl;
    private PersonalNoticeAdapter noticeAdapter;
    private INoticePresenter iNoticePresenter;
    private int page = 0;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smr;
    @BindView(R.id.img_no_mess)
    ImageView img_no_mess;
    @BindView(R.id.tv_no_datas)
    TextView tv_no_datas;
    private int firstNotice = 0;
    DBHelper helperMess = new DBHelper(getContext(), "lqwvje.db");
    private SQLiteDatabase db;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData() {
        iNoticePresenter = new INoticePresenterImpl();
        iNoticePresenter.registerViewCallback(this);
        iNoticePresenter.getData(page, 10, uniqueAdress, "1");
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
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
        recyclerView_personl = (RecyclerView) findViewById(R.id.recyclerView_personl);
        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_personl.setLayoutManager(linearLayoutManager);
        noticeAdapter = new PersonalNoticeAdapter(R.layout.fragment_personl_notice, list);
        recyclerView_personl.setAdapter(noticeAdapter);

        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                showDialog();
                page = 0;
                firstNotice = 0;
                sendMeg(page, "open");
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                showDialog();
                firstNotice = 0;
                sendMeg(page, "open");
            }
        });
        noticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                list.get(i).setIsRead("1");
                noticeAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getContext(), SystemNoticeContentActivity.class);
                intent.putExtra("id", list.get(i).getId() + "");
                intent.putExtra("type", "1");
                intent.putExtra("uniqueAdress", uniqueAdress);
                startActivity(intent);

            }
        });
        img_no_mess.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.no_mess_img));
        tv_no_datas.setText("无信息");
        showDialog();
        try {
            sendMeg(page, "open");
        } catch (Exception e) {

        }
    }

    private void sendMeg(int page, String status) {
        Log.e("FF2334", "UNIQUE:" + new Gson().toJson(unique));

        NetClient.getInstance().register(this);
        Skbuff skbuff = new Skbuff();
        skbuff.setCmd(status);
        skbuff.setType("nft");
        skbuff.setFlags(NetConstant.NETHDR_FLAG_NONE);
        skbuff.setReserve(NetConstant.NETHDR_RESERVER);
        skbuff.setVersion(NetConstant.NETHDR_VERSION);
        skbuff.setMagic(NetConstant.NETHDR_MAGIC);
        Skbuff.Data data = new Skbuff.Data();
        Skbuff.Data.Group group = new Skbuff.Data.Group();
        List<Skbuff.Data.Group.ModuleList> list = new ArrayList<>();
        Skbuff.Data.Group.ModuleList moduleList = new Skbuff.Data.Group.ModuleList();
        moduleList.setModuleId("m_notice");
        moduleList.setPage(page);
        moduleList.setPageSize(10);
        list.add(moduleList);
        group.setModuleList(list);
        group.setGid("g_my_notice");
        group.setDid(unique.getDid());
        group.setNoticeType(1);
        group.setCoupleId("notice");
        group.setWalletAddr(uniqueAdress);
        data.setGroup(group);
        skbuff.setData(data);
        NetClient.getInstance().send(skbuff);
        Log.e("FFS3325e", "send:" + skbuff.toString());
    }

    @Override
    public void loadNoticeData(NoticeMessageBean noticeMessageBean) {
    }

    @Override
    public void loadNoticeFail() {
        dismissDialog();
    }

    @Override
    public String id() {
        return null;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            try {
                MarkDetailSocketNewBean markDetailSocketBean = new Gson().fromJson(msg.getData().getString("skb"), MarkDetailSocketNewBean.class);
                Log.e("FF3442355", "markDetailSocketBean:" + new Gson().toJson(markDetailSocketBean));
                if (markDetailSocketBean != null && markDetailSocketBean.getCode() == 200) {
                    if (markDetailSocketBean.getData() != null && markDetailSocketBean.getData().getData() != null) {
                        for (int i = 0; i < markDetailSocketBean.getData().getData().size(); i++) {
                            //竞标价格
                            if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_notice")) {
                                String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                                List<PersonalMessBean> resultBean = new Gson().fromJson(json, new TypeToken<List<PersonalMessBean>>() {
                                }.getType());
                                Log.e("FF2344ff3", "消息:" + new Gson().toJson(resultBean));
//                            Log.e("FF2344ff","持有者记录:" + json);
                                Log.e("FFS3325e", "page:" + page);
                                Log.e("FFS3325e", "firstNotice:" + firstNotice);

                                if (page == 0) {//第一页
                                    if (firstNotice == 0) {
                                        if (list != null) {
                                            list.clear();
                                        }

                                        if (resultBean != null && resultBean.size() > 0) {
                                            firstNotice = 1;
                                            list.addAll(resultBean);
                                            page++;
                                            noticeAdapter.setNewData(list);
                                        } else {

                                        }
                                        if (list != null && list.size() > 0) {
                                            tv_no_data.setVisibility(View.GONE);
                                        } else {
                                            tv_no_data.setVisibility(View.VISIBLE);
                                        }
                                        if (smr != null) {
                                            smr.finishRefresh();
                                        }
                                        Log.e("FF2344ff34", "size:" + list.size());
                                    } else {
                                        if (!TextUtils.isEmpty(resultBean.toString()) && resultBean.size() > 0) {
                                            list.addAll(0, resultBean);
                                            noticeAdapter.setNewData(list);
                                        }
                                    }
                                } else {
                                    tv_no_data.setVisibility(View.GONE);
                                    if (firstNotice == 0) {
                                        if (resultBean != null && resultBean.size() > 0 && resultBean.size() == 15) {
                                            list.addAll(resultBean);
                                            noticeAdapter.notifyDataSetChanged();
                                            page++;
                                            noticeAdapter.setNewData(list);
                                        } else if (resultBean != null && resultBean.size() > 0 && resultBean.size() < 10) {
                                            list.addAll(resultBean);
                                            noticeAdapter.notifyDataSetChanged();
                                            page++;
                                            noticeAdapter.setNewData(list);
                                            Log.e("FFS3325e", "page:" + page);
                                        } else {

                                        }
                                        firstNotice = 1;
                                        if (smr != null) {
                                            smr.finishLoadMore();
                                        }
                                    } else {
                                        if (!TextUtils.isEmpty(resultBean.toString()) && resultBean.size() > 0) {
                                            list.addAll(0, resultBean);
                                            noticeAdapter.setNewData(list);
                                        }
                                    }
                                }

                            }

                        }
                    }

                }
            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        Log.e("FF2334", "name:" + SPUtils.getString(getContext(), Tools.name, ""));
        Log.e("FF2334", "boolean:" + UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, "")));
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
        try {
            sendMeg(page, "closegid");
        } catch (Exception e) {

        }
    }

    @Override
    public void recv(String skb) {
        Log.e("FFS3325e", "收到消息:" + skb);
        if (smr != null) {
            smr.finishRefresh();
        }
        if (smr != null) {
            smr.finishLoadMore();
        }
        dismissDialog();
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("skb", skb);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    public void notify(NetEvent event) {

    }
}