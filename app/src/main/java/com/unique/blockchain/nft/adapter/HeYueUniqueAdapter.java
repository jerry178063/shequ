package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.WalletAsstesBean;
import com.unique.blockchain.nft.domain.wallet.AssetsInfoBean;
import com.unique.blockchain.nft.net.JsonCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 合约资产ETH
 */
public class HeYueUniqueAdapter extends BaseQuickAdapter<HomeAsstesBean.Result.Balances, BaseViewHolder> {

    private LinSelectWalletListener linSelectWalletListener;
    private Context mContext;
    private List<HomeAsstesBean.Result.Balances> data;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private ImageView img_icon;
    private Context context;
    private TextView tv_monery_name, tv_monery_amount;


    public HeYueUniqueAdapter(int layoutResId, Context context, @Nullable @org.jetbrains.annotations.Nullable List<HomeAsstesBean.Result.Balances> data) {
        super(layoutResId, data);
        this.mContext = context;

    }

    public void setData(Context context, List<HomeAsstesBean.Result.Balances> data) {
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
    protected void convert(BaseViewHolder helper, HomeAsstesBean.Result.Balances item) {
        tv_monery_name = helper.getView(R.id.tv_monery_name);
        tv_monery_amount = helper.getView(R.id.tv_monery_amount);
        img_icon = helper.getView(R.id.img_icon);
        if (SpUtil.getInstance(mContext).getString("wallet_name").equals("UNIQUE")) {
//            if(item.getDenom().contains("ibc/")){
//
//                tv_monery_name.setText("USDT");
//            }else {
//                tv_monery_name.setText(item.getDenom() + "");
//            }
            Log.e("FF322hhh","item.getDenom():" + item.getDenom());
            OkGo.get(UrlConstant.baseUrlLian + "/unique/getAssetsInfo")
                    .headers("projectId", Constants.UNIQUE_HEADRE)
                    .params("assetsUtil",item.getDenom())
                    .execute(new JsonCallback<AssetsInfoBean>() {
                        @Override
                        public void onSuccess(AssetsInfoBean jsonObject, Call call, Response response) {
                            Log.e("FF322hhh","jsonObject:" + jsonObject);
                            if(jsonObject.getCode() == 200){
                                if(jsonObject.getResult() != null && jsonObject.getResult().size() > 0){
                                    tv_monery_name.setText(jsonObject.getResult().get(0).getChain().toUpperCase());
                                }
                            }

                        }

                        @Override
                        public void onFailure(String code, String message) {
                            super.onFailure(code, message);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Log.e("FF322hhh","e:" + e);
                        }
                    });
        }else {
            tv_monery_name.setText(item.getDenom() + "");
        }
        tv_monery_amount.setText(item.getAmount() + "");

        if (item.getAmount().contains(".")) {
            tv_monery_amount.setText(judgeNumber(item.getAmount() + ""));
        } else {
//            if(item.getDenom().contains("ibc/")) {
//                tv_monery_amount.setText(String.format("%.6f", Double.valueOf(item.getAmount())) + "");
//            }else {
                tv_monery_amount.setText(String.format("%.6f", Double.valueOf(item.getAmount()) / 1000000) + "");
//            }
        }
        if(tv_monery_name.getText().toString().equals("USDT")){
            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.usdt_img));
        }else if(tv_monery_name.getText().toString().equals("WBTC")){
            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wbtc_img));
        }
//        if (SpUtil.getInstance(context).getString("wallet_name").equals("UNIQUE")) {
//            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wallet_unique_img));
//        } else if (SpUtil.getInstance(context).getString("wallet_name").equals("ETH")) {
//            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wallet_eth_img));
//        } else if (SpUtil.getInstance(context).getString("wallet_name").equals("TRON")) {
//            img_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wallet_tron_img));
//        }
    }

    public String judgeNumber(String edt) {

        String temp = edt;
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        if (posDot > 0) {
            if (temp.length() - posDot > 6)//如果包含小数点
            {
//                edt.delete(posDot + 3, temp.length());//删除光标前的字符
                return edt.replace(edt.substring(posDot + 3, temp.length()), "");
            }else {
                return temp;
            }
        }
        return "";
    }
}
