package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.trade.GoTradeBean;

import java.util.List;

/**
 * 去交易列表adapter
 * */
public class TradeListAdapter extends BaseQuickAdapter<GoTradeBean.Rows, BaseViewHolder> {
    Context context;
    private GoTradeListener mGoTradeListener;

    public void getContext(Context context){
        this.context = context;
        this.notifyDataSetChanged();
    }
    public TradeListAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<GoTradeBean.Rows> data) {
        super(layoutResId, data);
    }
    public void setGoTrade(GoTradeListener goTradeListener){
        this.mGoTradeListener = goTradeListener;
    }
    public interface GoTradeListener{
        void goTrade(int position);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoTradeBean.Rows item) {
        TextView tv_word=helper.getView(R.id.has_compler);
//        tv_word.setText(item);
        helper.addOnClickListener(R.id.has_compler);
        ImageView img_product = helper.getView(R.id.img_product);
        Glide.with(context).load(item.getProductCover()).into(img_product);
        TextView tv_product_type_name = helper.getView(R.id.tv_product_type_name);
        if(item.getProductType() == 1){//票务
            tv_product_type_name.setText("票务");
            tv_product_type_name.setBackground(mContext.getResources().getDrawable(R.drawable.shop1));
        }else if(item.getProductType() == 2){//收藏品
            tv_product_type_name.setText("收藏品");
            tv_product_type_name.setBackground(mContext.getResources().getDrawable(R.drawable.shop2));
        }else if(item.getProductType() == 3){//艺术品
            tv_product_type_name.setText("艺术品");
            tv_product_type_name.setBackground(mContext.getResources().getDrawable(R.drawable.shop));
        }else if(item.getProductType() == 4){//轻奢品
            tv_product_type_name.setBackground(mContext.getResources().getDrawable(R.drawable.shop4));
            tv_product_type_name.setText("轻奢品");
        }

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getNftName());
        TextView tv_product_intro = helper.getView(R.id.tv_product_intro);
        if(TextUtils.isEmpty(item.getCurrentPrice())){
            tv_product_intro.setText("0.000000");
        }else {
            tv_product_intro.setText(String.format("%.6f", Double.valueOf(item.getCurrentPrice())) + "");
        }
        TextView go_trades = helper.getView(R.id.go_trades);
        go_trades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoTradeListener != null){
                    mGoTradeListener.goTrade(helper.getPosition());
                }
            }
        });
    }
}
