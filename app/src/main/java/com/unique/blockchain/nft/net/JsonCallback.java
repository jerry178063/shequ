package com.unique.blockchain.nft.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;


public abstract class JsonCallback<T> extends AbsCallback<T> {

    private ProgressDialog mProgressDialog;

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);

        if (request.getTag() instanceof Activity) {
            Activity activity = (Activity) request.getTag();
            mProgressDialog = ProgressDialog.show(activity, "", "执行中,请稍侯...");
        }


        Log.v("http", "request--->>" + request.getUrl() + "\n" + request.getParams());

    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertSuccess(Response response) throws Exception {

        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        JsonConvert<T> convert = new JsonConvert<>();
        convert.setType(params[0]);
        T t = convert.convertSuccess(response);
        response.close();
        return t;
    }

    public void onFailure(String code, String message) {
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        if (response != null) {
            if (response.code() == 400) {
                try {
                    if (e != null) {
                        String errorJson = e.getMessage();
                        //AppUtils.showToast("ERROR: " + error.getCode() + " " + response.message() + " " + error.getMessage());

                        reportError(call, errorJson);
                    }

                } catch (Exception e1) {
                    onFailure("500", "系统异常");
                    e1.printStackTrace();
                    reportError(call, ReportError.getExceptionMessage(e));
                }
            } else {
                //AppUtils.showToast("ERROR: STATUS=" + response.code() + " " + e.getMessage());
                onFailure("500", "系统异常");
                reportError(call, "response_code=" + response.code() + ReportError.getExceptionMessage(e));
            }
        } else {
            //AppUtils.showToast("ERROR: RESPONSE=NULL " + e.getMessage());
            onFailure("500", "系统异常");
            reportError(call, "HTTP_ERROR: RESPONSE=NULL " + ReportError.getExceptionMessage(e));
        }
    }


    private void reportError(Call call, String message) {
        if (call != null && call.request() != null) {

            HttpUrl httpUrl = call.request().url();
            if (httpUrl != null) {

//                if (!httpUrl.toString().contains(UrlConstant.POST_APP_ERROR)) {
//                    ReportError.reportError("HTTP_ERROR url=" + httpUrl.toString() + " " + message);
//                }
            } else {
                ReportError.reportError("httpUrl=null " + message);
            }

        }
    }
}