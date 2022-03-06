package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.discover.GetArticleListBean;

import java.util.List;

/**
 * 帮助中心adapter
 * */
public class HelpCenterAdapter extends BaseQuickAdapter<GetArticleListBean.Rows, BaseViewHolder> {

    private LinSelectWalletListener linSelectWalletListener;
    private boolean selectWallet;
    private Context mContext;

    public HelpCenterAdapter(int layoutResId, Context context, @Nullable @org.jetbrains.annotations.Nullable List<GetArticleListBean.Rows> data) {
        super(layoutResId, data);
        this.mContext = context;
    }
    public void setOnItemSelectedWallet(LinSelectWalletListener linSelectWalletListener){
        this.linSelectWalletListener = linSelectWalletListener;
    }
    public interface LinSelectWalletListener{
        void selectedWallet();
    }

    @Override
    protected void convert(BaseViewHolder helper, GetArticleListBean.Rows item) {
        TextView tv_word=helper.getView(R.id.has_compler);
        tv_word.setText(item.getTitle());
        helper.addOnClickListener(R.id.has_compler);
        TextView has_compler_time=helper.getView(R.id.has_compler_time);
        has_compler_time.setText(item.getReleaseTime());


    }
}
