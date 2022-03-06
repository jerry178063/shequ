package com.unique.blockchain.nft.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;
import com.unique.blockchain.nft.view.fragment.mark.JianFragment;
import com.unique.blockchain.nft.view.fragment.mark.PopularallFragment;

import butterknife.BindView;

public class RemenActivity extends BaseActivity {

    private LinearLayout mRemenFanhui;
    public static String company;
    private Bundle bundle;
    TextView tv_walletAdd, tv_name;
    public static String imgUrl, companyIntro, name;
    private ImageView img_tou, img_copy;
    @BindView(R.id.viewpager_remen)
    ViewPager viewpager_remen;
    private Fragment[] fragments_remen;

    public ImgUrl imgUrls;
    @BindView(R.id.remen_qu)
    TextView remen_qu;
    @BindView(R.id.remen_jian)
    TextView remen_jian;


    public interface ImgUrl {
        void ImgUrl(String imgUrl, String walletAddr);
    }

    public void setImgURL(ImgUrl imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remen;
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
        CommonUtil.setStatusBarTextColor(this, 1);
        tv_walletAdd = findViewById(R.id.tv_walletAdd);
        img_copy = findViewById(R.id.img_copy);
        img_tou = findViewById(R.id.img_tou);
        mRemenFanhui = findViewById(R.id.remen_fanhui);
        tv_name = findViewById(R.id.tv_name);
        company = getIntent().getStringExtra("companyWalletAddr");
        //Log.i("kkkllll", "initData: " + company);
        if(SpUtil.getInstance(RemenActivity.this).getString("walletAdd") != null) {
            tv_walletAdd.setText(SpUtil.getInstance(RemenActivity.this).getString("walletAdd"));
        }
        imgUrl = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(img_tou);
        companyIntro = getIntent().getStringExtra("companyIntro");
        name = getIntent().getStringExtra("name");
        tv_name.setText(name);
        if (imgUrls != null) {
            imgUrls.ImgUrl(companyIntro, company);
        }

        fragments_remen = new Fragment[]{new PopularallFragment(), new JianFragment()};
        viewpager_remen.setOffscreenPageLimit(2);
        //每项只进入一次
        viewpager_remen.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_remen[position];
            }

            @Override
            public int getCount() {
                return 2;
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
        viewpager_remen.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    remen_qu.setTextColor(getResources().getColor(R.color.color_4B4B4B));
                    remen_jian.setTextColor(getResources().getColor(R.color.color_B1B1B1));
                } else if (i == 1) {
                    remen_jian.setTextColor(getResources().getColor(R.color.color_4B4B4B));
                    remen_qu.setTextColor(getResources().getColor(R.color.color_B1B1B1));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mRemenFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyContentToClipboard(tv_walletAdd.getText().toString() + "", RemenActivity.this);
                ToastUtils.getInstance(RemenActivity.this).show("复制成功!");
            }
        });
        //全部
        remen_qu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_remen.setCurrentItem(0);
            }
        });
        //简介
        remen_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_remen.setCurrentItem(1);
            }
        });
    }

    @Override
    public void initData() {


    }

    @Override
    public void bindView(Bundle bundle) {

    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }


}