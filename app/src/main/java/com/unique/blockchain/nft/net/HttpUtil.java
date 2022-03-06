package com.unique.blockchain.nft.net;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络请求 get post
 *
 * */
public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    public static void sendOKHttpRequest(String address, Map<String,String> head, Map<String,String> body, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request.Builder builder=new Request.Builder().url(address);
        if(head!=null&&head.size()>0){
            for (Map.Entry<String, String> entry : head.entrySet()) {
                builder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        FormBody.Builder formBody = new FormBody.Builder();
        if(body!=null&&body.size()>0){
            for (Map.Entry<String, String> entry : head.entrySet()) {
                formBody.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody requestBody = formBody.build();
        Request request=builder.post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
    public static void post(String url, String json, okhttp3.Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
    }
    public static void sendOKHttpGetRequest(String address, Map<String,String> head, Map<String,String> body, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request.Builder builder=new Request.Builder().url(attachHttpGetParams(address,head));
        if(head!=null&&head.size()>0){
            for (Map.Entry<String, String> entry : head.entrySet()) {
                builder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        FormBody.Builder formBody = new FormBody.Builder();
        if(body!=null&&body.size()>0){
            for (Map.Entry<String, String> entry : head.entrySet()) {
                formBody.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody requestBody = formBody.build();
        Request request=builder.get().build();
        client.newCall(request).enqueue(callback);
    }
/**
        * 为HttpGet 的 url 方便的添加多个name value 参数。
            * @param url
     * @param params
     * @return
             */
    public static String attachHttpGetParams(String url, Map<String,String> params){

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i=0;i<params.size();i++ ) {
            String value=null;
            try {
                value= URLEncoder.encode(values.next(),"utf-8");
            }catch (Exception e){
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()+"="+value);
            if (i!=params.size()-1) {
                stringBuffer.append("&");
            }
        }

        return url + stringBuffer.toString();
    }
}
