package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.CreateValidatorBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Bech32;
import com.unique.blockchain.nft.infrastructure.other.ExtensionsKt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.DateLocalUtcUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.MinerFeeActivity;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.math.BigDecimal;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**
 *
 * ???????????????--????????????
 * */
public class CreateRealVerificationActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView,AddtionalAssetsDialog.OnClickView,AddtionalAssetsDialog.KuangGongOneOnClickView,AddtionalAssetsDialog.OnCancelClickView{
    static {
        System.loadLibrary("TrustWalletCore");
    }
    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_passwordc)
    EditText et_passwordc;
    @BindView(R.id.et_yongjin_bl)
    EditText et_yongjin_bl;
    @BindView(R.id.et_input_me_bai)
    EditText et_input_me_bai;
    @BindView(R.id.et_set_shouyi_1)
    EditText et_set_shouyi_1;
    @BindView(R.id.et_set_shouyi_2)
    EditText et_set_shouyi_2;
    @BindView(R.id.et_set_min_weituo)
    EditText et_set_min_weituo;
    @BindView(R.id.et_input_detail)
    EditText et_input_detail;
    String gauss_monery;
    @BindView(R.id.tv_gaussmonery)
    TextView tv_gaussmonery;
    @BindView(R.id.tv_detail_num)
    TextView tv_detail_num;
    @BindView(R.id.tv_ziliu)
    TextView tv_ziliu;
    @BindView(R.id.tv_kuangongfei)
    TextView tv_kuangongfei;
    //??????
    private String sequence1,address;
    private String account_num;
    DaoSession daoSession = MyApplication.getDaoSession();
    User unique;
    private UserDao userDao;
    String recomAdress,valationAddress,strFee;
    AddtionalAssetsDialog addtionalAssetsDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_verification;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("???????????????");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gauss_monery = getIntent().getStringExtra("gauss_monery");
        if(!TextUtils.isEmpty(gauss_monery)) {
            tv_gaussmonery.setText("??????GPB??????: " + gauss_monery);
        }else {
            tv_gaussmonery.setText("??????GPB??????: ");
        }
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        et_set_shouyi_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence.toString())) {
                    if (Double.valueOf(charSequence.toString()) > 100) {
                        ToastUtils.showToast(CreateRealVerificationActivity.this, "???????????????????????????100");
                        et_set_shouyi_2.setText("??????????????????");
                        et_set_shouyi_1.setText("");
                        et_set_shouyi_1.setHint("?????????????????????");
                    } else {
                        et_set_shouyi_2.setText((100 - Integer.valueOf(charSequence.toString())) + "");

                    }
                }else {
                    et_set_shouyi_2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_input_me_bai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence.toString())) {
                    if (Double.valueOf(charSequence.toString()) > 100) {
                        ToastUtils.showToast(CreateRealVerificationActivity.this, "???????????????????????????100");
                        et_input_me_bai.setText("");
                        et_input_me_bai.setHint("?????????????????????????????????");
                    }
                }else {
                    et_input_me_bai.setHint("???????????????????????????");
                    tv_ziliu.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_passwordc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    try {
                        judgeNumber(editable, et_passwordc);
                    } catch (Exception e) {

                    }
            }
        });
        /**
         * ????????????
         */
        int DECIMAL_COUNT = 6;
        getKeyCode();
