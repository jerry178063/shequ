package com.unique.blockchain.nft.view.activity.mark.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.unique.blockchain.nft.domain.trade.NftOutPriceBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPricePresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.IMarkOutPriceTwoPostPresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IMarkOutPricePresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.IMarkOutPriceTwoPostPresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceCallBack;
import com.unique.blockchain.nft.view.activity.mark.view.IMarkOutPriceTwoPostCallBack;

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
 * 市场---出价
 * */

public class OutPriceActivity extends BaseActivity implements IMarkOutPriceCallBack ,SafeAdminDialog.OnSafeClickView, IMarkOutPriceTwoPostCallBack {
    static {
        System.loadLibrary("TrustWalletCore");
    }
    @BindView(R.id.tv_charge)
    TextView tv_charge;
    @BindView(R.id.img_back)
    LinearLayout img_back;
    IMarkOutPricePresenter iMarkOutPricePresenter;
    private String nftId,uniqueAdress;
    @BindView(R.id.tv_banshui)
    TextView tv_banshui;//版税
    @BindView(R.id.tv_kuanggongfei)
    TextView tv_kuanggongfei;//旷工费
    @BindView(R.id.tv_your_balance)
    TextView tv_your_balance;//您的余额
    @BindView(R.id.tv_your_outprice)
    TextView tv_your_outprice;//您的出价余额
    @BindView(R.id.et_put_price)
    EditText et_put_price;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String title,balance;
    IMarkOutPriceTwoPostPresenter iMarkOutPriceTwoPostPresenter;
    //次数
    private String sequence1;
    private String account_num;
    private String productType,strFee,price,sign;
    User unique;
    private UserDao userDao;
    DaoSession daoSession = MyApplication.getDaoSession();
    SafeAdminDialog safeAdminDialog;
    @BindView(R.id.tv_zhishao_price)
    TextView tv_zhishao_price;
    String walletAddress,orderId;
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
        nftId = getIntent().getStringExtra("nftId");
        Log.e("FFD33","nftId:" + nftId);
        uniqueAdress = getIntent().getStringExtra("uniqueAdress");
        orderId = getIntent().getStringExtra("orderId");
        iMarkOutPricePresenter = new IMarkOutPricePresenterImpl();
        iMarkOutPricePresenter.registerViewCallback(this);
        iMarkOutPricePresenter.getData(nftId,uniqueAdress);
        title = getIntent().getStringExtra("title");
        tv_title.setText("您将要购买" + "\"" + title+ "\"");

