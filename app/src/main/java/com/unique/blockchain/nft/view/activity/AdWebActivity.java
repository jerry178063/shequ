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
        webView.requestFocus();//?????????????????????
        webView.setHorizontalScrollBarEnabled(false);// ???????????????
        webView.setVerticalScrollBarEnabled(false); // ???????????????
//        webView.loadUrl("file:///android_asset/test.html");//??????asset????????????html
        webView.getSettings().setUserAgentString("android");
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//?????????????????????????????????8M
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
                // ???????????????????????????  ??????
                view.loadDataWithBaseURL(null,
                        "<div align=\"center\"><br><span style=\"color:#242424;display:block;padding-top:50px\">??????????????????</span></div>",
                        "text/html",
                        "utf-8",
                        null);
            }
        });
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        final String[] gender = new String[]{"??????", "??????"};
        builder1 = new AlertDialog.Builder(AdWebActivity.this);
        builder1.setTitle("?????????");
        builder1.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("", "" + gender[which]);
                if (gender[which] == "??????") {
                    takePhoto();
                } else if (gender[which] == "??????") {
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
             * 8(Android 2.2) <= API <= 10(Android 2.3)???????????????
             */
            private void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg) {
                Log.e("WangJ", "???????????? openFileChooser-1");
                // (2)??????????????????????????????API < 21??????????????????????????? mUploadCallbackBelow????????? != null
                mUploadCallbackBelow = uploadMsg;
                builder1.show();
            }

            /**
             * 11(Android 3.0) <= API <= 15(Android 4.0.3)???????????????
             */
            public void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.e("WangJ", "???????????? openFileChooser-2 (acceptType: " + acceptType + ")");
                // ????????????????????????input??????????????????????????????
                openFileChooser(uploadMsg);
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)???????????????
             */
            public void openFileChooser(android.webkit.ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("WangJ", "???????????? openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
                // ????????????????????????input??????????????????????????????
                openFileChooser(uploadMsg);
            }

            /**
             * API >= 21(Android 5.0.1)???????????????
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                Log.e("WangJ", "???????????? onShowFileChooser");
                // (1)??????????????????????????????API >= 21??????????????????????????? mUploadCallbackAboveL????????? != null
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
     * JS??????android?????????
     *
     * @param
     * @return
     */
    @JavascriptInterface //??????????????????
    public void getClients2() {
        finish();
        Log.e("FDD4322", "html???????????????:");
    }

    /**
     * Created by jingbin on 2016/11/17.
     * js????????????
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
            ToastUtil.showShort(AdWebActivity.this, "????????????");
            handler.sendEmptyMessage(234);
            Log.e("FDSD432", "----????????????");
        }

        @JavascriptInterface
        public void showFeeView() {
            finish();
            Log.e("FDSD432", "----????????????");
        }

        @JavascriptInterface
        public void onClickAndroid() {
            finish();
            Log.e("FDSD432", "----????????????");
        }


    }

    /**
     * ????????????
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
//                    .useCamera(true) // ????????????????????????
//                    .setSingle(false)  //??????????????????
//                    .canPreview(true) //??????????????????????????????,????????????true
//                    .setMaxSelectCount(6) // ??????????????????????????????????????????0?????????????????????
//                    .start(AdWebActivity.this, REQUEST_CODE2); // ????????????
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
     * ????????????
     */
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
            takePhoto();
            // ?????????????????????????????????????????????


//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, REQUEST_CODE);
//
//
            // ???????????????????????????????????????,??????????????????????????????????????????
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
     * ?????? content:// uri
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
            // ????????????(1)???(2)??????????????????????????????????????????????????????????????????????????????????????????
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE2) {
            Log.e("WangJ", "mUploadCallbackBelow:" + mUploadCallbackBelow);
            Log.e("WangJ", "mUploadCallbackAboveL:" + mUploadCallbackAboveL);
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Android API < 21(Android 5.0)?????????????????????
     *
     * @param resultCode ?????????????????????????????????
     * @param data       ????????????????????????????????????
     */
    private void chooseBelow(int resultCode, Intent data) {
        Log.e("WangJ", "??????????????????--chooseBelow");
        Log.e("WangJ", "data:" + data.getData());
        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // ?????????????????????????????????
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e("WangJ", "????????????URI???" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // ??????????????????????????????????????????????????????????????????data??????
                Log.e("WangJ", "??????????????????" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) ?????????????????????
     *
     * @param resultCode ?????????????????????????????????
     * @param data       ????????????????????????????????????
     */
    private void chooseAbove(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            updatePhotos();
            if (data != null) {
                // ?????????????????????????????????????????????
                Uri[] results;
                Uri uriData = data.getData();


                ClipData clipData = data.getClipData();
                Log.e("WangJ", "uriData???" + clipData);
                if (clipData != null) {
                    int count = clipData.getItemCount();
                    Log.e("WangJ", "count???" + count);
                    if (count > 0) {
                        Uri[] uris = new Uri[count];
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            uris[i] = imageUri;
                            Log.e("WangJ", "item???" + imageUri);
                        }
                        Log.e("WangJ", "uris[0]???" + uris[0]);
                        mUploadCallbackAboveL.onReceiveValue(uris);
                    } else {
//                        mUploadCallbackAboveL.onReceiveValue(null);
                        if (uriData != null) {
                            results = new Uri[]{uriData};
                            for (Uri uri : results) {
                                Log.e("WangJ", "????????????URI???" + uri.toString());
                            }
                            Log.e("WangJ", "????????????URI???" + results);
                            mUploadCallbackAboveL.onReceiveValue(results);

                        } else {
                            mUploadCallbackAboveL.onReceiveValue(null);
                        }
                    }
                } else {
                    if (uriData != null) {
                        results = new Uri[]{uriData};
                        for (Uri uri : results) {
                            Log.e("WangJ", "????????????URI???" + uri.toString());
                        }
                        Log.e("WangJ", "????????????URI???" + results);
                        mUploadCallbackAboveL.onReceiveValue(results);

                    } else {
                        mUploadCallbackAboveL.onReceiveValue(null);
                    }
                }

//                if (uriData != null) {
//                    results = new Uri[]{uriData};
//                    for (Uri uri : results) {
//                        Log.e("WangJ", "????????????URI???" + uri.toString());
//                    }
//                    Log.e("WangJ", "????????????URI???" + results);
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
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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