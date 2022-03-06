package com.space.exchange.biz.common.bean;

import java.io.Serializable;

/**
 * @author DTL
 * @description:
 * @date :2020-02-27 14:43
 */
public class KYCBean implements Serializable {

    private int kyc1;//-1未认证 0认证中 1通过 2拒绝

    private int kyc2;//-1未认证 0认证中 1通过 2拒绝

    public int getKyc1() {
        return kyc1;
    }

    public void setKyc1(int kyc1) {
        this.kyc1 = kyc1;
    }

    public int getKyc2() {
        return kyc2;
    }

    public void setKyc2(int kyc2) {
        this.kyc2 = kyc2;
    }
}
