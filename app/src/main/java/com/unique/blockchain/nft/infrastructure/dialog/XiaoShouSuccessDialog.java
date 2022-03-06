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
 * 销售成功
 *
 * */
public class XiaoShouSuccessDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnBackZhujiClickView onClickView;
    public TextView tv_cancel;
    public XiaoShouSuccessDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public XiaoShouSuccessDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    EditText et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.xiaoshou_dialog_layout, null);
        tv_cancel=view.findViewById(R.id.tv_cancel);
        et_pass = view.findViewById(R.id.et_pass);
        tv_cancel.setOnClickListener(this);

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
//                case R.id.tv_sure:
//                    onClickView.setBackZhujiClickView(et_pass.getText().toString());
//                    break;
            }
    }
}
