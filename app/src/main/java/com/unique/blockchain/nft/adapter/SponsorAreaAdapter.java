package com.unique.blockchain.nft.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;

import java.util.List;

/**
 * 赞助区adapter
 * */
public class SponsorAreaAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SponsorAreaAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_word=helper.getView(R.id.has_compler);
        tv_word.setText(item);
        helper.addOnClickListener(R.id.has_compler);

    }
}
