package com.unique.blockchain.nft.view.fragment.mark;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SpUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.view.activity.AdWebActivity;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class JianFragment extends BaseFragment {


    @BindView(R.id.web)
    WebView webView;
    String banner_url;
    @BindView(R.id.web_back_iv)
    LinearLayout web_back_iv;
    String mShareData;

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.lin_title)
    LinearLayout lin_title;

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

    @Override
    public void initView() {
//        banner_url =  getActivity().getIntent().getStringExtra("banner_url");
        if (SpUtil.getInstance(getContext()).getString("metadataHash") != null) {
            banner_url = UrlConstant.baseWap + "browser/ipfs?key=" + SpUtil.getInstance(getContext()).getString("metadataHash");
            Log.e("FF344545", "banner_url:" + banner_url);
        }
        lin_title.setVisibility(View.GONE);
        show(banner_url);
        web_back_iv.setVisibility(View.GONE);
        top_s_title_toolbar.setLeftTitleText("");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @OnClick({R.id.web_back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_back_iv:
                getActivity().finish();
                break;
        }
    }

    public void show(String url) {
//        showWait(true);
        showDialog();
        webView = (WebView) findViewById(R.id.web);
        WebSettings webSettings = webView.getSettings();

        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        webView.getSettings().setUserAgentString("android");

        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//?????????????????????????????????8M
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String dbPath = getActivity().getApplicationContext().getDir("database", getActivity().MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        webSettings.setAppCacheEnabled(true);
        String appCaceDir = getActivity().getApplicationContext().getDir("cache", getActivity().MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCaceDir);

        webView.requestFocus();//?????????????????????
        webView.setHorizontalScrollBarEnabled(false);// ???????????????
        webView.setVerticalScrollBarEnabled(false); // ???????????????
        webView.loadUrl(url);

        webView.setWebChromeClient(new MyWebViewClient());
//        showWait(true);
        webView.setWebViewClient(new WebViewClient() {
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
        builder1 = new AlertDialog.Builder(getActivity());
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
     * ????????????
     */
    private void photo() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            photo();
        } else {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE2);
        }
    }

    /**
     * ????????????
     */
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // ????????????(1)???(2)??????????????????????????????????????????????????????????????????????????????????????????
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE2) {
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
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
        Log.e("WangJ", "??????????????????--chooseAbove");

        if (RESULT_OK == resultCode) {
            updatePhotos();
            if (data != null) {
                // ?????????????????????????????????????????????
                Uri[] results;
                Uri uriData = data.getData();
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
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void initData() {

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