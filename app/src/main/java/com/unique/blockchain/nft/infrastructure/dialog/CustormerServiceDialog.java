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
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.R;


public class CustormerServiceDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private ImageView iv_qrcode;
    private String type;
     private LoadOnclickView loadOnclickView;
     private String title;
     private String content;
     private int img;
    public CustormerServiceDialog(@NonNull Context context, String type) {
        this(context, R.style.customor_service);
        this.mContext = context;
        this.type=type;
    }

    public CustormerServiceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setImgAndTxt(int src,String title,String text){
        this.img=src;
        this.title=title;
        this.content=text;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_notice, null);
        TextView tv_kown=view.findViewById(R.id.tv_kown);
        ImageView iv_flight=view.findViewById(R.id.iv_flight);
        TextView tv_content=view.findViewById(R.id.tv_content);
        TextView tv_title=view.findViewById(R.id.tv_title);
        if(img!=0){
            iv_flight.setImageResource(img);
            tv_content.setText(content);
            tv_title.setText(title);
        }
        tv_kown.setOnClickListener(this::onClick);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*0.8);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }
    public void setLoadOnclickView(LoadOnclickView loadOnclickView){
        this.loadOnclickView=loadOnclickView;
    }
    public interface  LoadOnclickView{
       void setOnClickView(String type);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_kown:
                    if(loadOnclickView!=null){
                        loadOnclickView.setOnClickView(type);
                    }
                break;
        }

    }
}
