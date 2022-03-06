package com.unique.blockchain.nft.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.node.DelegtionBeanRecord;
import com.unique.blockchain.nft.infrastructure.utils.TimeUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 投票区adapter
 * */
public class CommissionRecordAllAdapter extends BaseQuickAdapter<DelegtionBeanRecord.Result.Records, BaseViewHolder> {

    public CommissionRecordAllAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<DelegtionBeanRecord.Result.Records> data) {
        super(layoutResId, data);
        //获取当前网络时间
        String webUrl="http://www.baidu.com";//百度时间
        new Thread(new Runnable(){
            @Override
            public void run() {
                time=getNetworkTime(webUrl);
            }
        }).start();
    }
    private String time;
    public void setTime(String time){
        this.time = time;
    }
    /*
     * 获取当前网络时间
     */
    public static String getNetworkTime(String webUrl) {
        try {
            URL url=new URL(webUrl);
            URLConnection conn=url.openConnection();
            conn.connect();
            long dateL=conn.getDate();
            Date date=new Date(dateL);

            //以下是将时间转换成String类型并返回
            SimpleDateFormat dateFormat= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
            }
            return dateFormat.format(date);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";
    }
    @Override
    protected void convert(BaseViewHolder helper, DelegtionBeanRecord.Result.Records item) {
        TextView tv_word=helper.getView(R.id.has_compler);
        TextView tv_item_name = helper.getView(R.id.tv_item_name);
        RelativeLayout rela_gongyao = helper.getView(R.id.rela_gongyao);
        RelativeLayout rela_name = helper.getView(R.id.rela_name);

        if(!TextUtils.isEmpty(item.getEvent())){
            if(item.getEvent().equals("delegate")) {
                tv_word.setText("委托");
                tv_item_name.setText("委托数量:");
                rela_gongyao.setVisibility(View.VISIBLE);
                rela_name.setVisibility(View.VISIBLE);
            }else if(item.getEvent().equals("begin_unbonding")) {
                tv_word.setText("赎回");
                tv_item_name.setText("赎回数量:");
                rela_gongyao.setVisibility(View.VISIBLE);
                rela_name.setVisibility(View.VISIBLE);
            }else if(item.getEvent().equals("withdraw_delegator_reward")) {
                tv_word.setText("提取收益");
                tv_item_name.setText("收益数量:");
                rela_gongyao.setVisibility(View.VISIBLE);
                rela_name.setVisibility(View.VISIBLE);
            }else if(item.getEvent().equals("withdraw_validator_commission")) {
                tv_word.setText("提取佣金");
                tv_item_name.setText("佣金数量:");
                rela_gongyao.setVisibility(View.GONE);
                rela_name.setVisibility(View.GONE);
            }

        }
        if(item != null) {
            TextView tv_vala_name = helper.getView(R.id.tv_vala_name);
            if (item.getValidatorsinfo() != null && item.getValidatorsinfo().getMoniker() != null) {
                tv_vala_name.setText(item.getValidatorsinfo().getMoniker());
            }
            TextView tv_pushkey_name = helper.getView(R.id.tv_pushkey_name);
            if (item.getValidatorsinfo() != null && item.getValidatorsinfo().getPubKey() != null) {
                tv_pushkey_name.setText(item.getValidatorsinfo().getPubKey());
            }
            TextView tv_status = helper.getView(R.id.tv_status);

            if(time != null && item.getDelegatorTransfer() != null && item.getDelegatorTransfer().getCompletion_time() != null) {
                Log.e("FF8876785","time:" + time);
                Log.e("FF8876785","getCompletion_time:" + TimeUtils.stampToDate(item.getDelegatorTransfer().getCompletion_time()));

                int res = time.compareTo(TimeUtils.stampToDate(item.getDelegatorTransfer().getCompletion_time()));
                if (res < 0) {//网络时间大于实际赎回时间--赎回中
                    tv_status.setText("赎回中");
                } else {
                    if (item.getTransationStatus() == 1) {
                        tv_status.setText("成功");
                    } else if (item.getTransationStatus() == 2) {
                        tv_status.setText("失败");
                    }
                }
            }else {
                if (item.getTransationStatus() == 1) {
                    tv_status.setText("成功");
                } else if (item.getTransationStatus() == 2) {
                    tv_status.setText("失败");
                }
            }
            TextView tv_dela_num = helper.getView(R.id.tv_dela_num);
            tv_dela_num.setText(item.getAmount() + " GPB");
            TextView tv_kuanggongfei = helper.getView(R.id.tv_kuanggongfei);
            if(item.getFee() != null) {
                tv_kuanggongfei.setText(item.getFee() + " GPB");
            }
            TextView tv_tx_time = helper.getView(R.id.tv_tx_time);
            tv_tx_time.setText(TimeUtils.stampToDate(item.getTxTime()));

            helper.addOnClickListener(R.id.has_compler);
        }

    }
}
