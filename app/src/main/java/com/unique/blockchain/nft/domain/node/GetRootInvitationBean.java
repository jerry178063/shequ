package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

/**
 * 获取验证人信息
 * */
public class GetRootInvitationBean extends BaseBean {

    private String msg;
    private int code;
    private Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private int id;
        private String rootInvitationCode;
        private String validatoraddr;
        private String gaussaddr;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRootInvitationCode() {
            return rootInvitationCode;
        }

        public void setRootInvitationCode(String rootInvitationCode) {
            this.rootInvitationCode = rootInvitationCode;
        }

        public String getValidatoraddr() {
            return validatoraddr;
        }

        public void setValidatoraddr(String validatoraddr) {
            this.validatoraddr = validatoraddr;
        }

        public String getGaussaddr() {
            return gaussaddr;
        }

        public void setGaussaddr(String gaussaddr) {
            this.gaussaddr = gaussaddr;
        }
    }

}
