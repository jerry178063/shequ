package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.unique.blockchain.nft.domain.me.PersonInfoBean;
import com.unique.blockchain.nft.domain.me.PersonlInfoBean;
import com.unique.blockchain.nft.domain.me.UploadImageFileBean;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.meAdapter.PersonalAdapter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressListPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeAddressListPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressListCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalActivity extends BaseActivity implements IMeAddressListCallBack {

    private LinearLayout mChaItem;
    private ImageView me_camera;
    private TextView mMePer;
    private TextView me_address;
    private RecyclerView personal_rec;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    private TextView mMePersonalUnique;
    private RequestBody boby;
    private ImageView iv_photo;
    private ImageView mMeCamera;

    private EditText mNickName;
    private EditText mNftSign;
    private String lian = "";
    private String dian = " ";
    private String di = " ";
    private String xiang = " ";

    private Integer id = 132123;
    private String portraitUrl = "";
    private PersonalAdapter personalAdapter;
    private List<UserAdressListBean.Data> lo = new ArrayList<>();
    IMeAddressListPresenter iMeAddressListPresenter;
    @BindView(R.id.me_camera2)
    ImageView me_camera2;
    private int userAddressId;
    private String imgUrl, mPrsonlInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    private void initView() {
        CommonUtil.setStatusBarColor(this, 1);
        CommonUtil.setStatusBarTextColor(this, 1);
        mChaItem = findViewById(R.id.cha_item);
        mMePersonalUnique = findViewById(R.id.me_personal_unique);
        iv_photo = findViewById(R.id.me_camera);
        mMeCamera = findViewById(R.id.me_camera);
        mNickName = findViewById(R.id.nickName);
        mNftSign = findViewById(R.id.nftSign);
        mMePer = findViewById(R.id.me_per);
        personal_rec = findViewById(R.id.personal_rec);
        me_address = findViewById(R.id.me_address);
        initunique();
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(PersonalActivity.this, 1); // 打开相册
            }
        });

        mNickName.setSelection(mNickName.getText().length());
        mNftSign.setSelection(mNftSign.getText().length());
        initpersonal();

        initData();

    }

    private void initpersonal() {
        lian = getIntent().getStringExtra("lian");
        dian = getIntent().getStringExtra("dian");
        di = getIntent().getStringExtra("di");
        xiang = getIntent().getStringExtra("xiang");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        personal_rec.setLayoutManager(linearLayoutManager);
        personalAdapter = new PersonalAdapter(this, lo,uniqueAdress);
        personal_rec.setAdapter(personalAdapter);

    }

    private void initunique() {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();//钱包地址
                    mMePersonalUnique.setText(uniqueAdress);
                }
            }
        }
        Log.e("FD444", "did:" + unique.getDid());
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
        initView();
    }

    public void initData() {
        mPrsonlInfoBean = getIntent().getStringExtra("mPrsonlInfoBean");
        PersonlInfoBean personlInfoBean = new Gson().fromJson(mPrsonlInfoBean, PersonlInfoBean.class);
        if (personlInfoBean != null) {
            Glide.with(PersonalActivity.this).load(personlInfoBean.getData().getPortraitUrl()).into(me_camera2);
            mNickName.setText(personlInfoBean.getData().getNickName());
            mNftSign.setText(personlInfoBean.getData().getNftSign());

        }


        mChaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        me_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(PersonalActivity.this, LocationActivity.class);
                    intent.putExtra("uniqueAdress", uniqueAdress);
                    startActivity(intent);
                }
            }
        });
        mMePer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (MoreClick.MoreClicks()) {
                    PersonInfoBean personInfoBean = new PersonInfoBean();
                    personInfoBean.setWalletAddr(uniqueAdress + "");
//                    personInfoBean.setNickName(mNickName.getText().toString());
                    personInfoBean.setPortraitUrl(imgUrl);
//                    personInfoBean.setUserAddressId(userAddressId);
//                        personInfoBean.setNftSign(mNftSign.getText().toString());
                    Log.e("FD444", "imgUrl:" + imgUrl);
                    Log.e("FD444", "personInfoBean:" + new Gson().toJson(personInfoBean));
//                    OkGo.post(UrlConstant.baseUrl + UrlConstant.ap)
//                            .upJson(personInfoBean.toString())
//                            .execute(new JsonCallback<CollectionBean>() {
//
//                                @Override
//                                public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
//                                    if (jsonObject.getCode() == 200) {
//                                        ToastUtil.showShort(PersonalActivity.this, "修改成功!");
//                                    }
//                                }
//                            });
//                    finish();
                }

            }
        });
        //获取地址列表
        if (iMeAddressListPresenter == null) {
            iMeAddressListPresenter = new IMeAddressListPresenterImpl();
            iMeAddressListPresenter.registerViewCallback(this);
        }
        iMeAddressListPresenter.getData(uniqueAdress);
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            if (requestCode == 1) {
                Uri data1 = data.getData();

//            Intent intent = new Intent();
//            intent.putExtra("mNickName",mNickName.getText().toString());
//            intent.putExtra("mNftSign",mNftSign.getText().toString());
//            Log.i("TAG", "onActivityResult: "+mNftSign);
//            setResult(Activity.RESULT_OK,intent);
                if (images != null && images.size() > 0) {
                    Log.e("FFF3333", "IMAGE_2:" + new Gson().toJson(images));
                    for (int i = 0; i < images.size(); i++) {
                        loadFile(images.get(i), 1);
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
                .execute(new JsonCallback<UploadImageFileBean>() {
                    @Override
                    public void onSuccess(UploadImageFileBean uploadImageFileBean, Call call, Response response) {
                        //请求成功
                        if (uploadImageFileBean != null) {
                            imgUrl = uploadImageFileBean.getData().getUrl();
                            Glide.with(PersonalActivity.this).load(imgUrl).into(me_camera2);
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

    //地址列表
    @Override
    public void loadAddressListPostData(UserAdressListBean userAdressListBean) {
        if (userAdressListBean != null && userAdressListBean.getCode() == 200) {

            if (userAdressListBean.getData() != null && userAdressListBean.getData().size() > 0) {
                if (lo != null) {
                    lo.clear();
                }
                lo.addAll(userAdressListBean.getData());
                personalAdapter.notifyDataSetChanged();
                for (int i = 0; i < userAdressListBean.getData().size(); i++) {
                    if (userAdressListBean.getData().get(i).getIsDefault() == 1) {
                        userAddressId = userAdressListBean.getData().get(i).getId();
                    }
                }
            }

        }
    }

    @Override
    public void loadAddressListPostFail() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iMeAddressListPresenter != null) {
            iMeAddressListPresenter.getData(uniqueAdress);
        }
    }
}