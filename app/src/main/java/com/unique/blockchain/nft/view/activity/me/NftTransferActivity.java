package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.trade.NftTransferBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.TiHuoSuccessDialog;
import com.unique.blockchain.nft.infrastructure.dialog.TransferSuccessDialog;
import com.unique.blockchain.nft.infrastructure.dialog.ZhuanYiSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.TransferWalletAssetsActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

public class NftTransferActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView, ZhuanYiSuccessDialog.OnBackZhujiClickView {

    static {
        System.loadLibrary("TrustWalletCore");
    }

    @BindView(R.id.tv_icon_more)
    TextView tv_icon_more;
    @BindView(R.id.ed_scan_content)
    EditText ed_scan_content;
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
    String sign;
    ZhuanYiSuccessDialog zhuanYiSuccessDialog;
    @BindView(R.id.tv_taansfer)
    TextView tv_taansfer;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_nft_transfer;
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

        ed_scan_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    tv_taansfer.setBackground(getResources().getDrawable(R.drawable.zhiya_no_status));
                } else {
                    tv_taansfer.setBackground(getResources().getDrawable(R.drawable.cread_shape));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void initData() {
        nftId = getIntent().getStringExtra("nftId");
        cover = getIntent().getStringExtra("cover");
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        intro = getIntent().getStringExtra("intro");

        if (type != null) {
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

    private void getKeyCode() {
        Map map = new HashMap();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    address = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        Log.e("FF555", "url:" + UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + address);
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + address)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("FF555", "gaussKeysBean:" + new Gson().toJson(gaussKeysBean));
                        //请求成功
                        if (gaussKeysBean != null) {
                            Message message = Message.obtain();
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("responseText", gaussKeysBean.toString());
                            message.setData(bundle);

                        }
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF555", "onFailure:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.rela_icon_more, R.id.img_saoyisao, R.id.tv_taansfer, R.id.img_back})
    public void setOnclick(View view) {
        getKeyCode();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rela_icon_more://矿工费-更多

//                        Intent intent = new Intent(NftTransferActivity.this, MinerFeeTransterActivity.class);
//                        intent.putExtra("comeAdress", "zhuanzhang");
//                        startActivityForResult(intent, 1);

                break;
            case R.id.img_saoyisao://扫一扫
                Intent intent_sao = new Intent(NftTransferActivity.this, CaptureActivity.class);
                startActivityForResult(intent_sao, 2222);
                break;
            case R.id.tv_taansfer://转账
                if (ed_scan_content.getText().toString().trim().equals(address)) {
                    ToastUtils.showToast(NftTransferActivity.this, "地址错误，请重新输入");
                    return;
                }
                if (!ed_scan_content.getText().toString().trim().startsWith("unique")) {
                    ToastUtils.showToast(NftTransferActivity.this, "地址错误，请重新输入");
                    return;
                }
                if (safeAdminDialog == null) {
                    safeAdminDialog = new SafeAdminDialog(this);
                    safeAdminDialog.setOnclick(this);
                    safeAdminDialog.show();
                } else {
                    safeAdminDialog.show();
                }


                break;
        }
    }

    byte[] bytes;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zhuanYiSuccessDialog.dismiss();
            finish();
        }
    };

    private void gaussTrans() {

//        String shapsd = unique.getPsd();
//        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
//        if (unique != null) {
//            for (int i = 0; i < unique.getMObjectList().size(); i++) {
//                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
//                    bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(i).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
//                }
//            }
//        }


        NftTransferBean nftTransferBean = new NftTransferBean();
        nftTransferBean.setCate_id(type);
        nftTransferBean.setSender(address);
        nftTransferBean.setToken_id(nftId);
        nftTransferBean.setRecipient(ed_scan_content.getText().toString().trim());

//        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
//        PrivateKey privateKey = new PrivateKey(bytes);

        String shapsd = unique.getPsd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        PrivateKey privateKey = new PrivateKey(bytes);

        BigDecimal strFeeBig = new BigDecimal(tv_icon_more.getText().toString().replace("UNIQUE", "").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }

        sign = TransactionUtilNew.INSTANCE.uniqueTrasferNftSigningTransaction(privateKey,
                Long.valueOf(strFee), 200000,
                nftTransferBean.toString(),
                Long.valueOf(sequence1),
                Long.valueOf(account_num));
        Log.e("FF23WW", "转移签名:" + sign);
        Log.e("FF23WW", "nftId:" + nftId);
        Log.e("FF23WW", "sellWalletAddr:" + address);
        Log.e("FF23WW", "walletAddr:" + ed_scan_content.getText().toString().trim());
//        OkGo.get(UrlConstant.baseUrl + "api/user/transfer")
//                .params("nftId", nftId)
//                .params("sellWalletAddr", address)
//                .params("walletAddr", ed_scan_content.getText().toString().trim())
//                .params("chainInfo", sign)
//                .readTimeOut(10000)
//                .execute(new JsonCallback<CollectionBean>() {
//                    @Override
//                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
//                        Log.e("FF23WW", "转移:" + jsonObject);
//                        safeAdminDialog.dismiss();
//                        if(jsonObject.getCode() == 200) {
//                            if (zhuanYiSuccessDialog == null) {
//                                zhuanYiSuccessDialog = new ZhuanYiSuccessDialog(NftTransferActivity.this);
//                                zhuanYiSuccessDialog.setOnclick(NftTransferActivity.this);
//                                zhuanYiSuccessDialog.show();
//                            }else {
//                                zhuanYiSuccessDialog.show();
//                            }
//                            handler.sendEmptyMessage(23545);
//                        }else {
//                            ToastUtil.showShort(NftTransferActivity.this,jsonObject.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String code, String message) {
//                        super.onFailure(code, message);
//                        Log.e("FF23WW", "转移失败4444");
//                        safeAdminDialog.dismiss();
//                        ToastUtil.showShort(NftTransferActivity.this,message);
//                    }
//
//                    @Override
//                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
//                        super.onError(call, response, e);
//                        Log.e("FF23WW", "转移错误onError");
//                        safeAdminDialog.dismiss();
//                    }
//                });
        OkGo.post(UrlConstant.baseUrlLian + "unique/broadcastStdTx")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("tx", sign)
                .execute(new JsonCallback<BroadcastBean>() {
                    @Override
                    public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                        Log.e("FFF4447", "转移:" + new Gson().toJson(jsonObject));
                        //请求成功
                        if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                            if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                if (zhuanYiSuccessDialog == null) {
                                    zhuanYiSuccessDialog = new ZhuanYiSuccessDialog(NftTransferActivity.this);
                                    zhuanYiSuccessDialog.setOnclick(NftTransferActivity.this);
                                    zhuanYiSuccessDialog.show();
                                } else {
                                    zhuanYiSuccessDialog.show();
                                }
                                handler.sendEmptyMessage(23545);
                            } else {
                                ToastUtils.showToast(NftTransferActivity.this, "转移失败");
                            }
                            finish();
                        } else {
                            ToastUtils.showToast(NftTransferActivity.this, "转移失败!");
                        }
                        safeAdminDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF4447", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {

            if (requestCode == 1) {
                tv_icon_more.setText(data.getStringExtra("aaa") + " UNIQUE");
            }

        }
        if (requestCode == 2222) {

            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                ed_scan_content.setText(content);
            }

        }
    }

    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
//        for (int i = 0; i < userDao.loadAll().size(); i++) {
//            if (userDao.loadAll().get(i).getPsd().equals(pass_sha)) {
//                if (DoubleClickUtil.isCommonClick()) {
//                    gaussTrans();
//                }
//                break;
//            } else {
//                ToastUtils.showToast(TransferWalletAssetsActivity.this, "请输入正确的安全密码");
//                break;
//            }
//        }
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            gaussTrans();
        } else {
            ToastUtils.showToast(NftTransferActivity.this, "请输入正确的安全密码");
            return;
        }
    }

    @Override
    public void setBackZhujiClickView(String et_pass) {

    }
}

