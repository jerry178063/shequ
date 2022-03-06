package com.unique.blockchain.nft.adapter;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.java.StringUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.mark.MarkHotBean;

import java.util.List;

/**
 * 我的-交易中
 */
public class ReceiptedAdapter extends BaseQuickAdapter<MarkHotBean.Rows, BaseViewHolder> {
    private String type = "";
    private int getType = 0;
    private int mPosition;
    private CommAdapter.OutPrice mOutPrice;
    private SparseArray<CountDownTimer> timerArray;
//    private TextView tv_detail_day;
//    private TextView tv_detail_hour;
//    private TextView tv_detail_minute;
//    private TextView tv_detail_sends;


    public interface OutPrice {
        void outPrice(int position);
    }

    public void setOutPriceListener(CommAdapter.OutPrice outPrice) {
        this.mOutPrice = outPrice;
    }

    public ReceiptedAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<MarkHotBean.Rows> data) {
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
        TextView current_price = helper.getView(R.id.current_price);
        ImageView search_img = helper.getView(R.id.img_product);
//        tv_detail_day = helper.getView(R.id.tv_detail_day);
//        tv_detail_hour = helper.getView(R.id.tv_detail_hour);
//        tv_detail_minute = helper.getView(R.id.tv_detail_minute);
//        tv_detail_sends = helper.getView(R.id.tv_detail_sends);
        TextView shi_jian = helper.getView(R.id.shi_jian);
        TextView tv_min_price = helper.getView(R.id.tv_min_price);

        TextView tv_price_type = helper.getView(R.id.tv_price_type);

        if(!TextUtils.isEmpty(item.getProductIntro())) {
            tv_price_type.setText(StringUtil.subStringWithDot(20,item.getProductIntro()) + "");
        }
        tv_min_price.setText(item.getMiniDidPrice());

        Glide.with(mContext).load(item.getProductCover()).into(search_img);
        search_text2.setText(item.getProductName());
        Log.e("FF3422", "item.getSurplus():" + item.getSurplus());
        if(!TextUtils.isEmpty(item.getCurrentPrice())) {
            current_price.setText(item.getCurrentPrice());
        }

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
