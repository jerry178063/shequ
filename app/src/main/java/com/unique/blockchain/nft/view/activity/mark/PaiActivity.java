package com.unique.blockchain.nft.view.activity.mark;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ExpressListAdapter;
import com.unique.blockchain.nft.adapter.MarkListRecordAdapter;
import com.unique.blockchain.nft.adapter.TradeListJingBiaoAdapter;
import com.unique.blockchain.nft.adapter.TradeListPowerAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.BannerDetailAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.Skbuff;
import com.unique.blockchain.nft.domain.SkbuffTwo;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.AuctionMarkBean;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.mark.HolderMarkBean;
import com.unique.blockchain.nft.domain.mark.JingBiaoBean;
import com.unique.blockchain.nft.domain.mark.MarkConfirmationBean;
import com.unique.blockchain.nft.domain.mark.MarkDetailSocketNewBean;
import com.unique.blockchain.nft.domain.mark.MarkMaxPriceBean;
import com.unique.blockchain.nft.domain.mark.MarkSeeCountBean;
import com.unique.blockchain.nft.domain.me.ExpressBean;
import com.unique.blockchain.nft.domain.trade.GoTradeCationBean;
import com.unique.blockchain.nft.domain.trade.GoTradeDetailBean;
import com.unique.blockchain.nft.domain.trade.GoTradeOwnerBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.infrastructure.net.NetClient;
import com.unique.blockchain.nft.infrastructure.net.NetConstant;
import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.IsDestroy;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.Share2;
import com.unique.blockchain.nft.infrastructure.utils.ShareContentType;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.mark.presenter.IJingBiaoPresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkCollectionPresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkUnCollectionPresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IJingBiaoPresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IMarkCollectionPresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IMarkUnCollectionPresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.mark.view.IJingBiaoCallBack;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkCollectionCallBack;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkUnCollectionCallBack;
import com.unique.blockchain.nft.view.activity.me.NftAdminHuoActivity;
import com.unique.blockchain.nft.view.activity.me.NftCancelBackActivity;
import com.unique.blockchain.nft.view.activity.me.NftTiHuoActivity;
import com.unique.blockchain.nft.view.activity.me.NftTransferActivity;
import com.unique.blockchain.nft.view.activity.me.SettingActivity;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeExpressPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeExpressPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAdminShouHuoExpressCallBack;
import com.unique.blockchain.nft.view.activity.trade.TradeListDetailActivity;
import com.unique.blockchain.nft.view.fragment.trade.presenter.IOwnerPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeCationPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeDetailPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeCationPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeDetailPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeOwnerPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeCationCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeListDetailCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeOwnerCallbask;
import com.unique.blockchain.nft.widget.TimeLineView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PaiActivity extends BaseActivity implements INetUpper, ITradeListDetailCallbask, ITradeOwnerCallbask, IJingBiaoCallBack, ITradeCationCallbask, IMarkCollectionCallBack
        , IMarkUnCollectionCallBack, IMeAdminShouHuoExpressCallBack {

    public User unique;
    public UserDao userDao;
    public String uniqueAdress, nftId;
    private TextView text_hu;
    @BindView(R.id.img_all_bg)
    ImageView img_all_bg;
    private ITradeDetailPresenter iTradeDetailPresenter;
    @BindView(R.id.tv_type_title)
    TextView tv_type_title;
    @BindView(R.id.tv_paimai_or_outprice)
    TextView tv_paimai_or_outprice;
    @BindView(R.id.tv_detail_name)
    TextView tv_detail_name;
    @BindView(R.id.shen_scan)
    TextView shen_scan;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.tv_max_price)
    TextView tv_max_price;
    @BindView(R.id.tv_min_jingpai)
    TextView tv_min_jingpai;
    @BindView(R.id.tv_seat_rows)
    TextView tv_seat_rows;
    @BindView(R.id.tv_freezeState)
    TextView tv_freezeState;
    @BindView(R.id.tv_banshui)
    TextView tv_banshui;
    @BindView(R.id.tv_zzshui)
    TextView tv_zzshui;
    @BindView(R.id.tv_jianjie)
    TextView tv_jianjie;
    @BindView(R.id.tv_zzz_adress)
    TextView tv_zzz_adress;
    @BindView(R.id.tv_owner_adress)
    TextView tv_owner_adress;
    @BindView(R.id.recyclewTwo)
    RecyclerView recyclewTwo;//产品资质
    @BindView(R.id.recyclewRecord)
    RecyclerView recyclewRecord;//持有者记录
    @BindView(R.id.tv_first_name)
    TextView tv_first_name;
    @BindView(R.id.recyclewJingBiao)//竞标者
    RecyclerView recyclewJingBiao;
    @BindView(R.id.img_pai_back)
    ImageView img_pai_back;
    List<String> proveUrls = new ArrayList<>();
    @BindView(R.id.tv_detail_day)
    TextView tv_detail_day;//天
    @BindView(R.id.tv_detail_hour)
    TextView tv_detail_hour;//时
    @BindView(R.id.tv_detail_minute)
    TextView tv_detail_minute;//分
    @BindView(R.id.tv_detail_sends)
    TextView tv_detail_sends;//秒
    @BindView(R.id.lin_on_xiajia)
    LinearLayout lin_on_xiajia;
    @BindView(R.id.liu_RecyclerView)
    RecyclerView liu_RecyclerView;
    @BindView(R.id.tv_pledge_deposit)
    TextView tv_pledge_deposit;
    @BindView(R.id.tv_colection)
    TextView tv_colection;
    @BindView(R.id.img_collection)
    ImageView img_collection;
    @BindView(R.id.img_collection2)
    ImageView img_collection2;
    @BindView(R.id.lin_min_jingpai)
    LinearLayout lin_min_jingpai;
    @BindView(R.id.tv_min_jingpai_name)
    TextView tv_min_jingpai_name;
    private CountDownTimer countDownTimer, countDownTimer2, countDownTimer3;
    private String surplus;
    private TradeListPowerAdapter tradeListPowerAdapter = new TradeListPowerAdapter(this, R.layout.fragment_trade_power_item, proveUrls);
    private IOwnerPresenter iOwnerPresenter;

    private List<HolderMarkBean> rows = new ArrayList<>();
    private MarkListRecordAdapter tradeListRecordAdapter = new MarkListRecordAdapter(R.layout.fragment_trade_record_item, rows);
    private List<ExpressBean.Data.ExpressInfoList> rowsExpress = new ArrayList<>();
    private ExpressListAdapter expressAdapter = new ExpressListAdapter(R.layout.activity_express, rowsExpress);

    private IJingBiaoPresenter iJingBiaoPresenter;
    private List<AuctionMarkBean> rowsJing = new ArrayList<>();
    private TradeListJingBiaoAdapter tradeListJingBiaoAdapter = new TradeListJingBiaoAdapter(R.layout.fragment_mark_jingbiao_item, rowsJing);
    private BannerDetailAdapter recAdapter;
    private List<BannerDatabase.DataBean> list = new ArrayList<>();
    private List<BannerDatabase.DataBean> data = new ArrayList<>();//广告
    List<String> bannImg = new ArrayList<>();//顶部滚动图片
    private ITradeCationPresenter iTradeCationPresenter;
    private String companyId;
    @BindView(R.id.tv_walletAdd)
    TextView tv_walletAdd;
    @BindView(R.id.tv_success)
    TextView tv_success;
    @BindView(R.id.pai_mai_img)
    ImageView pai_mai_img;
    @BindView(R.id.lin_outprice_goumai)
    LinearLayout lin_outprice_goumai;
    boolean isGoumai;
    @BindView(R.id.tv_name_choose)
    TextView tv_name_choose;
    @BindView(R.id.lin_detail_time_over)
    TextView lin_detail_time_over;//交易已结束
    @BindView(R.id.lin_detail_time)
    LinearLayout lin_detail_time;
    @BindView(R.id.tv_collection_liu)
    TextView tv_collection_liu;
    IMarkCollectionPresenter iMarkCollectionPresenter;
    IMarkUnCollectionPresenter iMarkUnCollectionPresenter;
    String shareUrl, shareTitle;//分享
    private String nftState;
    @BindView(R.id.lin_max_price_time)
    LinearLayout lin_max_price_time;
    @BindView(R.id.lin_max_price_time2)
    LinearLayout lin_max_price_time2;
    @BindView(R.id.yu)
    LinearLayout yu;
    @BindView(R.id.my_nft_daijiaoyi)
    LinearLayout my_nft_daijiaoyi;
    @BindView(R.id.lin_chexiao)
    LinearLayout lin_chexiao;//撤销交易
    @BindView(R.id.lin_max_jingjiazhe)
    LinearLayout lin_max_jingjiazhe;//最高竞价者
    @BindView(R.id.lin_wuliuxinxi)
    LinearLayout lin_wuliuxinxi;
    @BindView(R.id.timeLineView)
    TimeLineView timeLineView;
    @BindView(R.id.sto_recyclew)
    RecyclerView sto_recyclew;
    @BindView(R.id.tv_admin_shouhuo)
    TextView tv_admin_shouhuo;
    @BindView(R.id.tv_go_trade)
    TextView tv_go_trade;//去交易
    BannerDatabase.DataBean dataBean;
    GoTradeDetailBean mGoTradeDetailBean;
    IMeExpressPresenter iMeExpressPresenter;
    @BindView(R.id.img_kuaidi)
    ImageView img_kuaidi;//快递图标
    @BindView(R.id.tv_shentongkuaidi)
    TextView tv_shentongkuaidi;//快递名称
    @BindView(R.id.tv_shentongkuaidi_num)
    TextView tv_shentongkuaidi_num;//快递单号
    @BindView(R.id.img_kuaidi_copy)
    ImageView img_kuaidi_copy;//复制
    @BindView(R.id.tv_concate)
    TextView tv_concate;//联系人
    @BindView(R.id.tv_contact_phone)
    TextView tv_contact_phone;//联系电话
    @BindView(R.id.tv_contact_address)
    TextView tv_contact_address;//联系地址
    private String isTrasfer, endTime, ownAddre, zzzAddre;
    private int firstHolder = 0, firstAucation, firstMaxPrice;
    @BindView(R.id.tv_copy_zz1)
    TextView tv_copy_zz1;//复制铸造者
    @BindView(R.id.tv_copy_yyz)
    TextView tv_copy_yyz;//拥有者复制
    @BindView(R.id.rela_jingbiao_price)
    RelativeLayout rela_jingbiao_price;//竞价标题
    @BindView(R.id.tv_name_maxprice)
    TextView tv_name_maxprice;//最高价名称
    String sellMode;
    @BindView(R.id.lin_yongyouzhe)
    LinearLayout lin_yongyouzhe;
    private Long timaJianGe;
    private int freezeStatus;
    private String sellStatus;
    @BindView(R.id.img_tag)
    ImageView img_tag;
    String orderId, collect, liutong,my_bug;
    @BindView(R.id.lin_cehxiao_outprice_goumai)
    LinearLayout lin_cehxiao_outprice_goumai;

    /* socket  nft详情页面包含对象：nftMaxPrice  nftSeeCount  nftAuctionLog  nftHolderLog  nftMaxWalletAddr
    市场页面包含对象：userNoticeRemind
    消息中心页面包含对象：notice*/
    @Override
    public void initUI() {
        showDialog();
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
//        CommonUtil.setStatusBarTextColor(PaiActivity.this,1);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
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
        sellStatus = getIntent().getStringExtra("sellStatus");
        isTrasfer = getIntent().getStringExtra("isTrasfer");
        nftId = getIntent().getStringExtra("nftId");
        nftState = getIntent().getStringExtra("nftState");//我的nft类型
        collect = getIntent().getStringExtra("collect");
        liutong = getIntent().getStringExtra("liutong");
        my_bug = getIntent().getStringExtra("my_bug");
        Log.e("FF433G","FF433G_0:" + my_bug);
//        if (!TextUtils.isEmpty(sellStatus)) {
//            if (sellStatus.equals("1")) {
//                lin_outprice_goumai.setVisibility(View.VISIBLE);
//            } else {
//                lin_outprice_goumai.setVisibility(View.GONE);
//            }
//        }

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        recyclewTwo.setLayoutManager(gridLayoutManager);
        recyclewTwo.setAdapter(tradeListPowerAdapter);


        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        recyclewRecord.setLayoutManager(linearLayoutManager3);
        recyclewRecord.setAdapter(tradeListRecordAdapter);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        recyclewJingBiao.setLayoutManager(linearLayoutManager4);
        recyclewJingBiao.setAdapter(tradeListJingBiaoAdapter);
        setStatusBarColor(this, R.color.black);

        tradeListPowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Log.e("FFEEE","URL:" + proveUrls.get(i));

                    Intent intent3 = new Intent(PaiActivity.this, ProductImageActivity.class);
                    intent3.putExtra("url", proveUrls.get(i) + "");
                    startActivity(intent3);
            }
        });

