package com.unique.blockchain.nft.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.trade.GoTradeOwnerBean;

import java.util.List;

/**
 *持有者记录
 * */
public class TradeListRecordAdapter extends BaseQuickAdapter<GoTradeOwnerBean.Rows, BaseViewHolder> {

    public TradeListRecordAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<GoTradeOwnerBean.Rows> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoTradeOwnerBean.Rows item) {
        TextView tv_wallet_address=helper.getView(R.id.tv_wallet_address);
        tv_wallet_address.setText(item.getWalletAddr());
        TextView tv_count_time=helper.getView(R.id.tv_count_time);
        tv_count_time.setText(item.getCompletTime());

    }
}
