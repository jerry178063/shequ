package com.unique.blockchain.nft.view.activity.wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.DoubleClickUtil;
import com.space.exchange.biz.net.utils.NetworkUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.HomeDealBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.CrossBackDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.HttpUtil;
import com.unique.blockchain.nft.view.fragment.wallet.AllassetsFragment;
import com.unique.blockchain.nft.view.fragment.wallet.FailureFragment;
import com.unique.blockchain.nft.view.fragment.wallet.TransferInFragment;
import com.unique.blockchain.nft.view.fragment.wallet.TransferOutFragment;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/***
 *
 * 资产详情
 * */
public class AssetsDetailActivity extends BaseActivity implements CrossBackDialog.OnClickView {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tb_assets_detail)
    TabLayout tb_assets_detail;
    @BindView(R.id.viewpager_assets_detail)
    ViewPager viewpager_assets_detail;
    @BindView(R.id.tv_transfer)
    TextView tv_transfer;
    @BindView(R.id.tv_receive_payment)
    TextView tv_receive_payment;
    @BindView(R.id.tv_manager_assets)
    TextView tv_manager_assets;
    @BindView(R.id.tv_all_amount_detail)
    TextView tv_all_amount;
    @BindView(R.id.tv_yue_equal_detail)
    TextView tv_yue_equal;
    private UserInfoItem userInfoItem;

    final Fragment[] fragments = {new AllassetsFragment(), new TransferInFragment(), new TransferOutFragment(), new FailureFragment()};
    String[] titles = {};
    private String type, amount;
    private double lastDealAmount;
    public int totalTabNum = 4;//假如有四个Fragment页面
    public int openTabNum = 0;//当前已打开的页面数量

    @Override
    protected int getLayoutId() {
        return R.layout.activity_assets_detail;
    }
    private Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null) {
                if(msg.what == 0 && msg.getData() != null && msg.getData().getString("responseText") != null ) {
                    HomeAsstesBean homeAsstesBean = new Gson().fromJson(msg.getData().getString("responseText"), HomeAsstesBean.class);
                    if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("ugpb")) {

                                    try {
                                        if (homeAsstesBean.getResult().getBalances().get(i).getAmount() != null) {
                                            tv_all_amount.setText(String.format("%.6f",Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount())/1000000) + "");
                                            Log.e("FFKKKS","AMOUNT:" + String.format("%.6f", Double.valueOf(tv_all_amount.getText().toString().trim()) * 0.00334));
                                            tv_yue_equal.setText("≈" + String.format("%.6f", Double.valueOf(tv_all_amount.getText().toString().trim()) * 0.00334) + "" + "UGPB");
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                }

                            }
                        }else {
                            tv_all_amount.setText("0.000000");
                            tv_yue_equal.setText("0.000000");
                        }
                    }
                }else if(msg.what == 1){
                    HomeDealBean homeDealBean = new Gson().fromJson(msg.getData().getString("responseText"),HomeDealBean.class);
                    if(homeDealBean != null){
                        if(homeDealBean.getCode().equals("200")) {

                            for (int i = 0; i < homeDealBean.getData().size();i++){
                                if(homeDealBean.getData().get(i).getBuyToken().equals("GPB")) {
                                    lastDealAmount = homeDealBean.getData().get(i).getLastDealAmount();
                                }
                            }

                        }
                    }
                }

            }
        }
    };

    @Override
    public void initUI() {
        titles = new String[]{getResources().getString(R.string.all_assets), getResources().getString(R.string.transfer_in),
                getResources().getString(R.string.transfer_out), getResources().getString(R.string.failure_assets)};
        top_s_title_toolbar.setLeftTitleText("GPB");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userInfoItem = (UserInfoItem) getIntent().getSerializableExtra("name");
        amount = getIntent().getStringExtra("amount");
        Log.e("KKKD", "amount:" + amount);
        if (!TextUtils.isEmpty(amount)) {
//            tv_all_amount.setText(amount + "");
        }
        viewpager_assets_detail.setOffscreenPageLimit(4);
//        viewpager_assets_detail.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                Log.e("FFF44","i:" + i);
//                //滑动监听加载数据，一次只加载一个标签页
////                if(openTabNum < totalTabNum){
////                    openTabNum++;
////                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
        //每项只进入一次
        viewpager_assets_detail.setAdapter(fragmentPagerAdapter);
        tb_assets_detail.setupWithViewPager(viewpager_assets_detail);
        tb_assets_detail.getTabAt(0).select();//设置第一个为选中

        lastDealAmount = getIntent().getDoubleExtra("lastDealAmount", 0);
        type = getIntent().getStringExtra("type");
        if (type != null) {
            if (type.equals("1")) {
                tv_manager_assets.setVisibility(View.GONE);
                top_s_title_toolbar.mTxtRightTitle.setBackground(null);
            } else if (type.equals("2")) {
                tv_manager_assets.setVisibility(View.GONE);
                top_s_title_toolbar.mTxtRightTitle.setBackground(getResources().getDrawable(R.mipmap.icon_kuahui));
            } else {
                top_s_title_toolbar.mTxtRightTitle.setBackground(null);
                //如果是验证人就显示管理，非验证人就不显示
                tv_manager_assets.setVisibility(View.VISIBLE);
            }
        }
        if (!TextUtils.isEmpty(amount)) {
//            tv_yue_equal.setText("≈" + String.format("%.6f", Double.valueOf(amount) * 0.00334) + "" + "USDG");
        }
        postData();
    }

    FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    };
    User unique;
    private UserDao userDao;
    private void postData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(AssetsDetailActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        HashMap<String, String> map = new HashMap<>();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
//                map.put("address", unique.getMObjectList().get(0).getCoin_psd());
                    map.put("account", unique.getMObjectList().get(i).getCoin_psd());
                    Log.e("FFF3445","首页钱包addresss:" + unique.getMObjectList().get(i).getCoin_psd());
                    break;
                }
            }
        }
        map.put("projectId", Constants.UNIQUE_HEADRE);

        HttpUtil.sendOKHttpGetRequest(UrlConstant.baseUrlLian +"gpb/getBalanceAll",map,null,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                Log.e("FFF444","responseText_5:" + responseText);
                dismissDialog();
                //请求成功
                if(responseText != null && !responseText.contains("html")) {
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

    @Override
    public void bindView(Bundle bundle) {

    }

    private CrossBackDialog crossBackDialog;

    @OnClick({R.id.tv_transfer, R.id.tv_receive_payment, R.id.tv_manager_assets, R.id.txt_right_title})
    public void setOnclick(View view) {
//        if(FastClick.isFastClick()) {
        if (NetworkUtil.isConnected(AssetsDetailActivity.this)) {
            if (DoubleClickUtil.isCommonClick()) {
                switch (view.getId()) {
                    case R.id.tv_transfer://转账

                        Intent intent = new Intent(AssetsDetailActivity.this, TransferWalletAssetsActivity.class);
                        intent.putExtra("amount", amount + "");
                        startActivity(intent);

                        break;

                    case R.id.tv_receive_payment://收款

                        Intent intent_receive_payment = new Intent(AssetsDetailActivity.this, ReceivePaymentActivity.class);
                        startActivity(intent_receive_payment);

                        break;
                    case R.id.tv_manager_assets://管理

                        Intent intent_assets_manager = new Intent(AssetsDetailActivity.this, AssetsManagerActivity.class);
                        startActivity(intent_assets_manager);

                        break;
                    case R.id.txt_right_title://跨回
                        if (type != null) {
                            if (type.equals("2")) {
                                if (crossBackDialog == null) {
                                    crossBackDialog = new CrossBackDialog(this);
                                    crossBackDialog.setOnclick(this);
                                    crossBackDialog.show();
                                } else {
                                    crossBackDialog.setOnclick(this);
                                    crossBackDialog.show();
                                }
                            }
                        }
                        break;
                }
            }
        } else {
            ToastUtil.showShort(AssetsDetailActivity.this, getResources().getString(R.string.the_network_is_not_open));
        }
//        }
    }

    @Override
    public void setOnClickView() {
        crossBackDialog.dismiss();
        Intent intent = new Intent(AssetsDetailActivity.this, CrossBackAssetsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        postData();
    }
}
