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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.domain.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 跨回
 * */
public class CrossBackDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnClickView onClickView;
    RecyclerView recyclerView;
    private TextView tv_cancel,tv_sure;
    private List<User> users_list;
    private String users_item_name;

    private ArrayList <String>list=new ArrayList();

    public CrossBackDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public CrossBackDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.cross_back_dialog_layout, null);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        WindowManager windowManager=getWindow().getWindowManager();
//        Display display= windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
//        layoutParams.width=display.getWidth();//设置Dialog的宽度为屏幕宽度
//        getWindow().setAttributes(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*1);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_sure = view.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);

    }


    public void setOnclick(OnClickView onclick){
    this.onClickView=onclick;
    }
    public interface OnClickView{
        void setOnClickView();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    onClickView.setOnClickView();
                    break;
            }
    }
}