//        surplus = getIntent().getStringExtra("surplus");
//        if (!TextUtils.isEmpty(surplus) && !surplus.equals("null")) {
//            countDownTimer = new CountDownTimer(Long.valueOf(surplus), 1000) {
//
//                public void onTick(long millisUntilFinished) {
//                    formatLongToTimeStr((millisUntilFinished / 1000));
//                    lin_detail_time.setVisibility(View.VISIBLE);
//                    lin_detail_time_over.setVisibility(View.GONE);
//                }
//
//                public void onFinish() {
//                    lin_detail_time.setVisibility(View.GONE);
//                    lin_detail_time_over.setVisibility(View.VISIBLE);
//                }
//            };
//            countDownTimer.start();
//        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        liu_RecyclerView.setLayoutManager(linearLayoutManager);
        recAdapter = new BannerDetailAdapter(list, this);
        liu_RecyclerView.setAdapter(recAdapter);
        if (data != null) {
            data.clear();
        }
        companyId = getIntent().getStringExtra("companyId");
        //收货地址
        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(this);
        sto_recyclew.setLayoutManager(linearLayoutManager6);
        sto_recyclew.setAdapter(expressAdapter);

        if (iMeExpressPresenter == null) {
            iMeExpressPresenter = new IMeExpressPresenterImpl();
            iMeExpressPresenter.registerViewCallback(this);
        }
        iMeExpressPresenter.getData(nftId);

        //铸造者复制
        tv_copy_zz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyContentToClipboard(zzzAddre, PaiActivity.this);
                ToastUtils.getInstance(PaiActivity.this).show("复制成功!");
            }
        });
        //拥有者复制
        tv_copy_yyz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyContentToClipboard(ownAddre, PaiActivity.this);
                ToastUtils.getInstance(PaiActivity.this).show("复制成功!");
            }
        });
    }

    public void formatLongToTimeStr(Long date) {
        try {
            long day = date / (60 * 60 * 24);
            long hour = (date / (60 * 60) - day * 24);
//        long hour = date / (60 * 60);
            long min = ((date / 60) - day * 24 * 60 - hour * 60);
            long s = (date - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        String strtime = "剩余："+day+"天"+hour+"小时"+min+"分"+s+"秒";
            tv_detail_day.setText(day + "");//天
            tv_detail_hour.setText((day * 24 + hour) + "");//时
            tv_detail_minute.setText(min + "");//分
            tv_detail_sends.setText(s + "");//秒
        } catch (Exception e) {

        }
    }

    public void formatLongToTimeStr2(Long date) {
        try {
            long day = date / (60 * 60 * 24);
            long hour = (date / (60 * 60) - day * 24);
//        long hour = date / (60 * 60);
            long min = ((date / 60) - day * 24 * 60 - hour * 60);
            long s = (date - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        String strtime = "剩余："+day+"天"+hour+"小时"+min+"分"+s+"秒";
            String strtime = (day * 24 + hour) + "小时" + min + "分" + s + "秒";
            tv_success.setText(strtime + "无人出价即购买成功");
        } catch (Exception e) {

        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @OnClick({R.id.img_pai_back, R.id.img_share, R.id.lin_outprice_goumai, R.id.lin_collect, R.id.tv_go_trade, R.id.tv_zhuanyi,
            R.id.tv_qu_jiaoyi, R.id.tv_tihuo, R.id.lin_cehxiao_outprice_goumai, R.id.img_kuaidi_copy, R.id.tv_admin_shouhuo, R.id.lin_collection2
            , R.id.tv_jingbiao_more, R.id.tv_chiyouzhe_more, R.id.lin_zzz, R.id.lin_yongyouzhe, R.id.tv_ipfs, R.id.tv_liulanqi})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_pai_back://返回
                finish();
                break;
            case R.id.tv_ipfs:
                Intent intentipfs = new Intent(PaiActivity.this, AdWebActivity.class);
                intentipfs.putExtra("banner_url", UrlConstant.baseWap + "browser/ipfs?key=" + metatataHash);
                intentipfs.putExtra("hideTitle", true);
                startActivity(intentipfs);

                break;
            case R.id.tv_liulanqi:
                Intent intentlianqi = new Intent(PaiActivity.this, AdWebActivity.class);
                intentlianqi.putExtra("banner_url", UrlConstant.baseWap + "browser/ipfs?key=" + metatataCode);
                intentlianqi.putExtra("hideTitle", true);
                startActivity(intentlianqi);
                break;
            case R.id.img_share://分享
                new Share2.Builder(this)
                        .setContentType(ShareContentType.TEXT)
                        .setTextContent(shareUrl)
                        .setTitle(shareTitle)
                        // .forcedUseSystemChooser(false)
                        .build()
                        .shareBySystem();
                break;
            case R.id.lin_outprice_goumai://购买或者出价
//                if (!TextUtils.isEmpty(sellStatus)) {
//                    if (sellStatus.equals("1")) {
//                        lin_outprice_goumai.setVisibility(View.VISIBLE);
                if (MoreClick.MoreClicks()) {
                    if (isGoumai) {//购买
                        Intent intent = new Intent(PaiActivity.this, BuyMarkActivity.class);
                        intent.putExtra("title", nftName + "");
                        intent.putExtra("nftId", nftId);
                        intent.putExtra("uniqueAdress", uniqueAdress);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                        finish();
                    } else {//出价
                        Intent intent = new Intent(PaiActivity.this, OutPriceActivity.class);
                        intent.putExtra("title", nftName + "");
                        intent.putExtra("nftId", nftId);
                        intent.putExtra("uniqueAdress", uniqueAdress);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                        finish();
                    }
                }
//                    } else {
//                        lin_outprice_goumai.setVisibility(View.GONE);
//                    }
//                } else {
//                    if (isGoumai) {//购买
//                        Intent intent = new Intent(PaiActivity.this, BuyMarkActivity.class);
//                        intent.putExtra("title", nftName + "");
//                        intent.putExtra("nftId", nftId);
//                        intent.putExtra("uniqueAdress", uniqueAdress);
//                        intent.putExtra("orderId", orderId);
//                        startActivity(intent);
//                        finish();
//                    } else {//出价
//                        Intent intent = new Intent(PaiActivity.this, OutPriceActivity.class);
//                        intent.putExtra("title", nftName + "");
//                        intent.putExtra("nftId", nftId);
//                        intent.putExtra("uniqueAdress", uniqueAdress);
//                        intent.putExtra("orderId", orderId);
//                        startActivity(intent);
//                        finish();
//                    }
//                }

                break;
            case R.id.lin_collect://收藏或者取消收藏
            case R.id.lin_collection2://收藏或者取消收藏

                if (tv_colection.getText().toString().equals("收藏")) {
                    if (iMarkCollectionPresenter == null) {
                        iMarkCollectionPresenter = new IMarkCollectionPresenterImpl();
                        iMarkCollectionPresenter.registerViewCallback(this);
                    }
                    iMarkCollectionPresenter.getData(nftId, uniqueAdress);
                } else if (tv_colection.getText().toString().equals("取消收藏")) {
                    if (iMarkUnCollectionPresenter == null) {
                        iMarkUnCollectionPresenter = new IMarkUnCollectionPresenterImpl();
                        iMarkUnCollectionPresenter.registerViewCallback(this);
                    }
                    iMarkUnCollectionPresenter.getData(nftId, uniqueAdress);
                }
                break;
            case R.id.tv_go_trade://去交易

                Intent intent = new Intent(PaiActivity.this, TradeListDetailActivity.class);
                intent.putExtra("nftId", nftId + "");
                intent.putExtra("companyId", uniqueAdress);
                intent.putExtra("orderId", mGoTradeDetailBean.getData().getId() + "");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_zhuanyi://转移
                Intent intentZ = new Intent(PaiActivity.this, NftTransferActivity.class);
                if (mGoTradeDetailBean != null) {
                    intentZ.putExtra("nftId", mGoTradeDetailBean.getData().getNftId() + "");
                    intentZ.putExtra("cover", mGoTradeDetailBean.getData().getProductCover());
                    intentZ.putExtra("type", mGoTradeDetailBean.getData().getProductType() + "");
                    intentZ.putExtra("name", mGoTradeDetailBean.getData().getProductName());
                    intentZ.putExtra("intro", mGoTradeDetailBean.getData().getProductIntro());
                }
                startActivity(intentZ);
                finish();
                break;
            case R.id.tv_qu_jiaoyi://去交易
                Log.e("FFF44456", "去郊游:" + mGoTradeDetailBean.getData().getId() + "");
                Intent intentJ = new Intent(PaiActivity.this, TradeListDetailActivity.class);
                intentJ.putExtra("nftId", mGoTradeDetailBean.getData().getNftId() + "");
                intentJ.putExtra("companyId", uniqueAdress + "");
                intentJ.putExtra("orderId", mGoTradeDetailBean.getData().getId() + "");
                startActivity(intentJ);
                finish();
                break;
            case R.id.tv_tihuo://提货
                Intent intentT = new Intent(PaiActivity.this, NftTiHuoActivity.class);
                intentT.putExtra("nftId", mGoTradeDetailBean.getData().getNftId() + "");
                intentT.putExtra("img", mGoTradeDetailBean.getData().getProductImage());
                intentT.putExtra("name", mGoTradeDetailBean.getData().getProductName());
                intentT.putExtra("orderId", mGoTradeDetailBean.getData().getId() + "");
                startActivity(intentT);
                finish();
                break;
            case R.id.lin_cehxiao_outprice_goumai://撤销交易
//                if (isTrasfer != null) {
//                    if (isTrasfer.equals("0")) {
//                        ToastUtil.showShort(PaiActivity.this, "转移中");
//                        return;
//                    } else if (isTrasfer.equals("1")) {
//                        ToastUtil.showShort(PaiActivity.this, "转移失败");
//                    } else if (isTrasfer.equals("2")) {
                Intent intentC = new Intent(PaiActivity.this, NftCancelBackActivity.class);
                intentC.putExtra("nftId", mGoTradeDetailBean.getData().getNftId());
                intentC.putExtra("cover", mGoTradeDetailBean.getData().getProductCover());
                intentC.putExtra("type", mGoTradeDetailBean.getData().getProductType() + "");
                intentC.putExtra("name", mGoTradeDetailBean.getData().getProductName());
                intentC.putExtra("intro", mGoTradeDetailBean.getData().getProductIntro());
                intentC.putExtra("sellMode", mGoTradeDetailBean.getData().getSellMode() + "");
                intentC.putExtra("orderId", orderId + "");
                startActivity(intentC);
                finish();
//                        return;
//                    }
//                } else {
//                    ToastUtil.showShort(PaiActivity.this, "未转移");
//                    return;
//                }

                break;
            case R.id.img_kuaidi_copy://复制
                copyContentToClipboard(tv_shentongkuaidi_num.getText().toString() + "", PaiActivity.this);
                ToastUtils.getInstance(PaiActivity.this).show("复制成功!");
                break;
            case R.id.tv_admin_shouhuo://确认收货
                Intent intentA = new Intent(PaiActivity.this, NftAdminHuoActivity.class);
                intentA.putExtra("nftId", mGoTradeDetailBean.getData().getNftId() + "");
                intentA.putExtra("cover", mGoTradeDetailBean.getData().getProductCover() + "");
                intentA.putExtra("type", mGoTradeDetailBean.getData().getProductType() + "");
                intentA.putExtra("name", mGoTradeDetailBean.getData().getProductName() + "");
                intentA.putExtra("intro", mGoTradeDetailBean.getData().getProductIntro() + "");
                startActivity(intentA);
                break;
            case R.id.tv_jingbiao_more://竞标价格更多
                Intent intent1 = new Intent(PaiActivity.this, BiddingActivity.class);
                intent1.putExtra("nftId", nftId);
                intent1.putExtra("address", uniqueAdress);
                intent1.putExtra("did", unique.getDid());
                startActivity(intent1);
                break;
            case R.id.tv_chiyouzhe_more://持有者记录
                Intent intent2 = new Intent(PaiActivity.this, HolderActivity.class);
                intent2.putExtra("nftId", nftId);
                intent2.putExtra("address", uniqueAdress);
                intent2.putExtra("did", unique.getDid());
                startActivity(intent2);
                break;
//            case R.id.lin_zzz://铸造者
//                Intent intent3 = new Intent(PaiActivity.this, AdWebActivity.class);
//                intent3.putExtra("banner_url", UrlConstant.baseWap + "browser/ipfs?key=" + metatataHash);
//                intent3.putExtra("hideTitle", true);
//                startActivity(intent3);
//                break;
//            case R.id.lin_yongyouzhe://拥有者
//                Intent intent4 = new Intent(PaiActivity.this, AdWebActivity.class);
//                intent4.putExtra("banner_url", UrlConstant.baseWap + "browser/ipfs?key=" + metatataCode);
//                intent4.putExtra("hideTitle", true);
//                startActivity(intent4);
//                break;
        }

    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void initData() {

        iTradeDetailPresenter = new ITradeDetailPresenterImpl();
        iTradeDetailPresenter.registerViewCallback(this);
        if (TextUtils.isEmpty(orderId)) {
            iTradeDetailPresenter.getData(1, "", nftId, uniqueAdress);
        } else {
            iTradeDetailPresenter.getData(1, orderId, nftId, uniqueAdress);
        }
        //持有者
        iOwnerPresenter = new ITradeOwnerPresenterImpl();
        iOwnerPresenter.registerViewCallback(this);
        iOwnerPresenter.getData(1, nftId);
        //竞标者
        iJingBiaoPresenter = new IJingBiaoPresenterImpl();
        iJingBiaoPresenter.registerViewCallback(this);
        iJingBiaoPresenter.getData(nftId);
        //质押保证金
        iTradeCationPresenter = new ITradeCationPresenterImpl();
        iTradeCationPresenter.registerViewCallback(this);
        iTradeCationPresenter.getData(1, nftId);

    }

    private void sendMeg() {
        //先关闭更多的
        sendMegBid("closegid", 1);
        sendMegHolder("closegid", 1);

        NetClient.getInstance().register(this);
        SkbuffTwo skbuff = new SkbuffTwo();
        skbuff.setCmd("open");
        skbuff.setType("nft");
        skbuff.setFlags(NetConstant.NETHDR_FLAG_NONE);
        skbuff.setReserve(NetConstant.NETHDR_RESERVER);
        skbuff.setVersion(NetConstant.NETHDR_VERSION);
        skbuff.setMagic(NetConstant.NETHDR_MAGIC);

        SkbuffTwo.Data data = new SkbuffTwo.Data();
        SkbuffTwo.Data.Group group = new SkbuffTwo.Data.Group();
        group.setCoupleId(nftId);
        group.setWalletAddr(uniqueAdress);
        group.setDid(unique.getDid());
        group.setGid("g_nft_info");
        List<SkbuffTwo.Data.Group.ModuleList> moduleLists = new ArrayList<>();
        SkbuffTwo.Data.Group.ModuleList moduleList = new SkbuffTwo.Data.Group.ModuleList();
        moduleList.setModuleId("m_nft_holder_log");//持有者记录
        moduleList.setPageSize(5);
        moduleList.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList2 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList2.setModuleId("m_nft_auction_log");//竞标价格
        moduleList2.setPageSize(5);
        moduleList2.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList3 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList3.setModuleId("m_nft_max_price");//当前最高价
        moduleList3.setPageSize(5);
        moduleList3.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList4 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList4.setModuleId("m_nft_see_count");//浏览量
        moduleList4.setPageSize(5);
        moduleList4.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList5 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList5.setModuleId("m_nft_max_confirmation");//最新出价
        moduleList5.setPageSize(5);
        moduleList5.setPage(0);

        moduleLists.add(moduleList);
        moduleLists.add(moduleList2);
        moduleLists.add(moduleList3);
        moduleLists.add(moduleList4);
        moduleLists.add(moduleList5);

        group.setModuleList(moduleLists);
        data.setGroup(group);
        skbuff.setData(data);
        Log.e("FF23444343", "sendData_发送:" + skbuff.toString());

        NetClient.getInstance().sendTwo(skbuff);
    }

    private void sendMegBid(String cmd, int page) {
        NetClient.getInstance().register(this);
        SkbuffTwo skbuff = new SkbuffTwo();
        skbuff.setCmd(cmd);
        skbuff.setType("nft");
        skbuff.setFlags(NetConstant.NETHDR_FLAG_NONE);
        skbuff.setReserve(NetConstant.NETHDR_RESERVER);
        skbuff.setVersion(NetConstant.NETHDR_VERSION);
        skbuff.setMagic(NetConstant.NETHDR_MAGIC);

        List<SkbuffTwo.Data.Group.ModuleList> moduleLists = new ArrayList<>();
        SkbuffTwo.Data data = new SkbuffTwo.Data();
        SkbuffTwo.Data.Group group = new SkbuffTwo.Data.Group();
        group.setCoupleId(nftId);
        group.setWalletAddr(uniqueAdress);
        group.setDid(unique.getDid());
        group.setGid("g_nft_info");
        SkbuffTwo.Data.Group.ModuleList moduleList2 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList2.setModuleId("m_nft_auction_log");//竞标价格
        moduleList2.setPageSize(15);
        moduleList2.setPage(page);

        moduleLists.add(moduleList2);

        group.setModuleList(moduleLists);
        data.setGroup(group);
        skbuff.setData(data);
        Log.e("FF34423", "sendData:" + skbuff.toString());

        NetClient.getInstance().sendTwo(skbuff);
    }

    private void sendMegHolder(String cmd, int page) {
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
        group.setWalletAddr(uniqueAdress);
        group.setDid(unique.getDid());
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

    @Override
    public void bindView(Bundle bundle) {
        img_all_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pai;
    }

    @Override
    public String id() {
        return null;
    }

    List<AuctionMarkBean> resultBean;
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
                            //持有者记录
                            if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_nft_holder_log")) {
                                String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                                List<HolderMarkBean> resultBean = new Gson().fromJson(json, new TypeToken<List<HolderMarkBean>>() {
                                }.getType());
//                            Log.e("FF2344ff", "持有者记录:" + new Gson().toJson(resultBean));
                                Log.e("FF23444343", "持有者记录:" + json + "--firstHolder:" + firstHolder);

                                if (firstHolder == 0) {//第一次进来
                                    if (resultBean != null && resultBean.size() > 0) {
                                        firstHolder = 1;
                                        if (rows != null) {
                                            rows.clear();
                                        }

                                        rows.addAll(resultBean);
                                    }
                                } else {
                                    rows.addAll(0, resultBean);
                                }

                                if (rows != null && rows.size() > 5) {
                                    rows = rows.subList(0, 5);
                                }
                                tradeListRecordAdapter.setNewData(rows);

                            }
                            //竞标价格
                            if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_nft_auction_log")) {
                                String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                                resultBean = new Gson().fromJson(json, new TypeToken<List<AuctionMarkBean>>() {
                                }.getType());
//                            Log.e("FF2344ff3", "竞标价格:" + new Gson().toJson(resultBean));
                                Log.e("FF23444343h", "竞标价格:" + json);
                                if (!TextUtils.isEmpty(json)) {
                                    if (!json.equals("null")) {
                                        if (firstAucation == 0) {//第一次进来
                                            if (resultBean != null && resultBean.size() > 0) {
                                                firstAucation = 1;
                                                if (rowsJing != null) {
                                                    rowsJing.clear();
                                                }
                                                rowsJing.addAll(resultBean);
                                                Log.e("FF23444343", "新增一条");
                                            } else {
                                                rela_jingbiao_price.setVisibility(View.GONE);
                                                recyclewJingBiao.setVisibility(View.GONE);
                                            }
                                        } else {
                                            Log.e("FF23444343", "新增一条2222");
                                            rowsJing.addAll(0, resultBean);
                                        }

                                        if (rowsJing != null && rowsJing.size() > 5) {
                                            rowsJing = rowsJing.subList(0, 5);
                                        }

                                        tradeListJingBiaoAdapter.setNewData(rowsJing);
                                    } else {
                                        rela_jingbiao_price.setVisibility(View.GONE);
                                        recyclewJingBiao.setVisibility(View.GONE);
                                    }
                                } else {
                                    rela_jingbiao_price.setVisibility(View.GONE);
                                    recyclewJingBiao.setVisibility(View.GONE);
                                }
                            }
                            //最高出价
                            if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_nft_max_price")) {
                                String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                                List<MarkMaxPriceBean> resultBean2 = new Gson().fromJson(json, new TypeToken<List<MarkMaxPriceBean>>() {
                                }.getType());
                                Log.e("FDDD23ds", "最高出价_json:" + json);
                                if (!TextUtils.isEmpty(json)) {
                                    if (!json.equals("null")) {
                                        if (resultBean2 != null && resultBean2.size() > 0) {
                                            if (resultBean2.get(0) != null && resultBean2.get(0).getMaxPrice() != null && !resultBean2.get(0).getMaxPrice().equals("")) {

                                                Log.e("FDDD23ds", "sellMode:" + sellMode);
                                                if (!TextUtils.isEmpty(sellMode) && sellMode.equals("2")) {
                                                    if (Double.valueOf(resultBean2.get(0).getMaxPrice()) == 0) {
                                                        if (lin_max_jingjiazhe != null) {
                                                            lin_max_jingjiazhe.setVisibility(View.GONE);
                                                        }
                                                    } else {
                                                        if (lin_max_jingjiazhe != null) {
                                                            lin_max_jingjiazhe.setVisibility(View.VISIBLE);
                                                        }
                                                        if (tv_first_name != null) {
                                                            tv_first_name.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                    try {
                                                        tv_max_price.setText(String.format("%.6f", Double.valueOf(resultBean2.get(0).getMaxPrice())) + "");
                                                    } catch (Exception e) {

                                                    }
                                                    if (resultBean != null && resultBean.size() > 0) {
                                                        lin_max_jingjiazhe.setVisibility(View.VISIBLE);
                                                        tv_first_name.setVisibility(View.VISIBLE);
                                                    } else {
                                                        lin_max_jingjiazhe.setVisibility(View.GONE);
                                                        tv_first_name.setVisibility(View.GONE);
                                                    }
                                                } else {//固定价格
                                                    try {
                                                        tv_max_price.setText(String.format("%.6f", Double.valueOf(resultBean2.get(0).getMaxPrice())) + "");
                                                    } catch (Exception e) {

                                                    }
                                                    lin_max_jingjiazhe.setVisibility(View.GONE);
                                                    tv_first_name.setVisibility(View.GONE);
                                                }
                                            } else {
                                                tv_max_price.setText("0.000000");
                                                lin_max_jingjiazhe.setVisibility(View.GONE);
                                                tv_first_name.setVisibility(View.GONE);
                                            }
                                        } else {
                                            tv_max_price.setText("0.000000");
                                            lin_max_jingjiazhe.setVisibility(View.GONE);
                                            tv_first_name.setVisibility(View.GONE);
                                        }
                                    } else {
                                        tv_max_price.setText("0.000000");
                                        lin_max_jingjiazhe.setVisibility(View.GONE);
                                        tv_first_name.setVisibility(View.GONE);
                                    }
                                } else {
                                    tv_max_price.setText("0.000000");
                                    lin_max_jingjiazhe.setVisibility(View.GONE);
                                    tv_first_name.setVisibility(View.GONE);
                                }

                            }
                            //浏览量
                            if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_nft_see_count")) {
                                String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                                Log.e("FGFD4322", "浏览量:" + json);
                                List<MarkSeeCountBean> resultBean = new Gson().fromJson(json, new TypeToken<List<MarkSeeCountBean>>() {
                                }.getType());
                                if (resultBean != null && resultBean.size() > 0) {
                                    if (resultBean.get(0) != null && resultBean.get(0).getSeeCount() != null) {
                                        if (shen_scan != null) {
                                            if (resultBean.get(0).getSeeCount().contains(".")) {
                                                String seeCount = resultBean.get(0).getSeeCount();
                                                shen_scan.setText(seeCount.substring(0, seeCount.indexOf(".")) + "");
                                            }
                                        }
                                    }
                                }

                            }
                            //最新出价
                            if (markDetailSocketBean.getData().getData().get(i).getModuleId().equals("m_nft_max_confirmation")) {
                                String json = new Gson().toJson(markDetailSocketBean.getData().getData().get(i).getData());
                                Log.e("FDDD23", "最新出价:" + json);
                                Log.e("FDDD23", "firstHolder:" + firstHolder);
                                Log.e("FDDD23", "endTime:" + endTime);
                                Log.e("FDDD23", "oldTime:" + oldTime);

                                List<MarkConfirmationBean> resultBean = new Gson().fromJson(json, new TypeToken<List<MarkConfirmationBean>>() {
                                }.getType());
                                if (resultBean != null && resultBean.size() > 0) {
                                    Log.e("FDDD23ff", "resultBean:" + new Gson().toJson(resultBean.get(0)));
                                    if (!TextUtils.isEmpty(resultBean.get(0).getConfirmationDate()) && !TextUtils.isEmpty(resultBean.get(0).getCurSystemTime())
                                            && !TextUtils.isEmpty(resultBean.get(0).getAuctionTimeInterval())) {
                                        try {
                                            //拍卖的时间间隔
                                            timaJianGe = Long.valueOf(maxZuiPrice(mGoTradeDetailBean.getData().getAuctionTimeInterval() + "") * 1000);
                                            Log.e("FDDD23ff", "拍卖的时间间隔:" + timaJianGe);
                                            Long daojiTime = (daysBetween(getDate(resultBean.get(0).getCurSystemTime()), getDate(resultBean.get(0).getConfirmationDate())) + timaJianGe);
                                            Log.e("FDDD23ff", "daojiTime:" + daojiTime);
                                            if ((daysBetween(getDate(resultBean.get(0).getConfirmationDate()), getDate(resultBean.get(0).getCurSystemTime())) + timaJianGe) > 0) {//修改倒计时
                                                if (resultBean != null && resultBean.size() > 0 && timaJianGe > 1000) {

                                                    if (countDownTimer3 != null) {
                                                        countDownTimer3.cancel();
                                                        countDownTimer3 = null;
                                                    }
                                                        countDownTimer3 = new CountDownTimer(daojiTime, 1000) {

                                                            public void onTick(long millisUntilFinished) {
                                                                formatLongToTimeStr2((millisUntilFinished / 1000));
                                                            }

                                                            public void onFinish() {
                                                                tv_success.setText("拍卖已结束");
                                                            }
                                                        };
                                                        countDownTimer3.start();

                                                } else {
                                                    tv_success.setText("");
                                                }
                                            } else {//小于间隔时间就隐藏
                                                if (lin_max_jingjiazhe != null) {
                                                    lin_max_jingjiazhe.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }
                        }
                        if (rowsJing != null && rowsJing.size() > 0) {
                            if (!TextUtils.isEmpty(rowsJing.get(0) + "") && !TextUtils.isEmpty(rowsJing.get(0).getWalletAddr())) {
                                if (tv_walletAdd != null) {
                                    tv_walletAdd.setText(rowsJing.get(0).getWalletAddr() + "");
                                } else {
                                }
                            } else {
                            }
                        }
                        if (freezeState != 1) {
                            if (!TextUtils.isEmpty(sellMode) && sellMode.equals("1")) {
                                lin_max_jingjiazhe.setVisibility(View.GONE);
                                lin_max_price_time.setVisibility(View.GONE);
                            }
                        } else {

                        }

                    }
                }
            } catch (Exception e) {
                Log.e("FFS4434", "E:" + e);
            }
        }
    };
    /**
     * 获取当前时间
     *
     * @return
     */
    Date date;

    public Date getDate(String str) {
        try {
            java.text.SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            date = formatter.parse(str);
            return date;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;


    }

    public long daysBetween(Date startDate, Date endDate)
            throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDate = sdf.parse(sdf.format(startDate));
        endDate = sdf.parse(sdf.format(endDate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long time1 = calendar.getTimeInMillis();

        calendar.setTime(endDate);
        long time2 = calendar.getTimeInMillis();

        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        long between = (time2 - time1);
        return between;
    }

    @Override
    public void recv(String skb) {
        Log.e("FF23444343g", "11111:" + skb);
        dismissDialog();
        //判断页面是否在显示
        if (!IsDestroy.isDestroy(PaiActivity.this)) {
            Log.e("FF3442355", "全部skb:" + skb);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("skb", skb);
            message.setData(bundle);
            handler.sendMessage(message);


        } else {
            Log.e("FF3442355", "22222");
        }
    }

    @Override
    public void notify(NetEvent event) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("FF23444343g", "onResume");
        //判断页面是否在显示
        if (!IsDestroy.isDestroy(PaiActivity.this)) {
            Log.e("FF23444343", "显示 :" + firstAucation);
            firstAucation = 0;
            firstHolder = 0;
            sendMeg();
        } else {
            Log.e("FF23444343", "没有显示");
        }

    }

    long oldTime;
    String metatataCode, metatataHash, startPrice, nftName;
    int freezeState;

    //详情收到数据
    @Override
    public void loadData(GoTradeDetailBean goTradeDetailBean) {
        Log.e("FDD433", "goTradeDetailBean:" + goTradeDetailBean);
        dismissDialog();
        if (goTradeDetailBean != null && goTradeDetailBean.getCode() == 200) {
            if (goTradeDetailBean.getData() != null) {
                mGoTradeDetailBean = goTradeDetailBean;
                orderId = mGoTradeDetailBean.getData().getId() + "";
                sellMode = goTradeDetailBean.getData().getSellMode() + "";
                freezeState = goTradeDetailBean.getData().getFreezeState();
                if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getEndTime())) {
                    endTime = mGoTradeDetailBean.getData().getEndTime();
                }
                if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getMetatataHash())) {
                    metatataHash = mGoTradeDetailBean.getData().getMetatataHash();
                }
                if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getMetatataCode())) {
                    metatataCode = mGoTradeDetailBean.getData().getMetatataCode();
                }
                if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getStartPrice())) {
                    startPrice = mGoTradeDetailBean.getData().getStartPrice();
                }
                if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getNftName())) {
                    nftName = mGoTradeDetailBean.getData().getNftName();
                }
                surplus = mGoTradeDetailBean.getData().getSurplus();
                if (!TextUtils.isEmpty(surplus) && !surplus.equals("null")) {
                    countDownTimer = new CountDownTimer(Long.valueOf(surplus), 1000) {

                        public void onTick(long millisUntilFinished) {
                            formatLongToTimeStr((millisUntilFinished / 1000));
                            if (lin_detail_time != null) {
                                lin_detail_time.setVisibility(View.VISIBLE);
                            }
                            if (lin_detail_time_over != null) {
                                lin_detail_time_over.setVisibility(View.GONE);
                            }
                        }

                        public void onFinish() {
                            if (lin_detail_time != null) {
                                lin_detail_time.setVisibility(View.GONE);
                            }
                            if (lin_detail_time_over != null) {
                                lin_detail_time_over.setVisibility(View.VISIBLE);
                            }
                        }
                    };
                    countDownTimer.start();
                }
                //判断页面是否在显示
                if (!IsDestroy.isDestroy(PaiActivity.this)) {
                    if (goTradeDetailBean != null && goTradeDetailBean.getCode() == 200) {
                        if (goTradeDetailBean.getData() != null) {
                            mGoTradeDetailBean = goTradeDetailBean;
                            sellMode = goTradeDetailBean.getData().getSellMode() + "";
                            if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getEndTime())) {
                                endTime = mGoTradeDetailBean.getData().getEndTime();
                            }
                            if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getMetatataHash())) {
                                metatataHash = mGoTradeDetailBean.getData().getMetatataHash();
                            }
                            if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getMetatataCode())) {
                                metatataCode = mGoTradeDetailBean.getData().getMetatataCode();
                            }
                            if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getStartPrice())) {
                                startPrice = mGoTradeDetailBean.getData().getStartPrice();
                            }
                            if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getNftName())) {
                                nftName = mGoTradeDetailBean.getData().getNftName();
                            }


