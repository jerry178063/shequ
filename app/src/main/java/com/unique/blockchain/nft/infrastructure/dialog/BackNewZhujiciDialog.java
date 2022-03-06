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
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.R;

import org.jetbrains.annotations.NotNull;

/**
 * 返回生成新的助记词
 *
 * */
public class BackNewZhujiciDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnBackZhujiClickView onClickView;
    public BackNewZhujiciDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public BackNewZhujiciDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    EditText et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.back_zhujici_dialog_layout, null);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        et_pass = view.findViewById(R.id.et_pass);
        et_pass.setSingleLine(false);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*0.8);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }


    public void setOnclick(OnBackZhujiClickView onclick){
    this.onClickView=onclick;
    }
    public interface OnBackZhujiClickView{
        void setBackZhujiClickView(String et_pass);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    onClickView.setBackZhujiClickView(et_pass.getText().toString());
                    break;
            }
    }
}
