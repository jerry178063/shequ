package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.DoubleClickUtil;
import com.space.exchange.biz.net.utils.NetworkUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.GoRecommateBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.RecommAdressBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.HttpUtil;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.MinerFeeActivity;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**
 * ????????????
 */
public class AdminCommissionActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView {

    static {
        System.loadLibrary("TrustWalletCore");
    }

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    private String title, address, userAddress;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_address)
    TextView tv_address;
    User unique;
    private UserDao userDao;
    @BindView(R.id.tv_can_user)
    TextView tv_can_user;
    @BindView(R.id.tv_kuangongfei)
    TextView tv_kuangongfei;
    @BindView(R.id.et_min_num)
    EditText et_min_num;
    String recomAdress;
    DaoSession daoSession = MyApplication.getDaoSession();
    //??????
    private String sequence1;
    private String account_num;
    String strFee, isRoot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin_commission;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                if (msg.what == 0 && msg.getData() != null && msg.getData().getString("responseText") != null) {
                    HomeAsstesBean homeAsstesBean = new Gson().fromJson(msg.getData().getString("responseText"), HomeAsstesBean.class);
                    if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("ugpb")) {

                                    try {
                                        tv_can_user.setText("??????GPB:" + String.format("%.6f", Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount()) / 1000000) + "");
                                    } catch (Exception e) {
                                    }
                                }

                            }
                        } else {
                            tv_can_user.setText("??????GPB:" + "0.000000");
                        }
                    } else {
                        tv_can_user.setText("??????GPB:" + "0.000000");
                    }
                }
            }
        }
    };

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("??????");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = getIntent().getStringExtra("title");
        address = getIntent().getStringExtra("address");
        isRoot = getIntent().getStringExtra("isRoot");
        tv_title.setText(title);
        if (!TextUtils.isEmpty(address)) {
            String replace = address.substring(7, 42);
            tv_address.setText(address.replace(replace, "****"));
        }
        tv_address.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    userAddress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        et_min_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                int len = charSequence.toString().length();
                Log.e("FF999", "text:" + text + "LEN:" + len);
