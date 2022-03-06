package com.unique.blockchain.nft.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.view.activity.me.FeedbackActivity;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by  on 2018/1/5.
 */

public class AdWebActivity extends BaseActivity {

    //    @BindView(R.id.left)
//    LinearLayout left;
    @BindView(R.id.web)
    WebView webView;
    String banner_url;
    @BindView(R.id.web_back_iv)
    LinearLayout web_back_iv;
    String mShareData;

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;

    Boolean a = true;
    private android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL;
    private android.webkit.ValueCallback<Uri> mUploadCallbackBelow;
    private Uri imageUri;
    private int REQUEST_CODE = 1234;
    private int REQUEST_CODE2 = 5678;
    private AlertDialog.Builder builder1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {

            "android.permission.READ_EXTERNAL_STORAGE",

            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adweb;
    }

//    @OnClick({ R.id.web_back_iv})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.web_back_iv:
//                finish();
//                break;
//        }
//    }

    public void show(String url) {
//        showWait(true);
        showDialog();

        WebSettings webSettings = webView.getSettings();

        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        webView.requestFocus();//触摸焦点起作用
        webView.setHorizontalScrollBarEnabled(false);// 水平不显示
        webView.setVerticalScrollBarEnabled(false); // 垂直不显示
//        webView.loadUrl("file:///android_asset/test.html");//加载asset文件夹下html
        webView.getSettings().setUserAgentString("android");
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//设置缓冲大小，我设的是8M
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String dbPath = this.getApplicationContext().getDir("database", this.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", this.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);
        webView.addJavascriptInterface(new MyJavascriptInterface(this), "android");

//        webView.loadUrl("http://192.168.2.125:9090/index?address=gauss1ufxyjhsk0f08hkyl8ap5yfhr4lvtqa2r3hv92h&themeColor=night&userName=GAUSS&language=zh-Hant");
        webView.loadUrl(url);


        webView.setWebChromeClient(new MyWebViewClient());
//        showWait(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
//                showWait(false);
                dismissDialog();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                showWait(false);
                dismissDialog();
                // 加载网页失败时处理  如：
                view.loadDataWithBaseURL(null,
                        "<div align=\"center\"><br><span style=\"color:#242424;display:block;padding-top:50px\">数据加载失败</span></div>",
                        "text/html",
                        "utf-8",
                        null);
            }
        });
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        final String[] gender = new String[]{"拍照", "相册"};
        builder1 = new AlertDialog.Builder(AdWebActivity.this);
        builder1.setTitle("请选择");
        builder1.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("", "" + gender[which]);
                if (gender[which] == "拍照") {
                    takePhoto();
                } else if (gender[which] == "相册") {
                    photo();
                }
            }
        });
        builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelCallback();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
             */
            private void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg) {
                Log.e("WangJ", "运行方法 openFileChooser-1");
                // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
                mUploadCallbackBelow = uploadMsg;
                builder1.show();
            }

            /**
             * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
             */
            public void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.e("WangJ", "运行方法 openFileChooser-2 (acceptType: " + acceptType + ")");
                // 这里我们就不区分input的参数了，直接用拍照
                openFileChooser(uploadMsg);
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
             */
            public void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("WangJ", "运行方法 openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
                // 这里我们就不区分input的参数了，直接用拍照
                openFileChooser(uploadMsg);
            }

            /**
             * API >= 21(Android 5.0.1)回调此方法
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                Log.e("WangJ", "运行方法 onShowFileChooser");
                // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = valueCallback;
//                takePhoto();
//                phtoto();
                builder1.show();
                return true;
            }
        });

    }


    public void cancelCallback() {
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
        } else if (mUploadCallbackBelow != null) {
            mUploadCallbackBelow.onReceiveValue(null);
        }
    }

    /**
     * JS调用android的方法
     *
     * @param
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void getClients2() {
        finish();
        Log.e("FDD4322", "html调用客户端:");
    }

    /**
     * Created by jingbin on 2016/11/17.
     * js通信接口
     */
    public class MyJavascriptInterface {
        private Context context;

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull @NotNull Message msg) {
                super.handleMessage(msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        };

        public MyJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void backAction() {
            ToastUtil.showShort(AdWebActivity.this, "操作成功");
            handler.sendEmptyMessage(234);
            Log.e("FDSD432", "----操作成功");
        }

        @JavascriptInterface
        public void showFeeView() {
            finish();
            Log.e("FDSD432", "----操作成功");
        }

        @JavascriptInterface
        public void onClickAndroid() {
            finish();
            Log.e("FDSD432", "----操作成功");
        }


    }

    /**
     * 调用相册
     */
    private void photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            photo();
        } else {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE2);

