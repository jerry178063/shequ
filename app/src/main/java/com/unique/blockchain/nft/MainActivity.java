package com.unique.blockchain.nft;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.carlt.networklibs.NetType;
import com.carlt.networklibs.NetworkManager;
import com.carlt.networklibs.annotation.NetWork;
import com.carlt.networklibs.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.ActivityManager;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.base.SupportFragment;
import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SpUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.VersionUpdate;
import com.unique.blockchain.nft.infrastructure.utils.CheckApkUpdateUtil;
import com.unique.blockchain.nft.messagedb.DBHelper;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.SplashActivity;
import com.unique.blockchain.nft.view.fragment.home.HomeFragment;
import com.unique.blockchain.nft.view.fragment.mark.MarkFragment;
import com.unique.blockchain.nft.view.fragment.mark.MarkNewFragment;
import com.unique.blockchain.nft.view.fragment.me.MeNewFragment;
import com.unique.blockchain.nft.view.fragment.me.MeZuiXinFragment;
import com.unique.blockchain.nft.view.fragment.trade.TradeFragment;
import com.unique.blockchain.nft.view.fragment.wallet.WalletManagerFragment;
import com.unique.blockchain.nft.widget.BottomBar;
import com.unique.blockchain.nft.widget.BottomBarTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import okhttp3.Call;
import okhttp3.Response;
import update.UpdateAppUtils;


public class MainActivity extends BaseActivity  {
    private long mExitTime;
    private Bundle bundle;
    private MarkFragment markFragment;
    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    public BottomBar mBottomBar;
    @BindView(R.id.img_guide_home)
    ImageView img_guide_home;
    private int tabItem;

    private SupportFragment[] mFragments = new SupportFragment[5];

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        //注册
        NetworkManager.getInstance().registerObserver(this);
        checkUpdate();
        Log.e("FF334","tabItem:" + tabItem);
        Log.e("FF334","tabItemHome:" + SpUtil.getInstance(MainActivity.this).getString("tabItemHome"));
        if(tabItem == 1) {
            if (!TextUtils.isEmpty(SpUtil.getInstance(MainActivity.this).getString("tabItemHome"))) {
                img_guide_home.setVisibility(View.GONE);
            } else {
                img_guide_home.setVisibility(View.VISIBLE);
            }
        }

        img_guide_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabItem == 1){//市场tab
                    SpUtil.getInstance(MainActivity.this).putString("tabItemHome","1");
                }else if(tabItem == 3){//我的页面
                    SpUtil.getInstance(MainActivity.this).putString("tabItemMe","3");
                }
                img_guide_home.setVisibility(View.GONE);
            }
        });
        //个人消息数据库

    }




    @NetWork(netType = NetType.AUTO)
    public void network(NetType netType) {
        switch (netType) {
            case WIFI:
                Log.e(Constants.LOG_TAG, "wifi");
                ToastUtils.showToast(this,"wifi网络连接正常!");
                break;
            case CMNET:
            case CMWAP:
                Log.e(Constants.LOG_TAG, "4G");
                ToastUtils.showToast(this,"4G网络连接正常!");
                break;
            case AUTO:
                break;
            case NONE:
                Log.e(Constants.LOG_TAG, "无网络");
                ToastUtils.showToast(this,"网络断开连接!");
                break;
            default:
                break;
        }
    }
    @Override
    public void initData() {
        //检测版本是否更新
//        CheckApkUpdateUtil checkApkUpdateUtil = new CheckApkUpdateUtil();
//        checkApkUpdateUtil.checkApkUpdate(this, false, downId -> {
//        });

    }
    //获取验证人地址
