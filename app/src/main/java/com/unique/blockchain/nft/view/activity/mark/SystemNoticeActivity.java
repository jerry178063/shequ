package com.unique.blockchain.nft.view.activity.mark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.net.utils.NetworkUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.HelpCenterAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.discover.GetArticleListBean;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.utils.PhoneUtils;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 *
 * 发现-系统通知列表
 * */
public class SystemNoticeActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    private UserDao userDao;
    private List<User> users_list;
    private HelpCenterAdapter helpCenterAdapter=new HelpCenterAdapter(R.layout.help_center_item,this,new ArrayList<>());
    private List<GetArticleListBean.Rows> rows = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("通知");
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    String banner_url;

    @Override
    public void initData() {
        getData();
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                //判断是否有网络
                if (!PhoneUtils.isNetworkConnected(SystemNoticeActivity.this)) {
//                    ToastaUtils toastaUtils = new ToastaUtils(SystemNoticeActivity.this, getResources().getString(R.string.the_network_is_not_open));
//                    toastaUtils.show();
                    ToastUtil.showShort(SystemNoticeActivity.this, getResources().getString(R.string.the_network_is_not_open));
                } else {
                    getData();
                }
                smr.finishRefresh();
            }
        });


        String js = "<script type=\"text/javascript\">"+
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(helpCenterAdapter);
        helpCenterAdapter.setNewData(rows);
        helpCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                if(FastClick.isFastClick()) {
                    if (NetworkUtil.isConnected(SystemNoticeActivity.this)) {
//                        Intent intent = new Intent(SystemNoticeActivity.this, AdWebActivity.class);
                        Intent intent = new Intent(SystemNoticeActivity.this, SystemNoticeContentActivity.class);
                        intent.putExtra("id", rows.get(i).getId() + "");
                        intent.putExtra("title", rows.get(i).getTitle() + "");
                        intent.putExtra("time", rows.get(i).getReleaseTime() + "");
                        startActivity(intent);
                    } else {
                        ToastUtil.showShort(SystemNoticeActivity.this, getResources().getString(R.string.the_network_is_not_open));
                    }
//                }
            }
        });
    }
    private void getData() {
//        DaoSession daoSession = MyApplication.getDaoSession();
//        if(users_list != null){
//            users_list.clear();
//        }
//
//        userDao = daoSession.getUserDao();
//        users_list = userDao.loadAll();
        showDialog();
        OkGo.get(UrlConstant.baseCreateInportWallet + "operation/notice/pushNoticeList")
                .readTimeOut(10000)
                .execute(new JsonCallback<GetArticleListBean>() {
                    @Override
                    public void onSuccess(GetArticleListBean jsonObject, Call call, Response response) {
                        Log.e("FFKKL2","jsonObject:" + new Gson().toJson(jsonObject));
                        dismissDialog();
                        if(jsonObject != null && jsonObject.getRows() != null && jsonObject.getRows().size() > 0) {
                            if(rows != null){
                                rows.clear();
                            }
                            rows.addAll(jsonObject.getRows());
                            helpCenterAdapter.notifyDataSetChanged();
                            lin_no_data.setVisibility(View.GONE);
                        }else {
                            lin_no_data.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        dismissDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                    }
                });


    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
