package com.unique.blockchain.nft.view.activity.mark;

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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MarkCollectAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.SPUtils;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.me.SouMeActivity;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeCollectionPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeCollectionPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeCollectionCallBack;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkSearchPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkSearchPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkCollectCallBack;
import com.unique.blockchain.nft.widget.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SouCollectActivity extends BaseActivity implements IMeCollectionCallBack {

    @BindView(R.id.img_search)
    LinearLayout img_search;
    @BindView(R.id.history_search_recycler)
    RecyclerView history_search_recycler;
    private MarkCollectAdapter markCollectAdapter;
    private List<QuanDatabase.RowsBean> rowsShoucang;
    private IMeCollectionPresenter iMarkSearchPresenter;
    private String key;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    String collect,uniqueAdress;
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
        if (Build.VERSION.SDK_INT >= 21) {//??????????????????
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
        collect = getIntent().getStringExtra("collect");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        history_search_recycler.setLayoutManager(gridLayoutManager);
        rowsShoucang = new ArrayList<>();
        markCollectAdapter =  new MarkCollectAdapter(R.layout.search, rowsShoucang);
        iMarkSearchPresenter = new IMeCollectionPresenterImpl();
        iMarkSearchPresenter.registerViewCallback(this);
        history_search_recycler.setAdapter(markCollectAdapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MoreClick.MoreClicks()) {
                    if (TextUtils.isEmpty(s.toString())) {
                        tv_no_data.setVisibility(View.GONE);
                        lin_sousuo.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        img_dele_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    SPUtils.getInstance(SouCollectActivity.this).cleanHistoryCollect();
                    initHistory();
                }
            }
        });
        markCollectAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                if (MoreClick.MoreClicks()) {
                    if (rowsShoucang.get(position).getSellMode() == 1) {//??????
                        Intent intent = new Intent(SouCollectActivity.this, BuyMarkActivity.class);
                        intent.putExtra("title", rowsShoucang.get(position).getNftName());
                        intent.putExtra("nftId", rowsShoucang.get(position).getNftId());
                        intent.putExtra("uniqueAdress", rowsShoucang.get(position).getWalletAddr());
                        intent.putExtra("productType", rowsShoucang.get(position).getProductType() + "");
                        startActivity(intent);
                    } else if (rowsShoucang.get(position).getSellMode() == 2) {//??????
                        Intent intent = new Intent(SouCollectActivity.this, OutPriceActivity.class);
                        intent.putExtra("title", rowsShoucang.get(position).getNftName());
                        intent.putExtra("nftId", rowsShoucang.get(position).getNftId());
                        intent.putExtra("productType", rowsShoucang.get(position).getProductType() + "");
                        intent.putExtra("uniqueAdress", rowsShoucang.get(position).getWalletAddr());
                        startActivity(intent);
                    }
                }
            }
        });
        initHistory();
    }

    private void initHistory() {
        final String[] data = SPUtils.getInstance(SouCollectActivity.this).getHistoryListCollect();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);

        historyFl.removeAllViews();
//        for (int i = 0; i < data.length; i++) {
        if(data.length > 10){
            for (int i = data.length - 1; i >= 0 + (data.length - 10); i--) {
                if (isNullorEmpty(data[i])) {

                    return;
                }
                //??????????????????
                final int j = i;
                //???????????????
                View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
                TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
                keyWordTv.setText(data[i]);
                historyFl.addView(paramItemView, layoutParams);

                keyWordTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNullorEmpty(data[j])) {
                            SPUtils.getInstance(SouCollectActivity.this).save(et_search.getText().toString());
                            iMarkSearchPresenter.getData(data[j], uniqueAdress, 1, 10);
                        }
                        //????????????
                    }
                });
            }
        }else {
            for (int i = data.length - 1; i >= 0; i--) {
                if (isNullorEmpty(data[i])) {

                    return;
                }
                //??????????????????
                final int j = i;
                //???????????????
                View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
                TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
                keyWordTv.setText(data[j]);
                historyFl.addView(paramItemView, layoutParams);

                keyWordTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isNullorEmpty(data[j])) {
                            SPUtils.getInstance(SouCollectActivity.this).save(et_search.getText().toString());
                            iMarkSearchPresenter.getData(data[j], uniqueAdress, 1, 10);
                        }
                        //????????????
                    }
                });
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
        uniqueAdress = getIntent().getStringExtra("uniqueAdress");
        //??????
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMarkSearchPresenter != null){
                    if(TextUtils.isEmpty(et_search.getText().toString())){
                        ToastUtil.showShort(SouCollectActivity.this,"?????????????????????!");
                        return;
                    }
                    if(!TextUtils.isEmpty(et_search.getText().toString())) {
                        String searchKey = et_search.getText().toString();
                        if (!isNullorEmpty(searchKey)) {
                            final String[] data = com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouCollectActivity.this).getHistoryListCollect();
                            //??????
                            String keyWord = searchKey;
                            Log.e("FFDDD3","data:" + data);
                            if (!isNullorEmpty(keyWord)) {
                                    com.unique.blockchain.nft.infrastructure.utils.SPUtils.getInstance(SouCollectActivity.this).saveCollect(searchKey);
                            }

                            initHistory();
                        } else {
                            //????????????
                        }
                    }
                        iMarkSearchPresenter.getData(et_search.getText().toString(), uniqueAdress, 1,10);
                }
            }
        });
    }

    @Override
    public void bindView(Bundle bundle) {

    }


    @Override
    public void loadCollectionData(QuanDatabase quanDatabase) {
        dismissDialog();
        if (quanDatabase != null && quanDatabase.getCode() == 200) {
            Log.e("FF433", "quanDatabase:" + new Gson().toJson(quanDatabase));
            if (rowsShoucang != null) {
                rowsShoucang.clear();
            }
            if (quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {
                rowsShoucang.addAll(quanDatabase.getRows());
                tv_no_data.setVisibility(View.GONE);
                lin_sousuo.setVisibility(View.GONE);
            }else {
                if(tv_no_data != null){
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                lin_sousuo.setVisibility(View.GONE);
            }
            markCollectAdapter.notifyDataSetChanged();
            markCollectAdapter.setNewData(rowsShoucang);
        }
    }

    @Override
    public void loadCollectionFail() {
        dismissDialog();
    }
}