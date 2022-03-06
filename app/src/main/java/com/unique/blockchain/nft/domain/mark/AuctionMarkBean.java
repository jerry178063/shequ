package com.unique.blockchain.nft.domain.mark;

import com.unique.blockchain.nft.domain.BaseBean;

/**
 * 竞标价格
 */
public class AuctionMarkBean extends BaseBean {

        private Params params;
        private Integer id;
        private String nftId;
        private String walletAddr;
        private int transactionType;
        private String completTime;
        private String price;


    public Params getParams() {
            return params;
        }

        public void setParams(Params params) {
            this.params = params;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNftId() {
            return nftId;
        }

        public void setNftId(String nftId) {
            this.nftId = nftId;
        }

        public String getWalletAddr() {
            return walletAddr;
        }

        public void setWalletAddr(String walletAddr) {
            this.walletAddr = walletAddr;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
        }

    public String getCompletTime() {
        return completTime;
    }

    public void setCompletTime(String completTime) {
        this.completTime = completTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public class Params {
        }
}
