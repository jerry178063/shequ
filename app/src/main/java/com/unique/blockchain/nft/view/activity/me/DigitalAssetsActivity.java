package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.WalletAsstesBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.KuaLianTransferActivity;
import com.unique.blockchain.nft.view.activity.wallet.ReceivePaymentActivity;
import com.unique.blockchain.nft.view.activity.wallet.TransferHeYueWalletAssetsActivity;
import com.unique.blockchain.nft.view.activity.wallet.TransferWalletAssetsActivity;
import com.unique.blockchain.nft.view.fragment.wallet.AllassetsFragment;
import com.unique.blockchain.nft.view.fragment.wallet.EthAllassetsFragment;
import com.unique.blockchain.nft.view.fragment.wallet.EthFailureFragment;
import com.unique.blockchain.nft.view.fragment.wallet.EthTransferInFragment;
import com.unique.blockchain.nft.view.fragment.wallet.EthTransferOutFragment;
import com.unique.blockchain.nft.view.fragment.wallet.FailureFragment;
import com.unique.blockchain.nft.view.fragment.wallet.TransferInFragment;
import com.unique.blockchain.nft.view.fragment.wallet.TransferOutFragment;
import com.unique.blockchain.nft.view.fragment.wallet.TronAllassetsFragment;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class DigitalAssetsActivity extends BaseActivity {

    Fragment[] fragments_record;
    String[] titles_record = {};
    @BindView(R.id.viewpager_assets_shuzi)
    ViewPager viewpager_assets_shuzi;
    @BindView(R.id.tb_shuzi_tab)
    TabLayout tb_shuzi_tab;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_name)
    TextView tv_name;
    String balance;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private String zichan_type;//资产类型:1-链上资产,2-合约资产,3-跨链资产
    private String wallet_name;//链上资产点击进来的币种名称
    private String wallet_address;
    private String heyue_type;//合约item类型名称
    private BigDecimal balanceEth = new BigDecimal(0);
    private String account;//单个总资产
    private String wallet_name_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_digitalassets;
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
        wallet_name = getIntent().getStringExtra("wallet_name");
        zichan_type = getIntent().getStringExtra("zichan_type");
        heyue_type = getIntent().getStringExtra("heyue_type");
        wallet_address = SpUtil.getInstance(this).getString("wallet_address");
        account = getIntent().getStringExtra("account");
        wallet_name_title = getIntent().getStringExtra("wallet_name_title");

        tv_name.setText(wallet_name_title);

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
            }
        }
        if (!TextUtils.isEmpty(wallet_name)) {
            if (wallet_name.equals("UNIQUE")) {
                fragments_record = new Fragment[]{new AllassetsFragment(), new TransferInFragment(), new TransferOutFragment(), new FailureFragment()};
                titles_record = new String[]{"全部", "转入", "转出", "失败"};
                viewpager_assets_shuzi.setOffscreenPageLimit(fragments_record.length);
            } else if (wallet_name.equals("ETH")) {
                fragments_record = new Fragment[]{new EthAllassetsFragment(), new EthTransferInFragment(), new EthTransferOutFragment()};
                titles_record = new String[]{"全部", "转入", "转出"};
                viewpager_assets_shuzi.setOffscreenPageLimit(fragments_record.length);
            } else if (wallet_name.equals("TRON")) {
                fragments_record = new Fragment[]{new TronAllassetsFragment(), new EthTransferInFragment(), new EthTransferOutFragment()};
                titles_record = new String[]{"全部", "转入", "转出"};
                viewpager_assets_shuzi.setOffscreenPageLimit(fragments_record.length);
            }
        }


        tb_shuzi_tab.setupWithViewPager(viewpager_assets_shuzi);
        //每项只进入一次
        viewpager_assets_shuzi.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments_record[position];
            }

            @Override
            public int getCount() {
                return fragments_record.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles_record[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });

    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(wallet_name)) {
            if (wallet_name.equals("UNIQUE")) {
//                getMonery();
                tv_balance.setText("≈$" + String.format("%.6f", Double.valueOf(account.replace("≈$",""))) + "");
            } else if (wallet_name.equals("ETH")) {
//                getEthMonery();
                tv_balance.setText("≈$" + String.format("%.6f", Double.valueOf(account.replace("≈$",""))) + "");
            }

        }
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
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getAmount() + "";
                                        if (balance != null) {
                                            tv_balance.setText("≈$" + String.format("%.6f", Double.valueOf(balance) / 1000000) + "");
                                        }
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

    private void getEthMonery() {
        OkGo.get(UrlConstant.baseUrlLian + "ethereum/getBalanceAll")
                .params("address", wallet_address)
                .headers("projectId", Constants.ETH_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        WalletAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), WalletAsstesBean.class);
                        Log.e("FDD43", "homeAsstesBean222:" + new Gson().toJson(homeAsstesBean));
                        if (homeAsstesBean != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {
                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    balance = homeAsstesBean.getResult().getBalances().get(i).getBalance() + "";
                                    if (balance != null) {
                                        balanceEth = balanceEth.add(new BigDecimal(Double.valueOf(balance)));
                                    }
                                }
                                if (!TextUtils.isEmpty(balanceEth.toString())) {
                                    tv_balance.setText("≈$" + judgeNumber(balanceEth + ""));
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

    public String judgeNumber(String edt) {

        String temp = edt;
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        if (posDot > 0) {
            if (temp.length() - posDot > 6)//如果包含小数点
            {
//                edt.delete(posDot + 3, temp.length());//删除光标前的字符
                return edt.replace(edt.substring(posDot + 3, temp.length()), "");
            } else {
                return temp;
            }
        }
        return "";
    }

    @OnClick({R.id.img_back, R.id.tv_transfer, R.id.tv_get_monery})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_transfer://转账
                Log.e("FFF4447g", "zichan_type:" + zichan_type);
                if (zichan_type != null && zichan_type.equals("3")) {
                    Intent intent = new Intent(DigitalAssetsActivity.this, KuaLianTransferActivity.class);
                    startActivity(intent);

                } else if (zichan_type != null && zichan_type.equals("2")) {
                    Intent intent = new Intent(DigitalAssetsActivity.this, TransferHeYueWalletAssetsActivity.class);
                    intent.putExtra("amount", account);
                    if (!TextUtils.isEmpty(wallet_name)) {//从合约资产点击进来
                        intent.putExtra("wallet_name", wallet_name);
                    }
                    intent.putExtra("heyue_type", heyue_type);
                    intent.putExtra("wallet_name_title",wallet_name_title);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DigitalAssetsActivity.this, TransferWalletAssetsActivity.class);
                    intent.putExtra("amount", account);
                    if (!TextUtils.isEmpty(wallet_name)) {//从链上资产点击进来
                        intent.putExtra("wallet_name", wallet_name);
                    }
                    intent.putExtra("wallet_name_title",wallet_name_title);
                    startActivity(intent);
                }
                break;
            case R.id.tv_get_monery://收款
                Intent intent_receive_payment = new Intent(DigitalAssetsActivity.this, ReceivePaymentActivity.class);
                intent_receive_payment.putExtra("wallet_address", wallet_address);
                intent_receive_payment.putExtra("wallet_name_title",wallet_name_title);
                startActivity(intent_receive_payment);
                break;
        }
    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
