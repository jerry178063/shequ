package com.unique.blockchain.nft.domain.mark;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class MarkHotBean extends BaseBean {

    private int code;
    private String msg;
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

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public class Rows{
        private int id;
        private String productImage;//产品图片
        private String nftName;//nft名称
        private String walletAddr;//钱包地址
        private String currentPrice;//当前价格
        private int productType;//产品类型
        private Long surplus;//倒计时时间
        private int sellMode;//出价或者购买
        private String nftId;
        private String nftState;//列表类型
        private String productName;
        private String productIntro;//简介
        private String miniDidPrice;//最低竞拍
        private String productCover;
        private String companyId;
        private int takeState;
        private Integer isTransfer;
        private String nextKey;
        private String iconUrl;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getNextKey() {
            return nextKey;
        }

        public void setNextKey(String nextKey) {
            this.nextKey = nextKey;
        }

        public Integer getIsTransfer() {
            return isTransfer;
        }

        public void setIsTransfer(Integer isTransfer) {
            this.isTransfer = isTransfer;
        }

        public int getTakeState() {
            return takeState;
        }

        public void setTakeState(int takeState) {
            this.takeState = takeState;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getProductCover() {
            return productCover;
        }

        public void setProductCover(String productCover) {
            this.productCover = productCover;
        }

        public String getMiniDidPrice() {
            return miniDidPrice;
        }

        public void setMiniDidPrice(String miniDidPrice) {
            this.miniDidPrice = miniDidPrice;
        }

        public String getProductIntro() {
            return productIntro;
        }

        public void setProductIntro(String productIntro) {
            this.productIntro = productIntro;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getNftState() {
            return nftState;
        }

        public void setNftState(String nftState) {
            this.nftState = nftState;
        }

        public String getNftId() {
            return nftId;
        }

        public void setNftId(String nftId) {
            this.nftId = nftId;
        }

        public int getSellMode() {
            return sellMode;
        }

        public void setSellMode(int sellMode) {
            this.sellMode = sellMode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public Long getSurplus() {
            return surplus;
        }

        public void setSurplus(Long surplus) {
            this.surplus = surplus;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getNftName() {
            return nftName;
        }

        public void setNftName(String nftName) {
            this.nftName = nftName;
        }

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }
    }

}
