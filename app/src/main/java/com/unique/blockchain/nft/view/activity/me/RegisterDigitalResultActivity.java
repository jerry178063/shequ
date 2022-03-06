package com.unique.blockchain.nft.view.activity.me;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.domain.me.UploadImageFileBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.ChooseUnitTypeDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.ToastUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.meAdapter.GetJsonDataUtil;
import com.unique.blockchain.nft.view.activity.me.meAdapter.JsonBean;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAgainCommitPresenter;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAgainCommitCallBack;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 数字认证注册结果
 */
public class RegisterDigitalResultActivity extends BaseActivity implements IMeAgainCommitCallBack, ChooseUnitTypeDialog.OnChooseTypeClickView {

    private String dataStr;
    private CompanyInfoBean data;
    @BindView(R.id.tv_register_status)
    TextView tv_register_status;
    @BindView(R.id.tv_company_type)
    TextView tv_company_type;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_shehuidaima)
    TextView tv_shehuidaima;
    @BindView(R.id.img_yyzz)
    ImageView img_yyzz;
    @BindView(R.id.tv_address_fading)
    TextView tv_address_fading;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_card_id)
    TextView tv_card_id;
    @BindView(R.id.tv_tongxun_address)
    TextView tv_tongxun_address;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.img_id_zhengmian)
    ImageView img_id_zhengmian;
    @BindView(R.id.img_id_fan)
    ImageView img_id_fan;
    @BindView(R.id.lin_login_account)
    LinearLayout lin_login_account;
    @BindView(R.id.tv_login_account)
    TextView tv_login_account;
    @BindView(R.id.tv_load_xiazai)
    TextView tv_load_xiazai;
    @BindView(R.id.lin_shenhe_shibai)
    LinearLayout lin_shenhe_shibai;
    @BindView(R.id.tv_youxiaoqi)
    TextView tv_youxiaoqi;
    IMeAgainCommitPresenter iMeAgainCommitPresenter;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    @BindView(R.id.img_copy)
    ImageView img_copy;
    @BindView(R.id.tv_shenhe_status)
    TextView tv_shenhe_status;
    private String yyzz, sfzzm, sfzfm;
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private String province, city, area;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    ChooseUnitTypeDialog chooseUnitTypeDialog;
    String type = "";//企业类型,0,1
    @BindView(R.id.tv_detail_resean)
    TextView tv_detail_resean;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_digital_result;
    }

    @Override
    public void initUI() {
        if (Build.VERSION.SDK_INT >= 21) {//沉浸式状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322", "gaussAdress:" + uniqueAdress);
                }
            }
        }
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
        dataStr = getIntent().getStringExtra("dataStr");
        data = new Gson().fromJson(dataStr, CompanyInfoBean.class);
        if (data != null && data.getCode() == 200) {
            if (data.getData().getApprovalState() == 0) {
                tv_shenhe_status.setText("审核状态:");
                tv_register_status.setText("审核中");
                lin_login_account.setVisibility(View.GONE);
                lin_shenhe_shibai.setVisibility(View.GONE);

                tv_company_type.setEnabled(false);
                tv_company_name.setEnabled(false);
                tv_shehuidaima.setEnabled(false);
                tv_address_fading.setEnabled(false);
                tv_name.setEnabled(false);
                tv_card_id.setEnabled(false);
                tv_tongxun_address.setEnabled(false);
                tv_phone.setEnabled(false);
                img_yyzz.setEnabled(false);
                img_id_zhengmian.setEnabled(false);
                img_id_fan.setEnabled(false);
                tv_register_status.setTextColor(getResources().getColor(R.color.color_F79A4F));
                tv_title.setText("铸造NFT");
            } else if (data.getData().getApprovalState() == 1) {
                tv_shenhe_status.setText("审核状态:");
                tv_register_status.setText("审核失败");
                lin_login_account.setVisibility(View.GONE);
                lin_shenhe_shibai.setVisibility(View.VISIBLE);

                tv_company_type.setEnabled(false);
                tv_company_name.setEnabled(false);
                tv_shehuidaima.setEnabled(false);
                tv_address_fading.setEnabled(false);
                tv_name.setEnabled(false);
                tv_card_id.setEnabled(false);
                tv_tongxun_address.setEnabled(false);
                tv_phone.setEnabled(false);
                img_yyzz.setEnabled(false);
                img_id_zhengmian.setEnabled(false);
                img_id_fan.setEnabled(false);
                tv_register_status.setTextColor(getResources().getColor(R.color.color_DE8A8A));

                tv_detail_resean.setText(data.getData().getApprovalRemark());
                tv_title.setText("铸造NFT");
            } else if (data.getData().getApprovalState() == 2) {
                tv_shenhe_status.setText("审核状态:");
                tv_register_status.setText("审核通过");
                tv_register_status.setTextColor(getResources().getColor(R.color.color_7BBB5E));
                lin_login_account.setVisibility(View.VISIBLE);
                lin_shenhe_shibai.setVisibility(View.GONE);
                tv_login_account.setText(data.getData().getAccountName());
                tv_load_xiazai.setText(data.getData().getLoginUrl());

                tv_company_type.setEnabled(false);
                tv_company_name.setEnabled(false);
                tv_shehuidaima.setEnabled(false);
                tv_address_fading.setEnabled(false);
                tv_name.setEnabled(false);
                tv_card_id.setEnabled(false);
                tv_tongxun_address.setEnabled(false);
                tv_phone.setEnabled(false);
                img_yyzz.setEnabled(false);
                img_id_zhengmian.setEnabled(false);
                img_id_fan.setEnabled(false);
                tv_title.setText("铸造NFT");
                tv_time.setText("*有效期: " + data.getData().getEndTime());
            } else if (data.getData().getApprovalState() == 3) {
                tv_shenhe_status.setText("状态:");
                tv_register_status.setText("已过期");
                tv_register_status.setTextColor(getResources().getColor(R.color.color_D86D6D));
                lin_login_account.setVisibility(View.VISIBLE);
                lin_shenhe_shibai.setVisibility(View.GONE);
                tv_login_account.setText(data.getData().getAccountName());
                tv_load_xiazai.setText(data.getData().getLoginUrl());
                tv_youxiaoqi.setText("*有效期: " + data.getData().getEndTime());
                tv_youxiaoqi.setTextColor(getResources().getColor(R.color.color_FFF906));

                tv_company_type.setEnabled(false);
                tv_company_name.setEnabled(false);
                tv_shehuidaima.setEnabled(false);
                tv_address_fading.setEnabled(false);
                tv_name.setEnabled(false);
                tv_card_id.setEnabled(false);
                tv_tongxun_address.setEnabled(false);
                tv_phone.setEnabled(false);
                img_yyzz.setEnabled(false);
                img_id_zhengmian.setEnabled(false);
                img_id_fan.setEnabled(false);
                tv_title.setText("铸造NFT");
            }else {
                tv_title.setText("数字店铺");
            }
            if (data.getData().getType() == 0) {
                type = "0";
                tv_company_type.setText("企业");
            } else if (data.getData().getType() == 1) {
                tv_company_type.setText("个体工商用户");
                type = "1";
            }
            tv_company_name.setText(data.getData().getCompanyName());
            tv_shehuidaima.setText(data.getData().getCreditCode());
            Glide.with(this).load(data.getData().getBusinessLicence()).into(img_yyzz);
            tv_address_fading.setText(data.getData().getHomeLocation());
            tv_name.setText(data.getData().getArtificialPerson());
            tv_card_id.setText(data.getData().getCertificateNo());
            tv_tongxun_address.setText(data.getData().getAddress());
            tv_phone.setText(data.getData().getMobile());
            Glide.with(this).load(data.getData().getCertificateFront()).into(img_id_zhengmian);
            Glide.with(this).load(data.getData().getCertificateBack()).into(img_id_fan);


        }else {
            tv_title.setText("数字店铺");
        }
        tv_load_xiazai.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        Log.e("FF322", "data:" + new Gson().toJson(data));
    }

    @Override
    public void initData() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            options2Items.add(CityList);
            //添加地区数据
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @OnClick({R.id.img_back, R.id.btnSubmit2, R.id.lin_copy, R.id.img_yyzz, R.id.img_id_zhengmian, R.id.img_id_fan, R.id.tv_address_fading
            , R.id.tv_company_type})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.btnSubmit2://重新提交审核
                Intent intent = new Intent(RegisterDigitalResultActivity.this, RegisterAgainDigitalStoreActivity.class);
                intent.putExtra("dataStr", dataStr);
                startActivity(intent);
                finish();
