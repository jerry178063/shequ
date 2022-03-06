package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.SouActivity;


public class LiuTwoFragment extends BaseFragment {
    Fragment[] fragments_home;
    String[] titles_home = {};

    ViewPager viewpager_home;
    TabLayout tb_home;
    private LinearLayout liu_sou;

    public static LiuTwoFragment newInstance() {
        Bundle args = new Bundle();
        LiuTwoFragment fragment = new LiuTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_liu_two;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        viewpager_home = (ViewPager) findViewById(R.id.viewpager);
        tb_home = (TabLayout) findViewById(R.id.tb_kline_record);
        fragments_home = new Fragment[]{new Liu_All_Home_Fragment(), new Liu_Art_Home_Fragment(), new Liu_Collect_Home_Fragment(), new Liu_Ticket_Home_Fragment(), new Liu_Grade_Home_Fragment()};
        titles_home = new String[]{"全部", "艺术品","收藏品","票务","轻奢品"};
        viewpager_home.setOffscreenPageLimit(fragments_home.length);
        tb_home.setupWithViewPager(viewpager_home);
        //每项只进入一次
        viewpager_home.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_home[position];
            }

            @Override
            public int getCount() {
                return fragments_home.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles_home[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
        liu_sou = (LinearLayout) findViewById(R.id.liu_sou);
        liu_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getActivity(), SouActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}