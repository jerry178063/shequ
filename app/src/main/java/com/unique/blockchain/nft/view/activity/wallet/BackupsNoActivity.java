package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.dialog.CustormerServiceDialog;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;

import butterknife.BindView;
import butterknife.OnClick;

public class BackupsNoActivity extends BaseActivity implements CustormerServiceDialog.LoadOnclickView {
    private static final String TAG = "BackupsActivity";
    @BindView(R.id.iv_finish)
    LinearLayout iv_finish;
    @BindView(R.id.tv_backups_iknow)
    TextView tv_backups_iknow;
    private  CustormerServiceDialog   custormerServiceDialog;
    private String name;
    private String psd;
    private String add;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_backups_no;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this, 1);
    }

    @Override
    public void bindView(Bundle bundle) {
       name= getIntent().getStringExtra("name");
       psd= getIntent().getStringExtra("psd");
       add = getIntent().getStringExtra("add");
    }


    @OnClick({R.id.tv_backups_iknow,R.id.iv_finish})
    public void setOnClickView(View view){
            switch (view.getId()){
                case R.id.tv_backups_iknow:
//                    custormerServiceDialog = new CustormerServiceDialog(BackupsActivity.this, "2");
//                    custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot_ceate,getResources().getString(R.string.do_not_take_screenshots),getResources().getString(R.string.share_and_store_screenshots));
//                    custormerServiceDialog.setLoadOnclickView(BackupsActivity.this::setOnClickView);
//                    custormerServiceDialog.show();
                    if (MoreClick.MoreClicks()) {
                        Intent intent = new Intent(this, WordActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("psd", psd);
                        intent.putExtra("add", add);
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_finish:
                    finish();
                    break;
            }
    }

    @Override
    public void setOnClickView(String type) {
        if(custormerServiceDialog!=null){
            custormerServiceDialog.dismiss();
            if (MoreClick.MoreClicks()) {
                Intent intent = new Intent(this, WordActivity.class);
                intent.putExtra("name", name);
                Log.d(TAG, "setOnClickView: " + psd);
                intent.putExtra("psd", psd);
                intent.putExtra("add", getIntent().getStringExtra("add"));
                startActivity(intent);
            }
        }
    }
}