package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.home.HomeAssetsInfoBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.WalletAsstesBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.fragment.AllMyTradeFragment;
import com.unique.blockchain.nft.view.activity.me.fragment.NftZichenFragment;
import com.unique.blockchain.nft.view.fragment.mark.All_Home_Fragment;
import com.unique.blockchain.nft.view.fragment.mark.Art_Home_Fragment;
import com.unique.blockchain.nft.view.fragment.mark.Collect_Home_Fragment;
import com.unique.blockchain.nft.view.fragment.mark.Grade_Home_Fragment;
import com.unique.blockchain.nft.view.fragment.mark.Ticket_Home_Fragment;
import com.unique.blockchain.nft.view.fragment.trade.HeYue_Fragment;
import com.unique.blockchain.nft.view.fragment.trade.KuaLian_Fragment;
import com.unique.blockchain.nft.view.fragment.trade.LianShang_Fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class DigitalHomeAssetsActivity extends BaseActivity {

    @BindView(R.id.tv_balance)
    TextView tv_balance;
    String balance,balance2;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress,uniqueAdressEth;
    @BindView(R.id.rela_unique_monery)
    RelativeLayout rela_unique_monery;
    @BindView(R.id.tv_unique_monery)
    TextView tv_unique_monery;
    Fragment[] fragments_home;
    String[] titles_home = {};

    ViewPager viewpager_home;
    TabLayout tb_home;
    private String wallet_name,wallet_address;
    private BigDecimal balanceEth = new BigDecimal(0);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_digitalassets;
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
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
                if (unique.getMObjectList().get(i).getCoin_name().equals("ETH")) {
                    uniqueAdressEth = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FDD43","uniqueAdressEth:" + uniqueAdressEth);
                }

            }
        }
        wallet_name = SpUtil.getInstance(DigitalHomeAssetsActivity.this).getString("wallet_name");
        wallet_address = SpUtil.getInstance(DigitalHomeAssetsActivity.this).getString("wallet_address");
        viewpager_home = findViewById(R.id.viewpager);
        tb_home = findViewById(R.id.tb_kline_record);
        fragments_home = new Fragment[]{new LianShang_Fragment(), new HeYue_Fragment(), new KuaLian_Fragment(), new NftZichenFragment()};
        titles_home = new String[]{"链上资产", "合约资产","跨链资产","NFT资产"};
        viewpager_home.setOffscreenPageLimit(fragments_home.length);
        tb_home.setupWithViewPager(viewpager_home);
        //每项只进入一次
        viewpager_home.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_home[position];
            }

            @Override
            public int getCount() {
                return fragments_home.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles_home[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
    }

    @Override
    public void initData() {

        getAssetsInfo();


    }

    private void getAssetsInfo() {

        OkGo.get(UrlConstant.baseUrl + "api/home/getAssetsInfo")
                .execute(new JsonCallback<HomeAssetsInfoBean>() {
                    @Override
                    public void onSuccess(HomeAssetsInfoBean jsonObject, Call call, Response response) {
                        Log.e("FFS333","jsonObject:" + jsonObject);
                        if(jsonObject.getCode() == 200){
                            if(!TextUtils.isEmpty(wallet_name)) {
                                if(wallet_name.equals("UNIQUE")) {
                                    getMonery(jsonObject);
                                }else if(wallet_name.equals("ETH")) {
                                    getEthMonery();
                                }
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

    private void getEthMonery() {
        OkGo.get(UrlConstant.baseUrlLian + "ethereum/getBalanceAll")
                .params("address", wallet_address)
                .headers("projectId", Constants.ETH_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        WalletAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), WalletAsstesBean.class);
                        Log.e("FDD43r总", "homeAsstesBean222:" + new Gson().toJson(homeAsstesBean));
                        if (homeAsstesBean != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {
                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getBalance() + "";
                                        if (balance != null) {
                                            balanceEth = balanceEth.add(new BigDecimal(Double.valueOf(balance)));
                                        }
                                    }
                                if(!TextUtils.isEmpty(balanceEth.toString())) {
                                    Log.e("FDD43r总", "balanceEth:" + balanceEth);
                                    if(new BigDecimal(balanceEth.toString()).compareTo( new BigDecimal("0")) == 0){
                                        tv_balance.setText("≈$0.000000");
                                    }else {
                                        Log.e("FDD43r总", "balanceEth原:" + balanceEth);
                                        Log.e("FDD43r总", "balanceEth改:" + judgeNumber(balanceEth + ""));

                                        if(balanceEth.toString().contains(".")) {
                                            tv_balance.setText("≈$" + judgeNumber(balanceEth + ""));
                                        }else {
                                            tv_balance.setText("≈$" + String.format("%.6f", Double.valueOf(balanceEth.toString())) + "");
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
    public String  judgeNumber(String edt) {

        String temp = edt;
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        if (posDot > 0){
            if (temp.length() - posDot > 6)//如果包含小数点
            {
//                edt.delete(posDot + 3, temp.length());//删除光标前的字符
                return edt.replace(edt.substring(posDot + 3, temp.length()),"");
            }else {
                return temp;
            }
        }
        return "";
    }
    //ibc/3FB063D9DF8E157D75E7B974EA8A74ECDFBF8815E42ED4C274A2B131C4E0D914
    private void getMonery(HomeAssetsInfoBean json) {
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", wallet_address)
                .headers("projectId", Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        Log.e("FF88767","homeAsstesBean:" + jsonObject);
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("uunique")) {
                                        Log.e("FF88767", "余额:" + homeAsstesBean.getResult().getBalances().get(i).getAmount());
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getAmount() + "";
                                        if(!TextUtils.isEmpty(balance)) {
                                            tv_balance.setText(String.format("%.6f", Double.valueOf(balance)) + "");
                                        }
                                    }
                                    balance2 = homeAsstesBean.getResult().getBalances().get(i).getAmount() + "";
                                    if(!TextUtils.isEmpty(balance2)) {
                                        if(json != null && json.getData() != null && json.getData().size() > 0){
                                            for (int j = 0; j < json.getData().size(); j++){
                                                if(json.getData().get(j).getToken().toUpperCase().equals("UNIQUE")){
                                                    balanceEth = balanceEth.add(BigDecimal.valueOf(new BigDecimal(Double.valueOf(balance2)).multiply(new BigDecimal(json.getData().get(j).getTokenAmount())).setScale(6, RoundingMode.DOWN).doubleValue()));                                                            ;
                                                }
                                            }
                                        }

                                    }
                                }
                                Log.e("FF88767","balanceEth:" + balanceEth);
//                                if(balanceEth.toString().contains(".")) {
//                                    tv_balance.setText("≈$" + judgeNumber(balanceEth + ""));
//                                }else {
                                    tv_balance.setText("≈$" + String.format("%.6f", Double.valueOf(balanceEth.toString())/1000000) + "");
//                                tv_balance.setText("≈$" + balanceEth.toString() + "");
//                                }
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

    @OnClick({R.id.img_back,R.id.rela_unique_monery})
    public void setOnclick(View view) {
                switch (view.getId()) {
                    case R.id.img_back://返回
                        finish();
                        break;
                    case R.id.rela_unique_monery:
                        Intent intent1 = new Intent(this, DigitalAssetsActivity.class);
                        startActivity(intent1);
                        break;
                }
    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
