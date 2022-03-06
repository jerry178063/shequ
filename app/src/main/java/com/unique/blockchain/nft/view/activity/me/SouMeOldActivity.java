package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.AllMyTradeAdapter;
import com.unique.blockchain.nft.adapter.TradingAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.trade.TradeListDetailActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMeMyNftSearchPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMeMyNftSearchPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMeMyNftSearchCallBack;
import com.unique.blockchain.nft.widget.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SouMeOldActivity extends BaseActivity implements IMeMyNftSearchCallBack {

    @BindView(R.id.img_search)
    LinearLayout img_search;
    @BindView(R.id.history_search_recycler)
    RecyclerView history_search_recycler;
    private AllMyTradeAdapter allMyTradeAdapter;
    private IMeMyNftSearchPresenter iMeMyNftSearchPresenter;
    private List<MarkHotBean.Rows> rows;
    private String key;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    public User unique;
    public  UserDao userDao;
    public  String uniqueAdress;
    @BindView(R.id.history_fl)
    ZFlowLayout historyFl;
    @BindView(R.id.lin_sousuo)
    LinearLayout lin_sousuo;
    @BindView(R.id.img_dele_record)
    ImageView img_dele_record;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sou;
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
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(SouMeOldActivity.this);
        history_search_recycler.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        allMyTradeAdapter = new AllMyTradeAdapter(R.layout.all_trade_item,rows,uniqueAdress);
        history_search_recycler.setAdapter(allMyTradeAdapter);
        iMeMyNftSearchPresenter = new IMeMyNftSearchPresenterImpl();
        iMeMyNftSearchPresenter.registerViewCallback(this);
        history_search_recycler.setAdapter(allMyTradeAdapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    tv_no_data.setVisibility(View.GONE);
                    lin_sousuo.setVisibility(View.VISIBLE);
                }
            }
        });
        img_dele_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouMeOldActivity.this).cleanHistoryMe();
                initHistory();
            }
        });
        initHistory();
    }
    private void initHistory() {
        final String[] data = com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouMeOldActivity.this).getHistoryListMe();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);

        historyFl.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (isNullorEmpty(data[i])) {

                return;
            }
            //有数据往下走
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            historyFl.addView(paramItemView, layoutParams);

            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isNullorEmpty(data[j])) {
                        if(data.length <= 10) {
                            com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouMeOldActivity.this).save(et_search.getText().toString());
                            iMeMyNftSearchPresenter.getData(1, 10, data[j] + "", uniqueAdress);
                        }

                    }
                    //点击事件
                }
            });
            if(i == 9){
                break;
            }
            // initautoSearch();
        }
    }
    private boolean isNullorEmpty(String str) {
        return str == null || "".equals(str);
    }
    @Override
    public void initData() {

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //搜索
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    showDialog();
                    if (iMeMyNftSearchPresenter != null) {
                        if (TextUtils.isEmpty(et_search.getText().toString())) {
                            ToastUtil.showShort(SouMeOldActivity.this, "请输入搜索内容!");
                            return;
                        }
                        if (!TextUtils.isEmpty(et_search.getText().toString())) {
                            String searchKey = et_search.getText().toString();
                            if (!isNullorEmpty(searchKey)) {

                                final String[] data = com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouMeOldActivity.this).getHistoryListMe();
                                //搜索
                                String keyWord = searchKey;
                                Log.e("FFDDD3","data:" + data);
                                if (!isNullorEmpty(keyWord)) {
                                    if(!TextUtils.isEmpty(data.toString()) && data.length < 10) {
                                        com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouMeOldActivity.this).saveMe(searchKey);
                                    }
                                    if(TextUtils.isEmpty(data.toString())){
                                        com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouMeOldActivity.this).saveMe(searchKey);
                                    }
                                }

                                initHistory();
                            } else {
                                //搜索为空
                            }
                        }
                        iMeMyNftSearchPresenter.getData(1, 10, et_search.getText().toString() + "", uniqueAdress);
                    }
                }
            }
        });
        allMyTradeAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    if (rows.get(position).getSellMode() == 1) {//购买
                        Intent intent = new Intent(SouMeOldActivity.this, BuyMarkActivity.class);
                        intent.putExtra("title", rows.get(position).getNftName());
                        intent.putExtra("nftId", rows.get(position).getNftId());
                        intent.putExtra("uniqueAdress", uniqueAdress);
                        intent.putExtra("productType", rows.get(position).getProductType() + "");
                        intent.putExtra("orderId",rows.get(position).getId() + "");
                        startActivity(intent);
                    } else if (rows.get(position).getSellMode() == 2) {//出价
                        Intent intent = new Intent(SouMeOldActivity.this, OutPriceActivity.class);
                        intent.putExtra("title", rows.get(position).getNftName());
                        intent.putExtra("nftId", rows.get(position).getNftId());
                        intent.putExtra("uniqueAdress", uniqueAdress);
                        intent.putExtra("productType", rows.get(position).getProductType() + "");
                        intent.putExtra("orderId",rows.get(position).getId() + "");
                        startActivity(intent);
                    }
                }
            }
        });
        allMyTradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(SouMeOldActivity.this, PaiActivity.class);
                    intent.putExtra("nftId", rows.get(i).getNftId() + "");
                    intent.putExtra("isTrasfer", rows.get(i).getIsTransfer() + "");
                    intent.putExtra("surplus", rows.get(i).getSurplus() + "");
                    intent.putExtra("orderId",rows.get(i).getId() + "");
                    SouMeOldActivity.this.startActivity(intent);
                }
            }
        });
        //转移
        allMyTradeAdapter.setTransfer(new AllMyTradeAdapter.Transfer() {
            @Override
            public void Transfer(int position, MarkHotBean.Rows item) {
                Intent intent = new Intent(SouMeOldActivity.this, NftTransferActivity.class);
                intent.putExtra("nftId", item.getNftId());
                intent.putExtra("cover", item.getProductCover());
                intent.putExtra("type", item.getProductType() + "");
                intent.putExtra("name", item.getProductName());
                intent.putExtra("intro", item.getProductIntro());
                startActivity(intent);
            }
        });
        //撤销交易
        allMyTradeAdapter.setCancelTrade(new TradingAdapter.CancelTrade() {
            @Override
            public void CancelTrade(int position, MarkHotBean.Rows item) {
                Log.e("FF3454","type:" + item.getProductType());
                Intent intent = new Intent(SouMeOldActivity.this, NftCancelBackActivity.class);
                intent.putExtra("nftId", item.getNftId());
                intent.putExtra("cover", item.getProductCover());
                intent.putExtra("type", item.getProductType() + "");
                intent.putExtra("name", item.getProductName());
                intent.putExtra("intro", item.getProductIntro());
                intent.putExtra("orderId",item.getId() + "");
                startActivity(intent);
            }
        });
        //去交易
        allMyTradeAdapter.setGoTransfer(new AllMyTradeAdapter.GoTransfer() {
            @Override
            public void GoTransfer(int position, MarkHotBean.Rows item) {
                Intent intent = new Intent(SouMeOldActivity.this, TradeListDetailActivity.class);
                intent.putExtra("nftId", item.getNftId() + "");
                intent.putExtra("companyId", uniqueAdress + "");
                intent.putExtra("orderId",item.getId() + "");
                startActivity(intent);
            }
        });
        //提货
        allMyTradeAdapter.setTiHuo(new AllMyTradeAdapter.TiHuo() {
            @Override
            public void TiHuo(int position, MarkHotBean.Rows item) {
                Intent intent = new Intent(SouMeOldActivity.this, NftTiHuoActivity.class);
                intent.putExtra("img", item.getProductImage());
                intent.putExtra("name", item.getProductName());
                intent.putExtra("nftId", item.getNftId() + "");
                intent.putExtra("productType",item.getProductType() + "");
                intent.putExtra("orderId",item.getId() + "");
                startActivity(intent);
            }
        });
        allMyTradeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(SouMeOldActivity.this, PaiActivity.class);
                intent.putExtra("nftId", rows.get(i).getNftId() + "");
                intent.putExtra("nftState", rows.get(i).getNftState());
                intent.putExtra("surplus",rows.get(i).getSurplus() + "");
                intent.putExtra("orderId",rows.get(i).getId() + "");
                SouMeOldActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public void loadMyNftSearchData(MarkHotBean quanDatabase) {
        dismissDialog();

        if(quanDatabase != null && quanDatabase.getCode() == 200){
            if (rows != null) {
                rows.clear();
            }
            if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                history_search_recycler.setVisibility(View.VISIBLE);
                if(tv_no_data != null) {
                    tv_no_data.setVisibility(View.GONE);
                }
                lin_sousuo.setVisibility(View.GONE);
                rows.addAll(quanDatabase.getRows());
                allMyTradeAdapter.notifyDataSetChanged();

            } else {
                tv_no_data.setVisibility(View.VISIBLE);
                lin_sousuo.setVisibility(View.GONE);
            }
        }
        allMyTradeAdapter.notifyDataSetChanged();
        allMyTradeAdapter.setNewData(rows);
    }

    @Override
    public void loadMyNftSearchFail() {
        dismissDialog();
    }
}