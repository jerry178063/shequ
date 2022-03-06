package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.NoticeActivity;

import butterknife.BindView;

public class MarkNewFragment extends BaseFragment {


    Fragment[] fragments_record;
    String[] titles_record = {};
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tb_kline_record)
    TabLayout tb_kline_record;
    @BindView(R.id.img_notice)
    ImageView img_notice;

    public static MarkNewFragment newInstance() {
        Bundle args = new Bundle();
        MarkNewFragment fragment = new MarkNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mark;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);

//        fragments_record = new Fragment[]{new LiuNewFragment(), new ReMenNewFragment()};
        fragments_record = new Fragment[]{new LiuTwoFragment(), new ReMenNewFragment()};
        titles_record = new String[]{"浏览", "热门拍卖"};
        viewpager.setOffscreenPageLimit(fragments_record.length);
        tb_kline_record.setupWithViewPager(viewpager);
        //每项只进入一次
        viewpager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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

                img_notice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MoreClick.MoreClicks()) {
                            Intent intent = new Intent(getActivity(), NoticeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
