package com.unique.blockchain.nft.view.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.space.exchange.biz.common.base.BaseActivity;
import com.space.exchange.biz.net.utils.ToastUtil;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.widget.TopicsHeadToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class MinerFeeActivity extends BaseActivity {

    @BindView(R.id.top_s_title_toolbar)
    TopicsHeadToolbar top_s_title_toolbar;
    @BindView(R.id.tv_admin_wallet)
    TextView tv_admin_wallet;
    @BindView(R.id.tv_xiaoyu_30)
    TextView tv_xiaoyu_30;
    @BindView(R.id.tv_xiaoyu_5)
    TextView tv_xiaoyu_5;
    @BindView(R.id.tv_xiaoyu_2)
    TextView tv_xiaoyu_2;
    @BindView(R.id.tv_xiaoyu_1)
    TextView tv_xiaoyu_1;
    @BindView(R.id.radio_1)
    RadioButton radio_1;
    @BindView(R.id.radio_2)
    RadioButton radio_2;
    @BindView(R.id.radio_3)
    RadioButton radio_3;
    @BindView(R.id.radio_4)
    RadioButton radio_4;
    @BindView(R.id.et_gauss)
    EditText et_gauss;
    @BindView(R.id.tv_fu_title)
    TextView tv_fu_title;
    @BindView(R.id.et_customize)
    EditText et_customize;
    @BindView(R.id.lin_customize)
    LinearLayout lin_customize;
    boolean isSelect_customize;
    private String comeAdress;
    @BindView(R.id.tv_moren)
    TextView tv_moren;
    private int isFirstCome = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_miner_fee;
    }

    @Override
    public void initUI() {
        top_s_title_toolbar.setLeftTitleText("矿工费");
        comeAdress = getIntent().getStringExtra("comeAdress");

//        if (!TextUtils.isEmpty(comeAdress) && comeAdress.equals("zhuanzhang")) {
            tv_fu_title.setText("Gas price(" + "0.00000003" + "GPB)*Gas(200000)");
            tv_moren.setText("0.00000003GPB");
//        }

        top_s_title_toolbar.mTxtLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radio_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("FF8763","B:" + b);
                if (b) {
                    three = "0.006";
                    et_gauss.setText(three);
                    tv_fu_title.setText("Gas price(" + "0.00000003" + "GPB)*Gas(200000)");
                    if(isFirstCome == 1) {
                        et_customize.setText("");
                        et_customize.setHint("最小数量0.0000000003");
                    }
                    isFirstCome = 1;
                    radio_1.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_choose));
                    radio_2.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_3.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_4.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    isSelect_customize = false;

                } else {
                    tv_fu_title.setText("Gas price(" + "0" + "GPB)*Gas(200000)");
                    radio_1.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    isSelect_customize = true;
                }
            }
        });
        radio_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    three = "";
                    et_gauss.setText(three);
                    lin_customize.setVisibility(View.VISIBLE);
                    radio_1.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_2.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_choose));
                    radio_3.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_4.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    isSelect_customize = true;
                } else {
                    radio_2.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    lin_customize.setVisibility(View.GONE);
                    isSelect_customize = false;
                }
            }
        });
        radio_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    three = "0.006";
                    et_gauss.setText(three);
                    radio_1.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_2.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_3.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_choose));
                    radio_4.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                } else {
                    radio_3.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                }
            }
        });
        radio_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    three = "0.0045";
                    et_gauss.setText(three);
                    radio_1.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_2.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_3.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                    radio_4.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_choose));
                } else {
                    radio_4.setButtonDrawable(getResources().getDrawable(R.mipmap.gas_icon_unchoose));
                }
            }
        });
        et_customize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(comeAdress) && comeAdress.equals("zhuanzhang")) {
                    if (!TextUtils.isEmpty(charSequence.toString())) {
                        String fir = charSequence.toString().charAt(0) + "";
                        if (fir.equals(".")) {
                            //用char包装类中的判断字母的方法判断每一个字符
                            et_customize.setHint("最小数量0.0000000003");
                            et_customize.setText("");
                            if(charSequence.toString().length() > 1){
                                et_gauss.setText("0.");
                            }
                            return;
                        }

                        tv_fu_title.setText("Gas price(" + charSequence.toString() + "GPB)*Gas(200000)");
                        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00000000");
                        if(!charSequence.toString().equals(".")) {
                            et_gauss.setText(df.format(Double.valueOf(charSequence.toString()) * 100000) + "");
                        }

                    }
//                    else {
//                        tv_fu_title.setText("Gas price(" + 0 + "GPB)*Gas(200000)");
//                        et_gauss.setHint("0");
//                        et_gauss.setText("");
//                        et_customize.setHint("最小数量0.0000000003");
//                    }
//                    tv_moren.setText("0.006GPB");
                } else {
                    if (!TextUtils.isEmpty(charSequence.toString())) {
                        tv_fu_title.setText("Gas price(" + charSequence.toString() + "GPB)*Gas(200000)");
                        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00000000");
                        if(!charSequence.toString().equals(".")) {
                            et_gauss.setText(df.format(Double.valueOf(charSequence.toString()) * 200000) + "");
                        }
                    } else {
                        tv_fu_title.setText("Gas price(" + "0.00000003" + "GPB)*Gas(200000)");
                        et_gauss.setHint("0");
                        et_customize.setHint("最小数量0.000000003");
                    }
//                    tv_moren.setText("0.006GPB");
                }
                if(charSequence.toString().equals(".")) {
                    et_customize.setText("");
                    et_customize.setHint("最小委托数量(GPB)");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    judgeNumber(editable, et_customize);
                } catch (Exception e) {

                }
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
//                edt.delete(index - 1, index);//删除光标前的字符
//                return;
//            }
//        }
//        if (posDot > 5) {//小数点前大于5位数就删除光标前一位
//            edt.delete(index - 1, index);//删除光标前的字符
//            return;
//        }
        if (temp.contains(".")) {
            if (temp.length() - posDot - 1 > 9)//如果包含小数点
            {
                edt.delete(index - 1, index);//删除光标前的字符
                return;
            }
        }
    }

    @Override
    public void initData() {
        radio_1.setChecked(true);
        tv_xiaoyu_1.setText("一般");
        tv_xiaoyu_2.setText("自定义");
//        tv_xiaoyu_5.setText("一般<5分钟");
//        tv_xiaoyu_30.setText("缓慢<30分钟");
    }

    @Override
    public void bindView(Bundle bundle) {

    }

    String three;

    @OnClick({R.id.tv_admin_wallet})
    public void setOnclick(View view) {

        switch (view.getId()) {
            case R.id.tv_admin_wallet://确定

                if (isSelect_customize) {
                    if (!TextUtils.isEmpty(et_customize.getText().toString())) {
                        if (Double.valueOf(et_customize.getText().toString()) < 0.000000003) {
                            ToastUtil.showShort(MinerFeeActivity.this, "Gas Price不能低于0.000000003GPB");
                            return;
                        }
                    } else {
                        ToastUtil.showShort(MinerFeeActivity.this, "Gas Price不能低于0.000000003GPB");
                        return;
                    }
                }

                Intent intent = new Intent();
                if (radio_1.isSelected()) {
                    //Gas price(0.3uGAUSS)*Gas(300000)
                    tv_fu_title.setText("Gas price(" + three + "Gas)*Gas(200000)");
                } else if (radio_2.isSelected()) {
                    tv_fu_title.setText("Gas price(" + three + "Gas)*Gas(200000)");
                }
//                else if(radio_3.isSelected()){
//                    three = "0.006";
//                }else if(radio_4.isSelected()){
//                    three = "0.0045";
//                }
                if (!TextUtils.isEmpty(et_gauss.getText().toString())) {
                    intent.putExtra("aaa", et_gauss.getText().toString());
                }
                //通过intent对象返回结果 setResult
                setResult(2, intent);
                finish();  //结束当前activity的声明周期

                break;
        }
    }
}
