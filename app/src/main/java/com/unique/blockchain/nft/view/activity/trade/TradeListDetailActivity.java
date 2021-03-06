package com.unique.blockchain.nft.view.activity.trade;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.EncryptUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.unique.blockchain.nft.adapter.TradeListPowerAdapter;
import com.unique.blockchain.nft.constant.Constants;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.node.GaussKeysBean;
import com.unique.blockchain.nft.domain.node.HomeAsstesBean;
import com.unique.blockchain.nft.domain.trade.GoTradeCationBean;
import com.unique.blockchain.nft.domain.trade.GoTradeDetailBean;
import com.unique.blockchain.nft.domain.trade.NftSalesBean;
import com.unique.blockchain.nft.domain.trade.NftSalesTwoBean;
import com.unique.blockchain.nft.domain.trade.TradeDetailPriceBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.ShuzhiRegisterSuccessDialog;
import com.unique.blockchain.nft.infrastructure.dialog.XiaoShouSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtilNew;
import com.unique.blockchain.nft.infrastructure.utils.TransactionUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.ProductImageActivity;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeCationPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeDetailPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeTranscationGuDingPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.ITradeTranscationPresenter;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeCationPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeDetailPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeTranscationGuDingPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.presenter.impl.ITradeTranscationPresenterImpl;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeCationCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeListDetailCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeTransactionCallbask;
import com.unique.blockchain.nft.view.fragment.trade.view.ITradeTransactionGuDingCallbask;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import wallet.core.jni.PrivateKey;


/**
 * ???????????????
 */
