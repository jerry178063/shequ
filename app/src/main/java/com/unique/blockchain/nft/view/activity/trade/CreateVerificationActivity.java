package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 成为验证人
 * */
public class CreateVerificationActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    private String gauss_monery;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_verfication;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("成为验证人");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gauss_monery = getIntent().getStringExtra("gauss_monery");
    }
    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.tv_has_read})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_has_read://已阅读并同意

                Intent intent = new Intent(CreateVerificationActivity.this, CreateRealVerificationActivity.class);
                intent.putExtra("gauss_monery",gauss_monery + "");
                startActivity(intent);
                finish();
                break;
        }
    }
}
