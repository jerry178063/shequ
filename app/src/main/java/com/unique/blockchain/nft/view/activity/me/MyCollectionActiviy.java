package com.unique.blockchain.nft.view.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ColllecAllAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.constant.UrlConstant;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.CollectionBean;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.infrastructure.utils.MoreClick;
import com.unique.blockchain.nft.net.JsonCallback;
import com.unique.blockchain.nft.view.activity.mark.PaiActivity;
import com.unique.blockchain.nft.view.activity.mark.SouCollectActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.me.me_presenter.IMeCollectionPresenter;
import com.unique.blockchain.nft.view.activity.me.me_presenter.impl.IMeCollectionPresenterImpl;
import com.unique.blockchain.nft.view.activity.me.me_view.IMeCollectionCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectionActiviy extends BaseActivity implements IMeCollectionCallBack {

    private RecyclerView all_rec;
    private ColllecAllAdapter markAllAdapter;
    private IMeCollectionPresenter iMeCollectionPresenter;
    private List<QuanDatabase.RowsBean> rows;
    private int page = 1;
    SmartRefreshLayout smr;
    public User unique;
    public UserDao userDao;
    public String uniqueAdress;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    @BindView(R.id.liu_souyy)
    ImageView liu_souyy;
    @BindView(R.id.rela_all_choose)
    RelativeLayout rela_all_choose;
    private boolean isVisible,isChooseAll;
    @BindView(R.id.img_choose_img)
    ImageView img_choose_img;
    private List<String> sizeList = new ArrayList<>();
    private String nftIdDeiete;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
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
                    uniqueAdress = unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        all_rec = findViewById(R.id.All_rec);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        all_rec.setLayoutManager(gridLayoutManager);
        rows = new ArrayList<>();
        markAllAdapter = new ColllecAllAdapter(R.layout.search,rows);
        all_rec.setAdapter(markAllAdapter);


//        markAllAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
//            @Override
//            public void outPrice(int position) {
//                Log.e("FF3331","position:" + position + "-----" + rows.get(position).getSellMode());
//                if(rows.get(position).getSellMode() == 1){//购买
//                    Intent intent = new Intent(MyCollectionActiviy.this, BuyMarkActivity.class);
//                    intent.putExtra("title",rows.get(position).getNftName());
//                    intent.putExtra("nftId",rows.get(position).getNftId());
//                    intent.putExtra("uniqueAdress",uniqueAdress);
//                    intent.putExtra("productType",rows.get(position).getProductType() + "");
//                    startActivity(intent);
//                    Log.e("FF3331","购买");
//                }else if(rows.get(position).getSellMode() == 2){//出价
//                    Intent intent = new Intent(MyCollectionActiviy.this, OutPriceActivity.class);
//                    intent.putExtra("title",rows.get(position).getNftName());
//                    intent.putExtra("nftId",rows.get(position).getNftId());
//                    intent.putExtra("uniqueAdress",uniqueAdress);
//                    intent.putExtra("productType",rows.get(position).getProductType() + "");
//                    startActivity(intent);
//                    Log.e("FF3331","出价");
//                }
//            }
//        });
        smr = findViewById(R.id.smr);
        smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMeCollectionPresenter != null) {
                    page = 1;
                    iMeCollectionPresenter.getData("",uniqueAdress,page, 10);
                    markAllAdapter.cancelAllTimers();
                }
            }
        });
        smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iMeCollectionPresenter != null) {
                    iMeCollectionPresenter.getData("",uniqueAdress,page, 10);
                }
            }
        });
        markAllAdapter.setCancelChooseListener(new ColllecAllAdapter.Cancel() {
            @Override
            public void cancelChoose(String isCancel, int position) {
                if(isCancel.equals("cancel")){
                    isChooseAll = false;
                    img_choose_img.setImageDrawable(getResources().getDrawable(R.mipmap.unchoose_collect));
                    rows.get(position).setChooseCollect(false);
                    markAllAdapter.notifyDataSetChanged();
                    for (int i = 0; i < sizeList.size(); i++){
                        if(sizeList.get(i).equals(rows.get(position).getNftId())){
                            sizeList.remove(i);
                        }
                    }

                }else if(isCancel.equals("on_cancel")){
                    if(sizeList != null){
                        sizeList.clear();
                    }
                    rows.get(position).setChooseCollect(true);
                    for (int i = 0; i < rows.size(); i++){
                        Log.e("FGG33","choose:" + rows.get(i).isChooseCollect());
                        if(rows.get(i).isChooseCollect()){
                            sizeList.add("" + rows.get(i).getNftId());
                        }
                    }
                    Log.e("FGG33",sizeList.size() + "------" + rows.size());
                    if(sizeList.size() >= rows.size()){
                        img_choose_img.setImageDrawable(getResources().getDrawable(R.mipmap.choose_collect));
                    }else {
                        img_choose_img.setImageDrawable(getResources().getDrawable(R.mipmap.unchoose_collect));
                    }
                    markAllAdapter.notifyDataSetChanged();
                }

            }
        });
        markAllAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if(!isVisible) {
                    if (MoreClick.MoreClicks()) {
                        Intent intent = new Intent(MyCollectionActiviy.this, PaiActivity.class);
                        intent.putExtra("nftId", rows.get(i).getNftId());
                        intent.putExtra("sellStatus", rows.get(i).getSellState() + "");
                        intent.putExtra("surplus", rows.get(i).getSurplus() + "");
                        intent.putExtra("collect","collect");
                        startActivity(intent);
                    }
                }
            }
        });
