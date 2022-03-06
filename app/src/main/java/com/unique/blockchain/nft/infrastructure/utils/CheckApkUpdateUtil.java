package com.unique.blockchain.nft.infrastructure.utils;

import android.content.Context;
import android.text.TextUtils;

import com.space.exchange.biz.common.GlobalField;
import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.event.EventWhat;
import com.space.exchange.biz.common.util.SPUtils;
import com.space.exchange.biz.net.bean.AppVersionBean;
import com.space.exchange.biz.net.utils.ToastUtils;
import com.unique.blockchain.nft.BuildConfig;
import com.unique.blockchain.nft.infrastructure.dialog.UpdateApkDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;


public class CheckApkUpdateUtil {

    public void checkApkUpdate(Context context, boolean isshow, UpdateApkListener updateApkListener) {
        getUpdateData(context, isshow, updateApkListener);
    }

    private void getUpdateData(final Context context, boolean isshow, final UpdateApkListener updateApkListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("version", String.valueOf(BuildConfig.VERSION_CODE));
//        ZlyRequest.getAppVersion((RxAppCompatActivity) context, map)
//                .subscribe(new ApiListener<AppVersionResponse>(context) {
//                    @Override
//                    public void onSuccess(AppVersionResponse response) {
//                        if (response != null && response.data != null) {
//                            dealApkUpdate(context, isshow, response.data);
//                        } else {
//                            if (isshow) {
////                                ToastUtils.showToast(context, "当前已是最新版本");
//                            } else {
//                                sendNotice();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFail(Throwable e) {
//                        Log.i("-------", e.getMessage());
//                    }
//                });
    }

    private void sendNotice() {
        BaseEvent baseEvent = BaseEvent.obtainMessage();
        baseEvent.what = EventWhat.OFFICIAL_NOTICE_SHOW;
        EventBus.getDefault().post(baseEvent);
    }

    private void dealApkUpdate(final Context context, boolean isshow, final AppVersionBean bean) {
        int versionCode = BuildConfig.VERSION_CODE;
        if (bean != null && !TextUtils.isEmpty(bean.getVersion()) && versionCode < Integer.parseInt(bean.getVersion())) {
            SPUtils.putBoolean(context, GlobalField.IS_FORCE_UPDATE, "1".equals(String.valueOf(bean.getForce_update())));
            UpdateApkDialog dialog = new UpdateApkDialog(context, () -> {
            });
            dialog.setOnDismissListener(dialog1 -> {
                if ("0".equals(String.valueOf(bean.getForce_update())) && !isshow) {
                    sendNotice();
                }
            });
            dialog.show();
            dialog.setBean(bean);
        } else {
            if (isshow) {
                ToastUtils.showToast(context, "当前已是最新版本");
            } else {
                sendNotice();
            }
        }

    }

    public interface UpdateApkListener {
        void onUpdateId(long downId);
    }

}
