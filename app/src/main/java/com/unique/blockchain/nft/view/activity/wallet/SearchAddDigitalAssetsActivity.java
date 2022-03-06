package com.unique.blockchain.nft.view.activity.wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.EncryptUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.AddNewDigitalListAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.domain.wallet.ChainConfigBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddDigitalDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.NftUnique;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.DigitalHomeAssetsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;
import wallet.core.jni.PrivateKey;

/**
 * 搜索添加新资产
 */
public class SearchAddDigitalAssetsActivity extends BaseActivity implements AddDigitalDialog.OnSafeClickView {

    static {
        System.loadLibrary("TrustWalletCore");
    }
    @BindView(R.id.img_back)
    LinearLayout img_back;
    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private AddDigitalDialog addDigitalDialog;
    private AddNewDigitalListAdapter voteAreaAdapter = new AddNewDigitalListAdapter(R.layout.add_digital_list_item, this, new ArrayList<>());

    public String uniqueAdress;
    public String unique_name;
    public User unique;
    private UserDao userDao;
    private List<User> users_list;
    List<UserInfoItem> mObjectList = new ArrayList<>();
    private byte[] did_psd;
    List<ChainConfigBean.Rows> rows = new ArrayList<>();
    NftUnique nftUnique = new NftUnique();
    private String mName;
    @BindView(R.id.tv_no_liutong)
    TextView tv_no_liutong;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_add_digital;
    }

    @Override
    public void initUI() {
        nftUnique.init();
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voteAreaAdapter);
        getData(content);

        voteAreaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent1 = new Intent(SearchAddDigitalAssetsActivity.this, DigitalHomeAssetsActivity.class);
                startActivity(intent1);
            }
        });
        voteAreaAdapter.setOnItemSelectedWallet(new AddNewDigitalListAdapter.LinSelectWalletListener() {
            @Override
            public void selectedWallet(String name) {
                mName = name;
                if (addDigitalDialog == null) {
                    addDigitalDialog = new AddDigitalDialog(SearchAddDigitalAssetsActivity.this);
                    addDigitalDialog.setOnclick(SearchAddDigitalAssetsActivity.this);
                    addDigitalDialog.show();
                } else {
                    addDigitalDialog.show();
                }
            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_search})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_search://搜索
                if(TextUtils.isEmpty(et_search.getText().toString())){
                    ToastUtil.showShort(SearchAddDigitalAssetsActivity.this,"请输入主币资产名称");
                    return;
                }
                content = et_search.getText().toString();
                getData(content);
                break;
        }
    }

    private void getData(String content) {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        users_list = userDao.loadAll();
//        Log.e("FDS433", "users_list:" + new Gson().toJson(users_list));
        Log.e("FDS433", "-----------------------------------");
        Log.e("FDS433", "Tools.name:" + SPUtils.getString(this, Tools.name, ""));
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("FF543", "NAME:" + SPUtils.getString(this, Tools.name, ""));
        }
        if (unique != null) {
            if (unique.getMObjectList() != null && unique.getMObjectList().size() > 0) {
                if (mObjectList != null) {
                    mObjectList.clear();
                }
                mObjectList.addAll(unique.getMObjectList());
                Log.e("gdde22", "大小:" + unique.getMObjectList().size());
                for (int i = 0; i < unique.getMObjectList().size(); i++) {
                    Log.e("FDS433", "mobject:" + new Gson().toJson(unique.getMObjectList().get(i)));
//                    if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
//                        uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
//                    }
                }
                if (voteAreaAdapter != null) {
//                    voteAreaAdapter.setNewData(mObjectList);
                }

            }
        }
        Log.e("FFF444", "content:" + content);
        OkGo.get(UrlConstant.baseUrl + "chainConfig/list")
                .params("keyword",content)
                .readTimeOut(10000)
                .execute(new JsonCallback<ChainConfigBean>() {
                    @Override
                    public void onSuccess(ChainConfigBean jsonObject, Call call, Response response) {
                        if(jsonObject.getCode() == 200) {
                            Log.e("FFF444", "1111:" + jsonObject);
                            if(jsonObject.getRows() != null && jsonObject.getRows().size() > 0){

                                if(rows != null){
                                    rows.clear();
                                }
                                rows.addAll(jsonObject.getRows());
                                if (voteAreaAdapter != null) {
                                    voteAreaAdapter.setNewData(rows);
                                }
                                tv_no_liutong.setVisibility(View.GONE);
                            }else {
                                tv_no_liutong.setVisibility(View.VISIBLE);
                            }

                        }else {
                            tv_no_liutong.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF444", "4444");
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

    }
    private List<UserInfoItem> list = new ArrayList<UserInfoItem>();
    private HDWallet hdWallet;
    Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            //新资产在sdk中遍历存在，可以添加进去
            Log.e("FFF4447","mName:" + mName);
            if(!nftUnique.isCunZai(mName).equals("不存在")) {
                if (list != null) {
                    list.clear();
                }
                if(mName.equals("AETERNITY")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 993 + "\'/0\'/0/0", CoinType.AETERNITY.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("AION")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 425 + "\'/0\'/0/0", CoinType.AION.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("BINANCE")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 714 + "\'/0\'/0/0", CoinType.BINANCE.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("BITCOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 0 + "\'/0\'/0/0", CoinType.BITCOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("BITCOINCASH")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 145 + "\'/0\'/0/0", CoinType.BITCOINCASH.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("BITCOINGOLD")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 156 + "\'/0\'/0/0", CoinType.BITCOINGOLD.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("CALLISTO")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 820 + "\'/0\'/0/0", CoinType.CALLISTO.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("CARDANO")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 1815 + "\'/0\'/0/0", CoinType.CARDANO.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("COSMOS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 118 + "\'/0\'/0/0", CoinType.COSMOS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("DASH")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 5 + "\'/0\'/0/0", CoinType.DASH.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("DECRED")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 42 + "\'/0\'/0/0", CoinType.DECRED.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("DIGIBYTE")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 20 + "\'/0\'/0/0", CoinType.DIGIBYTE.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("DOGECOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 3 + "\'/0\'/0/0", CoinType.DOGECOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("EOS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 194 + "\'/0\'/0/0", CoinType.EOS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ETHEREUM")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 60 + "\'/0\'/0/0", CoinType.ETHEREUM.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ETHEREUMCLASSIC")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 61 + "\'/0\'/0/0", CoinType.ETHEREUMCLASSIC.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("FIO")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 235 + "\'/0\'/0/0", CoinType.FIO.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("GOCHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 6060 + "\'/0\'/0/0", CoinType.GOCHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("GROESTLCOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 17 + "\'/0\'/0/0", CoinType.GROESTLCOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ICON")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 74 + "\'/0\'/0/0", CoinType.ICON.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("IOTEX")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 304 + "\'/0\'/0/0", CoinType.IOTEX.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("KAVA")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 459 + "\'/0\'/0/0", CoinType.KAVA.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("KIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 2017 + "\'/0\'/0/0", CoinType.KIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("LITECOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 2 + "\'/0\'/0/0", CoinType.LITECOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("MONACOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 22 + "\'/0\'/0/0", CoinType.MONACOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("NEBULAS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 2718 + "\'/0\'/0/0", CoinType.NEBULAS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("NULS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 8964 + "\'/0\'/0/0", CoinType.NULS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("NANO")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 165 + "\'/0\'/0/0", CoinType.NANO.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("NEAR")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 397 + "\'/0\'/0/0", CoinType.NEAR.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("NIMIQ")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 242 + "\'/0\'/0/0", CoinType.NIMIQ.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ONTOLOGY")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 1024 + "\'/0\'/0/0", CoinType.ONTOLOGY.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("POANETWORK")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 178 + "\'/0\'/0/0", CoinType.POANETWORK.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("QTUM")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 2301 + "\'/0\'/0/0", CoinType.QTUM.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("XRP")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 144 + "\'/0\'/0/0", CoinType.XRP.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("SOLANA")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 501 + "\'/0\'/0/0", CoinType.SOLANA.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("STELLAR")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 148 + "\'/0\'/0/0", CoinType.STELLAR.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("TON")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 396 + "\'/0\'/0/0", CoinType.TON.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("TEZOS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 1729 + "\'/0\'/0/0", CoinType.TEZOS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("THETA")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 500 + "\'/0\'/0/0", CoinType.THETA.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("THUNDERTOKEN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 1001 + "\'/0\'/0/0", CoinType.THUNDERTOKEN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("NEO")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 888 + "\'/0\'/0/0", CoinType.NEO.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("TOMOCHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 889 + "\'/0\'/0/0", CoinType.TOMOCHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("TRON")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 195 + "\'/0\'/0/0", CoinType.TRON.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("VECHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 818 + "\'/0\'/0/0", CoinType.VECHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("VIACOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 14 + "\'/0\'/0/0", CoinType.VIACOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("WANCHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 5718350 + "\'/0\'/0/0", CoinType.WANCHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ZCASH")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 133 + "\'/0\'/0/0", CoinType.ZCASH.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ZCOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 136 + "\'/0\'/0/0", CoinType.ZCOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ZILLIQA")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 313 + "\'/0\'/0/0", CoinType.ZILLIQA.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ZELCASH")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 19167 + "\'/0\'/0/0", CoinType.ZELCASH.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("RAVENCOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 175 + "\'/0\'/0/0", CoinType.RAVENCOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("WAVES")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 5741564 + "\'/0\'/0/0", CoinType.WAVES.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("TERRA")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 330 + "\'/0\'/0/0", CoinType.TERRA.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("HARMONY")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 1023 + "\'/0\'/0/0", CoinType.HARMONY.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ALGORAND")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 283 + "\'/0\'/0/0", CoinType.ALGORAND.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("KUSAMA")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 434 + "\'/0\'/0/0", CoinType.KUSAMA.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("POLKADOT")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 354 + "\'/0\'/0/0", CoinType.POLKADOT.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("FILECOIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 461 + "\'/0\'/0/0", CoinType.FILECOIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("ELROND")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 508 + "\'/0\'/0/0", CoinType.ELROND.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("BANDCHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 494 + "\'/0\'/0/0", CoinType.BANDCHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("SMARTCHAINLEGACY")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 10000714 + "\'/0\'/0/0", CoinType.SMARTCHAINLEGACY.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("SMARTCHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 20000714 + "\'/0\'/0/0", CoinType.SMARTCHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("OASIS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 474 + "\'/0\'/0/0", CoinType.OASIS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("POLYGON")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 966 + "\'/0\'/0/0", CoinType.POLYGON.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("THORCHAIN")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 931 + "\'/0\'/0/0", CoinType.THORCHAIN.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("USDG")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 881 + "\'/0\'/0/0", CoinType.USDG.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("GAUSS")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 991 + "\'/0\'/0/0", CoinType.GAUSS.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("IGPC")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 992 + "\'/0\'/0/0", CoinType.IGPC.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("FEC")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 993 + "\'/0\'/0/0", CoinType.FEC.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("GPB")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 994 + "\'/0\'/0/0", CoinType.GPB.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }else if(mName.equals("UNIQUE")) {
                    list.add(new UserInfoItem(mName, "m/44\'/" + 995 + "\'/0\'/0/0", CoinType.UNIQUE.value(), getResources().getResourceEntryName(R.mipmap.app_logo_new)));
                }








                    hdWallet = new HDWallet(SpUtil.getInstance(SearchAddDigitalAssetsActivity.this).getString("content") + "", "");
                unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(SearchAddDigitalAssetsActivity.this, Tools.name, ""))).build().unique();

                List<UserInfoItem> userInfoItem = unique.getMObjectList();
                List<UserInfoItem> userInfoItems = setCoinKey(hdWallet, list);
                userInfoItem.addAll(userInfoItems);
                unique.setMObjectList(userInfoItem);
                try {
                    userDao.update(unique);
                    Log.e("FF543", "更新成功:" + SPUtils.getString(SearchAddDigitalAssetsActivity.this, Tools.name, ""));
                } catch (Exception e) {
                    Log.e("FF543", "NAME:" + SPUtils.getString(SearchAddDigitalAssetsActivity.this, Tools.name, ""));
                }
            }else {
                ToastUtil.showShort(SearchAddDigitalAssetsActivity.this,"已存在!");
            }
//            if (voteAreaAdapter != null) {
//                voteAreaAdapter.notifyDataSetChanged();
//            }
        }
    };
    List<String> name = new ArrayList<>();
    @Override
    public void setOnSafeClickView(String et_pass) {
        if (mObjectList != null) {
            if(name != null){
                name.clear();
            }
            for (int i = 0; i < mObjectList.size(); i++) {
                if (mObjectList.get(i).getCoin_name().contains(mName)) {//判断列表资产是否在本地数据库存在,有就存到新的数组name中
                    name.add("" + i);
                }
            }
            //根据name判断是否存在资产
            if(name != null && name.size() > 0){
                ToastUtil.showShort(SearchAddDigitalAssetsActivity.this, "资产已经存在!");
            }else {//不存在可以添加
                handler.sendEmptyMessage(555);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /***
     * 拿到钱包的地址
     * @param wallet
     * @param mlist
     * @return
     */
    public List<UserInfoItem> setCoinKey(HDWallet wallet, List<UserInfoItem> mlist) {
        List<UserInfoItem> userInfoItems = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            PrivateKey key = wallet.getKey(CoinType.createFromValue(list.get(i).getNum()), mlist.get(i).getCoin_psd());
            if (CoinType.createFromValue(mlist.get(i).getNum()) != null) {
                String key_coin = CoinType.createFromValue(mlist.get(i).getNum()).deriveAddress(key);
                byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(unique.getPsd()), "AES/ECB/PKCS5Padding", null);
                userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes));
                Log.e("ffff444", "pad:" + unique.getPsd() + "---" + "bytes:" + bytes);
            } else {

                switch (mlist.get(i).getCoin_name()) {
                    case "UNIQUE":
                        String key_coin = CoinType.UNIQUE.deriveAddress(key);
                        byte[] bytes = EncryptUtils.encryptAES(key.data(), hexString2Bytes(unique.getPsd()), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes));
                        Log.e("ffff444", "pad_GAUSS:" + unique.getPsd() + "---" + "bytes_GAUSS:" + bytes);
                        break;
                    case "USDG":
                        String key_coin_usdg = CoinType.COSMOS.deriveAddress(key);
                        byte[] bytes1 = EncryptUtils.encryptAES(key.data(), hexString2Bytes(unique.getPsd()), "AES/ECB/PKCS5Padding", null);
                        userInfoItems.add(new UserInfoItem(mlist.get(i).getCoin_name(), key_coin_usdg, mlist.get(i).getNum(), mlist.get(i).getImg(), bytes1));
                        break;
                    default:

                        Log.d("teddddd", mlist.get(i).getCoin_name());
                }
            }

        }
        return userInfoItems;
    }

    public static byte[] hexString2Bytes(String hexString) {
        if (com.blankj.utilcode.util.StringUtils.isSpace(hexString)) return new byte[0];
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
