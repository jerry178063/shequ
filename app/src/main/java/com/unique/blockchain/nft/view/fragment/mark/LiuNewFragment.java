package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MarkCollectAdapter;
import com.unique.blockchain.nft.adapter.MarkQingSheAdapter;
import com.unique.blockchain.nft.adapter.MarkTicketAdapter;
import com.unique.blockchain.nft.adapter.MarkYishuAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.BannerAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.ViewAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.mark.ArtworkActivity;
import com.unique.blockchain.nft.view.activity.mark.MarkCollectActivity;
import com.unique.blockchain.nft.view.activity.mark.MarkQingSheActivity;
import com.unique.blockchain.nft.view.activity.mark.MarkTicketActivity;
import com.unique.blockchain.nft.view.activity.mark.SouActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkBannerPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkCollectPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkQingShePresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkReMenzPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkTicketPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkYishuPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkBannerPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkCollectPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkQingShePresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkReMenzPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkTicketPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkYishuPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkBannerCallBack;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkCollectCallBack;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkQingSheCallBack;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkReMenzCallBack;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkTicketCallBack;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkYiShuCallBack;
import com.unique.blockchain.nft.widget.KlineViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class LiuNewFragment extends BaseFragment implements IMarkBannerCallBack, IMarkCollectCallBack,
        IMarkTicketCallBack, IMarkQingSheCallBack, IMarkYiShuCallBack, IMarkReMenzCallBack, View.OnClickListener {

    private View include1;
    private View liu_sou;
    private RecyclerView liu_RecyclerView;

    private BannerAdapter recAdapter;

    private List<BannerDatabase.DataBean> list = new ArrayList<>();
    KlineViewPager viewpager_home;
    TabLayout tb_home;
    Fragment[] fragments_home;
    public SmartRefreshLayout swipeRefresh;
    String[] titles_home = {};
    IMarkBannerPresenter iMarkBannerPresenter;
    public int page_mark = 1;
    private List<BannerDatabase.DataBean> data;//广告
    private List<ReDatabase.RowsBean> rows;//热门

    //----艺术品
    private RecyclerView recy_yishupin;
    private MarkYishuAdapter markYishuAdapter;
    private IMarkYishuPresenter iMarkYishuPresenter;
    private List<QuanDatabase.RowsBean> rowsYishu;
    //-----收藏品
    private RecyclerView recy_shoucangpin;
    private MarkCollectAdapter markCollectAdapter;
    private IMarkCollectPresenter iMarkCollectPresenter;
    private List<QuanDatabase.RowsBean> rowsShoucang;
    //-----票务
    private RecyclerView recy_piaowu;
    private MarkTicketAdapter markTicketAdapter;
    private IMarkTicketPresenter iMarkTicketPresenter;
    private List<QuanDatabase.RowsBean> rowsPiaowu;
    //-----轻奢品
    private RecyclerView recy_qingshepin;
    private MarkQingSheAdapter markQingSheAdapter;
    private IMarkQingShePresenter iMarkQingShePresenter;
    private List<QuanDatabase.RowsBean> rowsQingshe;
    //---热门铸造
    private ViewAdapter viewAdapter;
    private RecyclerView butt_rec;
    private IMarkReMenzPresenter iMarkReMenzPresenter;

    RelativeLayout rela_yishupin;
    RelativeLayout rela_collect;
    RelativeLayout rela_ticket;
    RelativeLayout rela_qingshepin;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private TextView tv_no_zhuzaozhe;
    private LinearLayout lin_no_data;
    private RelativeLayout rela_remen_zhuzaozhe;

    public static LiuNewFragment newInstance() {
        Bundle args = new Bundle();
        LiuNewFragment fragment = new LiuNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_liu_new;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        data = new ArrayList<>();
        rows = new ArrayList<>();
        swipeRefresh = (SmartRefreshLayout) findViewById(R.id.swipeRefresh);
        include1 = findViewById(R.id.include1);
        liu_sou = findViewById(R.id.liu_sou);
        liu_RecyclerView = (RecyclerView) findViewById(R.id.liu_RecyclerView);
        butt_rec = (RecyclerView) findViewById(R.id.butt_rec);
        tb_home = (TabLayout) findViewById(R.id.tb_home);
        viewpager_home = (KlineViewPager) findViewById(R.id.viewpager_home);
        recy_yishupin = (RecyclerView) findViewById(R.id.recy_yishupin);
        rela_yishupin = (RelativeLayout) findViewById(R.id.rela_yishupin);//艺术品
        rela_collect = (RelativeLayout) findViewById(R.id.rela_collect);//收藏品
        rela_ticket = (RelativeLayout) findViewById(R.id.rela_ticket);//票务
        rela_qingshepin = (RelativeLayout) findViewById(R.id.rela_qingshepin);//轻奢品
        tv_no_zhuzaozhe = (TextView) findViewById(R.id.tv_no_zhuzaozhe);//热门铸造者
        lin_no_data = (LinearLayout) findViewById(R.id.lin_no_data);//暂无数据
        rela_remen_zhuzaozhe = (RelativeLayout) findViewById(R.id.rela_remen_zhuzaozhe);////热门铸造者
        initRecyclerView();
        initFragment();
        initRen();
        initListener();
        showDialog();
        initDatas();

        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {

                showDialog();
                //----艺术品
                iMarkYishuPresenter.getData(1, 2, "", "3");
                //-----热门铸造
                iMarkReMenzPresenter.getData(1, 10, "", "");
                //banner轮播广告
                iMarkBannerPresenter.getData(1, 10, "");


                markYishuAdapter.cancelAllTimers();
                markCollectAdapter.cancelAllTimers();
                markTicketAdapter.cancelAllTimers();
                markQingSheAdapter.cancelAllTimers();

            }
        });
        swipeRefresh.setEnableLoadMore(false);

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


    private void initDatas() {
        //----艺术品
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recy_yishupin.setLayoutManager(gridLayoutManager);
        rowsYishu = new ArrayList<>();
        markYishuAdapter = new MarkYishuAdapter(R.layout.search, rowsYishu);
        recy_yishupin.setAdapter(markYishuAdapter);

        iMarkYishuPresenter = new IMarkYishuPresenterImpl();
        iMarkYishuPresenter.registerViewCallback(this);
        iMarkYishuPresenter.getData(1, 2, "", "3");
        //---收藏品
        recy_shoucangpin = (RecyclerView) findViewById(R.id.recy_shoucangpin);
        rowsShoucang = new ArrayList<>();
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        recy_shoucangpin.setLayoutManager(gridLayoutManager2);
        markCollectAdapter = new MarkCollectAdapter(R.layout.search, rowsShoucang);
        recy_shoucangpin.setAdapter(markCollectAdapter);
        iMarkCollectPresenter = new IMarkCollectPresenterImpl();
        iMarkCollectPresenter.registerViewCallback(this);
//        iMarkCollectPresenter.getData(1, 2, "", "2");
        //---票务
        recy_piaowu = (RecyclerView) findViewById(R.id.recy_piaowu);
        rowsPiaowu = new ArrayList<>();
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 2);
        recy_piaowu.setLayoutManager(gridLayoutManager3);
        markTicketAdapter = new MarkTicketAdapter(R.layout.search, rowsPiaowu);
        recy_piaowu.setAdapter(markTicketAdapter);
        iMarkTicketPresenter = new IMarkTicketPresenterImpl();
        iMarkTicketPresenter.registerViewCallback(this);
