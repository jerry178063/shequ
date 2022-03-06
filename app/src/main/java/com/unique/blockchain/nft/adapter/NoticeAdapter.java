package com.unique.blockchain.nft.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;

import java.util.List;
/**
 * 通知
 * */
public class NoticeAdapter extends BaseQuickAdapter<NoticeMessageBean.Rows, BaseViewHolder> {

    public NoticeAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<NoticeMessageBean.Rows> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeMessageBean.Rows item) {
        TextView tv_title=helper.getView(R.id.tv_title);
        TextView tv_time=helper.getView(R.id.tv_time);
        tv_title.setText(item.getTitle());
        tv_time.setText(item.getReleaseTime());
        ImageView img_hongdian = helper.getView(R.id.img_hongdian);
        if(item.getIsRead().equals("0")){
            img_hongdian.setVisibility(View.VISIBLE);
        }else {
            img_hongdian.setVisibility(View.GONE);
        }
    }
}
