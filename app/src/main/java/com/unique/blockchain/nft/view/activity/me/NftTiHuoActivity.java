package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.unique.blockchain.nft.adapter.markAdapter.BannerDetailAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.TiHuoBannerDetailAdapter;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.trade.GoTradeDetailBean;
import com.unique.blockchain.nft.domain.trade.NftTiHuoBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.ShuzhiRegisterSuccessDialog;
import com.unique.blockchain.nft.infrastructure.dialog.TiHuoSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.database.BannerDatabase;
import com.unique.blockchain.nft.view.activity.me.meAdapter.PersonalAdapter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressListPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeAddressListPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressListCallBack;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeDetailPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeDetailPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeListDetailCallbask;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**提货
 * */
public class NftTiHuoActivity extends BaseActivity implements SafeAdminDialog.OnSafeClickView , IMeAddressListCallBack ,TiHuoSuccessDialog.OnBackZhujiClickView
, ITradeListDetailCallbask {

    static {
        System.loadLibrary("TrustWalletCore");
    }

    private String amount;
    User unique;
    private UserDao userDao;
    private String psd;
    //次数
    private String sequence1;
    private String account_num;
    SafeAdminDialog safeAdminDialog;
    String strFee;
    @BindView(R.id.img_back)
    LinearLayout img_back;


    @BindView(R.id.liu_RecyclerView)
    RecyclerView liu_RecyclerView;
    private TiHuoBannerDetailAdapter recAdapter;
    private List<BannerDatabase.DataBean> list = new ArrayList<>();
    private List<BannerDatabase.DataBean> data = new ArrayList<>();//广告
    List<String> bannImg = new ArrayList<>();//顶部滚动图片

    String imgs,name,uniqueAdress,orderId;
    @BindView(R.id.tv_name_title)
    TextView tv_name_title;
    @BindView(R.id.personal_rec)
    RecyclerView personal_rec;
    private PersonalAdapter personalAdapter;
    private List<UserAdressListBean.Data> lo = new ArrayList<>();
    IMeAddressListPresenter iMeAddressListPresenter;
    private String userAddressId;
    private String nftId,balance,sign,productType;
    TiHuoSuccessDialog tiHuoSuccessDialog;
    BannerDatabase.DataBean dataBean;
    private ITradeDetailPresenter iTradeDetailPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_nft_tihuo;
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
        DaoSession daoSession = MyApplication.getDaoSession();
        orderId = getIntent().getStringExtra("orderId");
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("ffff444", "e: " + e);
        }
        if (unique != null) {
            psd = unique.getPsd();
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();//钱包地址
                }
            }
        }
        productType = getIntent().getStringExtra("productType");
        nftId = getIntent().getStringExtra("nftId");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        liu_RecyclerView.setLayoutManager(linearLayoutManager);

        iTradeDetailPresenter = new ITradeDetailPresenterImpl();
        iTradeDetailPresenter.registerViewCallback(this);
        iTradeDetailPresenter.getData(1,orderId, nftId, uniqueAdress);

//        imgs = getIntent().getStringExtra("img");
//
//        if (!TextUtils.isEmpty(imgs)) {
//            if (!imgs.contains(",")) {
//                dataBean = new BannerDatabase.DataBean();
//                dataBean.setImageAddress(imgs);
//                data.add(dataBean);
//            } else {
//                if (bannImg != null) {
//                    bannImg.clear();
//                }
//                bannImg = Arrays.asList(imgs.split(","));
//                if (bannImg != null) {
//                    for (int i = 0; i < bannImg.size(); i++) {
//                        dataBean = new BannerDatabase.DataBean();
//                        dataBean.setImageAddress(bannImg.get(i));
//                        data.add(dataBean);
//                    }
//                }
//            }
            recAdapter = new TiHuoBannerDetailAdapter(data, this);
            liu_RecyclerView.setAdapter(recAdapter);
