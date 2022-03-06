package com.unique.blockchain.nft.infrastructure.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.adapter.ShanDuiTypeAdapter;
import com.unique.blockchain.nft.domain.wallet.ChainConfigBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 闪兑类型选择
 *
 * */
public class ShanDuiNftTypeDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnSafeClickView onClickView;
    private ItemClickView itemClickView;
    private RecyclerView recyclerView;
    private int tag = 0;
    private ShanDuiTypeAdapter shanDuiTypeAdapter;
    private List<ChainConfigBean.Rows> mDdata = new ArrayList<>();
    public ShanDuiNftTypeDialog(@NonNull @NotNull Context context,List<ChainConfigBean.Rows> mDdata) {
        this(context, R.style.customor_service);
        this.mContext = context;
        this.mDdata = mDdata;
    }

    public ShanDuiNftTypeDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    EditText et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.shandui_type_dialog_layout, null);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);
        TextView tv_sure=view.findViewById(R.id.tv_sure);

        et_pass = view.findViewById(R.id.et_pass);
        recyclerView = view.findViewById(R.id.recyclew_shandui);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        shanDuiTypeAdapter = new ShanDuiTypeAdapter(R.layout.shandui_dialog_item,mDdata);
        recyclerView.setAdapter(shanDuiTypeAdapter);

        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*1);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        shanDuiTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                shanDuiTypeAdapter.setPosition(i);
                tag = i;
            }
        });
    }


    public void setOnclick(OnSafeClickView onclick){
    this.onClickView=onclick;
    }
    public interface OnSafeClickView{
        void setOnSafeClickView(String et_pass);
    }
    public void setItemClickView(ItemClickView itemClickView){
        this.itemClickView = itemClickView;
    }
    public interface ItemClickView{
        void setItemClickView(int tag);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    if(itemClickView != null){
                        itemClickView.setItemClickView(tag);
                    }
                    dismiss();
                    break;

//                    tv_all.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                    tv_piaowu.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                    tv_yishupin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                    tv_qingshepin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//                    tv_collection.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));
//
//                    tv_all.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
//                    tv_piaowu.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
//                    tv_yishupin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
//                    tv_qingshepin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
//                    tv_collection.setTextColor(mContext.getResources().getColor(R.color.color_863A1C));

            }
    }
}
