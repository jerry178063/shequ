package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.me.fragment.AdminReceiptFragment;
import com.unique.blockchain.nft.view.activity.me.fragment.AllMyTradeFragment;
import com.unique.blockchain.nft.view.activity.me.fragment.ReceiptedFragment;
import com.unique.blockchain.nft.view.activity.me.fragment.TradingFragment;
import com.unique.blockchain.nft.view.activity.me.fragment.WaitTradeFragment;
import com.unique.blockchain.nft.view.fragment.mark.MyFragment;
import com.unique.blockchain.nft.view.fragment.mark.SystemFragment;
import com.unique.blockchain.nft.view.fragment.me.DaiFragment;
import com.unique.blockchain.nft.view.fragment.me.JiaoFragment;
import com.unique.blockchain.nft.view.fragment.me.NFTQuanFragment;
import com.unique.blockchain.nft.view.fragment.me.ShouFragment;
import com.unique.blockchain.nft.view.fragment.me.TiFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class GriiActivity extends BaseActivity {
    private LinearLayout mTongFan;
    private MyFragment myFragment;
    private SystemFragment systemFragment;
    private FrameLayout mViewPagerTong;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments6;
    private LinearLayout mLiuSouyy;
    private RadioButton mQuanbuY;
    private RadioButton mDaiY;
    private RadioButton mJiaoY;
    private RadioButton mYijingY;
    private RadioButton mHuoY;
    private NFTQuanFragment quanFragment;
    private DaiFragment daiFragment;
    private JiaoFragment jiaoFragment;
    private TiFragment tiFragment;
    private ShouFragment shouFragment;
    private FrameLayout mFr;
    private LinearLayout mImgFanGrii;


    Fragment[] fragments_record;
    String[] titles_record = {};
    @BindView(R.id.viewpager_assets_shuzi)
    ViewPager viewpager_assets_shuzi;
    @BindView(R.id.tb_shuzi_tab)
    TabLayout tb_shuzi_tab;
    String item;
    @BindView(R.id.liu_souyy)
    LinearLayout liu_souyy;//搜索

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grii;
    }

    @Override
    public void initUI() {
        initView();
    }

    @Override
    public void initData() {

    }
    private void initView() {
        mLiuSouyy = findViewById(R.id.liu_souyy);
        mQuanbuY = findViewById(R.id.quanbu_y);
        mDaiY = findViewById(R.id.dai_y);
        mJiaoY = findViewById(R.id.jiao_y);
        mYijingY = findViewById(R.id.yijing_y);
        mHuoY = findViewById(R.id.huo_y);
        mFr = findViewById(R.id.fr);
        mImgFanGrii = findViewById(R.id.img_fan_Grii);

        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        fragments_record = new Fragment[]{new AllMyTradeFragment(), new WaitTradeFragment(), new TradingFragment(), new AdminReceiptFragment(), new ReceiptedFragment()};
        titles_record = new String[]{"全部", "待交易","交易中","已提货","已收货"};
        viewpager_assets_shuzi.setOffscreenPageLimit(fragments_record.length);
        tb_shuzi_tab.setupWithViewPager(viewpager_assets_shuzi);
        //每项只进入一次
        viewpager_assets_shuzi.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
        initFr();
        initOnClick();

        item = getIntent().getStringExtra("item");
        if(item != null){
            if(item.equals("1")){
                viewpager_assets_shuzi.setCurrentItem(1);
            }else if(item.equals("2")){
                viewpager_assets_shuzi.setCurrentItem(2);
            }else if(item.equals("3")){
                viewpager_assets_shuzi.setCurrentItem(3);
            }else if(item.equals("4")){
                viewpager_assets_shuzi.setCurrentItem(4);
            }
        }
        liu_souyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GriiActivity.this, SouMeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initOnClick() {
        mImgFanGrii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initFr() {
        quanFragment = new NFTQuanFragment();
        daiFragment = new DaiFragment();
        jiaoFragment = new JiaoFragment();
        tiFragment = new TiFragment();
        shouFragment = new ShouFragment();

        fragments6 = new ArrayList<>();
        fragments6.add(new NFTQuanFragment());
        fragments6.add(new DaiFragment());
        fragments6.add(new JiaoFragment());
        fragments6.add(new TiFragment());
        fragments6.add(new ShouFragment());

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fr, quanFragment)
                .add(R.id.fr, daiFragment)
                .add(R.id.fr, jiaoFragment)
                .add(R.id.fr, tiFragment)
                .add(R.id.fr, shouFragment)
                .show(quanFragment)
                .hide(daiFragment)
                .hide(jiaoFragment)
                .hide(tiFragment)
                .hide(shouFragment)
                .commit();
        mQuanbuY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(quanFragment)
                        .hide(daiFragment)
                        .hide(jiaoFragment)
                        .hide(tiFragment)
                        .hide(shouFragment)
                        .commit();

            }
        });
        mDaiY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(daiFragment)
                        .hide(quanFragment)
                        .hide(jiaoFragment)
                        .hide(tiFragment)
                        .hide(shouFragment)
                        .commit();

            }
        });
        mJiaoY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(jiaoFragment)
                        .hide(daiFragment)
                        .hide(quanFragment)
                        .hide(tiFragment)
                        .hide(shouFragment)
                        .commit();

            }
        });
        mYijingY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(tiFragment)
                        .hide(daiFragment)
                        .hide(jiaoFragment)
                        .hide(quanFragment)
                        .hide(shouFragment)
                        .commit();

            }
        });
        mHuoY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(shouFragment)
                        .hide(daiFragment)
                        .hide(jiaoFragment)
                        .hide(tiFragment)
                        .hide(quanFragment)
                        .commit();

            }
        });
    }


    @Override
    public void bindView(Bundle bundle) {

    }
}