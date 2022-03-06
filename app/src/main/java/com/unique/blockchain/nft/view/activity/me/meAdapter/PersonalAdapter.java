package com.unique.blockchain.nft.view.activity.me.meAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.view.activity.me.LocationActivity;
import com.unique.blockchain.nft.view.activity.me.PersonalActivity;

import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<UserAdressListBean.Data> list_personal;
    boolean isChoose;
    String uniqueAdress;

    public PersonalAdapter(Context context, List<UserAdressListBean.Data> list_personal,String uniqueAdress) {
        this.context = context;
        this.list_personal = list_personal;
        this.uniqueAdress = uniqueAdress;

        for (int i = 0; i < list_personal.size(); i++) {
            if (list_personal.get(i).getIsDefault() == 1) {
                list_personal.get(i).setSelected(true);
            } else {
                list_personal.get(i).setSelected(false);
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup path, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.personal_rec, path, false);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.personal_lian.setText(list_personal.get(position).getUserName());
        viewHolder.personal_dian.setText(list_personal.get(position).getMobile());
        viewHolder.personal_di.setText(list_personal.get(position).getHouseRegion() + list_personal.get(position).getAddress());

//        if(list_personal.get(position).getIsDefault().equals("1")){//是默认地址
//            viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
//            viewHolder.img_choosed.setVisibility(View.VISIBLE);
//        }else {
//            viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
//            viewHolder.img_choosed.setVisibility(View.GONE);
//        }

        viewHolder.lin_addresslist_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.putString(context, "wallet_address", position + "");
                notifyDataSetChanged();
            }
        });
        viewHolder.personal_add_xiugai.setOnClickListener(new View.OnClickListener() {
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

        if (list_personal.size() == 1) {
            if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
                viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                viewHolder.img_choosed.setVisibility(View.VISIBLE);
                viewHolder.tv_choosed.setVisibility(View.VISIBLE);
            }
        } else {
            if (SPUtils.getString(context, "wallet_address", null) != null) {
                if (SPUtils.getString(context, "wallet_address", null).equals(position + "")) {//是选中地址
                    viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                    viewHolder.img_choosed.setVisibility(View.VISIBLE);
                    viewHolder.tv_choosed.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
                    viewHolder.img_choosed.setVisibility(View.GONE);
                    viewHolder.tv_choosed.setVisibility(View.GONE);
                }
            } else {//还没有点击过
                if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
                    viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_info));
                    viewHolder.img_choosed.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lin_addresslist_bg.setBackground(context.getResources().getDrawable(R.drawable.dialog_bianji_no));
                    viewHolder.img_choosed.setVisibility(View.GONE);
                }
            }
        }

//        if (list_personal.get(position).getIsDefault() == 1) {//是默认地址
//            viewHolder.tv_choosed.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.tv_choosed.setVisibility(View.GONE);
//        }
        viewHolder.tv_choosed.setVisibility(View.GONE);
        viewHolder.img_address_xiugai.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list_personal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView personal_lian;
        public TextView personal_di;
        public TextView personal_dian;
        private LinearLayout lin_addresslist_bg;
        private ImageView img_choosed;
        private TextView personal_add_xiugai;
        private TextView tv_choosed;
        private ImageView img_address_xiugai;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personal_lian = itemView.findViewById(R.id.personal_lian);
            personal_di = itemView.findViewById(R.id.personal_di);
            personal_dian = itemView.findViewById(R.id.personal_dian);
            lin_addresslist_bg = itemView.findViewById(R.id.lin_addresslist_bg);
            img_choosed = itemView.findViewById(R.id.img_choosed);
            personal_add_xiugai = itemView.findViewById(R.id.personal_add_xiugai);
            tv_choosed = itemView.findViewById(R.id.tv_choosed);
            img_address_xiugai = itemView.findViewById(R.id.img_address_xiugai);
        }
    }
}
