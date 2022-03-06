package com.unique.blockchain.nft.view.activity.mark.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 市场-购买-成功
 */
public class BuyPriceSuccessActivity extends BaseActivity {

    private String detail;
    @BindView(R.id.tv_succes)
    TextView tv_succes;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    private String name;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_goumai_success;
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
        name = getIntent().getStringExtra("name");
        if(!TextUtils.isEmpty(name)){
            tv_title.setText("出价");
        }

    }

    @Override
    public void initData() {
        detail = getIntent().getStringExtra("detail");
        if(detail != null){
            if(detail.equals("buy")){
                tv_succes.setText("购买成功");
                tv_detail.setVisibility(View.VISIBLE);
                tv_detail.setText("正在进行资产转移,移交到您的钱包");
            }else if(detail.equals("outprice")){
                tv_succes.setText("出价成功");
                tv_detail.setVisibility(View.VISIBLE);
                tv_detail.setText("拍卖间隔时间内无人再出价，即为成交，将移交到您的钱包。");
            }

        }

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
    public void bindView(Bundle bundle) {

    }
}
