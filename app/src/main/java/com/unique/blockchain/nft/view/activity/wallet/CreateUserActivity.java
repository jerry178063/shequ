package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.DoubleClickUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.AESEncrypt;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.infrastructure.utils.StringUtils;
import com.unique.blockchain.nft.view.activity.AdWebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateUserActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CreateUserActivity";
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_passwordc)
    EditText et_passwordc;
    @BindView(R.id.tv_create_wallet)
    TextView tv_create_wallet;

    @BindView(R.id.iv_select)
    ImageView iv_select;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ed_invtation_code)
    EditText ed_invtation_code;
    @BindView(R.id.tv_intent)
    TextView tv_intent;
    @BindView(R.id.iv_finish)
    LinearLayout iv_finish;
    @BindView(R.id.img_xianshi_yincang)
    ImageView img_xianshi_yincang;
    @BindView(R.id.img_xianshi_yincang_two)
    ImageView img_xianshi_yincang_two;

    private UserDao userDao;
    private boolean isSelect;
    //type=1 创建 2导入
    private String type;
    private boolean isPswVisible1,isPswVisible2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_user;
    }

    @Override
    public void initUI() {
    }

    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this, 1);
        if (!et_name.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty() && !et_passwordc.getText().toString().isEmpty() && isSelect) {
            tv_create_wallet.setSelected(true);
            tv_create_wallet.setClickable(true);
            tv_create_wallet.setAlpha(Float.valueOf("1.0"));
        } else {
            tv_create_wallet.setClickable(false);
            tv_create_wallet.setAlpha(Float.valueOf("0.3"));
        }
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_name.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty() && !et_passwordc.getText().toString().isEmpty() && isSelect) {
                    tv_create_wallet.setSelected(true);
                    tv_create_wallet.setClickable(true);
                    tv_create_wallet.setAlpha(Float.valueOf("1.0"));
                } else {
                    tv_create_wallet.setClickable(false);
                    tv_create_wallet.setAlpha(Float.valueOf("0.3"));
                }
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    et_name.setText(str1);
                    et_name.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_name.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty() && !et_passwordc.getText().toString().isEmpty() && isSelect) {
                    tv_create_wallet.setSelected(true);
                    tv_create_wallet.setClickable(true);
                    tv_create_wallet.setAlpha(Float.valueOf("1.0"));
                } else {
                    tv_create_wallet.setClickable(false);
                    tv_create_wallet.setAlpha(Float.valueOf("0.3"));
                }
                if(TextUtils.isEmpty(s.toString())){
                    img_xianshi_yincang.setVisibility(View.GONE);
                }else {
                    img_xianshi_yincang.setVisibility(View.VISIBLE);
                }
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    et_password.setText(str1);
                    et_password.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_passwordc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_name.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty() && !et_passwordc.getText().toString().isEmpty() && isSelect) {
                    tv_create_wallet.setSelected(true);
                    tv_create_wallet.setClickable(true);
                    tv_create_wallet.setAlpha(Float.valueOf("1.0"));

                } else {
                    tv_create_wallet.setClickable(false);
                    tv_create_wallet.setAlpha(Float.valueOf("0.3"));

                }
                if(TextUtils.isEmpty(s.toString())){
                    img_xianshi_yincang_two.setVisibility(View.GONE);
                }else {
                    img_xianshi_yincang_two.setVisibility(View.VISIBLE);
                }
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    et_passwordc.setText(str1);
                    et_passwordc.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        ed_invtation_code.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!et_name.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty() && !et_passwordc.getText().toString().isEmpty() && !ed_invtation_code.getText().toString().isEmpty() && isSelect) {
//                    tv_create_wallet.setSelected(true);
//                    tv_create_wallet.setClickable(true);
//                    tv_create_wallet.setAlpha(Float.valueOf("1.0"));
//                } else {
//                    tv_create_wallet.setClickable(false);
//                    tv_create_wallet.setAlpha(Float.valueOf("0.3"));
//                }
//                if (s.toString().contains(" ")) {
//                    String[] str = s.toString().split(" ");
//                    String str1 = "";
//                    for (int i = 0; i < str.length; i++) {
//                        str1 += str[i];
//                    }
//                    ed_invtation_code.setText(str1);
//                    ed_invtation_code.setSelection(start);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        Log.e("FF987", "内容:" + getClickableSpan(tv_intent.getText().toString()));
//        tv_intent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void bindView(Bundle bundle) {
        DaoSession daoSession = MyApplication.getDaoSession();
        userDao = daoSession.getUserDao();

        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            tv_title.setText(getResources().getString(R.string.create_wallet));
            tv_create_wallet.setText(getResources().getString(R.string.create_wallet));
        } else {
            tv_title.setText(getResources().getString(R.string.import_wallet));
            tv_create_wallet.setText(getResources().getString(R.string.import_wallet));
        }
    }

    @OnClick({R.id.tv_create_wallet, R.id.iv_select, R.id.tv_intent, R.id.iv_finish,R.id.tv_intent_,R.id.cre_lin_xianshi_yincang_two,R.id.cre_lin_xianshi_yincang})
    public void setOnclick(View view) {
//        if(FastClick.isFastClick()) {

        switch (view.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.cre_lin_xianshi_yincang:
                isPswVisible1 = !isPswVisible1;
                if (isPswVisible1) {
                    //设置图片，隐藏或者显示图片
                    img_xianshi_yincang.setImageResource(R.mipmap.xianshi_new);
                    //显示密码方法一
                    HideReturnsTransformationMethod method2 = HideReturnsTransformationMethod.getInstance();
                    et_password.setTransformationMethod(method2);
                } else {
                    //设置图片，隐藏或者显示图片
                    img_xianshi_yincang.setImageResource(R.mipmap.yincang_new);
                    //隐藏密码方法一
                    PasswordTransformationMethod method1 = PasswordTransformationMethod.getInstance();
                    et_password.setTransformationMethod(method1);
                }
                //切换后将EditText光标置于末尾
                et_password.setSelection(et_password.getText().toString().length());
                break;
            case R.id.cre_lin_xianshi_yincang_two:
                isPswVisible2 = !isPswVisible2;
                if (isPswVisible2) {
                    //设置图片，隐藏或者显示图片
                    img_xianshi_yincang_two.setImageResource(R.mipmap.xianshi_new);
                    //显示密码方法一
                    HideReturnsTransformationMethod method2 = HideReturnsTransformationMethod.getInstance();
                    et_passwordc.setTransformationMethod(method2);
                } else {
                    //设置图片，隐藏或者显示图片
                    img_xianshi_yincang_two.setImageResource(R.mipmap.yincang_new);
                    //隐藏密码方法一
                    PasswordTransformationMethod method1 = PasswordTransformationMethod.getInstance();
                    et_passwordc.setTransformationMethod(method1);
                }
                //切换后将EditText光标置于末尾
                et_passwordc.setSelection(et_passwordc.getText().toString().length());
                break;
            case R.id.tv_create_wallet:
                if (MoreClick.MoreClicks()) {
                    if (!isSelect) {
                        ToastUtils.showToast(this, getResources().getString(R.string.please_check_user_agreement));
                        return;
                    }

                    if (TextUtils.isEmpty(et_name.getText().toString().replace(" ", ""))) {
                        ToastUtils.showToast(this, getResources().getString(R.string.please_enter_wallet_name));
                        return;
                    }

                    if (TextUtils.isEmpty(et_password.getText().toString().replace(" ", ""))) {
                        ToastUtils.showToast(this, getResources().getString(R.string.please_enter_secure_password));
                        return;
                    }

                    if (!StringUtils.rexCheckPassword(et_password.getText().toString().replace(" ", ""))) {
                        ToastUtils.showToast(this, getResources().getString(R.string.the_password_combination));
                        return;
                    }

                    if (TextUtils.isEmpty(et_passwordc.getText().toString().replace(" ", ""))) {
                        ToastUtils.showToast(this, getResources().getString(R.string.please_enter_security_password_again));
                        return;
                    }

                    if (!et_passwordc.getText().toString().replace(" ", "").equals(et_password.getText().toString().replace(" ", ""))) {
                        ToastUtils.showToast(this, getResources().getString(R.string.the_two_passwords_not_consistent));
                        return;
                    }
                    User user = new User();
                    user.setName(et_name.getText().toString().replace(" ", ""));

                    List<User> users_list = userDao.loadAll();


                    if (type.equals("1")) {
                        if (users_list != null && users_list.size() > 0 && !TextUtils.isEmpty(et_name.getText())) {
                            for (int i = 0; i < users_list.size(); i++) {
                                if (users_list.get(i).getName() != null) {
                                    if (users_list.get(i).getName().equals(et_name.getText().toString().trim())) {
                                        ToastUtils.showToast(CreateUserActivity.this, getResources().getString(R.string.the_user_already_exists_locally));
                                        return;
                                    }
                                }
                            }
                        }
                        String sha = AESEncrypt.sha(et_password.getText().toString().replace(" ", ""));
                        user.setPsd(sha);
                        Intent intent = new Intent(CreateUserActivity.this, SelectedLianActivity.class);
                        intent.putExtra("psd", sha);
                        intent.putExtra("name", et_name.getText().toString().replace(" ", ""));
                        intent.putExtra("add", getIntent().getStringExtra("add"));
                        startActivity(intent);
                    } else {
                        for (int i = 0; i < users_list.size(); i++) {
                            if (users_list.get(i).getName().equals(et_name.getText().toString())) {
                                ToastUtils.showToast(CreateUserActivity.this, getResources().getString(R.string.isuser));
                                return;
                            }
                        }
                        Log.e("FFF444", "3333");
                        //todo  通过SDK把助记词拿出
                        String sha = AESEncrypt.sha(et_password.getText().toString().replace(" ", ""));
                        user.setPsd(sha);

                        Intent intent = new Intent(CreateUserActivity.this, LoadWalletActivity.class);
                        intent.putExtra("psd", sha);
                        intent.putExtra("name", et_name.getText().toString().replace(" ", ""));
                        intent.putExtra("add", getIntent().getStringExtra("add"));
                        startActivity(intent);
                    }

                }


                break;
            case R.id.iv_select:
                isSelect = !isSelect;
                if (isSelect) {
                    iv_select.setSelected(true);


                    if (TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString())
                            || TextUtils.isEmpty(et_passwordc.getText().toString())) {
                        tv_create_wallet.setClickable(false);
                        tv_create_wallet.setAlpha(Float.valueOf("0.3"));
                    } else {
                        tv_create_wallet.setClickable(true);
                        tv_create_wallet.setAlpha(Float.valueOf("1.0"));
                    }

                } else {
                    iv_select.setSelected(false);
                    tv_create_wallet.setClickable(false);
                    tv_create_wallet.setAlpha(Float.valueOf("0.3"));
                }

                break;
            case R.id.tv_intent:
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(CreateUserActivity.this, AdWebActivity.class);
                    intent.putExtra("banner_url", UrlConstant.baseWap + "setting/service-agreement");
                    intent.putExtra("hideTitle", true);
                    startActivity(intent);
                }
                break;
            case R.id.tv_intent_:
                if (MoreClick.MoreClicks()) {
                    Intent intent2 = new Intent(CreateUserActivity.this, AdWebActivity.class);
                    intent2.putExtra("banner_url", UrlConstant.baseWap + "setting/service-agreement");
                    intent2.putExtra("hideTitle", true);
                    startActivity(intent2);
                }
                break;
        }
    }

    private SpannableString getClickableSpan(String text) {
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FF987", "click");
                Intent intent2 = new Intent(CreateUserActivity.this, AdWebActivity.class);
                intent2.putExtra("banner_url", "file:///android_asset/gpb_privacy.html");
                intent2.putExtra("hideTitle", true);
                startActivity(intent2);
            }
        };

        SpannableString spanableInfo = new SpannableString(text);
//        int start = spanableInfo.length() - 23;
        int start = spanableInfo.length() - 8;
        int end = spanableInfo.length();
//        spanableInfo.setSpan(new Clickable(l), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new Clickable(l), 5, 10, 0);
        return spanableInfo;
    }

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);//是否有下划线
        }

        @Override
        public void onClick(View v) {
            if (null != mListener) {
                mListener.onClick(v);
            }
        }
    }
}