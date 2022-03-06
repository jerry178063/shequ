package com.unique.blockchain.nft.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.ResultReceiver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;

    public static final int UPDATE_EXCEPTION = 4509;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InputStream is = null;
        FileOutputStream fos = null;
        String urlToDownload = intent.getStringExtra("url");
        String fileDestination = intent.getStringExtra("dest");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        if (urlToDownload.startsWith("http") && urlToDownload.endsWith("apk")) {
            OkHttpClient client = new OkHttpClient();
            try {
                Request request = new Request.Builder().get().url(urlToDownload).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    //Log.d("gl", "开始下载apk");
                    //获取内容总长度
                    long contentLength = response.body().contentLength();
                    //设置最大值

                    //保存到sd卡
                    //File apkFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".apk");
                    fos = new FileOutputStream(fileDestination);
                    //获得输入流
                    is = response.body().byteStream();
                    //定义缓冲区大小
                    byte[] bys = new byte[1024];
                    long progress = 0;
                    int len = -1;
                    while ((len = is.read(bys)) != -1) {
                        try {
//                        Thread.sleep(1);
                            fos.write(bys, 0, len);
                            fos.flush();
                            progress += len;
                            //设置进度
                            Bundle resultData = new Bundle();
                            resultData.putInt("progress", (int) (progress * 100 / contentLength));
                            receiver.send(UPDATE_PROGRESS, resultData);

                        } catch (Exception e) {
                            Message msg = Message.obtain();
                            Bundle resultData = new Bundle();
                            receiver.send(UPDATE_EXCEPTION, resultData);
                        }
                    }
//                Bundle resultData = new Bundle();
//                resultData.putInt("progress", 100);
//                receiver.send(UPDATE_PROGRESS, resultData);
                }
            } catch (IOException e) {
           /* Message msg = Message.obtain();
            msg.what = SHOW_ERROR;
            msg.obj = "ERROR:10003";
            handler.sendMessage(msg);
            load2Login();*/
                Bundle resultData = new Bundle();
                receiver.send(UPDATE_EXCEPTION, resultData);
            } finally {
                //关闭io流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is = null;
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fos = null;
                }
            }

       /* Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        receiver.send(UPDATE_PROGRESS, resultData);*/
        }
    }
}