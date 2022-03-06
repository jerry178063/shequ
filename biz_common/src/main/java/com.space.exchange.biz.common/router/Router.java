package com.space.exchange.biz.common.router;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Author: Evan
 * Date: 2019/5/31
 */
public class Router {

    /**
        // 打开界面
        Router.open(Paths.ACTIVITY_SPLASH);

        Router.open(Paths.ACTIVITY_SPLASH， "key"， "value");

        Router.open(Paths.ACTIVITY_SPLASH， "key"， new Object());
     */

    /**
     * 开启界面，无参数
     * @param path 待开启界面的标识
     */
    public static void open(String path) {

        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 开启界面，带一个字符串参数
     * @param path 待开启界面的标识
     */
    public static void open(String path, String key, String value) {

        ARouter.getInstance().build(path).withString(key, value).navigation();
    }

    /**
     * 开启界面，带一个int数值参数
     * @param path 待开启界面的标识
     */
    public static void open(String path, String key, int value) {

        ARouter.getInstance().build(path).withInt(key, value).navigation();
    }

    /**
     * 开启界面，带一个序列化的对象实例
     * @param path 待开启界面的标识
     */
    public static void open(String path, String key, Serializable so) {

        ARouter.getInstance().build(path).withSerializable(key, so).navigation();
    }

    /**
     * 开启界面，带一个未序列化的对象实例
     * @param path 待开启界面的标识
     */
    public static void open(String path, String key, Object o) {

        ARouter.getInstance().build(path).withObject(key, o).navigation();
    }

    /**
     * 开启界面，带Map形式的多个参数
     * @param path 待开启界面的标识
     */
    public static void open(String path, String key, HashMap<String, Object> map) {


    }
}























