package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.DelegationsBean;
import com.unique.blockchain.nft.domain.node.GetRootInvitationBean;
import com.unique.blockchain.nft.domain.node.GetValAddressBean;
import com.unique.blockchain.nft.domain.node.GetValiAddressBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.JaildBean;
import com.unique.blockchain.nft.domain.node.UpbandDataBean;
import com.unique.blockchain.nft.domain.node.ValidatorInfoListBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.BigDecimalNum;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.StringUtils;
import com.unique.blockchain.nft.net.HttpUtil;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 验证人-已验证
 */
public class HasVerificationActivity extends BaseActivity implements AddtionalAssetsDialog.OnClickView, SafeAdminDialog.OnSafeClickView {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_wallet_name)
    TextView tv_wallet_name;
    @BindView(R.id.tv_zhiya_num)
    TextView tv_zhiya_num;
    @BindView(R.id.tv_yongjin_rate)
    TextView tv_yongjin_rate;
    @BindView(R.id.tv_yzr_self_money)
    TextView tv_yzr_self_money;
    @BindView(R.id.tc_invate_rate)
    TextView tc_invate_rate;
    @BindView(R.id.tv_year_rate)
    TextView tv_year_rate;
    @BindView(R.id.tv_delegate_num)
    TextView tv_delegate_num;
    @BindView(R.id.tv_all_num)
    TextView tv_all_num;
    @BindView(R.id.tv_shuhui)
    TextView tv_shuhui;
    @BindView(R.id.tv_min_value)
    TextView tv_min_value;
    @BindView(R.id.tv_wallet_address)
    TextView tv_wallet_address;
    String gaussAdass, rootAddress, isYzr, canGaussMonery, gaussAdress, validatorAddress, all_num_monery;
    @BindView(R.id.tv_go_chuyu)
    TextView tv_go_chuyu;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    User unique;
    String str_yongjin_rate;
    String str_yzr_self_money;
    private UserDao userDao;
