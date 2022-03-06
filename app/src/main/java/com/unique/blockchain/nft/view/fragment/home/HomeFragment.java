package com.unique.blockchain.nft.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.donkingliang.imageselector.entry.Image;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.Skbuff;
import com.unique.blockchain.nft.domain.User;
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
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.NoticeActivity;
import com.unique.blockchain.nft.view.activity.mark.view.INoticeCallBack;
import com.unique.blockchain.nft.view.fragment.mark.LiuNewFragment;
import com.unique.blockchain.nft.view.fragment.mark.ReMenNewFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements INoticeCallBack , INetUpper {


    Fragment[] fragments_record;
    String[] titles_record = {};
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tb_kline_record)
    TabLayout tb_kline_record;
    @BindView(R.id.img_notice)
    ImageView img_notice;
    @BindView(R.id.img_notice2)
    ImageView img_notice2;
    @BindView(R.id.img_hongdian)
    ImageView img_hongdian;
    private boolean isXianshi = true;
    private int page = 0;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    List<String> isRead = new ArrayList<>();
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
                                if(isRead != null){
                                    isRead.clear();
                                }

                                if(resultBean != null && resultBean.size() > 0){
                                    for (int j = 0 ; j < resultBean.size(); j++){
                                        if(resultBean.get(j).getIsRead().equals("0") || resultBean.get(j).getIsRead().equals("0.0")){
                                            isRead.add(resultBean.get(j).getIsRead());
                                        }
                                    }
                                }
                                if(isRead != null && isRead.size() > 0){
                                    img_hongdian.setVisibility(View.VISIBLE);
                                }else {
                                    img_hongdian.setVisibility(View.GONE);
                                }

                            } else {
                            }

                        }
                    }
                }
            } catch (Exception e) {

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);

        fragments_record = new Fragment[]{new HomeHomeFragment()};
        titles_record = new String[]{"首页"};
        viewpager.setOffscreenPageLimit(fragments_record.length);
        tb_kline_record.setupWithViewPager(viewpager);
        //每项只进入一次
        viewpager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_record[position];
            }

            @Override
            public int getCount() {
                return fragments_record.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles_record[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });

//        img_notice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (MoreClick.MoreClicks()) {
//                    Intent intent = new Intent(getActivity(), NoticeActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        img_notice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getContext(), NoticeActivity.class);
                    startActivity(intent);
                    isXianshi = false;
                }
            }
        });

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
    private void sendMeg(int page, String status) {

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
    public void onResume() {
        super.onResume();
        try {
            sendMeg(page, "open");
        }catch (Exception e){

        }
        if(!isXianshi){
            img_hongdian.setVisibility(View.GONE);
        }else {
            img_hongdian.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            sendMeg(page, "closegid");
        } catch (Exception e) {

        }
    }

    @Override
    public void loadNoticeData(NoticeMessageBean noticeMessageBean) {

    }

    @Override
    public void loadNoticeFail() {

    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public void recv(String skb) {
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
