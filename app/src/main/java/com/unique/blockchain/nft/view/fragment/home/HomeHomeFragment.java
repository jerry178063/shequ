package com.unique.blockchain.nft.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.CurrentCommisonBuyAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.BannerAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.ViewAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.home.ListMatch;
import com.unique.blockchain.nft.domain.home.RankingBean;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.domain.me.VersionUpdate;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.HomeNftTypeDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.HomeShareActivity;
import com.unique.blockchain.nft.view.activity.MyGoBuyActivity;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.mark.ArtworkActivity;
import com.unique.blockchain.nft.view.activity.mark.FlashBuyActivity;
import com.unique.blockchain.nft.view.activity.mark.MarkCollectActivity;
import com.unique.blockchain.nft.view.activity.mark.MarkQingSheActivity;
import com.unique.blockchain.nft.view.activity.mark.MarkTicketActivity;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.SouActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseFragment;
import com.unique.blockchain.nft.view.activity.me.DigitalStoreActivity;
import com.unique.blockchain.nft.view.activity.me.RegisterDigitalResultActivity;
import com.unique.blockchain.nft.view.activity.me.SouMeOldActivity;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeGetCompanyInfoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeGetPanyInfoPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeGetCompanyInfoCallBack;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkBannerPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkReMenzPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkBannerPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkReMenzPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkBannerCallBack;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkReMenzCallBack;
import com.unique.blockchain.nft.widget.KlineViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class HomeHomeFragment extends BaseFragment implements IMarkBannerCallBack,
        IMarkReMenzCallBack, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, IMeGetCompanyInfoCallBack,
        HomeNftTypeDialog.OnSafeClickView, HomeNftTypeDialog.ItemClickView {

    private View include1;
    private View liu_sou;
    private RecyclerView liu_RecyclerView;

    private BannerAdapter recAdapter;

    private List<BannerDatabase.DataBean> list = new ArrayList<>();
    KlineViewPager viewpager_home;
    public SmartRefreshLayout swipeRefresh;
    IMarkBannerPresenter iMarkBannerPresenter;
    private List<BannerDatabase.DataBean> data;//广告
    private List<ReDatabase.RowsBean> rows;//热门

    //---热门铸造
    private ViewAdapter viewAdapter;
    private RecyclerView butt_rec;
    private IMarkReMenzPresenter iMarkReMenzPresenter;

    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private TextView tv_no_zhuzaozhe;
    private LinearLayout lin_no_data;
    private RelativeLayout rela_remen_zhuzaozhe;
    private LinearLayout lin_typechoose;
    private TextView tv_name_choose;
    private ImageView img_zhuzao_img;
    IMeGetCompanyInfoPresenter iMeGetCompanyInfoPresenter;
    private String dataStr;
    private ImageView lin_go_buy, img_share;
    private boolean isShow;
    private HomeNftTypeDialog homeNftTypeDialog;
    private RecyclerView home_recyclew;
    private List<RankingBean.Datas> number = new ArrayList<>();
    private CurrentCommisonBuyAdapter myCommissionAdapterBuy = new CurrentCommisonBuyAdapter(R.layout.fragment_kline__commission_buy_item, new ArrayList<>());
    private ImageView img_shandui;
    private String productType;
    private TextView tv_no_liutong;
    private ImageView img_home_banner_dibu;
    private FrameLayout frame_zhuzao;

    public static HomeHomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeHomeFragment fragment = new HomeHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onEut(String onEut) {

    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_home_home;
    }


    @Override
    protected void initView(View view) {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        data = new ArrayList<>();
        rows = new ArrayList<>();
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        include1 = view.findViewById(R.id.include1);
        liu_sou = view.findViewById(R.id.liu_sou);
        liu_RecyclerView = view.findViewById(R.id.liu_RecyclerView);
        butt_rec = view.findViewById(R.id.butt_rec);
        viewpager_home = view.findViewById(R.id.viewpager_home);
        tv_no_zhuzaozhe = view.findViewById(R.id.tv_no_zhuzaozhe);//热门铸造者
        lin_no_data = view.findViewById(R.id.lin_no_data);//暂无数据
        rela_remen_zhuzaozhe = view.findViewById(R.id.rela_remen_zhuzaozhe);////热门铸造者
        lin_typechoose = view.findViewById(R.id.lin_typechoose);//类型选择
        tv_name_choose = view.findViewById(R.id.tv_name_choose);
        img_zhuzao_img = view.findViewById(R.id.img_zhuzao_img);
        lin_go_buy = view.findViewById(R.id.lin_go_buy);//我要卖
        img_share = view.findViewById(R.id.img_share);//我要分享
        home_recyclew = view.findViewById(R.id.home_recyclew);//NFT流通排名
        img_shandui = view.findViewById(R.id.img_shandui);//闪兑
        tv_no_liutong = view.findViewById(R.id.tv_no_liutong);
        img_home_banner_dibu = view.findViewById(R.id.img_home_banner_dibu);
        frame_zhuzao = view.findViewById(R.id.frame_zhuzao);
        initRecyclerView();
        initFragment();
        initRen();
        initListener();

        initDatas();

        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                //banner轮播广告
                iMarkBannerPresenter.getData(1, 10, "");
                tv_name_choose.setText("全部");
                productType = "";
                getRanking();
            }
        });
        swipeRefresh.setEnableLoadMore(false);

        getAddress();
        lin_typechoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    namePopWindow();
                }
            }
        });
        img_zhuzao_img.setOnClickListener(this);
        lin_go_buy.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_shandui.setOnClickListener(this);

        //NFT流通排名
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        home_recyclew.setLayoutManager(linearLayoutManager);
        home_recyclew.setAdapter(myCommissionAdapterBuy);
        myCommissionAdapterBuy.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), PaiActivity.class);
                intent.putExtra("nftId", number.get(i).getNftId() + "");
                intent.putExtra("liutong","liutong");
                startActivity(intent);
            }
        });

        checkUpdate();
        //获取NFT流通排名
        getRanking();
    }

    private void getAddress() {
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

    private String downUrl;

    /**
     * 检查版本更新
     *///点击事件，一会找到钱包管理调用
    private void checkUpdate() {
        OkGo.get(UrlConstant.baseUrl + "api/version/getLatestVersion")
//                .headers("sign", "sign")
//                .headers("token", "")
//                .upJson(checkVersionPutBean.toString())
                .params("versionType", "Android")
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject versionInfo, Call call, Response response) {
                        VersionUpdate versionUpdate = new Gson().fromJson(versionInfo, VersionUpdate.class);
                        Log.e("FF333","111111:");
                        if (versionUpdate != null && versionUpdate.getCode() == 200 && versionUpdate.getData() != null && versionUpdate.getData().getVersion() != null) {
                            downUrl = versionUpdate.getData().getDownloadLink();
                            Log.e("FF333","downUrl:" + downUrl);
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });


    }

    private void getRanking() {
        OkGo.get(UrlConstant.baseUrl + "api/nft/circulation/ranking")
                .params("productType", productType)
                .readTimeOut(10000)
                .execute(new JsonCallback<RankingBean>() {
                    @Override
                    public void onSuccess(RankingBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        if (jsonObject.getCode() == 200) {
                            Log.e("FF3332gg2", "1111:" + new Gson().toJson(jsonObject));
                            Log.e("FF3332gg2", "size:" + jsonObject.getData().size());
                            if (!TextUtils.isEmpty(number.toString())) {
                                number.clear();
                            }
                            if (!TextUtils.isEmpty(jsonObject.getData().toString()) && jsonObject.getData().size() > 0) {
                                number.addAll(jsonObject.getData());
                                myCommissionAdapterBuy.setData(getContext());
//                                myCommissionAdapterBuy.setNewData(number);
                                tv_no_liutong.setVisibility(View.GONE);
                            } else {
                                tv_no_liutong.setVisibility(View.VISIBLE);
                            }
                            myCommissionAdapterBuy.setNewData(number);

                        } else {
                            tv_no_liutong.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332gg", "4444");
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332gg", "onError");
                    }
                });
    }

    private void namePopWindow() {

        if (homeNftTypeDialog == null) {
            homeNftTypeDialog = new HomeNftTypeDialog(getContext());
            homeNftTypeDialog.setOnclick(this);
            homeNftTypeDialog.setItemClickView(this);
            homeNftTypeDialog.show();
        } else {
            homeNftTypeDialog.show();
        }

    }

    @Override
    public void setOnSafeClickView(String et_pass) {
        if (homeNftTypeDialog != null) {
            homeNftTypeDialog.dismiss();
        }
    }

    @Override
    public void setItemClickView(int tag) {
        if (tag == 0) {
            tv_name_choose.setText("全部");
            productType = "";
        } else if (tag == 1) {
            tv_name_choose.setText("票务");
            productType = "1";
        } else if (tag == 2) {
            tv_name_choose.setText("艺术品");
            productType = "3";
        } else if (tag == 3) {
            tv_name_choose.setText("轻奢品");
            productType = "4";
        } else if (tag == 4) {
            tv_name_choose.setText("收藏品");
            productType = "2";
        }
        getRanking();
    }

    private void initDatas() {
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


    }

    private void initListener() {
        img_zhuzao_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRen() {
        //每项只进入一次
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
        //banner轮播广告
        if (iMarkBannerPresenter != null) {
            iMarkBannerPresenter.getData(1, 10, "");
        }
        //-----热门铸造
        if (iMarkReMenzPresenter != null) {
            iMarkReMenzPresenter.getData(1, 10, "", "");
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
                case R.id.img_zhuzao_img:
                    Log.e("FFD3222", "home：" + uniqueAdress);
                    getAddress();
                    if (iMeGetCompanyInfoPresenter == null) {
                        iMeGetCompanyInfoPresenter = new IMeGetPanyInfoPresenterImpl();
                        iMeGetCompanyInfoPresenter.registerViewCallback(this);
                    }
                    iMeGetCompanyInfoPresenter.getData(uniqueAdress);
                    break;
                case R.id.lin_go_buy:
                    Intent intent1 = new Intent(getContext(), MyGoBuyActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.img_share://我要分享
                    if (!TextUtils.isEmpty(downUrl)) {
//                        new Share2.Builder(getActivity())
//                                .setContentType(ShareContentType.TEXT)
//                                .setTextContent(downUrl)
//                                .setTitle("")
//                                // .forcedUseSystemChooser(false)
//                                .build()
//                                .shareBySystem();
                        Intent intent2 = new Intent(getContext(), HomeShareActivity.class);
                        intent2.putExtra("url", downUrl + "");
                        getContext().startActivity(intent2);
                    } else {
//                        new Share2.Builder(getActivity())
//                                .setContentType(ShareContentType.TEXT)
//                                .setTextContent("http://www.baidu.com")
//                                .setTitle("")
//                                // .forcedUseSystemChooser(false)
//                                .build()
//                                .shareBySystem();
                        Intent intent2 = new Intent(getContext(), HomeShareActivity.class);
                        intent2.putExtra("url", "http://www.baidu.com");
                        getContext().startActivity(intent2);
                    }
                    break;
                case R.id.img_shandui://闪兑
                    Intent intent2 = new Intent(getContext(), FlashBuyActivity.class);
                    startActivity(intent2);
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
            if (bannerDatabase.getData() != null && bannerDatabase.getData().size() > 0) {
                data.addAll(bannerDatabase.getData());
                recAdapter.setBannerl(data);
                img_home_banner_dibu.setVisibility(View.VISIBLE);
            } else {
                img_home_banner_dibu.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void loadBannerFail() {

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
                frame_zhuzao.setVisibility(View.VISIBLE);
            } else {
                tv_no_zhuzaozhe.setVisibility(View.GONE);
                rela_remen_zhuzaozhe.setVisibility(View.GONE);
                butt_rec.setVisibility(View.GONE);
                frame_zhuzao.setVisibility(View.GONE);
            }

            rows.addAll(reDatabase.getRows());
            viewAdapter.setGetList(rows);
        }
    }

    @Override
    public void loadReMenzFail() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (!TextUtils.isEmpty(baseQuickAdapter.getData().toString())) {
            if (baseQuickAdapter.getData().size() > 0) {
                ListMatch.DataBean dataBean = (ListMatch.DataBean) baseQuickAdapter.getData().get(i);
                tv_name_choose.setText(dataBean.getToken() + "");
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void loadGetCompanyInfoPostData(CompanyInfoBean companyInfoBean) {
        if (companyInfoBean != null && companyInfoBean.getCode() == 200) {
            if (companyInfoBean.getData() == null) {
                Intent intentConmit = new Intent(getContext(), DigitalStoreActivity.class);
                intentConmit.putExtra("uniqueAdress", uniqueAdress);
                startActivity(intentConmit);
            } else {
                dataStr = companyInfoBean.toString();
                Intent intent = new Intent(getContext(), RegisterDigitalResultActivity.class);
                intent.putExtra("dataStr", dataStr + "");
                startActivity(intent);
            }
        }
    }

    @Override
    public void loadGetCompanyInfoPostFail() {

    }

}