package com.unique.blockchain.nft.view.activity.trade;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.ShareNodeBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.CreatrQR;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.PhoneUtils;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;
import com.unique.blockchain.nft.infrastructure.utils.ToastaUtils;
import com.unique.blockchain.nft.net.JsonCallback;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 *
 * 分享
 * */

public class ShareCommissionActivity extends BaseActivity {


    @BindView(R.id.txt_left_title)
    TextView txt_left_title;
    @BindView(R.id.img_erweima)
    ImageView img_erweima;
    @BindView(R.id.tv_copy)
    TextView tv_copy;
    @BindView(R.id.tv_url)
    TextView tv_url;
    @BindView(R.id.txt_right_title)
    TextView txt_right_title;
    private UserDao userDao;
    User unique;
    String accountAddress,shareLoadurl;
    @BindView(R.id.tv_invate)
    TextView tv_invate;
    @BindView(R.id.tv_copy_url)
    TextView tv_copy_url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_commission;
    }

    @Override
    public void initUI() {
        CommonUtil.setStatusBarColor(this, 1);
        txt_right_title.setBackground(getResources().getDrawable(R.mipmap.icon_history_white));
        txt_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ShareCommissionActivity.this,ShareIncomeRecordActivity.class);
//                startActivity(intent);
                ToastUtil.showShort(ShareCommissionActivity.this, "敬请期待!");
            }
        });
        //判断是否有网络
        if (!PhoneUtils.isNetworkConnected(ShareCommissionActivity.this)) {
            ToastaUtils toastaUtils = new ToastaUtils(ShareCommissionActivity.this, getResources().getString(R.string.the_network_is_not_open));
            toastaUtils.show();
        } else {
            postData();
        }

    }

    private void postData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(ShareCommissionActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        HashMap<String, String> map = new HashMap<>();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    accountAddress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        OkGo.get(UrlConstant.baseCreateInportWallet +"system/setting/share")
                .params("gaussaddr",accountAddress)
                .params("platform","Android")
                .execute(new JsonCallback<ShareNodeBean>() {
                    @Override
                    public void onSuccess(ShareNodeBean shareNodeBean, Call call, Response response) {
                        Log.e("FF444T","ShareNodeBean:" +new Gson().toJson(shareNodeBean));
                        //请求成功
                        if(shareNodeBean != null && shareNodeBean.getCode() == 200) {
                            if(shareNodeBean.getData() != null && shareNodeBean.getData().getInvitationcode() != null && shareNodeBean.getData().getDownloadLink() != null){
                                tv_invate.setText(shareNodeBean.getData().getInvitationcode());
                                tv_url.setText(shareNodeBean.getData().getDownloadLink());
                                shareLoadurl = shareNodeBean.getData().getDownloadLink();
                                try {
                                    Bitmap bitmap = CreatrQR.createQRCode(shareLoadurl + "", 135);
                                    img_erweima.setImageBitmap(bitmap);
                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            try {
                                Bitmap bitmap = CreatrQR.createQRCode(shareLoadurl + "", 135);
                                img_erweima.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF444T","FAIL:" +code);
//                        try {
//                            Bitmap bitmap = CreatrQR.createQRCode(shareLoadurl + "", 135);
//                            img_erweima.setImageBitmap(bitmap);
//                        } catch (WriterException e) {
//                            e.printStackTrace();
//                        }
                    }
                });

    }

    @Override
    public void initData() {
        //生成二维码
//        img_erweima.setImageBitmap(qrCode);

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.txt_left_title,R.id.tv_copy_url})
    public void setOnclick(View view) {
//        if(FastClick.isFastClick()) {
            switch (view.getId()) {
                case R.id.txt_left_title://返回

                    finish();

                    break;
                case R.id.tv_copy_url://复制url地址
                    copyContentToClipboard(tv_url.getText().toString() + "", this);
//                ToastUtils.show("复制成功!");
                    ToastUtils.getInstance(this).show("复制成功!");
                    break;
            }
//        }
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