//                if(iMeAgainCommitPresenter == null){
//                    iMeAgainCommitPresenter = new IMeAgainCommitPresenterImpl();
//                    iMeAgainCommitPresenter.registerViewCallback(this);
//                }
//                UpdateCompanyBean updateCompanyBean = new UpdateCompanyBean();
//                updateCompanyBean.setAccountName(data.getData().getAccountName());//邮箱地址
//                updateCompanyBean.setType(type);//设置单位类型
//                updateCompanyBean.setCompanyName(tv_company_name.getText().toString());//企业名称
//                updateCompanyBean.setCreditCode(tv_shehuidaima.getText().toString());//社会信用代码
//                updateCompanyBean.setBusinessLicence(yyzz);//营业执照
//                updateCompanyBean.setHomeLocation(tv_address_fading.getText().toString());//归属地
//                updateCompanyBean.setArtificialPerson(tv_name.getText().toString());//姓名
//                updateCompanyBean.setCertificateNo(tv_card_id.getText().toString());//身份证号
//                updateCompanyBean.setAddress(tv_tongxun_address.getText().toString());//通讯地址
//                updateCompanyBean.setMobile(tv_phone.getText().toString());//手机号
//                updateCompanyBean.setCertificateFront(sfzzm);//身份证正面
//                updateCompanyBean.setCertificateBack(sfzfm);//身份证反面
//
//                iMeAgainCommitPresenter.getData(updateCompanyBean);
                break;
            case R.id.lin_copy://复制

                copyContentToClipboard(tv_load_xiazai.getText().toString() + "", RegisterDigitalResultActivity.this);
                ToastUtils.getInstance(RegisterDigitalResultActivity.this).show("复制成功!");
                break;
            case R.id.img_yyzz://营业执照
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(RegisterDigitalResultActivity.this, 1); // 打开相册
                break;
            case R.id.img_id_zhengmian://身份证正面
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(RegisterDigitalResultActivity.this, 3); // 打开相册
                break;
            case R.id.img_id_fan://身份证反面
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(RegisterDigitalResultActivity.this, 2); // 打开相册
                break;
            case R.id.tv_address_fading://法定代表归属地
                showPickerView();
                break;
