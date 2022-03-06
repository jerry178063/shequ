package com.unique.blockchain.nft.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment>MyVp;

    public MyAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> myVp) {
        super(fm);
        MyVp = myVp;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MyVp.get(position);
    }

    @Override
    public int getCount() {
        return MyVp.size();
    }
}
