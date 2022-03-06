package com.unique.blockchain.nft.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.unique.blockchain.nft.R;


public class BottomBarTab extends LinearLayout {
    private ImageView mIcon;
    private Context mContext;
    private TextView mTextView;
    private int mTabPosition = -1;
    private int icon;
    private int unicon;
    private static boolean ifshow = false;

    public BottomBarTab(Context context, @DrawableRes int icon, @DrawableRes int unicon , String title) {
        this(context, null, icon, unicon, title);
    }


    public BottomBarTab(Context context, AttributeSet attrs, int icon, int unicon,String title) {
        this(context, attrs, 0, icon,unicon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, int unicon,String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, unicon,title);
    }

    private void init(Context context, int icon,int unicon, String title) {
        mContext = context;
        this.icon =icon;
        this.unicon =unicon;
       /* TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();*/

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        mIcon = new ImageView(context);
        int dimension = (int) getResources().getDimension(R.dimen.dp_22);
        LayoutParams params = new LayoutParams(dimension, dimension);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mIcon.setImageResource(unicon);
        mIcon.setLayoutParams(params);
        LayoutParams textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;
        textViewParams.topMargin = (int) getResources().getDimension(R.dimen.dp_2);
        mTextView = new TextView(context);
        mTextView.setText(title);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.sp_11));
        mTextView.setLayoutParams(textViewParams);
        mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        addView(mIcon);
        addView(mTextView);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setImageResource(icon);
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.color_303235));

        } else {
            mIcon.setImageResource(unicon);
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.color_A9AAAB));
        }
    }


    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
