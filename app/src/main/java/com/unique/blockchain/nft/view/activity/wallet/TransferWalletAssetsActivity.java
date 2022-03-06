package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.domain.wallet.EthResultBean;
import com.unique.blockchain.nft.domain.wallet.EthTongLianTransferBean;
import com.unique.blockchain.nft.domain.wallet.EthWallet;
import com.unique.blockchain.nft.domain.wallet.EthZiChanInfo;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.TiHuoSuccessDialog;
import com.unique.blockchain.nft.infrastructure.dialog.TransferSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.DecimalDigitsInputFilter;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.NftTiHuoActivity;
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
import wallet.core.jni.CoinType;
import wallet.core.jni.PrivateKey;
import wallet.core.jni.proto.Ethereum;

/**
 * 转账
 */
public class TransferWalletAssetsActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView, TransferSuccessDialog.OnBackZhujiClickView {

    static {
        System.loadLibrary("TrustWalletCore");
    }

    @BindView(R.id.tv_icon_more)
    TextView tv_icon_more;
    @BindView(R.id.ed_scan_content)
    EditText ed_scan_content;
    private String amount;
    @BindView(R.id.tc_can_get_gauss)
    TextView tc_can_get_gauss;
    User unique;
    private UserDao userDao;
    private String psd;
    //次数
    private String sequence1;
    private String account_num;
    @BindView(R.id.et_num)
    EditText et_num;
    SafeAdminDialog safeAdminDialog;
    String strFee;
    @BindView(R.id.img_back)
    LinearLayout img_back;
    TransferSuccessDialog transferSuccessDialog;
    private String wallet_name;//链上资产进来币种名称
    private String wallet_address,wallet_name_title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_transfer;
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
        wallet_name = getIntent().getStringExtra("wallet_name");
        wallet_address = SpUtil.getInstance(this).getString("wallet_address");
        wallet_name_title = getIntent().getStringExtra("wallet_name_title");
        address = wallet_address;

