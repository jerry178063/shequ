package com.space.exchange.biz.net.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.UUID;

public class AppUtils {

    private static Typeface globalTypeFace;

    /*显示toast*/
    public static void showToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取屏幕分辨率，单位为px
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率，单位为px
     */
    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    /**
     * 获取版本号
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(versionName)) {
            versionName = "";
        }
        return versionName;
    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetwork(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获取当前类名和方法名
     *
     * @param context
     * @return
     */
    public static String getCmn(final Context context) {
        if (context != null) {
            String clazzName = context.getClass().getName();
            String funcName = new Throwable().getStackTrace()[1].getMethodName();
            return clazzName + funcName;
        }
        return null;
    }


    /*获取设备id*/
    public static String installationId;

    public static String getInstallationId(Context context) {
        if (installationId == null) {
            File installation = new File(context.getFilesDir(), "INSTALLATION");
            try {
                if (!installation.exists()) {
                    writeInstallationFile(installation);
                }
                installationId = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return installationId;
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }


    /**
     * 获取全局字体
     *
     * @param context
     * @return
     */
    public static Typeface getGlobalTypeFace(Context context) {
        if (globalTypeFace == null) {
            globalTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/dhytext.ttf");
        }
        return globalTypeFace;
    }


    /**
     * 获取屏幕宽度(px)
     *
     * @param context
     * @return
     */
    private static int screenWidthPx;

    public static int getWidthPx(Context context) {
        if (screenWidthPx <= 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            screenWidthPx = dm.widthPixels;
        }
        return screenWidthPx;
    }

    /**
     * 获取屏幕高度(px)
     *
     * @param context
     * @return
     */
    private static int screenHeightPx;

    public static int getHeightPx(Context context) {
        if (screenHeightPx <= 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            screenHeightPx = dm.heightPixels;
        }
        return screenHeightPx;
    }

    /**
     * 获取屏幕高度(dp)
     *
     * @param context
     * @return
     */
    private static float screenHeightDp;

    public static float getHeightDp(Context context) {
        if (screenHeightDp <= 0) {
            screenHeightDp = px2dip(context, getHeightPx(context));
        }
        return screenHeightDp;
    }

    /**
     * 获取屏幕宽度(dp)
     *
     * @param context
     * @return
     */
    private static float screenWidthDp;

    public static float getWidthDp(Context context) {
        if (screenWidthDp <= 0) {
            screenWidthDp = px2dip(context, getWidthPx(context));
        }
        return screenWidthDp;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 每4位添加一个空格
     *
     * @param content
     * @return
     */
    public static String addSpeaceByCredit(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        content = content.replaceAll(" ", "");
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        StringBuilder newString = new StringBuilder();
        for (int i = 1; i <= content.length(); i++) {
            if (i % 4 == 0 && i != content.length()) {
                newString.append(content.charAt(i - 1) + " ");
            } else {
                newString.append(content.charAt(i - 1));
            }
        }
//        Log.i("mengyuan", "添加空格后："+newString.toString());
        return newString.toString();
    }

    /**
     * 获取进程的名称
     *
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }


}