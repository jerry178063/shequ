package com.unique.blockchain.nft.infrastructure.utils;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.space.exchange.biz.common.util.SPUtils;
import com.unique.blockchain.nft.MyApplication;
import com.unique.blockchain.nft.domain.User;
import com.unique.blockchain.nft.domain.UserInfoItem;
import com.unique.blockchain.nft.dp.DaoSession;
import com.unique.blockchain.nft.dp.UserDao;
import com.unique.blockchain.nft.infrastructure.other.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import wallet.core.jni.PrivateKey;

public class UserDaoUtils {
    static {
        System.loadLibrary("TrustWalletCore");
    }

    private static DaoSession daoSession = null;

    public UserDaoUtils() {

    }

    public static synchronized DaoSession getInstance() {
        if (daoSession == null) {
            synchronized (UserDaoUtils.class) {

                if (daoSession == null) {

                    daoSession = MyApplication.getDaoSession();

                }

            }
        }

        return daoSession;
    }

    public static UserDao getUserDao() {

        if (daoSession == null) {
            daoSession = getInstance();
        }
        UserDao userDao = daoSession.getUserDao();
        return userDao;
    }

    public static String getUserPsd(Context context) {
        if (daoSession == null) {
            daoSession = getInstance();
        }
        UserDao userDao = daoSession.getUserDao();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(com.space.exchange.biz.common.util.SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            String psd = unique.getPsd();
            return psd;
        }
        return null;
    }


    public static String getDid(Context context) {
        if (daoSession == null) {
            daoSession = getInstance();
        }

        UserDao userDao = daoSession.getUserDao();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(com.space.exchange.biz.common.util.SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            String did = unique.getDid();
            return did;
        }
        return null;
    }

    public static String getUserAdress(Context context, String coin) {
        if (daoSession == null) {
            daoSession = getInstance();
        }
        UserDao userDao = daoSession.getUserDao();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(com.space.exchange.biz.common.util.SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (coin.equals(unique.getMObjectList().get(i).getCoin_name())) {
                    return unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        return null;
    }


    public static User getUser(Context context) {
        if (daoSession == null) {
            daoSession = getInstance();
        }
        UserDao userDao = daoSession.getUserDao();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(com.space.exchange.biz.common.util.SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            return unique;
        }
        return null;
    }

    public static UserInfoItem getUserinfo(Context context, String type) {
        if (daoSession == null) {
            daoSession = getInstance();
        }

        UserDao userDao = daoSession.getUserDao();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(com.space.exchange.biz.common.util.SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals(type.toUpperCase())) {
                    return unique.getMObjectList().get(i);
                }
            }
        }

        return null;
    }

    public static String getCoinImg(Context context, String type) {


        return null;
    }

    public static String getAdress(Context context, String type) {
        if (daoSession == null) {
            daoSession = getInstance();
        }

        UserDao userDao = daoSession.getUserDao();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(com.space.exchange.biz.common.util.SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (type.toUpperCase().equals(unique.getMObjectList().get(i).getCoin_name())) {
                    return unique.getMObjectList().get(i).getCoin_psd();
                }
            }
        }
        return null;
    }

    public static List<User> getAllUser() {
        if (daoSession == null) {
            daoSession = getInstance();
        }
        UserDao userDao = daoSession.getUserDao();

        return userDao.loadAll();
    }


    //获取全部地址 除TRON和ETH
    public static Map<String, String> getAllAdress(Context context) {
        if (daoSession == null) {
            daoSession = getInstance();
        }
        UserDao userDao = daoSession.getUserDao();
        Map<String, String> map = new HashMap();
        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(context, Tools.name, ""))).build().unique();
        if (unique != null) {
            for (int i = 0; i < unique.getMObjectList().size(); i++) {
                if (unique.getMObjectList().get(i).getCoin_name().equals("TRON") || unique.getMObjectList().get(i).getCoin_name().equals("ETH")) {

                } else {
                    map.put(unique.getMObjectList().get(i).getCoin_name(), unique.getMObjectList().get(i).getCoin_psd());
                }
            }
        }
        return map;
    }

    public static PrivateKey getGaussPrevateKey(Context context, String type) {
        PrivateKey privateKey = null;
        byte[] content = null;
        String userPsd = getUserPsd(context);
        byte[] bytes1 = TransactionUtils.hexString2Bytes(userPsd);
        List<UserInfoItem> mObjectList = getUser(context).getMObjectList();
        for (int i = 0; i < mObjectList.size(); i++) {
            if (type.toUpperCase().equals(mObjectList.get(i).getCoin_name())) {
                content = mObjectList.get(i).getPsd_psd();
            }
        }

        if (content != null) {
            byte[] bytes = EncryptUtils.decryptAES(content, bytes1, "AES/ECB/PKCS5Padding", null);
            Log.e("FFF444766","私钥:" + bytes);
            privateKey = new PrivateKey(bytes);
        }
        return privateKey;

    }

//    public static String getUserPsd(Context context){
//        DaoSession daoSession = MyApplication.getDaoSession();
//        UserDao userDao = daoSession.getUserDao();
//        User unique = userDao.queryBuilder().where(UserDao.Properties.Name.eq(SPUtils.getString(context, Tools.name, ""))).build().unique();
//        if(unique!=null){
//            String psd = unique.getPsd();
//            return psd;
//        }
//        return null;
//    }


    public static String getGLTime() {

        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        return str;
    }
}
