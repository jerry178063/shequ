package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.domain.wallet.ChainConfigBean;
import com.unique.blockchain.nft.dp.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 钱包管理adapter
 */
public class AddNewDigitalListAdapter extends BaseQuickAdapter<ChainConfigBean.Rows, BaseViewHolder> {

    private LinSelectWalletListener linSelectWalletListener;
    private boolean selectWallet;
    private Context mContext;
    private List<ChainConfigBean.Rows> data;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    private Context context;
    private ImageView img_icon,img_erweima,img_copy;
    private TextView tv_name;


    public AddNewDigitalListAdapter(int layoutResId, Context context, @Nullable @org.jetbrains.annotations.Nullable List<ChainConfigBean.Rows> data) {
        super(layoutResId, data);
        this.mContext = context;

    }

    public void setData(Context context, List<ChainConfigBean.Rows> data, UserDao userDao) {
        this.context = context;
        this.data = data;
        isClicks = new ArrayList<>();
        this.notifyDataSetChanged();
    }

    public void setOnItemSelectedWallet(LinSelectWalletListener linSelectWalletListener) {
        this.linSelectWalletListener = linSelectWalletListener;
    }


    public interface LinSelectWalletListener {
        void selectedWallet(String name);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChainConfigBean.Rows item) {
        img_icon = helper.getView(R.id.img_icon);
        img_erweima = helper.getView(R.id.img_erweima);
        img_copy = helper.getView(R.id.img_copy);
        tv_name = helper.getView(R.id.tv_name);

        tv_name.setText(item.getAssetsName());

        Glide.with(mContext).load(item.getAssetsLogo()).into(img_icon);
        img_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linSelectWalletListener != null){
                    linSelectWalletListener.selectedWallet(item.getAssetsName());
                }
            }
        });
    }

}
