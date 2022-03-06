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
 * 首页流通排名类型
 *
 * */
public class HomeNftTypeDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnSafeClickView onClickView;
    private ItemClickView itemClickView;
    private TextView tv_all,tv_piaowu,tv_yishupin,tv_qingshepin,tv_collection;
    private int tag;
    public HomeNftTypeDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public HomeNftTypeDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    EditText et_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_type_dialog_layout, null);
        TextView tv_cancel=view.findViewById(R.id.tv_cancel);
        TextView tv_sure=view.findViewById(R.id.tv_sure);
        et_pass = view.findViewById(R.id.et_pass);
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_all = view.findViewById(R.id.tv_all);
        tv_piaowu = view.findViewById(R.id.tv_piaowu);
        tv_yishupin = view.findViewById(R.id.tv_yishupin);
        tv_qingshepin = view.findViewById(R.id.tv_qingshepin);
        tv_collection = view.findViewById(R.id.tv_collection);
        tv_all.setOnClickListener(this);
        tv_piaowu.setOnClickListener(this);
        tv_yishupin.setOnClickListener(this);
        tv_qingshepin.setOnClickListener(this);
        tv_collection.setOnClickListener(this);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager m = getWindow().getWindowManager();
        Display d =m.getDefaultDisplay();
//        attributes.height = (int) (d.getHeight()*0.4);
        attributes.width = (int) (d.getWidth()*1);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
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
                    et_pass.setText("");
                    dismiss();
                    break;
                case R.id.tv_sure:
                    if(onClickView != null) {
                        onClickView.setOnSafeClickView(et_pass.getText().toString());
                    }
                    if(itemClickView != null){
                        itemClickView.setItemClickView(tag);
                    }
                    et_pass.setText("");
                    break;
                case R.id.tv_all://所有
                    tag = 0;

                    tv_all.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));
                    tv_piaowu.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_yishupin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_qingshepin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_collection.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    tv_all.setTextColor(mContext.getResources().getColor(R.color.color_863A1C));
                    tv_piaowu.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_yishupin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_qingshepin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_collection.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    break;
                case R.id.tv_piaowu://票务
                    tag = 1;
                    tv_all.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_piaowu.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));
                    tv_yishupin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_qingshepin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_collection.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    tv_all.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_piaowu.setTextColor(mContext.getResources().getColor(R.color.color_863A1C));
                    tv_yishupin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_qingshepin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_collection.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    break;
                case R.id.tv_yishupin://艺术品
                    tag = 2;
                    tv_all.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_piaowu.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_yishupin.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));
                    tv_qingshepin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_collection.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    tv_all.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_piaowu.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_yishupin.setTextColor(mContext.getResources().getColor(R.color.color_863A1C));
                    tv_qingshepin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_collection.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    break;
                case R.id.tv_qingshepin://轻奢品
                    tag = 3;
                    tv_all.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_piaowu.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_yishupin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_qingshepin.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));
                    tv_collection.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    tv_all.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_piaowu.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_yishupin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_qingshepin.setTextColor(mContext.getResources().getColor(R.color.color_863A1C));
                    tv_collection.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    break;
                case R.id.tv_collection://收藏品
                    tag = 4;

                    tv_all.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_piaowu.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_yishupin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_qingshepin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    tv_collection.setBackgroundColor(mContext.getResources().getColor(R.color.color_FFF8F2));

                    tv_all.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_piaowu.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_yishupin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_qingshepin.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
                    tv_collection.setTextColor(mContext.getResources().getColor(R.color.color_863A1C));
                    break;

            }
    }
}
