package com.unique.blockchain.nft.infrastructure.other;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.unique.blockchain.nft.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        if(!TextUtils.isEmpty((String) path)){
        //Glide 加载图片简单用法
        RequestOptions transform = new RequestOptions().centerCrop().transform(new GlideRoundTransform(context, 4));
        Glide.with(context).load(path).apply(transform).thumbnail(loadTransform(context, R.mipmap.pic_zwt,4)).into(imageView);
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        }

    }

    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, int radius) {

        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().centerCrop().transform(new GlideRoundTransform(context, radius)));

    }
}
