package com.unique.blockchain.nft.infrastructure.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unique.blockchain.nft.R;

public class ToastUtils {
    private static ToastUtils mToastUtils=null;
    private static Context mContext = null;
    private ToastUtils(Context context){
        mContext = context.getApplicationContext();
    }
    public synchronized static ToastUtils getInstance(Context context){
        if(mToastUtils==null) mToastUtils=new ToastUtils(context);
        return mToastUtils;
    }
    /**
     * 展示toast==LENGTH_SHORT
     *
     * @param msg
     */
    public void show(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 展示toast==LENGTH_LONG
     *
     * @param msg
     */
    public void showLong(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }


    private void show(String massage, int show_length) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_layout_two, null);
        //获取TextView
        TextView title = (TextView) view.findViewById(R.id.toast_tv);
        //设置显示的内容
        title.setText(massage);
        Toast toast = new Toast(mContext);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        toast.setDuration(show_length);
        toast.setView(view);
        toast.show();
    }
}
