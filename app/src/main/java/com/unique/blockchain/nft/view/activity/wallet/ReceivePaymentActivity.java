package com.unique.blockchain.nft.view.activity.wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.CreatrQR;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收款
 */

public class ReceivePaymentActivity extends BaseActivity {


    @BindView(R.id.txt_left_title)
    TextView txt_left_title;
    @BindView(R.id.img_erweima)
    ImageView img_erweima;
    @BindView(R.id.tv_copy)
    TextView tv_copy;
    @BindView(R.id.tv_url)
    TextView tv_url;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private UserDao userDao;
    User unique;
    String address,wallet_address,wallet_name_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_payment;
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
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("ffff444", "e: " + e);
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    address = unique.getMObjectList().get(i).getCoin_psd();


                }
            }
        }
        wallet_address = getIntent().getStringExtra("wallet_address");
        tv_url.setText(wallet_address);
        try {
            Bitmap bitmap = CreatrQR.createQRCode(tv_url.getText().toString(), 135);
            img_erweima.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        wallet_name_title = getIntent().getStringExtra("wallet_name_title");
        tv_title.setText(wallet_name_title);
    }

    @Override
    public void initData() {
        //生成二维码
//        img_erweima.setImageBitmap(qrCode);

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.img_back, R.id.tv_copy})
    public void setOnclick(View view) {
                switch (view.getId()) {
                    case R.id.img_back://返回
                        finish();
                        break;
                    case R.id.tv_copy://复制url地址
                        copyContentToClipboard(tv_url.getText().toString() + "", this);
                        ToastUtils.getInstance(this).show("复制成功!");
                        break;
                }
    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
