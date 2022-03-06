package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.common.util.CommonUtil;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.SelectAdapter;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectedLianActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private static final String TAG = "SelectedLianActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_nest)
    TextView tv_nest;
    @BindView(R.id.iv_finish)
    LinearLayout iv_finish;
    private List<String> list=new ArrayList();
    SelectAdapter selectAdapter=new SelectAdapter(R.layout.select_item,new ArrayList<>());
    private  String name;
    private String psd;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_selected_lian;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        CommonUtil.setStatusBarColor(this, 2);


    }

    @Override
    public void bindView(Bundle bundle) {
        list.add("UNIQUE");
//        list.add("GAUSS");
//        list.add("USDG");
        list.add("ETH");
        list.add("TRON");
//        list.add("ATOM");
//        list.add("BTC");
//        list.add("DOT");
//        list.add("IGPC");
//        list.add("FEC");
        name=  getIntent().getStringExtra("name");
        psd=   getIntent().getStringExtra("psd");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(selectAdapter);
        selectAdapter.setNewData(list);
        selectAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.tv_nest,R.id.iv_finish})
    public void setOnClick(View view){
            switch (view.getId()){
                case R.id.tv_nest:
                    if (MoreClick.MoreClicks()) {
                        Intent intent = new Intent(this, BackupsActivity.class);
                        intent.putExtra("name", name);
                        Log.d(TAG, "setOnClickView: " + psd);
                        intent.putExtra("psd", psd);
                        intent.putExtra("add", getIntent().getStringExtra("add"));
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_finish:
                finish();
                    break;
            }


    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showShort(this,"默认选择");
    }
}