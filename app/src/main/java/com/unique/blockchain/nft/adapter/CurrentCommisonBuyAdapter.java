package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.home.RankingBean;

import java.util.List;

/**
 *当前委托 --kline
 * */
public class CurrentCommisonBuyAdapter extends BaseQuickAdapter<RankingBean.Datas, BaseViewHolder> {
    List<RankingBean.Datas> pushBodyBuy;
    private Context context;
    public CurrentCommisonBuyAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<RankingBean.Datas> data) {
        super(layoutResId, data);

    }
    public void setData(Context context){
        this.context = context;
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, RankingBean.Datas item) {
        TextView tv_cishu = helper.getView(R.id.tv_cishu);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_shuliang = helper.getView(R.id.tv_shuliang);
        ImageView img_icon = helper.getView(R.id.img_icon);

        if(!TextUtils.isEmpty(item.getIconUrl())) {
            Glide.with(context).load(item.getIconUrl()).into(img_icon);
        }
        tv_cishu.setText(item.getCirculationCount() + "");
        tv_price.setText(item.getCurrentPrice());
        tv_shuliang.setText(item.getSeeCount() + "");

        if(!TextUtils.isEmpty(item.getMarketPriceType())) {
            if (item.getMarketPriceType().equals("0")) {//绿色
                tv_price.setTextColor(mContext.getResources().getColor(R.color.color_5BBB82));
            } else if (item.getMarketPriceType().equals("1")) {//红色
                tv_price.setTextColor(mContext.getResources().getColor(R.color.color_E41010));
            }
        }

    }

}
