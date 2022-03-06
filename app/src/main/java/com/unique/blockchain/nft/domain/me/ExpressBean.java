package com.unique.blockchain.nft.domain.me;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class ExpressBean extends BaseBean {

    private int code;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String logo;
        private String number;
        private String name;
        private String receivingPhone;
        private String receivingName;
        private String receivingAddr;
        private List<ExpressInfoList> expressInfoList;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReceivingPhone() {
            return receivingPhone;
        }

        public void setReceivingPhone(String receivingPhone) {
            this.receivingPhone = receivingPhone;
        }

        public String getReceivingName() {
            return receivingName;
        }

        public void setReceivingName(String receivingName) {
            this.receivingName = receivingName;
        }

        public String getReceivingAddr() {
            return receivingAddr;
        }

        public void setReceivingAddr(String receivingAddr) {
            this.receivingAddr = receivingAddr;
        }

        public List<ExpressInfoList> getExpressInfoList() {
            return expressInfoList;
        }

        public void setExpressInfoList(List<ExpressInfoList> expressInfoList) {
            this.expressInfoList = expressInfoList;
        }

        public class ExpressInfoList{
            private int expressId;
            private int id;
            private String time;
            private String status;


            public int getExpressId() {
                return expressId;
            }

            public void setExpressId(int expressId) {
                this.expressId = expressId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

}
