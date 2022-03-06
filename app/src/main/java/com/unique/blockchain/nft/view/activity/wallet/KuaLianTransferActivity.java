package com.unique.blockchain.nft.view.activity.wallet;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.fragment.wallet.KuaLianTrasnferFragment;
import com.unique.blockchain.nft.view.fragment.wallet.TongLianTrasnferFragment;

import butterknife.BindView;

/**
 * 跨链转账
 * */
public class KuaLianTransferActivity extends BaseActivity {

    Fragment[] fragments_record;
    String[] titles_record = {};
    @BindView(R.id.viewpager_assets_shuzi)
    ViewPager viewpager_assets_shuzi;
    @BindView(R.id.tb_shuzi_tab)
    TabLayout tb_shuzi_tab;
    public User unique;
    public  UserDao userDao;
    public  String uniqueAdress;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_kualian_transfer;
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
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(KuaLianTransferActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
        fragments_record = new Fragment[]{new TongLianTrasnferFragment(), new KuaLianTrasnferFragment()};
        titles_record = new String[]{"同链转账", "跨链转账"};
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
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
