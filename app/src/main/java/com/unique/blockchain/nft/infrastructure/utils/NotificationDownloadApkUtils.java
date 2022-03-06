package com.unique.blockchain.nft.infrastructure.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.space.exchange.biz.common.base.BaseApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.other.AppName;
import com.unique.blockchain.nft.service.DownloadService;

import java.io.File;

public class NotificationDownloadApkUtils {

    private Context mContext;

    private String mDownUrl;

    private File apkFile;

    private NotificationManager notificationManager;

    private NotificationCompat.Builder builder;

    private Notification notification;

    private Thread mThread;

    private boolean isDownloading = true;
    private int progress;

    public NotificationDownloadApkUtils(Context context) {
        this.mContext = context;
        initNotification();
    }


    //初始化通知
    private void initNotification() {
        notificationManager = (NotificationManager) BaseApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1",
                    AppName.appNames, NotificationManager.IMPORTANCE_MIN);
            channel.enableLights(true); //设置开启指示灯，如果设备有的话
            channel.setLightColor(Color.RED); //设置指示灯颜色
            channel.setShowBadge(true); //设置是否显示角标
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);//设置是否应在锁定屏幕上显示此频道的通知
            channel.setDescription(AppName.appNames);//设置渠道描述
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});//设置震动频率
            channel.setBypassDnd(true);//设置是否绕过免打扰模式
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(BaseApplication.getAppContext(), "1");
            builder.setOngoing(true);
        } else {
            builder = new NotificationCompat.Builder(BaseApplication.getAppContext());
            builder.setVibrate(new long[]{0});
            builder.setOngoing(true);
        }
        builder.setContentTitle("正在更新...") //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(BaseApplication.getAppContext().getResources(), R.mipmap.ic_launcher)) //设置通知的大图标
                .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
        notification = builder.build();//构建通知对象
        notification.flags = Notification.FLAG_INSISTENT;
        notificationManager.notify(1, notification);
    }

    public void setmDownUrl(String url) {
        this.mDownUrl = url;
    }

    public void downLoadApk() {
        String apkUrl = mDownUrl;
        String dir = mContext.getExternalFilesDir("apk").getAbsolutePath();
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        if (folder.exists() && folder.isDirectory()) {
            //do nothing
            //folder.delete();
        } else {
            folder.mkdirs();
        }
        String filename = "/coinka.apk";
        String destinationFilePath = dir + filename;
        apkFile = new File(destinationFilePath);
        Log.i("gl!!!", "此时的文件路径" + destinationFilePath);
        //Log.i("gl", "UpdateCheck的Context" + mContext.toString());
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra("url", apkUrl);
        intent.putExtra("dest", destinationFilePath);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        mContext.startService(intent);
    }

    class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                progress = resultData.getInt("progress");
                if (progress == -1) {
                    //-1代表下载异常
                    Toast.makeText(mContext, "网络异常，下载失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                startThread();
                if (progress == 100) {
                    isDownloading = false;
                    //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
                    String[] command = {"chmod", "777", apkFile.toString()};
                    try {
                        ProcessBuilder builder = new ProcessBuilder(command);
                        builder.start();
                        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上

                            Uri apkUri = FileProvider.getUriForFile(mContext, "com.gpb.blockchain.nodemgr.FileProvider", apkFile);//在AndroidManifest中的android:authorities值
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            mContext.startActivity(install);
                        } else {
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(install);
                        }

                    } catch (Exception e) {
                        Log.e("gl", "!!!!!!!!!!!!!!!!!!!!!!!!" + e.toString());
                    }
                }
            } else if (resultCode == DownloadService.UPDATE_EXCEPTION) {
                isDownloading = false;
//                ToastUtils.showToast(mContext, "下载失败,请重新下载");
            }
        }
    }

    //    在子线程中发送通知 避免anr
    private void startThread() {
        if (mThread == null) {
            mThread = new Thread() {
                @Override
                public void run() {
                    while (isDownloading) {
                        builder.setProgress(100, progress, false);
                        builder.setContentText("下载进度:" + progress + "%");
                        Notification build = builder.build();
                        notificationManager.notify(1, build);
                    }
                    progress = 0;
                    notificationManager.cancel(1);
                }
            };
            mThread.start();
        }
    }
}
