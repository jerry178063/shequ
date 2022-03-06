package com.unique.blockchain.nft.domain.discover;

import com.unique.blockchain.nft.domain.BaseBean;

public class NewDetailBean extends BaseBean {

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
        private String title;
        private String releaseTime;
        private String keyWords;
        private String digest;
        private String content;
        private String thumbnailaddress;

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

        public String getKeyWords() {
            return keyWords;
        }

        public void setKeyWords(String keyWords) {
            this.keyWords = keyWords;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getThumbnailaddress() {
            return thumbnailaddress;
        }

        public void setThumbnailaddress(String thumbnailaddress) {
            this.thumbnailaddress = thumbnailaddress;
        }
    }

}
