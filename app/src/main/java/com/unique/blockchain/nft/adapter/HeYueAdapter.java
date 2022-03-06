package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.node.WalletAsstesBean;
import com.unique.blockchain.nft.domain.wallet.ChainConfigBean;
import com.unique.blockchain.nft.dp.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 合约资产ETH
 */
public class HeYueAdapter extends BaseQuickAdapter<WalletAsstesBean.Result.Balances, BaseViewHolder> {

    private LinSelectWalletListener linSelectWalletListener;
    private Context mContext;
    private List<WalletAsstesBean.Result.Balances> data;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    private Context context;
    private TextView tv_monery_name,tv_monery_amount;
    private ImageView img_icon;


    public HeYueAdapter(int layoutResId, Context context, @Nullable @org.jetbrains.annotations.Nullable List<WalletAsstesBean.Result.Balances> data) {
        super(layoutResId, data);
        this.mContext = context;

    }

    public void setData(Context context, List<WalletAsstesBean.Result.Balances> data) {
        this.context = context;
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void setOnItemSelectedWallet(LinSelectWalletListener linSelectWalletListener) {
        this.linSelectWalletListener = linSelectWalletListener;
    }


    public interface LinSelectWalletListener {
        void selectedWallet(String name);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletAsstesBean.Result.Balances item) {
        tv_monery_name = helper.getView(R.id.tv_monery_name);
        tv_monery_amount = helper.getView(R.id.tv_monery_amount);
        img_icon = helper.getView(R.id.img_icon);
        tv_monery_name.setText(item.getCoinType());
        tv_monery_amount.setText(item.getBalance());
        Log.e("FF88767h4","balance:" + item.getBalance());
        if(item.getBalance().contains(".")) {
            tv_monery_amount.setText(judgeNumber(item.getBalance() + ""));
        }else {
            tv_monery_amount.setText(String.format("%.6f", Double.valueOf(item.getBalance())) + "");
        }


        if(item.getCoinType().equals("USDT")){
            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.usdt_img));
        }else if(item.getCoinType().equals("WBTC")){
            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wbtc_img));
        }
//        else if(SpUtil.getInstance(context).getString("wallet_name").equals("TRON")){
//            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wallet_tron_img));
//        }
    }
    public String  judgeNumber(String edt) {

        String temp = edt;
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        if (posDot > 0){
            if (temp.length() - posDot > 6)//如果包含小数点
            {
//                edt.delete(posDot + 3, temp.length());//删除光标前的字符
                return edt.replace(edt.substring(posDot + 3, temp.length()),"");
            }else {
                return  temp;
            }
        }
        return "";
    }
}
