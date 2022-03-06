package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeGetCompanyInfoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeGetPanyInfoPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeGetCompanyInfoCallBack;

import butterknife.OnClick;

/**
 * 认证数字店铺 Activity类
 *
 * @author admin
 */
public class DigitalStoreActivity extends BaseActivity implements IMeGetCompanyInfoCallBack {


    private LinearLayout mRenFan;
    private ImageView mRemenFanhui;
    private RadioButton mRenKaiqi;
    private LinearLayout mKaiRenzheng;
    IMeGetCompanyInfoPresenter iMeGetCompanyInfoPresenter;
    String uniqueAdress;
    private int isRegister;
    private String dataStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_digital_store;
    }

    /**
     * 初始化视图方法
     */
    private void initView() {
        mRenFan = findViewById(R.id.cha_item);
        //mRenKaiqi = findViewById(R.id.ren_kaiqi);
        mKaiRenzheng = findViewById(R.id.kai_renzheng);

        // 开启NFT神奇之旅按钮点击事件
        mKaiRenzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegister == 0) {//注册
                    Intent intent = new Intent(DigitalStoreActivity.this, RegisterDigitalStoreActivity.class);
                    intent.putExtra("dataStr",dataStr + "");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(DigitalStoreActivity.this, RegisterDigitalResultActivity.class);
                    intent.putExtra("dataStr",dataStr + "");
                    startActivity(intent);
                    finish();
                }
            }
        });

        mRenFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({})
    public void setOnclick(View view) {
        switch (view.getId()) {

        }
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
        // 初始化视图
        initView();
    }

    @Override
    public void initData() {
        uniqueAdress = getIntent().getStringExtra("uniqueAdress");
        if (iMeGetCompanyInfoPresenter == null) {
            iMeGetCompanyInfoPresenter = new IMeGetPanyInfoPresenterImpl();
            iMeGetCompanyInfoPresenter.registerViewCallback(this);
        }
        iMeGetCompanyInfoPresenter.getData(uniqueAdress);
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public void loadGetCompanyInfoPostData(CompanyInfoBean companyInfoBean) {
        if (companyInfoBean != null && companyInfoBean.getCode() == 200) {
            if (companyInfoBean.getData() == null) {
                isRegister = 0;
            } else {
                isRegister = 1;
                dataStr = companyInfoBean.toString();
            }
        }

    }

    @Override
    public void loadGetCompanyInfoPostFail() {

    }
}