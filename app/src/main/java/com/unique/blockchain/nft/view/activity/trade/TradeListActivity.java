package com.unique.blockchain.nft.view.activity.trade;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.TradeListOwerAdapter;
import com.unique.blockchain.nft.adapter.TradeListPowerAdapter;
import com.unique.blockchain.nft.adapter.TradeListRecordAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.BannerDetailAdapter;
import com.unique.blockchain.nft.domain.trade.GoTradeCationBean;
import com.unique.blockchain.nft.domain.trade.GoTradeDetailBean;
import com.unique.blockchain.nft.domain.trade.GoTradeOwnerBean;
import com.unique.blockchain.nft.infrastructure.utils.Share2;
import com.unique.blockchain.nft.infrastructure.utils.ShareContentType;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.fragment.trade.presenter.IOwnerPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeCationPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeDetailPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeCationPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeDetailPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeOwnerPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeCationCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeListDetailCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeOwnerCallbask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 去交易-票务等
 */
public class TradeListActivity extends BaseActivity implements ITradeListDetailCallbask, ITradeOwnerCallbask, ITradeCationCallbask {

    @BindView(R.id.tv_go_trade)
    TextView tv_go_trade;
    @BindView(R.id.recyclewTwo)
    RecyclerView recyclewTwo;
    @BindView(R.id.recyclewRecord)
    RecyclerView recyclewRecord;
    @BindView(R.id.img_share)
    ImageView img_share;
    @BindView(R.id.img_detail_back)
    ImageView img_detail_back;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_seecount)
    TextView tv_seecount;
    @BindView(R.id.tv_nftid)
    TextView tv_nftid;
    @BindView(R.id.tv_seat_rows)
    TextView tv_seat_rows;
    @BindView(R.id.tv_freezeState)
    TextView tv_freezeState;
    @BindView(R.id.tv_royalty)
    TextView tv_royalty;
    @BindView(R.id.tv_productVat)
    TextView tv_productVat;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.tv_zzz_adress)
    TextView tv_zzz_adress;
    @BindView(R.id.tv_owner_adress)
    TextView tv_owner_adress;
    String companyId;
    @BindView(R.id.tv_cation_monery)
    TextView tv_cation_monery;
    @BindView(R.id.tv_copy_own)
    TextView tv_copy_own;
    @BindView(R.id.tv_copy_zz)
    TextView tv_copy_zz;

    private List<String> list = new ArrayList<>();
    private TradeListOwerAdapter tradeListAdapter = new TradeListOwerAdapter(R.layout.fragment_trade_list_two_item, list);

    List<String> proveUrls = new ArrayList<>();
    private TradeListPowerAdapter tradeListPowerAdapter = new TradeListPowerAdapter(this, R.layout.fragment_trade_power_item, proveUrls);

    private List<String> list3 = new ArrayList<>();
    private List<GoTradeOwnerBean.Rows> rows;
    private TradeListRecordAdapter tradeListRecordAdapter = new TradeListRecordAdapter(R.layout.fragment_trade_record_item, rows);
    private String productType, productName, nftId;
    private ITradeDetailPresenter iTradeDetailPresenter;
    private GoTradeDetailBean mgoTradeDetailBean;
    private IOwnerPresenter iOwnerPresenter;
    private ITradeCationPresenter iTradeCationPresenter;
    boolean isPull;

    @BindView(R.id.liu_RecyclerView)
    RecyclerView liu_RecyclerView;

    private List<BannerDatabase.DataBean> data = new ArrayList<>();//广告
    List<String> bannImg = new ArrayList<>();//顶部滚动图片
    private BannerDetailAdapter recAdapter;
    private List<BannerDatabase.DataBean>listData=new ArrayList<>();
    private String orderId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tradefragment_list;
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
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        productType = getIntent().getStringExtra("productType");
        productName = getIntent().getStringExtra("productName");
        nftId = getIntent().getStringExtra("nftId");
        companyId = getIntent().getStringExtra("companyId");

        Log.e("FD32211", "nftId:" + nftId);
        iTradeDetailPresenter = new ITradeDetailPresenterImpl();
        iTradeDetailPresenter.registerViewCallback(this);
        iTradeDetailPresenter.getData(1, "",nftId,"");

        iOwnerPresenter = new ITradeOwnerPresenterImpl();
        iOwnerPresenter.registerViewCallback(this);
        iOwnerPresenter.getData(1, nftId);

        iTradeCationPresenter = new ITradeCationPresenterImpl();
        iTradeCationPresenter.registerViewCallback(this);
        iTradeCationPresenter.getData(1, companyId);


        tv_name.setText(productName + "");
        if (productType.equals("1")) {//票务
            tv_type.setText("票务");
        } else if (productType.equals("2")) {//收藏品
            tv_type.setText("收藏品");
        } else if (productType.equals("3")) {//艺术品
            tv_type.setText("艺术品");
        } else if (productType.equals("4")) {//轻奢品
            tv_type.setText("轻奢品");
        }
