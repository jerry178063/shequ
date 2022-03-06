package com.unique.blockchain.nft.websocket;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import com.unique.blockchain.nft.infrastructure.net.NetClient;
import com.unique.blockchain.nft.service.biz_router.BizRouter;
import com.unique.blockchain.nft.service.biz_router.BizRouterFactory;
import com.unique.blockchain.nft.service.biz_router.DexBizRouter;


public class BizRouterServices extends Service {
    private PowerManager.WakeLock wakeLock = null;//锁屏唤醒

    private final static int GRAY_SERVICE_ID = 1001;

    private BizRouterServicesBinder mBinder = new BizRouterServicesBinder();

    //灰色保活
    public static class GrayInnerFourService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    //用于Activity和service通讯
    public class BizRouterServicesBinder extends Binder {
        public BizRouterServicesBinder getService() {
            return BizRouterServicesBinder.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init(getApplicationContext());
        return START_STICKY;
    }

    private int init(Context context){//初始化app
        acquireWakeLock(context);

        BizRouter.setInstance(BizRouterFactory.createDexBizRouter());//创建dex工厂，将来会扩展其他模式
        NetClient.getInstance().init(context);
        return NetClient.getInstance().register(BizRouter.getInstance().inetUpper());
    }

    private void exit(){//退出app
        NetClient.getInstance().unregister(DexBizRouter.getInstance().inetUpper().id());
        NetClient.destroy();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public BizRouterServices() {
    }


    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    @SuppressLint("InvalidWakeLockTag")
    private void acquireWakeLock(Context context) {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }
}
