package com.unique.blockchain.nft.view.activity.mark;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;

import butterknife.BindView;
import butterknife.OnClick;

    public class ProductImageActivity extends BaseActivity{
    @BindView(R.id.img_url)
    ImageView img_url;
    @BindView(R.id.remote_pdf_root)
    RelativeLayout remote_pdf_root;
    @BindView(R.id.pdf_url)
    WebView pdf_url;
    private String url;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_img;
    }

    @Override
    public void initUI() {
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        url = getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(url)){
            if(url.contains(".jpg") || url.contains(".png") || url.contains(".jpeg")){
                Glide.with(this).load(url).into(img_url);
                Log.e("FF23444343h","url:" + url);
            }else if(url.contains(".pdf")){
                WebSettings webSettings = pdf_url.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setAllowUniversalAccessFromFileURLs(true);
                pdf_url.loadUrl("file:///android_asset/index.html?" + url);
            }

        }

    }
    /*设置监听*/
    protected void setDownloadListener(String url) {
    }
    @OnClick({R.id.img_back})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;

        }
    }
    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

}
