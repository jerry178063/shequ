package com.unique.blockchain.nft.view.activity.me;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.me.VersionUpdate;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.view.activity.AdWebTwoActivity;

import butterknife.BindView;
import butterknife.OnClick;
import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import okhttp3.Call;
import okhttp3.Response;
import update.UpdateAppUtils;

/**
 * 设置
 * */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.re_contant)
    RelativeLayout re_contant;
    public User unique;
    public  UserDao userDao;
    public  String uniqueAdress;
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
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(SettingActivity.this, Tools.name, ""))).build().unique();
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
    public void initData() {
        tv_version.setText(getAppVersionCode(SettingActivity.this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void bindView(Bundle bundle) {

    }


    @OnClick({R.id.img_back,R.id.re_version,R.id.re_contant,R.id.re_about_we,R.id.re_yinsi})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.re_version://版本更新
                checkUpdate();
                break;
            case R.id.re_contant://联系我们
                Intent intent2 = new Intent(SettingActivity.this, AdWebActivity.class);
                intent2.putExtra("banner_url", UrlConstant.baseWap + "setting/concat-us/" + uniqueAdress);
                intent2.putExtra("hideTitle", true);
                startActivity(intent2);
                break;
            case R.id.re_about_we://关于我们
                Intent intent = new Intent(SettingActivity.this,AdWebActivity.class);
                intent.putExtra("banner_url", UrlConstant.baseWap + "setting/about-us");
                intent.putExtra("hideTitle", true);
                startActivity(intent);
                break;
            case R.id.re_yinsi://隐私政策
                Intent intent3 = new Intent(SettingActivity.this,AdWebActivity.class);
                intent3.putExtra("banner_url", UrlConstant.baseWap + "setting/privacy");
                intent3.putExtra("hideTitle", true);
                startActivity(intent3);
                break;

        }
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
                            if (Integer.valueOf(getAppVersionCode(SettingActivity.this).replace(".", "")) < Integer.valueOf(versionUpdate.getData().getVersion().replace(".", ""))) {
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
                                ToastUtils.showToast(SettingActivity.this, "当前已是最新版本");
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


}