//        CommonUtil.setStatusBarTextColor(this, 1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclewTwo.setLayoutManager(gridLayoutManager);
        recyclewTwo.setAdapter(tradeListPowerAdapter);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        recyclewRecord.setLayoutManager(linearLayoutManager3);
        recyclewRecord.setAdapter(tradeListRecordAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        liu_RecyclerView.setLayoutManager(linearLayoutManager);
        recAdapter = new BannerDetailAdapter(listData,this);
        liu_RecyclerView.setAdapter(recAdapter);
        if(data != null){
            data.clear();
        }
    }

    private Animation backInAnimation;

    @Override
    public void initData() {


    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @OnClick({R.id.tv_go_trade, R.id.img_share, R.id.img_detail_back, R.id.tv_copy_own, R.id.tv_copy_zz})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_go_trade:
                Intent intent = new Intent(TradeListActivity.this, TradeListDetailActivity.class);
                intent.putExtra("nftId", nftId + "");
                intent.putExtra("companyId", companyId);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                break;
            case R.id.img_share://分享
                new Share2.Builder(this)
                        .setContentType(ShareContentType.TEXT)
                        .setTextContent("This is a test message.")
                        .setTitle("Share Text")
                        // .forcedUseSystemChooser(false)
                        .build()
                        .shareBySystem();
                break;
            case R.id.img_detail_back://返回
                finish();
                break;
            case R.id.tv_copy_own://复制拥有者
                if (!TextUtils.isEmpty(tv_owner_adress.getText())) {
                    copyContentToClipboard(tv_owner_adress.getText().toString() + "", TradeListActivity.this);
                    ToastUtil.showShort(TradeListActivity.this, "复制成功!");
                }
                break;
            case R.id.tv_copy_zz://复制铸造者
                if (!TextUtils.isEmpty(tv_zzz_adress.getText())) {
                    copyContentToClipboard(tv_zzz_adress.getText().toString() + "", TradeListActivity.this);
                    ToastUtil.showShort(TradeListActivity.this, "复制成功!");
                }

                break;

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
    public void loadData(GoTradeDetailBean goTradeDetailBean) {
        if (goTradeDetailBean != null && goTradeDetailBean.getCode() == 200) {
            mgoTradeDetailBean = goTradeDetailBean;
            tv_seecount.setText("浏览量: " + goTradeDetailBean.getData().getSeeCount());
            tv_nftid.setText(goTradeDetailBean.getData().getNftId());
            tv_seat_rows.setText(" " + goTradeDetailBean.getData().getSeveralRows() + "排" + goTradeDetailBean.getData().getSeatNumber() + "座");
            if (goTradeDetailBean.getData().getFreezeState() == 0) {
                tv_freezeState.setText("流通中");
            } else if (goTradeDetailBean.getData().getFreezeState() == 1) {
                tv_freezeState.setText("锁定中");
            } else if (goTradeDetailBean.getData().getFreezeState() == 2) {
                tv_freezeState.setText("永久冻结");
            }
            tv_royalty.setText(goTradeDetailBean.getData().getRoyalty() + "%");
            tv_productVat.setText(goTradeDetailBean.getData().getProductVat() + "%");
            tv_remark.setText(goTradeDetailBean.getData().getProductIntro() + "");
            if (goTradeDetailBean.getData().getWalletAddr() != null && goTradeDetailBean.getData().getWalletAddr().length() > 38) {
                String replace = goTradeDetailBean.getData().getWalletAddr().substring(7, 38);
                tv_zzz_adress.setText(goTradeDetailBean.getData().getWalletAddr().replace(replace, "****"));
            }
            if (goTradeDetailBean.getData().getOwnerWallet() != null && goTradeDetailBean.getData().getOwnerWallet().length() > 38) {
                String owner = goTradeDetailBean.getData().getOwnerWallet().substring(7, 38);
                tv_owner_adress.setText(goTradeDetailBean.getData().getOwnerWallet().replace(owner, "****"));
            }
            String proveUrl = goTradeDetailBean.getData().getProveUrl();
            if (!TextUtils.isEmpty(proveUrl)) {
                if (!proveUrl.contains(",")) {
                    proveUrls.add(proveUrl);
                } else {
                    proveUrls = Arrays.asList(proveUrl.split(","));
                }
                Log.e("FF344", "proveUrls:" + proveUrls);

                tradeListPowerAdapter.setNewData(proveUrls);
            }

            BannerDatabase.DataBean dataBean = new BannerDatabase.DataBean();
            if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductImage())) {
                if (!goTradeDetailBean.getData().getProductImage().contains(",")) {
//                        bannImg.add(goTradeDetailBean.getData().getProductImage());
                    dataBean.setImageAddress(goTradeDetailBean.getData().getProductImage());
                } else {
                    if(bannImg !=null) {
                        bannImg.clear();
                    }
                    bannImg = Arrays.asList(goTradeDetailBean.getData().getProductImage().split(","));
                    if(bannImg !=null) {
                        for (int i = 0; i < bannImg.size(); i++) {
                            dataBean.setImageAddress(bannImg.get(i));
                        }
                    }
                }
                Log.e("FF344", "proveUrls:" + proveUrls);
                tradeListPowerAdapter.setNewData(proveUrls);
            }
            data.add(dataBean);
            recAdapter.setBannerl(data);//大图片
        }
    }

    //持有者
    @Override
    public void loadData(GoTradeOwnerBean goTradeBean) {

        if (goTradeBean.getCode() == 200) {
            if (goTradeBean.getRows() != null) {
                rows = goTradeBean.getRows();
                tradeListRecordAdapter.setNewData(rows);
            }
        }


    }

    //质押保证金
    @Override
    public void loadData(GoTradeCationBean goTradeCationBean) {
        if (goTradeCationBean.getCode() == 200) {
            if (goTradeCationBean.getData() != null) {
                tv_cation_monery.setText(goTradeCationBean.getData().getCautionMoney() + "");
            }
        }

    }

    @Override
    public void loadFail() {

    }
}
