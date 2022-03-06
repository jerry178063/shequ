package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;

import java.util.List;

/**
 *产品资质
 * */
public class TradeListPowerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Context mContext;
    public TradeListPowerAdapter(Context context,int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<String> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        ImageView  img_zizhi=helper.getView(R.id.img_zizhi);
        Log.e("FF344","item:" + item);
//        Glide.with(mContext).load(item).into(img_zizhi);

        TextView textView = helper.getView(R.id.tv_url);
        textView.setText(item);
    }
}
