package com.space.exchange.biz.net.connection;

import android.util.Base64;
import android.util.Log;

import com.space.exchange.biz.common.ApiUrl;
import com.space.exchange.biz.net.Const;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求签名消息头拦截器
 * Author: Evan
 * Date: 2019/5/29
 */
public class SignHeaderInterceptor implements Interceptor {

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {

        String url = chain.request().url().toString();
        long currentTime = System.currentTimeMillis() / 1000 + Const.TIME_OUT_REQUEST;
        String[] split = url.split(ApiUrl.url);
        String resultUrl = "";
        if (split != null && split.length > 1) {
            resultUrl = split[1];
        }
        String sign = new StringBuilder().append(resultUrl).append("\n").append(currentTime).toString();
        String baseSign = getBase64(sign);
        String sha1Sign = stringToSign(baseSign);
        sha1Sign = sha1Sign.replace("+", "-");
        sha1Sign = sha1Sign.replace("/", "_");

        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader(Const.HEADER_SIGN, Const.HEADER_SIGN + " " + Const.ACCESS_KEY + ":" + sha1Sign + ":" + baseSign);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    private String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = Base64.encodeToString(b, Base64.NO_WRAP).toString();
            s = s.replace("+", "-");
            s = s.replace("/", "_");
        }
        return s;
    }

    private String stringToSign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(
                    Const.SECRET_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            return Base64.encodeToString(mac.doFinal(data.getBytes()), Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            Log.e("stringToSign", "Hash algorithm SHA-1 is not supported", e);
        } catch (UnsupportedEncodingException e) {
            Log.e("stringToSign", "Encoding UTF-8 is not supported", e);
        } catch (InvalidKeyException e) {
            Log.e("stringToSign", "Invalid key", e);
        }
        return "";
    }

}






