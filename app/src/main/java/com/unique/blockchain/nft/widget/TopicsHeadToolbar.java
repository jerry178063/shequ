package com.unique.blockchain.nft.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.unique.blockchain.nft.R;


public class TopicsHeadToolbar extends Toolbar {
    /*
     *
     * 主题头部导航
     *
     * */
    /**
     * 左侧Title
     */
    public TextView mTxtLeftTitle;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧Title
     */
    public TextView mTxtRightTitle;
    public LinearLayout lin_title;
    private Toolbar toolbar;

    public TopicsHeadToolbar(Context context) {
        this(context, null);
    }

    public TopicsHeadToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopicsHeadToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.topics_head_layout, this);
        mTxtLeftTitle = (TextView) findViewById(R.id.txt_left_title);
        mTxtMiddleTitle = (TextView) findViewById(R.id.txt_main_title);
        mTxtRightTitle = (TextView) findViewById(R.id.txt_right_title);
        toolbar = findViewById(R.id.ll);
        lin_title = findViewById(R.id.lin_title);
    }


    //设置中间title的内容
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }


    public void setMainTitleTypeface(Typeface text) {
        mTxtMiddleTitle.setTypeface(text);
    }


    //设置中间title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    //设置title左边文字
    public void setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
    }

    //设置title左边文字颜色
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }

    //设置title左边图标
    public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
    }

    //设置title左边点击事件
    public void setLeftTitleClickListener(OnClickListener onClickListener) {
        mTxtLeftTitle.setOnClickListener(onClickListener);
    }

    //设置title右边文字
    public void setRightTitleText(String text) {
        mTxtRightTitle.setVisibility(View.VISIBLE);
        mTxtRightTitle.setText(text);
        CharSequence text1 = mTxtRightTitle.getText();
    }

    public String getRightTitleText() {
        return mTxtRightTitle.getText().toString();
    }


    //设置title右边文字颜色
    public void setRightTitleColor(int color) {
        mTxtRightTitle.setTextColor(color);
    }

    //设置title右边图标
    public void setRightTitleDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTxtRightTitle.setCompoundDrawables(null, null, dwRight, null);
    }

    //设置title右边点击事件
    public void setRightTitleClickListener(OnClickListener onClickListener) {
        mTxtRightTitle.setOnClickListener(onClickListener);
    }

    public void setBgColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    public String getLiftText() {
        return mTxtLeftTitle.getText().toString();

    }

    public String getRightText() {
        return mTxtRightTitle.getText().toString();
    }

//    public void setvisibilityRightTitleDrawable() {
//        Drawable dwRight = getResources().getDrawable(R.drawable.icon_delected);
//        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
//        mTxtRightTitle.setCompoundDrawables(null, null, null, null);
//    }
//
//    public void setvisibilityLiftTitleDrawable() {
//        Drawable dwRight = getResources().getDrawable(R.drawable.btn_back);
//        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
//        mTxtLeftTitle.setCompoundDrawables(null, null, null, null);
//    }

}
