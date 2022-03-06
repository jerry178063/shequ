package com.space.exchange.biz.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.githang.statusbar.StatusBarCompat;
import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.util.LoadiangUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends SupportActivity implements BaseViewInterface,View.OnClickListener {
    private Unbinder mUnBinder;
    private long lastClick;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTransparent();
        setContentView(getLayoutId());
        StatusBarCompat.setStatusBarColor(this,getStatusColor(),isLightBar());
        EventBus.getDefault().register(this);
        mUnBinder = ButterKnife.bind(this);
        dialog = LoadiangUtil.showLoadingDialog(this);
        bindView(savedInstanceState);
        initUI();
        initData();
    }

    protected int getStatusColor(){
        return Color.WHITE;
    }

    protected boolean isLightBar(){
        return true;
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if (fastClick()){
            widgetClick(v);
        }
    }
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();

        Configuration configuration = new Configuration();

        configuration.setToDefaults();

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return resources;

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }
    protected void widgetClick(View v) {

    }

    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    protected Dialog getLoadingDialog(){
        return dialog;
    }

    protected void  showDialog(){
        if (dialog != null){
            dialog.show();
        }
    }

    protected void dismissDialog(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void onEventBusMain(BaseEvent bean){
        eventBusMainThread(bean);
    }

    protected void eventBusMainThread(BaseEvent bean){

    }

    public void setBarTransparent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT); //改为透明
            } catch (Exception e) {}
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }
    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
