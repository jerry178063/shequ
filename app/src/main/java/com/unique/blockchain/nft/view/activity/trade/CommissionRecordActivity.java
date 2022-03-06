package com.unique.blockchain.nft.view.activity.trade;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.fragment.trade.CommissionFragment;
import com.unique.blockchain.nft.view.fragment.trade.CommissionGetFragment;
import com.unique.blockchain.nft.view.fragment.trade.CommissionRecordAllFragment;
import com.unique.blockchain.nft.view.fragment.trade.CommissionRedemptionFragment;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;

/**
 *
 * 委托记录
 * */
public class CommissionRecordActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tb_assets_detail)
    TabLayout tb_assets_detail;
    @BindView(R.id.viewpager_assets_detail)
    ViewPager viewpager_assets_detail;
//new CommissionRecordAllFragment(),
    final Fragment[] fragments = {new CommissionFragment(),new CommissionRecordAllFragment(), new CommissionGetFragment(), new CommissionRedemptionFragment()};
    String[] titles = {};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commission_record;
    }

    @Override
    public void initUI() {
        titles = new String[]{"委托","提取收益",
                "提取佣金", "赎回"};
        top_s_title_toolbar.setLeftTitleText("委托记录");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
