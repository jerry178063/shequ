package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

public class LookRecommendBean extends BaseBean {
    private int code;
    private String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String recommendInvitationCode;
        private String gaussAddr;

        public String getGaussAddr() {
            return gaussAddr;
        }

        public void setGaussAddr(String gaussAddr) {
            this.gaussAddr = gaussAddr;
        }

        public String getRecommendInvitationCode() {
            return recommendInvitationCode;
        }

        public void setRecommendInvitationCode(String recommendInvitationCode) {
            this.recommendInvitationCode = recommendInvitationCode;
        }
    }

}
