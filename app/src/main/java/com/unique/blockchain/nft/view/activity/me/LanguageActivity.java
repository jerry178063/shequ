package com.unique.blockchain.nft.view.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 语言选择
 */
public class LanguageActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.rg_language)
    RadioGroup mRadioGroup;
    public LanguageActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_language;
    }
    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("语言选择");
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

    @OnClick({})
    public void setOnclick(View view) {
    }
}
