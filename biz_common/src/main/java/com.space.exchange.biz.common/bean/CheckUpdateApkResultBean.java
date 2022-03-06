package com.space.exchange.biz.common.bean;


import com.google.gson.annotations.SerializedName;

public class CheckUpdateApkResultBean {
    @SerializedName("name")
    private String app_name;

    @SerializedName("content")
    private String desc;

    @SerializedName("download_url")
    private String file_path;

    @SerializedName("is_force")
    private String isForceUpdate;//1 不强制更新 2强制更新

    @SerializedName("version")
    private String version_name;

    private int version_no;

    @SerializedName("add_time")
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(String isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_no() {
        return version_no;
    }

    public void setVersion_no(int version_no) {
        this.version_no = version_no;
    }
}
