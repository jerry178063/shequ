package com.unique.blockchain.nft.adapter.markAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.RemenActivity;
import com.unique.blockchain.nft.view.activity.database.ReDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ReDatabase.RowsBean>getList=new ArrayList<>();
    private String company="";
    public ViewAdapter(Context context) {
        this.context = context;
    }

    public void setGetList(List<ReDatabase.RowsBean> getList) {
        this.getList = getList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_yy, parent, false);
            return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder1 viewHolder1= (ViewHolder1) holder;
            Glide.with(context).load(getList.get(position).getImage()).into(viewHolder1.item_img_yy);
            company=getList.get(position).getWalletAddr();
            //Log.e("kkll", "displayImage: "+company);
            viewHolder1.item_img_yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MoreClick.MoreClicks()) {
                    Intent intentl = new Intent(context, RemenActivity.class);
                    intentl.putExtra("companyWalletAddr", company);
                    intentl.putExtra("imgUrl", getList.get(position).getImage());
                    intentl.putExtra("companyIntro", getList.get(position).getCompanyIntro() + "");
                    intentl.putExtra("name", getList.get(position).getName());
                    SpUtil.getInstance(context).putString("walletAdd",getList.get(position).getWalletAddr() + "");
                    SpUtil.getInstance(context).putString("metadataHash",getList.get(position).getMetadataHash() + "");
                    context.startActivity(intentl);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (getList==null){
            return 0;
        }

        return  getList.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView item_img_yy;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            item_img_yy=itemView.findViewById(R.id.item_img_yy);

        }
    }
}
