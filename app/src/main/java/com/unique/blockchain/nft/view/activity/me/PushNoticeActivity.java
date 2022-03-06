package com.unique.blockchain.nft.view.activity.me;

import android.os.Bundle;
import android.view.View;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;

/**
 * 推送通过
 * */
public class PushNoticeActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_push_notice;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("推送通知");
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
}
