package com.space.exchange.biz.common.util;

import android.app.Dialog;
import android.content.Context;

import com.space.exchange.biz.common.R;


public class LoadiangUtil {

    /**
     * 显示正在加载动画
     * @param context
     */
    public static Dialog showLoadingDialog(Context context) {
        //创建Dialog并传递style文件
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setCancelable(false);
        // 设置它的ContentView
        dialog.setContentView(R.layout.dialog_loading_layout);
//        dialog.show();//显示dialog
        return dialog;

    }
}
