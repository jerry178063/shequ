package com.unique.blockchain.nft.domain.home;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class HomeAssetsInfoBean extends BaseBean {

    private int code;
    private String msg;
    private List<Datas> data;

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

    public List<Datas> getData() {
        return data;
    }

    public void setData(List<Datas> data) {
        this.data = data;
    }

    public class Datas{
        private String token;
        private String tokenAmount;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenAmount() {
            return tokenAmount;
        }

        public void setTokenAmount(String tokenAmount) {
            this.tokenAmount = tokenAmount;
        }
    }

}
