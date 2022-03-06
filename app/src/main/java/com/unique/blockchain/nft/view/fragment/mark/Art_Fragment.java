package com.unique.blockchain.nft.view.fragment.mark;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.MarkAllAdapter;
import com.unique.blockchain.nft.adapter.markAdapter.CommAdapter;
import com.unique.blockchain.nft.domain.mark.QuanDatabase;
import com.unique.blockchain.nft.view.activity.mark.presenter.RePresenter;
import com.unique.blockchain.nft.view.activity.mark.ui.BaseMvpFragment;
import com.unique.blockchain.nft.view.activity.mark.ui.BuyMarkActivity;
import com.unique.blockchain.nft.view.activity.mark.ui.OutPriceActivity;
import com.unique.blockchain.nft.view.activity.mark.view.Re_view;
import com.unique.blockchain.nft.view.fragment.mark.presenter.IMarkOtherPresenter;
import com.unique.blockchain.nft.view.fragment.mark.presenter.impl.IMarkOtherPresenterImpl;
import com.unique.blockchain.nft.view.fragment.mark.view.IMarkOtherCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 市场-浏览-艺术品
 * */
public class Art_Fragment extends BaseMvpFragment<Re_view, RePresenter> implements IMarkOtherCallBack {
    private RecyclerView art_rec;
    private MarkAllAdapter markAllAdapter;

    private IMarkOtherPresenter iMarkOtherPresenter;
    private List<QuanDatabase.RowsBean> rows;
    private int page = 1;
    @Override
    public void onEut(String onEut) {

    }

    @Override
    protected void initData() {
        iMarkOtherPresenter = new IMarkOtherPresenterImpl();
        iMarkOtherPresenter.registerViewCallback(this);
        iMarkOtherPresenter.getData(page,10,"","3");
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_art_;
    }

    @Override
    protected RePresenter initPresenter() {
        return new RePresenter();
    }



    @Override
    protected void initView(View view) {
        super.initView(view);
        art_rec = view.findViewById(R.id.Art_rec);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        art_rec.setLayoutManager(gridLayoutManager);
        markAllAdapter = new MarkAllAdapter(R.layout.search,rows);
        art_rec.setAdapter(markAllAdapter);
        rows = new ArrayList<>();

        markAllAdapter.setOutPriceListener(new CommAdapter.OutPrice() {
            @Override
            public void outPrice(int position) {
                Log.e("FF3331","position:" + position + "-----" + rows.get(position).getSellMode());
                if(rows.get(position).getSellMode() == 1){//购买
                    Intent intent = new Intent(getContext(), BuyMarkActivity.class);
                    intent.putExtra("title",rows.get(position).getNftName());
                    startActivity(intent);
                    Log.e("FF3331","购买");
                }else if(rows.get(position).getSellMode() == 2){//出价
                    Intent intent = new Intent(getContext(), OutPriceActivity.class);
                    startActivity(intent);
                    Log.e("FF3331","出价");
                }
            }
        });
    }


    @Override
    public void loadData(QuanDatabase quanDatabase) {
        if(quanDatabase != null && quanDatabase.getCode() == 200){
            if(quanDatabase.getRows() != null && quanDatabase.getRows().size() > 0){
                if(page == 1) {
                    if (rows != null){
                        rows.clear();
                    }
                    rows.addAll(quanDatabase.getRows());
                }
            }
        }
        markAllAdapter.setNewData(rows);
    }

    @Override
    public void loadFail() {

    }
}