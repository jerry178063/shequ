package com.unique.blockchain.nft.view.fragment.mark;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseFragment;

import java.util.ArrayList;

public class RenFragment extends BaseFragment {

    private LinearLayout mMultiply;
    private TextView mTextAll;
    private TextView mTextYishu;
    private TextView mTextShoucang;
    private TextView mTextPiaowu;
    private TextView mTextQing;
    private FrameLayout mIncludeFrameLayout;
    private View include2;

    private All_Fragment all_fragment;
    private Art_Fragment art_fragment;
    private Collect_Fragment collect_fragment;
    private Ticket_Fragment ticket_fragment;
    private Grade_Fragment grade_fragment;
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    @Override
    protected void initView(View view) {
        include2 = view.findViewById(R.id.include2);
        mMultiply = include2.findViewById(R.id.multiply_);

        mTextAll = include2.findViewById(R.id.text_All);//全部
        mTextYishu = include2.findViewById(R.id.text_yishu);//艺术
        mTextShoucang = include2.findViewById(R.id.text_shoucang);//收藏
        mTextPiaowu = include2.findViewById(R.id.text_piaowu);//票务
        mTextQing = include2.findViewById(R.id.text_qing);//轻奢饰

        mIncludeFrameLayout = include2.findViewById(R.id.include_frameLayout);

        all_fragment = new All_Fragment();//全部
        art_fragment = new Art_Fragment();//艺术
        collect_fragment = new Collect_Fragment();//收藏
        ticket_fragment = new Ticket_Fragment();//票务
        grade_fragment = new Grade_Fragment();//轻奢饰

        fragments = new ArrayList<>();
        fragments.add(all_fragment);
        fragments.add(art_fragment);
        fragments.add(collect_fragment);
        fragments.add(ticket_fragment);
        fragments.add(grade_fragment);

        fragmentManager=getChildFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.include_frameLayout,all_fragment)
                .add(R.id.include_frameLayout,art_fragment)
                .add(R.id.include_frameLayout,collect_fragment)
                .add(R.id.include_frameLayout,ticket_fragment)
                .add(R.id.include_frameLayout,grade_fragment)
                .show(all_fragment)
                .hide(art_fragment)
                .hide(collect_fragment)
                .hide(ticket_fragment)
                .hide(grade_fragment)
                .commit();
        mTextAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(all_fragment)
                        .hide(art_fragment)
                        .hide(collect_fragment)
                        .hide(ticket_fragment)
                        .hide(grade_fragment)
                        .commit();
            }
        });//全部
        mTextYishu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(art_fragment)
                        .hide(all_fragment)
                        .hide(collect_fragment)
                        .hide(ticket_fragment)
                        .hide(grade_fragment)
                        .commit();
            }
        });//艺术
        mTextShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(collect_fragment)
                        .hide(all_fragment)
                        .hide(art_fragment)
                        .hide(ticket_fragment)
                        .hide(grade_fragment)
                        .commit();
            }
        });//收藏
        mTextPiaowu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(ticket_fragment)
                        .hide(all_fragment)
                        .hide(collect_fragment)
                        .hide(art_fragment)
                        .hide(grade_fragment)
                        .commit();
            }
        });
        mTextQing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .show(grade_fragment)
                        .hide(all_fragment)
                        .hide(collect_fragment)
                        .hide(ticket_fragment)
                        .hide(art_fragment)
                        .commit();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_ren;
    }

    @Override
    public void onEut(String onEut) {

    }
}