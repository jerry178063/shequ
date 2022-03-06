package com.unique.blockchain.nft.adapter.markAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unique.blockchain.nft.R;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;//item点击监听接口

    private List<String> histotyList = new ArrayList<String>();

    public HistorySearchAdapter(Context context, List<String>histotyList){
        this.mContext = context;
        this.histotyList = histotyList;

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private ImageView deleteImg;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.search_history_item_tv);
            deleteImg = (ImageView) itemView.findViewById(R.id.search_history_item_img);
            this.itemView = itemView;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_history_item_tv://点击历史纪录名称时调用
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemNameTvClick(v, (String) v.getTag());
                }
                break;
            case R.id.search_history_item_img://点击删除按钮时调用
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemDeleteImgClick(v, (String) v.getTag());
                }
                break;
            default:
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.history_search_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTv.setText(histotyList.get(position));
        holder.nameTv.setTag(histotyList.get(position));
        holder.deleteImg.setTag(histotyList.get(position));
        holder.nameTv.setOnClickListener(this);
        holder.deleteImg.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return histotyList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemNameTvClick(View v, String name);//点击历史纪录名称时
        void onItemDeleteImgClick(View v, String name);//点击删除按钮时
    }

}
