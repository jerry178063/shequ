package com.unique.blockchain.nft.view.activity.me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ReplacementTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.CompanyInfoBean;
import com.unique.blockchain.nft.domain.me.SaveCompanyInfoBean;
import com.unique.blockchain.nft.domain.me.UploadImageFileBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.ShuzhiRegisterSuccessDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.CountTimeUtil;
import com.unique.blockchain.nft.infrastructure.utils.PhoneUtils;
import com.unique.blockchain.nft.infrastructure.utils.StringUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.meAdapter.GetJsonDataUtil;
import com.unique.blockchain.nft.view.activity.me.meAdapter.JsonBean;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeSaveCompanyInfoPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeSavePanyInfoPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeSaveCompanyInfoCallBack;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册数字店铺
 *
 * @author admin
 */
public class RegisterDigitalStoreActivity extends BaseActivity implements IMeSaveCompanyInfoCallBack, ShuzhiRegisterSuccessDialog.OnBackZhujiClickView {


    // 账号
    @BindView(R.id.emailName)
    EditText emailName;
    // 密码
    @BindView(R.id.password)
    EditText password;
    // 单位类型
    // 企业名称
    @BindView(R.id.companyName)
    EditText companyName;
    //社会信用代码
    @BindView(R.id.creditCode)
    EditText creditCode;
    // 企业对公银行账号
    @BindView(R.id.bankCard)
    EditText bankCard;
    // 法定代表人姓名
    @BindView(R.id.realName)
    EditText realName;
    // 身份证号
    @BindView(R.id.certificateNo)
    EditText certificateNo;
    // 通讯地址（注册地址）
    @BindView(R.id.registerAddress)
    EditText registerAddress;
    // 手机号
    @BindView(R.id.mobile)
    EditText mobile;
    //验证码
    @BindView(R.id.inputYzm)
    EditText inputYzm;

    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;


