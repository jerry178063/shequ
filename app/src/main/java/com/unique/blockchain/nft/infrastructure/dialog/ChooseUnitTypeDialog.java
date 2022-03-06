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
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.R;

import org.jetbrains.annotations.NotNull;

/**
 * 选择单位类型
 *
 * */
public class ChooseUnitTypeDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnChooseTypeClickView onClickView;
    public ChooseUnitTypeDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public ChooseUnitTypeDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    EditText et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.choose_unit_dialog_layout, null);

        setContentView(view);
        RadioButton radioButton1 = view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = view.findViewById(R.id.radioButton2);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//企业
                if(onClickView != null){
                    onClickView.setChooseTypeClickView("0");
                    dismiss();
                }
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//个体工商户
                if(onClickView != null){
                    onClickView.setChooseTypeClickView("1");
                    dismiss();
                }
            }
        });

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*0.8);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }


    public void setOnclick(OnChooseTypeClickView onclick){
    this.onClickView=onclick;
    }
    public interface OnChooseTypeClickView{
        void setChooseTypeClickView(String type);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
            }
    }
}
