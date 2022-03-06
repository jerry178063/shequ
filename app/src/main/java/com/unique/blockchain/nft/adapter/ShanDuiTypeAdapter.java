package com.unique.blockchain.nft.adapter;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.wallet.ChainConfigBean;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordEthBean;
import com.unique.blockchain.nft.infrastructure.utils.TimeUtils;

import java.util.List;

public class ShanDuiTypeAdapter extends BaseQuickAdapter<ChainConfigBean.Rows, BaseViewHolder> {
    private String walletAddress;
    List<ChainConfigBean.Rows> data;
    private int position = 100;
    public ShanDuiTypeAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<ChainConfigBean.Rows> data) {
        super(layoutResId, data);
        this.data = data;
    }

    public void setAddress(String walletAddress){
        this.walletAddress = walletAddress;
        this.notifyDataSetChanged();
    }

    public void setPosition(int position){
        this.position = position;
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ChainConfigBean.Rows item) {
        TextView tv_name_shandui=helper.getView(R.id.tv_name_shandui);
        tv_name_shandui.setText(item.getAssetsName());

        if(position != 100) {
            if (helper.getLayoutPosition() == position) {
                tv_name_shandui.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));
            } else {
                tv_name_shandui.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }
}