//                if (!TextUtils.isEmpty(mGoTradeDetailBean.getData().getAuctionTimeInterval())) {
//                    Log.e("FDDD23","getAuctionTimeInterval:" + mGoTradeDetailBean.getData().getAuctionTimeInterval());
//                    oldTime = Long.valueOf(maxZuiPrice(mGoTradeDetailBean.getData().getAuctionTimeInterval() + "") * 1000);
//                    countDownTimer2 = new CountDownTimer(Long.valueOf(maxZuiPrice(mGoTradeDetailBean.getData().getAuctionTimeInterval() + "") * 1000), 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//                            formatLongToTimeStr2((millisUntilFinished / 1000));
//                        }
//
//                        public void onFinish() {
//                        }
//                    };
//                    countDownTimer2.start();
//                }
                            try {
                                if (goTradeDetailBean.getData().getProductType() == 1) {//票务
                                    tv_type_title.setText("票务");
                                    tv_seat_rows.setVisibility(View.VISIBLE);
                                    tv_type_title.setBackground(getResources().getDrawable(R.drawable.shop1));
                                } else if (goTradeDetailBean.getData().getProductType() == 2) {//收藏品
                                    tv_type_title.setText("收藏品");
                                    tv_seat_rows.setVisibility(View.GONE);
                                    tv_type_title.setBackground(getResources().getDrawable(R.drawable.shop2));
                                } else if (goTradeDetailBean.getData().getProductType() == 3) {//艺术品
                                    tv_type_title.setText("艺术品");
                                    tv_seat_rows.setVisibility(View.GONE);
                                    tv_type_title.setBackground(getResources().getDrawable(R.drawable.shop));
                                } else if (goTradeDetailBean.getData().getProductType() == 4) {//轻奢品
                                    tv_type_title.setBackground(getResources().getDrawable(R.drawable.shop4));
                                    tv_type_title.setText("轻奢品");
                                    tv_seat_rows.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {

                            }

                            Log.e("FSS24555", "getProductImage:" + goTradeDetailBean.getData().getProductImage());
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductImage())) {
                                if (!goTradeDetailBean.getData().getProductImage().contains(",")) {
//                        bannImg.add(goTradeDetailBean.getData().getProductImage());
                                    dataBean = new BannerDatabase.DataBean();
                                    dataBean.setImageAddress(goTradeDetailBean.getData().getProductImage());
                                    data.add(dataBean);

                                } else {
                                    if (bannImg != null) {
                                        bannImg.clear();
                                    }
                                    bannImg = Arrays.asList(goTradeDetailBean.getData().getProductImage().split(","));
                                    if (bannImg != null) {
                                        for (int i = 0; i < bannImg.size(); i++) {
                                            dataBean = new BannerDatabase.DataBean();
                                            dataBean.setImageAddress(bannImg.get(i));
                                            data.add(dataBean);
                                        }
                                    }
                                }
                            }
                            Glide.with(PaiActivity.this).load(goTradeDetailBean.getData().getIconUrl()).into(img_tag);
                            recAdapter.setBannerl(data);//大图片
                            if (goTradeDetailBean.getData().getSellMode() != null) {

                                if (tv_paimai_or_outprice != null) {
                                    if (goTradeDetailBean.getData().getSellMode().equals("1")) {
                                        tv_paimai_or_outprice.setText("固定价");
                                        lin_min_jingpai.setVisibility(View.GONE);
                                        rela_jingbiao_price.setVisibility(View.GONE);
                                        recyclewJingBiao.setVisibility(View.GONE);
                                        tv_name_maxprice.setText("固定价");
                                        tv_paimai_or_outprice.setBackground(getResources().getDrawable(R.drawable.go_trade_guding_bg));
                                    } else if (goTradeDetailBean.getData().getSellMode().equals("2")) {
                                        tv_paimai_or_outprice.setText("拍卖");
                                        tv_name_maxprice.setText("最高出价");
                                        if (goTradeDetailBean.getData().getFreezeState() != 1) {
                                            rela_jingbiao_price.setVisibility(View.GONE);
                                            recyclewJingBiao.setVisibility(View.GONE);
                                        } else {
                                        }
                                        lin_min_jingpai.setVisibility(View.VISIBLE);
                                        tv_paimai_or_outprice.setBackground(getResources().getDrawable(R.drawable.go_trade_detail_paimai_bg));
                                    }
                                }
                            } else {

                            }
                            if (goTradeDetailBean.getData().getProductName() != null) {
                                if (tv_detail_name != null) {
                                    tv_detail_name.setText(goTradeDetailBean.getData().getProductName());
                                }
                            }
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getNftId())) {
                                tv_id.setText(goTradeDetailBean.getData().getNftId() + "");
                            }
                            if (goTradeDetailBean.getData().getSeveralRows() != null && goTradeDetailBean.getData().getSeatNumber() != null) {
                                tv_seat_rows.setText(goTradeDetailBean.getData().getSeveralRows() + "排" + goTradeDetailBean.getData().getSeatNumber() + "座");
                            }
                            Log.e("FDD4322", "getFreezeState:" + goTradeDetailBean.getData().getFreezeState());
                            if (goTradeDetailBean.getData().getFreezeState() == 0) {
                                freezeStatus = goTradeDetailBean.getData().getFreezeState();
                                tv_freezeState.setText("流通中");
                            } else if (goTradeDetailBean.getData().getFreezeState() == 1) {
                                tv_freezeState.setText("锁定中");
                            } else if (goTradeDetailBean.getData().getFreezeState() == 2) {
                                tv_freezeState.setText("永久冻结");
                            }
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getRoyalty())) {
                                tv_banshui.setText(goTradeDetailBean.getData().getRoyalty() + " %");//版税
                            }
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductVat() + "")) {
                                tv_zzshui.setText(goTradeDetailBean.getData().getProductVat() + " %");//增值税
                            }
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductIntro())) {
                                tv_jianjie.setText(goTradeDetailBean.getData().getProductIntro());//简介
                            }
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getWalletAddr())) {
                                zzzAddre = goTradeDetailBean.getData().getWalletAddr();
                                if (goTradeDetailBean.getData().getWalletAddr().length() > 16) {
                                    tv_zzz_adress.setText(getSubStr(goTradeDetailBean.getData().getWalletAddr()));//铸造者钱包地址
                                }
                            }
                            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getOwnerWallet())) {
                                ownAddre = goTradeDetailBean.getData().getOwnerWallet();
                                if (goTradeDetailBean.getData().getOwnerWallet().length() > 16) {
                                    tv_owner_adress.setText(getSubStr(goTradeDetailBean.getData().getOwnerWallet()));//拥有者钱包地址
                                }
                            }
                        }
                        if (goTradeDetailBean.getData().getProductName() != null) {
                            if (tv_detail_name != null) {
                                tv_detail_name.setText(goTradeDetailBean.getData().getProductName());
                            }
                        }
                        if (goTradeDetailBean.getData().getProductName() != null) {
                            nftName = goTradeDetailBean.getData().getNftName();
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getNftId())) {
                            tv_id.setText(goTradeDetailBean.getData().getNftId() + "");
                        }
                        if (goTradeDetailBean.getData().getSeveralRows() != null && goTradeDetailBean.getData().getSeatNumber() != null) {
                            tv_seat_rows.setText(goTradeDetailBean.getData().getSeveralRows() + "排" + goTradeDetailBean.getData().getSeatNumber() + "座");
                        }
                        Log.e("FDD4322", "getFreezeState:" + goTradeDetailBean.getData().getFreezeState());
                        if (goTradeDetailBean.getData().getFreezeState() == 0) {
                            freezeStatus = goTradeDetailBean.getData().getFreezeState();
                            tv_freezeState.setText("流通中");
                        } else if (goTradeDetailBean.getData().getFreezeState() == 1) {
                            tv_freezeState.setText("锁定中");
                        } else if (goTradeDetailBean.getData().getFreezeState() == 2) {
                            tv_freezeState.setText("永久冻结");
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getRoyalty())) {
                            tv_banshui.setText(goTradeDetailBean.getData().getRoyalty() + " %");//版税
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductVat() + "")) {
                            tv_zzshui.setText(goTradeDetailBean.getData().getProductVat() + " %");//增值税
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductIntro())) {
                            tv_jianjie.setText(goTradeDetailBean.getData().getProductIntro());//简介
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getWalletAddr())) {
                            zzzAddre = goTradeDetailBean.getData().getWalletAddr();
                            if (goTradeDetailBean.getData().getWalletAddr().length() > 16) {
                                tv_zzz_adress.setText(getSubStr(goTradeDetailBean.getData().getWalletAddr()));//铸造者钱包地址
                            }
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getOwnerWallet())) {
                            ownAddre = goTradeDetailBean.getData().getOwnerWallet();
                            if (goTradeDetailBean.getData().getOwnerWallet().length() > 16) {
                                tv_owner_adress.setText(getSubStr(goTradeDetailBean.getData().getOwnerWallet()));//拥有者钱包地址
                            }
                        }

                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProveUrl())) {
                            String proveUrl = goTradeDetailBean.getData().getProveUrl();
                            if (!proveUrl.contains(",")) {
                                proveUrls.add(proveUrl);
                            } else {
                                proveUrls = Arrays.asList(proveUrl.split(","));
                            }

                            tradeListPowerAdapter.setNewData(proveUrls);
                        }
                        if (goTradeDetailBean.getData().getMiniDidPrice() == null) {
                            tv_min_jingpai.setText("0.000000");
                        } else {
                            tv_min_jingpai.setText(String.format("%.6f", Double.valueOf(goTradeDetailBean.getData().getMiniDidPrice())) + "");
                        }
                        Log.e("FF22122", "getSellMode:" + goTradeDetailBean.getData().getSellMode());
                        if (goTradeDetailBean.getData().getSellMode() != null) {
                            if (goTradeDetailBean.getData().getSellMode().equals("1")) {//购买
                                pai_mai_img.setBackground(getResources().getDrawable(R.mipmap.gouwuche));
                                isGoumai = true;
                                tv_name_choose.setText("数字资产购买");
                            } else if (goTradeDetailBean.getData().getSellMode().equals("2")) {//出价
                                pai_mai_img.setBackground(getResources().getDrawable(R.drawable.paimai));
                                isGoumai = false;
                                tv_name_choose.setText("数字资产出价");
                            }
                        }

                        if (goTradeDetailBean.getData().getIsCollection() == 0) {//没有收藏
                            tv_colection.setText("收藏");
                            img_collection.setImageDrawable(getResources().getDrawable(R.mipmap.no_collect));
                            img_collection2.setImageDrawable(getResources().getDrawable(R.mipmap.no_collect));
                            tv_collection_liu.setText("收藏");
                        } else if (goTradeDetailBean.getData().getIsCollection() == 1) {//已经收藏
                            tv_colection.setText("取消收藏");
                            img_collection.setImageDrawable(getResources().getDrawable(R.mipmap.collected));
                            img_collection2.setImageDrawable(getResources().getDrawable(R.mipmap.collected));
                            tv_collection_liu.setText("取消收藏");
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getShareUrl())) {
                            shareUrl = goTradeDetailBean.getData().getShareUrl() + "";
                        }
                        if (!TextUtils.isEmpty(goTradeDetailBean.getData().getShareTitle())) {
                            shareTitle = goTradeDetailBean.getData().getShareTitle() + "";
                        }
                    }
                    Log.e("FF433G", "goTradeDetailBean.getData().getFreezeState():" + goTradeDetailBean.getData().getFreezeState());
