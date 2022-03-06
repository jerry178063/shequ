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
import com.unique.blockchain.nft.infrastructure.utils.CountTimeUtil;

import java.util.List;

/**
 * 市场-浏览-热门全部adapter
 */
public class ReMenAllAdapter extends BaseQuickAdapter<MarkHotBean.Rows, BaseViewHolder> {
    private String type = "";
    private int getType = 0;
    private int mPosition;
    private CommAdapter.OutPrice mOutPrice;
    private CountDownTimer countDownTimer;
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

    public ReMenAllAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<MarkHotBean.Rows> data) {
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
        TextView tv_product_intro = helper.getView(R.id.tv_product_intro);//出价还是购买
        ImageView img_outprice = helper.getView(R.id.img_outprice);
        LinearLayout lin_out_price = helper.getView(R.id.lin_out_price);
        TextView tv_price_type = helper.getView(R.id.tv_price_type);

        Glide.with(mContext).load(item.getProductCover()).into(search_img);
        search_text2.setText(item.getNftName());
        Log.e("FF3422", "item.getSurplus():" + item.getSurplus());
        if(!TextUtils.isEmpty(item.getCurrentPrice())) {
            current_price.setText(item.getCurrentPrice());
        }

        countDownTimer = new CountDownTimer(item.getSurplus(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                shi_jian.setText(CountTimeUtil.formatLongToTimeStrNo(millisUntilFinished/1000) + "");
            }

            @Override
            public void onFinish() {
                shi_jian.setText("已下架");
            }
        };
        countDownTimer.start();
        timerArray.put(countDownTimer.hashCode(),countDownTimer);
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
        if(item.getSellMode() == 2){
            tv_product_intro.setText("出价");
            img_outprice.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.paimai));
            tv_price_type.setText("当前价格 (UNIQUE)");
        }else if(item.getSellMode() == 1){
            tv_product_intro.setText("购买");
            tv_price_type.setText("固定价 (UNIQUE)");
            img_outprice.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.gouwuche));
        }
        lin_out_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOutPrice != null) {
                    mOutPrice.outPrice(helper.getLayoutPosition());
                }
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
