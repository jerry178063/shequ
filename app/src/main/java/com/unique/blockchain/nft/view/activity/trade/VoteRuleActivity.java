package com.unique.blockchain.nft.view.activity.trade;

import android.os.Bundle;
import android.view.View;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;

/**
 *
 * 投票规则
 * */
public class VoteRuleActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_rule;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("投票规则");
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
