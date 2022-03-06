package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.me.Inventory;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.widget.BaseRecyclerViewAdapter;
import com.unique.blockchain.nft.widget.RecyclerViewHolder;

import java.util.List;

public class AddressReceiveAdapter extends BaseRecyclerViewAdapter<UserAdressListBean.Data> {

    private OnDeleteClickLister mDeleteClickListener;
    Context context;
    List<UserAdressListBean.Data> list_personal;
    public AddressReceiveAdapter(Context context, List<UserAdressListBean.Data> data) {
        super(context, data, R.layout.personal_rec);
        this.context = context;
        this.list_personal = data;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, UserAdressListBean.Data bean, int position) {
        View view = holder.getView(R.id.tv_delete);
//        view.setTag(position);
//        if (!view.hasOnClickListeners()) {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mDeleteClickListener != null) {
//                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
//                    }
//                }
//            });
//        }


        TextView personal_lian = (TextView) holder.getView(R.id.personal_lian);
        TextView personal_di = (TextView) holder.getView(R.id.personal_di);
        TextView personal_dian = (TextView) holder.getView(R.id.personal_dian);
        LinearLayout lin_addresslist_bg = (LinearLayout) holder.getView(R.id.lin_addresslist_bg);
        ImageView img_choosed = (ImageView) holder.getView(R.id.img_choosed);
        TextView personal_add_xiugai = (TextView) holder.getView(R.id.personal_add_xiugai);

        if (list_personal.size() == 1) {
            if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
                lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                img_choosed.setVisibility(View.VISIBLE);
            }
        } else {
            if (SPUtils.getString(context, "wallet_address", null) != null) {
                if (SPUtils.getString(context, "wallet_address", null).equals(position + "")) {//是选中地址
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                    img_choosed.setVisibility(View.VISIBLE);
                } else {
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
                    img_choosed.setVisibility(View.GONE);
                }
            } else {//还没有点击过
                if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                    img_choosed.setVisibility(View.VISIBLE);
                } else {
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
                    img_choosed.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
