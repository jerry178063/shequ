package com.unique.blockchain.nft.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.me.ExpressBean;

import java.util.List;

/**
 *收货地址
 * */
public class ExpressListAdapter extends BaseQuickAdapter<ExpressBean.Data.ExpressInfoList, BaseViewHolder> {

    private List<ExpressBean.Data.ExpressInfoList> mData;
    public ExpressListAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<ExpressBean.Data.ExpressInfoList> data) {
        super(layoutResId, data);
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressBean.Data.ExpressInfoList item) {
        TextView tv_expres_one=helper.getView(R.id.tv_expres_one);
        tv_expres_one.setText(item.getTime());
        TextView tv_expres_two=helper.getView(R.id.tv_expres_two);
        tv_expres_two.setText(item.getStatus());
        ImageView img_wuliu_yuan_one = helper.getView(R.id.img_wuliu_yuan_one);
        ImageView img_wuliu_yuan_two = helper.getView(R.id.img_wuliu_yuan_two);
        View view_line = helper.getView(R.id.view_line);

        if(!TextUtils.isEmpty(mData.toString()) && mData.size() > 0) {
            if (helper.getLayoutPosition() == (mData.size() - 1)) {
                view_line.setVisibility(View.GONE);
            }
        }

        if(helper.getLayoutPosition() == 0){
            tv_expres_one.setTextColor(mContext.getResources().getColor(R.color.color_FF7816));
            tv_expres_two.setTextColor(mContext.getResources().getColor(R.color.color_5B5B5B));
            img_wuliu_yuan_two.setVisibility(View.VISIBLE);
            img_wuliu_yuan_one.setVisibility(View.GONE);
        }else {
            tv_expres_one.setTextColor(mContext.getResources().getColor(R.color.color_9F9F9F));
            tv_expres_two.setTextColor(mContext.getResources().getColor(R.color.color_9F9F9F));
            img_wuliu_yuan_one.setVisibility(View.VISIBLE);
            img_wuliu_yuan_two.setVisibility(View.GONE);
        }
    }
}
