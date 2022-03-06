package com.space.exchange.biz.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.space.exchange.biz.common.cache.UserStore;
//import com.space.biz.storage.cache.UserStore;

/**
 * 应用初始化操作
 * Author: Evan
 * Date: 2019/5/31
 */
public class GlobalInit {

    public static void init(Application app) {
        initCache();
        initARoute(app);
    }

    private static void initCache() {
        UserStore.cheakFingerprintOrPattern = false;
        UserStore.checkSafeSetupDialog = false;
    }

    /**
     * 初始化ARoute
     */
    private static void initARoute(Application app) {

 //       if (BuildConfig.DEBUG)
        {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(app);
    }
}
