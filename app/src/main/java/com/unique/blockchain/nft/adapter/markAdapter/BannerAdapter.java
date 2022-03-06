package com.unique.blockchain.nft.adapter.markAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.me.SettingActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BannerDatabase.DataBean> bannerl=new ArrayList<>() ;


    public BannerAdapter(List<BannerDatabase.DataBean> list, Context context) {
        this.context = context;
        this.bannerl = list;
    }

    public void setBannerl(List<BannerDatabase.DataBean> bannerl) {
        this.bannerl = bannerl;
        notifyDataSetChanged();
    }

    /*@Override
        public int getItemViewType(int position) {
            if (position==0){
                return 0;

            }else {
                return 1;
            }
        }*/
    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View butt_rec = LayoutInflater.from(context).inflate(R.layout.butt_yy, null, false);
        return new ViewHolder(butt_rec);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Log.e("FF23WW", "displayImage444: "+new Gson().toJson(bannerl));
        if(!TextUtils.isEmpty(bannerl.toString())) {
            viewHolder.butt_Banner.setImages(bannerl)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            BannerDatabase.DataBean path1 = (BannerDatabase.DataBean) path;
                            Glide.with(context).load(path1.getImageAddress()).into(imageView);
                            //Log.e("哈哈", "displayImage: "+path1.getImageAddress());
                        }
                    }).start();
        }
        viewHolder.butt_Banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                Log.e("FF23WW","position:" + i);
                Log.e("FF23WW","banner_url:" + bannerl.get(i).getImageLink());
                if(!TextUtils.isEmpty(bannerl.get(i).getImageLink())) {
                    Intent intent3 = new Intent(context, AdWebActivity.class);
                    intent3.putExtra("banner_url", bannerl.get(i).getImageLink());
                    intent3.putExtra("hideTitle", true);
                    context.startActivity(intent3);
                }
            }
        });
        //找第二个recy的id
      /*  mButtRec = mButtRec.findViewById(R.id.butt_rec);
        LinearLayoutManager linage = new LinearLayoutManager(context);
        linage.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewAdapter = new ViewAdapter(context);
        mButtRec.setAdapter(viewAdapter);*/

    }

    @Override
    public int getItemCount() {
        if (bannerl==null){
            return 0;
        }
        return 1;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Banner butt_Banner;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            butt_Banner = itemView.findViewById(R.id.butt_Banner);
        }
    }
}
