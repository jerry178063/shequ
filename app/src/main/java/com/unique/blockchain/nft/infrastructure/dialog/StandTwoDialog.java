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

import com.unique.blockchain.nft.R;

import org.jetbrains.annotations.NotNull;

public class StandTwoDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnClickView onClickView;
    public StandTwoDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public StandTwoDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.stand_dialog_layout, null);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        setContentView(view);
        getWindow().setGravity(Gravity.CENTER);//设置显示在底部
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*0.8);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
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
