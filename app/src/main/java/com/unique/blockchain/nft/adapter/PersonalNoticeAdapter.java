package com.unique.blockchain.nft.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;
import com.unique.blockchain.nft.domain.mark.PersonalMessBean;
import com.unique.blockchain.nft.infrastructure.utils.TimeUtils;

import java.util.List;

/**
 * 通知
 * */
public class PersonalNoticeAdapter extends BaseQuickAdapter<PersonalMessBean, BaseViewHolder> {

    public PersonalNoticeAdapter(int layoutResId, @Nullable @org.jetbrains.annotations.Nullable List<PersonalMessBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalMessBean item) {
        TextView tv_title=helper.getView(R.id.tv_title);
        TextView tv_time=helper.getView(R.id.tv_time);
        tv_title.setText(item.getTitle());
        ImageView img_hongdian =helper.getView(R.id.img_hongdian);

//        try {
//            tv_time.setText(TimeUtils.stampToDate(item.getReleaseTime() + ""));
//        }catch (Exception e){
//
//        }
        if(!TextUtils.isEmpty(item.getIsRead())) {
            if (item.getIsRead().equals("0") || item.getIsRead().equals("0.0")) {
                img_hongdian.setVisibility(View.VISIBLE);
            } else if (item.getIsRead().equals("1") || item.getIsRead().equals("1.0")) {
                img_hongdian.setVisibility(View.INVISIBLE);
            }
        }

        tv_time.setText(item.getReleaseTime() + "");
    }
}
