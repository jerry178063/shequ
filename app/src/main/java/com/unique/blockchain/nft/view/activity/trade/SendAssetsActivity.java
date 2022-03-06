package com.unique.blockchain.nft.view.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ImagePickerAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.me.UploadImageFileBean;
import com.unique.blockchain.nft.infrastructure.dialog.AddtionalAssetsDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SafeAdminDialog;
import com.unique.blockchain.nft.infrastructure.dialog.SelectDialog;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 发行资产
 */
public class SendAssetsActivity extends BaseActivity implements AddtionalAssetsDialog.OnClickView, SafeAdminDialog.OnSafeClickView, ImagePickerAdapter.OnRecyclerViewItemClickListener {


    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    AddtionalAssetsDialog addtionalAssetsDialog;
    SafeAdminDialog safeAdminDialog;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private int maxImgCount = 1;               //允许选择图片最大数


    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private String gaussAdass;
    @BindView(R.id.et_gauss_adress)
    EditText et_gauss_adress;
    private String loadUrl;
    @BindView(R.id.ed_assets_name)
    EditText ed_assets_name;//资产名称
    @BindView(R.id.et_assets_quit)
    EditText et_assets_quit;//资产单位
    @BindView(R.id.et_min_assets_quit)
    EditText et_min_assets_quit;//最小资产单位
    @BindView(R.id.et_num_weishu)
    EditText et_num_weishu;//小数位数
    @BindView(R.id.et_start_num)
    EditText et_start_num;//初始发行量
    @BindView(R.id.et_max_num)
    EditText et_max_num;//最大发行量
    @BindView(R.id.et_guanwang_address)
    EditText et_guanwang_address;//官网地址
    @BindView(R.id.et_net_email)
    EditText et_net_email;//官网邮箱
    @BindView(R.id.chekcBox_assets)
    CheckBox chekcBox_assets;//锁定资产
    @BindView(R.id.et_input_deail)
    EditText et_input_deail;//请输入简介描述


    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_assets;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("发行资产");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gaussAdass = getIntent().getStringExtra("gaussAdass");
        et_gauss_adress.setText(gaussAdass + "");
        initWidget();
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @OnClick({R.id.tv_send_assets})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_send_assets://发行资产

                if(TextUtils.isEmpty(loadUrl)){
                    ToastUtils.showToast(this, "请添加资产图标");
                    return;
                }
                if(TextUtils.isEmpty(ed_assets_name.getText().toString())){
                    ToastUtils.showToast(this,"请输入资产名称");
                    return;
                }
                if(TextUtils.isEmpty(et_assets_quit.getText().toString())){
                    ToastUtils.showToast(this,"请输入资产单位");
                    return;
                }
                if(TextUtils.isEmpty(et_min_assets_quit.getText().toString())){
                    ToastUtils.showToast(this,"请输入最小资产单位");
                    return;
                }
                if(TextUtils.isEmpty(et_num_weishu.getText().toString())){
                    ToastUtils.showToast(this,"请输入数位数");
                    return;
                }
                if(TextUtils.isEmpty(et_start_num.getText().toString())){
                    ToastUtils.showToast(this,"请输入初始发行量");
                    return;
                }
                if(TextUtils.isEmpty(et_max_num.getText().toString())){
                    ToastUtils.showToast(this,"请输入最大发行量");
                    return;
                }
                if(TextUtils.isEmpty(et_guanwang_address.getText().toString())){
                    ToastUtils.showToast(this,"请输入官网地址");
                    return;
                }
                if(TextUtils.isEmpty(et_net_email.getText().toString())){
                    ToastUtils.showToast(this,"请输入官网邮箱");
                    return;
                }

                if(TextUtils.isEmpty(et_input_deail.getText().toString())){
                    ToastUtils.showToast(this,"请输入简介描述");
                    return;
                }


                if (addtionalAssetsDialog == null) {
                    addtionalAssetsDialog = new AddtionalAssetsDialog(this);
                    addtionalAssetsDialog.setOnclick(this);
                    addtionalAssetsDialog.show();
                } else {
                    addtionalAssetsDialog.show();
                }
                addtionalAssetsDialog.tv_can_gauss.setText("可用GPB: " + 1234);
                addtionalAssetsDialog.tv_title_dialog.setText("发行资产");

                break;
        }

    }

    @Override
    public void setOnClickView() {
        addtionalAssetsDialog.dismiss();

        if (safeAdminDialog == null) {
            safeAdminDialog = new SafeAdminDialog(this);
            safeAdminDialog.setOnclick(this);
            safeAdminDialog.show();
        } else {
            safeAdminDialog.show();
        }
    }

    @Override
    public void setOnSafeClickView(String pass) {
        safeAdminDialog.dismiss();
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

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
//                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
//                            case 0: // 直接调起相机
//                                /**
//                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
//                                 *
//                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
//                                 *
//                                 * 如果实在有所需要，请直接下载源码引用。
//                                 */
//                                //打开选择,本次允许选择的数量
//
//
//                                    ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                                    Intent intent = new Intent(SendAssetsActivity.this, ImageGridActivity.class);
//                                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
//                                    startActivityForResult(intent, REQUEST_CODE_SELECT);
//                                break;
                            case 0:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(SendAssetsActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                //添加图片返回
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null) {
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                        Log.e("FFF3333", "IMAGE_1:" + new Gson().toJson(images));
                        sendData(images.get(0).path);
                    }
                }
            } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                //预览图片返回
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        selImageList.clear();
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                        Log.e("FFF3333", "IMAGE_2:" + images.size());



                    }
                }
            }
        } catch (Exception e) {
            Log.e("FFF555", "E2:" + e);
        }
    }

    private void sendData(String path) {
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        OkGo.post(UrlConstant.baseUrlFile +"file/upload")
                .addFileParams("file",fileList)
//                .params("file",new File(path))
                .execute(new JsonCallback<UploadImageFileBean>() {
                    @Override
                    public void onSuccess(UploadImageFileBean uploadImageFileBean, Call call, Response response) {
                        //请求成功
                        if(uploadImageFileBean != null) {
                            Log.e("FFF3333", "uploadImageFileBean:" + uploadImageFileBean);
//                            loadUrl = uploadImageFileBean.getUrl();
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
