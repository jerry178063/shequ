package com.unique.blockchain.nft.view.activity.mark.ui;

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

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import com.unique.blockchain.nft.domain.mark.MarkOutPriceBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.trade.NftBuyBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPricePostPresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPricePresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IMarkOutPricePostPresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IMarkOutPricePresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceCallBack;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPricePostCallBack;
import com.unique.blockchain.nft.view.activity.wallet.TransferWalletAssetsActivity;

import java.math.BigDecimal;
import java.math.BigInteger;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**
 *
 *
 * 市场---购买
 * */

public class BuyMarkActivity extends BaseActivity implements IMarkOutPriceCallBack , SafeAdminDialog.OnSafeClickView, IMarkOutPricePostCallBack {
    static {
        System.loadLibrary("TrustWalletCore");
    }
    @BindView(R.id.tv_charge)
    TextView tv_charge;
    @BindView(R.id.img_back)
    LinearLayout img_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String title;
    IMarkOutPricePresenter iMarkOutPricePresenter;
    private String nftId,uniqueAdress;
    @BindView(R.id.tv_your_outprice)
    TextView tv_your_outprice;
    @BindView(R.id.tv_your_balance)
    TextView tv_your_balance;
    @BindView(R.id.tv_kuanggongfei)
    TextView tv_kuanggongfei;
    @BindView(R.id.tv_banshui)
    TextView tv_banshui;
    private String sign,balance;
    //次数
    private String sequence1;
    private String account_num;
    private String productType,strFee,price;
    User unique;
    private UserDao userDao;
    DaoSession daoSession = MyApplication.getDaoSession();
    SafeAdminDialog safeAdminDialog;
    IMarkOutPricePostPresenter iMarkOutPricePostPresenter;
    private String walletAddress,orderId;
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
    }

    @Override
    public void initData() {
        title = getIntent().getStringExtra("title");
        tv_title.setText("您将要购买" + "\"" + title+ "\"");
        productType = getIntent().getStringExtra("productType");
        nftId = getIntent().getStringExtra("nftId");
        uniqueAdress = getIntent().getStringExtra("uniqueAdress");
        orderId = getIntent().getStringExtra("orderId");


        iMarkOutPricePresenter = new IMarkOutPricePresenterImpl();
        iMarkOutPricePresenter.registerViewCallback(this);
        iMarkOutPricePresenter.getData(nftId,uniqueAdress);
        userDao = daoSession.getUserDao();
        Log.e("FF2344","NAME:" + UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, "")));
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    walletAddress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
        Log.e("FF2344","psd:" +unique.getPsd() );
        getMonery();
    }
    private void getAccount() {
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + walletAddress)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        dismissDialog();
                        //请求成功
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();

                            goSign();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        Log.e("FF88767", "onFailure:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF88767", "e:" + e);
                        dismissDialog();
                    }
                });
    }
    private void getMonery() {
        Log.e("FF88767","uniqueAdress:" + uniqueAdress);
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", walletAddress)
                .headers("projectId",Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        Log.e("FF88767","余额_homeAsstesBean:" + new Gson().toJson(homeAsstesBean));
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("uunique")) {
                                        Log.e("FF88767","余额:" + homeAsstesBean.getResult().getBalances().get(i).getAmount());
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getAmount();
                                        if(balance != null && !balance.equals("")) {
                                            tv_your_balance.setText(String.format("%.6f", Double.valueOf(balance) / 1000000) + "");
                                        }else {
                                            tv_your_balance.setText("0.000000");
                                        }
                                        break;
                                    }

                                }
                            }else {
                                tv_your_balance.setText("0.000000");
                            }
                        }else {
                            tv_your_balance.setText("0.000000");
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF88767","onFailure:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF88767","onError:" + e);
                    }
                });
    }

    @Override
    public void bindView(Bundle bundle) {

    }


    @OnClick({R.id.tv_charge,R.id.img_back,R.id.tv_nft_go})
    public void setOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_charge://现金支付
                ToastUtil.showShort(BuyMarkActivity.this,"敬请期待!");

                break;
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_nft_go://数字资产出价
                if(!TextUtils.isEmpty(tv_your_outprice.getText().toString())) {
                    if (Double.parseDouble(tv_your_outprice.getText().toString()) > Double.parseDouble(String.format("%.6f", Double.parseDouble(tv_your_balance.getText().toString())))) {
                        ToastUtils.showToast(BuyMarkActivity.this, "出价余额大于可用余额");
                        return;
                    }
                }else {
                    ToastUtil.showShort(BuyMarkActivity.this,"当前价格不能为空");
                    return;
                }
                getAccount();
                break;
        }
    }

    private void goSign() {
        NftBuyBean nftBuyBean = new NftBuyBean();
        nftBuyBean.setSender(walletAddress);
        nftBuyBean.setToken_id(nftId);
        NftBuyBean.Price price = new NftBuyBean.Price();
//
        String shapsd = unique.getPsd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        PrivateKey privateKey = new PrivateKey(bytes);

        BigDecimal strFeeBig = new BigDecimal("0.009");
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
        price.setDenom("uunique");
        if(!TextUtils.isEmpty(tv_your_outprice.getText().toString())) {
            BigDecimal bigDecimal = new BigDecimal(tv_your_outprice.getText().toString());
            BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal(10).pow(6));
            BigInteger outPrice = bigDecimal1.toBigInteger();
            price.setAmount(outPrice.toString());
            nftBuyBean.setPool_address(Constants.UNIQUE_ADDRESS);//先测试环境使用
        }else {
            return;
        }
        nftBuyBean.setPrice(price);
        sign = TransactionUtilNew.INSTANCE.uniqueBuyNftSigningTransaction(privateKey,
                Long.valueOf(strFee), 200000,
                nftBuyBean.toString(),
                Long.valueOf(sequence1),
                Long.valueOf(account_num));
        Log.e("FF33325","SIGN_BUY:" + sign);
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }
    //bute转16进制
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_buy;
    }

    @Override
    public void loadOutPriceData(MarkOutPriceBean markOutPriceBean) {
        if(markOutPriceBean != null && markOutPriceBean.getCode() == 200) {
            Log.e("FF344221", "markOutPriceBean:" + new Gson().toJson(markOutPriceBean));
            price = markOutPriceBean.getData().getCurrentPrice();
            tv_your_outprice.setText(markOutPriceBean.getData().getCurrentPrice() + "");
//            tv_your_balance.setText(markOutPriceBean.getData().getBalance() + "");
//            tv_kuanggongfei.setText(markOutPriceBean.getData().getMinersFee() + "");
            tv_banshui.setText(markOutPriceBean.getData().getRoyalty() + " ");

        }
    }

    @Override
    public void loadOutPriceFail() {

    }

    @Override
    public void setOnSafeClickView(String et_pass) {
        String pass_sha = AESEncrypt.sha(et_pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            showDialog();
            iMarkOutPricePostPresenter = new IMarkOutPricePostPresenterImpl();
            iMarkOutPricePostPresenter.registerViewCallback(this);
            Log.e("FF3332544","orderId:" + orderId);
            Log.e("FF3332544","orderId:" + orderId);
            Log.e("FF3332544","walletAddress:" + walletAddress);
            Log.e("FF3332544","price:" + price);
            Log.e("FF3332544","sign:" + sign);

            iMarkOutPricePostPresenter.getData(nftId,orderId,walletAddress,price,sign);
        } else {
            ToastUtils.showToast(BuyMarkActivity.this, "请输入正确的安全密码");
            return;
        }
    }
    //数字资产购买
    @Override
    public void loadOutPricePostData(CollectionBean markOutPriceBean) {
        dismissDialog();
        if(markOutPriceBean.getCode() == 200) {
            Log.e("FF33325", "购买成功:" + new Gson().toJson(markOutPriceBean));
            finish();
            Intent intent = new Intent(BuyMarkActivity.this, BuyPriceSuccessActivity.class);
            intent.putExtra("detail", "buy");
            startActivity(intent);
        }else {
            ToastUtil.showShort(BuyMarkActivity.this,"购买失败");
        }
    }

    @Override
    public void loadOutPricePostFail() {
        Log.e("FF33325","购买失败:");
    }
}
