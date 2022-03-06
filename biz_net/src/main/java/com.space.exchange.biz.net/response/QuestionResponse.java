package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

import java.util.List;

public class QuestionResponse extends BaseResponse {

    /**
     * id : 3
     * name : 1111
     * sort : 1111
     * is_deleted : 0
     * created_at : 2021-01-30 15:46:36
     * updated_at : 2021-01-30 15:46:36
     * class_id : 3
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String name;
        private int sort;
        private int is_deleted;
        private String created_at;
        private String updated_at;
        private int class_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(int is_deleted) {
            this.is_deleted = is_deleted;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }
    }
}
