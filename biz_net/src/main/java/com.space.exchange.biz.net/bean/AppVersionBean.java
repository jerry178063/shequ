package com.space.exchange.biz.net.bean;

import java.io.Serializable;

/**
 * @author DTL
 * @description:
 * @date :2020-01-19 16:49
 */
public class AppVersionBean implements Serializable {

    /**
     * update : false
     * app_name : null
     * intro : null
     * version_name : null
     * version : null
     * wgt_url : null
     * pkg_url : null
     */

    private boolean update;
    private String app_name;
    private String intro;
    private String version_name;
    private String version;
    private String wgt_url;
    private String pkg_url;
    private String force_update="0";

    public String getForce_update() {
        return force_update;
    }

    public void setForce_update(String force_update) {
        this.force_update = force_update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWgt_url() {
        return wgt_url;
    }

    public void setWgt_url(String wgt_url) {
        this.wgt_url = wgt_url;
    }

    public String getPkg_url() {
        return pkg_url;
    }

    public void setPkg_url(String pkg_url) {
        this.pkg_url = pkg_url;
    }
}
