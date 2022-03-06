package com.unique.blockchain.nft.view.activity.mark;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.unique.blockchain.nft.domain.mark.BigAddressBean;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.node.DuiHuanBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.domain.wallet.ChainConfigBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddDigitalDialog;
import com.unique.blockchain.nft.infrastructure.dialog.HomeNftTypeDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.ShanDuiNftTypeDialog;
import com.unique.blockchain.nft.infrastructure.dialog.TransferSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.infrastructure.utils.UserDaoUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.AddDigitalAssetsActivity;
import com.unique.blockchain.nft.view.activity.wallet.TransferWalletAssetsActivity;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.CoinType;
import wallet.core.jni.PrivateKey;

/**
 * 闪兑
 */
public class FlashBuyActivity extends BaseActivity implements ShanDuiNftTypeDialog.OnSafeClickView, ShanDuiNftTypeDialog.ItemClickView, SafeAdminDialog.OnSafeClickView {
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private ShanDuiNftTypeDialog shanDuiNftTypeDialog;
    SafeAdminDialog safeAdminDialog;
    private List<ChainConfigBean.Rows> rows = new ArrayList<>();
    @BindView(R.id.tv_name_choose)
    TextView tv_name_choose;
    @BindView(R.id.tv_two_name)
    TextView tv_two_name;
    private int choose_type;
    @BindView(R.id.et_usdg)
    EditText et_usdg;
    @BindView(R.id.et_gauss)
    EditText et_gauss;
    //市值
    private BigDecimal tokenAmount = new BigDecimal("12");
    private BigDecimal rate = new BigDecimal("12");
    //次数
    private String sequence1;
    private String account_num;
    private String to_Address;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_zichan_name)
    TextView tv_zichan_name;
    private String balance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash_dui;
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
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(FlashBuyActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
        //获取资产市值
        queryTokenAmount();
        //获取交易需要的次数
        getSequence();
        //查询地址
        getAddress();
        et_usdg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().startsWith(".")) {

                    et_usdg.setText("");

                } else {
                    if (!charSequence.toString().isEmpty()) {

                        if (tokenAmount != null && rate != null) {
                            BigDecimal gauss = tokenAmount.multiply((new BigDecimal("1").add(rate.divide(new BigDecimal("100")))));
                            BigDecimal divide = new BigDecimal(charSequence.toString()).divide(gauss, 6, RoundingMode.HALF_UP);
                            et_gauss.setText(divide.toPlainString());
                        }

                    } else {
                        et_gauss.setText("");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getAddress() {
        OkGo.post(UrlConstant.baseUrl + "mapping/walletAddr")
                .params("toExchange", tv_name_choose.getText().toString().trim())
                .execute(new JsonCallback<BigAddressBean>() {
                    @Override
                    public void onSuccess(BigAddressBean jsonObject, Call call, Response response) {
                        Log.e("FF23WW","jsonObject:" + jsonObject);
                        //请求成功
                        if (jsonObject.getCode() == 200) {
                            if (!TextUtils.isEmpty(jsonObject.getData())) {
                                to_Address = jsonObject.getData() + "";
                                Log.e("FF23WW","to_Address:" + to_Address);
                            }
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

    private void getSequence() {
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + uniqueAdress)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("FFF444766", "gaussKeysBean:" + new Gson().toJson(gaussKeysBean));
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

    private void queryTokenAmount() {
    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {
        OkGo.get(UrlConstant.baseUrl + "chainConfig/assetsList")
                .execute(new JsonCallback<ChainConfigBean>() {
                    @Override
                    public void onSuccess(ChainConfigBean jsonObject, Call call, Response response) {
                        if (jsonObject.getCode() == 200) {
                            Log.e("FFS333", "jsonObject:" + new Gson().toJson(jsonObject));

                            if (jsonObject.getRows() != null && jsonObject.getRows().size() > 0) {
                                if (rows != null) {
                                    rows.clear();
                                }
                                rows.addAll(jsonObject.getRows());
                                tv_name_choose.setText(jsonObject.getRows().get(0).getAssetsName());
//                                tv_two_name.setText(jsonObject.getRows().get(0).getAssetsName());
                                tv_zichan_name.setText("可用" + jsonObject.getRows().get(0).getAssetsName() + ":");
                                minUnit = jsonObject.getRows().get(0).getMinUnit();
                            }
                            getMonery();
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

    private List<HomeAsstesBean.Result.Balances> balances = new ArrayList<>();

    private void getMonery() {
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", uniqueAdress)
                .headers("projectId", Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        Log.e("FF88767h4", "余额:" + new Gson().toJson(homeAsstesBean));
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {
                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (balances != null) {
                                        balances.clear();
                                    }
                                    balances.addAll(homeAsstesBean.getResult().getBalances());
                                    Log.e("FF88767h", "balances:" + new Gson().toJson(balances));
                                    if (!TextUtils.isEmpty(minUnit)) {
                                        if (minUnit.equals(homeAsstesBean.getResult().getBalances().get(i).getDenom())) {
                                            balance = homeAsstesBean.getResult().getBalances().get(i).getAmount();
                                            tv_balance.setText(String.format("%.6f", Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount())/1000000) + "");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF88767", "onFailure:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF88767", "onError:" + e);
                    }
                });
    }


    @OnClick({R.id.img_back, R.id.lin_typechoose, R.id.rela_type_two, R.id.tv_sure, R.id.img_flash_record})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (MoreClick.MoreClicks()) {
                    finish();
                }
                break;
            case R.id.lin_typechoose://选择类型
                choose_type = 0;
                if (MoreClick.MoreClicks()) {
                    namePopWindow();
                }
                break;
            case R.id.rela_type_two:
                choose_type = 1;
//                if (MoreClick.MoreClicks()) {
//                    namePopWindow();
//                }
                break;
            case R.id.tv_sure://确定
                if (TextUtils.isEmpty(et_usdg.getText().toString())) {
                    ToastUtil.showShort(FlashBuyActivity.this, "请输入兑换数量!");
                    return;
                }
//                to_Address = "unique1pylc82zvejs5cjmu8m6vzvmeaxw2jrgg2yefam";
                if(Double.valueOf(et_usdg.getText().toString()) < 1 || Double.valueOf(et_usdg.getText().toString()) > 1000){
                    ToastUtil.showShort(FlashBuyActivity.this, "兑换数量不能小于1或者大于10000!");
                    return;
                }
                if(TextUtils.isEmpty(balance)){
                    ToastUtil.showShort(FlashBuyActivity.this, "余额不足!");
                    return;
                }else {
                    if (Double.valueOf(balance) <= 0) {
                        ToastUtil.showShort(FlashBuyActivity.this, "余额不足!");
                        return;
                    }else if(Double.valueOf(et_usdg.getText().toString()) < (Double.valueOf(balance) - Double.valueOf("0.009"))){
                        ToastUtil.showShort(FlashBuyActivity.this, "余额不足!");
                        return;
                    }
                }

                if(TextUtils.isEmpty(to_Address)){
                    ToastUtil.showShort(FlashBuyActivity.this, "兑换地址为空!");
                    return;
                }
                if (safeAdminDialog == null) {
                    safeAdminDialog = new SafeAdminDialog(FlashBuyActivity.this);
                    safeAdminDialog.setOnclick(FlashBuyActivity.this);
                    safeAdminDialog.show();
                } else {
                    safeAdminDialog.show();
                }
                break;
            case R.id.img_flash_record://记录
                startActivity(new Intent(FlashBuyActivity.this, FlashRecordActivity.class));
                break;
        }
    }

    @Override
    public void bindView(Bundle bundle) {


    }

    private void namePopWindow() {

        if (shanDuiNftTypeDialog == null) {
            shanDuiNftTypeDialog = new ShanDuiNftTypeDialog(this, rows);
            shanDuiNftTypeDialog.setOnclick(this);
            shanDuiNftTypeDialog.setItemClickView(this);
            shanDuiNftTypeDialog.show();
        } else {
            shanDuiNftTypeDialog.show();
        }

    }

    //    String denom = "ibc/6599C5CA3A3DF381F90B64FC1E1CFEA90D21005D0C2C50A16EA89D6F52F3C869";
    @Override
    public void setOnSafeClickView(String pass_sha) {
        String pass_sha2 = AESEncrypt.sha(pass_sha);
        String shapsd = unique.getPsd();
        Log.e("FFF444766", "shapsd:" + shapsd);
        Log.e("FFF444766", "pass_sha:" + pass_sha);

        if (pass_sha2.equals(shapsd)) {
            long fee = new BigDecimal("0.009").multiply(new BigDecimal("10").pow(6)).longValue();
            if (TextUtils.isEmpty(et_usdg.getText().toString())) {
                ToastUtil.showShort(FlashBuyActivity.this, "资产不足!");
                safeAdminDialog.dismiss();
                return;
            }
            long num = new BigDecimal(et_usdg.getText().toString()).multiply(new BigDecimal("10").pow(6)).longValue();
            PrivateKey privateKey = UserDaoUtils.getGaussPrevateKey(this, "UNIQUE");
//            to_Address = "unique1pylc82zvejs5cjmu8m6vzvmeaxw2jrgg2yefam";
            String sign = TransactionUtil.INSTANCE.usdtShortcutSigningTransaction(privateKey,
                    to_Address,
                    num,
                    fee,
                    Long.valueOf(sequence1)
                    , 300000,
                    Long.valueOf(account_num),
                    uniqueAdress,
                    minUnit,
                    CoinType.UNIQUE);
            Log.e("FFF444766", "privateKey:" + privateKey);
            Log.e("FFF444766", "sign:" + sign);
            OkGo.post(UrlConstant.baseUrl + "mapping/into")
                    .params("chainInfo", sign)
                    .params("fromType", tv_name_choose.getText().toString())
                    .params("toType", tv_two_name.getText().toString())
                    .execute(new JsonCallback<CollectionBean>() {
                        @Override
                        public void onSuccess(CollectionBean jsonObject, Call call, Response response) {
                            Log.e("FFF444766", "兑换:" + new Gson().toJson(jsonObject));
                            //请求成功
                            if (jsonObject.getCode() == 200) {
//                                    handler.sendEmptyMessage(123);
                                ToastUtils.showToast(FlashBuyActivity.this, "兑换成功");
                                finish();
                            } else {
                                ToastUtils.showToast(FlashBuyActivity.this, "兑换失败!");
                            }
                        }

                        @Override
                        public void onFailure(String code, String message) {
                            super.onFailure(code, message);
                            Log.e("FFF444766", "onFailure:" + code);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });
//            OkGo.post(UrlConstant.baseUrlLian + "unique/broadcastStdTx")
//                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
//                    .params("tx", sign)
//                    .execute(new JsonCallback<BroadcastBean>() {
//                        @Override
//                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
//                            Log.e("FFF444766", "兑换:" + new Gson().toJson(jsonObject));
//                            //请求成功
//                            if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
//                                if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
////                                    handler.sendEmptyMessage(123);
//                                    ToastUtils.showToast(FlashBuyActivity.this, "兑换成功");
//                                } else {
//                                    ToastUtils.showToast(FlashBuyActivity.this, "兑换失败");
//                                    finish();
//                                }
//
//                            } else {
//                                ToastUtils.showToast(FlashBuyActivity.this, "兑换失败!");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(String code, String message) {
//                            super.onFailure(code, message);
//                            Log.e("FFF444766", "onFailure:" + code);
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                        }
//                    });


        } else {
            ToastUtils.showToast(FlashBuyActivity.this, "请输入正确的安全密码");
            return;
        }
        safeAdminDialog.dismiss();
    }

    private String minUnit;

    @Override
    public void setItemClickView(int tag) {
        if (rows.size() > 0) {
            if (choose_type == 0) {
                tv_name_choose.setText(rows.get(tag).getAssetsName());
                tv_zichan_name.setText("可用" + rows.get(tag).getAssetsName() + ":");
                minUnit = rows.get(tag).getMinUnit();
            } else if (choose_type == 1) {
//            tv_two_name.setText(rows.get(tag).getAssetsName());
                tv_zichan_name.setText("可用" + rows.get(tag).getAssetsName() + ":");
                minUnit = rows.get(tag).getMinUnit();
            }
            //查询地址
            getAddress();
        } else {
            shanDuiNftTypeDialog.dismiss();
        }
    }
}
