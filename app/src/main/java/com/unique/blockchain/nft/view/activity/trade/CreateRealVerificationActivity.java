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
 * 成为验证人--填写资料
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
    //次数
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
        top_s_title_toolbar.setLeftTitleText("成为验证人");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gauss_monery = getIntent().getStringExtra("gauss_monery");
        if(!TextUtils.isEmpty(gauss_monery)) {
            tv_gaussmonery.setText("可用GPB数量: " + gauss_monery);
        }else {
            tv_gaussmonery.setText("可用GPB数量: ");
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
                        ToastUtils.showToast(CreateRealVerificationActivity.this, "输入的比例不能大于100");
                        et_set_shouyi_2.setText("设置收益比例");
                        et_set_shouyi_1.setText("");
                        et_set_shouyi_1.setHint("请设置收益比例");
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
                        ToastUtils.showToast(CreateRealVerificationActivity.this, "输入的比例不能大于100");
                        et_input_me_bai.setText("");
                        et_input_me_bai.setHint("请输入自留佣金收益比例");
                    }
                }else {
                    et_input_me_bai.setHint("请输入自留佣金收益");
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
         * 小数位数
         */
        int DECIMAL_COUNT = 6;
        getKeyCode();
//        getReferences();
        getValicationAddress();
    }
    //限制输入多少位小数点
    private void judgeNumber(Editable edt,EditText editText) {
        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
        //  if (posDot == 0) {//必须先输入数字后才能输入小数点
        //  edt.delete(0, temp.length());//删除所有字符
        //  return;
        //  }
//        if (posDot < 0) {//不包含小数点
//            if (temp.length() <= 5) {
//                return;//小于五位数直接返回
//            } else {
//                edt.delete(index-1, index);//删除光标前的字符
//                return;
//            }
//        }
//        if (posDot > 5) {//小数点前大于5位数就删除光标前一位
//            edt.delete(index-1, index);//删除光标前的字符
//            return;
//        }
        if(temp.contains(".")) {
            if (temp.length() - posDot - 1 > 6)//如果包含小数点
            {
                edt.delete(index - 1, index);//删除光标前的字符
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
        Log.e("FFF344","切换的钱包地址:" + adddRess);
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + adddRess + "/valaddress")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .readTimeOut(10000)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        dismissDialog();
                        if(jsonObject != null) {
                            Log.e("ppp4555", "jsonObject_验证人地址33333:" + jsonObject);
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
                        //请求成功
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
            case R.id.tv_create_verfication://成为验证人

                Log.e("ppp4555","质押数量:" + et_passwordc.getText().toString());

                if(TextUtils.isEmpty(et_name.getText().toString())){
                    ToastUtils.showToast(this, "请输入验证人公钥");
                    return;
                }
                if(TextUtils.isEmpty(et_password.getText().toString())){
                    ToastUtils.showToast(this,"请输入节点名称");
                    return;
                }
                if(TextUtils.isEmpty(et_passwordc.getText().toString())){
                    ToastUtils.showToast(this,"请输入质押数量");
                    return;
                }
                if(TextUtils.isEmpty(et_yongjin_bl.getText().toString())){
                    ToastUtils.showToast(this,"请输入佣金比例");
                    return;
                }
                if(TextUtils.isEmpty(et_input_me_bai.getText().toString())){
                    ToastUtils.showToast(this,"请输入自留佣金百分比");
                    return;
                }
                if(TextUtils.isEmpty(et_set_shouyi_1.getText().toString())){
                    ToastUtils.showToast(this,"请设置收益比例");
                    return;
                }
                if(TextUtils.isEmpty(et_set_shouyi_2.getText().toString())){
                    ToastUtils.showToast(this,"请设置收益比例");
                    return;
                }
                if(TextUtils.isEmpty(et_set_min_weituo.getText().toString())){
                    ToastUtils.showToast(this,"请输入最小委托值");
                    return;
                }
                if(TextUtils.isEmpty(et_input_detail.getText().toString())){
                    ToastUtils.showToast(this,"请输入简介描述");
                    return;
                }
                if(Double.valueOf(gauss_monery) < (Double.valueOf(et_passwordc.getText().toString().trim()) + Double.valueOf(tv_kuangongfei.getText().toString().replace("GPB","").trim()))){
                    ToastUtils.showToast(this,"余额不足");
                    return;
                }
                if(Double.valueOf(et_passwordc.getText().toString()) <= 0){
                    ToastUtils.showToast(this,"质押数量必须大于0");
                    return;
                }
                if(Double.valueOf(et_set_min_weituo.getText().toString()) < 1){
                    ToastUtils.showToast(this,"最小委托值必须大于等于1");
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
//                addtionalAssetsDialog.tv_title_dialog.setText("成为验证人");
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
                et_passwordc.setText(tv_gaussmonery.getText().toString().replace("可用GPB数量: ","") + "");
                break;
            case R.id.rela_kuanggongfei://矿工费
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
//                ToastUtils.showToast(CreateRealVerificationActivity.this,"请输入正确的安全密码");
//                return;
//            }
//        }
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            delegationGo();
        } else {
            ToastUtils.showToast(CreateRealVerificationActivity.this, "请输入正确的安全密码");
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
            Log.e("ppp4555","质押数量:" + et_passwordc.getText().toString());

            BigDecimal ugauss  = new BigDecimal(et_passwordc.getText().toString().replace("可用GPB数量: ",""));
            BigDecimal ugauss_num = ugauss.multiply(new BigDecimal(10).pow(6));
            String  result = ugauss_num.toString() + "";


            CreateValidatorBean.Value value = new CreateValidatorBean.Value();
            if(!TextUtils.isEmpty(result) && result.contains(".")) {//质押数量
                value.setAmount(ugauss_num.toString().substring(0, result.indexOf(".")) + "");
            }else {
                value.setAmount(ugauss_num.toString() + "");
            }
            value.setDenom("ugpb");
            createValidatorBean.setValue(value);
            CreateValidatorBean.Pubkey pubkey = new CreateValidatorBean.Pubkey();

            //生成公钥puhkey
            Map<String, String> pubFromAddress = Bech32.getPubFromAddress(et_name.getText().toString().trim());

            if( pubFromAddress!=null){
                pubkey.setValue(pubFromAddress.get("value"));
                pubkey.setType(pubFromAddress.get("type"));
            }else {
                ToastUtil.showShort(this,"输入的公钥有误,请重新输入");
                return;
            }

            createValidatorBean.setPubkey(pubkey);//验证人公钥

            CreateValidatorBean.Description description = new CreateValidatorBean.Description();
            description.setDetails(et_input_detail.getText().toString());//简介描述
            description.setMoniker(et_password.getText().toString());//节点名称
            createValidatorBean.setDescription(description);
            CreateValidatorBean.Commission commission = new CreateValidatorBean.Commission();
            commission.setRate(new BigDecimal(et_yongjin_bl.getText().toString()).divide(new BigDecimal("100")).setScale(18).toPlainString() +"");//佣金比例
            commission.setMax_change_rate(new BigDecimal("0.01").setScale(18).toPlainString());
            commission.setMax_rate(new BigDecimal("0.5").setScale(18).toPlainString());
            createValidatorBean.setCommission(commission);
            CreateValidatorBean.Commission_reallocation commission_reallocation = new CreateValidatorBean.Commission_reallocation();

            commission_reallocation.setReallocated_rate(new BigDecimal(Double.valueOf(et_set_shouyi_1.getText().toString().trim())/100 + "").setScale(18).toPlainString());//请设置收益比例
            commission_reallocation.setReserve_rate((new BigDecimal(Double.valueOf(et_input_me_bai.getText().toString())/100 + "").setScale(18).toPlainString()));//自留佣金百分比

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
            Log.e("FFF344","成为验证人_sign2:" + sign2);

            OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                    .params("tx", sign2)
                    .readTimeOut(10000)
                    .execute(new JsonCallback<BroadcastBean>() {
                        @Override
                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                            dismissDialog();
                            Log.e("FFF344","成为验证人:" + new Gson().toJson(jsonObject));
                            if(jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                if(jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                    ToastUtils.showToast(CreateRealVerificationActivity.this, "操作成功");
                                }else {
                                    ToastUtils.showToast(CreateRealVerificationActivity.this,"成为验证人失败");
                                }
                            }else {
                                ToastUtils.showToast(CreateRealVerificationActivity.this,"成为验证人失败!");
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
    //矿工费-确定
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
    //矿工费去选择自定义
    @Override
    public void setKuangGongOneOnClickView() {
        Intent intent = new Intent(CreateRealVerificationActivity.this, MinerFeeActivity.class);
        startActivityForResult(intent, 1);
    }

    //取消矿工费弹窗
    @Override
    public void setCancelOnClickView() {
        addtionalAssetsDialog.tv_kgf2.setText("矿工费:" + 0.006 + " GPB");
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