//        markAllAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                ToastUtil.showShort(MyCollectionActiviy.this,"长按");
//                return false;
//            }
//        });
//        markAllAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
//            @Override
//            public boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                ToastUtil.showShort(MyCollectionActiviy.this,"长按2222");
//                return false;
//            }
//        });
    }

    @Override
    public void initData() {
        iMeCollectionPresenter = new IMeCollectionPresenterImpl();
        iMeCollectionPresenter.registerViewCallback(this);
        iMeCollectionPresenter.getData("",uniqueAdress,page, 10);
    }
    @OnClick({R.id.img_fan_Grii,R.id.liu_souyy,R.id.tv_manager,R.id.img_choose_img,R.id.tv_choose_all,R.id.tv_deleCollect})
    public void setOnclick(View view) {
        switch (view.getId()){
            case R.id.img_fan_Grii://返回
                finish();
                break;
            case R.id.liu_souyy://搜索
                if (MoreClick.MoreClicks()) {
                    Intent intent = new Intent(MyCollectionActiviy.this, SouCollectActivity.class);
                    intent.putExtra("collect","0");
                    intent.putExtra("uniqueAdress",uniqueAdress);
                    startActivity(intent);
                }
                break;
            case R.id.tv_manager://管理
                if(!isVisible) {
                    isVisible = true;
                    if(!TextUtils.isEmpty(rows.toString()) && rows.size() > 0){
                        rela_all_choose.setVisibility(View.VISIBLE);
                    }else {
                        rela_all_choose.setVisibility(View.GONE);
                    }

                    markAllAdapter.setCollectVisit(1,"manager");
                }else {
                    isVisible = false;
                    rela_all_choose.setVisibility(View.GONE);
                    markAllAdapter.setCollectVisit(0,"manager");
                }
                break;
            case R.id.img_choose_img://全选
            case R.id.tv_choose_all:
                if(!isChooseAll){
                    isChooseAll = true;
//                    markAllAdapter.setChooseOrUn(1,"choose");
                    for (int i =0; i < rows.size();i++){
                        rows.get(i).setChooseCollect(true);
                    }

                    if(sizeList != null){
                        sizeList.clear();
                    }
                    for (int i = 0; i < rows.size(); i++){
                        if(rows.get(i).isChooseCollect()){
                            sizeList.add("" + rows.get(i).getNftId());
                        }
                    }
                    img_choose_img.setImageDrawable(getResources().getDrawable(R.mipmap.choose_collect));
                }else {
                    isChooseAll = false;
//                    markAllAdapter.setChooseOrUn(0,"choose");
                    for (int i =0; i < rows.size();i++){
                        rows.get(i).setChooseCollect(false);
                    }
                    if(sizeList != null && sizeList.size() > 0) {
                        for (int i = 0; i < sizeList.size(); i++) {
                            sizeList.clear();
                        }
                    }
                    img_choose_img.setImageDrawable(getResources().getDrawable(R.mipmap.unchoose_collect));
                }
                markAllAdapter.notifyDataSetChanged();

                break;
            case R.id.tv_deleCollect://取消收藏
                if (MoreClick.MoreClicks()) {
                    deleteCollect();
                }
                break;
        }
    }

    private void deleteCollect() {
        nftIdDeiete = "";
        if(sizeList != null && sizeList.size() > 0){
            if(sizeList.size() == 1){
                nftIdDeiete = sizeList.get(0);
                Log.e("FF4322ff","nftIdDeiete:" + nftIdDeiete);
            }else if(sizeList.size() > 1){
                for (int i = 0; i < sizeList.size(); i++){
                    nftIdDeiete += sizeList.get(i) + ",";
                }
                Log.e("FF4322ff","nftIdDeiete:" + nftIdDeiete.substring(0,nftIdDeiete.length() - 1));
            }
        }else {
            ToastUtil.showShort(MyCollectionActiviy.this,"请选择nft产品!");
            return;
        }
        Log.e("FF4322ff","sizeList:" + sizeList);


        OkGo.post(UrlConstant.baseUrl + "api/collection/del")
                .params("nftId",nftIdDeiete)
                .params("walletAddr",uniqueAdress)
                .readTimeOut(10000)
                .execute(new JsonCallback<CollectionBean>() {
                    @Override
                    public void onSuccess(CollectionBean jsonObject, okhttp3.Call call, okhttp3.Response response) {
                        if(jsonObject.getCode() == 200) {
                            Log.e("FF4322ff", "1111:" + jsonObject);
                            ToastUtil.showShort(MyCollectionActiviy.this,"取消成功!");
                            rela_all_choose.setVisibility(View.GONE);
                            if(iMeCollectionPresenter != null) {
                                page = 1;
                                iMeCollectionPresenter.getData("",uniqueAdress,page, 10);
                                markAllAdapter.cancelAllTimers();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String code, String message) {
                        super.onFailure(code, message);
                        Log.e("FF4322ff", "4444");
                    }

                    @Override
                    public void onError(okhttp3.Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("FF3332", "onError");
                    }
                });

    }

    @Override
    public void bindView(Bundle bundle) {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void loadCollectionData(QuanDatabase quanDatabase) {
        if(quanDatabase != null && quanDatabase.getCode() == 200){
            Log.e("FF4322ff","刷新...00000.");

            if (page == 1) {//首页
                if (rows != null) {
                    rows.clear();
                }
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0) {

                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    if(tv_no_data != null) {
                        tv_no_data.setVisibility(View.GONE);
                    }
                    page++;
                }else {
                    if(tv_no_data != null) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    }
                }
                if(smr != null){
                    smr.finishRefresh();
                    smr.finishLoadMore();
                }
                markAllAdapter.setNewData(rows);
            }else {//多页
                tv_no_data.setVisibility(View.GONE);
                Log.e("收到刷新","多页:" + page + "--size-:" + quanDatabase.getRows().size());
                if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() == 10) {
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    page++;
                    markAllAdapter.setNewData(rows);
                }else if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0 && quanDatabase.getRows().size() < 10) {
                    rows.addAll(quanDatabase.getRows());
                    markAllAdapter.notifyDataSetChanged();
                    page++;
                    markAllAdapter.setNewData(rows);
                }else{

                }
                if(smr != null){
                    smr.finishLoadMore();
                }
            }
        }else {
            if(smr != null){
                smr.finishRefresh();
                smr.finishLoadMore();
            }
        }

    }

    @Override
    public void loadCollectionFail() {
        if(smr != null){
            smr.finishRefresh();
        }
        if(smr != null){
            smr.finishLoadMore();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(markAllAdapter != null){
            markAllAdapter.cancelAllTimers();
        }
    }

}
