package com.unique.blockchain.nft.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordBean;
import com.unique.blockchain.nft.domain.wallet.TransactionRecordNewBean;
import com.unique.blockchain.nft.infrastructure.utils.TimeUtils;

import java.util.List;
/**
 * 转出*/
public class AllAsstesOutAdapter extends BaseQuickAdapter<TransactionRecordNewBean.Result.Records, BaseViewHolder> {
    private String walletAddress;
    List<TransactionRecordNewBean.Result.Records> data;
    public AllAsstesOutAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<TransactionRecordNewBean.Result.Records> data) {
        super(layoutResId, data);
        this.data = data;
    }

    public void setAddress(String walletAddress){
        this.walletAddress = walletAddress;
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionRecordNewBean.Result.Records item) {
        TextView tv_event=helper.getView(R.id.tv_event);
        TextView has_compler=helper.getView(R.id.has_compler);
        TextView tv_num=helper.getView(R.id.tv_num);
        TextView tv_from_address=helper.getView(R.id.tv_from_address);
        TextView tv_no_order = helper.getView(R.id.tv_no_order);
        TextView tv_tx_time = helper.getView(R.id.tv_tx_time);
        RelativeLayout rela_kgf = helper.getView(R.id.rela_kgf);
        rela_kgf.setVisibility(View.VISIBLE);
        TextView tv_mudi_address = helper.getView(R.id.tv_mudi_address);
        TextView tv_kgf = helper.getView(R.id.tv_kgf);
        tv_mudi_address.setText("目标地址:");
        if(!item.getAmount().contains("-")) {//转入
            tv_event.setText("转入");
            tv_num.setText( "+" + item.getAmount() + " UNIQUE");
            tv_from_address.setText(item.getOtherAdress());
            tv_mudi_address.setText("来源地址:");
        }else {//转出
            tv_event.setText("转出");
            tv_num.setText(item.getAmount() + " UNIQUE");
            tv_from_address.setText(item.getOtherAdress());
//            if(item.getTransationStatus() == 1){
//                rela_kgf.setVisibility(View.VISIBLE);
//            }else if(item.getTransationStatus() == 2){
//                rela_kgf.setVisibility(View.GONE);
//            }else if(item.getTransationStatus() == 0){
//                has_compler.setText("发送中");
//                has_compler.setTextColor(mContext.getResources().getColor(R.color.color_E05569_));
//            }
            tv_mudi_address.setText("目标地址:");
//            tv_kgf.setText(item.getFee() + " UNIQUE");
        }
        if(item.getStatus() == 0){
            has_compler.setText("已完成");
            has_compler.setTextColor(mContext.getResources().getColor(R.color.color_66D399));
        }else if(item.getStatus() == 1){
            has_compler.setText("已失败");
            has_compler.setTextColor(mContext.getResources().getColor(R.color.color_E77474));
        }

//        tv_no_order.setText(item.getIds());
        tv_tx_time.setText(TimeUtils.stampToDate(item.getTimestamp() + ""));

        helper.addOnClickListener(R.id.has_compler);

    }
}
