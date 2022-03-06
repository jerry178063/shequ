package com.unique.blockchain.nft.view.activity.database;

import java.util.List;

public class JsonDatabase {
    private int code;
    private String msg;
    private List<UserBean> muser;

    public class UserBean{
        private String moduleId ;
        private String data;
        private String gid;

        public String getModuleId() {
            return moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<UserBean> getMuser() {
        return muser;
    }

    public void setMuser(List<UserBean> muser) {
        this.muser = muser;
    }
}
