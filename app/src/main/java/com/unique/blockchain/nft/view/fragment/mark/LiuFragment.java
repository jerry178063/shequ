package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.markAdapter.BannerAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.ViewAdapter;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.database.PiaowuDatabase;
import com.unique.blockchain.nft.view.activity.database.QingDatabase;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;
import com.unique.blockchain.nft.view.activity.database.ShoucangDatabase;
import com.unique.blockchain.nft.view.activity.database.YishuDatabase;
import com.unique.blockchain.nft.view.activity.mark.SouActivity;
import com.unique.blockchain.nft.view.activity.mark.presenter.RePresenter;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseMvpFragment;
import com.unique.blockchain.nft.view.activity.mark.view.Re_view;

import java.util.ArrayList;
import java.util.List;


public class LiuFragment extends BaseMvpFragment<Re_view, RePresenter> implements Re_view {

    private View inclde1;
    private TextView text_All;
    private TextView text_yishu;
    private TextView text_shoucang;
    private TextView text_piaowu;
    private TextView text_qing;
    private FrameLayout mIncludeFrameLayout;

    private android.widget.LinearLayout mMultiply;
    private TextView mTextAll;
    private TextView mTextYishu;
    private TextView mTextShoucang;
    private TextView mTextPiaowu;
    private TextView mTextQing;
    private All_Fragment all_fragment;
    private Art_Fragment art_fragment;
    private Collect_Fragment collect_fragment;
    private Ticket_Fragment ticket_fragment;
    private Grade_Fragment grade_fragment;
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private RecyclerView rec;
    private View include1;
    private View liu_sou;
    private RecyclerView liu_RecyclerView;
    private RecyclerView butt_rec;
    private BannerAdapter recAdapter;
    private ViewAdapter viewAdapter;
    private ScrollView scrollView;
    private View item_img_yy;
    private List<BannerDatabase.DataBean>list=new ArrayList<>();

    @Override
    public void onEut(String onEut) {

    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_liu;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        include1 = view.findViewById(R.id.include1);
        liu_sou = view.findViewById(R.id.liu_sou);
        liu_RecyclerView = view.findViewById(R.id.liu_RecyclerView);
        butt_rec = view.findViewById(R.id.butt_rec);
        scrollView = view.findViewById(R.id.scrollView);
        mMultiply = include1.findViewById(R.id.multiply_);
        mTextAll = include1.findViewById(R.id.text_All);//??????
        mTextYishu = include1.findViewById(R.id.text_yishu);//??????
        mTextShoucang = include1.findViewById(R.id.text_shoucang);//??????
        mTextPiaowu = include1.findViewById(R.id.text_piaowu);//??????
        mTextQing = include1.findViewById(R.id.text_qing);//?????????
        initRecyclerView();
        initFragment();
        initRen();

    }

    private void initRen() {
    }

    @Override
    protected void initData() {
        presenter.initData();
        presenter.initList();

    }

    @Override
    protected RePresenter initPresenter() {
        return new RePresenter();
    }

    private void initRecyclerView() {
        //banner
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        liu_RecyclerView.setLayoutManager(linearLayoutManager);
        recAdapter = new BannerAdapter(list,getActivity());
        liu_RecyclerView.setAdapter(recAdapter);

        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.HORIZONTAL);//????????????
        butt_rec.setLayoutManager(linearManager);
        viewAdapter = new ViewAdapter(getActivity());
        butt_rec.setAdapter(viewAdapter);
    }

    private void initFragment() {
        liu_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SouActivity.class);
                startActivity(intent);
            }
        });
        mIncludeFrameLayout = include1.findViewById(R.id.include_frameLayout);

        all_fragment = new All_Fragment();//??????
        art_fragment = new Art_Fragment();//??????
        collect_fragment = new Collect_Fragment();//??????
        ticket_fragment = new Ticket_Fragment();//??????
        grade_fragment = new Grade_Fragment();//?????????

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
        });//??????
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
        });//??????
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
        });//??????
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
    }//??????5?????????????????????
    @Override
    public void success(BannerDatabase bannerDatabase) {
        recAdapter.setBannerl(bannerDatabase.getData());
        //Log.e("??????", "displayImage333: "+bannerDatabase);
    }

    @Override
    public void List(ReDatabase reDatabase) {
        viewAdapter.setGetList(reDatabase.getRows());
    }
    @Override
    public void Quan(QuanDatabase quanDatabase) {

    }

    @Override
    public void yishu(YishuDatabase yishuDatabase) {

    }

    @Override
    public void shoucang(ShoucangDatabase shoucangDatabase) {

    }

    @Override
    public void piaowu(PiaowuDatabase piaowuDatabase) {

    }

    @Override
    public void qingshi(QingDatabase qingDatabase) {

    }


}