    //用户bean对象
    private User unique;
    // 用户Dao对象
    private UserDao userDao;
    @BindView(R.id.tv_choose_area)
    TextView tv_choose_area;
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private String province, city, area;
    //用户钱包地扯
    private String uniqueAdress;
    @BindView(R.id.img_add_yingyezhizhao)
    ImageView img_add_yingyezhizhao;
    @BindView(R.id.img_id_fan)
    ImageView img_id_fan;
    @BindView(R.id.img_id_zhengmian)
    ImageView img_id_zhengmian;
    private String yyzz, sfzzm, sfzfm;
    IMeSaveCompanyInfoPresenter iMeSaveCompanyInfoPresenter;
    ShuzhiRegisterSuccessDialog shuzhiRegisterSuccessDialog;
    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    int type = 0;
    private String dataStr;
    private CompanyInfoBean data;
    private CountDownTimer countDownTimer;
    @BindView(R.id.get_yzm)
    TextView get_yzm;
    @BindView(R.id.register_xianshi_yincang)
    ImageView register_xianshi_yincang;
    private boolean isPswVisible1;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.lin_qiyeyinhang)
    LinearLayout lin_qiyeyinhang;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_digital_store;
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
        // 初始化视图
        initView();
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    register_xianshi_yincang.setVisibility(View.GONE);
                } else {
                    register_xianshi_yincang.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        creditCode.setTransformationMethod(new ReplacementTransformationMethod() {
            @Override
            protected char[] getOriginal() {
                char[] originalCharArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
                return originalCharArr;
            }

            @Override
            protected char[] getReplacement() {
                char[] replacementCharArr = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
                return replacementCharArr;
            }
        });
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

    @Override
    public void bindView(Bundle bundle) {

    }


    /**
     * 初始化视图
     */
    private void initView() {

        // 从本地数据库中获取用户钱包地扯
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(RegisterDigitalStoreActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF333", "gaussAdress: " + uniqueAdress);
                }
            }
        }

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

    @OnClick({R.id.img_back, R.id.tv_choose_area, R.id.img_add_yingyezhizhao, R.id.img_id_fan, R.id.img_id_zhengmian, R.id.btnSubmit, R.id.radioButton1, R.id.radioButton2
            , R.id.get_yzm, R.id.lin_register_xianshi_yincang})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_choose_area://选择所在地区
                showPickerView();
                break;
            case R.id.lin_register_xianshi_yincang:
                isPswVisible1 = !isPswVisible1;
                if (isPswVisible1) {
                    //设置图片，隐藏或者显示图片
                    register_xianshi_yincang.setImageResource(R.mipmap.xianshi_new);
                    //显示密码方法一
                    HideReturnsTransformationMethod method2 = HideReturnsTransformationMethod.getInstance();
                    password.setTransformationMethod(method2);
                } else {
                    //设置图片，隐藏或者显示图片
                    register_xianshi_yincang.setImageResource(R.mipmap.yincang_new);
                    //隐藏密码方法一
                    PasswordTransformationMethod method1 = PasswordTransformationMethod.getInstance();
                    password.setTransformationMethod(method1);
                }
                //切换后将EditText光标置于末尾
                password.setSelection(password.getText().toString().length());
                break;
            case R.id.img_add_yingyezhizhao://营业执照
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);

                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(RegisterDigitalStoreActivity.this, 1); // 打开相册
                break;
            case R.id.img_id_fan:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(RegisterDigitalStoreActivity.this, 2); // 打开相册
                break;
            case R.id.img_id_zhengmian://身份证正面
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(RegisterDigitalStoreActivity.this, 3); // 打开相册
                break;
            case R.id.btnSubmit://提交注册
                register();
                break;
            case R.id.radioButton1:
                type = 0;
                tv_company_name.setText("企业名称");
                lin_qiyeyinhang.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButton2:
                type = 1;
                tv_company_name.setText("个体户名称");
                lin_qiyeyinhang.setVisibility(View.GONE);
                break;
            case R.id.get_yzm://获取验证码
                if (TextUtils.isEmpty(mobile.getText().toString())) {
                    ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的手机号码!");
                    return;
                }
                boolean isPhoneNum = PhoneUtils.isMobileNO(mobile.getText().toString());
                if (!isPhoneNum) {
                    mobile.setText("");
                    ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入有效的手机号码!");
                    return;
                }
                if (countDownTimer == null) {
                    countDownTimer = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if(get_yzm != null) {
                                get_yzm.setText("剩余 " + millisUntilFinished / 1000 + " 秒");
                                get_yzm.setClickable(false);
                            }
                        }

                        @Override
                        public void onFinish() {
                            if(get_yzm != null) {
                                get_yzm.setText("获取验证码");
                                get_yzm.setClickable(true);
                            }
                        }
                    };
                    countDownTimer.start();
                } else {
                    countDownTimer.start();
                }
                break;
        }
    }

    private void register() {
        if (TextUtils.isEmpty(emailName.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入邮箱地址!");
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的密码!");
            return;
        }
        if (!TextUtils.isEmpty(password.getText().toString())) {
            if (password.getText().toString().length() < 6 || password.getText().toString().length() > 15) {
                ToastUtils.showToast(this, "密码为6-15位数字、字母、特殊字符或组合");
                return;
            }
        } else {
            ToastUtils.showToast(this, "密码为6-15位数字、字母、特殊字符或组合");
            return;
        }
        if (TextUtils.isEmpty(companyName.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入企业名称!");
            return;
        }
        if(companyName.getText().toString().length() < 2){
            if(type == 0) {
                ToastUtil.showShort(RegisterDigitalStoreActivity.this, "企业名称必须大于等于2个字符长度!");
            }else if(type == 1){
                ToastUtil.showShort(RegisterDigitalStoreActivity.this, "个体户名称必须大于等于2个字符长度!");
            }
                return;
        }
        if (TextUtils.isEmpty(creditCode.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入社会信用代码(注册号)!");
            return;
        }
        if(type == 0) {
            if (TextUtils.isEmpty(bankCard.getText().toString())) {
                ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的企业银行对公账号!");
                return;
            }
        }
        if (TextUtils.isEmpty(yyzz)) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请选择营业执照!");
            return;
        }
        if (tv_choose_area.getText().toString().equals("选择所在地区")) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "选择所在地区!");
            return;
        }

        if (TextUtils.isEmpty(realName.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的姓名!");
            return;
        }
        if (TextUtils.isEmpty(certificateNo.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的身份证号!");
            return;
        }
        if (TextUtils.isEmpty(sfzzm)) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请选择身份证正面!");
            return;
        }
        if (TextUtils.isEmpty(sfzfm)) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请选择身份证反面!");
            return;
        }
        if (TextUtils.isEmpty(registerAddress.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的通讯地址!");
            return;
        }
        if (TextUtils.isEmpty(mobile.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的手机号码!");
            return;
        }
        if (TextUtils.isEmpty(inputYzm.getText().toString())) {
            ToastUtil.showShort(RegisterDigitalStoreActivity.this, "请输入您的验证码!");
            return;
        }

        if (iMeSaveCompanyInfoPresenter == null) {
            iMeSaveCompanyInfoPresenter = new IMeSavePanyInfoPresenterImpl();
            iMeSaveCompanyInfoPresenter.registerViewCallback(this);
        }
        SaveCompanyInfoBean saveCompanyInfoBean = new SaveCompanyInfoBean();
        saveCompanyInfoBean.setAccountName(emailName.getText().toString());//邮箱地址
        saveCompanyInfoBean.setPassword(password.getText().toString());//密码
        saveCompanyInfoBean.setType(type);//单位类型
        saveCompanyInfoBean.setCompanyName(companyName.getText().toString());
        saveCompanyInfoBean.setCreditCode(creditCode.getText().toString());
        saveCompanyInfoBean.setBankCard(bankCard.getText().toString());
        saveCompanyInfoBean.setBusinessLicence(yyzz);
        saveCompanyInfoBean.setHomeLocation(province + "," + city + "," + area);
        saveCompanyInfoBean.setArtificialPerson(realName.getText().toString());
        saveCompanyInfoBean.setCertificateNo(certificateNo.getText().toString());
        saveCompanyInfoBean.setCertificateFront(sfzzm);
        saveCompanyInfoBean.setCertificateBack(sfzfm);
        saveCompanyInfoBean.setAddress(registerAddress.getText().toString());
        saveCompanyInfoBean.setMobile(mobile.getText().toString());
        saveCompanyInfoBean.setAddress(registerAddress.getText().toString());
        saveCompanyInfoBean.setWalletAddr(uniqueAdress);
        Log.e("FFF3333", "请求数据:" + saveCompanyInfoBean.toString());
        iMeSaveCompanyInfoPresenter.getData(saveCompanyInfoBean.toString());
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
                                Glide.with(RegisterDigitalStoreActivity.this).load(yyzz).into(img_add_yingyezhizhao);
                            } else if (pisition == 2) {
                                sfzzm = uploadImageFileBean.getData().getUrl();
                                Glide.with(RegisterDigitalStoreActivity.this).load(sfzzm).into(img_id_fan);
                            } else if (pisition == 3) {
                                sfzfm = uploadImageFileBean.getData().getUrl();
                                Glide.with(RegisterDigitalStoreActivity.this).load(sfzfm).into(img_id_zhengmian);
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

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tv_choose_area.setText(options1Items.get(options1).getPickerViewText() + "  "
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(3000);
                shuzhiRegisterSuccessDialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    };

    //提交信息返回结果
    @Override
    public void loadSaveCompanyInfoPostData(CollectionBean companyInfoBean) {
        Log.e("FFF3333", "companyInfoBean:" + companyInfoBean);
        if (companyInfoBean.getCode() == 200) {
            if (shuzhiRegisterSuccessDialog == null) {
                shuzhiRegisterSuccessDialog = new ShuzhiRegisterSuccessDialog(this);
                shuzhiRegisterSuccessDialog.setOnclick(this);
                shuzhiRegisterSuccessDialog.show();
            }
            handler.sendEmptyMessage(100);
        } else {
            ToastUtil.showShort(this, companyInfoBean.getMsg());
        }
    }

    @Override
    public void loadSaveCompanyInfoPostFail() {

    }

    @Override
    public void setBackZhujiClickView(String et_pass) {

    }
}