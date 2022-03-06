package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.trade.NftDeleteOrderBean;
import com.unique.blockchain.nft.domain.trade.NftDeleteOrderPaiBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.MinerFeeTransterActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**
 * 撤销交易
 */
public class NftCancelBackActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView {

    static {
        System.loadLibrary("TrustWalletCore");
    }

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
    ImageView img_back;

    @BindView(R.id.tv_type_product)
    TextView tv_type_product;
    @BindView(R.id.img_product)
    ImageView img_product;
    String nftId;
    String cover;
    String type;
    String name;
    String intro;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_intro)
    TextView tv_intro;
    @BindView(R.id.tv_icon_more)
    TextView tv_icon_more;
    String sign, sellMode,orderId;
    PrivateKey privateKey;
    NftDeleteOrderBean nftTransferBean;
    NftDeleteOrderPaiBean nftDeleteOrderPaiBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nft_cancel_back;
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
        sellMode = getIntent().getStringExtra("sellMode");
        amount = getIntent().getStringExtra("amount");
        orderId = getIntent().getStringExtra("orderId");
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
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    address = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }


    }

    @Override
    public void initData() {
        nftId = getIntent().getStringExtra("nftId");
        cover = getIntent().getStringExtra("cover");
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        intro = getIntent().getStringExtra("intro");
        Log.e("FF3454", nftId + "--" + cover + "--" + type + "--" + name + "--" + intro);
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
        tv_intro.setText(intro);

        getKeyCode();
    }

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


    String address;


    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.rela_icon_more, R.id.img_back, R.id.tv_admin_cancel})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rela_icon_more://矿工费-更多

                Intent intent = new Intent(NftCancelBackActivity.this, MinerFeeTransterActivity.class);
                intent.putExtra("comeAdress", "zhuanzhang");
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_admin_cancel://确认撤销


                if (safeAdminDialog == null) {
                    safeAdminDialog = new SafeAdminDialog(this);
                    safeAdminDialog.setOnclick(this);
                    safeAdminDialog.show();
                } else {
                    safeAdminDialog.show();
                }
                break;
        }
//        }
    }

    byte[] bytes;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            goSign(shapsd);

        } else {
            ToastUtils.showToast(NftCancelBackActivity.this, "请输入正确的安全密码");
            return;
        }
    }

    private void goSign(String shapsd) {
        if (sellMode != null && sellMode.equals("1")) {
            nftTransferBean = new NftDeleteOrderBean();
            nftTransferBean.setCreator(address);
            nftTransferBean.setToken_id(nftId);
            nftTransferBean.setPool_address(Constants.UNIQUE_ADDRESS);



            byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
            if (unique != null) {
                for (int i = 0; i < unique.getMObjectList().size(); i++) {
                    if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                        bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(i).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
                    }
                }
            }
            byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
            privateKey = new PrivateKey(bytes);

            BigDecimal strFeeBig = new BigDecimal(tv_icon_more.getText().toString().replace("unique", "").trim());
            BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
            String resultFee = multiplyBifg.toString() + "";
            if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
                strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
            } else {
                strFee = multiplyBifg.toString() + "";
            }
            sign = TransactionUtilNew.INSTANCE.uniqueDeleteNftGudingOrderSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    nftTransferBean.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
        }else {
            nftDeleteOrderPaiBean = new NftDeleteOrderPaiBean();
            nftDeleteOrderPaiBean.setCreator(address);
            nftDeleteOrderPaiBean.setToken_id(nftId);
            nftDeleteOrderPaiBean.setPool_address(Constants.UNIQUE_ADDRESS);

            byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
            if (unique != null) {
                for (int i = 0; i < unique.getMObjectList().size(); i++) {
                    if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                        bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(i).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
                    }
                }
            }
            byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
            privateKey = new PrivateKey(bytes);

            BigDecimal strFeeBig = new BigDecimal(tv_icon_more.getText().toString().replace("unique", "").trim());
            BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
            String resultFee = multiplyBifg.toString() + "";
            if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
                strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
            } else {
                strFee = multiplyBifg.toString() + "";
            }
            sign = TransactionUtilNew.INSTANCE.uniqueDeleteNdtOrderSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    nftDeleteOrderPaiBean.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
        }

        Log.e("FF23WW", "sign:" + sign);
        Log.e("FF23WW", "nftId:" + nftId);
        OkGo.post(UrlConstant.baseUrl + "api/transaction/revoke")
                .params("nftId", nftId)
                .params("chainInfo", sign)
                .params("orderId",orderId)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, Call call, Response response) {
                        Log.e("FF23WW", "jsonObject撤销:" + new Gson().toJson(jsonObject));
                        if (jsonObject.getCode() == 200) {
                            ToastUtil.showShort(NftCancelBackActivity.this, "撤销成功!");
                            finish();
                        } else {
                            ToastUtil.showShort(NftCancelBackActivity.this, jsonObject.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        ToastUtil.showShort(NftCancelBackActivity.this, "撤销失败!");
                        Log.e("FF23WW", "onFailure:" + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtil.showShort(NftCancelBackActivity.this, "撤销失败!");
                    }
                });
    }

}

