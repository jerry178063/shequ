package com.unique.blockchain.nft.view.activity.wallet;

import android.os.Bundle;
import android.view.View;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 销毁资产
 * */
public class DestroyAsstesActivity extends BaseActivity implements  SafeAdminDialog.OnSafeClickView {


    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_destroy;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("销毁资产");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }
    AddtionalAssetsDialog addtionalAssetsDialog;
    SafeAdminDialog safeAdminDialog;
    @OnClick({R.id.tv_destroy})
    public void setOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_destroy://输入后确定

                if (safeAdminDialog == null) {
                    safeAdminDialog = new SafeAdminDialog(this);
                    safeAdminDialog.setOnclick(this);
                    safeAdminDialog.show();
                } else {
                    safeAdminDialog.show();
                }
                break;
        }
    }


    @Override
    public void setOnSafeClickView(String pass) {//身份安全确认
        safeAdminDialog.dismiss();
        finish();
    }
}
