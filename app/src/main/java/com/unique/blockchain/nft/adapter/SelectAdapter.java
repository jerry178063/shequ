package com.unique.blockchain.nft.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;

import java.util.List;

public class SelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SelectAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      TextView textView= helper.getView(R.id.tv_name);
      ImageView iv_logo= helper.getView(R.id.iv_logo);
        textView.setText(item);


        if(item.equals("UNIQUE")){
            iv_logo.setImageResource(R.mipmap.wallet_unique_img);
        }
//        if(item.equals("USDG")){
//            iv_logo.setImageResource(R.drawable.logo_usdg);
//        }
        if(item.equals("ETH")){
            iv_logo.setImageResource(R.mipmap.wallet_eth_img);
        }
        if(item.equals("TRON")){
            iv_logo.setImageResource(R.mipmap.wallet_tron_img);
        }
//        if(item.equals("ATOM")){
//            iv_logo.setImageResource(R.drawable.logo_atom);
//        }
//        if(item.equals("BTC")){
//            iv_logo.setImageResource(R.drawable.logo_btc);
//        }
//        if(item.equals("DOT")){
//            iv_logo.setImageResource(R.drawable.logo_dot);
//        }
//        if(item.equals("FIL")){
//            iv_logo.setImageResource(R.drawable.logo_fil);
//        }
//        if(item.equals("IGPC")){
//            iv_logo.setImageResource(R.drawable.logo_igpc);
//        }
//        if(item.equals("FEC")){
//            iv_logo.setImageResource(R.drawable.logo_fec);
//        }
    }
}
