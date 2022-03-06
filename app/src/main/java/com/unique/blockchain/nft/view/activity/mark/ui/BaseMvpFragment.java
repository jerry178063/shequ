package com.unique.blockchain.nft.view.activity.mark.ui;

import android.view.View;

import com.unique.blockchain.nft.view.activity.mark.base.IBaseP;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseV;


public abstract class BaseMvpFragment<V extends IBaseV,P extends IBaseP> extends BaseFragment {

    protected P presenter;

    @Override
    protected void initView(View view) {
        presenter = initPresenter();
            if (presenter != null){
                presenter.addView(this);
            }

        
    }

    protected abstract P initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.dataView();
        }
    }
}

