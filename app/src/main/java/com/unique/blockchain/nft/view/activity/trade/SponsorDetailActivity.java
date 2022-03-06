package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
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
 * 赞助区详情
 */
public class SponsorDetailActivity extends BaseActivity implements AddtionalAssetsDialog.OnClickView , SafeAdminDialog.OnSafeClickView{

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sponsor_area_detail;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("详情");
        top_s_title_toolbar.setRightTitleText("投票规则");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        top_s_title_toolbar.mTxtRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SponsorDetailActivity.this,VoteRuleActivity.class);
                startActivity(intent);
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
    @OnClick({R.id.tv_sponsor})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_sponsor://赞助

                if (addtionalAssetsDialog == null) {
                    addtionalAssetsDialog = new AddtionalAssetsDialog(this);
                    addtionalAssetsDialog.setOnclick(this);
                    addtionalAssetsDialog.show();
                } else {
                    addtionalAssetsDialog.show();
                }

                break;
        }
    }

    @Override
    public void setOnClickView() {
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
    public void setOnSafeClickView(String pass) {
        safeAdminDialog.dismiss();
    }
}
