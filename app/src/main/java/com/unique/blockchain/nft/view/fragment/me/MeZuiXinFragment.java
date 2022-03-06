package com.unique.blockchain.nft.view.fragment.me;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.domain.me.PersonInfoBean;
import com.unique.blockchain.nft.domain.me.PersonlInfoBean;
import com.unique.blockchain.nft.domain.me.UploadImageFileBean;
import com.unique.blockchain.nft.domain.me.VersionUpdate;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.view.activity.me.AddressReceiveActivity;
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
import com.unique.blockchain.nft.view.activity.wallet.ReceivePaymentActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import okhttp3.Call;
import okhttp3.Response;
import update.UpdateAppUtils;

/**
 * 我的
 */
public class MeZuiXinFragment extends BaseFragment implements IMePersonlInfoCallBack, IMeGetCompanyInfoCallBack {


    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    public String unique_name;
    IMePersonlPresenter iMePersonlPresenter;
    @BindView(R.id.img_user)
    ImageView img_user;//用户头像
    IMeGetCompanyInfoPresenter iMeGetCompanyInfoPresenter;
    private String dataStr;
    TextView tv_load_touxiang;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_zuixin_layout;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        tv_load_touxiang = (TextView) findViewById(R.id.tv_load_touxiang);
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

    public static MeZuiXinFragment newInstance() {
        Bundle args = new Bundle();
        MeZuiXinFragment fragment = new MeZuiXinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.rea_set_new, R.id.rela_collect,R.id.rela_wallet_manager, R.id.rela_zhiyabaozhengjin_img, R.id.rela_zhuzao_nft,R.id.me_chakan, R.id.lin_wait_trade, R.id.lin_trading, R.id.lin_has_tihuo, R.id.lin_has_shouhuo
            ,R.id.rela_yinsizhengce_img,R.id.rela_rece_address,R.id.rela_about_we,R.id.rela_back_kui,R.id.rela_version_update,R.id.tv_load_touxiang,R.id.img_user})
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
            case R.id.img_user:
            case R.id.tv_load_touxiang:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(MeZuiXinFragment.this, 1); // 打开相册
                break;
            case R.id.rela_wallet_manager://钱包管理
                if (MoreClick.MoreClicks()) {
                    Intent intentMan = new Intent(getContext(), WalletManagerActivity.class);
                    startActivity(intentMan);
                }
                break;
            case R.id.rela_zhiyabaozhengjin_img://质押保证金
                if (MoreClick.MoreClicks()) {
                    Intent intentZhiya = new Intent(getContext(), PledgeActivity.class);
                    startActivity(intentZhiya);
                }
                break;
            case R.id.rela_yinsizhengce_img://隐私政策
                if (MoreClick.MoreClicks()) {
                    Intent intent3 = new Intent(getContext(), AdWebActivity.class);
                    intent3.putExtra("banner_url", UrlConstant.baseWap + "setting/privacy");
                    intent3.putExtra("hideTitle", true);
                    startActivity(intent3);
                }
                break;
            case R.id.rela_about_we://关于我们
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(getContext(), AdWebActivity.class);
                    intent.putExtra("banner_url", UrlConstant.baseWap + "setting/about-us");
                    intent.putExtra("hideTitle", true);
                    startActivity(intent);
                }
                break;
            case R.id.rela_back_kui://意见反馈
                if (MoreClick.MoreClicks()) {
                    Intent intent2 = new Intent(getContext(), AdWebActivity.class);
                    intent2.putExtra("banner_url", UrlConstant.baseWap + "setting/concat-us/" + uniqueAdress);
                    intent2.putExtra("hideTitle", true);
                    startActivity(intent2);
                }
                break;
            case R.id.rela_zhuzao_nft://认证数字店铺
                Log.e("FFD3222","ME：" + uniqueAdress);
                if (MoreClick.MoreClicks()) {
                    getUserDao();
                    if (iMeGetCompanyInfoPresenter == null) {
                        iMeGetCompanyInfoPresenter = new IMeGetPanyInfoPresenterImpl();
                        iMeGetCompanyInfoPresenter.registerViewCallback(this);
                    }
                    iMeGetCompanyInfoPresenter.getData(uniqueAdress);
                }
                break;
            case R.id.rela_version_update://版本更新
                if (MoreClick.MoreClicks()) {
                    checkUpdate();
                }
                break;
