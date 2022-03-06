package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

import java.util.List;

public class QuestionListResponse extends BaseResponse {

    /**
     * id : 4
     * question : 1111
     * answer : 123
     * class_id : 1
     * sort : 1111
     * is_deleted : 0
     * created_at : 2021-01-30 15:51:49
     * updated_at : 2021-01-30 15:51:49
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String question;
        private String answer;
        private String class_id;
        private String sort;
        private String is_deleted;
        private String created_at;
        private String updated_at;
        private boolean showAnswer;

        public boolean isShowAnswer() {
            return showAnswer;
        }

        public void setShowAnswer(boolean showAnswer) {
            this.showAnswer = showAnswer;
        }



        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
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
    }
}
