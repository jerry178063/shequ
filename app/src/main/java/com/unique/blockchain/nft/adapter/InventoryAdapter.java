package com.unique.blockchain.nft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.me.Inventory;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.infrastructure.utils.StringUtils;
import com.unique.blockchain.nft.view.activity.me.LocationActivity;
import com.unique.blockchain.nft.widget.BaseRecyclerViewAdapter;
import com.unique.blockchain.nft.widget.RecyclerViewHolder;

import java.util.List;

import freemarker.template.utility.StringUtil;

public class InventoryAdapter extends BaseRecyclerViewAdapter<UserAdressListBean.Data> {

    private OnDeleteClickLister mDeleteClickListener;
    private List<UserAdressListBean.Data> list_personal;
    private Context context;
    String uniqueAdress;
    public InventoryAdapter(Context context, List<UserAdressListBean.Data> data,String uniqueAdress) {
//        super(context, data, R.layout.item_inventroy);
        super(context, data, R.layout.address_rec);
        this.context = context;
        this.uniqueAdress = uniqueAdress;
        this.list_personal = data;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, UserAdressListBean.Data bean, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        TextView personal_lian = (TextView) holder.getView(R.id.personal_lian);
        TextView personal_di = (TextView) holder.getView(R.id.personal_di);
        TextView personal_dian = (TextView) holder.getView(R.id.personal_dian);
        LinearLayout lin_addresslist_bg = (LinearLayout) holder.getView(R.id.lin_addresslist_bg);
        ImageView img_choosed = (ImageView) holder.getView(R.id.img_choosed);
        ImageView img_address_xiugai = (ImageView) holder.getView(R.id.img_address_xiugai);
        TextView tv_choosed = (TextView) holder.getView(R.id.tv_choosed);

        personal_lian.setText(StringUtils.subStringWithEndDot(bean.getUserName()));
        personal_dian.setText(bean.getMobile());
        personal_di.setText(bean.getHouseRegion().replace("-"," ") +  " " + bean.getAddress());


        if (list_personal.size() == 1) {
            if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
                tv_choosed.setVisibility(View.VISIBLE);
            }
        } else {
            if (SPUtils.getString(context, "wallet_address", null) != null) {
                if (SPUtils.getString(context, "wallet_address", null).equals(position + "")) {//是选中地址
                    tv_choosed.setVisibility(View.VISIBLE);
                } else {
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
                    tv_choosed.setVisibility(View.GONE);
                }
            } else {//还没有点击过
                if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                    tv_choosed.setVisibility(View.VISIBLE);
                } else {
//                    lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
                    tv_choosed.setVisibility(View.GONE);
                }
            }
        }
        if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
            tv_choosed.setVisibility(View.VISIBLE);
        } else {
            tv_choosed.setVisibility(View.GONE);
        }
        img_address_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocationActivity.class);
                intent.putExtra("uniqueAdress", uniqueAdress);
                intent.putExtra("user", list_personal.get(position).getUserName());
                intent.putExtra("mobile", list_personal.get(position).getMobile());
                intent.putExtra("region", list_personal.get(position).getHouseRegion());
                intent.putExtra("address", list_personal.get(position).getAddress());
                intent.putExtra("xiugai", "xiugai");
                intent.putExtra("id",list_personal.get(position).getId() + "");
                intent.putExtra("isDefault",list_personal.get(position).getIsDefault() + "");
                intent.putExtra("size",list_personal.size() + "");
                context.startActivity(intent);
            }
        });
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
