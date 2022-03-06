package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class NftAdminHuoActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView {

    static {
        System.loadLibrary("TrustWalletCore");
    }

    @BindView(R.id.tv_icon_more)
    TextView tv_icon_more;
    private String amount;
    User unique;
    private UserDao userDao;
    private String psd;
    //次数
    private String sequence1;
    private String account_num;
    SafeAdminDialog safeAdminDialog;
    String strFee;
    @BindView(R.id.img_back)
    LinearLayout img_back;
    String nftId;
    String cover;
    String type;
    String name;
    String intro;
    @BindView(R.id.tv_type_product)
    TextView tv_type_product;
    @BindView(R.id.img_product)
    ImageView img_product;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_intro_product)
    TextView tv_intro_product;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_nft_admin_huo;
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
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        amount = getIntent().getStringExtra("amount");
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("ffff444", "e: " + e);
        }
        if (unique != null) {
            psd = unique.getPsd();
        }


    }

    @Override
    public void initData() {
        nftId = getIntent().getStringExtra("nftId");
        cover = getIntent().getStringExtra("cover");
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        intro = getIntent().getStringExtra("intro");

        if(type != null) {
            if (type.equals("1")) {
                tv_type_product.setText("产品类型: " + "票务");
            } else if (type.equals("2")) {
                tv_type_product.setText("产品类型: " + "收藏品");
            } else if (type.equals("3")) {
                tv_type_product.setText("产品类型: " + "艺术品");
            } else if (type.equals("4")) {
                tv_type_product.setText("产品类型: " + "轻奢品");
            }
        }
        Glide.with(this).load(cover).into(img_product);
        tv_title.setText(name);
        tv_intro_product.setText(intro);

    }


    String address;


    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.rela_icon_more, R.id.img_back,R.id.tv_admin_shouhuo})
    public void setOnclick(View view) {
                switch (view.getId()) {
                    case R.id.img_back:
                        finish();
                        break;
                    case R.id.rela_icon_more://矿工费-更多

//                        Intent intent = new Intent(NftAdminHuoActivity.this, MinerFeeTransterActivity.class);
//                        intent.putExtra("comeAdress", "zhuanzhang");
//                        startActivityForResult(intent, 1);
                        break;
                    case R.id.tv_admin_shouhuo://确认收货
                        getConfirmTake();
                        break;
                }
//        }
    }

    private void getConfirmTake() {
        Log.e("FF23WW","nftId:" + nftId);
        OkGo.get(UrlConstant.baseUrl + "api/user/confirm/take")
                .params("nftId",nftId)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, Call call, Response response) {
                        Log.e("FF23WW","jsonObject:" + new Gson().toJson(jsonObject));
                        if(jsonObject.getCode() == 200){
                            ToastUtil.showShort(NftAdminHuoActivity.this,"确认收货成功!");
                            finish();
                        }else {
                            ToastUtil.showShort(NftAdminHuoActivity.this,jsonObject.getMsg());
                        }

                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        ToastUtil.showShort(NftAdminHuoActivity.this,message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtil.showShort(NftAdminHuoActivity.this,"确认收货失败!");
                    }
                });

    }

    byte[] bytes;

    private void gaussTrans() {

        String shapsd = unique.getPsd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(i).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
                }
            }
        }



        BigDecimal strFeeBig = new BigDecimal(tv_icon_more.getText().toString().replace("GPB","").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            gaussTrans();
        } else {
            ToastUtils.showToast(NftAdminHuoActivity.this, "请输入正确的安全密码");
            return;
        }
    }

}

