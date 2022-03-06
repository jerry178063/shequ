package com.unique.blockchain.nft.view.activity.database;

import java.util.List;

public class BannerDatabase {

    /**
     * code : 200
     * msg : null
     * data : [{"id":19,"imageAddress":"http://192.168.2.13:31062/statics/2021/09/07/9cfd36d3-ca0a-45fa-acbd-b18b6dcacce3.jpg","imageLink":"www.baidu.com","placedTop":0,"status":1,"releaseTime":"2021-09-07 10:48:37","topTime":"2021-09-07 10:50:06","type":1},{"id":20,"imageAddress":"http://192.168.2.13:31062/statics/2021/09/07/752dd20a-6ddf-4d6b-bf18-26ec01cbe0a6.jpg","imageLink":"www.jd.com","placedTop":1,"status":1,"releaseTime":"2021-09-07 10:49:03","topTime":"2021-09-07 10:50:04","type":1},{"id":21,"imageAddress":"http://192.168.2.13:31062/statics/2021/09/07/ce9aa1c6-b5c1-472d-966c-2c36683ec040.png","imageLink":"www.taobao.com","placedTop":1,"status":1,"releaseTime":"2021-09-07 10:49:26","topTime":"2021-09-07 10:50:01","type":1}]
     */

    private int code;
    private Object msg;
    /**
     * id : 19
     * imageAddress : http://192.168.2.13:31062/statics/2021/09/07/9cfd36d3-ca0a-45fa-acbd-b18b6dcacce3.jpg
     * imageLink : www.baidu.com
     * placedTop : 0
     * status : 1
     * releaseTime : 2021-09-07 10:48:37
     * topTime : 2021-09-07 10:50:06
     * type : 1
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String imageAddress;
        private String imageLink;
        private int placedTop;
        private int status;
        private String releaseTime;
        private String topTime;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }

        public int getPlacedTop() {
            return placedTop;
        }

        public void setPlacedTop(int placedTop) {
            this.placedTop = placedTop;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getTopTime() {
            return topTime;
        }

        public void setTopTime(String topTime) {
            this.topTime = topTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
