package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.node.GetValiAddressBean;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 去委托详情
 * */
public class GoCommissionDetailActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    private GetValiAddressBean info;
    private String strInfo;
    @BindView(R.id.tv_ziya_num)
    TextView tv_ziya_num;
    @BindView(R.id.tv_year_rate)
    TextView tv_year_rate;
    @BindView(R.id.tv_min_commision)
    TextView tv_min_commision;
    @BindView(R.id.tv_get_all_monery)
    TextView tv_get_all_monery;
    @BindView(R.id.tv_gauss_ads)
    TextView tv_gauss_ads;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    String peopleNum,upbondData,allMonery,rootAddress,rootAlletAddress,yearRate,isRoot;
    @BindView(R.id.tv_weituo_num)
    TextView tv_weituo_num;
    @BindView(R.id.tv_shuhui)
    TextView tv_shuhui;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gocommission_detail;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("详情");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        strInfo = getIntent().getStringExtra("info");
        peopleNum = getIntent().getStringExtra("peopleNum");
        upbondData = getIntent().getStringExtra("upbondData");
        allMonery = getIntent().getStringExtra("allMonery");
        rootAddress = getIntent().getStringExtra("rootAddress");
        rootAlletAddress = getIntent().getStringExtra("rootAlletAddress");
        yearRate = getIntent().getStringExtra("yearRate");
        isRoot = getIntent().getStringExtra("isRoot");
        info = new Gson().fromJson(strInfo,GetValiAddressBean.class);
        if(!TextUtils.isEmpty(strInfo) && info != null && info.getValidators() != null) {
            for (int i = 0; i < info.getValidators().size(); i++) {
                if(rootAddress.equals(info.getValidators().get(i).getOperator_address())) {
                    tv_ziya_num.setText(String.format("%.6f",Double.valueOf(info.getValidators().get(i).getTokens())/1000000) +  "");

                    if(!TextUtils.isEmpty(yearRate)) {
                        BigDecimal bd = new BigDecimal(yearRate + "");
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

                        tv_year_rate.setText("≈ " + bd + "%");
                    }
                    tv_min_commision.setText(info.getValidators().get(i).getMin_self_delegation() + "");

                    if(!TextUtils.isEmpty(info.getValidators().get(i).getOperator_address())) {
                        tv_gauss_ads.setText(info.getValidators().get(i).getOperator_address());
                    }
                    if(!TextUtils.isEmpty(info.getValidators().get(i).getDescription().getMoniker())) {
                        tv_title_name.setText(info.getValidators().get(i).getDescription().getMoniker());
                    }


                    if(!TextUtils.isEmpty(info.getValidators().get(i).getOperator_address())) {
                        String replace =info.getValidators().get(i).getOperator_address().substring(7,42);
                        tv_gauss_ads.setText(info.getValidators().get(i).getOperator_address().replace(replace , "****" ));
                    }
                }
            }
        }
        if(!TextUtils.isEmpty(peopleNum)) {
            tv_weituo_num.setText(peopleNum + "");
        }else {
            tv_weituo_num.setText("0");
        }
        if(!TextUtils.isEmpty(upbondData)) {
            tv_shuhui.setText(upbondData + "");
        }else {
            tv_shuhui.setText("0");
        }
        if(!TextUtils.isEmpty(allMonery)) {
            tv_get_all_monery.setText(String.format("%.6f",Double.valueOf(allMonery)/1000000) +  "");
        }else {
            tv_get_all_monery.setText("0");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.tv_go_commission})
    public void setOnclick(View view) {
            switch (view.getId()) {

                case R.id.tv_go_commission://委托
                    Intent intent = new Intent(GoCommissionDetailActivity.this, AdminCommissionActivity.class);
                    intent.putExtra("title", tv_title_name.getText().toString() + "");
                    intent.putExtra("address", rootAddress + "");
                    intent.putExtra("isRoot",isRoot);
                    startActivity(intent);
                    finish();
                    break;

            }
    }

}
