package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;
import com.unique.blockchain.nft.infrastructure.other.BigDecimalNum;

import java.math.BigDecimal;

public class GoTradeDetailBean extends BaseBean {

    private int code;
    private int total;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String balance;//个人余额
        private String currency;//币种
        private String currentPrice;//当前价格 ，最高价格
        private String endTime;//销售结束时间
        private int examineState;//审核状态
        private int foundState;//铸造状态
        private String foundTime;//铸造时间
        private int freezeState;//NFT状态：0流通，1：锁定，2：永久冻结
        private int id;//id
        private int isCollection;//是否收藏 1收藏 0没有收藏
        private String metadataUrl;//元数据url
        private String minerPrice;//矿工费
        private String miniDidPrice;//最低竞标价格
        private String nftId;//NFTID 产品ID
        private String nftName;//NFT名称
        private String ownerAvatar;//拥有者头像地址
        private String ownerNickName;//拥有者昵称
        private String ownerWallet;//拥有者钱包地址
        private String productId;//产品id
        private String productImage;//产品缩略图
        private String productIntro;//产品简介
        private String productName;//产品名称
        private int productType;//产品类型 票务：1，收藏品：2, 艺术品：3，轻奢品:4
        private BigDecimal productVat;//增值税
        private String proveUrl;//产权证明路径
        private String remark;//Nft备注，简介
        private String royalty;//版税
        private String seatNumber;//座位号
        private String seeCount;//浏览数
        private String sellMode;//1：固定价格，2：定时拍卖
        private String sellState;//销售状态
        private String severalRows;//排
        private int takeState;//提货状态
        private String tenantId;//租户ID
        private String walletAddr;//铸造者钱包地址
        private String contractUrl;//合同链接地址
        private String contractName;//合同名称
        private String shareUrl;//分享地址
        private String shareTitle;//分享标题
        private String productCover;
        private String auctionTimeInterval;//最高价的时间间隔
        private String confirmationDate;//最后一次出价的时间
        private String nftState;
        private String metatataHash;//企业元数据
        private String metatataCode;//元数据
        private String startPrice;//固定价格
        private String iconUrl;
        private String surplus;

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(String startPrice) {
            this.startPrice = startPrice;
        }

        public String getMetatataHash() {
            return metatataHash;
        }

        public void setMetatataHash(String metatataHash) {
            this.metatataHash = metatataHash;
        }

        public String getNftState() {
            return nftState;
        }

        public void setNftState(String nftState) {
            this.nftState = nftState;
        }

        public String getConfirmationDate() {
            return confirmationDate;
        }

        public void setConfirmationDate(String confirmationDate) {
            this.confirmationDate = confirmationDate;
        }

        public String getAuctionTimeInterval() {
            return auctionTimeInterval;
        }

        public void setAuctionTimeInterval(String auctionTimeInterval) {
            this.auctionTimeInterval = auctionTimeInterval;
        }

        public String getProductCover() {
            return productCover;
        }

        public void setProductCover(String productCover) {
            this.productCover = productCover;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getContractUrl() {
            return contractUrl;
        }

        public void setContractUrl(String contractUrl) {
            this.contractUrl = contractUrl;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
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

        public int getExamineState() {
            return examineState;
        }

        public void setExamineState(int examineState) {
            this.examineState = examineState;
        }

        public int getFoundState() {
            return foundState;
        }

        public void setFoundState(int foundState) {
            this.foundState = foundState;
        }

        public String getFoundTime() {
            return foundTime;
        }

        public void setFoundTime(String foundTime) {
            this.foundTime = foundTime;
        }

        public int getFreezeState() {
            return freezeState;
        }

        public void setFreezeState(int freezeState) {
            this.freezeState = freezeState;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(int isCollection) {
            this.isCollection = isCollection;
        }

        public String getMetadataUrl() {
            return metadataUrl;
        }

        public void setMetadataUrl(String metadataUrl) {
            this.metadataUrl = metadataUrl;
        }

        public String getMetatataCode() {
            return metatataCode;
        }

        public void setMetatataCode(String metatataCode) {
            this.metatataCode = metatataCode;
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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
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

        public BigDecimal getProductVat() {
            return productVat;
        }

        public void setProductVat(BigDecimal productVat) {
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

        public String getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getSeeCount() {
            return seeCount;
        }

        public void setSeeCount(String seeCount) {
            this.seeCount = seeCount;
        }

        public String getSellMode() {
            return sellMode;
        }

        public void setSellMode(String sellMode) {
            this.sellMode = sellMode;
        }

        public String getSellState() {
            return sellState;
        }

        public void setSellState(String sellState) {
            this.sellState = sellState;
        }

        public String getSeveralRows() {
            return severalRows;
        }

        public void setSeveralRows(String severalRows) {
            this.severalRows = severalRows;
        }


        public int getTakeState() {
            return takeState;
        }

        public void setTakeState(int takeState) {
            this.takeState = takeState;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }
    }
}
