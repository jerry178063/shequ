package com.unique.blockchain.nft.view.activity.database;

public class QarticularsDatabase {


    /**
     * code : 200
     * msg : null
     * data : {"id":65,"productName":null,"walletAddr":"unique15hng4pll3upv5gj842d22lw83x38yuj6n9z9sx","productId":null,"productIntro":"132121","ownerWallet":"unique15hng4pll3upv5gj842d22lw83x38yuj6n9z9sx","productImage":"http://192.168.2.13:31062/statics/2021/09/15/c6a59471-1b08-4c06-a542-a855fb06b4c7.png","productType":2,"sellMode":1,"royalty":0.01,"productVat":0.01,"proveUrl":"http://192.168.2.13:31062/statics/2021/09/15/de0a975c-95b1-4fb0-9c1c-db0060994d07.png","remark":null,"foundTime":"2021-09-15 08:59:39","examineState":null,"foundState":2,"sellState":1,"takeState":0,"freezeState":0,"currentPrice":null,"miniDidPrice":null,"severalRows":0,"seatNumber":0,"nftId":"第三方第三方士大夫20210915155515X9PUG988L7VMW42Y","nftName":"铸造测试","tenantId":70,"seeCount":17,"isCollection":0,"ownerNickName":null,"ownerAvatar":null,"balance":null,"minerPrice":null,"currency":null,"endTime":null,"metatataCode":"QmRfg7Hp723zLoPGJsJcrucnro3aDc7TG7aL9ZKyNpp9Ro","metadataUrl":"http://192.168.1.131:8080/ipfs/ipfs/download/review/hash?hash=QmRfg7Hp723zLoPGJsJcrucnro3aDc7TG7aL9ZKyNpp9Ro","metatataHash":null}
     */

    private int code;
    private Object msg;
    /**
     * id : 65
     * productName : null
     * walletAddr : unique15hng4pll3upv5gj842d22lw83x38yuj6n9z9sx
     * productId : null
     * productIntro : 132121
     * ownerWallet : unique15hng4pll3upv5gj842d22lw83x38yuj6n9z9sx
     * productImage : http://192.168.2.13:31062/statics/2021/09/15/c6a59471-1b08-4c06-a542-a855fb06b4c7.png
     * productType : 2
     * sellMode : 1
     * royalty : 0.01
     * productVat : 0.01
     * proveUrl : http://192.168.2.13:31062/statics/2021/09/15/de0a975c-95b1-4fb0-9c1c-db0060994d07.png
     * remark : null
     * foundTime : 2021-09-15 08:59:39
     * examineState : null
     * foundState : 2
     * sellState : 1
     * takeState : 0
     * freezeState : 0
     * currentPrice : null
     * miniDidPrice : null
     * severalRows : 0
     * seatNumber : 0
     * nftId : 第三方第三方士大夫20210915155515X9PUG988L7VMW42Y
     * nftName : 铸造测试
     * tenantId : 70
     * seeCount : 17
     * isCollection : 0
     * ownerNickName : null
     * ownerAvatar : null
     * balance : null
     * minerPrice : null
     * currency : null
     * endTime : null
     * metatataCode : QmRfg7Hp723zLoPGJsJcrucnro3aDc7TG7aL9ZKyNpp9Ro
     * metadataUrl : http://192.168.1.131:8080/ipfs/ipfs/download/review/hash?hash=QmRfg7Hp723zLoPGJsJcrucnro3aDc7TG7aL9ZKyNpp9Ro
     * metatataHash : null
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private Object productName;
        private String walletAddr;
        private Object productId;
        private String productIntro;
        private String ownerWallet;
        private String productImage;
        private int productType;
        private int sellMode;
        private double royalty;
        private double productVat;
        private String proveUrl;
        private Object remark;
        private String foundTime;
        private Object examineState;
        private int foundState;
        private int sellState;
        private int takeState;
        private int freezeState;
        private Object currentPrice;
        private Object miniDidPrice;
        private int severalRows;
        private int seatNumber;
        private String nftId;
        private String nftName;
        private int tenantId;
        private int seeCount;
        private int isCollection;
        private Object ownerNickName;
        private Object ownerAvatar;
        private Object balance;
        private Object minerPrice;
        private Object currency;
        private Object endTime;
        private String metatataCode;
        private String metadataUrl;
        private Object metatataHash;

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

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public String getProductIntro() {
            return productIntro;
        }

        public void setProductIntro(String productIntro) {
            this.productIntro = productIntro;
        }

        public String getOwnerWallet() {
            return ownerWallet;
        }

        public void setOwnerWallet(String ownerWallet) {
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

        public double getRoyalty() {
            return royalty;
        }

        public void setRoyalty(double royalty) {
            this.royalty = royalty;
        }

        public double getProductVat() {
            return productVat;
        }

        public void setProductVat(double productVat) {
            this.productVat = productVat;
        }

        public String getProveUrl() {
            return proveUrl;
        }

        public void setProveUrl(String proveUrl) {
            this.proveUrl = proveUrl;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getFoundTime() {
            return foundTime;
        }

        public void setFoundTime(String foundTime) {
            this.foundTime = foundTime;
        }

        public Object getExamineState() {
            return examineState;
        }

        public void setExamineState(Object examineState) {
            this.examineState = examineState;
        }

        public int getFoundState() {
            return foundState;
        }

        public void setFoundState(int foundState) {
            this.foundState = foundState;
        }

        public int getSellState() {
            return sellState;
        }

        public void setSellState(int sellState) {
            this.sellState = sellState;
        }

        public int getTakeState() {
            return takeState;
        }

        public void setTakeState(int takeState) {
            this.takeState = takeState;
        }

        public int getFreezeState() {
            return freezeState;
        }

        public void setFreezeState(int freezeState) {
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

        public int getSeeCount() {
            return seeCount;
        }

        public void setSeeCount(int seeCount) {
            this.seeCount = seeCount;
        }

        public int getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(int isCollection) {
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

        public String getMetatataCode() {
            return metatataCode;
        }

        public void setMetatataCode(String metatataCode) {
            this.metatataCode = metatataCode;
        }

        public String getMetadataUrl() {
            return metadataUrl;
        }

        public void setMetadataUrl(String metadataUrl) {
            this.metadataUrl = metadataUrl;
        }

        public Object getMetatataHash() {
            return metatataHash;
        }

        public void setMetatataHash(Object metatataHash) {
            this.metatataHash = metatataHash;
        }
    }
}