//                    if (nftState != null) {
                    if (goTradeDetailBean.getData().getFreezeState() == 0) {//待交易
                        Log.e("FF433G", "liutong:" + liutong);
                        lin_min_jingpai.setVisibility(View.GONE);
                        tv_min_jingpai_name.setVisibility(View.GONE);
                        if(!TextUtils.isEmpty(goTradeDetailBean.getData().getCurrentPrice())) {
                            tv_min_jingpai.setText(String.format("%.6f", Double.valueOf(goTradeDetailBean.getData().getCurrentPrice())) + "");
                        }else {
                            tv_min_jingpai.setText("0.000000");
                        }
                        if (!TextUtils.isEmpty(liutong)) {//流通进来
                            lin_cehxiao_outprice_goumai.setVisibility(View.GONE);
                            yu.setVisibility(View.VISIBLE);
                            lin_outprice_goumai.setVisibility(View.GONE);
                            lin_max_price_time.setVisibility(View.GONE);
                            lin_max_jingjiazhe.setVisibility(View.GONE);
                            tv_first_name.setVisibility(View.GONE);
                            lin_wuliuxinxi.setVisibility(View.GONE);
                            tv_go_trade.setVisibility(View.GONE);
                        } else {
                            if (goTradeDetailBean.getData().getWalletAddr().equals(uniqueAdress)) {//自己的nft
                                my_nft_daijiaoyi.setVisibility(View.GONE);
                                lin_chexiao.setVisibility(View.GONE);
                                yu.setVisibility(View.GONE);
                            } else {//别人的nft

                                lin_chexiao.setVisibility(View.GONE);
                                lin_cehxiao_outprice_goumai.setVisibility(View.GONE);
                                yu.setVisibility(View.GONE);
                            }

                            my_nft_daijiaoyi.setVisibility(View.VISIBLE);
                            lin_max_price_time.setVisibility(View.GONE);
                            lin_max_jingjiazhe.setVisibility(View.GONE);
                            tv_first_name.setVisibility(View.GONE);
                            lin_wuliuxinxi.setVisibility(View.GONE);
                            tv_go_trade.setVisibility(View.GONE);
                            tv_paimai_or_outprice.setVisibility(View.GONE);
                        }

                    } else if (goTradeDetailBean.getData().getFreezeState() == 1) {//交易中
//                        lin_max_price_time.setVisibility(View.VISIBLE);
//                        yu.setVisibility(View.GONE);
//                        my_nft_daijiaoyi.setVisibility(View.GONE);

//                        lin_max_jingjiazhe.setVisibility(View.GONE);
//                        tv_first_name.setVisibility(View.GONE);
//                        lin_wuliuxinxi.setVisibility(View.GONE);
//                        tv_go_trade.setVisibility(View.GONE);
//                        tv_paimai_or_outprice.setVisibility(View.VISIBLE);

//                        if (!TextUtils.isEmpty(liutong)) {//流通进来
//                            lin_cehxiao_outprice_goumai.setVisibility(View.GONE);
//                            yu.setVisibility(View.VISIBLE);
//                            lin_outprice_goumai.setVisibility(View.GONE);
//                            my_nft_daijiaoyi.setVisibility(View.GONE);
//                            lin_chexiao.setVisibility(View.GONE);
//                            tv_first_name.setVisibility(View.GONE);
//                            lin_wuliuxinxi.setVisibility(View.GONE);
//                            tv_admin_shouhuo.setVisibility(View.GONE);
//                            tv_go_trade.setVisibility(View.GONE);
//                            lin_max_jingjiazhe.setVisibility(View.GONE);
//                        } else {
                            if (!TextUtils.isEmpty(collect) && collect.equals("collect")) {//收藏进来
                                lin_outprice_goumai.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(sellMode) && sellMode.equals("2")) {//竞拍
                                    tv_paimai_or_outprice.setVisibility(View.VISIBLE);
                                    yu.setVisibility(View.VISIBLE);
                                    if (goTradeDetailBean.getData().getFreezeState() == 1) {//锁定中=交易中
                                        lin_max_price_time.setVisibility(View.VISIBLE);
                                    } else {
                                        lin_max_price_time.setVisibility(View.GONE);
                                    }
                                    my_nft_daijiaoyi.setVisibility(View.GONE);
                                    lin_chexiao.setVisibility(View.GONE);
                                    tv_first_name.setVisibility(View.GONE);
                                    lin_wuliuxinxi.setVisibility(View.GONE);
                                    tv_admin_shouhuo.setVisibility(View.GONE);
                                    tv_go_trade.setVisibility(View.GONE);
                                    lin_max_jingjiazhe.setVisibility(View.GONE);
                                } else {//购买
                                    tv_paimai_or_outprice.setVisibility(View.VISIBLE);
                                    yu.setVisibility(View.VISIBLE);
                                    my_nft_daijiaoyi.setVisibility(View.GONE);
                                    lin_chexiao.setVisibility(View.GONE);

                                    if (!TextUtils.isEmpty(sellMode) && sellMode.equals("2")) {
                                        lin_max_jingjiazhe.setVisibility(View.VISIBLE);
                                        tv_first_name.setVisibility(View.VISIBLE);
                                    } else {
                                        lin_max_jingjiazhe.setVisibility(View.GONE);
                                        tv_first_name.setVisibility(View.GONE);
                                    }
                                    lin_wuliuxinxi.setVisibility(View.GONE);
                                    tv_admin_shouhuo.setVisibility(View.GONE);
                                    tv_go_trade.setVisibility(View.GONE);
                                }
                                lin_chexiao.setVisibility(View.GONE);
                            } else if(!TextUtils.isEmpty(my_bug)){
                                Log.e("FF433G","my_bug:" + my_bug);
                                lin_chexiao.setVisibility(View.VISIBLE);
                                my_nft_daijiaoyi.setVisibility(View.GONE);
                                tv_first_name.setVisibility(View.GONE);
                                lin_wuliuxinxi.setVisibility(View.GONE);
                                tv_admin_shouhuo.setVisibility(View.GONE);
                                tv_go_trade.setVisibility(View.GONE);
                                lin_max_jingjiazhe.setVisibility(View.GONE);
                                yu.setVisibility(View.GONE);
                            }else {
                                lin_outprice_goumai.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(sellMode) && sellMode.equals("2")) {//竞拍

                                    yu.setVisibility(View.VISIBLE);
                                    my_nft_daijiaoyi.setVisibility(View.GONE);

                                    tv_paimai_or_outprice.setVisibility(View.VISIBLE);
                                    if (goTradeDetailBean.getData().getFreezeState() == 1) {//锁定中=交易中
                                        lin_max_price_time.setVisibility(View.VISIBLE);
                                    } else {
                                        lin_max_price_time.setVisibility(View.GONE);
                                    }
                                    lin_chexiao.setVisibility(View.GONE);
                                    tv_first_name.setVisibility(View.GONE);
                                    lin_wuliuxinxi.setVisibility(View.GONE);
                                    tv_admin_shouhuo.setVisibility(View.GONE);
                                    tv_go_trade.setVisibility(View.GONE);
                                    lin_max_jingjiazhe.setVisibility(View.GONE);
                                } else {//购买
                                    if (goTradeDetailBean.getData().getWalletAddr().equals(uniqueAdress)) {//自己的nft
                                        yu.setVisibility(View.GONE);
                                        my_nft_daijiaoyi.setVisibility(View.GONE);
                                    } else {//别人的nft
                                        yu.setVisibility(View.VISIBLE);
                                        my_nft_daijiaoyi.setVisibility(View.GONE);
                                    }

                                    tv_paimai_or_outprice.setVisibility(View.VISIBLE);
                                    lin_chexiao.setVisibility(View.GONE);

                                    if (!TextUtils.isEmpty(sellMode) && sellMode.equals("2")) {
                                        lin_max_jingjiazhe.setVisibility(View.VISIBLE);
                                        tv_first_name.setVisibility(View.VISIBLE);
                                    } else {
                                        lin_max_jingjiazhe.setVisibility(View.GONE);
                                        tv_first_name.setVisibility(View.GONE);
                                    }
                                    lin_wuliuxinxi.setVisibility(View.GONE);
                                    tv_admin_shouhuo.setVisibility(View.GONE);
                                    tv_go_trade.setVisibility(View.GONE);
                                }
                                lin_chexiao.setVisibility(View.GONE);
                            }
