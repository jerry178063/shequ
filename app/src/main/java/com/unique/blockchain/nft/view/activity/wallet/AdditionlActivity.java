package com.unique.blockchain.nft.view.activity.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 销毁资产
 * */
public class AdditionlActivity extends BaseActivity implements AddtionalAssetsDialog.OnClickView , SafeAdminDialog.OnSafeClickView {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_additional)
    TextView tv_additional;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_additonl;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("增发资产");
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
    @OnClick({R.id.tv_additional})
    public void setOnclick(View view) {
//        if(FastClick.isFastClick()) {
            switch (view.getId()) {
                case R.id.tv_additional://输入后确定

                    if (addtionalAssetsDialog == null) {
                        addtionalAssetsDialog = new AddtionalAssetsDialog(this);
                        addtionalAssetsDialog.setOnclick(this);
                        addtionalAssetsDialog.show();
                    } else {
                        addtionalAssetsDialog.show();
                    }
                    break;
            }
//        }
    }

    @Override
    public void setOnClickView() {//输入数量确认
        addtionalAssetsDialog.dismiss();

        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }

    @Override
    public void setOnSafeClickView(String pass) {//身份安全确认
        safeAdminDialog.dismiss();
    }
}
