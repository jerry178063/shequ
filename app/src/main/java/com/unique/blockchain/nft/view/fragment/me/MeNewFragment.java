package com.unique.blockchain.nft.view.fragment.me;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.domain.me.PersonlInfoBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.view.activity.me.DigitalHomeAssetsActivity;
import com.unique.blockchain.nft.view.activity.me.DigitalStoreActivity;
import com.unique.blockchain.nft.view.activity.me.GriiActivity;
import com.unique.blockchain.nft.view.activity.me.MyCollectionActiviy;
import com.unique.blockchain.nft.view.activity.me.PersonalActivity;
import com.unique.blockchain.nft.view.activity.me.PledgeActivity;
import com.unique.blockchain.nft.view.activity.me.RegisterDigitalResultActivity;
import com.unique.blockchain.nft.view.activity.me.SettingActivity;
import com.unique.blockchain.nft.view.activity.me.WalletManagerActivity;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeGetCompanyInfoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMePersonlPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeGetPanyInfoPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMePersonlPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeGetCompanyInfoCallBack;
import com.unique.blockchain.nft.view.activity.me.me_view.IMePersonlInfoCallBack;
import com.unique.blockchain.nft.view.activity.wallet.CreateUserActivity;
import com.unique.blockchain.nft.view.activity.wallet.ReceivePaymentActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MeNewFragment extends BaseFragment implements IMePersonlInfoCallBack, IMeGetCompanyInfoCallBack {


    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    public String unique_name;
    @BindView(R.id.me_unqique)
    TextView me_unqique;
    IMePersonlPresenter iMePersonlPresenter;
    @BindView(R.id.tv_nicheng)
    TextView tv_nicheng;
    @BindView(R.id.tv_gexing)
    TextView tv_gexing;//个性签名
    @BindView(R.id.img_user)
    ImageView img_user;//用户头像
    @BindView(R.id.lin_change_info)
    LinearLayout lin_change_info;
    IMeGetCompanyInfoPresenter iMeGetCompanyInfoPresenter;
    private String dataStr;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_new_layout;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getUserDao();
        if (iMePersonlPresenter == null) {
            iMePersonlPresenter = new IMePersonlPresenterImpl();
            iMePersonlPresenter.registerViewCallback(this);
        }
        if(unique != null && unique.getDid() != null) {
            iMePersonlPresenter.getData(unique.getDid());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserDao();
        if(unique != null && unique.getDid() != null) {
            iMePersonlPresenter.getData(unique.getDid());
        }
        me_unqique.setText(uniqueAdress);
    }

    private void getUserDao() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
            Log.e("FF543","NAME:" + SPUtils.getString(getContext(), Tools.name, ""));
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
    }

    public static MeNewFragment newInstance() {
        Bundle args = new Bundle();
        MeNewFragment fragment = new MeNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.rea_set_new, R.id.rela_wallet_manager, R.id.re_zhiya_me, R.id.rela_shuzi_shopping, R.id.img_copy, R.id.img_change_info, R.id.lin_change_info
            , R.id.lin_num_zichan, R.id.me_chakan, R.id.lin_wait_trade, R.id.lin_trading, R.id.lin_has_tihuo, R.id.lin_has_shouhuo, R.id.lin_my_collection
            , R.id.lin_receive_monery})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.rea_set_new://设置
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
//                    Intent intent = new Intent(getContext(), AdWebActivity.class);
//                    intent.putExtra("banner_url", "http://192.168.2.13:8008/index?address=" + uniqueAdress
//                    + "&themeColor=night" + "&userName=" + SPUtils.getString(getContext(),"witch_wallet",null) + "&language=" + "zh-Hant");
//                    intent.putExtra("hideTitle", true);
//                    startActivity(intent);
                }
                break;
            case R.id.rela_wallet_manager://钱包管理
                if (MoreClick.MoreClicks()) {
                    Intent intentMan = new Intent(getContext(), WalletManagerActivity.class);
                    startActivity(intentMan);
                }
                break;
            case R.id.re_zhiya_me://质押保证金
                if (MoreClick.MoreClicks()) {
                    Intent intentZhiya = new Intent(getContext(), PledgeActivity.class);
                    startActivity(intentZhiya);
                }
                break;
            case R.id.rela_shuzi_shopping://认证数字店铺
                if (MoreClick.MoreClicks()) {
                    if (iMeGetCompanyInfoPresenter == null) {
                        iMeGetCompanyInfoPresenter = new IMeGetPanyInfoPresenterImpl();
                        iMeGetCompanyInfoPresenter.registerViewCallback(this);
                    }
                    iMeGetCompanyInfoPresenter.getData(uniqueAdress);
                }
                break;
            case R.id.img_copy://复制
                if (MoreClick.MoreClicks()) {
                    copyContentToClipboard(me_unqique.getText().toString() + "", getContext());
                    ToastUtils.getInstance(getContext()).show("复制成功!");
                }
                break;
            case R.id.lin_change_info:
            case R.id.img_change_info://编辑个人信息
                if (MoreClick.MoreClicks()) {
                    Intent intentPensor = new Intent(getContext(), PersonalActivity.class);
                    if (mPrsonlInfoBean != null) {
                        intentPensor.putExtra("mPrsonlInfoBean", mPrsonlInfoBean.toString());
                    }
                    startActivity(intentPensor);
                }
                break;
            case R.id.lin_num_zichan://数字资产
                if (MoreClick.MoreClicks()) {
                    Intent intent1 = new Intent(getContext(), DigitalHomeAssetsActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.me_chakan://我的nft
                if (MoreClick.MoreClicks()) {
                    Intent intentCha = new Intent(getContext(), GriiActivity.class);
                    startActivity(intentCha);
                }
                break;
            case R.id.lin_wait_trade://待交易
                if (MoreClick.MoreClicks()) {
                    Intent intentWait = new Intent(getContext(), GriiActivity.class);
                    intentWait.putExtra("item", "1");
                    startActivity(intentWait);
                }
                break;
            case R.id.lin_trading://交易中
                if (MoreClick.MoreClicks()) {
                    Intent intentTrading = new Intent(getContext(), GriiActivity.class);
                    intentTrading.putExtra("item", "2");
                    startActivity(intentTrading);
                }
                break;
            case R.id.lin_has_tihuo://已提货
                if (MoreClick.MoreClicks()) {
                    Intent intentReceived = new Intent(getContext(), GriiActivity.class);
                    intentReceived.putExtra("item", "3");
                    startActivity(intentReceived);
                }
                break;
            case R.id.lin_has_shouhuo://已收货
                if (MoreClick.MoreClicks()) {
                    Intent intentShouHuo = new Intent(getContext(), GriiActivity.class);
                    intentShouHuo.putExtra("item", "4");
                    startActivity(intentShouHuo);
                }
                break;
            case R.id.lin_my_collection://我的收藏
                if (MoreClick.MoreClicks()) {
                    Intent intent2 = new Intent(getContext(), MyCollectionActiviy.class);
                    startActivity(intent2);
                }
                break;
            case R.id.lin_receive_monery://收款
                if (MoreClick.MoreClicks()) {
                    Intent intent_receive_payment = new Intent(getContext(), ReceivePaymentActivity.class);
                    startActivity(intent_receive_payment);
                }
                break;
        }

    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    PersonlInfoBean mPrsonlInfoBean;

    @Override
    public void loadPersonlInfoPostData(PersonlInfoBean personlInfoBean) {
        if (personlInfoBean != null && personlInfoBean.getCode() == 200) {
            mPrsonlInfoBean = personlInfoBean;
            Log.e("FF4322", "personlInfoBean:" + new Gson().toJson(personlInfoBean));
            if (TextUtils.isEmpty(personlInfoBean.getData().getNickName())) {
                tv_nicheng.setText("昵称");
            } else {
                tv_nicheng.setText(personlInfoBean.getData().getNickName() + "");
            }
            if (TextUtils.isEmpty(personlInfoBean.getData().getNftSign())) {
                tv_gexing.setText("编辑个性签名...");
            } else {
                tv_gexing.setText(personlInfoBean.getData().getNftSign() + "");
            }
            Glide.with(getContext()).load(personlInfoBean.getData().getPortraitUrl()).error(R.mipmap.touxiang).into(img_user);
        }
    }

    @Override
    public void loadPersonlInfoPostFail() {

    }

    @Override
    public void loadGetCompanyInfoPostData(CompanyInfoBean companyInfoBean) {
        if (companyInfoBean != null && companyInfoBean.getCode() == 200) {
            if (companyInfoBean.getData() == null) {
                Intent intentConmit = new Intent(getContext(), DigitalStoreActivity.class);
                intentConmit.putExtra("uniqueAdress", uniqueAdress);
                startActivity(intentConmit);
            } else {
                dataStr = companyInfoBean.toString();
                Intent intent = new Intent(getContext(), RegisterDigitalResultActivity.class);
                intent.putExtra("dataStr", dataStr + "");
                startActivity(intent);
            }
        }
    }

    @Override
    public void loadGetCompanyInfoPostFail() {

    }
}
