package com.unique.blockchain.nft.domain.wallet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unique.blockchain.nft.domain.me.MifrateStandBean;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssectConverent implements PropertyConverter<List<MifrateStandBean>,String> {
    Gson mGson;
    public AssectConverent() {

        mGson = new Gson();

    }
    @Override
    public List<MifrateStandBean> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<MifrateStandBean>>() {
        }.getType();
        ArrayList<MifrateStandBean> itemList= mGson.fromJson(databaseValue, type);
        return itemList;

    }

    @Override
    public String convertToDatabaseValue(List<MifrateStandBean> userInfoItems) {
        String dbString = mGson.toJson(userInfoItems);
        return dbString;
    }
}

