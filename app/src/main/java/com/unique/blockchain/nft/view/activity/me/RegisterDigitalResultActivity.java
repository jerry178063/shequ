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
 * ????????????????????????
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
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //???
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//???
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//???
    private String province, city, area;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    ChooseUnitTypeDialog chooseUnitTypeDialog;
    String type = "";//????????????,0,1
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
        if (Build.VERSION.SDK_INT >= 21) {//??????????????????
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
            //???????????????????????????????????????????????????app????????????????????????
            ImageSelector.preload(this);
        } else {
            //??????????????????????????????
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
        dataStr = getIntent().getStringExtra("dataStr");
        data = new Gson().fromJson(dataStr, CompanyInfoBean.class);
        if (data != null && data.getCode() == 200) {
            if (data.getData().getApprovalState() == 0) {
                tv_shenhe_status.setText("????????????:");
                tv_register_status.setText("?????????");
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
                tv_title.setText("??????NFT");
            } else if (data.getData().getApprovalState() == 1) {
                tv_shenhe_status.setText("????????????:");
                tv_register_status.setText("????????????");
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
                tv_title.setText("??????NFT");
            } else if (data.getData().getApprovalState() == 2) {
                tv_shenhe_status.setText("????????????:");
                tv_register_status.setText("????????????");
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
                tv_title.setText("??????NFT");
                tv_time.setText("*?????????: " + data.getData().getEndTime());
            } else if (data.getData().getApprovalState() == 3) {
                tv_shenhe_status.setText("??????:");
                tv_register_status.setText("?????????");
                tv_register_status.setTextColor(getResources().getColor(R.color.color_D86D6D));
                lin_login_account.setVisibility(View.VISIBLE);
                lin_shenhe_shibai.setVisibility(View.GONE);
                tv_login_account.setText(data.getData().getAccountName());
                tv_load_xiazai.setText(data.getData().getLoginUrl());
                tv_youxiaoqi.setText("*?????????: " + data.getData().getEndTime());
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
                tv_title.setText("??????NFT");
            }else {
                tv_title.setText("????????????");
            }
            if (data.getData().getType() == 0) {
                type = "0";
                tv_company_type.setText("??????");
            } else if (data.getData().getType() == 1) {
                tv_company_type.setText("??????????????????");
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
            tv_title.setText("????????????");
        }
        tv_load_xiazai.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        Log.e("FF322", "data:" + new Gson().toJson(data));
    }

    @Override
    public void initData() {
        /**
         * ?????????assets ????????????Json??????????????????????????????????????????????????????
         * ???????????????????????????
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//??????assets????????????json????????????

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//???Gson ????????????

        /**
         * ??????????????????
         * ???????????????????????????JavaBean????????????????????????????????? IPickerViewData ?????????
         * PickerView?????????getPickerViewText????????????????????????????????????
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//????????????
            ArrayList<String> CityList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//??????????????????????????????????????????

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//??????????????????????????????
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//????????????
                ArrayList<String> City_AreaList = new ArrayList<>();//??????????????????????????????

                //??????????????????????????????????????????????????????????????????null ?????????????????????????????????????????????
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//??????????????????????????????
            }
            //??????????????????
            options2Items.add(CityList);
            //??????????????????
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson ??????
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
            case R.id.img_back://??????
                finish();
                break;
            case R.id.btnSubmit2://??????????????????
                Intent intent = new Intent(RegisterDigitalResultActivity.this, RegisterAgainDigitalStoreActivity.class);
                intent.putExtra("dataStr", dataStr);
                startActivity(intent);
                finish();
//                if(iMeAgainCommitPresenter == null){
//                    iMeAgainCommitPresenter = new IMeAgainCommitPresenterImpl();
//                    iMeAgainCommitPresenter.registerViewCallback(this);
//                }
//                UpdateCompanyBean updateCompanyBean = new UpdateCompanyBean();
//                updateCompanyBean.setAccountName(data.getData().getAccountName());//????????????
//                updateCompanyBean.setType(type);//??????????????????
//                updateCompanyBean.setCompanyName(tv_company_name.getText().toString());//????????????
//                updateCompanyBean.setCreditCode(tv_shehuidaima.getText().toString());//??????????????????
//                updateCompanyBean.setBusinessLicence(yyzz);//????????????
//                updateCompanyBean.setHomeLocation(tv_address_fading.getText().toString());//?????????
//                updateCompanyBean.setArtificialPerson(tv_name.getText().toString());//??????
//                updateCompanyBean.setCertificateNo(tv_card_id.getText().toString());//????????????
//                updateCompanyBean.setAddress(tv_tongxun_address.getText().toString());//????????????
//                updateCompanyBean.setMobile(tv_phone.getText().toString());//?????????
//                updateCompanyBean.setCertificateFront(sfzzm);//???????????????
//                updateCompanyBean.setCertificateBack(sfzfm);//???????????????
//
//                iMeAgainCommitPresenter.getData(updateCompanyBean);
                break;
            case R.id.lin_copy://??????

                copyContentToClipboard(tv_load_xiazai.getText().toString() + "", RegisterDigitalResultActivity.this);
                ToastUtils.getInstance(RegisterDigitalResultActivity.this).show("????????????!");
                break;
            case R.id.img_yyzz://????????????
                ImageSelector.builder()
                        .useCamera(true) // ????????????????????????
                        .setSingle(false)  //??????????????????
                        .canPreview(true) //??????????????????????????????,????????????true
                        .setMaxSelectCount(1) // ??????????????????????????????????????????0?????????????????????
                        .start(RegisterDigitalResultActivity.this, 1); // ????????????
                break;
            case R.id.img_id_zhengmian://???????????????
                ImageSelector.builder()
                        .useCamera(true) // ????????????????????????
                        .setSingle(false)  //??????????????????
                        .canPreview(true) //??????????????????????????????,????????????true
                        .setMaxSelectCount(1) // ??????????????????????????????????????????0?????????????????????
                        .start(RegisterDigitalResultActivity.this, 3); // ????????????
                break;
            case R.id.img_id_fan://???????????????
                ImageSelector.builder()
                        .useCamera(true) // ????????????????????????
                        .setSingle(false)  //??????????????????
                        .canPreview(true) //??????????????????????????????,????????????true
                        .setMaxSelectCount(1) // ??????????????????????????????????????????0?????????????????????
                        .start(RegisterDigitalResultActivity.this, 2); // ????????????
                break;
            case R.id.tv_address_fading://?????????????????????
                showPickerView();
                break;
//            case R.id.tv_company_type://??????????????????
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
                //?????????????????????????????????????????????
                tv_address_fading.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);
            }
        })
                .setTitleText("????????????")
                .setTitleBgColor(Color.WHITE)//???????????????????????????
                .setDividerColor(Color.BLACK)//????????????????????????
                .setTextColorCenter(Color.BLACK) //???????????????????????????
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//???????????????
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
                        //????????????
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
     * ????????????????????????
     *
     * @param content
     * @param context
     */
    public void copyContentToClipboard(String content, Context context) {
        //???????????????????????????
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // ?????????????????????ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // ???ClipData?????????????????????????????????
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public void loadAgainCommitPostData(CollectionBean collectionBean) {
        if (collectionBean != null && collectionBean.getCode() == 200) {
            ToastUtil.showShort(RegisterDigitalResultActivity.this, "????????????!");
            tv_register_status.setText("?????????");
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
                tv_company_type.setText("??????");
            } else if (types.equals("1")) {
                Log.e("FF2344", "types:" + types);
                type = types;
                tv_company_type.setText("???????????????");
            }
        }
    }
}
