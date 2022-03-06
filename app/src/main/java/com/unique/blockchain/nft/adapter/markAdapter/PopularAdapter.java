package com.unique.blockchain.nft.adapter.markAdapter;

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
import com.unique.blockchain.nft.view.activity.database.PopularDatabase;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<PopularDatabase.RowsBean>list=new ArrayList<>();

    public PopularAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<PopularDatabase.RowsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup path, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.popular_all, path, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ViewHolder viewHolder= (ViewHolder) holder;
        //viewHolder.popular_state.setText("艺术品");
        Glide.with(context).load(list.get(i).getProductImage()).into(viewHolder.popular_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView popular_img;
        public TextView  popular_state;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popular_img=itemView.findViewById(R.id.popular_img);
            popular_state=itemView.findViewById(R.id.popular_state);

        }
    }
}
