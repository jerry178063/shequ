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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.R;

import org.jetbrains.annotations.NotNull;
/**
 * 增发资产
 *
 * */
public class AddtionalAssetsDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnClickView onClickView;
    private KuangGongOneOnClickView kuangGongOnClickView;
    private OnCancelClickView onCancelClickView;
    public TextView tv_can_gauss;
    public TextView tv_title_dialog;
    public TextView tv_title2,tv_kgf2;
    public ImageView img_right;
    public RelativeLayout rela_right;
    public AddtionalAssetsDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public AddtionalAssetsDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.addtionl_dialog_layout, null);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_can_gauss = view.findViewById(R.id.tv_can_gauss);
        tv_title_dialog = view.findViewById(R.id.tv_title_dialog);
        tv_title2 = view.findViewById(R.id.tv_title2);
        tv_kgf2 = view.findViewById(R.id.tv_kgf2);
        img_right = view.findViewById(R.id.img_right);
        img_right.setOnClickListener(this);
        rela_right = view.findViewById(R.id.rela_right);
        rela_right.setOnClickListener(this);

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


    public void setOnclick(OnClickView onclick){
    this.onClickView=onclick;
    }
    public void setKuangGongOneOnClickView(KuangGongOneOnClickView onclick){
        this.kuangGongOnClickView=onclick;
    }
    public void setOnCancelClickView(OnCancelClickView onclick){
        this.onCancelClickView=onclick;
    }
    public interface OnClickView{
        void setOnClickView();
    }
    public interface KuangGongOneOnClickView{
        void setKuangGongOneOnClickView();
    }
    public interface OnCancelClickView{
        void setCancelOnClickView();
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    onCancelClickView.setCancelOnClickView();
                    dismiss();
                    break;
                case R.id.tv_sure:
                    onClickView.setOnClickView();
                    break;
                case R.id.rela_right:
                    kuangGongOnClickView.setKuangGongOneOnClickView();
                    break;
            }
    }
}
