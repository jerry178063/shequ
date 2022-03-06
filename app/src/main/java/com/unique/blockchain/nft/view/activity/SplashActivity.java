package com.unique.blockchain.nft.view.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.ActivityManager;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.MainActivity;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.service.biz_router.BizRouter;
import com.unique.blockchain.nft.service.biz_router.DexBizRouter;
import com.unique.blockchain.nft.service.biz_router.IBizUpper;
import com.unique.blockchain.nft.view.activity.me.WalletManagerActivity;
import com.unique.blockchain.nft.view.activity.wallet.FristCreadActivity;
import com.unique.blockchain.nft.websocket.BizRouterServices;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

public class SplashActivity extends BaseActivity {

    private ArrayList<View> views;
    private View page1;
    private View page2;
    private View page3;
    private View page4;
//    private MyAdapter myAdapter;
    private ViewPager mActivityVp;
    //是否第一次进入
    String isFirst = "yes";
    private int recLen = 1;
    Timer timer = new Timer();

    private BizRouter bizRouter;
    private IBizUpper iBizUpper;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    if(!SPUtils.getString(SplashActivity.this,Tools.name,"").isEmpty()){
//                        Tools.startActivity(SplashActivity.this, MainActivity.class);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                        Log.e("FF222","销毁");
                    }else {
//                        Tools.startActivity(SplashActivity.this, FristCreadActivity.class);
                        startActivity(new Intent(SplashActivity.this, FristCreadActivity.class));
                        finish();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    public void initUI() {
//        new MyApplication().addActivity(this);
        mActivityVp = findViewById(R.id.activity_vp);
        //启动页

        isFirst = SpUtil.getInstance(this).getString("isFirst");
        onVerifySuccess(1000);
        try {
            //初始化socket
            startBizRouterService();
        }catch (Exception e){

        }

    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("MainActivity", "服务与活动成功绑定");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };
    /**
     * 绑定并启动BizRouterServices
     */
    private void startBizRouterService() {
        Intent bindIntent = new Intent(SplashActivity.this, BizRouterServices.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
//        Intent intent = new Intent(SplashActivity.this, BizRouterServices.class);
        startService(bindIntent);

        bizRouter = new DexBizRouter();
        bizRouter.register(iBizUpper);
    }
    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this, 1);

        MyApplication.mWaleetClick = 0;
    }

    @Override
    public void bindView(Bundle bundle) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("FF222","再次进来了...");
        isFirst = SpUtil.getInstance(this).getString("isFirst");
        onVerifySuccess(1000);
        try {
            //初始化socket
            startBizRouterService();
        }catch (Exception e){

        }

    }

    //校验成功的下一步处理
    private void onVerifySuccess(long millis) {
        Log.e("FFF4442","isFirst:" + isFirst);
        if(!TextUtils.isEmpty(isFirst)) {
            if (!isFirst.equals("no")) {
//                    timer.schedule(task, 1000, 1000);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, FristCreadActivity.class));
                finish();
            } else {
                Log.e("FFF4442","isFirst_2:" + TextUtils.isEmpty(SPUtils.getString(SplashActivity.this, "witch_wallet", null)));
                if (TextUtils.isEmpty(SPUtils.getString(SplashActivity.this, "witch_wallet", null))) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    startActivity(new Intent(SplashActivity.this, WalletManagerActivity.class));
                    finish();
                }else {
                    handler.sendEmptyMessageDelayed(0, millis);
                }

            }
        }else {
//            if(task != null) {
//                if(timer != null) {
//                    timer.schedule(task, 1000, 1000);
//                }
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(SplashActivity.this, FristCreadActivity.class));
            finish();
        }
//        handler.sendEmptyMessageDelayed(0, millis);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // UI thread
            runOnUiThread(() -> {
                recLen--;
                if (recLen < 1) {
                    if(timer != null) {
                        timer.cancel();
                    }
//                    startActivity(new Intent(SplashActivity.this, Guide2Activity.class));
//                    finish();
//                    startActivity(new Intent(SplashActivity.this, WordActivity.class));
                    startActivity(new Intent(SplashActivity.this, FristCreadActivity.class));
                    finish();
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceConnection != null) {
            unbindService(serviceConnection);
        }

    }

}