//            ImageSelector.builder()
//                    .useCamera(true) // 设置是否使用拍照
//                    .setSingle(false)  //设置是否单选
//                    .canPreview(true) //是否点击放大图片查看,，默认为true
//                    .setMaxSelectCount(6) // 图片的最大选择数量，小于等于0时，不限数量。
//                    .start(AdWebActivity.this, REQUEST_CODE2); // 打开相册
        }
    }

    @Override
    public void initUI() {
        webView = (WebView) findViewById(R.id.web);
        banner_url = getIntent().getStringExtra("banner_url");
//        banner_url = "http://www.baidu.com";
        Log.e("FF344545", "banner_url:" + banner_url);
        show(banner_url);
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 调用相机
     */
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
            takePhoto();
            // 指定拍照存储位置的方式调起相机


//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, REQUEST_CODE);
//
//
            // 选择图片（不包括相机拍照）,则不用成功后发刷新图库的广播
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");
//        startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE);


//        Intent Photo = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//        Intent chooserIntent = Intent.createChooser(Photo, "Image Chooser");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});


        } else {
            String filePath = Environment.getExternalStorageDirectory() + File.separator
                    + Environment.DIRECTORY_PICTURES + File.separator;
            String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            imageUri = Uri.fromFile(new File(filePath + fileName));
            Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(Intent.createChooser(captureIntent, "Image Chooser"), REQUEST_CODE);
        }


    }

    /**
     * 转换 content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE2) {
            Log.e("WangJ", "mUploadCallbackBelow:" + mUploadCallbackBelow);
            Log.e("WangJ", "mUploadCallbackAboveL:" + mUploadCallbackAboveL);
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        Log.e("WangJ", "返回调用方法--chooseBelow");
        Log.e("WangJ", "data:" + data.getData());
        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e("WangJ", "系统返回URI：" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                Log.e("WangJ", "自定义结果：" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            updatePhotos();
            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();


                ClipData clipData = data.getClipData();
                Log.e("WangJ", "uriData：" + clipData);
                if (clipData != null) {
                    int count = clipData.getItemCount();
                    Log.e("WangJ", "count：" + count);
                    if (count > 0) {
                        Uri[] uris = new Uri[count];
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            uris[i] = imageUri;
                            Log.e("WangJ", "item：" + imageUri);
                        }
                        Log.e("WangJ", "uris[0]：" + uris[0]);
                        mUploadCallbackAboveL.onReceiveValue(uris);
                    } else {
//                        mUploadCallbackAboveL.onReceiveValue(null);
                        if (uriData != null) {
                            results = new Uri[]{uriData};
                            for (Uri uri : results) {
                                Log.e("WangJ", "系统返回URI：" + uri.toString());
                            }
                            Log.e("WangJ", "系统返回URI：" + results);
                            mUploadCallbackAboveL.onReceiveValue(results);

                        } else {
                            mUploadCallbackAboveL.onReceiveValue(null);
                        }
                    }
                } else {
                    if (uriData != null) {
                        results = new Uri[]{uriData};
                        for (Uri uri : results) {
                            Log.e("WangJ", "系统返回URI：" + uri.toString());
                        }
                        Log.e("WangJ", "系统返回URI：" + results);
                        mUploadCallbackAboveL.onReceiveValue(results);

                    } else {
                        mUploadCallbackAboveL.onReceiveValue(null);
                    }
                }

//                if (uriData != null) {
//                    results = new Uri[]{uriData};
//                    for (Uri uri : results) {
//                        Log.e("WangJ", "系统返回URI：" + uri.toString());
//                    }
//                    Log.e("WangJ", "系统返回URI：" + results);
//                    mUploadCallbackAboveL.onReceiveValue(results);
//
//                } else {
//                    mUploadCallbackAboveL.onReceiveValue(null);
//                }
            } else {
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
//                showWait(false);
                dismissDialog();
            }
        }
    }


}