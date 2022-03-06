package com.unique.blockchain.nft.view.activity.database;

import java.util.List;

public class ReDatabase {
    /**
     * total : 1
     * rows : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":70,"companyId":null,"quantity":2,"name":"   一个店铺","image":"http://192.168.2.13:31062/statics/2021/09/15/a6f7acfa-e8de-4c30-bbe5-4c16e334fb6c.jpg","mobile":null,"walletAddr":"unique15hng4pll3upv5gj842d22lw83x38yuj6n9z9sx","state":null,"modifyTime":null,"modifyId":null,"cautionMoney":null,"createId":null}]
     * code : 200
     * msg : 查询成功
     */

    private int total;
    private int code;
    private String msg;
    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * params : {}
     * id : 70
     * companyId : null
     * quantity : 2
     * name :    一个店铺
     * image : http://192.168.2.13:31062/statics/2021/09/15/a6f7acfa-e8de-4c30-bbe5-4c16e334fb6c.jpg
     * mobile : null
     * walletAddr : unique15hng4pll3upv5gj842d22lw83x38yuj6n9z9sx
     * state : null
     * modifyTime : null
     * modifyId : null
     * cautionMoney : null
     * createId : null
     */

    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private Object searchValue;
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private int id;
        private Object companyId;
        private int quantity;
        private String name;
        private String image;
        private Object mobile;
        private String walletAddr;
        private Object state;
        private Object modifyTime;
        private Object modifyId;
        private Object cautionMoney;
        private Object createId;
        private String companyIntro;
        private String metadataHash;

        public String getMetadataHash() {
            return metadataHash;
        }

        public void setMetadataHash(String metadataHash) {
            this.metadataHash = metadataHash;
        }

        public String getCompanyIntro() {
            return companyIntro;
        }

        public void setCompanyIntro(String companyIntro) {
            this.companyIntro = companyIntro;
        }

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public Object getModifyId() {
            return modifyId;
        }

        public void setModifyId(Object modifyId) {
            this.modifyId = modifyId;
        }

        public Object getCautionMoney() {
            return cautionMoney;
        }

        public void setCautionMoney(Object cautionMoney) {
            this.cautionMoney = cautionMoney;
        }

        public Object getCreateId() {
            return createId;
        }

        public void setCreateId(Object createId) {
            this.createId = createId;
        }
    }
}
