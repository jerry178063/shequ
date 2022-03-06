package com.unique.blockchain.nft.view.fragment.trade;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.DoubleClickUtil;
import com.space.exchange.biz.net.utils.NetworkUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MyCommissionAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.DelationItemBean;
import com.unique.blockchain.nft.domain.node.DelegetaJBean;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.GetRootInvitationBean;
import com.unique.blockchain.nft.domain.node.GetTiquValitionBean;
import com.unique.blockchain.nft.domain.node.GetValAddressBean;
import com.unique.blockchain.nft.domain.node.GetValitionBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.node.PledgeAmountBean;
import com.unique.blockchain.nft.domain.node.ShareAmountBean;
import com.unique.blockchain.nft.domain.wallet.BroadcastBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsShuhuiDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtil;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.HttpUtil;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.wallet.MinerFeeActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;

/**
 * 我的委托
 */
public class MyCommissionFragment extends BaseFragment implements AddtionalAssetsDialog.OnClickView, AddtionalAssetsShuhuiDialog.OnClickView,
        SafeAdminDialog.OnSafeClickView, AddtionalAssetsShuhuiDialog.KuangGongOnClickView, AddtionalAssetsDialog.KuangGongOneOnClickView, AddtionalAssetsDialog.OnCancelClickView
        , AddtionalAssetsShuhuiDialog.OnShuhuiCancelClickView {
    static {
        System.loadLibrary("TrustWalletCore");
    }

    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    @BindView(R.id.tv_my_link)
    TextView tv_my_link;//我的链接
    @BindView(R.id.tv_pledge_amount)
    TextView tv_pledge_amount;//链接质押数量
    @BindView(R.id.tv_link_income)
    TextView tv_link_income;//链接收益

    User unique;
    private UserDao userDao;
    String gaussAdress, address, valitorAdress, canGaussMonery, rootAlletAddress, rootValiAddress, rootAddress;

    private MyCommissionAdapter myCommissionAdapter = new MyCommissionAdapter(R.layout.my_commission_item, new ArrayList<>());
    private ArrayList<String> list = new ArrayList();
    AddtionalAssetsDialog addtionalAssetsDialog;
    SafeAdminDialog safeAdminDialog;
    private List<DelegetaJBean.ResultBean> lists;
    private int itemPosition;
    //次数
    private String sequence1, weituoNum, strFee, isRootAdress;
    private String account_num, shouyiAddress, shouyiAddress2, mMonery;
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
                                        canGaussMonery = String.format("%.6f", Double.valueOf(homeAsstesBean.getResult().getBalances().get(i).getAmount()) / 1000000) + "";
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
    protected int getLayoutId() {
        return R.layout.fragment_my_commison;
    }

    @Override
    public void initView() {

        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                    gaussAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                smr.finishRefresh();
                if (!NetworkUtil.isConnected(getContext())) {
                    ToastUtil.showShort(getContext(), getResources().getString(R.string.the_network_is_not_open));
                    return;
                }
                getSequece();
//                postDataLastDealAmount();
                postMoneryData();
                getRootInvitationCode();
                postValAddress();

            }
        });
        lists = new ArrayList<>();
        if (lists != null) {
            lists.clear();
        }
        getSequece();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myCommissionAdapter);
        myCommissionAdapter.setNewData(lists);
