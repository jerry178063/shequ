package com.unique.blockchain.nft.view.fragment.mark;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.space.exchange.biz.common.base.BaseFragment;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.NoticeAdapter;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;
import com.unique.blockchain.nft.messagedb.DBHelper;
import com.unique.blockchain.nft.view.activity.mark.SystemNoticeContentActivity;
import com.unique.blockchain.nft.view.activity.mark.presenter.INoticePresenter;
import com.unique.blockchain.nft.view.activity.mark.presenter.impl.INoticePresenterImpl;
import com.unique.blockchain.nft.view.activity.mark.view.INoticeCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * 系统消息
 *
 */
public class SystemFragment extends BaseFragment implements INoticeCallBack {

    private List<NoticeMessageBean.Rows> list;

    public User unique;
    public UserDao userDao;
    public  String uniqueAdress;
    private String TAG = "TradeFragment";

    RecyclerView recyclerView_personl;
    private NoticeAdapter noticeAdapter;
    private INoticePresenter iNoticePresenter;
    private int page = 1;
    @BindView(R.id.tv_no_data)
    LinearLayout tv_no_data;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.img_no_mess)
    ImageView img_no_mess;
    @BindView(R.id.tv_no_datas)
    TextView tv_no_datas;
    private List<NoticeMessageBean.Rows> rowsDb = new ArrayList<>();
    private NoticeMessageBean.Rows rowDb;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }
    @Override
    protected void initData() {
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
                    Log.e("FF322","gaussAdress:" + uniqueAdress);
                }
            }
        }
        showDialog();
        iNoticePresenter = new INoticePresenterImpl();
        iNoticePresenter.registerViewCallback(this);
        iNoticePresenter.getData(page,10,uniqueAdress,"0");
    }
    @Override
    public void initView() {

        recyclerView_personl = (RecyclerView) findViewById(R.id.recyclerView_personl);
        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_personl.setLayoutManager(linearLayoutManager);
        noticeAdapter = new NoticeAdapter(R.layout.fragment_personl_notice,list);
        recyclerView_personl.setAdapter(noticeAdapter);

        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iNoticePresenter != null) {
                    showDialog();
                    page = 1;
                    iNoticePresenter.getData(page, 10, uniqueAdress, "0");
                }
            }
        });
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                if(iNoticePresenter != null) {
                    showDialog();
                    iNoticePresenter.getData(page, 10, uniqueAdress, "0");
                }
            }
        });

        noticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                updateData(i);
                Intent intent = new Intent(getContext(), SystemNoticeContentActivity.class);
                intent.putExtra("id", rowsDb.get(i).getId() + "");
                intent.putExtra("type", "0");
                intent.putExtra("uniqueAdress",uniqueAdress);
                startActivity(intent);
            }
        });
        img_no_mess.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.no_notice));
        tv_no_datas.setText("无公告");
    }


    @Override
    public void loadNoticeData(NoticeMessageBean noticeMessageBean) {
        dismissDialog();
        if (noticeMessageBean != null && noticeMessageBean.getCode() == 200) {
            if (page == 1) {//首页
                if (list != null) {
                    list.clear();
                }
                if (noticeMessageBean.getRows() != null && noticeMessageBean.getRows().size() > 0) {
                    recyclerView_personl.setVisibility(View.VISIBLE);
                    if(tv_no_data != null) {
                        tv_no_data.setVisibility(View.GONE);
                    }

//                    list.addAll(noticeMessageBean.getRows());
                    insertData(noticeMessageBean.getRows());
                    noticeAdapter.notifyDataSetChanged();
                    Log.e("FF33325", "list_size:" + list.size());
                    page++;
                } else {
                    if (smartRefresh != null) {
                        smartRefresh.finishLoadMore();
                    }
                    if(recyclerView_personl != null) {
                        recyclerView_personl.setVisibility(View.GONE);
                    }
                    if(tv_no_data != null) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    }
                }
                if (smartRefresh != null) {
                    smartRefresh.finishRefresh();
                }
            } else {//多页
                recyclerView_personl.setVisibility(View.VISIBLE);
                tv_no_data.setVisibility(View.GONE);
                if (noticeMessageBean.getRows() != null && noticeMessageBean.getRows().size() > 0 && noticeMessageBean.getRows().size() == 10) {
                    list.addAll(noticeMessageBean.getRows());
                    noticeAdapter.notifyDataSetChanged();
                    page++;
                } else if (noticeMessageBean.getRows() != null && noticeMessageBean.getRows().size() > 0 && noticeMessageBean.getRows().size() < 10) {
                    list.addAll(noticeMessageBean.getRows());
                    noticeAdapter.notifyDataSetChanged();
                    page++;
                } else {

                }
                if (smartRefresh != null) {
                    smartRefresh.finishLoadMore();
                }
            }
        } else {
            smartRefresh.finishLoadMore();
            recyclerView_personl.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }
        noticeAdapter.setNewData(rowsDb);
    }

    private void insertData(List<NoticeMessageBean.Rows> rows) {



        DBHelper helperMess=new DBHelper(getContext(),"lqwvje.db");
        SQLiteDatabase dbMess=  helperMess.getReadableDatabase();



        for (int i = 0; i < rows.size();i++){
            ContentValues contentValues=new ContentValues();
            contentValues.put("ids",rows.get(i).getId());
            contentValues.put("noticeId",rows.get(i).getNoticeId());
            contentValues.put("type",rows.get(i).getType());
            contentValues.put("isRead","0");
            contentValues.put("title",rows.get(i).getTitle());
            contentValues.put("content",rows.get(i).getContent());
            contentValues.put("releaseTime",rows.get(i).getReleaseTime());
            Cursor cursor = dbMess.query("person",new String[]{"ids"},"ids" + "=?",new String[]{rows.get(i).getId() + ""},null,null,null);
            if(cursor != null && cursor.getCount() > 0){
                Log.e("FFF3222","cursor.getCount() > 0");
            }else {
                Log.e("FFF3222","cursor.getCount() < 0");
                dbMess.insert("person", null, contentValues);
            }
        }
        //查询数据库
        queryData();
    }
    //修改数据库
    private void updateData(int i) {
        DBHelper helperMess=new DBHelper(getContext(),"lqwvje.db");
        SQLiteDatabase dbMess=  helperMess.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("isRead","1");
        dbMess.update("person",contentValues,"noticeId=?",new String[]{rowsDb.get(i).getNoticeId() + ""});
        noticeAdapter.notifyDataSetChanged();
        noticeAdapter.setNewData(rowsDb);
    }
    private void queryData() {
        DBHelper helperMess=new DBHelper(getContext(),"lqwvje.db");
        SQLiteDatabase dbMess=  helperMess.getReadableDatabase();
        Cursor cursor = dbMess.query("person",null,null,null,null,null,null);
        if(!TextUtils.isEmpty(rowsDb.toString())){
            rowsDb.clear();
        }
        if(cursor.moveToFirst()){
            do{
                rowDb = new NoticeMessageBean.Rows();
                rowDb.setId(cursor.getInt(cursor.getColumnIndex("ids")));
                rowDb.setNoticeId(cursor.getInt(cursor.getColumnIndex("noticeId")));
                rowDb.setType(cursor.getInt(cursor.getColumnIndex("type")));
                rowDb.setIsRead(cursor.getString(cursor.getColumnIndex("isRead")));
                rowDb.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                rowDb.setReleaseTime(cursor.getString(cursor.getColumnIndex("releaseTime")));
                rowsDb.add(rowDb);
            }while (cursor.moveToNext());
        }
        Log.e("FFF3222","rowsDb:" + new Gson().toJson(rowsDb));
//        list.addAll(rowsDb);
    }

    @Override
    public void loadNoticeFail() {
        dismissDialog();
        if(smartRefresh != null){
            smartRefresh.finishRefresh();
        }
        if(smartRefresh != null){
            smartRefresh.finishLoadMore();
        }
    }
}