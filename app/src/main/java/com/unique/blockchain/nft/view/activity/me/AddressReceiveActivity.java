package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lzy.okgo.OkGo;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.AddressReceiveAdapter;
import com.unique.blockchain.nft.adapter.InventoryAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.me.Inventory;
import com.unique.blockchain.nft.domain.me.UserAdressListBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.AddDigitalDialog;
import com.unique.blockchain.nft.infrastructure.dialog.DeleAddressDigitalDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.me.meAdapter.PersonalAdapter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeAddressListPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeAddressListPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeAddressListCallBack;
import com.unique.blockchain.nft.view.activity.wallet.AddDigitalAssetsActivity;
import com.unique.blockchain.nft.widget.SlideNewRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * 收货地址
 * */
public class AddressReceiveActivity extends BaseActivity implements IMeAddressListCallBack,DeleAddressDigitalDialog.OnSafeClickView {
    @BindView(R.id.address_rec)
    SlideNewRecyclerView personal_rec;
    @BindView(R.id.location_add)
    TextView location_add;
    @BindView(R.id.img_back)
    LinearLayout img_back;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;

    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    IMeAddressListPresenter iMeAddressListPresenter;
    private List<UserAdressListBean.Data> lo = new ArrayList<>();
    private int userAddressId;

    private List<Inventory> mInventories;
    private InventoryAdapter mInventoryAdapter;
    private DeleAddressDigitalDialog addDigitalDialog;
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_address;
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
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();//钱包地址
                }
            }
        }
//        personal_rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        addressReceiveAdapter = new AddressReceiveAdapter(this, lo);
//        personal_rec.setAdapter(addressReceiveAdapter);


        personal_rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.mipmap.delete_zjc));
        personal_rec.addItemDecoration(itemDecoration);
        mInventories = new ArrayList<>();
        Inventory inventory;
        Random random = new Random();
//        for (int i = 0; i < 50; i++) {
//            inventory = new Inventory();
//            inventory.setItemDesc("测试数据" + i);
//            inventory.setQuantity(random.nextInt(100000));
//            inventory.setItemCode("0120816");
//            inventory.setDate("20180219");
//            inventory.setVolume(random.nextFloat());
//            mInventories.add(inventory);
//        }
        mInventoryAdapter = new InventoryAdapter(this, lo,uniqueAdress);
        personal_rec.setAdapter(mInventoryAdapter);
        mInventoryAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                mPosition = position;
                if (addDigitalDialog == null) {
                    addDigitalDialog = new DeleAddressDigitalDialog(AddressReceiveActivity.this);
                    addDigitalDialog.setOnclick(AddressReceiveActivity.this);
                    addDigitalDialog.show();
                } else {
                    addDigitalDialog.show();
                }



            }
        });
    }

    @Override
    public void setOnSafeClickView(String et_pass) {
        OkGo.get(UrlConstant.baseUrl + "api/wallet/deleteAddress")
                .params("id",lo.get(mPosition).getId())
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        if(jsonObject.getCode() == 200) {
                            Log.e("FF43222", "1111:" + jsonObject);
                            lo.remove(mPosition);
                            mInventoryAdapter.notifyDataSetChanged();
                            personal_rec.closeMenu();
                            ToastUtil.showShort(AddressReceiveActivity.this,"删除成功");
                            if(!TextUtils.isEmpty(lo.toString()) && lo.size() > 0){//还有数据
                                tv_no_data.setVisibility(View.GONE);
                            }else {//无数据
                                tv_no_data.setVisibility(View.VISIBLE);
                            }
                        }

                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF43222", "4444");
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });


    }
    @Override
    protected void onResume() {
        super.onResume();
        if (iMeAddressListPresenter != null) {
            iMeAddressListPresenter.getData(uniqueAdress);
        }
    }

    @Override
    public void initData() {

        //获取地址列表
        if (iMeAddressListPresenter == null) {
            iMeAddressListPresenter = new IMeAddressListPresenterImpl();
            iMeAddressListPresenter.registerViewCallback(this);
        }
        iMeAddressListPresenter.getData(uniqueAdress);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        location_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressReceiveActivity.this,LocationActivity.class);
                intent.putExtra("uniqueAdress", uniqueAdress);
                intent.putExtra("size",lo.size() + "");
                startActivity(intent);
            }
        });
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    public void loadAddressListPostData(UserAdressListBean userAdressListBean) {
        if (userAdressListBean != null && userAdressListBean.getCode() == 200) {

            if (userAdressListBean.getData() != null && userAdressListBean.getData().size() > 0) {
                if (lo != null) {
                    lo.clear();
                }
                lo.addAll(userAdressListBean.getData());
                mInventoryAdapter.notifyDataSetChanged();
                for (int i = 0; i < userAdressListBean.getData().size(); i++) {
                    if (userAdressListBean.getData().get(i).getIsDefault() == 1) {
                        userAddressId = userAdressListBean.getData().get(i).getId();
                    }
                }
                tv_no_data.setVisibility(View.GONE);
            }else {
                tv_no_data.setVisibility(View.VISIBLE);
            }

        }else {
            tv_no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadAddressListPostFail() {

    }

}
