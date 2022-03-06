package com.unique.blockchain.nft.net;


/**
 * Created by w.gs on 2016/10/24.
 */

public class ReportError {

    public static void reportError(String errorMessage) {

        if (errorMessage != null && errorMessage.length() > 1000) {
            errorMessage = errorMessage.substring(0, 1000);
        }


        //app错误上传  这里注释掉
//        OkGo.post(UrlConstant.POST_APP_ERROR).tag("reportError")
//
//                .upJson(appError.toString())
//
//                .execute(new JsonCallback<Empty>() {
//                    @Override
//                    public void onSuccess(Empty empty, Call call, Response response) {
//
//                    }
//                });

    }

    public static void reportError(Exception e) {

        ReportError.reportError(getExceptionMessage(e));
    }

    public static String getExceptionMessage(Exception e) {

        if (e == null) {
            return "";
        }

        StackTraceElement[] elements = e.getStackTrace();
        if (elements != null) {
            String msg = e.getMessage() + "\n";

            for (StackTraceElement element : elements) {
                msg = msg + "\n" + element.toString();
            }
            return msg;
        }

        return "";
    }

}
