package com.space.exchange.biz.net.model.zly_bean;

import com.space.exchange.biz.net.bean.BaseResponse;

public class AppVersionBean  extends BaseResponse {


    /**
     * success : true
     * data : {"id":18,"product_name":"中链云","platform":1,"version":"2.4.1","status":1,"force_update":1,"update_desc":"修改系统的一些bug和优化界面","download_path":"http://zoonchaincloud.oss-cn-shenzhen.aliyuncs.com/zlyc.apk","version_num":1,"creator":"system","create_datetime":"2020-10-30 20:48:28"}
     * logs :
     */

    private boolean success;
    /**
     * id : 18
     * product_name : 中链云
     * platform : 1
     * version : 2.4.1
     * status : 1
     * force_update : 1
     * update_desc : 修改系统的一些bug和优化界面
     * download_path : http://zoonchaincloud.oss-cn-shenzhen.aliyuncs.com/zlyc.apk
     * version_num : 1
     * creator : system
     * create_datetime : 2020-10-30 20:48:28
     */

    private DataBean data;
    private String logs;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public static class DataBean {
        private int id;
        private String product_name;
        private int platform;
        private String version;
        private int status;
        private int force_update;
        private String update_desc;
        private String download_path;
        private int version_num;
        private String creator;
        private String create_datetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getForce_update() {
            return force_update;
        }

        public void setForce_update(int force_update) {
            this.force_update = force_update;
        }

        public String getUpdate_desc() {
            return update_desc;
        }

        public void setUpdate_desc(String update_desc) {
            this.update_desc = update_desc;
        }

        public String getDownload_path() {
            return download_path;
        }

        public void setDownload_path(String download_path) {
            this.download_path = download_path;
        }

        public int getVersion_num() {
            return version_num;
        }

        public void setVersion_num(int version_num) {
            this.version_num = version_num;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreate_datetime() {
            return create_datetime;
        }

        public void setCreate_datetime(String create_datetime) {
            this.create_datetime = create_datetime;
        }
    }
}
