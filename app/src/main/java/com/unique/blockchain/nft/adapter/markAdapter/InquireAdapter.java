package com.unique.blockchain.nft.adapter.markAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.database.InquireDatabase;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;

import java.util.ArrayList;
import java.util.List;

public class InquireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<InquireDatabase.RowsBean> list=new ArrayList<>();

    private String type="";
    public InquireAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<InquireDatabase.RowsBean> list) {
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

        Glide.with(context).load(list.get(position).getProductImage()).into(viewHolder.search_img);
        viewHolder.search_text2.setText(list.get(position).getNftName());
        viewHolder.shi_jian.setText(list.get(position).getProductType()+"");

        type=list.get(position).getNftId();

        viewHolder.search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaiActivity.class);
                intent.putExtra("nftId",type + "");
                context.startActivity(intent);
            }
        });
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
        public TextView shi_jian;
        public ImageView search_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_text2=itemView.findViewById(R.id.search_text2);
            shi_jian=itemView.findViewById(R.id.shi_jian);
            search_img=itemView.findViewById(R.id.search_img);

        }
    }
}
