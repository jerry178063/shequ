package com.space.exchange.biz.common.base;

/**
 * Created by Administrator on 2018/10/31.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.util.LoadiangUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * The class <code>Class BaseFragment</code>.
 *
 * @author Tonghu Lei
 * @version 1.0
 */
public abstract class BaseFragment extends SupportFragment {

    /**
     *
     * 片段基类的工作
     *
     * -> EventBus
     * -> LoadingDialog
     *
     */
    protected View mView;
    private Unbinder bind;
    private Dialog dialog;

    protected boolean isVisible;

    //是否是第一次开启网络加载
    public boolean isFirst=true;
    private boolean isPrepared;

    protected View findViewById(int id) {
        if (null != getView())
            return getView().findViewById(id);
        return null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mView = inflater.inflate(getLayoutId(), container, false);
        //StatusBarCompat.setStatusBarColor(getActivity(), R.color.color_some_tv);
        bind = ButterKnife.bind(this, mView);
        return mView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        dialog = LoadiangUtil.showLoadingDialog(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        initView();
        initData();
        lazyLoad();
    }

    protected void initData() {

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();

        } else {

            isVisible = false;
            onInvisible();

        }
    }
    /**
     * layout xml文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();
    /**
     * 控件初始化
     */
    public abstract void initView();
    public void lazyLoad() {

        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }

        isFirst = false;
        requestData();

    }

    protected void requestData(){

    }

    /*
    不可见时会走
     */
    protected void onInvisible() {

    }

    protected Dialog getLoadingDialog(){
        return dialog;
    }

    protected void  showDialog(){
        if (dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    protected void dismissDialog(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void onEventBusMain(BaseEvent bean){
        eventBusMainThread(bean);
    }

    protected void eventBusMainThread(BaseEvent bean){}

    /**
     * 监听返回键,返回true,自己消耗该事件,false,返回上层处理
     */
    protected boolean onBackPressed() {
        return false;
    }

    //    刷新数据
    public void refreshData(){

    }

}
