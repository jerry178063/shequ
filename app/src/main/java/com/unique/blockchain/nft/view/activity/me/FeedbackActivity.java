package com.unique.blockchain.nft.view.activity.me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ImagePickerFeedAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.UploadImageFileBean;
import com.unique.blockchain.nft.infrastructure.dialog.SelectDialog;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by panjunquan on 2018/3/4.
 * 意见反馈
 */

public class FeedbackActivity extends BaseActivity  implements ImagePickerFeedAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.submit_tv)
    TextView submitTv;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImagePickerFeedAdapter mAdapter;
    private int maxImgCount = 1;               //允许选择图片最大数

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<String> selImageList; //当前选择的所有图片
    private String loadUrl;
    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            Log.e("ImageSelector", "是否是拍照图片：" + isCameraImage);
            Log.e("ImageSelector", "images：" + images);

            if(selImageList != null){
                selImageList.clear();
            }

            if(selImageList != null && selImageList.size() >= 6){
                ToastUtils.showToast(FeedbackActivity.this, "最多添加6张照片");
                return;
            }

            if(isCameraImage){//拍照
                if (images != null && images.size() > 0) {
                    selImageList.addAll(images);
                    mAdapter.setImages(selImageList);
                    Log.e("FFF3333", "IMAGE_1:" + new Gson().toJson(images));
                    sendData(images.get(0));
                }
            }else {//相册
                if (images != null && images.size() > 0) {
                    selImageList.addAll(images);
                    mAdapter.setImages(selImageList);
                    Log.e("FFF3333", "IMAGE_2:" + new Gson().toJson(images));
                    for (int i = 0; i < selImageList.size(); i++){
                        sendData(images.get(i));
                    }

                }
            }
        }
    }


    @Override
    public void initUI() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        selImageList = new ArrayList<>();
        mAdapter = new ImagePickerFeedAdapter(this, selImageList, maxImgCount);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(contentEt.getText())) {

                } else {
//                    ToastUtils.getInstance("请留下您的意见");
                }
            }
        });
        top_s_title_toolbar.setLeftTitleText("意见反馈");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void sendData(String path) {
        Log.e("FFF3333", "Authorization:" + MyApplication.mAuthorization);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        OkGo.post(UrlConstant.baseCreateInportWallet +"common/upload")
                .addFileParams("file",fileList)
//                .params("file",new File(path))
                .headers("Authorization", MyApplication.mAuthorization)
                .execute(new JsonCallback<UploadImageFileBean>() {
                    @Override
                    public void onSuccess(UploadImageFileBean uploadImageFileBean, Call call, Response response) {
                        //请求成功
                        if(uploadImageFileBean != null) {
                            Log.e("FFF3333", "uploadImageFileBean_feek:" + uploadImageFileBean);
//                            loadUrl = uploadImageFileBean.getUrl();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF3333", "onFailure_feek:" + code);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF3333", "onError:" + response.body() + "e:" + e);
                    }
                });
    }
    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }
    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //预加载手机图片
                ImageSelector.preload(this);
            } else {
                //拒绝权限。
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e("FFLLL5","position:" + position);
//        switch (position){
//            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                ImageSelector.builder()
                                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                                        .start(FeedbackActivity.this, REQUEST_CODE);
                                Log.e("FFLLL5","直接调起相机:");
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImageSelector.builder()
                                        .useCamera(true) // 设置是否使用拍照
                                        .setSingle(false)  //设置是否单选
                                        .canPreview(true) //是否点击放大图片查看,，默认为true
                                        .setMaxSelectCount(6) // 图片的最大选择数量，小于等于0时，不限数量。
                                        .start(FeedbackActivity.this, REQUEST_CODE); // 打开相册
                                break;
                        }

                    }
                }, names);

//                break;
//        }
    }
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
}
