package com.unique.blockchain.nft.adapter;

import android.content.Intent;
import android.os.CountDownTimer;
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
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.infrastructure.utils.CountTimeUtil;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;

import java.util.List;

/**
 * 市场-浏览-收藏品adapter
 * */
public class MarkCollectAdapter extends BaseQuickAdapter<QuanDatabase.RowsBean, BaseViewHolder> {
    private String type = "";
    private int getType = 0;
    private int mPosition;
    private CommAdapter.OutPrice mOutPrice;

    private SparseArray<CountDownTimer> timerArray;
    private CountDownTimer countDownTimer;

    public interface OutPrice {
        void outPrice(int position);
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
    public void setOutPriceListener(CommAdapter.OutPrice outPrice) {
        this.mOutPrice = outPrice;
    }

    public MarkCollectAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<QuanDatabase.RowsBean> data) {
        super(layoutResId, data);
        timerArray = new SparseArray<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, QuanDatabase.RowsBean item) {


        TextView search_text2 = helper.getView(R.id.search_text2);
        TextView search_text1 = helper.getView(R.id.search_text1);
        TextView shi_jian = helper.getView(R.id.shi_jian);
        ImageView search_img = helper.getView(R.id.search_img);
        ImageView pai_mai = helper.getView(R.id.pai_mai);
        LinearLayout lin_pai_mai = helper.getView(R.id.lin_pai_mai);
        Glide.with(mContext).load(item.getProductCover()).into(search_img);
        ImageView img_type = helper.getView(R.id.img_type);
        TextView tv_current_price = helper.getView(R.id.tv_current_price);
        tv_current_price.setText(String.format("%.6f", Double.valueOf(item.getCurrentPrice().toString())) + "");
        search_text2.setText(item.getNftName());
        countDownTimer =  new CountDownTimer(item.getSurplus(), 1000) {

            public void onTick(long millisUntilFinished) {
                shi_jian.setText(CountTimeUtil.formatLongToTimeStrNo(millisUntilFinished/1000) + "");
            }

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
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(mContext, PaiActivity.class);
                    intent.putExtra("nftId", item.getNftId());
                    intent.putExtra("surplus", item.getSurplus() + "");
                    if (item.getSellMode() == 1) {//购买
                        intent.putExtra("nftState", "5");
                    } else if (item.getSellMode() == 2) {//出价
                        intent.putExtra("nftState", "6");
                    }
                    mContext.startActivity(intent);
                }
            }
        });
        //拍卖
        lin_pai_mai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOutPrice != null) {
                    mOutPrice.outPrice(helper.getLayoutPosition());
                }
            }
        });
        if (item.getSellMode() == 1) {//购买
            pai_mai.setBackground(mContext.getResources().getDrawable(R.mipmap.gouwuche));
            img_type.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.gouwuche_new));
        } else if (item.getSellMode() == 2) {//出价
            pai_mai.setBackground(mContext.getResources().getDrawable(R.drawable.paimai));
            img_type.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.paimai_new));
        }
        TextView tv_transfer = helper.getView(R.id.tv_transfer);
        if(item.isTransfer()){//正在转移
            tv_transfer.setVisibility(View.VISIBLE);
            lin_pai_mai.setVisibility(View.GONE);
        }else {
            tv_transfer.setVisibility(View.GONE);
            lin_pai_mai.setVisibility(View.VISIBLE);
        }
    }
}
