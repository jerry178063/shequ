package com.unique.blockchain.nft.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;

import java.util.List;

public class SureWordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SureWordAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_word=helper.getView(R.id.tv_word);
        ImageView iv_clear=helper.getView(R.id.iv_clear);
        tv_word.setText(item);
        helper.addOnClickListener(R.id.tv_word);
        helper.addOnClickListener(R.id.iv_clear);
    }
}