//            case R.id.img_change_info://编辑个人信息
//                if (MoreClick.MoreClicks()) {
//                    Intent intentPensor = new Intent(getContext(), PersonalActivity.class);
//                    if (mPrsonlInfoBean != null) {
//                        intentPensor.putExtra("mPrsonlInfoBean", mPrsonlInfoBean.toString());
//                    }
//                    startActivity(intentPensor);
//                }
//                break;
//            case R.id.lin_num_zichan://数字资产
//                if (MoreClick.MoreClicks()) {
//                    Intent intent1 = new Intent(getContext(), DigitalHomeAssetsActivity.class);
//                    startActivity(intent1);
//                }
//                break;
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
            case R.id.rela_collect://我的收藏
                if (MoreClick.MoreClicks()) {
                    Intent intentCollect = new Intent(getContext(), MyCollectionActiviy.class);
                    startActivity(intentCollect);
                }
                break;
//            case R.id.lin_receive_monery://收款
//                if (MoreClick.MoreClicks()) {
//                    Intent intent_receive_payment = new Intent(getContext(), ReceivePaymentActivity.class);
//                    startActivity(intent_receive_payment);
//                }
//                break;
            case R.id.rela_rece_address://收货地址
                if (MoreClick.MoreClicks()) {
                    Intent intente = new Intent(getContext(), AddressReceiveActivity.class);
                    startActivity(intente);
                }
                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            if (requestCode == 1) {
                Uri data1 = data.getData();

                if (images != null && images.size() > 0) {
                    Log.e("FFF3333", "IMAGE_2:" + new Gson().toJson(images));
                    for (int i = 0; i < images.size(); i++) {
                        loadFile(images.get(i), 1);
                    }

                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imgUrl;
    private void loadFile(String path, int pisition) {
        Log.e("FFF3333", "path:" + path);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        OkGo.post(UrlConstant.baseUrlFile + "file/upload")
                .addFileParams("file", fileList)
                .execute(new JsonCallback<UploadImageFileBean>() {
                    @Override
                    public void onSuccess(UploadImageFileBean uploadImageFileBean, Call call, Response response) {
                        //请求成功
                        if (uploadImageFileBean != null) {
                            imgUrl = uploadImageFileBean.getData().getUrl();
                            Log.e("FFD43","imgUrl:" + imgUrl);
                            Glide.with(getContext()).load(imgUrl).into(img_user);
                            PersonInfoBean personInfoBean = new PersonInfoBean();
                            personInfoBean.setWalletAddr(uniqueAdress + "");
                            personInfoBean.setPortraitUrl(imgUrl);
//                        personInfoBean.setNftSign(mNftSign.getText().toString());
                            Log.e("FD444", "imgUrl:" + imgUrl);
                            Log.e("FD444", "personInfoBean:" + new Gson().toJson(personInfoBean));
                            OkGo.post(UrlConstant.baseUrl + UrlConstant.ap)
                                    .upJson(personInfoBean.toString())
                                    .execute(new JsonCallback<CollectionBean>() {

                                        @Override
                                        public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                                            if (jsonObject.getCode() == 200) {
                                                ToastUtil.showShort(getContext(), "修改成功!");
                                                tv_load_touxiang.setText("修改头像");
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FD444", "code:" + code + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
    /**
     * 检查版本更新
     *///点击事件，一会找到钱包管理调用
    private void checkUpdate() {
        OkGo.get(UrlConstant.baseUrl + "api/version/getLatestVersion")
//                .headers("sign", "sign")
//                .headers("token", "")
//                .upJson(checkVersionPutBean.toString())
                .params("versionType", "Android")
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject versionInfo, Call call, Response response) {
                        VersionUpdate versionUpdate = new Gson().fromJson(versionInfo, VersionUpdate.class);
                        if (versionUpdate != null && versionUpdate.getCode() == 200 && versionUpdate.getData() != null && versionUpdate.getData().getVersion() != null) {
                            if (Integer.valueOf(getAppVersionCode(getContext()).replace(".", "")) < Integer.valueOf(versionUpdate.getData().getVersion().replace(".", ""))) {
//                                downloadAndInstallApk(versionInfo);
                                UpdateConfig updateConfig = new UpdateConfig();
                                if (versionUpdate.getData().getForceUpdate() == 1) {
                                    updateConfig.setForce(true);
                                } else {
                                    updateConfig.setForce(false);
                                }
                                updateConfig.setCheckWifi(true);
                                UiConfig uiConfig = new UiConfig();
                                uiConfig.setUpdateLogoImgRes(R.mipmap.pic_rocket);
                                uiConfig.setUpdateBtnBgRes(R.drawable.all_wallet_bg);
                                uiConfig.setUiType(UiType.PLENTIFUL);
                                uiConfig.setCancelBtnText("下次再说");
                                uiConfig.setUpdateBtnText("马上升级");
                                UpdateAppUtils
                                        .getInstance()
                                        .apkUrl(versionUpdate.getData().getDownloadLink() + "")
                                        .updateTitle("更新提示")
                                        .updateContent(versionUpdate.getData().getReleaseNote())
                                        .updateConfig(updateConfig)
                                        .uiConfig(uiConfig)
                                        .update();

//                                // ui配置
//                                val uiConfig = UiConfig().apply {
//                                    uiType = UiType.PLENTIFUL
//                                    cancelBtnText = "下次再说"
//                                    updateLogoImgRes = R.drawable.ic_update
//                                    updateBtnBgRes = R.drawable.bg_btn
//                                    titleTextColor = Color.BLACK
//                                    titleTextSize = 18f
//                                    contentTextColor = Color.parseColor("#88e16531")
//                                }
//
//                                // 更新配置
//                                val updateConfig = UpdateConfig().apply {
//                                    force = true
//                                    checkWifi = true
//                                    needCheckMd5 = true
//                                    isShowNotification = true
//                                    notifyImgRes = R.drawable.ic_logo
//                                    apkSavePath = Environment.getExternalStorageDirectory().absolutePath +"/teprinciple"
//                                    apkSaveName = "teprinciple"
//                                }

                            } else {
                                ToastUtils.showToast(getContext(), "当前已是最新版本");
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FKKF2", "onFailure:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FKKF2", "onError:" + e);
                    }
                });


    }
    /**
     * 返回当前程序版本号
     */
    public String getAppVersionCode(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName + "";
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
//            if (TextUtils.isEmpty(personlInfoBean.getData().getNickName())) {
//                tv_nicheng.setText("昵称");
//            } else {
//                tv_nicheng.setText(personlInfoBean.getData().getNickName() + "");
//            }
//            if (TextUtils.isEmpty(personlInfoBean.getData().getNftSign())) {
//                tv_gexing.setText("编辑个性签名...");
//            } else {
//                tv_gexing.setText(personlInfoBean.getData().getNftSign() + "");
//            }
            if(!TextUtils.isEmpty(personlInfoBean.getData().getPortraitUrl())) {
                tv_load_touxiang.setText("更改头像");
                Glide.with(getContext()).load(personlInfoBean.getData().getPortraitUrl()).error(R.mipmap.touxiang).into(img_user);
            }else {
//                tv_load_touxiang.setText("上传头像");
            }
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
