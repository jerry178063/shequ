package com.unique.blockchain.nft.view.activity.mark;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.fragment.mark.MyFragment;
import com.unique.blockchain.nft.view.fragment.mark.SystemFragment;

import butterknife.BindView;

/**
 * 通知
 * */
public class NoticeActivity extends BaseActivity {

    private LinearLayout img_back;
    Fragment[] fragments_record;
    String[] titles_record = {};
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tb_notice)
    TabLayout tb_notice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tong;
    }
    @Override
    public void initUI() {
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        fragments_record = new Fragment[]{new MyFragment(), new SystemFragment()};
        titles_record = new String[]{"个人通知", "系统通知"};
        viewpager.setOffscreenPageLimit(fragments_record.length);
        tb_notice.setupWithViewPager(viewpager);
        //每项只进入一次
        viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_record[position];
            }

            @Override
            public int getCount() {
                return fragments_record.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles_record[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
    }

    @Override
    public void initData() {
        img_back = findViewById(R.id.img_back);
        //返回键盘
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void bindView(Bundle bundle) {

    }

}