        if(!TextUtils.isEmpty(wallet_name)){
            Log.e("FFF444766","amount:" + amount);
            if(wallet_name.equals("UNIQUE")){
                if (amount != null) {
                    tc_can_get_gauss.setText("可用" + wallet_name_title + ": " + String.format("%.6f", Double.valueOf(amount)));
                } else {
                    tc_can_get_gauss.setText("可用" + wallet_name_title + ": 0.000000");
                }
            }else if(wallet_name.equals("ETH")){
                if (amount != null) {
                    tc_can_get_gauss.setText("可用"  + wallet_name_title + ": " + String.format("%.6f", Double.valueOf(amount)));
                } else {
                    tc_can_get_gauss.setText("可用" + wallet_name_title + ": 0.000000");
                }
            }

        }

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
        et_num.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6)});


        Log.e("ffff444", "wallet_name: " + wallet_name);
        Log.e("ffff444", "wallet_address: " + wallet_address);
        if (wallet_name != null) {
            if (wallet_name.equals("ETH")) {
                getTransferConfig();

            }
        }
        //获取资产信息
        getZiChanInfo();
    }

    private void getZiChanInfo() {

        OkGo.get(UrlConstant.baseUrlLian + "ethereum" + "/contracts")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .execute(new JsonCallback<EthZiChanInfo>() {
                    @Override
                    public void onSuccess(EthZiChanInfo jsonObject, Call call, Response response) {
                        Log.e("FFF4447g", "获取资产信息:" +jsonObject);
                        //请求成功
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF4447g", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    @Override
    public void initData() {
    }


    String address;

    private void getKeyCode() {

        Log.e("FFF4447", "url:" + UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + address);
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + address)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("FFF4447", "gaussKeysBean:" + new Gson().toJson(gaussKeysBean));
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

    @OnClick({R.id.rela_icon_more, R.id.img_saoyisao, R.id.tv_taansfer})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.rela_icon_more://矿工费-更多

//                        Intent intent = new Intent(TransferWalletAssetsActivity.this, MinerFeeTransterActivity.class);
//                        intent.putExtra("comeAdress", "zhuanzhang");
//                        startActivityForResult(intent, 1);

                break;
            case R.id.img_saoyisao://扫一扫
                Intent intent_sao = new Intent(TransferWalletAssetsActivity.this, CaptureActivity.class);
                startActivityForResult(intent_sao, 2222);
                break;
            case R.id.tv_taansfer://转账
//                        if (unique != null) {
//                            for (int i = 0; i < unique.getMObjectList().size(); i++) {
//                                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
//                                    address = unique.getMObjectList().get(i).getCoin_psd();
//                                }
//                            }
//                        }
                if (TextUtils.isEmpty(ed_scan_content.getText().toString())) {
                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "请输入接收地址");
                    return;
                }
                if (ed_scan_content.getText().toString().trim().equals(address)) {
                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "地址错误，请重新输入");
                    return;
                }
                if (wallet_name != null) {
                    if (wallet_name.equals("UNIQUE")) {
                        if (!ed_scan_content.getText().toString().trim().startsWith("unique")) {
                            ToastUtils.showToast(TransferWalletAssetsActivity.this, "地址错误，请重新输入");
                            return;
                        }
                    } else if (wallet_name.equals("ETH")) {
                        if (!ed_scan_content.getText().toString().trim().startsWith("0x")) {
                            ToastUtils.showToast(TransferWalletAssetsActivity.this, "地址错误，请重新输入");
                            return;
                        }
                    }
                }

                if (!TextUtils.isEmpty(et_num.getText().toString())) {
                    if (!TextUtils.isEmpty(amount)) {
                        if (wallet_name != null) {
                            if (wallet_name.equals("UNIQUE")) {
                                if (new BigDecimal(et_num.getText().toString()).compareTo(new BigDecimal(0)) == 0) {
                                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "输入的金额不能为0");
                                    return;
                                } else if (Double.parseDouble(et_num.getText().toString()) > Double.parseDouble(String.format("%.6f", Double.valueOf(amount)))) {
                                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "转账数量大于可用余额");
                                    return;
                                }
                            }else if (wallet_name.equals("ETH")) {
                                if (new BigDecimal(et_num.getText().toString()).compareTo(new BigDecimal(0)) == 0) {
                                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "输入的金额不能为0");
                                    return;
                                } else if (Double.parseDouble(et_num.getText().toString()) > Double.parseDouble(String.format("%.6f", Double.valueOf(amount)))) {
                                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "转账数量大于可用余额");
                                    return;
                                }
                            }
                        }
                    } else {
                        ToastUtils.showToast(TransferWalletAssetsActivity.this, "可用余额为0");
                        return;
                    }
                } else {
                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "转账数量不能为空");
                    return;
                }

                if (wallet_name != null) {
                    if (wallet_name.equals("UNIQUE")) {
                        if (ed_scan_content.getText().toString().trim().startsWith("unique")) {
                            if (Double.parseDouble(et_num.getText().toString()) > ((Double.parseDouble(String.format("%.6f", Double.valueOf(amount))) - Double.parseDouble(tv_icon_more.getText().toString().replace("UNIQUE", "").trim())))) {
                                ToastUtil.showShort(TransferWalletAssetsActivity.this, "矿工费不足");
                                return;
                            }
                            getKeyCode();
                        }
                    } else if (wallet_name.equals("ETH")) {
                        if (ed_scan_content.getText().toString().trim().startsWith("0x")) {
                            if (Double.parseDouble(et_num.getText().toString()) > ((Double.parseDouble(String.format("%.6f", Double.valueOf(amount))) - Double.parseDouble(tv_icon_more.getText().toString().replace("ETH", "").trim())))) {
                                ToastUtil.showShort(TransferWalletAssetsActivity.this, "矿工费不足");
                                return;
                            }
                        }
                    }
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
                transferSuccessDialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(null);
            handler = null;
        }
        if (transferSuccessDialog != null) {
            transferSuccessDialog.cancel();
            transferSuccessDialog = null;
        }
    }

    private void gaussTrans() {

        String shapsd = unique.getPsd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(i).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
                }
            }
        }

        BigDecimal bigDecimal1 = new BigDecimal(et_num.getText().toString());
        BigDecimal multiply1 = bigDecimal1.multiply(new BigDecimal(10).pow(6));
        long num = multiply1.longValue();


        BigDecimal strFeeBig = new BigDecimal(tv_icon_more.getText().toString().replace("UNIQUE", "").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
        if (bytes != null && sequence1 != null && account_num != null) {
            PrivateKey privateKey = new PrivateKey(bytes);
            String sign = TransactionUtil.INSTANCE.gaussSigningTransaction(privateKey,
                    ed_scan_content.getText().toString(),//接受者的gauss地址
                    num,//数量
                    Long.valueOf(strFee),
                    Long.valueOf(sequence1),
                    80000,//矿工费
                    Long.valueOf(account_num));
            Map map = new HashMap();
            map.put("tx", sign);
            Log.e("FFF4447", "sign:" + sign);
            OkGo.post(UrlConstant.baseUrlLian + "unique/broadcastStdTx")
                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                    .params("tx", sign)
                    .execute(new JsonCallback<BroadcastBean>() {
                        @Override
                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                            Log.e("FFF4447", "转账:" + new Gson().toJson(jsonObject));
                            //请求成功
                            if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
//                                    handler.sendEmptyMessage(123);
                                    if (transferSuccessDialog == null) {
                                        transferSuccessDialog = new TransferSuccessDialog(TransferWalletAssetsActivity.this);
                                        transferSuccessDialog.setOnclick(TransferWalletAssetsActivity.this);
                                        transferSuccessDialog.show();
                                    } else {
                                        transferSuccessDialog.show();
                                    }
                                    handler.sendEmptyMessage(123);
                                } else {
                                    ToastUtils.showToast(TransferWalletAssetsActivity.this, "转账失败");
                                    finish();
                                }

                            } else {
                                ToastUtils.showToast(TransferWalletAssetsActivity.this, "转账失败!");
                            }
                            safeAdminDialog.dismiss();
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
//
        } else {
            Log.e("FFF4447", "空:" + bytes + "--" + sequence1 + "--" + account_num);
        }
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
            if (wallet_name != null) {
                if (wallet_name.equals("UNIQUE")) {
                    if (ed_scan_content.getText().toString().trim().startsWith("unique")) {
                        gaussTrans();
                    }
                } else if (wallet_name.equals("ETH")) {
                    if (ed_scan_content.getText().toString().trim().startsWith("0x")) {
                        getTransferConfig12();
                    }
                }
            }
        } else {
            ToastUtils.showToast(TransferWalletAssetsActivity.this, "请输入正确的安全密码");
            return;
        }
        safeAdminDialog.dismiss();
    }

    EthResultBean ethResultBean;
    private String eth_limit;
    private String eth_gas;

    //获取eth交易参数配置
    private void getTransferConfig() {
        Log.e("FFF4447", "fromAddress:" + wallet_address);
        OkGo.get(UrlConstant.baseUrlLian + "ethereum/getTransferConfig")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("fromAddress", wallet_address)
                .execute(new JsonCallback<EthResultBean>() {
                    @Override
                    public void onSuccess(EthResultBean jsonObject, Call call, Response response) {
                        Log.e("FFF4447", "获取eth交易参数配置:" + new Gson().toJson(jsonObject));
                        //请求成功
                        if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                            ethResultBean = jsonObject;
                            eth_limit = new BigDecimal(jsonObject.getResult().getErc20_gas_limit()).stripTrailingZeros().toPlainString();
                            eth_gas = new BigDecimal(jsonObject.getResult().getGas_price_average()).stripTrailingZeros().toPlainString();
                            tv_icon_more.setText(String.format("%.6f", Double.valueOf(new BigDecimal(jsonObject.getResult().getErc20_gas_limit()).
                                    multiply(new BigDecimal(jsonObject.getResult().getGas_price_average())).divide(new BigDecimal("10").pow(18)).toPlainString())) + " ETH");
                        }
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

    //获取eth交易参数配置
    private void getTransferConfig12() {
        Log.e("FFF4447", "fromAddress:" + wallet_address);
        OkGo.get(UrlConstant.baseUrlLian + "ethereum/getTransferConfig")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("fromAddress", wallet_address)
                .execute(new JsonCallback<EthResultBean>() {
                    @Override
                    public void onSuccess(EthResultBean jsonObject, Call call, Response response) {
                        Log.e("FFF4447", "获取eth交易参数配置:" + new Gson().toJson(jsonObject));
                        //请求成功
                        if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                            ethResultBean = jsonObject;
                            eth_limit = new BigDecimal(jsonObject.getResult().getErc20_gas_limit()).stripTrailingZeros().toPlainString();
                            eth_gas = new BigDecimal(jsonObject.getResult().getGas_price_average()).stripTrailingZeros().toPlainString();
                            tv_icon_more.setText(String.format("%.6f", Double.valueOf(new BigDecimal(jsonObject.getResult().getErc20_gas_limit()).
                                    multiply(new BigDecimal(jsonObject.getResult().getGas_price_average())).divide(new BigDecimal("10").pow(18)).toPlainString())) + " ETH");
                            standTrans(ethResultBean);
                        }
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

    //EHT同链转账
    private void standTrans(EthResultBean ethResultBean) {
        EthResultBean.Result result = ethResultBean.getResult();

        String shapsd = unique.getPsd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("ETH")) {
                    bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(i).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
                }
            }
        }

        BigDecimal bigDecimal1 = new BigDecimal(et_num.getText().toString());
        BigDecimal multiply1 = bigDecimal1.multiply(new BigDecimal(10).pow(6));
        long num = multiply1.longValue();


        BigDecimal strFeeBig = new BigDecimal(tv_icon_more.getText().toString().replace("ETH", "").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
        PrivateKey privateKey = new PrivateKey(bytes);
        EthWallet ethWallet = new EthWallet(privateKey);
        EthWallet.ETHTxCommonParams ethTxCommonParams = new EthWallet.ETHTxCommonParams(eth_gas,
                eth_limit, new BigDecimal(result.getChainId()).toPlainString(), ed_scan_content.getText().toString()
        );
        BigDecimal multiply = new BigDecimal(et_num.getText().toString()).multiply(new BigDecimal("10").pow(18)).setScale(0,BigDecimal.ROUND_HALF_DOWN);
        String s = ethWallet.EthereumTransactionSigning(ethTxCommonParams, multiply.stripTrailingZeros().toPlainString(), new BigDecimal(result.getNonce()).toPlainString());

        transferPost(s);
        Log.e("FFDDD3","授权信息:" + s);
    }

    private void transferPost(String s) {
        OkGo.get(UrlConstant.baseUrlLian + "ethereum" +  "/sendRawTransaction")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("hex", s)
                .execute(new JsonCallback<EthTongLianTransferBean>() {
                    @Override
                    public void onSuccess(EthTongLianTransferBean jsonObject, Call call, Response response) {
                        Log.e("FFDDD3", "转账广播交易:" + jsonObject);
                        //请求成功
                        if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                            if(jsonObject.getResult().getHash() != null && jsonObject.getResult().getHash().startsWith("0x")){
                                if (transferSuccessDialog == null) {
                                    transferSuccessDialog = new TransferSuccessDialog(TransferWalletAssetsActivity.this);
                                    transferSuccessDialog.setOnclick(TransferWalletAssetsActivity.this);
                                    transferSuccessDialog.show();
                                } else {
                                    transferSuccessDialog.show();
                                }
                                handler.sendEmptyMessage(123);
                            }else {
                                ToastUtil.showShort(TransferWalletAssetsActivity.this,"转账失败");
                            }
                        }else {
                            ToastUtil.showShort(TransferWalletAssetsActivity.this,"转账失败");
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }


    @Override
    public void setBackZhujiClickView(String et_pass) {

    }
}
