package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.space.exchange.biz.common.base.BaseActivity;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 钱包-跨链资产-跨回
 * */
public class CrossBackAssetsActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView{

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_icon_more)
    TextView tv_icon_more;
    SafeAdminDialog safeAdminDialog;
    @BindView(R.id.img_saoyisao)
    ImageView img_saoyisao;
    @BindView(R.id.ed_scan_content)
    EditText ed_scan_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_cross_back;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("跨回链上");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.tv_icon_more,R.id.tv_cross_back,R.id.img_saoyisao})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_icon_more://矿工费-更多

                Intent intent = new Intent(CrossBackAssetsActivity.this,MinerFeeActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_cross_back://确认
                if (safeAdminDialog == null) {
                    safeAdminDialog = new SafeAdminDialog(this);
                    safeAdminDialog.setOnclick(this);
                    safeAdminDialog.show();
                } else {
                    safeAdminDialog.show();
                }
                break;
            case R.id.img_saoyisao://扫描
                Intent intent_sao = new Intent(CrossBackAssetsActivity.this, CaptureActivity.class);
                startActivityForResult(intent_sao, 333);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2){

            if (requestCode == 1 ){
                Log.e("FFF333",data.getStringExtra("aaa"));
            }

        }
        if(requestCode == 333){

            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.e("FFF44","CONTENT:" + content);
                ed_scan_content.setText(content);
            }

        }
    }

    @Override
    public void setOnSafeClickView(String pass) {
        safeAdminDialog.dismiss();
    }
}
