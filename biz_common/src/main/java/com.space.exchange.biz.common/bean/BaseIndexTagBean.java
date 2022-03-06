package com.space.exchange.biz.common.bean;

/**
 * 介绍：索引类的标志位的实体基类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/04.
 */

public class BaseIndexTagBean {
    private String baseIndexTag;//所属的分类（城市的汉语拼音首字母）

    private boolean isCommon;

    public boolean isCommon() {
        return isCommon;
    }

    public void setCommon(boolean common) {
        isCommon = common;
    }

    public String getBaseIndexTag() {
        return baseIndexTag;
    }

    public void setBaseIndexTag(String baseIndexTag) {
        this.baseIndexTag = baseIndexTag;
    }
}
