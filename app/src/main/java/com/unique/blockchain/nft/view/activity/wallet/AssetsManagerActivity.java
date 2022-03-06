package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 钱包-合约资产-资产管理
 */
public class AssetsManagerActivity extends BaseActivity {
    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_additional)
    TextView tv_additional;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_manager;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("资产管理");
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

    @OnClick({R.id.tv_additional,R.id.tv_destroy,R.id.tv_unlock,R.id.tv_transfer_owner})
    public void setOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_additional://增发
                Intent intent = new Intent(AssetsManagerActivity.this,AdditionlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_destroy://销毁
                Intent intent_destroy = new Intent(AssetsManagerActivity.this,DestroyAsstesActivity.class);
                startActivity(intent_destroy);
                break;
            case R.id.tv_unlock://解锁
                Intent intent_unlock = new Intent(AssetsManagerActivity.this,UnLockAsstesActivity.class);
                startActivity(intent_unlock);
                break;
            case R.id.tv_transfer_owner://转移拥有者
                Intent intent_transfer = new Intent(AssetsManagerActivity.this,TransferOwnerAsstesActivity.class);
                startActivity(intent_transfer);
                break;
        }
    }
}