//        iMarkTicketPresenter.getData(1, 2, "", "1");
        //---轻奢品
        recy_qingshepin = (RecyclerView) findViewById(R.id.recy_qingshepin);
        rowsQingshe = new ArrayList<>();
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getActivity(), 2);
        recy_qingshepin.setLayoutManager(gridLayoutManager4);
        markQingSheAdapter = new MarkQingSheAdapter(R.layout.search, rowsQingshe);
        recy_qingshepin.setAdapter(markQingSheAdapter);
        iMarkQingShePresenter = new IMarkQingShePresenterImpl();
        iMarkQingShePresenter.registerViewCallback(this);
//        iMarkQingShePresenter.getData(1, 2, "", "4");
        //banner轮播广告
        iMarkBannerPresenter = new IMarkBannerPresenterImpl();
        iMarkBannerPresenter.registerViewCallback(this);
        iMarkBannerPresenter.getData(1, 10, "");
        //-----热门铸造
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.HORIZONTAL);//垂直滑动
        butt_rec.setLayoutManager(linearManager);
        viewAdapter = new ViewAdapter(getActivity());
        butt_rec.setAdapter(viewAdapter);
        iMarkReMenzPresenter = new IMarkReMenzPresenterImpl();
        iMarkReMenzPresenter.registerViewCallback(this);
        iMarkReMenzPresenter.getData(1, 10, "", "");

        markQingSheAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    if (rowsQingshe.get(position).getSellMode() == 1) {//购买
                        Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                        intent.putExtra("title", rowsQingshe.get(position).getNftName());
                        intent.putExtra("nftId", rowsQingshe.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsQingshe.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsQingshe.get(position).getProductType() + "");
                        startActivity(intent);
                    } else if (rowsQingshe.get(position).getSellMode() == 2) {//出价
                        Intent intent = new Intent(getContext(), OutPriceActivity.class);
                        intent.putExtra("title", rowsQingshe.get(position).getNftName());
                        intent.putExtra("nftId", rowsQingshe.get(position).getNftId());
                        intent.putExtra("productType", rowsQingshe.get(position).getProductType() + "");
                        intent.putExtra("uniqueAdress", rowsQingshe.get(position).getWalletAddr());
                        startActivity(intent);
                    }
                }
            }
        });
        markYishuAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    Log.e("FF33325", "getSellMode:" + rowsYishu.get(position).getSellMode());
                    if (rowsYishu.get(position).getSellMode() == 1) {//购买
                        Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                        intent.putExtra("title", rowsYishu.get(position).getNftName());
                        intent.putExtra("nftId", rowsYishu.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsYishu.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsYishu.get(position).getProductType() + "");
                        startActivity(intent);
                    } else if (rowsYishu.get(position).getSellMode() == 2) {//出价
                        Intent intent = new Intent(getContext(), OutPriceActivity.class);
                        intent.putExtra("title", rowsYishu.get(position).getNftName());
                        intent.putExtra("nftId", rowsYishu.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsYishu.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsYishu.get(position).getProductType() + "");
                        startActivity(intent);
                    }
                }
            }
        });
        markCollectAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    if (rowsShoucang.get(position).getSellMode() == 1) {//购买
                        Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                        intent.putExtra("title", rowsShoucang.get(position).getNftName());
                        intent.putExtra("nftId", rowsShoucang.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsShoucang.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsShoucang.get(position).getProductType() + "");
                        startActivity(intent);
                        Log.e("FF3332", "getWalletAddr:" + rowsShoucang.get(position).getWalletAddr());
                    } else if (rowsShoucang.get(position).getSellMode() == 2) {//出价
                        Intent intent = new Intent(getContext(), OutPriceActivity.class);
                        intent.putExtra("title", rowsShoucang.get(position).getNftName());
                        intent.putExtra("nftId", rowsShoucang.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsShoucang.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsShoucang.get(position).getProductType() + "");
                        startActivity(intent);
                    }
                }
            }
        });
        markTicketAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    if (rowsPiaowu.get(position).getSellMode() == 1) {//购买
                        Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                        intent.putExtra("title", rowsPiaowu.get(position).getNftName());
                        intent.putExtra("nftId", rowsPiaowu.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsPiaowu.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsPiaowu.get(position).getProductType() + "");
                        startActivity(intent);
                    } else if (rowsPiaowu.get(position).getSellMode() == 2) {//出价
                        Intent intent = new Intent(getContext(), OutPriceActivity.class);
                        intent.putExtra("title", rowsPiaowu.get(position).getNftName());
                        intent.putExtra("nftId", rowsPiaowu.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsPiaowu.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsPiaowu.get(position).getProductType() + "");
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void initListener() {
        rela_yishupin.setOnClickListener(this);
        rela_collect.setOnClickListener(this);
        rela_ticket.setOnClickListener(this);//票务
        rela_qingshepin.setOnClickListener(this);//轻奢品
    }

    private void initRen() {
        fragments_home = new Fragment[]{new All_Home_Fragment(), new Art_Home_Fragment(), new Collect_Home_Fragment(), new Ticket_Home_Fragment(), new Grade_Home_Fragment()};
//        fragments_home = new Fragment[]{new All_Fragment(), new Art_Fragment(), new Collect_Fragment(), new Ticket_Fragment(), new Grade_Fragment()};
        titles_home = new String[]{"全部", "艺术品", "收藏品", "票务", "轻奢品"};
        viewpager_home.setOffscreenPageLimit(fragments_home.length);
        tb_home.setupWithViewPager(viewpager_home);
        //每项只进入一次
        viewpager_home.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_home[position];
            }

            @Override
            public int getCount() {
                return fragments_home.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles_home[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
    }

    @Override
    protected void initData() {
    }




    private void initRecyclerView() {
        //banner
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        liu_RecyclerView.setLayoutManager(linearLayoutManager);
        recAdapter = new BannerAdapter(list, getActivity());
        liu_RecyclerView.setAdapter(recAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        if(iMarkYishuPresenter != null) {
            iMarkYishuPresenter.getData(1, 2, "", "3");
        }
        //banner轮播广告
        if(iMarkBannerPresenter != null) {
            iMarkBannerPresenter.getData(1, 10, "");
        }
        //-----热门铸造
        if(iMarkReMenzPresenter != null) {
            iMarkReMenzPresenter.getData(1, 10, "", "");
        }
        if(markYishuAdapter != null) {
            markYishuAdapter.cancelAllTimers();
        }
        if(markCollectAdapter != null) {
            markCollectAdapter.cancelAllTimers();
        }
        if(markTicketAdapter != null) {
            markTicketAdapter.cancelAllTimers();
        }
        if(markQingSheAdapter != null) {
            markQingSheAdapter.cancelAllTimers();
        }
    }

    @Override
    public void onClick(View view) {
        if (MoreClick.MoreClicks()) {
            switch (view.getId()) {
                case R.id.rela_yishupin://艺术品更多
                    Intent intent = new Intent(getContext(), ArtworkActivity.class);
                    startActivity(intent);
                    break;
                case R.id.rela_collect://收藏品更多
                    Intent intentCollect = new Intent(getContext(), MarkCollectActivity.class);
                    startActivity(intentCollect);
                    break;
                case R.id.rela_ticket://票务更多
                    Intent intentTicket = new Intent(getContext(), MarkTicketActivity.class);
                    startActivity(intentTicket);
                    break;
                case R.id.rela_qingshepin://轻奢品更多
                    Intent intentQingshe = new Intent(getContext(), MarkQingSheActivity.class);
                    startActivity(intentQingshe);
                    break;
            }
        }
    }

    private void initFragment() {
        liu_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getActivity(), SouActivity.class);
                    startActivity(intent);
                }
            }
        });
    }//底部5个页面显示隐藏

    //banner轮播广告
    @Override
    public void loadBannerData(BannerDatabase bannerDatabase) {
        swipeRefresh.finishRefresh();
        if (bannerDatabase != null && bannerDatabase.getCode() == 200) {
            if (data != null) {
                data.clear();
            }
            data.addAll(bannerDatabase.getData());
            recAdapter.setBannerl(data);
        }
    }

    @Override
    public void loadBannerFail() {

    }

    //收藏品
    @Override
    public void loadCollectData(QuanDatabase quanDatabase) {
        dismissDialog();
        if (quanDatabase != null && quanDatabase.getCode() == 200) {
            if (rowsShoucang != null) {
                rowsShoucang.clear();
            }
            Log.e("FF433", "quanDatabase:" + new Gson().toJson(quanDatabase));
            if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                rowsShoucang.addAll(quanDatabase.getRows());
                markCollectAdapter.notifyDataSetChanged();
            }
            markCollectAdapter.setNewData(rowsShoucang);
            //---票务
            iMarkTicketPresenter.getData(1, 2, "", "1");
        }

    }

    @Override
    public void loadCollectFail() {

    }

    //票务
    @Override
    public void loadTicketData(QuanDatabase quanDatabase) {
        dismissDialog();
        if (quanDatabase != null && quanDatabase.getCode() == 200) {
            Log.e("FF433", "quanDatabase:" + new Gson().toJson(quanDatabase));
            if (rowsPiaowu != null) {
                rowsPiaowu.clear();
            }
            if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {

                rowsPiaowu.addAll(quanDatabase.getRows());
                markTicketAdapter.notifyDataSetChanged();
            }
            markTicketAdapter.setNewData(rowsPiaowu);

            //---轻奢品
            iMarkQingShePresenter.getData(1, 2, "", "4");
        }
    }

    @Override
    public void loadTicketFail() {
        dismissDialog();
    }

    //轻奢品
    @Override
    public void loadQingSheData(QuanDatabase quanDatabase) {
        dismissDialog();
        if (quanDatabase != null && quanDatabase.getCode() == 200) {
            if (rowsQingshe != null) {
                rowsQingshe.clear();
            }
            Log.e("FF433", "quanDatabase:" + new Gson().toJson(quanDatabase));
            if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {

                rowsQingshe.addAll(quanDatabase.getRows());
                markQingSheAdapter.notifyDataSetChanged();
            }
            markQingSheAdapter.setNewData(rowsQingshe);
            Log.e("FF34455",rowsYishu.size() + "--" + rowsShoucang.size() + "--" + rowsPiaowu.size() + "--" + rowsQingshe.size());

            if(rowsYishu.size() <= 0 && rowsShoucang.size() <= 0 && rowsPiaowu.size() <= 0
                    && rowsQingshe.size() <= 0){
                Log.e("FF34455","没有数据");
                lin_no_data.setVisibility(View.VISIBLE);
                rela_collect.setVisibility(View.GONE);
                rela_ticket.setVisibility(View.GONE);
                rela_qingshepin.setVisibility(View.GONE);
                rela_yishupin.setVisibility(View.GONE);
            }else {
                Log.e("FF34455","有数据");
                lin_no_data.setVisibility(View.GONE);
                if(rowsShoucang.size() > 0) {
                    rela_collect.setVisibility(View.VISIBLE);
                }else {
                    rela_collect.setVisibility(View.GONE);
                }
                if(rowsPiaowu.size() > 0) {
                    rela_ticket.setVisibility(View.VISIBLE);
                }else {
                    rela_ticket.setVisibility(View.GONE);
                }
                if(rowsQingshe.size() > 0) {
                    rela_qingshepin.setVisibility(View.VISIBLE);
                }else {
                    rela_qingshepin.setVisibility(View.GONE);
                }
                if(rowsYishu.size() > 0) {
                    rela_yishupin.setVisibility(View.VISIBLE);
                }else {
                    rela_yishupin.setVisibility(View.GONE);
                }
            }

        }
    }

    @Override
    public void loadQingSheFail() {
        dismissDialog();
    }

    //艺术品
    @Override
    public void loadYishuData(QuanDatabase quanDatabase) {
        dismissDialog();
        if (quanDatabase != null && quanDatabase.getCode() == 200) {
            if (rowsYishu != null) {
                rowsYishu.clear();
            }
            Log.e("FF433", "quanDatabase:" + new Gson().toJson(quanDatabase));
            if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {

                rowsYishu.addAll(quanDatabase.getRows());
                markYishuAdapter.notifyDataSetChanged();
            }
            markYishuAdapter.setNewData(rowsYishu);

            //---收藏品
            iMarkCollectPresenter.getData(1, 2, "", "2");
        }
    }

    @Override
    public void loadYishuFail() {
        dismissDialog();
    }

    //浏览-热门铸造
    @Override
    public void loadReMenzData(ReDatabase reDatabase) {
        if (reDatabase != null && reDatabase.getCode() == 200) {
            if (rows != null) {
                rows.clear();
            }
            if (!TextUtils.isEmpty(reDatabase.getRows().toString()) && reDatabase.getRows().size() > 0) {
                tv_no_zhuzaozhe.setVisibility(View.GONE);

                rela_remen_zhuzaozhe.setVisibility(View.VISIBLE);
                butt_rec.setVisibility(View.VISIBLE);
            } else {
                tv_no_zhuzaozhe.setVisibility(View.GONE);
                rela_remen_zhuzaozhe.setVisibility(View.GONE);
                butt_rec.setVisibility(View.GONE);
            }

            rows.addAll(reDatabase.getRows());
            viewAdapter.setGetList(rows);
        }
    }

    @Override
    public void loadReMenzFail() {
        dismissDialog();
    }

}