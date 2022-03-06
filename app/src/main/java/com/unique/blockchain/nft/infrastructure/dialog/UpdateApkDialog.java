package com.unique.blockchain.nft.infrastructure.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.space.exchange.biz.common.GlobalField;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.bean.AppVersionBean;
import com.space.exchange.biz.net.utils.DisplayUtil;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.R;
import com.unique.blockchain.nft.infrastructure.utils.NotificationDownloadApkUtils;
import com.unique.blockchain.nft.infrastructure.utils.UpdateCheckerUtils;


public class UpdateApkDialog extends Dialog {

    private AppVersionBean bean;
    private Context context;
    private TextView tvversion;
    private TextView tvDesc;
    private TextView tvCancel;
    private TextView tvConfirm;
    private onConfirmClickListener mListener;
    private boolean aBoolean;//是否强制更新

    public UpdateApkDialog(@NonNull Context context, onConfirmClickListener listener) {
        this(context, R.style.CustomDialogStyle);
        this.context = context;
        this.mListener = listener;
        aBoolean = SPUtils.getBoolean(context, GlobalField.IS_FORCE_UPDATE, false);
    }

    public UpdateApkDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initui();
    }

    private void initui() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_update_apk, null, false);
        tvversion = view.findViewById(R.id.tv_version);
        tvDesc = view.findViewById(R.id.tv_desc);
        tvDesc.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvCancel = view.findViewById(R.id.tv_update_apk_cancel);
        tvConfirm = view.findViewById(R.id.tv_update_apk_confirm);
        tvCancel.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> {
            try {
                //开启静默下载
                if (bean != null && "0".equals(bean.getForce_update())) {
                    NotificationDownloadApkUtils apkUtils = new NotificationDownloadApkUtils(context);
                    if (!TextUtils.isEmpty(bean.getPkg_url())) {
                        apkUtils.setmDownUrl(bean.getPkg_url());
                        apkUtils.downLoadApk();
                    }
                    dismiss();
                    ToastUtils.showToast(context, "正在下载");
                } else if (bean != null && "1".equals(bean.getForce_update())) {
                    UpdateCheckerUtils updateCheckerUtils = new UpdateCheckerUtils(context);
                    if (!TextUtils.isEmpty(bean.getPkg_url())) {
                        updateCheckerUtils.setCheckUrl(bean.getPkg_url());
                        updateCheckerUtils.downLoadApk();//下载APK
                    }
                    dismiss();
                }
            } catch (Exception e) {
                ToastUtils.showToast(context, context.getResources().getString(R.string.can_not_connect_network));
                dismiss();
            }
        });
        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        attributes.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - DisplayUtil.dipToPix(context, 60);
//        if (aBoolean) {
//            attributes.height = (int) (((Activity) context).getWindowManager().getDefaultDisplay().getHeight() * 0.48);
//        } else {
//            attributes.height = (int) (((Activity) context).getWindowManager().getDefaultDisplay().getHeight() * 0.6);
//        }
    }

    public void setBean(AppVersionBean bean) {
        this.bean = bean;
        if (bean != null) {
            tvversion.setText("V ".concat(bean.getVersion_name()));
            tvDesc.setText(bean.getIntro());
            if ("1".equals(bean.getForce_update())) {
                setCancelable(false);
                tvCancel.setVisibility(View.GONE);
            } else {
                setCancelable(true);
                tvCancel.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface onConfirmClickListener {
        void onclick();
    }
}
