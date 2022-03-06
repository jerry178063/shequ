package com.unique.blockchain.nft.domain;

//import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserInfoConverent implements PropertyConverter<List<UserInfoItem>,String> {
    Gson mGson;
    public UserInfoConverent() {

        mGson = new Gson();

    }
    @Override
    public List<UserInfoItem> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<UserInfoItem>>() {
        }.getType();
        ArrayList<UserInfoItem> itemList= mGson.fromJson(databaseValue, type);
        return itemList;

    }

    @Override
    public String convertToDatabaseValue(List<UserInfoItem> userInfoItems) {
        String dbString = mGson.toJson(userInfoItems);
        return dbString;
    }
}
