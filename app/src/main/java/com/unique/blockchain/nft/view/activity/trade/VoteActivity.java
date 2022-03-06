package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.fragment.trade.IinitiatedFragment;
import com.unique.blockchain.nft.view.fragment.trade.IparticipatedFragment;
import com.unique.blockchain.nft.view.fragment.trade.SponsorshipAreaFragment;
import com.unique.blockchain.nft.view.fragment.trade.VoteAreaFragment;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;

/**
 *
 * 投票
 * */
public class VoteActivity extends BaseActivity {


    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tb_assets_detail)
    TabLayout tb_assets_detail;
    @BindView(R.id.viewpager_assets_detail)
    ViewPager viewpager_assets_detail;

    final Fragment[] fragments = {new VoteAreaFragment(), new SponsorshipAreaFragment(), new IinitiatedFragment(), new IparticipatedFragment()};
    String[] titles = {};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote;
    }

    @Override
    public void initUI() {
        titles = new String[]{"投票区", "赞助区",
                "我发起的", "我参与的"};
        top_s_title_toolbar.setLeftTitleText("投票");
        top_s_title_toolbar.setRightTitleText("发起投票");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        top_s_title_toolbar.mTxtRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VoteActivity.this, StartVoteActivity.class);
                startActivity(intent);
            }
        });

        //每项只进入一次
        viewpager_assets_detail.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tb_assets_detail.setupWithViewPager(viewpager_assets_detail);
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
