package com.unique.blockchain.nft.adapter.markAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;

import java.util.ArrayList;
import java.util.List;

public class CommAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<QuanDatabase.RowsBean> list=new ArrayList<>();
    private String type="";
    private int getType=0;
    private int mPosition;
    private OutPrice mOutPrice;
    public interface OutPrice{
        void outPrice(int position);
    }

    public void setOutPriceListener(OutPrice outPrice){
        this.mOutPrice = outPrice;
    }

    public CommAdapter( Context context) {
        this.context = context;
    }

    public void setList(List<QuanDatabase.RowsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup path, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.search, path, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        mPosition = position;

        Glide.with(context).load(list.get(position).getProductImage()).into(viewHolder.search_img);
        viewHolder.search_text2.setText(list.get(position).getNftName());
        viewHolder.shi_jian.setText(list.get(position).getEndTime()+"");

       getType=list.get(position).getProductType();
        if (getType==1){
            viewHolder.search_text1.setBackground(context.getResources().getDrawable(R.drawable.shop1));
            viewHolder.search_text1.setText("票务");
        }else if (getType==2){
            viewHolder.search_text1.setBackground(context.getResources().getDrawable(R.drawable.shop2));
            viewHolder.search_text1.setText("收藏品");
        }else if (getType==3){
            viewHolder.search_text1.setBackground(context.getResources().getDrawable(R.drawable.shop));
            viewHolder.search_text1.setText("艺术品");
        }else if (getType==4){
            viewHolder.search_text1.setBackground(context.getResources().getDrawable(R.drawable.shop4));
            viewHolder.search_text1.setText("轻奢品");
        }
        type=list.get(position).getNftId();
        viewHolder.search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaiActivity.class);
                intent.putExtra("nftId",type + "");
                context.startActivity(intent);
            }
        });
        //拍卖
        viewHolder.lin_pai_mai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOutPrice != null){
                    mOutPrice.outPrice(mPosition);
                }
            }
        });
        if(list.get(position).getSellMode() == 1){//购买
            viewHolder.pai_mai.setBackground(context.getResources().getDrawable(R.mipmap.gouwuche));
        }else if(list.get(position).getSellMode() == 2){//出价
            viewHolder.pai_mai.setBackground(context.getResources().getDrawable(R.drawable.paimai));
        }
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }

        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView search_text2;
        public TextView search_text1;
        public TextView shi_jian;
        public ImageView search_img;
        private ImageView pai_mai;
        private LinearLayout lin_pai_mai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_text2=itemView.findViewById(R.id.search_text2);
            search_text1=itemView.findViewById(R.id.search_text1);
            shi_jian=itemView.findViewById(R.id.shi_jian);
            search_img=itemView.findViewById(R.id.search_img);
            pai_mai = itemView.findViewById(R.id.pai_mai);
            lin_pai_mai = itemView.findViewById(R.id.lin_pai_mai);
        }
    }
}
