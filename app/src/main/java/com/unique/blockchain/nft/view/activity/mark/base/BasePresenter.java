package com.unique.blockchain.nft.view.activity.mark.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter <V extends IBaseV>implements IBaseP<V>{
    protected V view;
     WeakReference<V> vWeakReference;

    @Override
    public void addView(V baseView) {
        vWeakReference = new WeakReference<>(baseView);
        this.view=vWeakReference.get();
    }

    @Override
    public void dataView() {
        if (view!=null){
            view=null;
        }
    }
}
