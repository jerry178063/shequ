package com.space.exchange.biz.net.bean;

import java.util.List;

public class NewsFlashResponse extends BaseResponse {
    /**
     * 新的快讯数量
     */
    private int news;
    /**
     * 总的快讯数量
     */
    private int count;
    /**
     * 结果中最新的快讯id
     */
    private int top_id;
    /**
     * 结果中最后的快讯id
     */
    private int bottom_id;

    private List<NewsFlashItem> list;

    public int getNews() {
        return news;
    }

    public void setNews(int news) {
        this.news = news;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTop_id() {
        return top_id;
    }

    public void setTop_id(int top_id) {
        this.top_id = top_id;
    }

    public int getBottom_id() {
        return bottom_id;
    }

    public void setBottom_id(int bottom_id) {
        this.bottom_id = bottom_id;
    }

    public List<NewsFlashItem> getList() {
        return list;
    }

    public void setList(List<NewsFlashItem> list) {
        this.list = list;
    }

    public static class NewsFlashItem {
        /**
         * 快讯按天汇总的日期
         */
        private String date;
        private List<NewsFlashBaen> lives;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<NewsFlashBaen> getLives() {
            return lives;
        }

        public void setLives(List<NewsFlashBaen> lives) {
            this.lives = lives;
        }
    }

    public static class NewsFlashBaen {
        /**
         * 快讯id
         */
        private int id;
        /**
         * 快讯内容
         */
        private String content;
        /**
         * 链接名称
         */
        private String link_name;
        /**
         * 链接地址
         */
        private String link;
        /**
         * 星级 （5、4、3、2、1、0）
         */
        private int grade;
        /**
         * 是否标红； red 标红
         */
        private String highlight_color;
        /**
         * 图片地址，分别对应原图地址、缩略图地址和图片大小
         */
        private List<ImageBean> images;
        /**
         * 快讯时间
         */
        private String created_at;
        /**
         * 看涨数量
         */
        private int up_counts;
        /**
         * 看跌数量
         */
        private int down_counts;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLink_name() {
            return link_name;
        }

        public void setLink_name(String link_name) {
            this.link_name = link_name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getHighlight_color() {
            return highlight_color;
        }

        public void setHighlight_color(String highlight_color) {
            this.highlight_color = highlight_color;
        }

        public List<ImageBean> getImages() {
            return images;
        }

        public void setImages(List<ImageBean> images) {
            this.images = images;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getUp_counts() {
            return up_counts;
        }

        public void setUp_counts(int up_counts) {
            this.up_counts = up_counts;
        }

        public int getDown_counts() {
            return down_counts;
        }

        public void setDown_counts(int down_counts) {
            this.down_counts = down_counts;
        }
    }

    public static class ImageBean {
        private String url;
        private String thumbnail;
        private int height;
        private int width;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
