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
 * 删除钱包
 *
 * */
public class DeleWalletDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnDeleClickView onClickView;
    public DeleWalletDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public DeleWalletDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    EditText et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dele_wallet_dialog_layout, null);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        et_pass = view.findViewById(R.id.et_pass);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*1);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部
    }


    public void setOnclick(OnDeleClickView onclick){
    this.onClickView=onclick;
    }
    public interface OnDeleClickView{
        void setOnDeleClickView();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    onClickView.setOnDeleClickView();
                    break;
            }
    }
}
