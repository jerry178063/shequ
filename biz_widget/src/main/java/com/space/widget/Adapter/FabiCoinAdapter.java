package com.space.widget.Adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.bean.CoinBean;
import com.space.widget.R;

import java.util.List;

public class FabiCoinAdapter extends BaseQuickAdapter<CoinBean,BaseViewHolder> {
    public FabiCoinAdapter(int layoutResId, @Nullable List<CoinBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinBean item) {
        helper.setText(R.id.tv_fabi_coin_name1, item.getCurrency_type());
        helper.setText(R.id.tv_fabi_coin_name2, "/CNY");
    }
}
