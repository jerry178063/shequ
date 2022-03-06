package com.unique.blockchain.nft.adapter;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;
import com.unique.blockchain.nft.widget.AutoFitTextView;

import java.util.List;

/**
 * 我的-交易中
 */
public class TradingAdapter extends BaseQuickAdapter<MarkHotBean.Rows, BaseViewHolder> {
    private String type = "";
    private int getType = 0;
    private int mPosition;
    private CommAdapter.OutPrice mOutPrice;
    private SparseArray<CountDownTimer> timerArray;
    private CancelTrade cancelTrade;

    public interface CancelTrade {
        void CancelTrade(int position, MarkHotBean.Rows item);
    }

    public interface OutPrice {
        void outPrice(int position);
    }

    public void setCancelTrade(CancelTrade cancelTrade) {
        this.cancelTrade = cancelTrade;
    }

    public void setOutPriceListener(CommAdapter.OutPrice outPrice) {
        this.mOutPrice = outPrice;
    }

    public TradingAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<MarkHotBean.Rows> data) {
        super(layoutResId, data);
        timerArray = new SparseArray<>();
    }

    public void cancelAllTimers() {
        if (timerArray == null) {
            return;
        }
        int size = timerArray.size();
        for (int i = 0; i < size; i++) {
            CountDownTimer cdt = timerArray.get(timerArray.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MarkHotBean.Rows item) {


        TextView search_text2 = helper.getView(R.id.tv_name);
        TextView search_text1 = helper.getView(R.id.tv_product_type_name);
        AutoFitTextView current_price = helper.getView(R.id.current_price);
        ImageView search_img = helper.getView(R.id.img_product);
//        tv_detail_day = helper.getView(R.id.tv_detail_day);
//        tv_detail_hour = helper.getView(R.id.tv_detail_hour);
//        tv_detail_minute = helper.getView(R.id.tv_detail_minute);
//        tv_detail_sends = helper.getView(R.id.tv_detail_sends);
        TextView shi_jian = helper.getView(R.id.shi_jian);
        TextView tv_min_price = helper.getView(R.id.tv_min_price);
        ImageView img_cancel_trade = helper.getView(R.id.img_cancel_trade);
        LinearLayout lin_out_price = helper.getView(R.id.lin_out_price);//最低出价区域
        TextView tv_price_type = helper.getView(R.id.tv_price_type);//

        if(item.getSellMode() == 1){//固定价
            lin_out_price.setVisibility(View.GONE);
            tv_price_type.setText("固定价:");
            tv_price_type.setTextColor(mContext.getResources().getColor(R.color.color_626061));
            current_price.setTextColor(mContext.getResources().getColor(R.color.color_DA6E6E));
            if (!TextUtils.isEmpty(item.getCurrentPrice())) {
                current_price.setText(item.getCurrentPrice());
            }
        }else if(item.getSellMode() == 2){//拍卖
            lin_out_price.setVisibility(View.VISIBLE);
            tv_price_type.setText("最高出价:");
            tv_price_type.setTextColor(mContext.getResources().getColor(R.color.color_9095AB));
            current_price.setTextColor(mContext.getResources().getColor(R.color.color_DA6E6E));
            if (!TextUtils.isEmpty(item.getCurrentPrice())) {
                current_price.setText(item.getCurrentPrice());
            }
        }

        Glide.with(mContext).load(item.getProductCover()).into(search_img);
        search_text2.setText(item.getProductName());
        Log.e("FF3422", "item.getSurplus():" + item.getSurplus());

        tv_min_price.setText(item.getMiniDidPrice());

        getType = item.getProductType();
        if (getType == 1) {
            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop1));
            search_text1.setText("票务");
        } else if (getType == 2) {
            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop2));
            search_text1.setText("收藏品");
        } else if (getType == 3) {
            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop));
            search_text1.setText("艺术品");
        } else if (getType == 4) {
            search_text1.setBackground(mContext.getResources().getDrawable(R.drawable.shop4));
            search_text1.setText("轻奢品");
        }
        type = item.getProductType() + "";
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PaiActivity.class);
//                intent.putExtra("nftId", type);
//                mContext.startActivity(intent);
            }
        });
        img_cancel_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cancelTrade != null) {
                    cancelTrade.CancelTrade(helper.getLayoutPosition(), item);
                }

            }
        });
        TextView tv_trade_status = helper.getView(R.id.tv_trade_status);//转移状态
        if (item.getIsTransfer() != null) {
            if (item.getIsTransfer().intValue() == 0) {
                tv_trade_status.setText("转移中");
            } else if (item.getIsTransfer().intValue() == 1) {
                tv_trade_status.setText("转移失败");
            } else if (item.getIsTransfer().intValue() == 2) {
                tv_trade_status.setText("转移成功");
            }
        } else {
            tv_trade_status.setText("");
        }
    }

//    public  void formatLongToTimeStr(Long date) {
//        long day = date / (60 * 60 * 24);
//        long hour = (date / (60 * 60) - day * 24);
//        long min = ((date / 60) - day * 24 * 60 - hour * 60);
//        long s = (date - day*24*60*60 - hour*60*60 - min*60);
////        String strtime = "剩余："+day+"天"+hour+"小时"+min+"分"+s+"秒";
//        tv_detail_day.setText(day + "");//天
//        tv_detail_hour.setText(hour + "");//时
//        tv_detail_minute.setText(min + "");//分
//        tv_detail_sends.setText(s + "");//秒
//    }
}
