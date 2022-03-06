package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.SouActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseFragment;

/**
 * 热门
 * */
public class ReMenNewFragment extends BaseFragment {

    Fragment[] fragments_home;
    String[] titles_home = {};

    ViewPager viewpager_home;
    TabLayout tb_home;
    private LinearLayout liu_sou;
    @Override
    protected void initView(View view) {
        viewpager_home = view.findViewById(R.id.viewpager);
        tb_home = view.findViewById(R.id.tb_kline_record);
        fragments_home = new Fragment[]{new All_Home_Fragment(), new Art_Home_Fragment(), new Collect_Home_Fragment(), new Ticket_Home_Fragment(), new Grade_Home_Fragment()};
//        fragments_home = new Fragment[]{new All_Fragment(), new Art_Fragment(), new Collect_Fragment(), new Ticket_Fragment(), new Grade_Fragment()};
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
        liu_sou = view.findViewById(R.id.liu_sou);
        liu_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getActivity(), SouActivity.class);
                    intent.putExtra("sellMode","2");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_remen;
    }

    @Override
    public void onEut(String onEut) {

    }
}