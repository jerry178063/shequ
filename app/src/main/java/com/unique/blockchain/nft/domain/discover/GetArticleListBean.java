package com.unique.blockchain.nft.domain.discover;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class GetArticleListBean extends BaseBean {

    private int code;
    private String msg;
    private int total;
    private List<Rows> rows;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public class Rows{
        private int id;
        private String title;
        private String releaseTime;
        private String content;
        private String digest;
        private String thumbnailaddress;

        public String getThumbnailaddress() {
            return thumbnailaddress;
        }

        public void setThumbnailaddress(String thumbnailaddress) {
            this.thumbnailaddress = thumbnailaddress;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }
    }
}
