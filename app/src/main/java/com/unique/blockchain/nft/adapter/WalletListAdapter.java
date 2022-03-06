package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.R;

import java.util.List;
/**
 * 钱包列表
 * */
public class WalletListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TextView tv_word;
    private String nmeColor;
    private Context context;
    List<String> data;
    public WalletListAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }
    public void setColor(Context context,String nmeColor){
        this.context = context;
        this.nmeColor = nmeColor;
        this.notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        tv_word=helper.getView(R.id.has_compler);
        tv_word.setText(item);
        helper.addOnClickListener(R.id.has_compler);
//        if(nmeColor != null && nmeColor.equals("yes")){
//            tv_word.setTextColor(context.getColor(R.color.color_303235));
//        }else {
//            tv_word.setTextColor(context.getColor(R.color.color_5678E5));
//        }
        Log.e("FKFK3","SPUtils.getString(context,\"witch_wallet\",null):" + SPUtils.getString(context,"witch_wallet",null));
        if(SPUtils.getString(context,"witch_wallet",null) != null){
            if(SPUtils.getString(context,"witch_wallet",null).equals(item)){
                tv_word.setTextColor(context.getColor(R.color.color_5678E5));
            }else {
                tv_word.setTextColor(context.getColor(R.color.color_303235));
            }
        }else {
            for (int i = 0; i < data.size(); i++) {
                if(i == 0){
                    tv_word.setTextColor(context.getColor(R.color.color_5678E5));
                }else {
                    tv_word.setTextColor(context.getColor(R.color.color_303235));
                }
            }
        }
    }
}
