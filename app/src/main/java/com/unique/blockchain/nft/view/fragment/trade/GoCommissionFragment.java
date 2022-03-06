package com.unique.blockchain.nft.view.fragment.trade;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.net.utils.DoubleClickUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.node.AllMoneyBean;
import com.unique.blockchain.nft.domain.node.DelegationsBean;
import com.unique.blockchain.nft.domain.node.GetRootInvitationBean;
import com.unique.blockchain.nft.domain.node.GetValiAddressBean;
import com.unique.blockchain.nft.domain.node.UpbandDataBean;
import com.unique.blockchain.nft.domain.node.ValidatorInfoListBean;
import com.unique.blockchain.nft.infrastructure.other.BigDecimalNum;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.trade.GoCommissionDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 去委托
 */
public class GoCommissionFragment extends BaseFragment {

    private String rootAddress;
    TextView has_compler;
    TextView tv_year_rate;
    private GetValiAddressBean info;
    TextView tv_num;
    TextView tv_num2;
    SmartRefreshLayout smr;
    String yearRate,weituoOperatorAdress;
    private String peopleNum, upbondData, allMonery, rootAlletAddress;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_go_commission;
    }

    @Override
    public void initView() {


        has_compler = (TextView) findViewById(R.id.has_compler_commit);
        tv_year_rate = (TextView) findViewById(R.id.tv_year_rate_com);
        tv_num = (TextView) findViewById(R.id.tv_num_com);
        tv_num2 = (TextView) findViewById(R.id.tv_num2_com);
        smr = (SmartRefreshLayout) findViewById(R.id.smr_com);
        getRootInvitationCode();
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                getRootInvitationCode();
            }
        });
    }

    //获取根节点验证人地址
    private void getRootInvitationCode() {
//        showDialog();
        OkGo.get(UrlConstant.baseCreateInportWallet + "system/setting/getRootInvitationCode")
                .execute(new JsonCallback<GetRootInvitationBean>() {
                    @Override
                    public void onSuccess(GetRootInvitationBean getRootInvitationBean, Call call, Response response) {
                        Log.e("FFF344", "查询验证人:" + getRootInvitationBean);
                        if(smr != null) {
                            smr.finishRefresh();
                        }
                        //请求成功
                        if (getRootInvitationBean != null && getRootInvitationBean.getData() != null) {

                            rootAddress = getRootInvitationBean.getData().getValidatoraddr();
                            rootAlletAddress = getRootInvitationBean.getData().getGaussaddr();
                            postData(rootAddress);
                            postTwoData(rootAddress);
                            postAllPPeople(rootAddress);
                            postAllMonery(rootAddress);
                            postUpbond(rootAddress);
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        smr.finishRefresh();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                        smr.finishRefresh();
                    }
                });

    }


    //解绑中、赎回中
    private void postUpbond(String address) {
        Log.e("FFF344", "url:" + UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators/" + address + "/unbonding_delegations");
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators/" + address + "/unbonding_delegations")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<UpbandDataBean>() {
                    @Override
                    public void onSuccess(UpbandDataBean jsonObject, Call call, Response response) {
                        dismissDialog();
                        Log.e("FF98777", "解绑中:" + jsonObject);
                        if (jsonObject != null && jsonObject.getPagination() != null) {
                            upbondData = jsonObject.getPagination().getTotal();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
//                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        dismissDialog();
                    }
                });


    }

    //查询总收益
    private void postAllMonery(String address) {

        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/distribution/v1beta1/validators/" + address + "/outstanding_rewards")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<AllMoneyBean>() {
                    @Override
                    public void onSuccess(AllMoneyBean jsonObject, Call call, Response response) {
                        dismissDialog();
                        if (jsonObject != null && jsonObject.getRewards() != null && jsonObject.getRewards().getRewards() != null && jsonObject.getRewards().getRewards().size() > 0) {
                            Log.e("FF98777", "总收益:" + jsonObject);
                            allMonery = jsonObject.getRewards().getRewards().get(0).getAmount() + "";
                        }

                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
//                        dismissDialog();
                        Log.e("FF98777", "总收益_onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        dismissDialog();
                        Log.e("FF98777", "总收益_onError:" + e);
                    }
                });


    }

    //查询总人数
    private void postAllPPeople(String address) {
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators/" + address + "/delegations")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .connTimeOut(10000)
                .readTimeOut(10000)
                .execute(new JsonCallback<DelegationsBean>() {
                    @Override
                    public void onSuccess(DelegationsBean jsonObject, Call call, Response response) {
//                        dismissDialog();
                        if (jsonObject != null && jsonObject.getPagination() != null && !TextUtils.isEmpty(jsonObject.getPagination().getTotal())) {
                            tv_num2.setText(jsonObject.getPagination().getTotal());
                            peopleNum = jsonObject.getPagination().getTotal();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
//                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        dismissDialog();
                    }
                });


    }

    private void postTwoData(String address) {
        OkGo.get(UrlConstant.baseUrlLian + "gpb/validatorsinfoList")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .execute(new JsonCallback<ValidatorInfoListBean>() {
                    @Override
                    public void onSuccess(ValidatorInfoListBean validatorInfoListBean, Call call, Response response) {
                        dismissDialog();
                        //请求成功
                        if (validatorInfoListBean != null && validatorInfoListBean.getResult() != null && validatorInfoListBean.getResult().getValidators() != null && validatorInfoListBean.getResult().getValidators().size() > 0) {
                            for (int i = 0; i < validatorInfoListBean.getResult().getValidators().size(); i++) {
                                if (address.equals(validatorInfoListBean.getResult().getValidators().get(i).getOperatorAddress())) {
                                    if(!TextUtils.isEmpty(validatorInfoListBean.getResult().getValidators().get(i).getMoniker())) {
                                        has_compler.setText(validatorInfoListBean.getResult().getValidators().get(i).getMoniker() + "");
                                    }
                                    if(!TextUtils.isEmpty(validatorInfoListBean.getResult().getValidators().get(i).getYearRate())) {
                                        yearRate = BigDecimalNum.setDouble(Double.valueOf(validatorInfoListBean.getResult().getValidators().get(i).getYearRate()) + "");
                                    }
                                    if(!TextUtils.isEmpty(validatorInfoListBean.getResult().getValidators().get(i).getYearRate())) {
                                        BigDecimal bd = new BigDecimal(BigDecimalNum.setDouble(Double.valueOf(validatorInfoListBean.getResult().getValidators().get(i).getYearRate()) + ""));
                                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

                                        tv_year_rate.setText("≈ " + bd.toString() + "%");
                                    }
                                    if(!TextUtils.isEmpty(validatorInfoListBean.getResult().getValidators().get(i).getPledge())) {
                                        tv_num.setText(BigDecimalNum.setDouble((Double.valueOf(validatorInfoListBean.getResult().getValidators().get(i).getPledge()) / 1000000) + "") + " GPB" + "/");
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        Log.e("FFF344r", "查询info_失败:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                        Log.e("FFF344r", "查询info_onError:" + e);
                    }
                });

    }

    //获取验证人地址
    private void postData(String address) {
        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators")
                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
                .params("address", address)
                .readTimeOut(10000)
                .execute(new JsonCallback<GetValiAddressBean>() {
                    @Override
                    public void onSuccess(GetValiAddressBean getValiAddressBean, Call call, Response response) {
                        dismissDialog();
                        info = getValiAddressBean;
                        //请求成功
                        if (getValiAddressBean != null && getValiAddressBean.getValidators() != null && getValiAddressBean.getValidators().size() > 0) {

                            Log.e("FF88767", "查询info:" + getValiAddressBean);
                            Log.e("FF88767", "address:" + address);
                            for (int i = 0; i < getValiAddressBean.getValidators().size(); i++) {
                                Log.e("FF88767", "getOperator_address:" + getValiAddressBean.getValidators().get(i).getOperator_address());
                                if (address.equals(getValiAddressBean.getValidators().get(i).getOperator_address())) {
                                  weituoOperatorAdress =   getValiAddressBean.getValidators().get(i).getOperator_address();
                                    has_compler.setText(getValiAddressBean.getValidators().get(i).getDescription().getMoniker());
//                                    tv_year_rate.setText("≈ " + (Double.valueOf(info.getValidators().get(i).getCommission_reallocation().getReserve_rate())) * 100 + "%");
                                    tv_num.setText(BigDecimalNum.setDouble((Double.valueOf(getValiAddressBean.getValidators().get(i).getTokens()) / 1000000) + "") + " GPB" + "/");
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        Log.e("FFF344", "查询info_失败:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                        Log.e("FFF344", "查询info_onError:" + e);
                    }
                });

    }

    @OnClick({R.id.lin_get_valiadd})
    public void setOnclick(View view) {
        if (DoubleClickUtil.isCommonClick()) {
            switch (view.getId()) {
                case R.id.lin_get_valiadd:



                    Intent intent = new Intent(getContext(), GoCommissionDetailActivity.class);
                    intent.putExtra("info", info + "");
                    intent.putExtra("peopleNum", peopleNum);
                    intent.putExtra("upbondData", upbondData);
                    intent.putExtra("allMonery", allMonery);
                    intent.putExtra("rootAddress", rootAddress);//根验证人地址
                    intent.putExtra("rootAlletAddress", rootAlletAddress);
                    intent.putExtra("yearRate", yearRate);
                    if(!TextUtils.isEmpty(weituoOperatorAdress) && !TextUtils.isEmpty(rootAddress)){
                        if(weituoOperatorAdress.equals(rootAddress)){
                            intent.putExtra("isRoot", "true");
                        }else {
                            intent.putExtra("isRoot", "false");
                        }
                    }
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getRootInvitationCode();
    }
}
