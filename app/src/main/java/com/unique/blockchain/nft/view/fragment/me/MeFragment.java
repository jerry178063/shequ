package com.unique.blockchain.nft.view.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.dialog.CustormerServiceDialog;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.view.activity.me.DigitalStoreActivity;
import com.unique.blockchain.nft.view.activity.me.GriiActivity;
import com.unique.blockchain.nft.view.activity.me.PersonalActivity;
import com.unique.blockchain.nft.view.activity.me.PledgeActivity;
import com.unique.blockchain.nft.view.activity.me.SettingActivity;
import com.unique.blockchain.nft.view.activity.me.WalletManagerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MeFragment extends BaseFragment implements CustormerServiceDialog.LoadOnclickView {


    private ImageView rela_wallet_manager;
    private ImageView layout_bianji;
    private LinearLayout jiao_yiiii;
    private LinearLayout zhiya_me;
    private LinearLayout Ren_zheng;
    private LinearLayout me_chakan;
    private TextView me_unqique;
    private TextView me_name;
    private TextView me_qian;
    private Window Window;
    private List<String> list = new ArrayList<>();
    private View cha_item;

    @BindView(R.id.me_unqique)
    TextView tv_address_name;

    public User unique;
    public  UserDao userDao;
    public String uniqueAdress;
    public String unique_name;
    private String NickName="";
    private String NftSign="";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        Log.i("TAG", "onActivityResult: "+bundle);
    }

    @Override
    public void setOnClickView(String type) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 1);
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        rela_wallet_manager = (ImageView) findViewById(R.id.rela_wallet_manager);
        me_unqique = (TextView) findViewById(R.id.me_unqique);
        me_name = (TextView) findViewById(R.id.me_name);
        me_qian = (TextView) findViewById(R.id.me_qian);
        layout_bianji = (ImageView) findViewById(R.id.layout_bianji);
        jiao_yiiii = (LinearLayout) findViewById(R.id.jiao_yiiii);
        zhiya_me = (LinearLayout) findViewById(R.id.zhiya_me);
        Ren_zheng = (LinearLayout) findViewById(R.id.Ren_zheng);
        me_chakan = (LinearLayout) findViewById(R.id.me_chakan);

        NickName =getArguments().getString("mNickName");
        NftSign =getArguments().getString("mNftSign");
        Log.i("TAG", "initView: "+NickName);
        me_name.setText(NickName);
        me_qian.setText(NftSign);

        initOnClickView();//点击事件
        initBianji();//编辑个人资料


    }
    @Override
    public void onResume() {
        super.onResume();

        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        try {
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(getContext(), Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        tv_address_name.setText(uniqueAdress);
    }
    private void initBianji() {
        layout_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupWindow(view);
                Intent intent = new Intent(getContext(), PersonalActivity.class);
                startActivity(intent);
            }
        });

        zhiya_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PledgeActivity.class);
                startActivity(intent);
            }
        });
        Ren_zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DigitalStoreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initOnClickView() {
        rela_wallet_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WalletManagerActivity.class);
                startActivity(intent);

            }
        });
        jiao_yiiii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GriiActivity.class);
                startActivity(intent);
            }
        });
        me_chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GriiActivity.class);
                startActivity(intent);
            }
        });

    }

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @OnClick({R.id.lin_set})
    public void setOnclick(View view) {
        switch (view.getId()) {
            case R.id.lin_set://设置
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;

        }
    }


    /*private CustormerServiceDialog custormerServiceDialog;
    private int mCurrentVersionCode;
    @BindView(R.id.tv_wallet_name)
    TextView tv_wallet_name;
  /*  @BindView(R.id.tv_check_update_version)
    TextView tv_check_update_version;
    // 本地语言设置
    Locale myLocale = null;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    public void initView() {
        CommonUtil.setStatusBarTextColor(getActivity(), 2);
        mCurrentVersionCode = getVersionCode();
       // tv_check_update_version.setText("v " + getAppVersionCode(getContext()) + "");
    }

    public static int getVersionCode() {
        String packageName = MyApplication.getAppContext().getPackageName();
        PackageInfo packageInfo;
        try {
            packageInfo = MyApplication.getAppContext().getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new AssertionError(e);
        }
    }*/
    /*public void setOnclick(View v) {
        if (NetworkUtil.isConnected(getContext())) {
//            if (FastClick.isFastClick()) {
            if (DoubleClickUtil.isCommonClick()) {
                switch (v.getId()) {
                   // case R.id.rela_check_update://检测版本更新

                     //   checkUpdate();

                        //break;
                    case R.id.tv_create_wallet://创建钱包
                        MyApplication.mWaleetClick = 1;
                        custormerServiceDialog = new CustormerServiceDialog(getContext(), "1");
                        custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot, getResources().getString(R.string.turn_airplane_mode), getResources().getString(R.string.for_your_wallet_security));
                        custormerServiceDialog.setLoadOnclickView(this::setOnClickView);
                        custormerServiceDialog.show();
                        break;
                    case R.id.tv_import_wallet://导入钱包
                        MyApplication.mWaleetClick = 1;
                        custormerServiceDialog = new CustormerServiceDialog(getContext(), "2");
                        custormerServiceDialog.setImgAndTxt(R.mipmap.pic_not_screenshot, getResources().getString(R.string.turn_airplane_mode), getResources().getString(R.string.for_your_wallet_security));
                        custormerServiceDialog.setLoadOnclickView(this::setOnClickView);
                        custormerServiceDialog.show();
                        break;
                    case R.id.rela_wallet_manager://钱包管理
                        Intent intent = new Intent(getContext(), WalletManagerActivity.class);
                        startActivity(intent);
                        break;
                   // case R.id.rela_user_back://用户反馈
//                Intent intent1 = new Intent(getContext(), FeedbackActivity.class);
//                startActivity(intent1);
                        ToastUtil.showShort(getContext(), "敬请期待!");
                        break;
                   // case R.id.rela_gauss_liulan://区块链浏览器
                        Intent intent1 = new Intent(getContext(), AdWebActivity.class);
//                        intent1.putExtra("banner_url", "http://192.168.2.13:8006");
                        intent1.putExtra("banner_url", "http://wap.galaxypc.vip");
                        startActivity(intent1);
                        break;
                  //  case R.id.rela_send_notice://推送通知
//                Intent intent2 = new Intent(getContext(), PushNoticeActivity.class);
//                startActivity(intent2);
                        ToastUtil.showShort(getContext(), "敬请期待!");
                        break;
                 //   case R.id.rela_help_center://帮助中心
                        Intent intent3 = new Intent(getContext(), HelpCenterActivity.class);
                        startActivity(intent3);

                        break;
                  //  case R.id.rela_more_language://多语言切换

                        ToastUtil.showShort(getContext(), "敬请期待!");

//                Intent intent4 = new Intent(getContext(), LanguageActivity.class);
//                startActivity(intent4);
//                        // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
//                        new XPopup.Builder(getContext())
//                                .asBottomList("请选择", new String[]{ "English", "中文"},
//                                        new OnSelectListener() {
//                                            @Override
//                                            public void onSelect(int position, String text) {
//                                                switch (position) {
//                                                    case 0:
//                                                        LanguageUtils.applyLanguage(Locale.US, "");
//                                                        break;
//                                                    case 1:
//                                                        LanguageUtils.applyLanguage(Locale.SIMPLIFIED_CHINESE, "");
//                                                        break;
//                                                }
//                                            }
//                                        })
//                                .show();
                        break;
                }
            }
//            }
        } else {
            ToastUtil.showShort(getContext(), getResources().getString(R.string.the_network_is_not_open));
        }
    }//所有点击事件


  /*  @Override
    public void setOnClickView(String type) {
        //type =1 为创建  2 为导入

        if (type.equals("1")) {
            Intent intent = new Intent(getContext(), CreateUserActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            //导入钱包
        } else if (type.equals("2")) {
            Intent intent = new Intent(getContext(), CreateUserActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
        custormerServiceDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();

        tv_wallet_name.setText(SPUtils.getString(getContext(), "witch_wallet", "") + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) { //方法名任意，这里的参数和  EventBus.getDefault().post(111);对应即可
        if (!TextUtils.isEmpty(message) && message.equals("choose_wallet")) {
            Log.e("FF887","aaa:" + message);
            tv_wallet_name.setText(SPUtils.getString(getContext(), "witch_wallet", "") + "");
        }
    }
*///别乱打开

}
