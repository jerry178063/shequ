package com.unique.blockchain.nft.view.activity.trade;

import android.os.Bundle;
import android.view.View;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/***
 *
 * 发起投票
 * */
public class StartVoteActivity extends BaseActivity implements AddtionalAssetsDialog.OnClickView , SafeAdminDialog.OnSafeClickView{

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_start;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("发起投票");
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
    @OnClick({R.id.tv_start_vote,R.id.tv_start_zanzhu})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_start_vote://发起投票
                if (addtionalAssetsDialog == null) {
                    addtionalAssetsDialog = new AddtionalAssetsDialog(this);
                    addtionalAssetsDialog.setOnclick(this);
                    addtionalAssetsDialog.show();
                } else {
                    addtionalAssetsDialog.show();
                }
                break;
            case R.id.tv_start_zanzhu://发起赞助
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