//        }

        //地址
        LinearLayoutManager linearLayoutManagerAdd = new LinearLayoutManager(this);
        personal_rec.setLayoutManager(linearLayoutManagerAdd);
        personalAdapter = new PersonalAdapter(this, lo,uniqueAdress);
        personal_rec.setAdapter(personalAdapter);
    }

    @Override
    public void initData() {
        name = getIntent().getStringExtra("name");
        tv_name_title.setText(name);
        getMonery();
        //获取地址列表
        if (iMeAddressListPresenter == null) {
            iMeAddressListPresenter = new IMeAddressListPresenterImpl();
            iMeAddressListPresenter.registerViewCallback(this);
        }
        iMeAddressListPresenter.getData(uniqueAdress);

    }

    private void getMonery() {
        Log.e("FFDSS433","uniqueAdress:" + uniqueAdress);
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", uniqueAdress)
                .headers("projectId", Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        Log.e("FF88767","余额_homeAsstesBean:" + new Gson().toJson(homeAsstesBean));
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("uunique")) {
                                        Log.e("FFDSS433","余额:" + homeAsstesBean.getResult().getBalances().get(i).getAmount());
                                        balance = String.format("%.6f", Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount()) / 1000000);
                                        break;
                                    }

                                }
                            }
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

    String address;


    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.img_back,R.id.me_address,R.id.tv_tihuo})
    public void setOnclick(View view) {
                switch (view.getId()) {
                    case R.id.img_back:
                        finish();
                        break;
                    case R.id.me_address://添加新地址
                        Intent intent = new Intent(NftTiHuoActivity.this, LocationActivity.class);
                        intent.putExtra("uniqueAdress", uniqueAdress);
                        startActivity(intent);
                        break;
                    case R.id.tv_tihuo://提货
                        if(TextUtils.isEmpty( userAddressId)){

                            ToastUtil.showShort(NftTiHuoActivity.this,"请添加地址并选中!");
                            return;
                        }
                        if(!TextUtils.isEmpty(balance)){
                            Log.e("FFDSS433","(Double.valueOf(balance) - 0.009):" + (Double.valueOf(balance) - 0.009));
                            if((Double.valueOf(balance) - 0.009) <= 0){
                                ToastUtil.showShort(NftTiHuoActivity.this,"扣除旷工费余额不足!");
                                return;
                            }
                        }else {
                            ToastUtil.showShort(NftTiHuoActivity.this,"余额不足!");
                            return;
                        }
                        getAccount();
                        break;
                }
//        }
    }
    private void getAccount() {
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + uniqueAdress)
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
        NftTiHuoBean nftTiHuoBean = new NftTiHuoBean();
        nftTiHuoBean.setSender(uniqueAdress);
        nftTiHuoBean.setToken_id(nftId);
        nftTiHuoBean.setCate_id(productType);
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
        sign = TransactionUtilNew.INSTANCE.uniqueTiHuoSigningTransaction(privateKey,
                Long.valueOf(strFee), 200000,
                nftTiHuoBean.toString(),
                Long.valueOf(sequence1),
                Long.valueOf(account_num));
        Log.e("FF23WW","SIGN:" + sign);
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (iMeAddressListPresenter != null) {
            iMeAddressListPresenter.getData(uniqueAdress);
        }


    }
    @Override
    public void setOnSafeClickView(String pass) {
        String pass_sha = AESEncrypt.sha(pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            getTiHuo();
        } else {
            ToastUtils.showToast(NftTiHuoActivity.this, "请输入正确的安全密码");
            return;
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tiHuoSuccessDialog.dismiss();
            finish();
        }
    };
    private void getTiHuo() {
        Log.e("FF23WW","nftId:" + nftId);
        Log.e("FF23WW","walletAddr:" + uniqueAdress);
        Log.e("FF23WW","addressId:" + userAddressId);

        OkGo.get(UrlConstant.baseUrl + "api/user/take")
                .params("addressId",userAddressId + "")
                .params("nftId",nftId)
                .params("walletAddr",uniqueAdress)
                .params("chainInfo",sign)
                .params("orderId",orderId)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, Call call, Response response) {
                        safeAdminDialog.dismiss();
                        if(jsonObject.getCode() == 200){
                            Log.e("FF23WW","json:" + new Gson().toJson(jsonObject));
                            if (tiHuoSuccessDialog == null) {
                                tiHuoSuccessDialog = new TiHuoSuccessDialog(NftTiHuoActivity.this);
                                tiHuoSuccessDialog.setOnclick(NftTiHuoActivity.this);
                                tiHuoSuccessDialog.show();
                            }else {
                                tiHuoSuccessDialog.show();
                            }
                            handler.sendEmptyMessage(100);

                        }else {
                            ToastUtil.showShort(NftTiHuoActivity.this,jsonObject.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        ToastUtil.showShort(NftTiHuoActivity.this,message);
                        safeAdminDialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtil.showShort(NftTiHuoActivity.this,"提货失败!");
                        safeAdminDialog.dismiss();
                    }
                });

    }

    //地址列表
    @Override
    public void loadAddressListPostData(UserAdressListBean userAdressListBean) {
        if (userAdressListBean != null && userAdressListBean.getCode() == 200) {

            if (userAdressListBean.getData() != null && userAdressListBean.getData().size() > 0) {
                if (lo != null) {
                    lo.clear();
                }
                lo.addAll(userAdressListBean.getData());
                personalAdapter.notifyDataSetChanged();
                for (int i = 0; i < userAdressListBean.getData().size(); i++) {
                    if (userAdressListBean.getData().get(i).getIsDefault() == 1) {
                        userAddressId = userAdressListBean.getData().get(i).getId() + "";
                    }
                }
            }

        }
    }

    @Override
    public void loadAddressListPostFail() {

    }

    @Override
    public void setBackZhujiClickView(String et_pass) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(recAdapter != null){
            recAdapter.close();
        }
    }
    GoTradeDetailBean mGoTradeDetailBean;
    @Override
    public void loadData(GoTradeDetailBean goTradeDetailBean) {
        if (goTradeDetailBean != null && goTradeDetailBean.getCode() == 200) {
            if (goTradeDetailBean.getData() != null) {
                mGoTradeDetailBean = goTradeDetailBean;
                if (!TextUtils.isEmpty(goTradeDetailBean.getData().getProductImage())) {
                    if (!goTradeDetailBean.getData().getProductImage().contains(",")) {
//                        bannImg.add(goTradeDetailBean.getData().getProductImage());
                        dataBean = new BannerDatabase.DataBean();
                        dataBean.setImageAddress(goTradeDetailBean.getData().getProductImage());
                        data.add(dataBean);
                    } else {
                        if (bannImg != null) {
                            bannImg.clear();
                        }
                        bannImg = Arrays.asList(goTradeDetailBean.getData().getProductImage().split(","));
                        if (bannImg != null) {
                            for (int i = 0; i < bannImg.size(); i++) {
                                dataBean = new BannerDatabase.DataBean();
                                dataBean.setImageAddress(bannImg.get(i));
                                data.add(dataBean);
                            }
                        }
                    }
                }
                recAdapter.setBannerl(data);//大图片
            }
        }
    }

    @Override
    public void loadFail() {

    }
}

