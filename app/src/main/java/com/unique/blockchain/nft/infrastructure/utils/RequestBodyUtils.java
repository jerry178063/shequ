package com.unique.blockchain.nft.infrastructure.utils;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyUtils {

    public static RequestBody getRequestjson(Map map){
        MediaType parse = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(parse, json);
        return requestBody;
    }
}
