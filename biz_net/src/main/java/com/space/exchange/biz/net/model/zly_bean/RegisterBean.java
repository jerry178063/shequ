package com.space.exchange.biz.net.model.zly_bean;

import com.space.exchange.biz.net.bean.BaseResponse;

public class RegisterBean  extends BaseResponse {


    /**
     * created_at : 2021-01-27 13:41:15
     * id : 2
     * nickname : 130****0990
     * phone : 13071220990
     * user_id : 2
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String created_at;
        private int id;
        private String nickname;
        private String phone;
        private int user_id;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
