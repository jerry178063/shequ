package com.unique.blockchain.nft.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.mark.AuctionMarkBean;
import com.unique.blockchain.nft.infrastructure.utils.TimeUtils;

import java.util.List;

/**
 *竞标者
 * */
public class TradeListJingBiaoMoreAdapter extends BaseQuickAdapter<AuctionMarkBean, BaseViewHolder> {

    public TradeListJingBiaoMoreAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<AuctionMarkBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuctionMarkBean item) {
        TextView tv_unique_num=helper.getView(R.id.tv_unique_num);
        TextView tv_unique_add=helper.getView(R.id.tv_unique_add);
        TextView tv_unique_time=helper.getView(R.id.tv_unique_time);

        tv_unique_num.setText(item.getPrice() + " " + "UNIQUE");
        tv_unique_add.setText(item.getWalletAddr());
//        try {
//            tv_unique_time.setText(TimeUtils.stampToDate(item.getCompletTime() + ""));
//        }catch (Exception e){
//
//        }

        tv_unique_time.setText(item.getCompletTime() + "");
    }
}
