package com.unique.blockchain.nft.adapter;

import android.content.Intent;
import android.os.CountDownTimer;
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
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.infrastructure.utils.CountTimeUtil;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;

import java.util.List;

/**
 * 市收藏-adapter
 */
public class ColllecAllAdapter extends BaseQuickAdapter<QuanDatabase.RowsBean, BaseViewHolder> {
    private String type = "";
    private int getType = 0;
    private int mPosition;
    private CommAdapter.OutPrice mOutPrice;
    private CountDownTimer countDownTimer;
    private SparseArray<CountDownTimer> timerArray;
    private int visit,choose;
    private String manager,mChoose,isCancel;
    private boolean isChoose;
    private Cancel cancel;

    public void setCollectVisit(int i,String manager) {
        this.visit = i;
        this.manager = manager;
        this.notifyDataSetChanged();
    }
    public void setChooseOrUn(int i,String choose) {
        this.choose = i;
        this.mChoose = choose;
        this.notifyDataSetChanged();
    }

    public interface OutPrice {
        void outPrice(int position);
    }
    public interface Cancel{
        void cancelChoose(String isCancel,int position);
    }

    public void setCancelChooseListener(Cancel cancel){
        this.cancel = cancel;
    }

    public void setOutPriceListener(CommAdapter.OutPrice outPrice) {
        this.mOutPrice = outPrice;
    }

    public ColllecAllAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<QuanDatabase.RowsBean> data) {
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
    protected void convert(BaseViewHolder helper, QuanDatabase.RowsBean item) {


        TextView search_text2 = helper.getView(R.id.search_text2);
        TextView search_text1 = helper.getView(R.id.search_text1);
        TextView shi_jian = helper.getView(R.id.shi_jian);
        ImageView search_img = helper.getView(R.id.search_img);
        ImageView pai_mai = helper.getView(R.id.pai_mai);
        LinearLayout lin_pai_mai = helper.getView(R.id.lin_pai_mai);
        Glide.with(mContext).load(item.getProductCover()).into(search_img);
        search_text2.setText(item.getNftName());
        ImageView img_choose_btn = helper.getView(R.id.img_choose_btn);
        TextView tv_current_price = helper.getView(R.id.tv_current_price);
        tv_current_price.setText(String.format("%.6f", Double.valueOf(item.getCurrentPrice())) + "");
        Log.e("FF3422", "mData:" + mData.size());

        if(manager != null && manager.equals("manager")) {

        }else {
            countDownTimer = new CountDownTimer(item.getSurplus(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    shi_jian.setText(CountTimeUtil.formatLongToTimeStr(millisUntilFinished / 1000) + "");
                }

                @Override
                public void onFinish() {
                    shi_jian.setText("已下架");
                }
            };
        }
        countDownTimer.start();
        timerArray.put(countDownTimer.hashCode(), countDownTimer);
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
        if (item.getSellState() == 1) {
            lin_pai_mai.setVisibility(View.VISIBLE);
        } else {
            lin_pai_mai.setVisibility(View.GONE);
        }
        if(visit == 0){//隐藏
            img_choose_btn.setVisibility(View.GONE);
        }else if(visit == 1){//显示
            img_choose_btn.setVisibility(View.VISIBLE);
        }

//        if(mChoose != null && mChoose.equals("choose")){
//            if(choose == 0){//全取消
//                img_choose_btn.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.unchoose_collect));
//                item.setChooseCollect(false);
//            }else if(choose == 1){//全选
//                img_choose_btn.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.choose_collect));
//                item.setChooseCollect(true);
//            }
//        }
        img_choose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isChooseCollect() == true){
                    if(cancel != null){
                        cancel.cancelChoose("cancel",helper.getLayoutPosition());
                    }
                }else {
                    if(cancel != null){
                        cancel.cancelChoose("on_cancel",helper.getLayoutPosition());
                    }
                }

            }
        });
        if(item.isChooseCollect()){
            img_choose_btn.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.choose_collect));
        }else {
            img_choose_btn.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.unchoose_collect));
        }
//        search_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (MoreClick.MoreClicks()) {
//                    Intent intent = new Intent(mContext, PaiActivity.class);
//                    intent.putExtra("nftId", item.getNftId());
//                    intent.putExtra("sellStatus",item.getSellState() + "");
//                    intent.putExtra("surplus", item.getSurplus() + "");
//                    mContext.startActivity(intent);
//                }
//            }
//        });
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
        } else if (item.getSellMode() == 2) {//出价
            pai_mai.setBackground(mContext.getResources().getDrawable(R.drawable.paimai));
        }
        TextView tv_transfer = helper.getView(R.id.tv_transfer);
        if (item.isTransfer()) {//正在转移
            tv_transfer.setVisibility(View.VISIBLE);
//            lin_pai_mai.setVisibility(View.GONE);
        } else {
            tv_transfer.setVisibility(View.GONE);
//            lin_pai_mai.setVisibility(View.VISIBLE);
        }
        setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                return false;
            }
        });
    }


}
