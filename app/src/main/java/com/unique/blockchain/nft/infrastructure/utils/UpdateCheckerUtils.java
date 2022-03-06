package com.unique.blockchain.nft.infrastructure.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.space.exchange.biz.common.GlobalField;
import com.space.exchange.biz.common.base.BaseApplication;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.service.DownloadService;

import java.io.File;


public class UpdateCheckerUtils {

    private static final String TAG = "UpdateChecker";
    private Context mContext;
    //检查版本信息的线程
    private Thread mThread;
    //版本对比地址
    private String mCheckUrl;
    //下载apk的对话框
    private ProgressDialog mProgressDialog;

    private String json;//json数据
    private File apkFile;

    public void setCheckUrl(String url) {
        mCheckUrl = url;
    }

    public UpdateCheckerUtils(Context context) {
        mContext = context;
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("正在下载");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressDrawable(BaseApplication.getAppContext().getResources().getDrawable(R.drawable.progressbar_bg));

        mProgressDialog.setOnCancelListener(dialog -> {

        });
        mProgressDialog.setOnDismissListener(dialog -> {
            // TODO Auto-generated method stub

        });
    }

    public void downLoadApk() {
        String apkUrl = mCheckUrl;
        Log.i("gl!!!", "!!!!!!!!!!!!!!!!!!!!!!" + mCheckUrl);
        String dir = mContext.getExternalFilesDir("apk").getAbsolutePath();
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        if (folder.exists() && folder.isDirectory()) {
            //do nothing
            //folder.delete();
        } else {
            folder.mkdirs();
        }
        String filename = "/zly.apk";
        String destinationFilePath = dir + filename;
        apkFile = new File(destinationFilePath);
        Log.i("gl!!!", "此时的文件路径" + destinationFilePath);
        mProgressDialog.show();
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
                int progress = resultData.getInt("progress");
                if (progress == -1) {
                    //-1代表下载异常
                    mProgressDialog.dismiss();
                    Toast.makeText(mContext, "网络异常，下载失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressDialog.setProgress(progress);
                if (progress == 100) {
                    mProgressDialog.dismiss();
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
                if (SPUtils.getBoolean(mContext, GlobalField.IS_FORCE_UPDATE, false)) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setMessage("下载失败");
                    }
                } else {
                    if (mProgressDialog != null) {
                        mProgressDialog.setMessage("下载失败,点击返回退出下载");
                        mProgressDialog.setCancelable(true);
                    }
                }
            }
        }
    }
}

