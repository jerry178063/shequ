package com.unique.blockchain.nft.domain.home;

import com.google.gson.Gson;
import com.space.exchange.biz.net.bean.BaseResponse;

import java.util.List;

public class ListMatch extends BaseResponse {


    /**
     * id : 1387408878282473541
     * token : USDG
     * buyToken : GAUSS
     */
    private List<DataBean> data;

    public static ListMatch objectFromData(String str) {

        return new Gson().fromJson(str, ListMatch.class);
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String token;

        public boolean isIs_select() {
            return is_select;
        }

        public void setIs_select(boolean is_select) {
            this.is_select = is_select;
        }

        private String buyToken;
        private boolean is_select;
        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getBuyToken() {
            return buyToken;
        }

        public void setBuyToken(String buyToken) {
            this.buyToken = buyToken;
        }
    }
}
