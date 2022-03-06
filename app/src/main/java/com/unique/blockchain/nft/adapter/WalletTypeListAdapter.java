package com.unique.blockchain.nft.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.java.StringUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.StringUtils;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.wallet.AssetsDetailActivity;
import com.unique.blockchain.nft.view.activity.wallet.ReceivePaymentActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 钱包管理adapter
 */
public class WalletTypeListAdapter extends BaseQuickAdapter<UserInfoItem, BaseViewHolder> {

    private LinSelectWalletListener linSelectWalletListener;
    private boolean selectWallet;
    private Context mContext;
    private List<UserInfoItem> data;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    private ImageView img_icon,img_erweima;
    private TextView tv_name,tv_wallet_address;
    private LinearLayout img_copy;


    public WalletTypeListAdapter(int layoutResId, Context context, @Nullable @org.jetbrains.annotations.Nullable List<UserInfoItem> data) {
        super(layoutResId, data);
        this.mContext = context;

    }
    public void setContext(Context context) {
        this.mContext = context;
        this.notifyDataSetChanged();
    }
    public void setData(Context context, List<UserInfoItem> data, UserDao userDao) {
        this.data = data;
        isClicks = new ArrayList<>();
        this.notifyDataSetChanged();
    }

    public void setOnItemSelectedWallet(LinSelectWalletListener linSelectWalletListener) {
        this.linSelectWalletListener = linSelectWalletListener;
    }


    public interface LinSelectWalletListener {
        void selectedWallet(int position);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfoItem item) {
        img_icon = helper.getView(R.id.img_icon);
        img_erweima = helper.getView(R.id.img_erweima);
        img_copy = helper.getView(R.id.img_copy);
        tv_name = helper.getView(R.id.tv_name);
        tv_wallet_address = helper.getView(R.id.tv_wallet_address);

        tv_name.setText(item.getCoin_name());
        tv_wallet_address.setText("钱包地址: " + StringUtils.subStringWithCenter2Dot(item.getCoin_psd().length(),item.getCoin_psd()));
        Log.e("FFD3323","ADDRESS:" + item.getCoin_psd());
        if(item.getCoin_name().equals("UNIQUE")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.wallet_unique_img));
        }else if(item.getCoin_name().equals("ETH")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.wallet_eth_img));
        }else if(item.getCoin_name().equals("TRON")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.wallet_tron_img));
        }else if(item.getCoin_name().equals("GAUSS")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.gauss_img));
        }else if(item.getCoin_name().equals("USDG")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.usdg_img));
        }else if(item.getCoin_name().equals("USDT")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.usdt_img));
        }else if(item.getCoin_name().equals("WBTC")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.wbtc_img));
        }else if(item.getCoin_name().equals("GDEX")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.gdex_img));
        }else if(item.getCoin_name().equals("CECF")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.cecf_img));
        }else if(item.getCoin_name().equals("SWAP")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.swap_img));
        }else if(item.getCoin_name().equals("GDY")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.gdy_img));
        }else if(item.getCoin_name().equals("GPB")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.logo_gpb));
        }else if(item.getCoin_name().equals("FEC")){
            img_icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.fec_img));
        }

        img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyContentToClipboard(item.getCoin_psd(), mContext);
                ToastUtils.getInstance(mContext).show("复制成功!");
            }
        });
        img_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_receive_payment = new Intent(mContext, ReceivePaymentActivity.class);
                intent_receive_payment.putExtra("wallet_address",item.getCoin_psd());
                mContext.startActivity(intent_receive_payment);
            }
        });

    }
    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
