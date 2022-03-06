package com.unique.blockchain.nft.view.activity.mark.ui;


import com.unique.blockchain.nft.view.activity.mark.base.IBaseP;
import com.unique.blockchain.nft.view.activity.mark.base.IBaseV;

public abstract class BaseMvpActivity<V extends IBaseV,P extends IBaseP>extends BaseActivity implements IBaseV{
    protected P presenter;
    protected abstract P crenPresenter();

    @Override
    protected void initData() {
        if (presenter==null){
            presenter=crenPresenter();
            presenter.addView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.dataView();
        }
    }
}
