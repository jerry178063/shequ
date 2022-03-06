package com.space.exchange.biz.net.request;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.space.exchange.biz.common.GlobalField;
import com.space.exchange.biz.common.event.BaseEvent;
import com.space.exchange.biz.common.event.EventWhat;
import com.space.exchange.biz.common.sp.AccountPrefHelper;
import com.space.exchange.biz.common.sp.SPUtils;
import com.space.exchange.biz.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.space.exchange.biz.net.Const.CODE_2FA;
import static com.space.exchange.biz.net.Const.CODE_FABI;
import static com.space.exchange.biz.net.Const.CODE_OK;
import static com.space.exchange.biz.net.Const.CODE_TOKEN_LOGIN;

/**
 * 网络请求统一处理
 * <p>
 * 1-无网络情况
 * 2-回复状态码统一处理
 * <p>
 * Author: Evan
 * Date: 2019/5/30
 */
public abstract class ApiListener<T extends BaseResponse> implements Observer {

    private Context context;
    // 是否需要重新登录，默认需要
    private boolean isLoginAgain = true;
    public TokenErrorListener listener;

    public ApiListener(Context context) {
        this.context = context;
    }

    public ApiListener(Context context, TokenErrorListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public ApiListener(Context context, boolean isLoginAgain) {
        this.context = context;
        this.isLoginAgain = isLoginAgain;
    }

    public ApiListener(Context context, boolean isLoginAgain, TokenErrorListener listener) {
        this.context = context;
        this.isLoginAgain = isLoginAgain;
        this.listener = listener;
    }

    /*
    失败：onSubscribe - onError
    成功：onSubscribe - onNext - onComplete
     */
    @Override
    public void onSubscribe(Disposable d) {
        /**
         * 判断网络，无网络则提示用户
         */
//        if (!NetworkUtil.isConnected(context)) {
//            showToast(context, "网络连接失败");
//        }
    }

    @Override
    public void onNext(Object o) {
        if (!(o instanceof BaseResponse)) {
            throw new RuntimeException(">>> param type error <<<");
        }
        /**
         * 状态码处理
         *
         * 401              Token失效，跳转到登录页，不回调
         * 1                Toast显示错误信息，回调onFail
         * 0,402,403...     不处理，回调onSuccess，返回实体数据
         */
        BaseResponse response = (BaseResponse) o;
        if (response.code == CODE_OK || response.code == CODE_2FA || response.code == CODE_FABI) {
            onSuccess(cast(response));
        } else if (response.code == CODE_TOKEN_LOGIN) {
            AccountPrefHelper.saveToken(context, "");
            SPUtils.putString(context, GlobalField.NAME, "");
            SPUtils.putString(context, GlobalField.PHONE, "");
            SPUtils.putString(context, GlobalField.EMAIL, "");
            SPUtils.putString(context, GlobalField.USER_ID, "");
            // Application中执行
            BaseEvent baseEvent = BaseEvent.obtainMessage();
            baseEvent.what = EventWhat.NEED_LOGIN;
            baseEvent.obj = isLoginAgain;
            EventBus.getDefault().post(baseEvent);
            if (listener != null) {
                listener.tokenError();
            }
        } else {
            onFail(new Throwable(response.msg));
            if (!TextUtils.isEmpty(response.msg)) {
//                ToastUtil.showShort(context, response.msg);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        String message = e.toString();
        if (!message.contains("HttpTransaction")) {
            showToast(context, "网络开小差,请稍后再试");
            onFail(e);
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T response);

    public abstract void onFail(Throwable e);

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public interface TokenErrorListener {
        void tokenError();
    }

    private static Toast mTextToast;

    public static void showToast(Context context, String msg) {
        if (null == mTextToast) {
            mTextToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        mTextToast.setText(msg);
        mTextToast.show();
    }
}




















