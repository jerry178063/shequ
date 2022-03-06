package com.space.exchange.biz.common.base;

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.space.exchange.biz.common.ActivityManager;
import com.space.exchange.biz.common.R;
import com.space.exchange.biz.common.util.AppUtil;
import com.space.exchange.biz.common.util.CrashHandler;
import com.zxy.tiny.Tiny;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yc.Zhao on 2017/11/29 0029.
 */

public class BaseApplication extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        registerActivityLifecycleCallbacks(ActivityManager.getActivityManager());
        Tiny.getInstance().init(this);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.transparent, R.color.color_28292B);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            layout.setRefreshFooter(new BallPulseFooter(BaseApplication.this).setSpinnerStyle(SpinnerStyle.Scale));
            ClassicsFooter classicsFooter = new ClassicsFooter(context).setDrawableSize(20);
            classicsFooter.setFinishDuration(1000);
            classicsFooter.setTextSizeTitle(13f);
            classicsFooter.setAccentColorId(R.color.color_b8c4d9);
            classicsFooter.setPrimaryColorId(R.color.transparent);
            classicsFooter.setProgressResource(R.drawable.icon_tb_tijiaozhong);
            classicsFooter.setDrawableMarginRight(6);
            classicsFooter.setDrawableSize(16);
            return classicsFooter;
        });
        //  将Crash保存本地
        CrashHandler crashHandler = CrashHandler.getInstance();//
        crashHandler.init(this);
        EventBus.getDefault().register(this);
        GlobalInit.init(this);
        AppUtil.syncIsDebug(this);


//        /**
//         * 对于7.0以下，需要在Application创建的时候进行语言切换
//         */
//        String language = SpUtil.getInstance(this).getString(SpUtil.LANGUAGE);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//
//            LanguageUtil.changeAppLanguage(BaseApplication.getAppContext(), language);
//        }
    }

    public static Context getAppContext() {
        return mAppContext;
    }


}