//            case R.id.tv_company_type://选择单位类型
//                if(chooseUnitTypeDialog == null){
//                    chooseUnitTypeDialog = new ChooseUnitTypeDialog(this);
//                    chooseUnitTypeDialog.setOnclick(this);
//                }
//                chooseUnitTypeDialog.show();
//                break;
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tv_address_fading.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);
            }
        })
                .setTitleText("城市选择")
                .setTitleBgColor(Color.WHITE)//设置标题的背景颜色
                .setDividerColor(Color.BLACK)//设置分割线的颜色
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            if (requestCode == 1) {
                Uri data1 = data.getData();

                if (images != null && images.size() > 0) {
                    Log.e("FFF3333", "IMAGE_2:" + new Gson().toJson(images));
                    for (int i = 0; i < images.size(); i++) {
                        loadFile(images.get(i), 1);
                    }

                }

            } else if (requestCode == 2) {
                if (images != null && images.size() > 0) {
                    Log.e("FFF3333", "IMAGE_2:" + new Gson().toJson(images));
                    for (int i = 0; i < images.size(); i++) {
                        loadFile(images.get(i), 2);
                    }

                }
            } else if (requestCode == 3) {
                if (images != null && images.size() > 0) {
                    Log.e("FFF3333", "IMAGE_2:" + new Gson().toJson(images));
                    for (int i = 0; i < images.size(); i++) {
                        loadFile(images.get(i), 3);
                    }

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void loadFile(String path, int pisition) {
        Log.e("FFF3333", "path:" + path);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        OkGo.post(UrlConstant.baseUrlFile + "file/upload")
                .addFileParams("file", fileList)
//                .params("file",new File(path))
//                .headers("Authorization", MyApplication.mAuthorization)
                .execute(new JsonCallback<UploadImageFileBean>() {
                    @Override
                    public void onSuccess(UploadImageFileBean uploadImageFileBean, Call call, Response response) {
                        //请求成功
                        if (uploadImageFileBean != null) {
                            Log.e("FFF3333", "uploadImageFileBean_feek:" + uploadImageFileBean);
                            if (pisition == 1) {
                                yyzz = uploadImageFileBean.getData().getUrl();
                                Glide.with(RegisterDigitalResultActivity.this).load(yyzz).into(img_yyzz);
                            } else if (pisition == 2) {
                                sfzfm = uploadImageFileBean.getData().getUrl();
                                Glide.with(RegisterDigitalResultActivity.this).load(sfzfm).into(img_id_fan);
                            } else if (pisition == 3) {
                                sfzzm = uploadImageFileBean.getData().getUrl();
                                Glide.with(RegisterDigitalResultActivity.this).load(sfzzm).into(img_id_zhengmian);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF3333", "onFailure_feek:" + code + "mess:" + message);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FFF3333", "onError:" + response.body() + "e:" + e);
                    }
                });
    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public void loadAgainCommitPostData(CollectionBean collectionBean) {
        if (collectionBean != null && collectionBean.getCode() == 200) {
            ToastUtil.showShort(RegisterDigitalResultActivity.this, "请求成功!");
            tv_register_status.setText("审核中");
            lin_shenhe_shibai.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadAgainCommitPostFail() {

    }

    @Override
    public void setChooseTypeClickView(String types) {
        if (types != null) {
            if (types.equals("0")) {
                Log.e("FF2344", "types:" + types);
                type = types;
                tv_company_type.setText("企业");
            } else if (types.equals("1")) {
                Log.e("FF2344", "types:" + types);
                type = types;
                tv_company_type.setText("个体工商户");
            }
        }
    }
}