public class TradeListDetailActivity extends BaseActivity implements ITradeListDetailCallbask, ITradeCationCallbask, ITradeTransactionCallbask, ITradeTransactionGuDingCallbask, SafeAdminDialog.OnSafeClickView
        , XiaoShouSuccessDialog.OnBackZhujiClickView {
    static {
        System.loadLibrary("TrustWalletCore");
    }

    private String TAG = "TradeListDetailActivity";

    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_paimai)
    TextView tv_paimai;
    @BindView(R.id.lin_price)
    LinearLayout lin_price;
    @BindView(R.id.lin_paimai)
    LinearLayout lin_paimai;
    @BindView(R.id.recyclewTwo)
    RecyclerView recyclewTwo;
    private List<String> list2 = new ArrayList<>();
    private TradeListPowerAdapter tradeListPowerAdapter = new TradeListPowerAdapter(this, R.layout.fragment_trade_power_item, list2);
    private String productType, productName, nftId;
    private GoTradeDetailBean gotradebaen;
    private String goTradeStr;
    @BindView(R.id.tv_id_data)
    TextView tv_id_data;
    @BindView(R.id.tv_liutongzhong)
    TextView tv_liutongzhong;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_product_detail)
    TextView tv_product_detail;
    @BindView(R.id.tv_product_type)
    TextView tv_product_type;
    @BindView(R.id.tv_zhiya_jin)
    TextView tv_zhiya_jin;
    @BindView(R.id.tv_royalties)
    TextView tv_royalties;
    @BindView(R.id.tv_vat)
    TextView tv_vat;
    @BindView(R.id.img_dail_top)
    ImageView img_dail_top;
    private ITradeDetailPresenter iTradeDetailPresenter;
    private GoTradeDetailBean mgoTradeDetailBean;
    List<String> proveUrls = new ArrayList<>();
    private ITradeCationPresenter iTradeCationPresenter;
    @BindView(R.id.img_start_time)
    ImageView img_start_time;
    @BindView(R.id.img_end_time)
    ImageView img_end_time;
    @BindView(R.id.et_pai_start_time)
    EditText et_pai_start_time;
    String st_pai_start_time;
    @BindView(R.id.img_pai_start_time)
    ImageView img_pai_start_time;
    @BindView(R.id.et_pai_end_time_end)
    EditText et_pai_end_time_end;
    String st_pai_end_time_end;
    @BindView(R.id.img_pai_end_time_end)
    ImageView img_pai_end_time_end;
    @BindView(R.id.tv_nft_go)
    TextView tv_nft_go;
    private TimePickerView pvTime, pvTimeEnd, pvPayTime, pvPayTimeEnd;
    private long pvTimeLong, pvTimeEndLong, pvPayTimeLong, pvPayTimeEndLong;
    private String userAddress, strFee;
    User unique;
    private UserDao userDao;
    DaoSession daoSession = MyApplication.getDaoSession();
    //??????
    private String sequence1;
    private String account_num;
    private int tabTag;
    @BindView(R.id.et_min_paimai_price)
    EditText et_min_paimai_price;
    @BindView(R.id.et_hour)
    EditText et_hour;
    @BindView(R.id.et_minute)
    EditText et_minute;
    @BindView(R.id.et_seconds)
    EditText et_seconds;
    @BindView(R.id.et_paimai_price_jiange)
    EditText et_paimai_price_jiange;
    ITradeTranscationPresenter iTradeTranscationPresenter;
    ITradeTranscationGuDingPresenter iTradeTranscationGuDingPresenter;
    private boolean isSelect;
    @BindView(R.id.img_is_goumai)
    ImageView img_is_goumai;
    @BindView(R.id.tv_is_goumai)
    TextView tv_is_goumai;
    private boolean isSelectGou;
    @BindView(R.id.img_time_gou)
    ImageView img_time_gou;
    @BindView(R.id.et_min_price_guding)
    EditText et_min_price_guding;
    @BindView(R.id.et_max_price_guding)
    EditText et_max_price_guding;
    @BindView(R.id.et_start_time)
    EditText et_start_time_guding;
    String st_start_time_guding;
    @BindView(R.id.et_end_time_guding)
    EditText et_end_time_guding;
    String st_end_time_guding;
    private boolean isSelectTimeGou;
    SafeAdminDialog safeAdminDialog;
    @BindView(R.id.tv_nft_hetong)
    TextView tv_nft_hetong;
    @BindView(R.id.tv_time_gou)
    TextView tv_time_gou;
    private String orderId,heUrl;
    private String sign, balance, time;
    private XiaoShouSuccessDialog xiaoShouSuccessDialog;
    @BindView(R.id.tv_start_price_end)
    TextView tv_start_price_end;
    @BindView(R.id.lin_edit_min_price)
    LinearLayout lin_edit_min_price;
    private BigDecimal timaBig;
    BigDecimal hout;
    BigDecimal minute;
    BigDecimal seconds;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tradefragment_detail;
    }

    @Override
    public void initUI() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= 21) {//??????????????????
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
        productType = getIntent().getStringExtra("productType");
        productName = getIntent().getStringExtra("productName");
        nftId = getIntent().getStringExtra("nftId");
        orderId = getIntent().getStringExtra("orderId");
//        uniqueAdress = getIntent().getStringExtra("uniqueAdress");

        gotradebaen = new Gson().fromJson(goTradeStr, GoTradeDetailBean.class);
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    userAddress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        tv_start_price_end.setText("????????????<????????????????????????????????????????????????;");
        if (productType != null) {

        }
        showTimeDialog();
        showPayTimeDialog();
        showTimeDialogEnd();
        showPayTimeDialogEnd();

//        CommonUtil.setStatusBarTextColor(this, 1);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        recyclewTwo.setLayoutManager(gridLayoutManager);
        recyclewTwo.setAdapter(tradeListPowerAdapter);
        tradeListPowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Log.e("FFEEE","URL:" + proveUrls.get(i));

                Intent intent3 = new Intent(TradeListDetailActivity.this, ProductImageActivity.class);
                intent3.putExtra("url", proveUrls.get(i) + "");
                startActivity(intent3);
            }
        });
        et_paimai_price_jiange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    if (s.toString().substring(0, 1).equals(".")) {
                        et_paimai_price_jiange.setText("0.");
                        et_paimai_price_jiange.setSelection(et_paimai_price_jiange.getText().length());//???????????????????????????
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getMonery();

        et_min_price_guding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    if(s.toString().equals("00")){
                        s = "0.";
                        et_min_price_guding.setText(s);
                    }else if(s.toString().equals(".")){
                        s = "0.";
                        et_min_price_guding.setText(s);
                    }else if(s.toString().equals("0")){
                        et_min_price_guding.setText("");
                        ToastUtil.showShort(TradeListDetailActivity.this,"?????????????????????0");
                        return;
                    }
                    et_min_price_guding.setSelection(et_min_price_guding.getText().length());//???????????????????????????
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeNumber(s,et_min_price_guding);
            }
        });
        et_max_price_guding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    if(s.toString().equals("00")){
                        s = "0.";
                        et_max_price_guding.setText(s);
                    }else if(s.toString().equals(".")){
                        s = "0.";
                        et_max_price_guding.setText(s);
                    }else if(s.toString().equals("0")){
                        et_max_price_guding.setText("");
                        ToastUtil.showShort(TradeListDetailActivity.this,"?????????????????????0");
                        return;
                    }
                    et_max_price_guding.setSelection(et_max_price_guding.getText().length());//???????????????????????????
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeNumber(s,et_max_price_guding);
            }
        });
        et_min_paimai_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    if(s.toString().equals("00")){
                        s = "0.";
                        et_min_paimai_price.setText(s);
                    }else if(s.toString().equals(".")){
                        s = "0.";
                        et_min_paimai_price.setText(s);
                    }else if(s.toString().equals("0")){
                        et_min_paimai_price.setText("");
                        ToastUtil.showShort(TradeListDetailActivity.this,"???????????????????????????0");
                        return;
                    }
                    et_min_paimai_price.setSelection(et_min_paimai_price.getText().length());//???????????????????????????
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                judgeNumber(editable,et_min_paimai_price);
            }
        });

        et_paimai_price_jiange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                judgeNumber(editable,et_paimai_price_jiange);
            }
        });

        et_max_price_guding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                judgeNumber(editable,et_max_price_guding);
            }
        });

        et_min_price_guding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                judgeNumber(editable,et_min_price_guding);
            }
        });
    }

    public static void judgeNumber(Editable edt,EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//???????????????????????????????????????????????????????????????
        int index = editText.getSelectionStart();//??????????????????
        //  if (posDot == 0) {//?????????????????????????????????????????????
        //  edt.delete(0, temp.length());//??????????????????
        //  return;
        //  }
//        if (posDot < 0) {//??????????????????
//            if (temp.length() <= 5) {
//                return;//???????????????????????????
//            } else {
//                edt.delete(index-1, index);//????????????????????????
//                return;
//            }
//        }
//        if (posDot > 5) {//??????????????????5??????????????????????????????
//            edt.delete(index-1, index);//????????????????????????
//            return;
//        }
        if(temp.contains(".")) {
            if (temp.length() - posDot > 6)//?????????????????????
            {
                edt.delete(posDot + 7, temp.length());//????????????????????????
                return;
            }
        }
    }

    private void getMonery() {
        OkGo.get(UrlConstant.baseUrlLian + "unique/getBalanceAll")
                .params("account", userAddress)
                .headers("projectId", Constants.UNIQUE_HEADRE)
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject, Call call, Response response) {
                        HomeAsstesBean homeAsstesBean = new Gson().fromJson(jsonObject.toString(), HomeAsstesBean.class);
                        if (homeAsstesBean != null && homeAsstesBean.getCode() != null && homeAsstesBean.getResult() != null) {
                            if (homeAsstesBean.getCode().equals("200") && homeAsstesBean.getResult().getBalances().size() > 0) {

                                for (int i = 0; i < homeAsstesBean.getResult().getBalances().size(); i++) {
                                    if (homeAsstesBean.getResult().getBalances().get(i).getDenom().contains("uunique")) {
                                        Log.e("FF88767", "??????:" + homeAsstesBean.getResult().getBalances().get(i).getAmount());
                                        balance = homeAsstesBean.getResult().getBalances().get(i).getAmount();
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

    private void getAccount() {
        showDialog();
        OkGo.get(UrlConstant.baseGetValiAddUrl2 + "unique/auth/v1beta1/accounts/" + userAddress)
                .execute(new JsonCallback<GaussKeysBean>() {
                    @Override
                    public void onSuccess(GaussKeysBean gaussKeysBean, Call call, Response response) {
                        dismissDialog();
                        //????????????
                        if (gaussKeysBean.getAccount() != null) {
                            sequence1 = gaussKeysBean.getAccount().getSequence();
                            account_num = gaussKeysBean.getAccount().getAccount_number();
                            go_nft();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                        Log.e("FF88767", "onFailure:" + code + message);
                        ToastUtil.showShort(TradeListDetailActivity.this,"???????????????????????????!");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF88767", "e:" + e);
                        dismissDialog();
                    }
                });
    }

    @Override
    public void initData() {
        iTradeDetailPresenter = new ITradeDetailPresenterImpl();
        iTradeDetailPresenter.registerViewCallback(this);
        if(TextUtils.isEmpty(orderId) || orderId.equals("null") ||orderId.equals("0") ){
            iTradeDetailPresenter.getData(1,"", nftId, userAddress);
        }else {
            iTradeDetailPresenter.getData(1,orderId, nftId, userAddress);
        }


        iTradeCationPresenter = new ITradeCationPresenterImpl();
        iTradeCationPresenter.registerViewCallback(this);
        iTradeCationPresenter.getData(1, userAddress);

        //???????????????
        isSelectGou = true;
        img_is_goumai.setSelected(true);
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.tv_price, R.id.tv_paimai, R.id.img_back, R.id.img_start_time, R.id.img_end_time, R.id.img_pai_start_time, R.id.img_pai_end_time_end, R.id.tv_nft_go
            , R.id.img_is_goumai, R.id.img_time_gou, R.id.tv_nft_hetong, R.id.tv_is_goumai, R.id.tv_time_gou})
    public void setOnclick(View view) {
        if (MoreClick.MoreClicks()) {
            switch (view.getId()) {
                case R.id.img_back:
                    finish();
                    break;
                case R.id.tv_price://??????
                    tv_price.setBackground(getResources().getDrawable(R.drawable.dialog_time_choose_bg));
                    tv_paimai.setBackground(getResources().getDrawable(R.drawable.dialog_time_bg));

                    tv_price.setTextColor(Color.WHITE);
                    tv_paimai.setTextColor(Color.GRAY);
                    lin_price.setVisibility(View.VISIBLE);
                    lin_paimai.setVisibility(View.GONE);
                    tabTag = 0;
                    break;
                case R.id.tv_paimai://??????
                    tv_price.setBackground(getResources().getDrawable(R.drawable.dialog_time_bg));
                    tv_paimai.setBackground(getResources().getDrawable(R.drawable.dialog_time_choose_bg));

                    tv_price.setTextColor(Color.GRAY);
                    tv_paimai.setTextColor(Color.WHITE);
                    lin_price.setVisibility(View.GONE);
                    lin_paimai.setVisibility(View.VISIBLE);
                    tabTag = 1;
                    break;
                case R.id.img_start_time://??????????????????

                    if (pvTime != null) {
                        pvTime.show(view);//??????????????????????????????????????????????????????????????????????????????view
                    }
                    break;
                case R.id.img_end_time://??????????????????
                    if (pvTimeEnd != null) {
                        pvTimeEnd.show(view);//??????????????????????????????????????????????????????????????????????????????view
                    }
                    break;
                case R.id.img_pai_start_time://??????????????????
                    if (pvPayTime != null) {
                        pvPayTime.show(view);
                    }
                    break;
                case R.id.img_pai_end_time_end://??????????????????
                    if (pvPayTimeEnd != null) {
                        pvPayTimeEnd.show(view);
                    }
                    break;
                case R.id.tv_nft_go://nft??????
                    getAccount();

                    break;
//            case R.id.iv_select://???????????????????????????nft
//                isSelect = !isSelect;
//                if (isSelect) {
//                    iv_select.setSelected(true);
//                } else {
//                    iv_select.setSelected(false);
//                }
//                break;
                case R.id.img_is_goumai://????????????????????????????????????
                case R.id.tv_is_goumai:
                    isSelectGou = !isSelectGou;
                    if (isSelectGou) {
                        img_is_goumai.setSelected(true);
                    } else {
                        img_is_goumai.setSelected(false);
                    }
                    break;
                case R.id.img_time_gou://???????????????????????????nft
                case R.id.tv_time_gou:
                    isSelectTimeGou = !isSelectTimeGou;
                    if (isSelectTimeGou) {
                        img_time_gou.setSelected(true);
                    } else {
                        img_time_gou.setSelected(false);
                    }
                    break;
                case R.id.tv_nft_hetong://????????????
                    Intent intent = new Intent(TradeListDetailActivity.this, AdWebActivity.class);
//                    intent.putExtra("banner_url", heUrl + "http://192.168.2.13:8017/setting/contract-sell");
                    intent.putExtra("banner_url", UrlConstant.baseWap + "setting/contract-sell");
                    startActivity(intent);
                    break;
            }
        }
    }

    private void go_nft() {


        if (tabTag == 0) {
            NftSalesBean nftSalesBean = new NftSalesBean();
            nftSalesBean.setSender(userAddress);
            nftSalesBean.setToken_id(nftId);


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
            NftSalesBean.StartPrice price = new NftSalesBean.StartPrice();
            price.setDenom("uunique");
            nftSalesBean.setPool_address(Constants.UNIQUE_ADDRESS);//?????????????????????


            if (!isSelectGou) {
                ToastUtil.showShort(TradeListDetailActivity.this, "???????????????????????????????????????");
                return;
            }
            if (TextUtils.isEmpty(et_min_price_guding.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
            if (!TextUtils.isEmpty(et_min_price_guding.getText()) && Double.valueOf(et_min_price_guding.getText().toString()) == 0) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????0");
                return;
            }
            if (TextUtils.isEmpty(et_max_price_guding.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
            if (!TextUtils.isEmpty(et_max_price_guding.getText()) && Double.valueOf(et_max_price_guding.getText().toString()) == 0) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????0");
                return;
            }
            if (TextUtils.isEmpty(et_start_time_guding.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
            if (TextUtils.isEmpty(et_end_time_guding.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
//            if (Double.valueOf(et_min_price_guding.getText().toString()) > Double.valueOf(et_max_price_guding.getText().toString())) {
//                ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????????????????");
//                return;
//            }
            if (TextUtils.isEmpty(balance)) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????!");
                return;
            } else {
                if (Double.valueOf(balance) < 0.009) {
                    ToastUtil.showShort(TradeListDetailActivity.this, "????????????!");
                    return;
                }
            }
            if (pvTimeLong >= pvTimeEndLong) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????????????????????????????");
                return;
            }
            BigDecimal bigDecimal = new BigDecimal(et_min_price_guding.getText().toString());
            BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal(10).pow(6));
            String bss = bigDecimal1.toString() + "";
            if (!TextUtils.isEmpty(bss) && bss.contains(".")) {
                bss = bss.substring(0, bss.indexOf(".")) + "";
            } else {
                bss = bss + "";
            }
            price.setAmount(bss);
            nftSalesBean.setStart_price(price);

            BigDecimal bigDecimalEnd = new BigDecimal(et_max_price_guding.getText().toString());
            BigDecimal bigDecimal1End = bigDecimalEnd.multiply(new BigDecimal(10).pow(6));
            String bssEnd = bigDecimal1End.toString() + "";
            if (!TextUtils.isEmpty(bssEnd) && bssEnd.contains(".")) {
                bssEnd = bssEnd.substring(0, bssEnd.indexOf(".")) + "";
            } else {
                bssEnd = bssEnd + "";
            }
            NftSalesBean.EndPrice endPrice = new NftSalesBean.EndPrice();
            endPrice.setDenom("uunique");
            endPrice.setAmount(bssEnd);
            nftSalesBean.setStart_price(price);
            nftSalesBean.setEnd_price(endPrice);
            nftSalesBean.setStart_time(st_start_time_guding);
            nftSalesBean.setEnd_time(st_end_time_guding);

            sign = TransactionUtilNew.INSTANCE.uniqueGudingPriceSigningTransaction(privateKey,
                    Long.valueOf(strFee), 200000,
                    nftSalesBean.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
            Log.e("FFF44455", "SIGN0:" + sign);
        } else if (tabTag == 1) {
            NftSalesTwoBean nftSalesTwoBean = new NftSalesTwoBean();
            nftSalesTwoBean.setSender(userAddress);
            nftSalesTwoBean.setToken_id(nftId);
            nftSalesTwoBean.setStart_time(st_pai_start_time);
            nftSalesTwoBean.setEnd_time(st_pai_end_time_end);
            nftSalesTwoBean.setPool_address(Constants.UNIQUE_ADDRESS);//?????????????????????

            NftSalesTwoBean.Price price2 = new NftSalesTwoBean.Price();

            String shapsd2 = unique.getPsd();
            byte[] bytes12 = TransactionUtils.hexString2Bytes(shapsd2);
            byte[] bytes2 = EncryptUtils.decryptAES(unique.getMObjectList().get(0).getPsd_psd(), bytes12, "AES/ECB/PKCS5Padding", null);
            PrivateKey privateKey2 = new PrivateKey(bytes2);

            BigDecimal strFeeBig2 = new BigDecimal("0.009");
            BigDecimal multiplyBifg2 = strFeeBig2.multiply(new BigDecimal(10).pow(6));
            String resultFee2 = multiplyBifg2.toString() + "";
            if (!TextUtils.isEmpty(resultFee2) && resultFee2.contains(".")) {
                strFee = multiplyBifg2.toString().substring(0, resultFee2.indexOf(".")) + "";
            } else {
                strFee = multiplyBifg2.toString() + "";
            }
            price2.setDenom("uunique");


            if (!isSelectGou) {
                ToastUtil.showShort(TradeListDetailActivity.this, "???????????????????????????????????????");
                return;
            }

            if (TextUtils.isEmpty(et_min_paimai_price.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
            if (!TextUtils.isEmpty(et_min_paimai_price.getText()) && Double.valueOf(et_min_paimai_price.getText().toString()) == 0) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????0");
                return;
            }
            if (TextUtils.isEmpty(et_pai_start_time.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
            if (TextUtils.isEmpty(et_pai_end_time_end.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "?????????????????????");
                return;
            }
//            if (!TextUtils.isEmpty(et_hour.getText()) && !TextUtils.isEmpty(et_minute.getText()) && !TextUtils.isEmpty(et_seconds.getText())) {
//                time = et_hour.getText().toString() + "," + et_minute.getText().toString() + "," + et_seconds.getText().toString();
//            } else {
//                time = "0,0,0";
//            }
            if(TextUtils.isEmpty(et_hour.getText()) || !TextUtils.isEmpty(et_hour.getText()) && Double.valueOf(et_hour.getText().toString()) == 0){//???????????????0
                if(TextUtils.isEmpty(et_minute.getText())){
                    ToastUtil.showShort(TradeListDetailActivity.this, "???????????????????????????10??????");
                    return;
                }else if(!TextUtils.isEmpty(et_minute.getText()) && Double.valueOf(et_minute.getText().toString()) < 10){
                    ToastUtil.showShort(TradeListDetailActivity.this, "???????????????????????????10??????");
                    return;
                }
                if(TextUtils.isEmpty(et_seconds.getText())){
                    et_seconds.setText("0");
                }
                time = "0" + "," + et_minute.getText().toString() + "," + et_seconds.getText().toString();
            }else {//????????????
                if(TextUtils.isEmpty(et_minute.getText())){
                    et_minute.setText("0");
                }
                if(TextUtils.isEmpty(et_seconds.getText())){
                    et_seconds.setText("0");
                }
                time = et_hour.getText().toString() + "," + et_minute.getText().toString() + "," + et_seconds.getText().toString();
            }
            if(!TextUtils.isEmpty(et_hour.getText().toString())) {
                hout = new BigDecimal(et_hour.getText().toString()).multiply(new BigDecimal("3600")).multiply(new BigDecimal("1000000000"));
            }else {
                hout = new BigDecimal("0");
            }
            if(!TextUtils.isEmpty(et_minute.getText().toString())) {
                minute = new BigDecimal(et_minute.getText().toString()).multiply(new BigDecimal("60")).multiply(new BigDecimal("1000000000"));
            }else {
                minute = new BigDecimal("0");
            }
            if(!TextUtils.isEmpty(et_seconds.getText().toString())) {
                seconds = new BigDecimal(et_seconds.getText().toString()).multiply(new BigDecimal("1000000000"));
            }else {
                seconds = new BigDecimal("0");
            }

            timaBig = new BigDecimal(hout.add(minute).add(seconds).toString());
            Log.e("FDDD23ds","time:" + time);
            if (TextUtils.isEmpty(et_paimai_price_jiange.getText())) {
                ToastUtil.showShort(TradeListDetailActivity.this, "???????????????????????????");
                return;
            } else {
                if (Double.valueOf(et_paimai_price_jiange.getText().toString()) < 0) {
                    ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????????????????0");
                    return;
                } else if (Double.valueOf(et_paimai_price_jiange.getText().toString()) < 0.000001) {
                    ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????????????????0.000001");
                    return;
                }
            }
            if (TextUtils.isEmpty(balance)) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????!");
                return;
            } else {
                if (Double.valueOf(balance) < 0.009) {
                    ToastUtil.showShort(TradeListDetailActivity.this, "????????????!");
                    return;
                }
            }
            if (pvPayTimeLong >= pvPayTimeEndLong) {
                ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????????????????????????????");
                return;
            }
            BigDecimal bigDecimal = new BigDecimal(et_min_paimai_price.getText().toString());
            BigDecimal bigDecimal1 = bigDecimal.multiply(new BigDecimal(10).pow(6));
            String bss = bigDecimal1.toString() + "";
            if (!TextUtils.isEmpty(bss) && bss.contains(".")) {
                bss = bss.substring(0, bss.indexOf(".")) + "";
            } else {
                bss = bss + "";
            }
            price2.setAmount(bss);
            nftSalesTwoBean.setStart_price(price2);

            NftSalesTwoBean.MinPrice minPrice = new NftSalesTwoBean.MinPrice();
            minPrice.setDenom("uunique");
            BigDecimal bigDecimalMin = new BigDecimal(et_paimai_price_jiange.getText().toString());
            BigDecimal bigDecimal1Min = bigDecimalMin.multiply(new BigDecimal(10).pow(6));
            String bssMin = bigDecimal1Min.toString() + "";
            if (!TextUtils.isEmpty(bssMin) && bssMin.contains(".")) {
                bssMin = bssMin.substring(0, bss.indexOf(".")) + "";
            } else {
                bssMin = bssMin + "";
            }
            minPrice.setAmount(bssMin);
            nftSalesTwoBean.setMin_step(minPrice);
            nftSalesTwoBean.setAuto_agree_period(timaBig.toString());

            sign = TransactionUtilNew.INSTANCE.uniquePaiPriceSigningTransaction(privateKey2,
                    Long.valueOf(strFee), 200000,
                    nftSalesTwoBean.toString(),
                    Long.valueOf(sequence1),
                    Long.valueOf(account_num));
            Log.e("FFF44455", "SIGN1:" + sign);
        }

        if (!isSelectTimeGou) {
            ToastUtil.showShort(TradeListDetailActivity.this, "????????????????????????????????????");
            return;
        }
        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }

    }

    private void showTimeDialog() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                pvTimeLong = date.getTime();
                et_start_time_guding.setText(getOldTime(date));
                st_start_time_guding = getTime(date);

                Log.e("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
            }
        }
    }

    private void showPayTimeDialog() {
        pvPayTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                pvPayTimeLong = date.getTime();
                et_pai_start_time.setText(getOldTime(date));
                st_pai_start_time = getTime(date);

                Log.e("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                .build();

        Dialog mDialog = pvPayTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvPayTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
            }
        }
    }

    private void showTimeDialogEnd() {
        pvTimeEnd = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                pvTimeEndLong = date.getTime();
                et_end_time_guding.setText(getOldTime(date));
                st_end_time_guding = getTime(date);
                Log.e("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                .build();

        Dialog mDialog = pvTimeEnd.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTimeEnd.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
            }
        }
    }

    private void showPayTimeDialogEnd() {
        pvPayTimeEnd = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                pvPayTimeEndLong = date.getTime();
                et_pai_end_time_end.setText(getOldTime(date));
                st_pai_end_time_end = getTime(date);
                Log.e("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                .build();

        Dialog mDialog = pvPayTimeEnd.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvPayTimeEnd.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
            }
        }
    }

    private String getTime(Date localDate) {//???????????????????????????????????????

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
//        format.setTimeZone(TimeZone.getTimeZone("GMT"));
//        return format.format(date);

        long localTimeInMillis=localDate.getTime();
        /** long???????????????Calendar */
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** ????????????????????? */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** ?????????????????? */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** ??????????????????????????????????????????????????????UTC??????*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** ?????????????????????UTC???????????? */
        Date utcDate=new Date(calendar.getTimeInMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(utcDate);
    }
    public static String localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate= null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis=localDate.getTime();
        /** long???????????????Calendar */
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** ????????????????????? */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** ?????????????????? */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** ??????????????????????????????????????????????????????UTC??????*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** ?????????????????????UTC???????????? */
        Date utcDate=new Date(calendar.getTimeInMillis());
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(utcDate);
    }

    private String getOldTime(Date date) {//???????????????????????????????????????

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    @Override
    public void loadData(GoTradeDetailBean goTradeDetailBean) {
        if (goTradeDetailBean != null && goTradeDetailBean.getCode() == 200) {
            mgoTradeDetailBean = goTradeDetailBean;
            Log.e("FF344", "==mgoTradeDetailBean===" + new Gson().toJson(mgoTradeDetailBean));
            tv_id_data.setText("" + mgoTradeDetailBean.getData().getNftId());
            if (mgoTradeDetailBean.getData().getFreezeState() == 0) {
                tv_liutongzhong.setText("?????????");
            } else if (mgoTradeDetailBean.getData().getFreezeState() == 1) {
                tv_liutongzhong.setText("?????????");
            } else if (mgoTradeDetailBean.getData().getFreezeState() == 2) {
                tv_liutongzhong.setText("????????????");
            }
            if (mgoTradeDetailBean.getData().getProductType() == 1) {//??????
                tv_product_type.setText("??????");
            } else if (mgoTradeDetailBean.getData().getProductType() == 2) {//?????????
                tv_product_type.setText("?????????");
            } else if (mgoTradeDetailBean.getData().getProductType() == 3) {//?????????
                tv_product_type.setText("?????????");
            } else if (mgoTradeDetailBean.getData().getProductType() == 4) {//?????????
                tv_product_type.setText("?????????");
            }
            tv_product_name.setText(mgoTradeDetailBean.getData().getProductName());
            tv_product_detail.setText(mgoTradeDetailBean.getData().getProductIntro());
            tv_royalties.setText(mgoTradeDetailBean.getData().getRoyalty() + "%");//??????
            tv_vat.setText(mgoTradeDetailBean.getData().getProductVat() + "%");//?????????
            Glide.with(this).load(mgoTradeDetailBean.getData().getProductCover()).into(img_dail_top);
            String proveUrl = goTradeDetailBean.getData().getProveUrl();
            if (!TextUtils.isEmpty(proveUrl)) {
                if (!proveUrl.contains(",")) {
                    proveUrls.add(proveUrl);
                } else {
                    proveUrls = Arrays.asList(proveUrl.split(","));
                }
                Log.e("FF344", "proveUrls:" + proveUrls);
                tradeListPowerAdapter.setNewData(proveUrls);
            }
            tv_nft_hetong.setText(mgoTradeDetailBean.getData().getContractName());
            heUrl = mgoTradeDetailBean.getData().getContractUrl();
        }
    }

    //???????????????
    @Override
    public void loadData(GoTradeCationBean goTradeCationBean) {
        if (goTradeCationBean.getCode() == 200) {
            if (goTradeCationBean.getData() != null && goTradeCationBean.getData().getCautionMoney() != null) {
                tv_zhiya_jin.setText(goTradeCationBean.getData().getCautionMoney() + " unique");
            }
        }

    }

    @Override
    public void loadFail() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(3000);
                xiaoShouSuccessDialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    };

    //NFT????????????--??????
    @Override
    public void loadTradeData(TradeDetailPriceBean jsonObject) {
        if (jsonObject.getCode() == 200) {
            Log.e("FF88767", "success:" + new Gson().toJson(jsonObject));
            if (xiaoShouSuccessDialog == null) {
                xiaoShouSuccessDialog = new XiaoShouSuccessDialog(this);
                xiaoShouSuccessDialog.setOnclick(this);
                xiaoShouSuccessDialog.show();
            }
            handler.sendEmptyMessage(100);
        } else {
            ToastUtil.showShort(TradeListDetailActivity.this, jsonObject.getMsg());
        }

    }

    //NFT????????????--??????
    @Override
    public void loadTradeFail(String msg) {
        ToastUtil.showShort(TradeListDetailActivity.this, msg);
    }

    //NFT????????????--????????????
    @Override
    public void loadTradeGuDingData(TradeDetailPriceBean jsonObject) {
        if (jsonObject.getCode() == 200) {

            if (xiaoShouSuccessDialog == null) {
                xiaoShouSuccessDialog = new XiaoShouSuccessDialog(this);
                xiaoShouSuccessDialog.setOnclick(this);
                xiaoShouSuccessDialog.show();
            }
            handler.sendEmptyMessage(100);
        } else {
            ToastUtil.showShort(TradeListDetailActivity.this, jsonObject.getMsg());
        }
    }

    //NFT????????????--????????????
    @Override
    public void loadTradeGuDingFail(String msg) {
        ToastUtil.showShort(TradeListDetailActivity.this, msg);
    }

    @Override
    public void setOnSafeClickView(String et_pass) {
        String pass_sha = AESEncrypt.sha(et_pass);
        String shapsd = unique.getPsd();
        if (pass_sha.equals(shapsd)) {
            safeAdminDialog.dismiss();
            Log.e("FFF44455", "tabTag:" + tabTag);
            Log.e("FFF44455", "sign:" + sign);

            if (tabTag == 1) {//??????
                iTradeTranscationPresenter = new ITradeTranscationPresenterImpl();
                iTradeTranscationPresenter.registerViewCallback(this);
                iTradeTranscationPresenter.getData(nftId, "2", et_min_paimai_price.getText().toString(), et_pai_start_time.getText().toString(),
                        et_pai_end_time_end.getText().toString(), time, et_paimai_price_jiange.getText().toString(), "unique", sign);
            } else if (tabTag == 0) {//??????
                iTradeTranscationGuDingPresenter = new ITradeTranscationGuDingPresenterImpl();
                iTradeTranscationGuDingPresenter.registerViewCallback(this);
                iTradeTranscationGuDingPresenter.getData(nftId, et_min_price_guding.getText().toString(), et_max_price_guding.getText().toString(), et_start_time_guding.getText().toString(),
                        et_end_time_guding.getText().toString(), "unique", sign);
            }
        } else {
            ToastUtils.showToast(TradeListDetailActivity.this, "??????????????????????????????");
            return;
        }
    }

    @Override
    public void setBackZhujiClickView(String et_pass) {

    }
}