//        postDataLastDealAmount();
        postMoneryData();
        HashMap<String, String> map = new HashMap<>();


        getRootInvitationCode();
        myCommissionAdapter.setIncomelickView(new MyCommissionAdapter.GetIncomelickView() {
            @Override
            public void setIncomeView(DelegetaJBean.ResultBean item, int pos, String monery) {
                DelationItemBean delationItemBean = new DelationItemBean();
                delationItemBean.setName("income");
                delationItemBean.setContent("");
//                EventBus.getDefault().post(delationItemBean);
                itemPosition = pos;
                shouyiAddress = item.getValidator_address();
                mMonery = monery;
                shouyiOnClick();
            }
        });
        myCommissionAdapter.setCommissionlickView(new MyCommissionAdapter.GetCommissionlickView() {
            @Override
            public void setCommissionView(DelegetaJBean.ResultBean item, int pos,String yongjin) {
                DelationItemBean delationItemBean = new DelationItemBean();
                delationItemBean.setName("commission");
                delationItemBean.setContent("");
//                EventBus.getDefault().post(delationItemBean);
                itemPosition = pos;
                shouyiAddress = item.getValidator_address();
                yongjin(yongjin);
            }
        });
        myCommissionAdapter.setRedemptionlickView(new MyCommissionAdapter.RedemptionlickView() {
            @Override
            public void setRedemptionView(DelegetaJBean.ResultBean item, int pos, String shares) {
                weituoNum = shares + "";
                DelationItemBean delationItemBean = new DelationItemBean();
                delationItemBean.setName("redemption");
                delationItemBean.setContent(weituoNum);
//                EventBus.getDefault().post(delationItemBean);
                itemPosition = pos;

                shouyiAddress = item.getValidator_address();

                shuhui(delationItemBean);
            }
        });
        postValAddress();
    }

    private void getShareAmount() {
        OkGo.get(UrlConstant.baseCreateInportWallet + "home/page/getDirShareAndIndShare")
                .params("address", gaussAdress + "")
                .execute(new JsonCallback<ShareAmountBean>() {
                    @Override
                    public void onSuccess(ShareAmountBean jsonObject, Call call, Response response) {
                        dismissDialog();
                        //请求成功
                        if(jsonObject.getCode() == 200 && jsonObject.getResult() != null && !jsonObject.getResult().equals("0") && jsonObject.getResult() != null){
                            tv_link_income.setText(jsonObject.getResult() + "");
                        }else {
                            tv_link_income.setText("0.000000");
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF344y", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    private void getPledgeAmount() {
        OkGo.get(UrlConstant.baseCreateInportWallet + "home/page/getDirSharePle")
                .params("validatorAddress", rootValiAddress + "")
                .params("walletAddress", gaussAdress + "")
                .execute(new JsonCallback<PledgeAmountBean>() {
                    @Override
                    public void onSuccess(PledgeAmountBean jsonObject, Call call, Response response) {
                        dismissDialog();
                        //请求成功
                        if(jsonObject.getCode() == 200 && jsonObject.getData() != null){
                            tv_pledge_amount.setText(String.format("%.6f",Double.valueOf(jsonObject.getData().getShares())) + "");
                            tv_my_link.setText(jsonObject.getData().getWallets() + "");
                        }else {
                            tv_pledge_amount.setText("0.000000");
                            tv_my_link.setText("0.000000");
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


    private void shuhui(DelationItemBean delationItemBean) {
        dialog_position = 3;

        shengyuGause = delationItemBean.getContent() + "";


            if (addtionalAssetsShuhuiDialog == null) {
                addtionalAssetsShuhuiDialog = new AddtionalAssetsShuhuiDialog(getContext());
                addtionalAssetsShuhuiDialog.setOnclick(this);
                addtionalAssetsShuhuiDialog.setKuangGongOnClickView(this);
                addtionalAssetsShuhuiDialog.setOnCancelClickView(this);
                addtionalAssetsShuhuiDialog.show();
            } else {
                addtionalAssetsShuhuiDialog.show();
            }

            addtionalAssetsShuhuiDialog.tv_title2.setHint("最小数量0.000001个");
            addtionalAssetsShuhuiDialog.tv_title2.setBackgroundColor(getContext().getColor(R.color.color_F5F5F6));
            addtionalAssetsShuhuiDialog.tv_can_gauss.setText("可赎回数量: " + delationItemBean.getContent() + "");
            addtionalAssetsShuhuiDialog.tv_title_dialog.setText("赎回本金");

            addtionalAssetsShuhuiDialog.tv_can_gauss.setVisibility(View.VISIBLE);
            getSequece();
    }

    private void yongjin(String yongjin) {
        dialog_position = 2;
        if (yongjin.equals("0.000000")) {
            ToastUtil.showShort(getContext(), "佣金不足!");
            return;
        }
        if (addtionalAssetsDialog == null) {
            addtionalAssetsDialog = new AddtionalAssetsDialog(getContext());
            addtionalAssetsDialog.setOnclick(this);
            addtionalAssetsDialog.setKuangGongOneOnClickView(this);
            addtionalAssetsDialog.setOnCancelClickView(this);
            addtionalAssetsDialog.show();
        } else {
            addtionalAssetsDialog.show();
        }
        addtionalAssetsDialog.tv_can_gauss.setText("");
        addtionalAssetsDialog.tv_title_dialog.setText("提取佣金");
        addtionalAssetsDialog.tv_title2.setText("是否提取所有佣金");
        addtionalAssetsDialog.tv_can_gauss.setVisibility(View.GONE);
        addtionalAssetsDialog.tv_title2.setBackgroundColor(getContext().getColor(R.color.white));
        getSequece();
    }

    private void shouyiOnClick() {
        dialog_position = 1;
        if (mMonery.equals("0.000000")) {
            ToastUtil.showShort(getContext(), "委托收益不足!");
            return;
        }
        if (addtionalAssetsDialog == null) {
            addtionalAssetsDialog = new AddtionalAssetsDialog(getContext());
            addtionalAssetsDialog.setOnclick(this);
            addtionalAssetsDialog.setKuangGongOneOnClickView(this);
            addtionalAssetsDialog.setOnCancelClickView(this);
            addtionalAssetsDialog.show();
        } else {
            addtionalAssetsDialog.show();
        }
        addtionalAssetsDialog.tv_can_gauss.setText("");
        addtionalAssetsDialog.tv_title_dialog.setText("提取收益");
        addtionalAssetsDialog.tv_title2.setText("是否提取所有收益");
        addtionalAssetsDialog.tv_can_gauss.setVisibility(View.GONE);
        addtionalAssetsDialog.tv_title2.setBackgroundColor(getContext().getColor(R.color.white));
        getSequece();
    }

    //是否是验证人
    private void postValAddress() {
        Log.e("FFF344", "gaussAdress:" + gaussAdress);
        showDialog();
        OkGo.get(UrlConstant.baseUrlLian + "gpb/getValAddress")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("accAddress", gaussAdress)
                .execute(new JsonCallback<GetValAddressBean>() {
                    @Override
                    public void onSuccess(GetValAddressBean versionInfo, Call call, Response response) {
                        dismissDialog();
                        Log.e("FFF344", "请求成功_1:" + versionInfo);
                        //请求成功
                        if (versionInfo != null) {


                            if (versionInfo.getCode() == 200) {
                                myCommissionAdapter.setValiAdress(versionInfo.getResult().getValidatorsAddress());
                            } else {
                                //未验证
                            }

                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF344", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
//

    }

    private void postData2(String gaussAdress) {
        OkGo.get(UrlConstant.baseUrlLian + "gpb/delegators")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("delegatorAddress", gaussAdress)
                .execute(new JsonCallback<DelegetaJBean>() {
                    @Override
                    public void onSuccess(DelegetaJBean validatorsBean, Call call, Response response) {
                        Log.e("FFF447", "validatorsBean:" + new Gson().toJson(validatorsBean));
                        //请求成功
                        if (validatorsBean != null) {
                            if (lists != null && lists.size() > 0) {
                                lists.clear();
                            }
                            lists.addAll(validatorsBean.getResult());
                            myCommissionAdapter.notifyDataSetChanged();

                            myCommissionAdapter.setData(lists);
                            if (lists != null && lists.size() > 0) {
                                if (lin_no_data != null) {
                                    lin_no_data.setVisibility(View.GONE);
                                }
                            } else {
                                if (lin_no_data != null) {
                                    lin_no_data.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (lin_no_data != null) {
                                lin_no_data.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        if (lin_no_data != null) {
                            lin_no_data.setVisibility(View.VISIBLE);
                        }
                        Log.e("FFF447", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                    }
                });
    }

    private void postData3(String gaussAdress) {
        OkGo.get(UrlConstant.baseUrlLian + "gpb/delegators")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("delegatorAddress", gaussAdress)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject validatorsBean, Call call, Response response) {
                        Log.e("FFF44788", "validatorsBean:" + new Gson().toJson(validatorsBean));
                        //请求成功
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        if (lin_no_data != null) {
                            lin_no_data.setVisibility(View.VISIBLE);
                        }
                        Log.e("FFF447", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                    }
                });
    }

    private void getSequece() {
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
//                map.put("address", unique.getMObjectList().get(0).getCoin_psd());
                    address = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "gauss/auth/v1beta1/accounts/" + address)
//                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
//                .headers("token", "")
//                .upJson(map.toString() + "")
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        //请求成功
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();
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


    private void postMoneryData() {
        HashMap<String, String> map = new HashMap<>();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
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
                Log.e("FFF444","responseText_e:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.e("FFF444","responseText:" + responseText);
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

    //查询验证人地址
    private void getRootInvitationCode() {
//        showDialog();
        OkGo.get(UrlConstant.baseCreateInportWallet + "system/setting/getRootInvitationCode")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<GetRootInvitationBean>() {
                    @Override
                    public void onSuccess(GetRootInvitationBean getRootInvitationBean, Call call, Response response) {

                        //请求成功
                        if (getRootInvitationBean != null && getRootInvitationBean.getData() != null) {

                            rootValiAddress = getRootInvitationBean.getData().getValidatoraddr();
                            rootAlletAddress = getRootInvitationBean.getData().getGaussaddr();
//                            postValidatorData(address);
                            postData2(gaussAdress);
                            postData3(gaussAdress);
                            myCommissionAdapter.setRootValiAdress(rootValiAddress);
                            getPledgeAmount();//质押数量
                            getShareAmount();//链接收益
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

    String tv_title3, shengyuGause;
    private int dialog_position;
    AddtionalAssetsShuhuiDialog addtionalAssetsShuhuiDialog;



    @Override
    public void setOnClickView() {//输入数量确认
        if (addtionalAssetsDialog != null) {
            addtionalAssetsDialog.dismiss();
        }

        if (addtionalAssetsShuhuiDialog != null) {
            tv_title3 = addtionalAssetsShuhuiDialog.tv_title2.getText().toString();


            if (dialog_position == 3) {
                if (TextUtils.isEmpty(tv_title3)) {
                    ToastUtil.showShort(getContext(), "请输入赎回数量");
                    return;
                }
                Log.e("FFF444", "canGaussMonery:" + canGaussMonery);
                if (!TextUtils.isEmpty(canGaussMonery)) {
                    if (Double.valueOf(canGaussMonery) < Double.valueOf(addtionalAssetsShuhuiDialog.tv_kgf2.getText().toString().replace("矿工费:", "").replace("GPB", ""))) {
                        ToastUtil.showShort(getContext(), "可用资产不足!");
                        Log.e("FFF444", "3333");
                        return;
                    }
                }else {
                    ToastUtil.showShort(getContext(), "可用资产不足!");
                    return;
                }
                Log.e("FFF555","Double.valueOf(shengyuGause):" + Double.valueOf(shengyuGause));
                Log.e("FFF555","Double.valueOf(tv_title3):" + Double.valueOf(tv_title3));
                if ((Double.valueOf(shengyuGause) - Double.valueOf(tv_title3)) < 0) {
                    ToastUtil.showShort(getContext(), "可赎回数量不足!");
                    return;
                }
                if (Double.valueOf(tv_title3)  < 0.000001)  {
                    ToastUtil.showShort(getContext(), "最小数量0.000001个");
                    return;
                }
//
//                if(Double.valueOf(shengyuGause) < 1.8){
//                    ToastUtil.showShort(getContext(), "可用资产不足!");
//                    return;
//                }else if((Double.valueOf(shengyuGause) - Double.valueOf(tv_title3)) < 0){
//                    ToastUtil.showShort(getContext(), "可用资产不足!");
//                    return;
//                }

            }
            addtionalAssetsShuhuiDialog.dismiss();

        }
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(getContext());
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }

    @Override
    public void setOnSafeClickView(String pass) {//身份安全确认
        String pass_sha = AESEncrypt.sha(pass);
        if (NetworkUtil.isConnected(getContext())) {
            if (DoubleClickUtil.isCommonClick()) {
//                for (int i = 0; i < userDao.loadAll().size(); i++) {
//                    if (userDao.loadAll().get(i).getPsd().equals(pass_sha)) {
//                        if (dialog_position == 1) {//提取收益
//                            getMonery();
//                        } else if (dialog_position == 2) {//提取佣金
//                            getMoneryTwo();
//                        } else if (dialog_position == 3) {//赎回本金
//
//                            getBackMonery();
//                        }
//                        break;
//                    } else {
//                        ToastUtils.showToast(getContext(), "请输入正确的安全密码");
//                        break;
//                    }
//                }
                String shapsd = unique.getPsd();
                if (pass_sha.equals(shapsd)) {
                    if (dialog_position == 1) {//提取收益
                        getMonery();
                    } else if (dialog_position == 2) {//提取佣金
                        getMoneryTwo();
                    } else if (dialog_position == 3) {//赎回本金

                        getBackMonery();
                    }
                } else {
                    ToastUtils.showToast(getContext(), "请输入正确的安全密码");
                    return;
                }
            }
        } else {
            ToastUtil.showShort(getContext(), getResources().getString(R.string.the_network_is_not_open));
        }
    }

    private void getMonery() {
        //valitorAdress   验证人地址
        String shapsd = unique.getPsd();
        String coin_psd = unique.getMObjectList().get(0).getCoin_psd();//委托人地址
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        GetTiquValitionBean getValitionBean = new GetTiquValitionBean();
        getValitionBean.setDelegator_address(gaussAdress);
        getValitionBean.setValidator_address(shouyiAddress);
        BigDecimal strFeeBig = new BigDecimal(addtionalAssetsDialog.tv_kgf2.getText().toString().replace("GPB", "").replace("矿工费:", "").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
        if (bytes != null) {
            PrivateKey privateKey = new PrivateKey(bytes);
            String sign = TransactionUtil.INSTANCE.gaussGetShouyiInSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    getValitionBean.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
            Log.e("FF88767", "sign:" + sign);
            OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                    .params("tx", sign)
                    .readTimeOut(10000)
                    .execute(new JsonCallback<BroadcastBean>() {
                        @Override
                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                            Log.e("FF88767", "jsonObject:" + jsonObject);
                            safeAdminDialog.dismiss();
                            if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                    ToastUtils.showToast(getContext(), "操作成功");
//                                    getSequece();
//                                    postDataLastDealAmount();
//                                    getRootInvitationCode();
//                                    postValAddress();
                                    getActivity().finish();
                                } else {
                                    ToastUtils.showToast(getContext(), "提取收益失败");
                                }
                            } else {
                                ToastUtils.showToast(getContext(), "提取收益失败!");
                            }
                        }

                        @Override
                        public void onFailure(String code, String message) {
                            super.onFailure(code, message);
                            safeAdminDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            safeAdminDialog.dismiss();
                        }
                    });

        }


    }

    private void getMoneryTwo() {
        //valitorAdress   验证人地址
        String shapsd = unique.getPsd();
        Log.e("FF88767", "shapsd:" + shapsd);
        String coin_psd = unique.getMObjectList().get(0).getCoin_psd();//委托人地址
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        BigDecimal gauss = new BigDecimal("1.8");
        BigDecimal gauss_num = gauss.multiply(new BigDecimal(10).pow(6)).divide(new BigDecimal("0.03"));
//        GetTiquValitionBean getValitionBean = new GetTiquValitionBean();
//        getValitionBean.setDelegator_address(gaussAdress);
//        getValitionBean.setValidator_address(shouyiAddress);
        Log.e("FF88767", "coin_psd:" + coin_psd);
        GetTiquValitionBean getValitionBean2 = new GetTiquValitionBean();
        getValitionBean2.setValidator_address(shouyiAddress);

        BigDecimal strFeeBig = new BigDecimal(addtionalAssetsDialog.tv_kgf2.getText().toString().replace("GPB", "").replace("矿工费:", "").trim());
        BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
        String resultFee = multiplyBifg.toString() + "";
        if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
            strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
        } else {
            strFee = multiplyBifg.toString() + "";
        }
        if (bytes != null) {
            PrivateKey privateKey = new PrivateKey(bytes);
            String sign = TransactionUtil.INSTANCE.gaussGetYongJInSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    getValitionBean2.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
            Log.e("FF88767", "sign:" + sign);
            OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                    .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                    .params("tx", sign)
                    .readTimeOut(10000)
                    .execute(new JsonCallback<BroadcastBean>() {
                        @Override
                        public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                            Log.e("FF88767", "jsonObject_提取佣金:" + jsonObject);
                            safeAdminDialog.dismiss();
                            if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                    ToastUtils.showToast(getContext(), "操作成功");
                                    DaoSession daoSession = MyApplication.getDaoSession();
                                    userDao = daoSession.getUserDao();
                                    try {
                                        unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
                                    } catch (Exception e) {
                                    }
                                    if (unique != null) {
                                        for (int i = 0; i < unique.getMObjectList().size(); i++) {
                                            if (unique.getMObjectList().get(i).getCoin_name().equals("GPB")) {
                                                gaussAdress = unique.getMObjectList().get(i).getCoin_psd();
                                            }
                                        }
                                    }
//                                    getSequece();
//                                    postDataLastDealAmount();
//                                    getRootInvitationCode();
//                                    postValAddress();
                                    getActivity().finish();
                                } else {
                                    ToastUtils.showToast(getContext(), "提取佣金失败");
                                }
                            } else {
                                ToastUtils.showToast(getContext(), "委托失败!");
                            }
                        }

                        @Override
                        public void onFailure(String code, String message) {
                            super.onFailure(code, message);
                            safeAdminDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            safeAdminDialog.dismiss();
                        }
                    });

        }


    }

    private void getBackMonery() {
        Log.e("FF88767", "赎回:" + gaussAdress + "---" + shouyiAddress);
        //valitorAdress   验证人地址
        String shapsd = unique.getPsd();
        String coin_psd = unique.getMObjectList().get(0).getCoin_psd();//委托人地址
        byte[] bytes1 = TransactionUtils.hexString2Bytes(shapsd);
        byte[] bytes = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes1, "AES/ECB/PKCS5Padding", null);
        GetValitionBean getValitionBean = new GetValitionBean();
        getValitionBean.setDelegator_address(gaussAdress);
        getValitionBean.setValidator_address(shouyiAddress);
        GetValitionBean.Amount amount = new GetValitionBean.Amount();
        amount.setDenom("ugpb");
//        amount.setAmount((Double.valueOf(tv_title3.trim()) * 1000000) + "");
//        amount.setAmount(tv_title3 + "");
        Log.e("FF88767", "tv_title3:" + tv_title3);


        String str = tv_title3;
        BigDecimal ugauss;
        if (!TextUtils.isEmpty(str)) {
//            if(str.contains(".")) {
//                 ugauss = new BigDecimal(str.substring(0, str.indexOf(".")));
//            }else {
            ugauss = new BigDecimal(str);
//            }
            BigDecimal ugauss_num = ugauss.multiply(new BigDecimal(10).pow(6));
            String result = ugauss_num.toString() + "";
            if (!TextUtils.isEmpty(result) && result.contains(".")) {
                amount.setAmount(ugauss_num.toString().substring(0, result.indexOf(".")) + "");
            } else {
                amount.setAmount(ugauss_num.toString() + "");
            }
//        amount.setAmount(Double.valueOf(ugauss_num.toString())*1000000 + "");
            BigDecimal strFeeBig = new BigDecimal(addtionalAssetsShuhuiDialog.tv_kgf2.getText().toString().replace("GPB", "").replace("矿工费:", "").trim());
            BigDecimal multiplyBifg = strFeeBig.multiply(new BigDecimal(10).pow(6));
            String resultFee = multiplyBifg.toString() + "";
            if (!TextUtils.isEmpty(resultFee) && resultFee.contains(".")) {
                strFee = multiplyBifg.toString().substring(0, resultFee.indexOf(".")) + "";
            } else {
                strFee = multiplyBifg.toString() + "";
            }
            getValitionBean.setAmount(amount);
            if (bytes != null) {
                PrivateKey privateKey = new PrivateKey(bytes);
                String sign = TransactionUtil.INSTANCE.gaussGetMoneryBackSigningTransaction(privateKey,
                        Long.valueOf(strFee), 200000,
                        getValitionBean.toString(),
                        Long.valueOf(sequence1),
                        Long.valueOf(account_num));
                Log.e("FF88767", "sign:" + sign);
                OkGo.post(UrlConstant.baseUrlLian + "gpb/broadcastStdTx")
                        .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                        .params("tx", sign)
                        .readTimeOut(10000)
                        .execute(new JsonCallback<BroadcastBean>() {
                            @Override
                            public void onSuccess(BroadcastBean jsonObject, Call call, Response response) {
                                Log.e("FF88767", "jsonObject:" + jsonObject + "--" + new Gson().toJson(response.body()));
                                safeAdminDialog.dismiss();
                                if (safeAdminDialog != null) {
                                    safeAdminDialog = null;
                                }
                                if (addtionalAssetsShuhuiDialog != null) {
                                    addtionalAssetsShuhuiDialog = null;
                                }
                                if (jsonObject.getCode() == 200 && jsonObject.getResult() != null) {
                                    if (jsonObject.getResult().getRaw_log() != null && jsonObject.getResult().getRaw_log().equals("[]") && !TextUtils.isEmpty(jsonObject.getResult().getTxhash())) {
                                        ToastUtils.showToast(getContext(), "操作成功");
                                        getSequece();
//                                        postDataLastDealAmount();
//                                        getRootInvitationCode();
//                                        postValAddress();
                                        getActivity().finish();
                                    } else {
                                        ToastUtils.showToast(getContext(), "赎回失败!");
                                    }
                                } else {
                                    ToastUtils.showToast(getContext(), "赎回失败!");
                                }
                            }

                            @Override
                            public void onFailure(String code, String message) {
                                super.onFailure(code, message);
                                safeAdminDialog.dismiss();
                                if (safeAdminDialog != null) {
                                    safeAdminDialog = null;
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                safeAdminDialog.dismiss();
                            }
                        });

            }

        }
    }

    @Override
    public void setKuangGongOnClickView() {
        Intent intent = new Intent(getContext(), MinerFeeActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {

            if (requestCode == 1) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
                addtionalAssetsDialog.tv_kgf2.setText("矿工费:" + df.format(Double.valueOf(data.getStringExtra("aaa"))) + " GPB");
            }
            if (requestCode == 2) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
                addtionalAssetsShuhuiDialog.tv_kgf2.setText("矿工费:" + df.format(Double.valueOf(data.getStringExtra("aaa"))) + " GPB");
            }
        }
    }

    @Override
    public void setKuangGongOneOnClickView() {
        Intent intent = new Intent(getContext(), MinerFeeActivity.class);
        startActivityForResult(intent, 1);
    }

    //提取收益-提取佣金  取消-清空数据
    @Override
    public void setCancelOnClickView() {
        addtionalAssetsDialog.tv_kgf2.setText("矿工费:" + 0.006 + " GPB");
    }

    //赎回  取消-清空数据
    @Override
    public void setShuHuiCancelOnClickView() {
        addtionalAssetsShuhuiDialog.tv_kgf2.setText("矿工费:" + 0.006 + " GPB");
    }
}
