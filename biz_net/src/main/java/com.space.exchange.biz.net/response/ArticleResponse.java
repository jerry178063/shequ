package com.space.exchange.biz.net.response;

import com.space.exchange.biz.net.bean.BaseResponse;

import java.util.List;

public class ArticleResponse extends BaseResponse {

    private ArticleBean data;

    public ArticleBean getData() {
        return data;
    }

    public void setData(ArticleBean data) {
        this.data = data;
    }

    public static class ArticleBean {

        /**
         * data : [{"article_id":1,"category_id":"2","content":"teewqeqeqeq","created_at":"2021-01-25 16:58:46","id":2,"title":"tttt"}]
         * total : 1
         */

        private int total;
        /**
         * article_id : 1
         * category_id : 2
         * content : teewqeqeqeq
         * created_at : 2021-01-25 16:58:46
         * id : 2
         * title : tttt
         */

        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {

            /**
             * id : 24
             * category_parent_id : 1
             * category_id : 2
             * title : dasdadad
             * content : dasdadadadad
             * username : xxx
             * is_show : 0
             * is_hot : 0
             * status : 1
             * sort : 1
             * created_at : 2021-01-28 10:08:57
             * updated_at : 2021-01-28 10:08:57
             * article_id : 24
             */

            private int id;
            private String category_parent_id;
            private String category_id;
            private String title;
            private String content;
            private String username;
            private String is_show;
            private String is_hot;
            private String status;
            private String sort;
            private String created_at;
            private String updated_at;
            private String img;
            private int article_id;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCategory_parent_id() {
                return category_parent_id;
            }

            public void setCategory_parent_id(String category_parent_id) {
                this.category_parent_id = category_parent_id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
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

            public int getArticle_id() {
                return article_id;
            }

            public void setArticle_id(int article_id) {
                this.article_id = article_id;
            }
        }
    }

}