//                        }
                        if(goTradeDetailBean.getData().getSellMode().equals("2")){//竞拍

                        }
                    }else
                    if (goTradeDetailBean.getData().getFreezeState() == 2) {//永久冻结中
                        lin_min_jingpai.setVisibility(View.GONE);
                        tv_min_jingpai_name.setVisibility(View.GONE);
                        if(!TextUtils.isEmpty(goTradeDetailBean.getData().getCurrentPrice())) {
                            tv_min_jingpai.setText(String.format("%.6f", Double.valueOf(goTradeDetailBean.getData().getCurrentPrice())) + "");
                        }else {
                            tv_min_jingpai.setText("0.000000");
                        }
                        if (!TextUtils.isEmpty(liutong)) {//流通进来
                            lin_cehxiao_outprice_goumai.setVisibility(View.GONE);
                            lin_outprice_goumai.setVisibility(View.GONE);
                            yu.setVisibility(View.VISIBLE);
                            lin_max_price_time.setVisibility(View.GONE);
                            lin_min_jingpai.setVisibility(View.GONE);
                            lin_max_price_time2.setVisibility(View.GONE);
                            my_nft_daijiaoyi.setVisibility(View.GONE);
                            lin_chexiao.setVisibility(View.GONE);
                            lin_max_jingjiazhe.setVisibility(View.GONE);
                            tv_first_name.setVisibility(View.GONE);
                            lin_wuliuxinxi.setVisibility(View.GONE);
                            tv_go_trade.setVisibility(View.GONE);
                            tv_paimai_or_outprice.setVisibility(View.GONE);
                        } else {
                            Log.e("FF433G","getTakeState:" + goTradeDetailBean.getData().getTakeState());
                            if (goTradeDetailBean.getData().getTakeState() == 1) {//已提货

//                                if (goTradeDetailBean.getData().getWalletAddr().equals(uniqueAdress)) {//自己的nft
//                                    yu.setVisibility(View.GONE);
//                                    my_nft_daijiaoyi.setVisibility(View.GONE);
//                                    tv_admin_shouhuo.setVisibility(View.VISIBLE);
//                                } else {//别人的nft
                                    yu.setVisibility(View.GONE);
                                    lin_cehxiao_outprice_goumai.setVisibility(View.GONE);
                                    my_nft_daijiaoyi.setVisibility(View.GONE);
                                    tv_admin_shouhuo.setVisibility(View.VISIBLE);
//                                }
                                lin_max_price_time.setVisibility(View.GONE);
                                lin_min_jingpai.setVisibility(View.GONE);
                                lin_max_price_time2.setVisibility(View.GONE);
                                my_nft_daijiaoyi.setVisibility(View.GONE);
                                lin_chexiao.setVisibility(View.GONE);
                                lin_max_jingjiazhe.setVisibility(View.GONE);
                                tv_first_name.setVisibility(View.GONE);
                                lin_wuliuxinxi.setVisibility(View.VISIBLE);
                                tv_go_trade.setVisibility(View.GONE);
                                tv_paimai_or_outprice.setVisibility(View.GONE);
                            } else if (goTradeDetailBean.getData().getTakeState() == 2) {//已收货

//                                if (goTradeDetailBean.getData().getWalletAddr().equals(uniqueAdress)) {//自己的nft
                                    yu.setVisibility(View.GONE);
//                                } else {//别人的nft
//                                    yu.setVisibility(View.VISIBLE);
//                                    lin_cehxiao_outprice_goumai.setVisibility(View.GONE);
//                                }

                                lin_max_price_time2.setVisibility(View.GONE);
                                lin_max_price_time.setVisibility(View.GONE);
                                lin_min_jingpai.setVisibility(View.GONE);
                                my_nft_daijiaoyi.setVisibility(View.GONE);
                                lin_chexiao.setVisibility(View.GONE);
                                lin_max_jingjiazhe.setVisibility(View.GONE);
                                tv_first_name.setVisibility(View.GONE);
                                tv_first_name.setVisibility(View.GONE);
                                lin_wuliuxinxi.setVisibility(View.VISIBLE);
                                tv_paimai_or_outprice.setVisibility(View.GONE);
                                tv_admin_shouhuo.setVisibility(View.GONE);
                                tv_go_trade.setVisibility(View.GONE);
                            }
                        }
                    }
