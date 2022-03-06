package com.unique.blockchain.nft.infrastructure.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unique.blockchain.nft.R;

import org.jetbrains.annotations.NotNull;

/**
 * 赎回
 *
 * */
public class AddtionalAssetsShuhuiDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private OnClickView onClickView;
    private KuangGongOnClickView kuangGongOnClickView;
    private OnShuhuiCancelClickView onCancelClickView;
    public TextView tv_can_gauss;
    public TextView tv_title_dialog;
    public TextView tv_kgf2;
    public EditText tv_title2;
    public ImageView img_right;
    public RelativeLayout rela_right;
    public AddtionalAssetsShuhuiDialog(@NonNull @NotNull Context context) {
        this(context, R.style.customor_service);
        this.mContext = context;
    }

    public AddtionalAssetsShuhuiDialog(@NonNull @NotNull Context context, int themeResId) {
        super(context, themeResId);
    }
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.addtionl_dialog_shuhui_layout, null);
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

        tv_title2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                int len = charSequence.toString().length();
                Log.e("FF999","text:" + text + "LEN:"  + len);
//                if (len == 1 && text.equals("0")) {
//                    et_min_num.setHint("最小委托数量(GAUSS)");
//                    et_min_num.setText("");
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                judgeNumber(editable,tv_title2);
            }
        });
    }
    //限制输入多少位小数点
    private void judgeNumber(Editable edt, EditText editText) {
        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
        //  if (posDot == 0) {//必须先输入数字后才能输入小数点
        //  edt.delete(0, temp.length());//删除所有字符
        //  return;
        //  }
//        if (posDot < 0) {//不包含小数点
//            if (temp.length() <= 5) {
//                return;//小于五位数直接返回
//            } else {
//                edt.delete(index-1, index);//删除光标前的字符
//                return;
//            }
//        }
//        if (posDot > 5) {//小数点前大于5位数就删除光标前一位
//            edt.delete(index-1, index);//删除光标前的字符
//            return;
//        }
        if(temp.contains(".")) {
            if (temp.length() - posDot - 1 > 6)//如果包含小数点
            {
                edt.delete(index - 1, index);//删除光标前的字符
                return;
            }
        }
    }

    public void setOnclick(OnClickView onclick){
    this.onClickView=onclick;
    }
    public void setKuangGongOnClickView(KuangGongOnClickView onclick){
        this.kuangGongOnClickView=onclick;
    }
    public void setOnCancelClickView(OnShuhuiCancelClickView onclick){
        this.onCancelClickView=onclick;
    }
    public interface OnClickView{
        void setOnClickView();
    }
    public interface KuangGongOnClickView{
        void setKuangGongOnClickView();
    }
    public interface OnShuhuiCancelClickView{
        void setShuHuiCancelOnClickView();
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    onCancelClickView.setShuHuiCancelOnClickView();
                    dismiss();
                    break;
                case R.id.tv_sure:
                    onClickView.setOnClickView();
                    break;
                case R.id.rela_right:
                    kuangGongOnClickView.setKuangGongOnClickView();
                    break;
            }
    }
}
