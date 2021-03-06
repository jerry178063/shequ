package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.ChangeRateBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsYongDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.BigDecimalNum;
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
 * ????????????-????????????
 * */
public class CommissionRateActivity extends BaseActivity implements AddtionalAssetsYongDialog.OnClickView , SafeAdminDialog.OnSafeClickView,AddtionalAssetsYongDialog.KuangGongOneOnClickView,
        AddtionalAssetsYongDialog.OnCancelClickView{
    static {
        System.loadLibrary("TrustWalletCore");
    }
    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_rate)
    TextView tv_rate;
    User unique;
    private UserDao userDao;
    private String gaussAdress,address,rootAddress,canGaussMonery,validatorAddress,rate_str,name;
    //??????
    private String sequence1;
    private String account_num,strFee;
    @BindView(R.id.et_input_rate)
    EditText et_input_rate;
    private String detail;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_commissionrate;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null) {
                if(msg.what == 0 && msg.getData() != null && msg.getData().getString("responseText") != null) {
                    HomeAsstesBean homeAsstesBean = new Gson().fromJson(msg.getData().getString("responseText"), HomeAsstesBean.class);
                    if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("ugpb")) {

                                    try {
                                        canGaussMonery = BigDecimalNum.setDouble(Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount()) / 1000000 + "");
                                    } catch (Exception e) {
                                    }
                                }

                            }
                        }else {
                        }
                    }
                }
            }
        }
    };

    @Override
    public void initUI() {
        validatorAddress = getIntent().getStringExtra("validatorAddress");
        rate_str = getIntent().getStringExtra("rate_str");
        name = getIntent().getStringExtra("name");
        detail = getIntent().getStringExtra("detail");
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(CommissionRateActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    gaussAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        top_s_title_toolbar.setLeftTitleText("????????????");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSequece();
    }
    private void getSequece() {
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
//                map.put("address", unique.getMObjectList().get(0).getCoin_psd());
                    address = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        Log.e("fff4555","url::" + UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + address);
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + address)
//                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
//                .headers("token", "")
//                .upJson(map.toString() + "")
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("fff4555","??????_:" + new Gson().toJson(gaussKeysBean));
                        //????????????
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("fff4555","??????_:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
    @Override
    public void initData() {
//        postDataLastDealAmount();
        postMoneryData();
    }

    private void postMoneryData() {
        HashMap<String, String> map = new HashMap<>();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
//                map.put("address", unique.getMObjectList().get(0).getCoin_psd());
                    map.put("account", unique.getMObjectList().get(i).getCoin_psd());

                }
            }
        }
        map.put("projectId","ab771e9ce0f94b06925f47e3d3ffa51d");

        HttpUtil.sendOKHttpGetRequest(UrlConstant.baseUrlLian+"gpb/getBalanceAll",map,null,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                //????????????
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                //????????????
                if(responseText != null) {
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
    public void bindView(Bundle bundle) {

    }
    AddtionalAssetsYongDialog addtionalAssetsDialog;
    SafeAdminDialog safeAdminDialog;
    @OnClick({R.id.tv_additional})
    public void setOnclick(View view) {
//        if(FastClick.isFastClick()) {
            switch (view.getId()) {
                case R.id.tv_additional://????????????
                    if(TextUtils.isEmpty(et_input_rate.getText().toString())){
                        ToastUtils.showToast(CommissionRateActivity.this, "?????????????????????");
                        return;
                    }
                    if(!TextUtils.isEmpty(et_input_rate.getText().toString()) && Double.valueOf(et_input_rate.getText().toString()) > Double.valueOf(rate_str) && Double.valueOf(et_input_rate.getText().toString()) <= 50){
                        if((Double.valueOf(et_input_rate.getText().toString())) != (Double.valueOf(rate_str) + 1)) {
                            ToastUtils.showToast(CommissionRateActivity.this, "???????????????????????????????????????????????????!");
                            return;
                        }
                    }
                    if(!TextUtils.isEmpty(et_input_rate.getText().toString()) && Double.valueOf(et_input_rate.getText().toString()) > 50){
                        ToastUtils.showToast(CommissionRateActivity.this, "???????????????????????????????????????????????????!");
                        return;
                    }
                    if(!TextUtils.isEmpty(et_input_rate.getText().toString()) && Double.valueOf(et_input_rate.getText().toString()) <= 0){
                        ToastUtils.showToast(CommissionRateActivity.this, "???????????????????????????????????????????????????!");
                        return;
                    }

                    if (addtionalAssetsDialog == null) {
                        addtionalAssetsDialog = new AddtionalAssetsYongDialog(this);
                        addtionalAssetsDialog.setOnclick(this);
                        addtionalAssetsDialog.setKuangGongOneOnClickView(this);
                        addtionalAssetsDialog.setOnCancelClickView(this);
                        addtionalAssetsDialog.show();
                    } else {
                        addtionalAssetsDialog.show();
                    }
                    addtionalAssetsDialog.tv_can_gauss.setText("??????GPB: " + canGaussMonery);
                    break;
            }
//        }
    }

    @Override
    public void setOnClickView() {
        if(canGaussMonery != null){
            if((Double.valueOf(canGaussMonery) - Double.valueOf(addtionalAssetsDialog.tv_kgf2.getText().toString().replace("?????????:","").replace("GPB","").trim())) < 0){
                ToastUtils.showToast(CommissionRateActivity.this, "??????????????????!");
                return;
            }
        }

        addtionalAssetsDialog.dismiss();

        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }

    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
//        for (int i = 0; i < userDao.loadAll().size(); i++) {
//            if (userDao.loadAll().get(i).getPsd().equals(pass_sha)) {
//                getChangeRate();
//                break;
//            }else {
//                ToastUtils.showToast(CommissionRateActivity.this,"??????????????????????????????");
//                return;
//            }
//        }
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            getChangeRate();
        } else {
            ToastUtils.showToast(CommissionRateActivity.this, "??????????????????????????????");
            return;
        }
    }

    private void getChangeRate() {

        String shapsd = unique.getPsd();
        String coin_psd = unique.getMObjectList().get(0).getCoin_psd();//???????????????
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        BigDecimal gauss = new BigDecimal(et_input_rate.getText().toString());
        BigDecimal gauss_num = gauss.divide(new BigDecimal(10).pow(2));
        ChangeRateBean getValitionBean = new ChangeRateBean();
        ChangeRateBean value = new ChangeRateBean();
        value.setValidator_address(validatorAddress);
        value.setCommission_rate(gauss_num.setScale(18).toPlainString());

        ChangeRateBean.Description description = new ChangeRateBean.Description();
        description.setMoniker(name);
        description.setDetails(detail);
        value.setDescription(description);
        BigDecimal strFeeBig = new BigDecimal(addtionalAssetsDialog.tv_kgf2.getText().toString().replace("GPB","").replace("?????????:","").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
        if (bytes != null) {
            PrivateKey privateKey = new PrivateKey(bytes);
            String sign = TransactionUtil.INSTANCE.gaussChangeRateSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    value.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num) );
            Log.e("FF88767","validatorAddress:" + validatorAddress);
            Log.e("FF88767","sign:" + sign);
            OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                    .params("tx", sign)
                    .readTimeOut(10000)
                    .execute(new JsonCallback<BroadcastBean>() {
                        @Override
                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                            Log.e("FF88767","jsonObject:" + jsonObject);
                            safeAdminDialog.dismiss();
                            if(jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                if(jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                    ToastUtils.showToast(CommissionRateActivity.this, "????????????");
                                }else {
                                    ToastUtils.showToast(CommissionRateActivity.this,"????????????????????????!");
                                }
                            }else {
                                ToastUtils.showToast(CommissionRateActivity.this,"????????????????????????!");
                            }
                            finish();
                        }

                        @Override
                        public void onFailure(String code, String message) {
                            super.onFailure(code, message);
                            safeAdminDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            safeAdminDialog.dismiss();
                        }
                    });

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {

            if (requestCode == 1) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
                addtionalAssetsDialog.tv_kgf2.setText("?????????:" + df.format(Double.valueOf(data.getStringExtra("aaa"))) + " GPB");
            }
        }
    }
    @Override
    public void setKuangGongOneOnClickView() {
        Intent intent = new Intent(CommissionRateActivity.this, MinerFeeActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void setCancelOnClickView() {
        addtionalAssetsDialog.tv_kgf2.setText("?????????:" + 0.006 + " GPB");
    }
}
