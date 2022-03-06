package com.unique.blockchain.nft.view.activity.me;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.MeAddAddressBean;
import com.unique.blockchain.nft.domain.me.MeXiuGaiAddressBean;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.view.activity.me.meAdapter.GetJsonDataUtil;
import com.unique.blockchain.nft.view.activity.me.meAdapter.JsonBean;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressListPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeAddressPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeAddressXiuGaiPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressCallBack;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity implements IMeAddressCallBack {

    @BindView(R.id.location_lian)
    EditText mLocationLian;//联系人
    @BindView(R.id.location_dian)
    EditText mLocationDian;//电话
    @BindView(R.id.location_xiang)
    EditText mLocationXiang;//详细地址
    @BindView(R.id.location_fan)
    LinearLayout location_fan;//返回

    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    @BindView(R.id.location_area)
    TextView location_area;
    private String province, city, area, uniqueAdress, user, mobile, address, region;
    IMeAddressPresenter iMeAddressPresenter, iMeAddressPresenterXiuGai;
    IMeAddressListPresenter iMeAddressListPresenter;
    List<String> bannImg = new ArrayList<>();//顶部滚动图片
    private String xiugai, id;
    @BindView(R.id.tv_titled)
    TextView tv_titled;
    private int aa = 0;
    private int isDefault = 0;
    @BindView(R.id.img_is_goumai)
    ImageView img_is_goumai;
    private boolean isSelectGou;
    @BindView(R.id.location_add)
    TextView location_add;
    private String size, isDefaults;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location;
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
        uniqueAdress = getIntent().getStringExtra("uniqueAdress");
        user = getIntent().getStringExtra("user");
        mobile = getIntent().getStringExtra("mobile");
        address = getIntent().getStringExtra("address");
        region = getIntent().getStringExtra("region");
        xiugai = getIntent().getStringExtra("xiugai");
        id = getIntent().getStringExtra("id");
        size = getIntent().getStringExtra("size");
        isDefaults = getIntent().getStringExtra("isDefault");
        Log.e("size2", "size:" + size);

        if (location_area.getText().toString().equals("选择所在地区")) {
            location_area.setTextColor(getResources().getColor(R.color.color_D7D7D7));
        } else {
            location_area.setTextColor(getResources().getColor(R.color.color_ff202121));
        }

        if (user != null) {
            mLocationLian.setText(user);
        }
        if (mobile != null) {
            mLocationDian.setText(mobile);
        }
        Log.e("FDD344", "region:" + region);
        if (region != null) {
            location_area.setText(region);

            bannImg = Arrays.asList(region.split("-"));
            if (bannImg != null) {
                for (int i = 0; i < bannImg.size(); i++) {
                    if (bannImg.size() >= 1) {
                        province = bannImg.get(0);
                    }
                    if (bannImg.size() >= 2) {
                        city = bannImg.get(1);
                    }
                    if (bannImg.size() >= 3) {
                        area = bannImg.get(2);
                    }
                }
            }
            Log.e("FDD344", "province1:" + province);
            Log.e("FDD344", "city1:" + city);
            Log.e("FDD344", "area1:" + area);
        }
        if (!TextUtils.isEmpty(xiugai) && xiugai.equals("xiugai")) {
            tv_titled.setText("修改地址");
            location_add.setText("保存收货地址");
            if(!TextUtils.isEmpty(isDefaults)){
               if(isDefaults.equals("1")){//默认选中
                   isSelectGou = true;
                   img_is_goumai.setSelected(true);
                   isDefault = 1;
               } else {
                   isDefault = 0;
                   isSelectGou = false;
                   img_is_goumai.setSelected(false);
               }
            }
        } else {
            location_add.setText("新增收货地址");
            if(!TextUtils.isEmpty(size)) {
                if (size.equals("0")) {
                    isSelectGou = true;
                    img_is_goumai.setSelected(true);
                    isDefault = 1;
                }
            }
        }


        if (location_area.getText().toString().equals("选择所在地区")) {
            location_area.setTextColor(getResources().getColor(R.color.color_D7D7D7));
        } else {
            location_area.setTextColor(getResources().getColor(R.color.color_ff202121));
        }
        Log.e("FGD4555", "address:" + address);
        if (address != null) {
            mLocationXiang.setText(address);
        }
        initView();
        initData();
    }

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

    @OnClick({R.id.location_add, R.id.img_is_goumai, R.id.tv_is_goumai})
    public void setOnclick(View view) {
        if (MoreClick.MoreClicks()) {
            switch (view.getId()) {
                case R.id.location_add://添加
                    if (TextUtils.isEmpty(mLocationLian.getText())) {
                        ToastUtil.showShort(LocationActivity.this, "请输入联系人!");
                        return;
                    }
                    if (TextUtils.isEmpty(mLocationDian.getText())) {
                        ToastUtil.showShort(LocationActivity.this, "请输入电话!");
                        return;
                    }
                    if (!TextUtils.isEmpty(location_area.getText()) && location_area.getText().toString().equals("选择所在地区")) {
                        ToastUtil.showShort(LocationActivity.this, "请选择所在地区!");
                        return;
                    }
                    if (TextUtils.isEmpty(mLocationXiang.getText())) {
                        ToastUtil.showShort(LocationActivity.this, "请输入详细地址!");
                        return;
                    }

                    MeAddAddressBean meAddAddressBean = new MeAddAddressBean();
                    meAddAddressBean.setProvince(province);
                    meAddAddressBean.setCity(city);
                    meAddAddressBean.setArea(area);
                    meAddAddressBean.setMobile(mLocationDian.getText().toString());
                    meAddAddressBean.setUserName(mLocationLian.getText().toString());
                    meAddAddressBean.setAddress(mLocationXiang.getText().toString());
                    meAddAddressBean.setWalletAddr(uniqueAdress);
                    meAddAddressBean.setIsDefault(isDefault);
                    if (aa == 0) {
                        aa = 1;

                        if (TextUtils.isEmpty(xiugai)) {
                            showDialog();
                            if (iMeAddressPresenter == null) {
                                iMeAddressPresenter = new IMeAddressPresenterImpl();
                                iMeAddressPresenter.registerViewCallback(this);
                            }
                            Log.e("FDD344", "meAddAddressBean:" + new Gson().toJson(meAddAddressBean));
                            iMeAddressPresenter.getData(meAddAddressBean.toString());
                        } else {
                            showDialog();
                            MeXiuGaiAddressBean meXiuGaiAddressBean = new MeXiuGaiAddressBean();
                            meXiuGaiAddressBean.setProvince(province);
                            meXiuGaiAddressBean.setCity(city);
                            meXiuGaiAddressBean.setArea(area);
                            meXiuGaiAddressBean.setMobile(mLocationDian.getText().toString());
                            meXiuGaiAddressBean.setUserName(mLocationLian.getText().toString());
                            meXiuGaiAddressBean.setAddress(mLocationXiang.getText().toString());
                            meXiuGaiAddressBean.setWalletAddr(uniqueAdress);
                            meXiuGaiAddressBean.setId(id + "");
                            meXiuGaiAddressBean.setIsDefault(isDefault);
                            if (iMeAddressPresenterXiuGai == null) {
                                iMeAddressPresenterXiuGai = new IMeAddressXiuGaiPresenterImpl();
                                iMeAddressPresenterXiuGai.registerViewCallback(this);
                            }
                            Log.e("FDD344", "meAddAddressBean:" + new Gson().toJson(meAddAddressBean));
                            iMeAddressPresenterXiuGai.getData(meXiuGaiAddressBean.toString());

                        }
                    }
                    break;
                case R.id.tv_is_goumai:
                case R.id.img_is_goumai:
                    if (!isSelectGou) {
                        isSelectGou = true;
                        img_is_goumai.setSelected(true);
                        isDefault = 1;
                    } else {
                        isDefault = 0;
                        isSelectGou = false;
                        img_is_goumai.setSelected(false);
                    }
                    break;
            }
        }
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    private void initView() {

        location_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        location_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MoreClick.MoreClicks()) {
                    showPickerView();
                }
            }
        });
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                location_area.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);

                if (location_area.getText().toString().equals("选择所在地区")) {
                    location_area.setTextColor(getResources().getColor(R.color.color_D7D7D7));
                } else {
                    location_area.setTextColor(getResources().getColor(R.color.color_ff202121));
                }
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
    public void loadAddressPostData(CollectionBean personlInfoBean) {
        dismissDialog();
        if (personlInfoBean != null && personlInfoBean.getCode() == 200) {
            ToastUtil.showShort(LocationActivity.this, "添加地址成功!");
            finish();
        } else {
            ToastUtil.showShort(LocationActivity.this, personlInfoBean.getMsg());
        }
    }

    @Override
    public void loadAddressPostFail() {
        dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aa = 0;
    }
}