        et_put_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_your_outprice.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                judgeNumber(editable,et_put_price);
            }
        });
        userDao = daoSession.getUserDao();

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
        getMonery();
    }
    public static void judgeNumber(Editable edt,EditText editText) {

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
        if (posDot > 0){
            if (temp.length() - posDot > 6)//如果包含小数点
            {
                edt.delete(posDot + 7, temp.length());//删除光标前的字符
                return;
            }
        }
    }
    private void getMonery() {
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", walletAddress)
                .headers("projectId",Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("uunique")) {
                                        Log.e("FF33325","余额:" + homeAsstesBean.getResult().getBalances().get(i).getAmount());
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getAmount();
//                                        balance = "100000000";
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
                ToastUtil.showShort(OutPriceActivity.this,"敬请期待!");

                break;
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_nft_go://数字资产出价
                if(TextUtils.isEmpty(et_put_price.getText())){
                    ToastUtil.showShort(OutPriceActivity.this,"请输入出价价格");
                    return;
                }
                if(!TextUtils.isEmpty(tv_your_outprice.getText().toString())) {
                    if (Double.parseDouble(tv_your_outprice.getText().toString()) > Double.parseDouble(String.format("%.6f", Double.parseDouble(tv_your_balance.getText().toString())))) {
                        ToastUtils.showToast(OutPriceActivity.this, "出价大于可用余额");
                        return;
                    }
                }else {
                    ToastUtil.showShort(OutPriceActivity.this,"当前价格不能为空");
                    return;
                }
                if (Double.parseDouble(tv_your_outprice.getText().toString()) > ((Double.parseDouble(String.format("%.6f", Double.parseDouble(tv_your_balance.getText().toString()))) - Double.parseDouble(tv_kuanggongfei.getText().toString().replace("UNIQUE","").trim())))) {
                    ToastUtil.showShort(OutPriceActivity.this, "矿工费不足");
                    return;
                }
                if(!TextUtils.isEmpty(min_price)){
                    if (Double.parseDouble(et_put_price.getText().toString()) < Double.parseDouble(min_price)) {
                        ToastUtil.showShort(OutPriceActivity.this, "您的出价必须至少为" + min_price + " UNIQUE");
                        return;
                    }
                }
                getAccount();
                break;
        }
    }

    private void getAccount() {
        Log.e("FF33325", "walletAddress:" + walletAddress);
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + walletAddress)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        Log.e("FF33325", "sequence1:" + new Gson().toJson(gaussKeysBean));
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

                        Log.e("FF88767", "onFailure:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF88767", "e:" + e);
                    }
                });
    }

    private void goSign() {
        NftOutPriceBean nftOutPriceBean = new NftOutPriceBean();
        nftOutPriceBean.setSender(walletAddress);
        nftOutPriceBean.setToken_id(nftId);
        NftOutPriceBean.Price price = new NftOutPriceBean.Price();
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

        BigDecimal bigDecimal = new BigDecimal(et_put_price.getText().toString());
        BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal(10).pow(6));
        BigInteger outPrice = bigDecimal1.toBigInteger();
        price.setAmount(outPrice.toString());
        nftOutPriceBean.setPrice(price);
        nftOutPriceBean.setPool_address(Constants.UNIQUE_ADDRESS);//先测试环境使用
        sign = TransactionUtilNew.INSTANCE.uniqueOutNftSigningTransaction(privateKey,
                Long.valueOf(strFee), 200000,
                nftOutPriceBean.toString(),
                Long.valueOf(sequence1),
                Long.valueOf(account_num));
        Log.e("FF33325","SIGN_out:" + sign);
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }
    private String min_price;
    @Override
    public void loadOutPriceData(MarkOutPriceBean markOutPriceBean) {
        if(markOutPriceBean != null && markOutPriceBean.getCode() == 200) {
            Log.e("FFD33", "markOutPriceBean:" + new Gson().toJson(markOutPriceBean));
            min_price = markOutPriceBean.getData().getCurrentPrice() + "";
            tv_banshui.setText(markOutPriceBean.getData().getRoyalty() + "");
//            tv_kuanggongfei.setText(markOutPriceBean.getData().getMinersFee() + "");
            tv_kuanggongfei.setText("0.009");
            tv_zhishao_price.setText("*必须至少为" + markOutPriceBean.getData().getCurrentPrice() + markOutPriceBean.getData().getCurrency().toUpperCase());
        }
    }

    @Override
    public void loadOutPriceFail() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_outprice;
    }

    @Override
    public void setOnSafeClickView(String et_pass) {
        String pass_sha = AESEncrypt.sha(et_pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            iMarkOutPriceTwoPostPresenter = new IMarkOutPriceTwoPostPresenterImpl();
            iMarkOutPriceTwoPostPresenter.registerViewCallback(this);
            iMarkOutPriceTwoPostPresenter.getData(nftId,orderId,walletAddress,et_put_price.getText().toString(),sign);
            Log.e("FF33325y","nftId:" + nftId);
            Log.e("FF33325y","uniqueAdress:" + walletAddress);
            Log.e("FF33325y","price:" + et_put_price.getText().toString());
            Log.e("FF33325y","sign:" + sign);
        } else {
            ToastUtils.showToast(OutPriceActivity.this, "请输入正确的安全密码");
            return;
        }
    }

    @Override
    public void loadOutPriceTwoPostData(CollectionBean markOutPriceBean) {
        Log.e("FF33325y","markOutPriceBean:" + new Gson().toJson(markOutPriceBean));
        if(markOutPriceBean.getCode() == 200) {
            finish();
            Intent intent = new Intent(OutPriceActivity.this, BuyPriceSuccessActivity.class);
            intent.putExtra("detail","outprice");
            intent.putExtra("name","out");
            startActivity(intent);
        }else {
            ToastUtil.showShort(this,markOutPriceBean.getMsg());
        }
    }

    @Override
    public void loadOutPriceTwoPostFail() {

    }
}
