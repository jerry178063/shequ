package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.WalletManagerAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.me.DeleteWallet;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.CustormerServiceDialog;
import com.unique.blockchain.nft.infrastructure.dialog.DeleWalletDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.wallet.CreateUserActivity;
import com.unique.blockchain.nft.view.activity.wallet.FristCreadActivity;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的页面-钱包管理
 */
public class WalletManagerActivity extends BaseActivity implements CustormerServiceDialog.LoadOnclickView, BaseQuickAdapter.OnItemChildClickListener, SafeAdminDialog.OnSafeClickView, DeleWalletDialog.OnDeleClickView {


    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tc_create_wallet)
    TextView tc_create_wallet;
    private UserDao userDao;
    private List<User> users_list;
    private WalletManagerAdapter voteAreaAdapter = new WalletManagerAdapter(R.layout.wallet_manager_item, this, new ArrayList<>());
    private ArrayList<String> list = new ArrayList();
    private CustormerServiceDialog custormerServiceDialog;
    SafeAdminDialog safeAdminDialog;
    DeleWalletDialog deleWalletDialog;
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_manager;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setMainTitle("钱包管理");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(users_list.toString())) {
//                    ToastUtils.showToast(WalletManagerActivity.this, "没有钱包请先创建或者导入钱包!");
                    Intent intent = new Intent(WalletManagerActivity.this, FristCreadActivity.class);
                    startActivity(intent);
                } else {
                    if (TextUtils.isEmpty(SPUtils.getString(WalletManagerActivity.this, "witch_wallet", null))) {
//                        ToastUtils.showToast(WalletManagerActivity.this, "没有选中钱包请先选中钱包!");
                        Intent intent = new Intent(WalletManagerActivity.this, FristCreadActivity.class);
                        startActivity(intent);
                    } else {
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        getData();
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                getData();
                smr.finishRefresh();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voteAreaAdapter);
        voteAreaAdapter.setNewData(users_list);

        voteAreaAdapter.setOnItemSelectedWallet(new WalletManagerAdapter.LinSelectWalletListener() {
            @Override
            public void selectedWallet(int pos) {
                Log.e("FFF444", "555555:" + pos);
                DaoSession daoSession = MyApplication.getDaoSession();
                userDao = daoSession.getUserDao();
                users_list = userDao.loadAll();
                SPUtils.putString(WalletManagerActivity.this, Tools.name, users_list.get(pos).getName());
//                finish();
                SPUtils.putString(WalletManagerActivity.this, "wallet_address",  null);
            }
        });
//        voteAreaAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                EventBus.getDefault().post("choose_wallet");
//                SPUtils.putString(WalletManagerActivity.this, Tools.name, users_list.get(i).getName());
//                finish();
//            }
//        });
    }

    private void getData() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        users_list = userDao.loadAll();

        voteAreaAdapter.setData(this, users_list, userDao);
        voteAreaAdapter.setOnItemChildClickListener(this);
        Log.e("FF8867","钱包管理：" + new Gson().toJson(users_list));
        Log.e("FF8867", "users_list_删除前:" + users_list.size());
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.tc_create_wallet, R.id.tv_import_wallet})
    public void setOnclick(View v) {
                switch (v.getId()) {
                    case R.id.tc_create_wallet://创建钱包
                        custormerServiceDialog = new CustormerServiceDialog(this, "1");
                        custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot, getResources().getString(R.string.turn_airplane_mode), getResources().getString(R.string.for_your_wallet_security));
                        custormerServiceDialog.setLoadOnclickView(this::setOnClickView);
                        custormerServiceDialog.show();
                        break;

                    case R.id.tv_import_wallet://导入钱包
                        custormerServiceDialog = new CustormerServiceDialog(this, "2");
                        custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot, getResources().getString(R.string.turn_airplane_mode), getResources().getString(R.string.for_your_wallet_security));
                        custormerServiceDialog.setLoadOnclickView(this::setOnClickView);
                        custormerServiceDialog.show();
                        break;
                }

    }

    @Override
    public void setOnClickView(String type) {
        //type =1 为创建  2 为导入

        if (type.equals("1")) {
            Intent intent = new Intent(WalletManagerActivity.this, CreateUserActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            //导入钱包
        } else if (type.equals("2")) {
            Intent intent = new Intent(WalletManagerActivity.this, CreateUserActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
        custormerServiceDialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (TextUtils.isEmpty(users_list.toString())) {
//                    ToastUtils.showToast(WalletManagerActivity.this, "没有钱包请先创建或者导入钱包!");
                    Intent intent = new Intent(WalletManagerActivity.this, FristCreadActivity.class);
                    startActivity(intent);
                } else {
                    if (TextUtils.isEmpty(SPUtils.getString(WalletManagerActivity.this, "witch_wallet", null))) {
//                        ToastUtils.showToast(WalletManagerActivity.this, "没有选中钱包请先选中钱包!");
                        Intent intent = new Intent(WalletManagerActivity.this,FristCreadActivity.class);
                        startActivity(intent);
                    } else {
                        finish();
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        switch (view.getId()) {
            case R.id.lin_delete:
                mPosition = position;

//                if (deleWalletDialog == null) {
//                    deleWalletDialog = new DeleWalletDialog(this);
//                    deleWalletDialog.setOnclick(this);
//                    deleWalletDialog.show();
//                } else {
//                    deleWalletDialog.show();
//                }
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

    @Override
    public void setOnSafeClickView(String et_pass) {

        String pass_sha = AESEncrypt.sha(et_pass);
//        for (int i = 0; i < userDao.loadAll().size(); i++) {
        if (userDao.loadAll().get(mPosition).getPsd().equals(pass_sha)) {
            safeAdminDialog.dismiss();
            voteAreaAdapter.remove(mPosition);
            DaoSession daoSession = MyApplication.getDaoSession();
            userDao = daoSession.getUserDao();
            users_list = userDao.loadAll();

            userDao.delete(users_list.get(mPosition));
            if (SPUtils.getString(WalletManagerActivity.this, "witch_wallet", null) != null) {
                if (SPUtils.getString(WalletManagerActivity.this, "witch_wallet", null).equals(users_list.get(mPosition).getName())) {
                    SPUtils.putString(WalletManagerActivity.this, "witch_wallet", null);
                    voteAreaAdapter.notifyDataSetChanged();
                    Log.e("FF3433", "_删除后111111:" );
                }else {
                    Log.e("FF3433", "_删除后222222:" );
                }
            }
            Log.e("FF3433", "users_list_删除后:" + users_list.size());
            EventBus.getDefault().post(new DeleteWallet(users_list.get(mPosition).getName()));
        } else {
            ToastUtils.showToast(WalletManagerActivity.this, "请输入正确的安全密码");
        }

//        }

    }

    @Override
    public void setOnDeleClickView() {
        deleWalletDialog.dismiss();

//        if (safeAdminDialog == null) {
//            safeAdminDialog = new SafeAdminDialog(this);
//            safeAdminDialog.setOnclick(this);
//            safeAdminDialog.show();
//        } else {
//            safeAdminDialog.show();
//        }
    }
}
