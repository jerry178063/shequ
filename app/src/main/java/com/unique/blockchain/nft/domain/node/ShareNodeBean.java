package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

public class ShareNodeBean extends BaseBean {

    private int code;
    private String msg;
    private  Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

    public class Data{
        private String invitationcode;
        private String downloadLink;

        public String getInvitationcode() {
            return invitationcode;
        }

        public void setInvitationcode(String invitationcode) {
            this.invitationcode = invitationcode;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }
    }

}