//        getReferences();
        getValicationAddress();
    }
    //??????????????????????????????
    private void judgeNumber(Editable edt,EditText editText) {
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
//                edt.delete(index-1, index);//????????????????????????
//                return;
//            }
//        }
//        if (posDot > 5) {//??????????????????5??????????????????????????????
//            edt.delete(index-1, index);//????????????????????????
//            return;
//        }
        if(temp.contains(".")) {
            if (temp.length() - posDot - 1 > 6)//?????????????????????
            {
                edt.delete(index - 1, index);//????????????????????????
                return;
            }
        }
    }
    String adddRess;
    private void getValicationAddress() {
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    adddRess = unique.getMObjectList().get(i).getCoin_psd();
                    break;
                }
            }
        }
        Log.e("FFF344","?????????????????????:" + adddRess);
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + adddRess + "/valaddress")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .readTimeOut(10000)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        dismissDialog();
                        if(jsonObject != null) {
                            Log.e("ppp4555", "jsonObject_???????????????33333:" + jsonObject);
                            valationAddress = jsonObject.get("address").getAsString();
                        }

                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        Log.e("ppp4555","onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                    }
                });
    }

    private void getKeyCode() {
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    address = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + address)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
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

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {
        et_input_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_detail_num.setText(charSequence.length() + "/" + "120");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_input_detail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
        et_input_detail.setSingleLine(false);
    }
    SafeAdminDialog safeAdminDialog;
    @OnClick({R.id.tv_create_verfication,R.id.tv_all_num,R.id.rela_kuanggongfei})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_create_verfication://???????????????

                Log.e("ppp4555","????????????:" + et_passwordc.getText().toString());

                if(TextUtils.isEmpty(et_name.getText().toString())){
                    ToastUtils.showToast(this, "????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_password.getText().toString())){
                    ToastUtils.showToast(this,"?????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_passwordc.getText().toString())){
                    ToastUtils.showToast(this,"?????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_yongjin_bl.getText().toString())){
                    ToastUtils.showToast(this,"?????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_input_me_bai.getText().toString())){
                    ToastUtils.showToast(this,"??????????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_set_shouyi_1.getText().toString())){
                    ToastUtils.showToast(this,"?????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_set_shouyi_2.getText().toString())){
                    ToastUtils.showToast(this,"?????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_set_min_weituo.getText().toString())){
                    ToastUtils.showToast(this,"????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(et_input_detail.getText().toString())){
                    ToastUtils.showToast(this,"?????????????????????");
                    return;
                }
                if(Double.valueOf(gauss_monery) < (Double.valueOf(et_passwordc.getText().toString().trim()) + Double.valueOf(tv_kuangongfei.getText().toString().replace("GPB","").trim()))){
                    ToastUtils.showToast(this,"????????????");
                    return;
                }
                if(Double.valueOf(et_passwordc.getText().toString()) <= 0){
                    ToastUtils.showToast(this,"????????????????????????0");
                    return;
                }
                if(Double.valueOf(et_set_min_weituo.getText().toString()) < 1){
                    ToastUtils.showToast(this,"?????????????????????????????????1");
                    return;
                }
//                if (addtionalAssetsDialog == null) {
//                    addtionalAssetsDialog = new AddtionalAssetsDialog(CreateRealVerificationActivity.this);
//                    addtionalAssetsDialog.setOnclick(this);
//                    addtionalAssetsDialog.setKuangGongOneOnClickView(this);
//                    addtionalAssetsDialog.setOnCancelClickView(this);
//                    addtionalAssetsDialog.show();
//                } else {
//                    addtionalAssetsDialog.show();
//                }
//                addtionalAssetsDialog.tv_can_gauss.setText("");
//                addtionalAssetsDialog.tv_title_dialog.setText("???????????????");
//                addtionalAssetsDialog.tv_title2.setText("");
//                addtionalAssetsDialog.tv_can_gauss.setVisibility(View.GONE);
//                addtionalAssetsDialog.tv_title2.setBackgroundColor(getColor(R.color.white));

                if (safeAdminDialog == null) {
                    safeAdminDialog = new SafeAdminDialog(this);
                    safeAdminDialog.setOnclick(this);
                    safeAdminDialog.show();
                } else {
                    safeAdminDialog.show();
                }
                break;
            case R.id.tv_all_num:
                et_passwordc.setText(tv_gaussmonery.getText().toString().replace("??????GPB??????: ","") + "");
                break;
            case R.id.rela_kuanggongfei://?????????
                Intent intent = new Intent(CreateRealVerificationActivity.this, MinerFeeActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
//        for (int i = 0; i < userDao.loadAll().size(); i++) {
//            if (userDao.loadAll().get(i).getPsd().equals(pass_sha)) {
//                safeAdminDialog.dismiss();
//                delegationGo();
//                break;
//            }else {
//                ToastUtils.showToast(CreateRealVerificationActivity.this,"??????????????????????????????");
//                return;
//            }
//        }
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            delegationGo();
        } else {
            ToastUtils.showToast(CreateRealVerificationActivity.this, "??????????????????????????????");
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void delegationGo() {
        String shapsd = unique.getPsd();
        String coin_psd = unique.getMObjectList().get(0).getCoin_psd();
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        Log.e("FFF344","getPsd_psd:" + unique.getMObjectList().get(0).getPsd_psd());
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        if (bytes != null) {

            PrivateKey privateKey = new PrivateKey(bytes);
            String s = ExtensionsKt.toDelHex(privateKey.getPublicKeySecp256k1(true).data());
//            String gaussvaloper = Bech32.generateValidatorAddressFromPub(s, "gaussvaloper");
            String gaussvaloper = Bech32.generateValidatorAddressFromPub(s, "gpbvaloper");

            Log.e("FFF344","gaussvaloper:" + gaussvaloper);

            CreateValidatorBean createValidatorBean = new CreateValidatorBean();
            Log.e("ppp4555","????????????:" + et_passwordc.getText().toString());

            BigDecimal ugauss  = new BigDecimal(et_passwordc.getText().toString().replace("??????GPB??????: ",""));
            BigDecimal ugauss_num = ugauss.multiply(new BigDecimal(10).pow(6));
            String  result = ugauss_num.toString() + "";


            CreateValidatorBean.Value value = new CreateValidatorBean.Value();
            if(!TextUtils.isEmpty(result) && result.contains(".")) {//????????????
                value.setAmount(ugauss_num.toString().substring(0, result.indexOf(".")) + "");
            }else {
                value.setAmount(ugauss_num.toString() + "");
            }
            value.setDenom("ugpb");
            createValidatorBean.setValue(value);
            CreateValidatorBean.Pubkey pubkey = new CreateValidatorBean.Pubkey();

            //????????????puhkey
            Map<String, String> pubFromAddress = Bech32.getPubFromAddress(et_name.getText().toString().trim());

            if( pubFromAddress!=null){
                pubkey.setValue(pubFromAddress.get("value"));
                pubkey.setType(pubFromAddress.get("type"));
            }else {
                ToastUtil.showShort(this,"?????????????????????,???????????????");
                return;
            }

            createValidatorBean.setPubkey(pubkey);//???????????????

            CreateValidatorBean.Description description = new CreateValidatorBean.Description();
            description.setDetails(et_input_detail.getText().toString());//????????????
            description.setMoniker(et_password.getText().toString());//????????????
            createValidatorBean.setDescription(description);
            CreateValidatorBean.Commission commission = new CreateValidatorBean.Commission();
            commission.setRate(new BigDecimal(et_yongjin_bl.getText().toString()).divide(new BigDecimal("100")).setScale(18).toPlainString() +"");//????????????
            commission.setMax_change_rate(new BigDecimal("0.01").setScale(18).toPlainString());
            commission.setMax_rate(new BigDecimal("0.5").setScale(18).toPlainString());
            createValidatorBean.setCommission(commission);
            CreateValidatorBean.Commission_reallocation commission_reallocation = new CreateValidatorBean.Commission_reallocation();

            commission_reallocation.setReallocated_rate(new BigDecimal(Double.valueOf(et_set_shouyi_1.getText().toString().trim())/100 + "").setScale(18).toPlainString());//?????????????????????
            commission_reallocation.setReserve_rate((new BigDecimal(Double.valueOf(et_input_me_bai.getText().toString())/100 + "").setScale(18).toPlainString()));//?????????????????????

            commission_reallocation.setUpdate_time(DateLocalUtcUtil.Local2UTC());
            createValidatorBean.setCommission_reallocation(commission_reallocation);
            createValidatorBean.setMin_self_delegation(et_set_min_weituo.getText().toString());
            createValidatorBean.setDelegator_address(coin_psd);
            createValidatorBean.setValidator_address(gaussvaloper);

            BigDecimal strFeeBig = new BigDecimal(Double.valueOf(tv_kuangongfei.getText().toString().replace("GPB","").trim()));
            BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
            String resultFee = multiplyBifg.toString() + "";
            if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
                strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
            } else {
                strFee = multiplyBifg.toString() + "";
            }

            String sign2 = TransactionUtil.INSTANCE.gaussCreateVilesitionSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    createValidatorBean.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
            Log.e("FFF344","???????????????_sign2:" + sign2);

            OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                    .params("tx", sign2)
                    .readTimeOut(10000)
                    .execute(new JsonCallback<BroadcastBean>() {
                        @Override
                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                            dismissDialog();
                            Log.e("FFF344","???????????????:" + new Gson().toJson(jsonObject));
                            if(jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                if(jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                    ToastUtils.showToast(CreateRealVerificationActivity.this, "????????????");
                                }else {
                                    ToastUtils.showToast(CreateRealVerificationActivity.this,"?????????????????????");
                                }
                            }else {
                                ToastUtils.showToast(CreateRealVerificationActivity.this,"?????????????????????!");
                            }
                            finish();
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
    //?????????-??????
    @Override
    public void setOnClickView() {
        if (addtionalAssetsDialog != null) {
            addtionalAssetsDialog.dismiss();
        }
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }
    //???????????????????????????
    @Override
    public void setKuangGongOneOnClickView() {
        Intent intent = new Intent(CreateRealVerificationActivity.this, MinerFeeActivity.class);
        startActivityForResult(intent, 1);
    }

    //?????????????????????
    @Override
    public void setCancelOnClickView() {
        addtionalAssetsDialog.tv_kgf2.setText("?????????:" + 0.006 + " GPB");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {

            if (requestCode == 1) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
                tv_kuangongfei.setText(df.format(Double.valueOf(data.getStringExtra("aaa"))) + " GPB");
            }
        }
    }
}
