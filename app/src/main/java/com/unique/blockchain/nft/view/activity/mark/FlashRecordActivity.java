package com.unique.blockchain.nft.view.activity.mark;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.FlashRecordAdapter;
import com.unique.blockchain.nft.adapter.NoticeAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.home.FlashRecordBean;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.presenter.INoticePresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.INoticePresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.view.INoticeCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 兑换记录
 * */
public class FlashRecordActivity extends BaseActivity  {

    public User unique;
    public UserDao userDao;
    public  String uniqueAdress;

    RecyclerView recyclerView_personl;
    private FlashRecordAdapter noticeAdapter;
    private int page = 1;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.img_no_mess)
    ImageView img_no_mess;
    @BindView(R.id.top)
    RelativeLayout top;
    private List<FlashRecordBean> list;
    FlashRecordBean rows;
    private List<FlashRecordBean.Rows> rowsList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
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
            unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(FlashRecordActivity.this, Tools.name, ""))).build().unique();
        } catch (Exception e) {
        }
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("UNIQUE")) {
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
        top.setVisibility(View.VISIBLE);
//        showDialog();
        postData();
    }

    private void postData() {

        OkGo.get(UrlConstant.baseUrl + "mapping/list")
                .readTimeOut(10000)
                .execute(new JsonCallback<FlashRecordBean>() {
                    @Override
                    public void onSuccess(FlashRecordBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        Log.e("FF33325", "1111:" + jsonObject);

                        if(jsonObject.getCode() == 200){

                            if(rowsList != null){
                                rowsList.clear();
                            }
                            if(jsonObject.getRows() != null && jsonObject.getRows().size() > 0){

                                rowsList.addAll(jsonObject.getRows());
                                noticeAdapter.setNewData(rowsList);
                                tv_no_data.setVisibility(View.GONE);
                            }else {
                                tv_no_data.setVisibility(View.VISIBLE);
                            }

                        }else {
                            tv_no_data.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF3332", "4444");

                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");

                    }
                });


    }

    @OnClick({R.id.img_back})
    public void setOnClick(View view){
        switch (view.getId()){
            case R.id.img_back:
                if (MoreClick.MoreClicks()) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void initData() {
        recyclerView_personl = (RecyclerView) findViewById(R.id.recyclerView_personl);
        list = new ArrayList<>();

        rows = new FlashRecordBean();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_personl.setLayoutManager(linearLayoutManager);
        noticeAdapter = new FlashRecordAdapter(R.layout.activity_flash_record,this,rowsList);
        recyclerView_personl.setAdapter(noticeAdapter);

        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                postData();
                smartRefresh.finishRefresh();
            }
        });
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                smartRefresh.finishLoadMore();
            }
        });

        noticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
            }
        });
    }

    @Override
    public void bindView(Bundle bundle) {

    }


}
