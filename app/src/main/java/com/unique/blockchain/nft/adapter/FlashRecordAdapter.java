package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.home.FlashRecordBean;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 通知
 * */
public class FlashRecordAdapter extends BaseQuickAdapter<FlashRecordBean.Rows, BaseViewHolder> {
    private Context context;
    public FlashRecordAdapter(int layoutResId, Context context,@Nullable @org.jetbrains.annotations.Nullable List<FlashRecordBean.Rows> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FlashRecordBean.Rows item) {
        TextView tv_bizhong=helper.getView(R.id.tv_bizhong);//币种
        TextView tv_bizhong_status=helper.getView(R.id.tv_bizhong_status);//兑换状态
        TextView tv_amount=helper.getView(R.id.tv_amount);//兑换数量
        TextView tv_other_flash=helper.getView(R.id.tv_other_flash);//兑换为
        TextView tv_laiyuan_address=helper.getView(R.id.tv_laiyuan_address);//来源地址
        TextView tv_kuanggongfei=helper.getView(R.id.tv_kuanggongfei);//矿工费
        TextView tv_order_number=helper.getView(R.id.tv_order_number);//订单号
        TextView tv_order_time=helper.getView(R.id.tv_order_time);//订单时间

        tv_bizhong.setText(item.getTradingPair());
        if(item.getExchangeStatus() == 0){
            tv_bizhong_status.setText("兑换中");
            tv_bizhong_status.setTextColor(context.getResources().getColor(R.color.color_FF5602));
        }else if(item.getExchangeStatus() == 1){
            tv_bizhong_status.setText("成功");
            tv_bizhong_status.setTextColor(context.getResources().getColor(R.color.color_2CC815));
        }if(item.getExchangeStatus() == 2){
            tv_bizhong_status.setText("兑换失败");
            tv_bizhong_status.setTextColor(context.getResources().getColor(R.color.color_FF5602));
        }

        tv_order_number.setText(item.getOrderNo());
        tv_order_time.setText(item.getCompleteTime());
        tv_laiyuan_address.setText(item.getFromWalletAddress());
        tv_amount.setText(item.getExchangeNum());
        tv_other_flash.setText(new BigDecimal(item.getExchangeNum()).multiply(new BigDecimal(item.getExchangeRate())).toString());
    }
}
