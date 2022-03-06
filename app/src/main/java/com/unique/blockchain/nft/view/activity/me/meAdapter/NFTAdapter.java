package com.unique.blockchain.nft.view.activity.me.meAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.me.baen.JiaonftDatabase;

import java.util.ArrayList;
import java.util.List;

public class NFTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<JiaonftDatabase.RowsBean>listuu=new ArrayList<>();
    private int sellState;
    public NFTAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<JiaonftDatabase.RowsBean> listuu) {
        this.listuu = listuu;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        sellState=listuu.get(i).getSellState();//未提货：0， 提货中1，：完成提货'2,
        if (sellState==1){
            View inflate = LayoutInflater.from(context).inflate(R.layout.me_nft_dai, viewGroup, false);
            return new ViewHolder0(inflate) ;
        }else if (sellState==0){
            View inflate = LayoutInflater.from(context).inflate(R.layout.me_nft_zhong, viewGroup, false);
            return new ViewHolder1(inflate) ;
        }else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.me_nft_yi, viewGroup, false);
            return new ViewHolder2(inflate);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (sellState==1){
            ViewHolder0 viewHolder0= (ViewHolder0) holder;
            Glide.with(context).load(listuu.get(position).getProductImage()).into(viewHolder0.nft_dai_img);
            viewHolder0.nft_dai_name.setText( listuu.get(position).getProductName());
            viewHolder0.nft_dai_content.setText( listuu.get(position).getProductName());

        }else if (sellState==0){
            ViewHolder1 viewHolder1= (ViewHolder1) holder;
            Glide.with(context).load(listuu.get(position).getProductImage()).into(viewHolder1.nft_zhong_img);
            viewHolder1.nft_zhong_name.setText( listuu.get(position).getProductName());
            viewHolder1.nft_zhong_Low.setText((Integer) listuu.get(position).getMiniDidPrice()+"");
            viewHolder1.nft_zhong_high.setText((Integer) listuu.get(position).getCurrency()+"");
        }
        else if (sellState==2){
            ViewHolder2 viewHolder2= (ViewHolder2) holder;
            Glide.with(context).load(listuu.get(position).getProductImage()).into(viewHolder2.nft_yi_img);
            viewHolder2.nft_yi_name.setText( listuu.get(position).getProductName());
            viewHolder2.nft_yi_high.setText((Integer) listuu.get(position).getCurrency()+"");
            viewHolder2.nft_yi_Low.setText((Integer) listuu.get(position).getMiniDidPrice()+"");
        }
    }

    @Override
    public int getItemCount() {
        return listuu.size();
    }
    public static class ViewHolder0 extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView nft_dai_img;
        public TextView nft_dai_name;
        public TextView nft_dai_content;
        public TextView nft_dai_type;

        public ViewHolder0(@NonNull View itemView) {
            super(itemView);
            nft_dai_img=itemView.findViewById(R.id.nft_dai_img);
            nft_dai_name=itemView.findViewById(R.id.nft_dai_name);
            nft_dai_content=itemView.findViewById(R.id.nft_dai_content);
            nft_dai_type=itemView.findViewById(R.id.nft_dai_type);

        }
    }
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView nft_zhong_img;
        public TextView nft_zhong_high;
        public TextView nft_zhong_Low;
        public TextView nft_zhong_name;
        public TextView nft_zhong_type;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            nft_zhong_img=itemView.findViewById(R.id.nft_zhong_img);
            nft_zhong_high=itemView.findViewById(R.id.nft_zhong_high);
            nft_zhong_Low=itemView.findViewById(R.id.nft_zhong_Low);
            nft_zhong_name=itemView.findViewById(R.id.nft_zhong_name);
            nft_zhong_type=itemView.findViewById(R.id.nft_zhong_type);


        }
    }
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView nft_yi_img;
        public TextView nft_yi_name;
        public TextView nft_yi_high;
        public TextView nft_yi_Low;
        public TextView nft_yi_type;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            nft_yi_img=itemView.findViewById(R.id.nft_yi_img);
            nft_yi_name=itemView.findViewById(R.id.nft_yi_name);
            nft_yi_high=itemView.findViewById(R.id.nft_yi_high);
            nft_yi_Low=itemView.findViewById(R.id.nft_yi_Low);
            nft_yi_type=itemView.findViewById(R.id.nft_yi_type);

        }
    }
}
