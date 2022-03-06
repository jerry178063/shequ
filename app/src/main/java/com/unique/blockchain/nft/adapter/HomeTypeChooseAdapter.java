package com.unique.blockchain.nft.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.home.ListMatch;

import java.util.List;

public class HomeTypeChooseAdapter extends BaseQuickAdapter<ListMatch.DataBean, BaseViewHolder> {

    public HomeTypeChooseAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<ListMatch.DataBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ListMatch.DataBean item) {
       TextView tv_name =helper.getView(R.id.tv_name);
        tv_name.setText(item.getToken());
        helper.addOnClickListener(R.id.tv_name);

        if(item.isIs_select()){
            tv_name.setSelected(true);
        }else {
            tv_name.setSelected(false);
        }


    }

}
