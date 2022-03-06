package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.fragment.trade.GoCommissionFragment;
import com.unique.blockchain.nft.view.fragment.trade.MyCommissionFragment;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;

/**
 * 节点-委托挖矿
 * */
public class CommissionActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tb_assets_detail)
    TabLayout tb_assets_detail;
    @BindView(R.id.viewpager_assets_detail)
    ViewPager viewpager_assets_detail;
    final Fragment[] fragments = {new MyCommissionFragment(), new GoCommissionFragment()};
    String[] titles = {};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_commision;
    }

    @Override
    public void initUI() {
        titles = new String[]{"我的委托", "去委托"};
        top_s_title_toolbar.setLeftTitleText("委托");
        top_s_title_toolbar.lin_title.setBackground(getResources().getDrawable(R.color.color_F5F5F6));
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        top_s_title_toolbar.mTxtRightTitle.setBackground(getResources().getDrawable(R.mipmap.icon_history));
        top_s_title_toolbar.mTxtRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(FastClick.isFastClick()) {
                    Intent intent = new Intent(CommissionActivity.this, CommissionRecordActivity.class);
                    startActivity(intent);
//                }
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

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.commission == 1){
            MyApplication.commission = 0;
            viewpager_assets_detail.setCurrentItem(0);

        }

    }
}
