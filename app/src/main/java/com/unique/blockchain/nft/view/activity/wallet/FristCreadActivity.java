package com.unique.blockchain.nft.view.activity.wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.space.exchange.biz.common.ActivityManager;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.dialog.CustormerServiceDialog;

import java.util.List;

import butterknife.BindView;


public class FristCreadActivity extends BaseActivity implements CustormerServiceDialog.LoadOnclickView {
    @BindView(R.id.tv_create_wallet)
    TextView tv_create_wallet;
    @BindView(R.id.tv_load_wallet)
    TextView tv_load_wallet;
    private  String TAG = "FristCreadActivity";
    private long mExitTime;
    private CustormerServiceDialog custormerServiceDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_frist_cread;
    }

    @Override
    public void initUI() {
    }

    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this, 1);
    }

    @Override
    public void bindView(Bundle bundle) {
        getPermission(true,true);
        //创建钱包
        tv_create_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                custormerServiceDialog = new CustormerServiceDialog(FristCreadActivity.this, "1");
                custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot,getResources().getString(R.string.turn_airplane_mode),getResources().getString(R.string.for_your_wallet_security));
                custormerServiceDialog.setLoadOnclickView(FristCreadActivity.this::setOnClickView);
                custormerServiceDialog.show();

            }
        });
        //导入钱包
        tv_load_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custormerServiceDialog = new CustormerServiceDialog(FristCreadActivity.this, "2");
                custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot,getResources().getString(R.string.turn_airplane_mode),getResources().getString(R.string.for_your_wallet_security));
                custormerServiceDialog.setLoadOnclickView(FristCreadActivity.this::setOnClickView);
                custormerServiceDialog.show();

            }
        });
    }

    @Override
    public void setOnClickView(String type) {

        //type =1 为创建  2 为导入

        if (type.equals("1")) {
            Intent intent = new Intent(this, CreateUserActivity.class);
            intent.putExtra("type",type);
            startActivity(intent);
            //导入钱包
        }
        else if (type.equals("2")) {
            Intent intent = new Intent(this, CreateUserActivity.class);
            intent.putExtra("type",type);
            startActivity(intent);
        }
        custormerServiceDialog.dismiss();
    }

    private  void getPermission(boolean isAsk, final boolean isHandOpen){
        if(!isAsk)return;
        if (XXPermissions.isHasPermission(this,
                //所需危险权限可以在此处添加：
//                Permission.READ_PHONE_STATE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.CAMERA)) {
        }else {
            XXPermissions.with((Activity)this).permission(
                    //同时在此处添加：
//                    Permission.READ_PHONE_STATE,
                    Permission.WRITE_EXTERNAL_STORAGE,
                    Permission.CAMERA
            ).request(new OnPermission() {
                @Override
                public void noPermission(List<String> denied, boolean quick) {
                    if (quick) {
                        Log.e(TAG, "被永久拒绝授权，请手动授予权限");
                        //如果是被永久拒绝就跳转到应用权限系统设置页面
                        if(isHandOpen) {
                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(FristCreadActivity.this);
                            normalDialog.setTitle(getResources().getString(R.string.enable_permission_guidance));
                            normalDialog.setMessage(getResources().getString(R.string.the_permissions_that_permanently));
                            normalDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    XXPermissions.gotoPermissionSettings(FristCreadActivity.this);
                                }
                            });
                            normalDialog.setNegativeButton(getResources().getString(R.string.next_time), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                            normalDialog.show();
                        }
                    }else {
                        Log.e(TAG, "获取权限失败");
                    }
                }

                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    if (isAll) {
                        Log.e(TAG, "获取权限成功");
                    }else {
                        Log.e(TAG, "获取权限成功，部分权限未正常授予");
                    }
                }
            });
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private void exitApp() {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showToast(this, "再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getActivityManager().AppExit();
            }
        }
}