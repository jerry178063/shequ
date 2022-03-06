package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class GoTradeBean extends BaseBean {

    private int code;
    private int total;
    private List<Rows> rows;

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

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public class Rows {

        private String balance;//个人余额
        private String currency;//币种
        private String currentPrice;//当前价格 ，最高价格
        private String endTime;//销售结束时间
        private String examineState;//审核状态
        private String foundState;//铸造状态
        private String foundTime;//铸造时间
        private String freezeState;//NFT状态：0流通，1：锁定，2：永久冻结
        private String id;
        private int isCollection;//是否收藏 1收藏 0没有收藏
        private String minerPrice;//矿工费
        private String miniDidPrice;//最低竞标价格
        private String nftId;//NFTID 产品ID 规则：企业社会信息码(18)+创作时间yyyyMMddHHmmss(14)+产品流水ID(16) [备注：流水号随机生成）
        private String nftName;//NFT名称
        private String ownerAvatar;//拥有者头像地址
        private String ownerNickName;//拥有者昵称
        private String ownerWallet;//拥有者钱包地址
        private int productId;//产品id
        private String productImage;//产品缩略图
        private String productIntro;//产品简介
        private String productName;//产品名称
        private int productType;//产品类型 艺术：1，收藏品：2，音乐：3，域名:4，视频：5，摄影：6
        private String productVat;//增值税
        private String proveUrl;//产权证明路径
        private String remark;//Nft备注，简介
        private String royalty;//版税
        private int seatNumber;//座位号
        private int sellMode;//1：固定价格，2：定时拍卖
        private int sellState;//销售状态
        private int severalRows;//排
        private int takeState;//提货状态
        private int tenantId;//租户ID
        private String viewingCount;//浏览数
        private String walletAddr;//铸造者钱包地址
        private int companyId;
        private String productCover;
        private int getChain;
        private String nextKey;

        public String getNextKey() {
            return nextKey;
        }

        public void setNextKey(String nextKey) {
            this.nextKey = nextKey;
        }

        public int getGetChain() {
            return getChain;
        }

        public void setGetChain(int getChain) {
            this.getChain = getChain;
        }

        public String getProductCover() {
            return productCover;
        }

        public void setProductCover(String productCover) {
            this.productCover = productCover;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getExamineState() {
            return examineState;
        }

        public void setExamineState(String examineState) {
            this.examineState = examineState;
        }

        public String getFoundState() {
            return foundState;
        }

        public void setFoundState(String foundState) {
            this.foundState = foundState;
        }

        public String getFoundTime() {
            return foundTime;
        }

        public void setFoundTime(String foundTime) {
            this.foundTime = foundTime;
        }

        public String getFreezeState() {
            return freezeState;
        }

        public void setFreezeState(String freezeState) {
            this.freezeState = freezeState;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(int isCollection) {
            this.isCollection = isCollection;
        }

        public String getMinerPrice() {
            return minerPrice;
        }

        public void setMinerPrice(String minerPrice) {
            this.minerPrice = minerPrice;
        }

        public String getMiniDidPrice() {
            return miniDidPrice;
        }

        public void setMiniDidPrice(String miniDidPrice) {
            this.miniDidPrice = miniDidPrice;
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

        public String getOwnerAvatar() {
            return ownerAvatar;
        }

        public void setOwnerAvatar(String ownerAvatar) {
            this.ownerAvatar = ownerAvatar;
        }

        public String getOwnerNickName() {
            return ownerNickName;
        }

        public void setOwnerNickName(String ownerNickName) {
            this.ownerNickName = ownerNickName;
        }

        public String getOwnerWallet() {
            return ownerWallet;
        }

        public void setOwnerWallet(String ownerWallet) {
            this.ownerWallet = ownerWallet;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
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

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getProductVat() {
            return productVat;
        }

        public void setProductVat(String productVat) {
            this.productVat = productVat;
        }

        public String getProveUrl() {
            return proveUrl;
        }

        public void setProveUrl(String proveUrl) {
            this.proveUrl = proveUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRoyalty() {
            return royalty;
        }

        public void setRoyalty(String royalty) {
            this.royalty = royalty;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public int getSellMode() {
            return sellMode;
        }

        public void setSellMode(int sellMode) {
            this.sellMode = sellMode;
        }

        public int getSellState() {
            return sellState;
        }

        public void setSellState(int sellState) {
            this.sellState = sellState;
        }

        public int getSeveralRows() {
            return severalRows;
        }

        public void setSeveralRows(int severalRows) {
            this.severalRows = severalRows;
        }

        public int getTakeState() {
            return takeState;
        }

        public void setTakeState(int takeState) {
            this.takeState = takeState;
        }

        public int getTenantId() {
            return tenantId;
        }

        public void setTenantId(int tenantId) {
            this.tenantId = tenantId;
        }

        public String getViewingCount() {
            return viewingCount;
        }

        public void setViewingCount(String viewingCount) {
            this.viewingCount = viewingCount;
        }

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }
    }
}
