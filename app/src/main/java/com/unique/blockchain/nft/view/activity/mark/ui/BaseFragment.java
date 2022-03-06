package com.unique.blockchain.nft.view.activity.mark.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unique.blockchain.nft.view.activity.mark.base.IBaseV;

public abstract class BaseFragment extends Fragment implements IBaseV {
    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(getlayout(),null);
        initView(view);
        initData();
        return view;
    }


    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract int getlayout();


}
