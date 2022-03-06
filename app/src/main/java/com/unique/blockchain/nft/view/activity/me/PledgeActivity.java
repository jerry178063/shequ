package com.unique.blockchain.nft.view.activity.me;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
import com.unique.blockchain.nft.domain.me.CheckCompanyBean;
import com.unique.blockchain.nft.domain.me.UpdateCautionBean;
import com.unique.blockchain.nft.domain.me.ZhiYaBeanBaoBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.ShuzhiRegisterSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeCheckCompanyPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeUpdateCautionPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeChekCompanyPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeUpdateCautionPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeCheckCompanyCallBack;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeUpdateCautionCallBack;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**
 * 质押保证金
 *
 * @author panhao
 * @date 2021-09-23
 */
public class PledgeActivity extends BaseActivity implements IMeCheckCompanyCallBack, IMeUpdateCautionCallBack, SafeAdminDialog.OnSafeClickView, ShuzhiRegisterSuccessDialog.OnBackZhujiClickView {
    static {
        System.loadLibrary("TrustWalletCore");
    }

    /**
     * 显示保证金
     */
    private TextView deposit_tv;

    //用户bean对象
    private User unique;

    // 用户Dao对象
    private UserDao userDao;

    //用户钱包地扯
    private String uniqueAdress;
    IMeCheckCompanyPresenter iMeCheckCompanyPresenter;
    @BindView(R.id.deposit)
    TextView deposit;
    //次数
    private String sequence1;
    private String account_num;
    private String sign, balance;
    IMeUpdateCautionPresenter iMeUpdateCautionPresenter;
    String strFee;
    SafeAdminDialog safeAdminDialog;
    @BindView(R.id.tv_zhiya)
    TextView tv_zhiya;
    @BindView(R.id.tv_title)
    TextView tv_title;
    ShuzhiRegisterSuccessDialog shuzhiRegisterSuccessDialog;
    @BindView(R.id.lin_go_zhiya)
    RelativeLayout lin_go_zhiya;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pledge;
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
        initView();
    }

    @Override
    public void initData() {
        getAccount();
        getMonery();
    }

    private void getMonery() {
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", uniqueAdress)
                .headers("projectId", Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("uunique")) {
                                        Log.e("FF88767", "余额:" + homeAsstesBean.getResult().getBalances().get(i).getAmount());
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getAmount();
                                        break;
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

    private void getAccount() {
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + uniqueAdress)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("FF88767", "sequence1:" + new Gson().toJson(gaussKeysBean));
                        //请求成功
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();
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
                        Log.e("FF88767", "e:" + e);
                    }
                });
    }

    /**
     * 页面初始化
     */
    private void initView() {

        // 保证金组件对象.
        deposit_tv = findViewById(R.id.deposit);

        // 从本地数据库中获取用户钱包地扯
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(PledgeActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF333", "gaussAdress: " + uniqueAdress);
                }
            }
        }
        // 调用后台接口根据用户当前钱包地扯获取保证金.

        if (iMeCheckCompanyPresenter == null) {
            iMeCheckCompanyPresenter = new IMeChekCompanyPresenterImpl();
            iMeCheckCompanyPresenter.registerViewCallback(this);
        }
        iMeCheckCompanyPresenter.getData(uniqueAdress);


    }

    @OnClick({R.id.img_back, R.id.tv_zhiya})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_zhiya://质押
                if (!TextUtils.isEmpty(balance)) {
                    if ((Double.valueOf(balance) - 0.009) < Double.valueOf(deposit.getText().toString())) {
                        ToastUtil.showShort(this, "余额不足!");
                        return;
                    }
                } else {
                    ToastUtil.showShort(this, "余额不足!");
                    return;
                }

                if (iMeUpdateCautionPresenter == null) {
                    iMeUpdateCautionPresenter = new IMeUpdateCautionPresenterImpl();
                    iMeUpdateCautionPresenter.registerViewCallback(this);
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

    private void goZhiYa() {
        ZhiYaBeanBaoBean zhiYaBeanBaoBean = new ZhiYaBeanBaoBean();
        zhiYaBeanBaoBean.setDelegator(uniqueAdress);
        zhiYaBeanBaoBean.setPool_address(Constants.UNIQUE_ADDRESS);//先测试环境使用
        ZhiYaBeanBaoBean.Amount amount = new ZhiYaBeanBaoBean.Amount();
        amount.setDenom("uunique");
        BigDecimal bigDecimal = new BigDecimal(deposit.getText().toString());
        BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal(10).pow(6));
        String strDeposit = bigDecimal1.toString();
        if (strDeposit.contains(".")) {
            amount.setAmount(strDeposit.substring(0, strDeposit.indexOf(".")));
        } else {
            amount.setAmount(strDeposit);
        }
        zhiYaBeanBaoBean.setAmount(amount);

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
        sign = TransactionUtilNew.INSTANCE.uniqueZhiyaSigningTransaction(privateKey,
                Long.valueOf(strFee), 200000,
                zhiYaBeanBaoBean.toString(),
                Long.valueOf(sequence1),
                Long.valueOf(account_num));
        Log.e("FFF3333", "sign:" + sign);

        UpdateCautionBean updateCautionBean = new UpdateCautionBean();
        if (tv_zhiya.getText().toString().equals("去质押")) {
            updateCautionBean.setType(0);
        } else {
            updateCautionBean.setType(1);
        }

        updateCautionBean.setWalletAddr(uniqueAdress);
        updateCautionBean.setCautionMoney(bigDecimal.toString());
        updateCautionBean.setChainInfo(sign);
        Log.e("FFF2211", "updateCautionBean:" + new Gson().toJson(updateCautionBean));
        iMeUpdateCautionPresenter.getData(updateCautionBean);
    }


    @Override
    public void bindView(Bundle bundle) {

    }

    //获取质押余额
    @Override
    public void loadCheckCompanyPostData(CheckCompanyBean collectionBean) {
        Log.e("FF23444", "collectionBean:" + new Gson().toJson(collectionBean));
        if (collectionBean != null && collectionBean.getCode() == 200) {
            if (collectionBean.getData() != null) {

                if (collectionBean.getData().getPayCautionMoney() != null) {

                    if (collectionBean.getData().getStatus() == 0) {
                        tv_zhiya.setBackground(getResources().getDrawable(R.drawable.zhiya_no_status));
                        tv_zhiya.setEnabled(false);
                    } else if (collectionBean.getData().getStatus() == 1) {
                        tv_zhiya.setBackground(getResources().getDrawable(R.drawable.cread_shape));
                        tv_zhiya.setEnabled(true);
                    }

                    if (collectionBean.getData().getName() == 0) {
                        tv_title.setText("当前待缴保证金(UNIQUE)");
                        tv_zhiya.setText("去质押");
                        Log.e("FF23444", "collectionBean22:" + new Gson().toJson(collectionBean));
                        if(!TextUtils.isEmpty(collectionBean.getData().getSurplusCautionMoney()) && !collectionBean.getData().getSurplusCautionMoney().equals("0") && !collectionBean.getData().getSurplusCautionMoney().equals("0.0")) {
                            deposit_tv.setText(String.format("%.6f", Double.valueOf(collectionBean.getData().getSurplusCautionMoney())) + "");
                        }else {
                            deposit_tv.setText("0.000000");
                        }
                    } else if (collectionBean.getData().getName() == 1) {
                        tv_title.setText("当前保证金(UNIQUE)");
                        tv_zhiya.setText("去赎回");
                        if(!TextUtils.isEmpty(collectionBean.getData().getPayCautionMoney()) && !collectionBean.getData().getPayCautionMoney().equals("0") && !collectionBean.getData().getPayCautionMoney().equals("0.0")) {
                            deposit_tv.setText(String.format("%.6f", Double.valueOf(collectionBean.getData().getPayCautionMoney())) + "");
                        }else {
                            deposit_tv.setText("0.000000");
                        }
                    }


                } else {
                    deposit_tv.setText("0.000000");
                }

            } else {
                deposit_tv.setText("0.000000");
            }
        }
    }

    @Override
    public void loadCheckCompanyPostFail() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(3000);
                shuzhiRegisterSuccessDialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    };

    //质押
    @Override
    public void loadUpdateCautionPostData(CollectionBean collectionBean) {
        if (collectionBean != null && collectionBean.getCode() == 200) {
            if (shuzhiRegisterSuccessDialog == null) {
                shuzhiRegisterSuccessDialog = new ShuzhiRegisterSuccessDialog(this);
                shuzhiRegisterSuccessDialog.setOnclick(this);
                shuzhiRegisterSuccessDialog.show();
                if (tv_zhiya.getText().toString().equals("去质押")) {
                    shuzhiRegisterSuccessDialog.tv_cancel.setText("质押成功");
                } else {
                    shuzhiRegisterSuccessDialog.tv_cancel.setText("赎回成功");
                }
                shuzhiRegisterSuccessDialog.tv_cancel.setVisibility(View.GONE);
            }
            handler.sendEmptyMessage(100);

//            iMeCheckCompanyPresenter.getData(uniqueAdress);

        } else {
            ToastUtil.showShort(PledgeActivity.this, collectionBean.getMsg());
        }
    }

    @Override
    public void loadUpdateCautionPostFail() {

    }

    @Override
    public void setOnSafeClickView(String et_pass) {
        String pass_sha = AESEncrypt.sha(et_pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            goZhiYa();
        } else {
            ToastUtils.showToast(PledgeActivity.this, "请输入正确的安全密码");
            return;
        }
    }

    @Override
    public void setBackZhujiClickView(String et_pass) {

    }
}