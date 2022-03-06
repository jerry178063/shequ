package com.unique.blockchain.nft.view.fragment.trade;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MarkAllAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.WalletAsstesBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.me.DigitalAssetsActivity;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkAllPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkAllCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 *
 * */
public class LianShang_Fragment extends BaseFragment {

    @BindView(R.id.tv_unique_monery)
    TextView tv_unique_monery;
    public User unique;
    public UserDao userDao;
    public  String uniqueAdress;
    String balance,wallet_name;
    @BindView(R.id.rela_unique_monery)
    RelativeLayout rela_unique_monery;
    @BindView(R.id.tv_name_lianshang)
    TextView tv_name_lianshang;
    @BindView(R.id.img_icon)
    ImageView img_icon;
    @Override
    protected void initData() {

        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lianshang;
    }

    @OnClick({R.id.rela_unique_monery})
    public void setOnClick(View view){
        switch (view.getId()){
            case R.id.rela_unique_monery:
                Intent intent1 = new Intent(getContext(), DigitalAssetsActivity.class);
                intent1.putExtra("wallet_name",wallet_name);
                intent1.putExtra("account",tv_unique_monery.getText().toString());
                intent1.putExtra("wallet_name_title",wallet_name);
                startActivity(intent1);
                break;
        }
    }
    @Override
    public void initView() {
        wallet_name = SpUtil.getInstance(getContext()).getString("wallet_name");
        if(wallet_name != null) {
            tv_name_lianshang.setText(wallet_name);
            if(wallet_name.equals("UNIQUE")){
                img_icon.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.wallet_unique_img));
            }else if(wallet_name.equals("ETH")){
                img_icon.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.wallet_eth_img));
            }else if(wallet_name.equals("TRON")){
                img_icon.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.wallet_tron_img));
            }
        }
        getMonery();
    }
    private void getMonery() {
        Log.e("FDD43", "wallet_address:" + SpUtil.getInstance(getContext()).getString("wallet_address"));
        if(SpUtil.getInstance(getContext()).getString("wallet_name") != null) {
            if(SpUtil.getInstance(getContext()).getString("wallet_name").equals("ETH")) {
                if(SpUtil.getInstance(getContext()).getString("wallet_address") != null) {
//                OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                    OkGo.get(UrlConstant.baseUrlLian + "ethereum/getBalanceAll")
                            .params("address", SpUtil.getInstance(getContext()).getString("wallet_address"))
                            .headers("projectId", Constants.ETH_HEADRE)
                            .execute(new JsonCallback<JsonObject>() {
                                @Override
                                public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                                    WalletAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), WalletAsstesBean.class);
                                    Log.e("FDD43", "homeAsstesBean222:" + new Gson().toJson(homeAsstesBean));
                                    if (homeAsstesBean != null && homeAsstesBean.getResult() != null) {
                                        Log.e("FDD43", "000000");
                                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {
                                            Log.e("FDD43", "11111111");
                                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                                if (homeAsstesBean.getResult().getBalances().get(i).getCoinType().contains("ETH")) {
                                                    Log.e("FDD43", "22222222");
                                                    balance = homeAsstesBean.getResult().getBalances().get(i).getBalance() + "";
                                                    if (balance != null) {
                                                        Log.e("FDD43", "33333333");
                                                        tv_unique_monery.setText("≈$" + String.format("%.6f", Double.valueOf(balance)) + "");
                                                    }else {
                                                        tv_unique_monery.setText("≈$0.000000");
                                                    }
                                                    break;
                                                }

                                            }
                                        }else {
                                            tv_unique_monery.setText("≈$0.000000");
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
            }else if(SpUtil.getInstance(getContext()).getString("wallet_name").equals("UNIQUE")){
                if(SpUtil.getInstance(getContext()).getString("wallet_address") != null) {
                    OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
//                    OkGo.get(UrlConstant.baseUrlLian + "ethereum/getBalanceAll")
                            .params("account", SpUtil.getInstance(getContext()).getString("wallet_address"))
                            .headers("projectId", Constants.UNIQUE_HEADRE)
                            .execute(new JsonCallback<JsonObject>() {
                                @Override
                                public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                                    HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                                    Log.e("FDD43g", "homeAsstesBean222:" + new Gson().toJson(homeAsstesBean));
                                    if (homeAsstesBean != null && homeAsstesBean.getResult() != null) {
                                        Log.e("FDD43g", "000000");
                                        if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {
                                            Log.e("FDD43g", "11111111");
                                            for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
//                                                    Log.e("FDD43g", "22222222");
                                                    balance = homeAsstesBean.getResult().getBalances().get(i).getAmount() + "";
                                                    if(homeAsstesBean.getResult().getBalances().get(i).getDenom().equals("uunique")) {
                                                        if (balance != null) {
                                                            Log.e("FDD43g", "33333333");
                                                            tv_unique_monery.setText("≈$" + String.format("%.6f", Double.valueOf(balance) / 1000000) + "");
                                                        } else {
                                                            tv_unique_monery.setText("≈$0.000000");
                                                        }
                                                        break;
                                                    }
                                            }
                                        }else {
                                            tv_unique_monery.setText("≈$0.000000");
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
            }
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}