//                    if (nftState != null) {
//                        if (nftState.equals("4")) {//去交易进来
//                            tv_paimai_or_outprice.setVisibility(View.GONE);
//                            lin_max_price_time.setVisibility(View.GONE);
//                            lin_min_jingpai.setVisibility(View.GONE);
//                            yu.setVisibility(View.GONE);
//                            my_nft_daijiaoyi.setVisibility(View.GONE);
//                            lin_chexiao.setVisibility(View.GONE);
//                            lin_max_jingjiazhe.setVisibility(View.GONE);
//                            tv_first_name.setVisibility(View.GONE);
//                            tv_first_name.setVisibility(View.GONE);
//                            lin_wuliuxinxi.setVisibility(View.GONE);
//                            tv_admin_shouhuo.setVisibility(View.GONE);
//                            tv_go_trade.setVisibility(View.VISIBLE);
//                        } else if (nftState.equals("5")) {//购买
//                            tv_paimai_or_outprice.setVisibility(View.VISIBLE);
//                            yu.setVisibility(View.VISIBLE);
//                            if (goTradeDetailBean.getData().getFreezeState() == 1) {//交易中
//                                lin_max_price_time.setVisibility(View.VISIBLE);
//                            } else {
//                                lin_max_price_time.setVisibility(View.GONE);
//                            }
//                            my_nft_daijiaoyi.setVisibility(View.GONE);
//                            lin_chexiao.setVisibility(View.GONE);
//
//
//                            if (!TextUtils.isEmpty(sellMode) && sellMode.equals("2")) {
//                                lin_max_jingjiazhe.setVisibility(View.VISIBLE);
//                                tv_first_name.setVisibility(View.VISIBLE);
//                            } else {
//                                lin_max_jingjiazhe.setVisibility(View.GONE);
//                                tv_first_name.setVisibility(View.GONE);
//                            }
//                            lin_wuliuxinxi.setVisibility(View.GONE);
//                            tv_admin_shouhuo.setVisibility(View.GONE);
//                            tv_go_trade.setVisibility(View.GONE);
//                        } else if (nftState.equals("6")) {//出价
//                            tv_paimai_or_outprice.setVisibility(View.VISIBLE);
//                            yu.setVisibility(View.VISIBLE);
//                            if (goTradeDetailBean.getData().getFreezeState() == 1) {//锁定中=交易中
//                                lin_max_price_time.setVisibility(View.VISIBLE);
//                            } else {
//                                lin_max_price_time.setVisibility(View.GONE);
//                            }
//                            my_nft_daijiaoyi.setVisibility(View.GONE);
//                            lin_chexiao.setVisibility(View.GONE);
//                            tv_first_name.setVisibility(View.GONE);
//                            lin_wuliuxinxi.setVisibility(View.GONE);
//                            tv_admin_shouhuo.setVisibility(View.GONE);
//                            tv_go_trade.setVisibility(View.GONE);
//                        }
//                    }
                } else {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
//            sendMeg();
            }
        }
    }

    private String getSubStr(String str) {
        String subStr1 = str.substring(0, 8);
        String subStr2 = str.substring(str.length() - 8, str.length());
        return subStr1 + "......" + subStr2;
    }

    Long timeOut;

    private Long maxZuiPrice(String auctionTime) {
        if (auctionTime != null && auctionTime.contains(",")) {
            String[] auctionTimeInterval = auctionTime.split(",");
            String h = auctionTimeInterval[0];
            String m = auctionTimeInterval[1];
            String s = auctionTimeInterval[2];
            timeOut = Long.parseLong(h) * 60 * 60 + Long.parseLong(m) * 60 + Long.parseLong(s);
        }
        return timeOut;
    }

    //持有者记录
    @Override
    public void loadData(GoTradeOwnerBean goTradeOwnerBean) {
        if (goTradeOwnerBean.getCode() == 200) {
            if (goTradeOwnerBean.getRows() != null) {
//                rows = goTradeOwnerBean.getRows();
//                tradeListRecordAdapter.setNewData(rows);
            }
        }
    }

    //质押保证金
    @Override
    public void loadData(GoTradeCationBean goTradeCationBean) {
        if (goTradeCationBean.getCode() == 200) {
            if (goTradeCationBean.getData() != null && !TextUtils.isEmpty(goTradeCationBean.getData().getCautionMoney())) {
                if (tv_pledge_deposit != null) {
                    tv_pledge_deposit.setText(goTradeCationBean.getData().getCautionMoney() + "");
                }
            }
        }
    }

    @Override
    public void loadFail() {
        dismissDialog();
    }

    //竞标
    @Override
    public void loadJingBiaoData(JingBiaoBean jingBiaoBean) {
        Log.e("D322S", "jingBiaoBean:" + new Gson().toJson(jingBiaoBean));
        if (jingBiaoBean.getCode() == 200) {
            if (jingBiaoBean.getRows() != null) {
//                rowsJing = jingBiaoBean.getRows();
//                tradeListJingBiaoAdapter.setNewData(rowsJing);
            }
        }


    }

    @Override
    public void loadJingBiaoFail() {
        dismissDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
            countDownTimer2 = null;
        }
        if (countDownTimer3 != null) {
            countDownTimer3.cancel();
            countDownTimer3 = null;
        }
        if (recAdapter != null) {
            recAdapter.close();
        }
        closeMeg();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
            countDownTimer2 = null;
        }
        if (countDownTimer3 != null) {
            countDownTimer3.cancel();
            countDownTimer3 = null;
        }
        if (recAdapter != null) {
            recAdapter.close();
        }
        closeMeg();
    }

    private void closeMeg() {
        NetClient.getInstance().register(this);
        SkbuffTwo skbuff = new SkbuffTwo();
        skbuff.setCmd("closegid");
        skbuff.setType("nft");
        skbuff.setFlags(NetConstant.NETHDR_FLAG_NONE);
        skbuff.setReserve(NetConstant.NETHDR_RESERVER);
        skbuff.setVersion(NetConstant.NETHDR_VERSION);
        skbuff.setMagic(NetConstant.NETHDR_MAGIC);

        SkbuffTwo.Data data = new SkbuffTwo.Data();
        SkbuffTwo.Data.Group group = new SkbuffTwo.Data.Group();
        group.setCoupleId(nftId);
        group.setWalletAddr(uniqueAdress);
        group.setDid(unique.getDid());
        group.setGid("g_nft_info");
        List<SkbuffTwo.Data.Group.ModuleList> moduleLists = new ArrayList<>();
        SkbuffTwo.Data.Group.ModuleList moduleList = new SkbuffTwo.Data.Group.ModuleList();
        moduleList.setModuleId("m_nft_holder_log");//持有者记录
        moduleList.setPageSize(5);
        moduleList.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList2 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList2.setModuleId("m_nft_auction_log");//竞标价格
        moduleList2.setPageSize(5);
        moduleList2.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList3 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList3.setModuleId("m_nft_max_price");//当前最高价
        moduleList3.setPageSize(5);
        moduleList3.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList4 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList4.setModuleId("m_nft_see_count");//浏览量
        moduleList4.setPageSize(5);
        moduleList4.setPage(0);
        SkbuffTwo.Data.Group.ModuleList moduleList5 = new SkbuffTwo.Data.Group.ModuleList();
        moduleList5.setModuleId("m_nft_max_confirmation");//最新出价
        moduleList5.setPageSize(5);
        moduleList5.setPage(0);

        moduleLists.add(moduleList);
        moduleLists.add(moduleList2);
        moduleLists.add(moduleList3);
        moduleLists.add(moduleList4);
        moduleLists.add(moduleList5);

        group.setModuleList(moduleLists);
        data.setGroup(group);
        skbuff.setData(data);
        Log.e("FF23444343", "sendData_close:" + skbuff.toString());

        NetClient.getInstance().sendTwo(skbuff);
    }

    //收藏成功
    @Override
    public void loadCollectionPostData(CollectionBean markOutPriceBean) {
        if (markOutPriceBean.getCode() == 200) {
            tv_colection.setText("取消收藏");
            img_collection.setImageDrawable(getResources().getDrawable(R.mipmap.collected));
            img_collection2.setImageDrawable(getResources().getDrawable(R.mipmap.collected));
            tv_collection_liu.setText("取消收藏");
        }
    }

    @Override
    public void loadCollectionPostFail() {

    }

    //取消收藏
    @Override
    public void loadUnCollectionPostData(CollectionBean markOutPriceBean) {
        Log.e("FSS23444", "取消收藏成功:" + new Gson().toJson(markOutPriceBean));
        if (markOutPriceBean.getCode() == 200) {
            tv_colection.setText("收藏");
            img_collection.setImageDrawable(getResources().getDrawable(R.mipmap.no_collect));
            img_collection2.setImageDrawable(getResources().getDrawable(R.mipmap.no_collect));
            tv_collection_liu.setText("收藏");
        }
    }

    @Override
    public void loadUnCollectionPostFail() {

    }

    //收货列表
    @Override
    public void loadShouHuoExpressData(ExpressBean expressBean) {
        if (expressBean != null && expressBean.getCode() == 200) {
            if (expressBean.getData() != null && expressBean.getData().getExpressInfoList() != null) {
                if (expressBean.getData().getExpressInfoList().size() > 0) {
                    rowsExpress.addAll(expressBean.getData().getExpressInfoList());
                    expressAdapter.setNewData(rowsExpress);
                    timeLineView.setTimelineCount(expressBean.getData().getExpressInfoList().size());
                    timeLineView.setTimelineRadiusDistance(170);


                    tv_shentongkuaidi.setText(expressBean.getData().getName());
                    Glide.with(this).load(expressBean.getData().getLogo()).into(img_kuaidi);


                }
            }
            if (!TextUtils.isEmpty(expressBean.getData().getNumber())) {
                if (img_kuaidi_copy != null) {
                    img_kuaidi_copy.setVisibility(View.VISIBLE);
                }
                tv_shentongkuaidi_num.setText(expressBean.getData().getNumber() + "");
            } else {
                if (img_kuaidi_copy != null) {
                    img_kuaidi_copy.setVisibility(View.GONE);
                }
            }
            if (expressBean.getData().getReceivingName() != null) {
                tv_concate.setText(expressBean.getData().getReceivingName() + "");
            }
            if (expressBean.getData().getReceivingPhone() != null) {
                tv_contact_phone.setText(expressBean.getData().getReceivingPhone() + "");
            }
            if (expressBean.getData().getReceivingAddr() != null) {
                tv_contact_address.setText(expressBean.getData().getReceivingAddr() + "");
            }
        }
    }

    @Override
    public void loadShouHuoExpressFail() {

    }
}

