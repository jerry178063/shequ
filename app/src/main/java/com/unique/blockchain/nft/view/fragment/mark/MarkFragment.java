package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MyAdapter;
import com.unique.blockchain.nft.view.activity.mark.NoticeActivity;

import java.util.ArrayList;
import java.util.List;

public class MarkFragment extends BaseFragment {

    private TextView mFragmentLiuLan;
    private TextView mFragmentReMen;
    private FrameLayout mFrameLayout;
    private ImageView mFragmentTong;
    private LiuFragment liuFragment;
    private RenFragment renFragment;
    private TableLayout fragment_TableLayout;
    private ArrayList<Fragment> fragments;
    private List<String> list = new ArrayList<>();
    private FragmentManager fragmentManager;
    private MyAdapter myAdapter;

    public static MarkFragment newInstance() {
        Bundle args = new Bundle();
        MarkFragment fragment = new MarkFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bazaar;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 2);
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        mFragmentLiuLan = (TextView) findViewById(R.id.fragment_LiuLan);
        mFragmentReMen = (TextView) findViewById(R.id.fragment_ReMen);
        mFrameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        mFragmentTong = (ImageView) findViewById(R.id.fragment_Tong);

        liuFragment = new LiuFragment();
        renFragment = new RenFragment();

        fragments = new ArrayList<>();
        fragments.add(new LiuFragment());
        fragments.add(new RenFragment());

        /*myAdapter = new MyAdapter(getChildFragmentManager(), fragments);
        mFrameLayout.setAdapter(myAdapter);*/
        //更改FrameLayout布局控件类型
        fragmentManager=getChildFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frameLayout,liuFragment)
                .add(R.id.frameLayout,renFragment)
                .show(liuFragment)
                .hide(renFragment)
                .commit();
        mFragmentLiuLan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(liuFragment)
                        .hide(renFragment)
                        .commit();
            }
        });
        mFragmentReMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(renFragment)
                        .hide(liuFragment)
                        .commit();
            }
        });
        mFragmentTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });
    }

}
