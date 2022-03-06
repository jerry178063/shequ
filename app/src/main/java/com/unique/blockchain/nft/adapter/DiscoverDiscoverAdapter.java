package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.discover.GetArticleListBean;

import java.util.List;

/**
 * 发现-发现adapter
 * */
public class DiscoverDiscoverAdapter extends BaseQuickAdapter<GetArticleListBean.Rows, BaseViewHolder> {
    Context context;
    public void getContext(Context context){
        this.context = context;
        this.notifyDataSetChanged();
    }

    public DiscoverDiscoverAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<GetArticleListBean.Rows> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetArticleListBean.Rows item) {
        TextView tv_word=helper.getView(R.id.has_compler);
        tv_word.setText(item.getTitle());
        TextView tv_digest = helper.getView(R.id.tv_digest);
//        tv_digest.setText(item.getDigest());
        TextView tv_time = helper.getView(R.id.tv_time);
        tv_time.setText(item.getReleaseTime());
        helper.addOnClickListener(R.id.has_compler);
        ImageView img_left = helper.getView(R.id.img_left);
        Glide.with(context)
                .load(item.getThumbnailaddress())
//                    .dontAnimate()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(context.getResources().getDrawable(R.mipmap.logo_gauss))
                .into(img_left);
    }
}