//    private void postData(String address) {
//        OkGo.post(UrlConstant.baseGetValiAddUrl2 + "gauss/staking/v1beta1/validators")
//                .headers("projectId", "ab771e9ce0f94b06925f47e3d3ffa51d")
//                .params("address", address)
//                .readTimeOut(10000)
//                .execute(new JsonCallback<JsonObject>() {
//                    @Override
//                    public void onSuccess(JsonObject getValiAddressBean, Call call, Response response) {
//                        dismissDialog();
//                        Log.e("GGG444556", "验证人信息:" + getValiAddressBean);
//                    }
//
//                    @Override
//                    public void onFailure(String code, String message) {
//                        super.onFailure(code, message);
//                        dismissDialog();
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        dismissDialog();
//                    }
//                });
//
//    }
    /**
     * 检查版本更新
     */
    private void checkUpdate() {

        OkGo.get(UrlConstant.baseUrl + "api/version/getLatestVersion")
//                .headers("sign", "sign")
//                .headers("token", "")
//                .upJson(checkVersionPutBean.toString())
                .params("versionType","Android")
                .execute(new JsonCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject versionInfo, Call call, Response response) {
                        Log.e("FKKF2", "versionInfo:" + new Gson().toJson(versionInfo));
                        Log.e("FKKF2", "当前系统版本:" + Integer.valueOf(getAppVersionCode(MainActivity.this).replace(".","")));
                        VersionUpdate versionUpdate = new Gson().fromJson(versionInfo,VersionUpdate.class);
                        if (versionUpdate != null  && versionUpdate.getCode() == 200 && versionUpdate.getData()!= null && versionUpdate.getData().getVersion() != null) {
                            if (Integer.valueOf(getAppVersionCode(MainActivity.this).replace(".","")) < Integer.valueOf(versionUpdate.getData().getVersion().replace(".",""))) {
//                                downloadAndInstallApk(versionInfo);
                                UpdateConfig updateConfig = new UpdateConfig();
                                if(versionUpdate.getData().getForceUpdate() == 1){
                                    updateConfig.setForce(true);
                                }else {
                                    updateConfig.setForce(false);
                                }

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

                            } else {
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
    public  String getAppVersionCode(Context context) {
        String  versionName = "";
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
     * 主页面包括市场、去交易、我的三个Fragment功能模块.
     * @param savedInstanceState
     */
    @Override
    public void bindView(Bundle savedInstanceState) {
        CommonUtil.setStatusBarColor(this, 2);
        if (savedInstanceState == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = MarkNewFragment.newInstance();
//            mFragments[2] = TradeFragment.newInstance();
            mFragments[2] = WalletManagerFragment.newInstance();
//            mFragments[3] = MeNewFragment.newInstance();
            mFragments[3] = MeZuiXinFragment.newInstance();
            getSupportDelegate().loadMultipleRootFragment(R.id.contentContainer, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3]);

//            mFragments[0] = MarkNewFragment.newInstance();
//            mFragments[1] = TradeFragment.newInstance();
//            mFragments[2] = MeNewFragment.newInstance();
//            getSupportDelegate().loadMultipleRootFragment(R.id.contentContainer, 0,
//                    mFragments[0],
//                    mFragments[1],
//                    mFragments[2]);

        } else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(MarkNewFragment.class);
//            mFragments[2] = findFragment(TradeFragment.class);
            mFragments[2] = findFragment(WalletManagerFragment.class);
//            mFragments[3] = findFragment(MeNewFragment.class);
            mFragments[3] = findFragment(MeZuiXinFragment.class);



//            mFragments[0] = findFragment(MarkNewFragment.class);
//            mFragments[1] = findFragment(TradeFragment.class);
//            mFragments[2] = findFragment(MeNewFragment.class);
        }
        mBottomBar.addItem(new BottomBarTab(this,  R.mipmap.main_home_icon,R.mipmap.main_home_no_icon, "首页"))
                .addItem(new BottomBarTab(this,  R.mipmap.icon_wallet,R.mipmap.icon_wallet_select, "市场"))
                .addItem(new BottomBarTab(this,  R.mipmap.icon_node_select,R.mipmap.icon_node, "钱包"))
                .addItem(new BottomBarTab(this, R.mipmap.icon_my_select,R.mipmap.icon_my,  getResources().getString(R.string.main_me)));

//        mBottomBar.addItem(new BottomBarTab(this,  R.mipmap.icon_wallet,R.mipmap.icon_wallet_select, "市场"))
//                .addItem(new BottomBarTab(this,  R.mipmap.icon_node_select,R.mipmap.icon_node, "去交易"))
//                .addItem(new BottomBarTab(this, R.mipmap.icon_my_select,R.mipmap.icon_my,  getResources().getString(R.string.main_me)));
        mBottomBar.setCurrentItem(0);
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                if (mFragments != null && mFragments[position] != null && mFragments[prePosition] != null) {
                    getSupportDelegate().showHideFragment(mFragments[position], mFragments[prePosition]);
                    switch (position) {
                        case 0:
                            tabItem = 0;
                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
                            break;
                        case 1:
                            tabItem = 1;
                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
                            break;
                        case 2:
                            tabItem = 2;
                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
                            break;
                        case 3:
                            tabItem = 3;
                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
                            if(!TextUtils.isEmpty(SpUtil.getInstance(MainActivity.this).getString("tabItemMe"))){
                                img_guide_home.setVisibility(View.GONE);
                            }else {
                                img_guide_home.setVisibility(View.VISIBLE);
                            }
                            img_guide_home.setImageDrawable(getResources().getDrawable(R.mipmap.guide_me_img));
                            break;

//                        case 0:
//                            tabItem = 0;
//                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
//                            break;
//                        case 1:
//                            tabItem = 1;
//                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
//                            break;
//                        case 2:
//                            tabItem = 2;
//                            CommonUtil.setStatusBarTextColor(MainActivity.this,1);
//                            if(!TextUtils.isEmpty(SpUtil.getInstance(MainActivity.this).getString("tabItemMe"))){
//                                img_guide_home.setVisibility(View.GONE);
//                            }else {
//                                img_guide_home.setVisibility(View.VISIBLE);
//                            }
//                            img_guide_home.setImageDrawable(getResources().getDrawable(R.mipmap.guide_me_img));
//                            break;
                    }
                }

            }

            @Override
            public void onTabUnselected(int position) {
                Log.d("MainActivity", "onTabUnselected:" + position);

            }

            @Override
            public void onTabReselected(int position) {
                Log.d("MainActivity", "onTabReselected:" + position);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void exitApp() {
        SupportFragment mFragment = mFragments[mBottomBar.getCurrentItemPosition()];
        if (mFragment.isAdded() && !mFragment.isHidden() && !mFragment.onBackPressedSupport()) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showToast(this, "再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getActivityManager().AppExit();
            }
        }
    }

    //定义处理事件消息方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventMsg(BaseEvent msg) {
        Log.e("FF3222","MSG:" +new Gson().toJson(msg));
//        if (!TextUtils.isEmpty(msg.what)) {
//            if (msg.what.equals("gobuy")) {
//                mBottomBar.setCurrentItem(3);
//            }
//            else if (msg.what.equals("toHome")) {
//                mBottomBar.setCurrentItem(1);
//            }
//        }
//        if(msg.what.equals("go_scan")){
//            mBottomBar.setCurrentItem(1);
//        }

        if (!TextUtils.isEmpty(msg.what)) {
            if (msg.what.equals("gobuy")) {
                mBottomBar.setCurrentItem(3);
            }
            else if (msg.what.equals("toHome")) {
                mBottomBar.setCurrentItem(1);
            }
        }
        if(msg.what.equals("go_scan")){
            mBottomBar.setCurrentItem(1);
        }
        //回收消息
        msg.recycle();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra("index", -1);
        int childIndex = intent.getIntExtra("childIndex", -1);
        if (index >= 0) {
            mBottomBar.setCurrentItem(index);
        }
        if (childIndex >= 0) {
            BaseEvent baseEvent = BaseEvent.obtainMessage();
            baseEvent.what = "childIndex";
            baseEvent.obj = childIndex;
            EventBus.getDefault().post(baseEvent);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            SharedPreferences preferences = getSharedPreferences("language", Context.MODE_PRIVATE);
//            String selectedLanguage = preferences.getString("language", "");
//            Log.e("selectedLanguage", "onActivityResult: " + selectedLanguage);
//            LanguageUtil.applyLanguage(MainActivity.this, selectedLanguage);
//            recreate();
        }
    }
    @Override
    protected void onPause() {
        //注销广播
        NetworkManager.getInstance().unRegisterObserver(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) { //方法名任意，这里的参数和  EventBus.getDefault().post(111);对应即可
        if(!TextUtils.isEmpty(message) && message.equals("choose_wallet")){
            mBottomBar.setCurrentItem(2);
            CommonUtil.setStatusBarTextColor(MainActivity.this,2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /*@Override
    public void SendMessageValue(String strValue) {
        Log.i("TAG", "SendMessageValue: "+strValue);
        bundle = new Bundle();
        bundle.putString("strValue",strValue);
        markFragment.setArguments(bundle);
        //交易界面传过来的值implements TradeFragment.CallBackValue类的继承
    }*/
}