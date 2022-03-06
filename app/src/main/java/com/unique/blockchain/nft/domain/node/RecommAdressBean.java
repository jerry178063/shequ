package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

public class RecommAdressBean extends BaseBean {

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
        private String gaussAddr;

        public String getGaussAddr() {
            return gaussAddr;
        }

        public void setGaussAddr(String gaussAddr) {
            this.gaussAddr = gaussAddr;
        }
    }


}
