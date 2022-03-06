package com.space.exchange.biz.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.Stack;

public class ActivityManager implements Application.ActivityLifecycleCallbacks {

    private static ActivityManager instance;
    private Stack<Activity> activityStack;

    public static ActivityManager getActivityManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;

    }

    private ActivityManager() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        pushActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (BuildConfig.DEBUG) {
            Log.d("ActivityManager", "获取焦点" + activity.getClass().getSimpleName());
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (BuildConfig.DEBUG) {
            Log.d("ActivityManager", "失去焦点" + activity.getClass().getSimpleName());
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        popActivity(activity);
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        if (BuildConfig.DEBUG) {
            Log.d("ActivityManager", "加入栈中的页面是:" + activity.getClass().getSimpleName());
        }
    }

    // 退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activityStack.remove(activity);
            if (BuildConfig.DEBUG) {
                Log.d("ActivityManager", "退出栈中的页面是:" + activity.getClass().getSimpleName());
            }
        }
    }

    // 退出栈中所有Activity
    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        } else {
            if (!activityStack.empty())
                activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity(同样的全部干掉)
     */
    public void finishSameActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }

    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }

    }

    /**
     * 结束指定的Activity
     */
    public void getActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            Log.d("AppExit", "AppExit: ");
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if(activityStack!=null){
            Log.d("finishAllActivity", "finishAllActivity: "+activityStack.size());
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }

    }
}
