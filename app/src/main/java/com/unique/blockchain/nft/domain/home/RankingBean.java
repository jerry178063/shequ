package com.unique.blockchain.nft.domain.home;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;


public class RankingBean extends BaseBean {

    private int code;
    private String msg;
    private List<Datas> data;

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

    public List<Datas> getData() {
        return data;
    }

    public void setData(List<Datas> data) {
        this.data = data;
    }

    public class Datas{
        private int circulationCount;
        private String nftId;
        private String iconUrl;
        private int seeCount;
        private int productType;
        private String currentPrice;
        private String marketPriceType;

        public String getMarketPriceType() {
            return marketPriceType;
        }

        public void setMarketPriceType(String marketPriceType) {
            this.marketPriceType = marketPriceType;
        }

        public int getCirculationCount() {
            return circulationCount;
        }

        public void setCirculationCount(int circulationCount) {
            this.circulationCount = circulationCount;
        }

        public String getNftId() {
            return nftId;
        }

        public void setNftId(String nftId) {
            this.nftId = nftId;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getSeeCount() {
            return seeCount;
        }

        public void setSeeCount(int seeCount) {
            this.seeCount = seeCount;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }
    }

}
