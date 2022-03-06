package com.unique.blockchain.nft;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.view.activity.SplashActivity;
import com.unique.blockchain.nft.view.fragment.GuideFourFragment;
import com.unique.blockchain.nft.view.fragment.GuideOneFragment;
import com.unique.blockchain.nft.view.fragment.GuideThreeFragment;
import com.unique.blockchain.nft.view.fragment.GuideTwoFragment;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {


    @BindView(R.id.viewpager_guide)
    ViewPager viewpager_guide;
    Fragment[] fragments_guide;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
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
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.e("FF24344","firstInstall:" + SpUtil.getBoolean(this, "firstInstall", false));
        if (SpUtil.getBoolean(this, "firstInstall", false) == false) {

            fragments_guide = new Fragment[]{new GuideOneFragment(), new GuideTwoFragment(), new GuideThreeFragment(), new GuideFourFragment()};

            viewpager_guide.setOffscreenPageLimit(4);
            //每项只进入一次
            viewpager_guide.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments_guide[position];
                }

                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return "";
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    super.destroyItem(container, position, object);
                }
            });

        } else {
            Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.e("FF24344","firstInstall:" + SpUtil.getBoolean(this, "firstInstall", false));
        if (SpUtil.getBoolean(this, "firstInstall", false) == false) {

            fragments_guide = new Fragment[]{new GuideOneFragment(), new GuideTwoFragment(), new GuideThreeFragment(), new GuideFourFragment()};

            viewpager_guide.setOffscreenPageLimit(4);
            //每项只进入一次
            viewpager_guide.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments_guide[position];
                }

                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return "";
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    super.destroyItem(container, position, object);
                }
            });

        } else {
            Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void bindView(Bundle bundle) {

    }

}