//                if (len == 1 && text.equals("0")) {
//                    et_min_num.setHint("??????????????????(GAUSS)");
//                    et_min_num.setText("");
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    judgeNumber(editable, et_min_num);
                } catch (Exception e) {

                }
            }
        });
        getKeyCode();

        getReferences();
        postData();
    }

    //??????????????????????????????
    private void judgeNumber(Editable edt, EditText editText) {
        String temp = edt.toString();
        int posDot = temp.indexOf(".");//???????????????????????????????????????????????????????????????
        int index = editText.getSelectionStart();//??????????????????
        //  if (posDot == 0) {//?????????????????????????????????????????????
        //  edt.delete(0, temp.length());//??????????????????
        //  return;
        //  }
//        if (posDot < 0) {//??????????????????
//            if (temp.length() <= 5) {
//                return;//???????????????????????????
//            } else {
//                edt.delete(index - 1, index);//????????????????????????
//                return;
//            }
//        }
//        if (posDot > 5) {//??????????????????5??????????????????????????????
//            edt.delete(index - 1, index);//????????????????????????
//            return;
//        }
        if (temp.contains(".")) {
            if (temp.length() - posDot - 1 > 6)//?????????????????????
            {
                edt.delete(index - 1, index);//????????????????????????
                return;
            }
        }
    }

    private void getKeyCode() {

        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + userAddress)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("FF88767", "sequence1:" + new Gson().toJson(gaussKeysBean));
                        //????????????
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();
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

    private void postData() {

        showDialog();
        HashMap<String, String> map = new HashMap<>();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
//                map.put("address", unique.getMObjectList().get(0).getCoin_psd());
                    map.put("account", unique.getMObjectList().get(i).getCoin_psd());
                }
            }
        }
        map.put("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d");

        HttpUtil.sendOKHttpGetRequest(UrlConstant.baseUrlLian + "gpb/getBalanceAll", map, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //????????????
                dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                dismissDialog();
                Log.e("FF098", "responseText:" + responseText);
                //????????????
                if (responseText != null) {
                    Message message = Message.obtain();
                    message.what = 0;
                    Bundle bundle = new Bundle();
                    bundle.putString("responseText", responseText);
                    message.setData(bundle);
                    handler.sendMessage(message);

                }
            }
        });

    }

    @Override
    public void initData() {

    }

    //?????????????????????
    private void getReferences() {
        Log.e("FFF444786", "userAddress:" + userAddress);
        Log.e("FF887678", "url:" + UrlConstant.baseCreateInportWallet + "system/rel/references");
        OkGo.get(UrlConstant.baseCreateInportWallet + "system/rel/references")
                .params("gaussaddr", userAddress)
                .readTimeOut(10000)
                .execute(new JsonCallback<RecommAdressBean>() {
                    @Override
                    public void onSuccess(RecommAdressBean recommAdressBean, Call call, Response response) {
                        Log.e("FF887678", "recommAdressBean_???????????????:" + new Gson().toJson(recommAdressBean));
                        dismissDialog();
                        if (recommAdressBean.getCode() == 200 && recommAdressBean != null && recommAdressBean.getData() != null) {
                            if (recommAdressBean.getData().getGaussAddr() != null) {
                                recomAdress = recommAdressBean.getData().getGaussAddr();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        Log.e("FF887678", "recommAdressBean??????onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                    }
                });

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    SafeAdminDialog safeAdminDialog;

    @OnClick({R.id.tv_go_commission, R.id.rela_kuanggongfei, R.id.tv_max})
    public void setOnclick(View view) {
        if (NetworkUtil.isConnected(AdminCommissionActivity.this)) {

            if (DoubleClickUtil.isCommonClick()) {
                switch (view.getId()) {

                    case R.id.tv_go_commission://????????????
                        if (!TextUtils.isEmpty(isRoot) && isRoot.equals("true")) {//???????????????????????????

                        } else {
                            if (userAddress.equals(recomAdress)) {//????????????
                                ToastUtils.showToast(AdminCommissionActivity.this, "??????????????????????????????????????????");
                                return;
                            }
                        }


                        if (TextUtils.isEmpty(et_min_num.getText().toString())) {
                            ToastUtils.showToast(AdminCommissionActivity.this, "???????????????????????????");
                            return;
                        }
                        if (!TextUtils.isEmpty(et_min_num.getText().toString())) {
                            if (Double.valueOf(et_min_num.getText().toString()) < 1) {
                                ToastUtils.showToast(AdminCommissionActivity.this, "????????????????????????????????????1");
                                return;
                            }
                        }
                        if (!TextUtils.isEmpty(et_min_num.getText().toString()) && !TextUtils.isEmpty(tv_can_user.getText().toString())) {
                            if (Double.valueOf(et_min_num.getText().toString()) > ((Double.valueOf(tv_can_user.getText().toString().replace("??????GPB:", "").trim()) - Double.valueOf(tv_kuangongfei.getText().toString().replace("GPB", "").trim())))) {
                                ToastUtil.showShort(AdminCommissionActivity.this, "???????????????");
                                return;
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
                    case R.id.rela_kuanggongfei://?????????
                        Intent intent = new Intent(AdminCommissionActivity.this, MinerFeeActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.tv_max://??????
                        if (!TextUtils.isEmpty(tv_can_user.getText().toString()) && (Double.valueOf(tv_can_user.getText().toString().replace("??????GPB:", "")) > Double.valueOf(tv_kuangongfei.getText().toString().replace("GPB", "").trim()))) {
                            et_min_num.setText(String.format("%.6f", Double.valueOf(tv_can_user.getText().toString().replace("??????GPB:", "")) - Double.valueOf(tv_kuangongfei.getText().toString().replace("GPB", "").trim())) + "");
                        } else {
                            ToastUtil.showShort(this, "??????????????????");
                        }
                        break;

                }

            }
        } else {
            ToastUtil.showShort(AdminCommissionActivity.this, getResources().getString(R.string.the_network_is_not_open));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {

            if (requestCode == 1) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
                tv_kuangongfei.setText(df.format(Double.valueOf(data.getStringExtra("aaa"))) + " GPB");
            }

        }
    }

    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
//        for (int i = 0; i < userDao.loadAll().size(); i++) {
//            if (userDao.loadAll().get(i).getPsd().equals(pass_sha)) {
//                safeAdminDialog.dismiss();
//                delegationGo();
//                break;
//            } else {
//                ToastUtils.showToast(AdminCommissionActivity.this, "??????????????????????????????");
//                return;
//            }
//        }
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            delegationGo();
        } else {
            ToastUtils.showToast(AdminCommissionActivity.this, "??????????????????????????????");
            return;
        }
    }

    private void delegationGo() {
        if (TextUtils.isEmpty(sequence1)) {
            ToastUtil.showShort(AdminCommissionActivity.this, "?????????!");
            return;
        }

        String shapsd = unique.getPsd();
        String coin_psd = unique.getMObjectList().get(0).getCoin_psd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        if (!userAddress.equals(address)) {//????????????
            if (bytes != null) {
                PrivateKey privateKey = new PrivateKey(bytes);

                BigDecimal gauss = new BigDecimal(tv_kuangongfei.getText().toString().replace("GPB", "").trim());
//                BigDecimal gauss_num = gauss.multiply(new BigDecimal(10).pow(6)).divide(new BigDecimal("0.03"));

                GoRecommateBean recommateBean = new GoRecommateBean();
                recommateBean.setDelegator_address(userAddress);
                recommateBean.setValidator_address(address);
                recommateBean.setRecommander_address(recomAdress);
                GoRecommateBean.Amount amount = new GoRecommateBean.Amount();
                amount.setDenom("ugpb");

                BigDecimal bigDecimal1 = new BigDecimal(et_min_num.getText().toString());
                BigDecimal multiply1 = bigDecimal1.multiply(new BigDecimal(10).pow(6));
                String result = multiply1.toString() + "";
                if (!TextUtils.isEmpty(result) && result.contains(".")) {
                    amount.setAmount(multiply1.toString().substring(0, result.indexOf(".")) + "");
                } else {
                    amount.setAmount(multiply1.toString() + "");
                }
                recommateBean.setAmount(amount);


                BigDecimal strFeeBig = new BigDecimal(tv_kuangongfei.getText().toString().replace("GPB", "").trim());
                BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
                String resultFee = multiplyBifg.toString() + "";
                if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
                    strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
                } else {
                    strFee = multiplyBifg.toString() + "";
                }

                String sign2 = TransactionUtil.INSTANCE.gaussWTSigningTransaction(privateKey,
                        Long.valueOf(strFee), 200000,
                        recommateBean.toString(),
                        Long.valueOf(sequence1),
                        Long.valueOf(account_num));
                OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                        .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                        .params("tx", sign2)
                        .readTimeOut(10000)
                        .execute(new JsonCallback<BroadcastBean>() {
                            @Override
                            public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                                dismissDialog();
                                Log.e("FF887678", "jsonObject_??????_1:" + jsonObject);
                                if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                    if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                        ToastUtils.showToast(AdminCommissionActivity.this, "????????????");
                                        MyApplication.commission = 1;

                                    } else {
                                        ToastUtils.showToast(AdminCommissionActivity.this, "????????????");
                                    }
                                    finish();
                                } else {
                                    ToastUtils.showToast(AdminCommissionActivity.this, "????????????!");
                                }
                            }

                            @Override
                            public void onFailure(String code, String message) {
                                super.onFailure(code, message);
                                dismissDialog();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                dismissDialog();
                            }
                        });


            }
        } else {//?????????
            if (bytes != null) {
                PrivateKey privateKey = new PrivateKey(bytes);
                BigDecimal bigDecimal1 = new BigDecimal(et_min_num.getText().toString());
                BigDecimal multiply1 = bigDecimal1.multiply(new BigDecimal(10).pow(6));

                String result = multiply1.toString() + "";


                GoRecommateBean recommateBean = new GoRecommateBean();
                recommateBean.setDelegator_address(userAddress);
                recommateBean.setValidator_address(address);
                recommateBean.setRecommander_address(recomAdress);
                GoRecommateBean.Amount amount = new GoRecommateBean.Amount();
                amount.setDenom("ugpb");

                if (!TextUtils.isEmpty(result) && result.contains(".")) {
                    amount.setAmount(multiply1.toString().substring(0, result.indexOf(".")) + "");
                } else {
                    amount.setAmount(multiply1.toString() + "");
                }
                recommateBean.setAmount(amount);
                BigDecimal strFeeBig = new BigDecimal(tv_kuangongfei.getText().toString().replace("GPB", "").trim());
                BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
                String resultFee = multiplyBifg.toString() + "";
                if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
                    strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
                } else {
                    strFee = multiplyBifg.toString() + "";
                }
                String sign2 = TransactionUtil.INSTANCE.gaussWTValateSigningTransaction(privateKey,
                        Long.valueOf(strFee), 200000,
                        recommateBean.toString(),
                        Long.valueOf(sequence1),
                        Long.valueOf(account_num));
                OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                        .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                        .params("tx", sign2)
                        .readTimeOut(10000)
                        .execute(new JsonCallback<JsonObject>() {
                            @Override
                            public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                                dismissDialog();
                                if (jsonObject.get("code").getAsInt() == 200) {
                                    ToastUtils.showToast(AdminCommissionActivity.this, "??????????????????!");
                                    MyApplication.commission = 1;
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(String code, String message) {
                                super.onFailure(code, message);
                                dismissDialog();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                dismissDialog();
                            }
                        });


            }
        }

    }
}