//gauss1yqwrcyqytnf3tsfqlr2rsczxc4964frr99krry           一个验证人的地址  开发使用

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_has_verification;
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
                                        if(homeAsstesBean.getResult().getBalances().get(i).getAmount() != null) {
                                            canGaussMonery = String.format("%.6f", Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount()) / 1000000) + "";
                                        }
                                    } catch (Exception e) {
                                    }
                                }

                            }
                        } else {
                        }
                    }
                }
            }
        }
    };

    @Override
    public void initUI() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        validatorAddress = getIntent().getStringExtra("gaussAdass");
        top_s_title_toolbar.setLeftTitleText("验证人");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        all_num_monery = getIntent().getStringExtra("all_num_monery");
        if(!TextUtils.isEmpty(all_num_monery)) {
            tv_all_num.setText(String.format("%.6f", Double.valueOf(all_num_monery) / 1000000) + "");
        }else {

        }

    }

    @Override
    public void initData() {
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(HasVerificationActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    gaussAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        OkGo.get(UrlConstant.baseUrlLian + "gpb/getValAddress")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("accAddress", gaussAdress)
                .execute(new JsonCallback<GetValAddressBean>() {
                    @Override
                    public void onSuccess(GetValAddressBean versionInfo, Call call, Response response) {
                        dismissDialog();
                        //请求成功
                        if (versionInfo != null && versionInfo.getResult() != null && versionInfo.getResult().getValidatorsAddress() != null) {

                            if (versionInfo.getCode() == 200) {
                                gaussAdass = versionInfo.getResult().getValidatorsAddress();
                                postData(gaussAdass);
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


        getRootInvitationCode();

        postValiteData(validatorAddress);
//        postDataLastDealAmount();
        postMoneryData();
    }

    //获取验证人地址
    private void postValiteData(String address) {
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address", address)
                .readTimeOut(10000)
                .execute(new JsonCallback<GetValiAddressBean>() {
                    @Override
                    public void onSuccess(GetValiAddressBean getValiAddressBean, Call call, Response response) {
                        dismissDialog();
                        //请求成功
                        if (getValiAddressBean != null && getValiAddressBean.getValidators() != null && getValiAddressBean.getValidators().size() > 0) {
                            if(getValiAddressBean.getValidators().get(0).getOperator_address() != null) {
                                isYzr = getValiAddressBean.getValidators().get(0).getOperator_address();
                            }
                            getValidatorsBean(isYzr);
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
        map.put("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d");

        HttpUtil.sendOKHttpGetRequest(UrlConstant.baseUrlLian + "gpb/getBalanceAll", map, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //请求成功
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

    //获取是否是入狱状态
    private void getValidatorsBean(String gaussAdass) {
        OkGo.get(UrlConstant.baseUrlGauss + "gpb/staking/v1beta1/validators/" + gaussAdass)
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<JaildBean>() {
                    @Override
                    public void onSuccess(JaildBean jsonObject, Call call, Response response) {
                        if (jsonObject != null && jsonObject.getValidator() != null) {
                            if (jsonObject.getValidator().isJailed()) {//监狱状态
                                tv_go_chuyu.setVisibility(View.VISIBLE);
                            } else {//非监狱状态
                                tv_go_chuyu.setVisibility(View.GONE);
                            }
                        }

                        //请求成功
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

    //获取根节点验证人地址
    private void getRootInvitationCode() {
        showDialog();
        OkGo.get(UrlConstant.baseCreateInportWallet + "system/setting/getRootInvitationCode")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<GetRootInvitationBean>() {
                    @Override
                    public void onSuccess(GetRootInvitationBean getRootInvitationBean, Call call, Response response) {

                        //请求成功
                        if (getRootInvitationBean != null && getRootInvitationBean.getData() != null && getRootInvitationBean.getData().getValidatoraddr() != null) {

                            rootAddress = getRootInvitationBean.getData().getValidatoraddr();
                            postAllPPeople(validatorAddress);
                            postUpbond(validatorAddress);
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

    //获取验证人地址
    private void postData(String address) {
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address", address)
                .readTimeOut(10000)
                .execute(new JsonCallback<GetValiAddressBean>() {
                    @Override
                    public void onSuccess(GetValiAddressBean getValiAddressBean, Call call, Response response) {
                        dismissDialog();
                        Log.e("FFF34454","getValiAddressBean:" + new Gson().toJson(getValiAddressBean));
                        //请求成功
                        if (getValiAddressBean != null && getValiAddressBean.getValidators() != null && getValiAddressBean.getValidators().size() > 0) {

                            for (int i = 0; i < getValiAddressBean.getValidators().size(); i++) {
                                if (getValiAddressBean.getValidators().get(i).getOperator_address() != null && address.equals(getValiAddressBean.getValidators().get(i).getOperator_address())) {
                                    postTwoData(address);
                                    tv_wallet_address.setText(getValiAddressBean.getValidators().get(i).getOperator_address());

                                    //只质押量
                                    if(getValiAddressBean.getValidators().get(i).getTokens() != null) {
                                        String str_zhiya_num = getValiAddressBean.getValidators().get(i).getTokens() + "";
                                        BigDecimal bigDecimal = new BigDecimal(str_zhiya_num.trim());
                                        BigDecimal multiply = bigDecimal.divide(new BigDecimal(10).pow(6));
                                        tv_zhiya_num.setText(multiply + "" + " GPB");
                                    }
                                    if(getValiAddressBean.getValidators().get(i).getDescription() != null && getValiAddressBean.getValidators().get(i).getDescription().getMoniker() != null) {
                                        //钱包名称
                                        tv_wallet_name.setText(getValiAddressBean.getValidators().get(i).getDescription().getMoniker());
                                    }
                                    if(getValiAddressBean.getValidators().get(i).getCommission().getCommission_rates() != null && getValiAddressBean.getValidators().get(i).getCommission().getCommission_rates().getRate() != null) {
                                        //佣金比例
                                         str_yongjin_rate = StringUtils.formatDouble2(Double.valueOf(getValiAddressBean.getValidators().get(i).getCommission().getCommission_rates().getRate()) * 100) + "";
                                        tv_yongjin_rate.setText(str_yongjin_rate.substring(0, str_yongjin_rate.indexOf(".")) + "%");
                                    }
                                    //验证人自留佣金收益
                                    if(getValiAddressBean.getValidators().get(i).getCommission_reallocation()!= null && getValiAddressBean.getValidators().get(i).getCommission_reallocation().getReserve_rate() != null) {
                                        str_yzr_self_money = StringUtils.formatDouble2(Double.valueOf(getValiAddressBean.getValidators().get(i).getCommission_reallocation().getReserve_rate())* 100) + "";
                                        tv_yzr_self_money.setText(str_yzr_self_money + "%");


                                    }
                                    if (getValiAddressBean.getValidators().get(i).getCommission_reallocation() != null && getValiAddressBean.getValidators().get(i).getCommission_reallocation().getReallocated_rate() != null) {
                                        //邀请收益比例
                                        String str_invate_rate1 = Double.valueOf(getValiAddressBean.getValidators().get(i).getCommission_reallocation().getReallocated_rate()) + "";
                                        BigDecimal bigDecimall1 = new BigDecimal(str_invate_rate1.trim());
                                        BigDecimal multiplyl1 = bigDecimall1.multiply(new BigDecimal(10).pow(2));


                                        ;//请设置收益比例

                                        NumberFormat nf = NumberFormat.getInstance();
                                        tc_invate_rate.setText(nf.format(StringUtils.formatDouble2((Double.valueOf(str_yongjin_rate)/100- Double.valueOf(str_yongjin_rate)/100*Double.valueOf(getValiAddressBean.getValidators().get(i).getCommission_reallocation().getReserve_rate() + ""))*Double.valueOf(str_invate_rate1)*100)) + " %" + ":"
                                                + nf.format(StringUtils.formatDouble2((Double.valueOf(str_yongjin_rate)/100- Double.valueOf(str_yongjin_rate)/100*Double.valueOf(getValiAddressBean.getValidators().get(i).getCommission_reallocation().getReserve_rate() + ""))*(1-Double.valueOf(str_invate_rate1))*100)) + "%");

                                    }
                                    //tv_min_value
                                    if (getValiAddressBean.getValidators().get(i).getMin_self_delegation() != null) {
                                        tv_min_value.setText(getValiAddressBean.getValidators().get(i).getMin_self_delegation() + "");
                                    }
                                    if (getValiAddressBean.getValidators().get(i).getDescription() != null && getValiAddressBean.getValidators().get(i).getDescription().getDetails() != null) {
                                        tv_detail.setText(getValiAddressBean.getValidators().get(i).getDescription().getDetails());
                                    }
                                    break;
                                }
                            }
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

    private void postTwoData(String address) {
        OkGo.get(UrlConstant.baseUrlLian + "gpb/validatorsinfoList")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .execute(new JsonCallback<ValidatorInfoListBean>() {
                    @Override
                    public void onSuccess(ValidatorInfoListBean validatorInfoListBean, Call call, Response response) {
                        dismissDialog();
                        Log.e("FF88767", "validatorInfoListBean:" + new Gson().toJson(validatorInfoListBean));
                        //请求成功
                        if (validatorInfoListBean != null && validatorInfoListBean.getResult() != null && validatorInfoListBean.getResult().getValidators() != null && validatorInfoListBean.getResult().getValidators().size() > 0) {

                            for (int i = 0; i < validatorInfoListBean.getResult().getValidators().size(); i++) {
                                if (address.equals(validatorInfoListBean.getResult().getValidators().get(i).getOperatorAddress())) {

                                    //年化率
                                    BigDecimal bd = new BigDecimal(BigDecimalNum.setDouble(Double.valueOf(validatorInfoListBean.getResult().getValidators().get(i).getYearRate()) + ""));
                                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

                                    tv_year_rate.setText("≈ " + bd.toString() + "%");
                                    break;
                                }
                            }
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


    //解绑中、赎回中
    private void postUpbond(String address) {

        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators/" + address + "/unbonding_delegations")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<UpbandDataBean>() {
                    @Override
                    public void onSuccess(UpbandDataBean jsonObject, Call call, Response response) {
                        dismissDialog();
                        if (jsonObject != null && jsonObject.getPagination() != null && jsonObject.getPagination().getTotal() != null) {
                            tv_shuhui.setText(String.format("%.6f", Double.valueOf(jsonObject.getPagination().getTotal()) / 1000000) + "");
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

    //查询总人数
    private void postAllPPeople(String address) {
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators/" + address + "/delegations")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<DelegationsBean>() {
                    @Override
                    public void onSuccess(DelegationsBean jsonObject, Call call, Response response) {
                        dismissDialog();
                        if (jsonObject != null && jsonObject.getPagination() != null && !TextUtils.isEmpty(jsonObject.getPagination().getTotal())) {
                            tv_delegate_num.setText(jsonObject.getPagination().getTotal());
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

    @Override
    public void bindView(Bundle bundle) {

    }

    AddtionalAssetsDialog addtionalAssetsDialog;
    SafeAdminDialog safeAdminDialog;

    @OnClick({R.id.commission_rate, R.id.verification_commission_income, R.id.tv_go_chuyu})
    public void setOnclick(View view) {
//        if(FastClick.isFastClick()) {
        switch (view.getId()) {
            case R.id.commission_rate://佣金比例

                Intent intent = new Intent(HasVerificationActivity.this, CommissionRateActivity.class);
                intent.putExtra("validatorAddress", validatorAddress);//到时候替换成自己的地址 ，因为自己就是验证人了
                intent.putExtra("rate_str", tv_yongjin_rate.getText().toString().replace("%", ""));
                intent.putExtra("name", tv_wallet_name.getText().toString());
                intent.putExtra("detail", tv_detail.getText().toString());
                startActivity(intent);

                break;
            case R.id.verification_commission_income://验证人自留佣金收益
                Intent intent2 = new Intent(HasVerificationActivity.this, CommissionVerificationIncomeActivity.class);
                intent2.putExtra("validatorAddress", validatorAddress);//到时候替换成自己的地址 ，因为自己就是验证人了
                intent2.putExtra("name", tv_wallet_name.getText().toString());
                intent2.putExtra("detail", tv_detail.getText().toString());
                intent2.putExtra("rate_str", tv_yongjin_rate.getText().toString().replace("%", ""));
                startActivity(intent2);
                break;
            case R.id.tv_go_chuyu://出狱
                if (addtionalAssetsDialog == null) {
                    addtionalAssetsDialog = new AddtionalAssetsDialog(HasVerificationActivity.this);
                    addtionalAssetsDialog.setOnclick(HasVerificationActivity.this);
                    addtionalAssetsDialog.show();
                } else {
                    addtionalAssetsDialog.show();
                }
                addtionalAssetsDialog.tv_can_gauss.setText("可用GPB: " + canGaussMonery);
                addtionalAssetsDialog.tv_title_dialog.setText("出狱");
                addtionalAssetsDialog.tv_title2.setText("矿工费 1.8 GPB");
                addtionalAssetsDialog.tv_kgf2.setVisibility(View.GONE);
                break;
        }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(HasVerificationActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    gaussAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        getRootInvitationCode();

        postValiteData(validatorAddress);
//        postDataLastDealAmount();
        postMoneryData();
    }

    @Override
    public void setOnClickView() {
        addtionalAssetsDialog.dismiss();
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(HasVerificationActivity.this);
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
//                //加签交易
//            } else {
//                ToastUtils.showToast(HasVerificationActivity.this, "请输入正确的安全密码");
//                return;
//            }
//        }
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            //加签交易
        } else {
            ToastUtils.showToast(HasVerificationActivity.this, "请输入正确的安全密码");
            return;
        }
    }

    //把String转化为int
    public static int convertToInt(String number, int defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
