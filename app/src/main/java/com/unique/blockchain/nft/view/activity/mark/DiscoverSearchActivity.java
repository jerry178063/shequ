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
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.DiscoverDiscoverAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.discover.GetArticleListBean;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 发现-搜索
 * */
public class DiscoverSearchActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.smr)
    SmartRefreshLayout smr;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_no_data)
    LinearLayout lin_no_data;
    private String content;
    private DiscoverDiscoverAdapter discoverDiscoverAdapter = new DiscoverDiscoverAdapter(R.layout.discover_discover_item, new ArrayList<>());
    private List<GetArticleListBean.Rows> rows = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_discover_search;
    }

    @Override
    public void initUI() {
        content = getIntent().getStringExtra("content");
        top_s_title_toolbar.setLeftTitleText(content);
        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                getArticle();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DiscoverSearchActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(discoverDiscoverAdapter);
        discoverDiscoverAdapter.setNewData(rows);

        discoverDiscoverAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(DiscoverSearchActivity.this, NewsContentActivity.class);
                intent.putExtra("banner_url", rows.get(i).getContent() + "");
                intent.putExtra("title", rows.get(i).getTitle() + "");
                intent.putExtra("time", rows.get(i).getReleaseTime() + "");
                intent.putExtra("id", rows.get(i).getId() + "");
                startActivity(intent);
//                ToastUtil.showShort(DiscoverSearchActivity.this, "敬请期待!");
            }
        });
        getArticle();
    }
    private void getArticle() {
        Log.e("FFF766","输入neir:" + content.trim());
        OkGo.get(UrlConstant.baseCreateInportWallet + "operation/article/fuzzyQuery")
                .params("fuzzyInfo",content.trim())
                .readTimeOut(10000)
                .execute(new JsonCallback<GetArticleListBean>() {
                    @Override
                    public void onSuccess(GetArticleListBean jsonObject, Call call, Response response) {
                        Log.e("FFF766","返回查询内容:" + new Gson().toJson(jsonObject));
                        smr.finishRefresh();
                        dismissDialog();
                        if (jsonObject != null && jsonObject.getRows() != null && jsonObject.getRows().size() > 0) {

                            if (rows != null) {
                                rows.clear();
                            }
                            rows.clear();
                            rows.addAll(jsonObject.getRows());
                            discoverDiscoverAdapter.notifyDataSetChanged();

                            discoverDiscoverAdapter.getContext(DiscoverSearchActivity.this);

                            if (rows != null && rows.size() > 0) {
                                if(lin_no_data != null) {
                                    lin_no_data.setVisibility(View.GONE);
                                }
                                if(recyclerView != null){
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if(lin_no_data != null) {
                                    lin_no_data.setVisibility(View.VISIBLE);
                                }
                                if(recyclerView != null){
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }
                        }else {
                            if(lin_no_data != null){
                                lin_no_data.setVisibility(View.VISIBLE);
                            }
                            if(recyclerView != null){
                                recyclerView.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FFF766","返回查询内容onFailure:" + code);
                        dismissDialog();
                        smr.finishRefresh();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dismissDialog();
                        smr.finishRefresh();
                    }
                });

    }
    @Override
    public void initData() {

    }

    @Override
    public void bindView(Bundle bundle) {

    }
}
