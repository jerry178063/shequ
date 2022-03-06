package com.space.exchange.biz.common.cache;

/**
 * Author: Evan
 * Date: 2019/5/30
 */
public class UserStore {
    // 在当前生命周期内，是否验证过指纹或手势
    public static boolean cheakFingerprintOrPattern;

    // 在当前生命周期内，是否弹出过设置手势或指纹的痰喘
    public static boolean checkSafeSetupDialog;

}
