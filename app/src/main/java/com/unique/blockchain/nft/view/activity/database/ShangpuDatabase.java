package com.unique.blockchain.nft.view.activity.database;

import java.util.List;

public class ShangpuDatabase {

    /**
     * code : 200
     * msg : null
     * data : [{"id":21,"productName":null,"walletAddr":null,"productId":null,"productIntro":null,"ownerWallet":null,"productImage":"http://192.168.2.13:31062/statics/2021/09/06/fe93a1e3-e43a-4f6c-a516-48b22877462f.png","productType":2,"sellMode":1,"royalty":null,"productVat":null,"proveUrl":null,"remark":null,"foundTime":null,"examineState":null,"foundState":null,"sellState":null,"takeState":null,"freezeState":null,"currentPrice":null,"miniDidPrice":null,"severalRows":0,"seatNumber":0,"nftId":"社会信用代码202109062158502L2S7LPMNA3J6GLM","nftName":"11","tenantId":64,"viewingCount":null,"isCollection":null,"ownerNickName":null,"ownerAvatar":null,"balance":null,"minerPrice":null,"currency":null,"endTime":null}]
     */

    private int code;
    private Object msg;
    /**
     * id : 21
     * productName : null
     * walletAddr : null
     * productId : null
     * productIntro : null
     * ownerWallet : null
     * productImage : http://192.168.2.13:31062/statics/2021/09/06/fe93a1e3-e43a-4f6c-a516-48b22877462f.png
     * productType : 2
     * sellMode : 1
     * royalty : null
     * productVat : null
     * proveUrl : null
     * remark : null
     * foundTime : null
     * examineState : null
     * foundState : null
     * sellState : null
     * takeState : null
     * freezeState : null
     * currentPrice : null
     * miniDidPrice : null
     * severalRows : 0
     * seatNumber : 0
     * nftId : 社会信用代码202109062158502L2S7LPMNA3J6GLM
     * nftName : 11
     * tenantId : 64
     * viewingCount : null
     * isCollection : null
     * ownerNickName : null
     * ownerAvatar : null
     * balance : null
     * minerPrice : null
     * currency : null
     * endTime : null
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
        private Object productName;
        private Object walletAddr;
        private Object productId;
        private Object productIntro;
        private Object ownerWallet;
        private String productImage;
        private int productType;
        private int sellMode;
        private Object royalty;
        private Object productVat;
        private Object proveUrl;
        private Object remark;
        private Object foundTime;
        private Object examineState;
        private Object foundState;
        private Object sellState;
        private Object takeState;
        private Object freezeState;
        private Object currentPrice;
        private Object miniDidPrice;
        private int severalRows;
        private int seatNumber;
        private String nftId;
        private String nftName;
        private int tenantId;
        private Object viewingCount;
        private Object isCollection;
        private Object ownerNickName;
        private Object ownerAvatar;
        private Object balance;
        private Object minerPrice;
        private Object currency;
        private Object endTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getProductName() {
            return productName;
        }

        public void setProductName(Object productName) {
            this.productName = productName;
        }

        public Object getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(Object walletAddr) {
            this.walletAddr = walletAddr;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public Object getProductIntro() {
            return productIntro;
        }

        public void setProductIntro(Object productIntro) {
            this.productIntro = productIntro;
        }

        public Object getOwnerWallet() {
            return ownerWallet;
        }

        public void setOwnerWallet(Object ownerWallet) {
            this.ownerWallet = ownerWallet;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public int getSellMode() {
            return sellMode;
        }

        public void setSellMode(int sellMode) {
            this.sellMode = sellMode;
        }

        public Object getRoyalty() {
            return royalty;
        }

        public void setRoyalty(Object royalty) {
            this.royalty = royalty;
        }

        public Object getProductVat() {
            return productVat;
        }

        public void setProductVat(Object productVat) {
            this.productVat = productVat;
        }

        public Object getProveUrl() {
            return proveUrl;
        }

        public void setProveUrl(Object proveUrl) {
            this.proveUrl = proveUrl;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getFoundTime() {
            return foundTime;
        }

        public void setFoundTime(Object foundTime) {
            this.foundTime = foundTime;
        }

        public Object getExamineState() {
            return examineState;
        }

        public void setExamineState(Object examineState) {
            this.examineState = examineState;
        }

        public Object getFoundState() {
            return foundState;
        }

        public void setFoundState(Object foundState) {
            this.foundState = foundState;
        }

        public Object getSellState() {
            return sellState;
        }

        public void setSellState(Object sellState) {
            this.sellState = sellState;
        }

        public Object getTakeState() {
            return takeState;
        }

        public void setTakeState(Object takeState) {
            this.takeState = takeState;
        }

        public Object getFreezeState() {
            return freezeState;
        }

        public void setFreezeState(Object freezeState) {
            this.freezeState = freezeState;
        }

        public Object getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(Object currentPrice) {
            this.currentPrice = currentPrice;
        }

        public Object getMiniDidPrice() {
            return miniDidPrice;
        }

        public void setMiniDidPrice(Object miniDidPrice) {
            this.miniDidPrice = miniDidPrice;
        }

        public int getSeveralRows() {
            return severalRows;
        }

        public void setSeveralRows(int severalRows) {
            this.severalRows = severalRows;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getNftId() {
            return nftId;
        }

        public void setNftId(String nftId) {
            this.nftId = nftId;
        }

        public String getNftName() {
            return nftName;
        }

        public void setNftName(String nftName) {
            this.nftName = nftName;
        }

        public int getTenantId() {
            return tenantId;
        }

        public void setTenantId(int tenantId) {
            this.tenantId = tenantId;
        }

        public Object getViewingCount() {
            return viewingCount;
        }

        public void setViewingCount(Object viewingCount) {
            this.viewingCount = viewingCount;
        }

        public Object getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(Object isCollection) {
            this.isCollection = isCollection;
        }

        public Object getOwnerNickName() {
            return ownerNickName;
        }

        public void setOwnerNickName(Object ownerNickName) {
            this.ownerNickName = ownerNickName;
        }

        public Object getOwnerAvatar() {
            return ownerAvatar;
        }

        public void setOwnerAvatar(Object ownerAvatar) {
            this.ownerAvatar = ownerAvatar;
        }

        public Object getBalance() {
            return balance;
        }

        public void setBalance(Object balance) {
            this.balance = balance;
        }

        public Object getMinerPrice() {
            return minerPrice;
        }

        public void setMinerPrice(Object minerPrice) {
            this.minerPrice = minerPrice;
        }

        public Object getCurrency() {
            return currency;
        }

        public void setCurrency(Object currency) {
            this.currency = currency;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }
    }
}
