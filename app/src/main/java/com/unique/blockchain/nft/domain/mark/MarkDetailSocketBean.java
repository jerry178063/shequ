package com.unique.blockchain.nft.domain.mark;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class MarkDetailSocketBean extends BaseBean {
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
        private String gid;
        private DataModuleVOList dataModuleVOList;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public DataModuleVOList getDataModuleVOList() {
            return dataModuleVOList;
        }

        public void setDataModuleVOList(DataModuleVOList dataModuleVOList) {
            this.dataModuleVOList = dataModuleVOList;
        }

        public class DataModuleVOList{
            private NftMaxPrice nftMaxPrice;
            private NftSeeCount nftSeeCount;
            private NftAuctionLog nftAuctionLog;
            private NftHolderLog nftHolderLog;//持有者记录
            private NftMaxWalletAddr nftMaxWalletAddr;//最高竞价者

            public NftMaxPrice getNftMaxPrice() {
                return nftMaxPrice;
            }

            public void setNftMaxPrice(NftMaxPrice nftMaxPrice) {
                this.nftMaxPrice = nftMaxPrice;
            }

            public NftSeeCount getNftSeeCount() {
                return nftSeeCount;
            }

            public void setNftSeeCount(NftSeeCount nftSeeCount) {
                this.nftSeeCount = nftSeeCount;
            }

            public NftAuctionLog getNftAuctionLog() {
                return nftAuctionLog;
            }

            public void setNftAuctionLog(NftAuctionLog nftAuctionLog) {
                this.nftAuctionLog = nftAuctionLog;
            }

            public NftHolderLog getNftHolderLog() {
                return nftHolderLog;
            }

            public void setNftHolderLog(NftHolderLog nftHolderLog) {
                this.nftHolderLog = nftHolderLog;
            }

            public NftMaxWalletAddr getNftMaxWalletAddr() {
                return nftMaxWalletAddr;
            }

            public void setNftMaxWalletAddr(NftMaxWalletAddr nftMaxWalletAddr) {
                this.nftMaxWalletAddr = nftMaxWalletAddr;
            }

            public class NftMaxPrice{//最高价格
                private String data;
                private String moduleId;

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

                public String getModuleId() {
                    return moduleId;
                }

                public void setModuleId(String moduleId) {
                    this.moduleId = moduleId;
                }
            }
            public class NftSeeCount{//浏览数
                private String data;
                private String moduleId;

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

                public String getModuleId() {
                    return moduleId;
                }

                public void setModuleId(String moduleId) {
                    this.moduleId = moduleId;
                }
            }
            public class NftAuctionLog{//竞标价格
                private List<DataAuction> data;

                public List<DataAuction> getData() {
                    return data;
                }

                public void setData(List<DataAuction> data) {
                    this.data = data;
                }

                public class DataAuction{
                    private int id;
                    private String price;
                    private String currency;//单位
                    private String walletAddr;//钱包地址
                    private String completTime;//时间

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getCurrency() {
                        return currency;
                    }

                    public void setCurrency(String currency) {
                        this.currency = currency;
                    }

                    public String getWalletAddr() {
                        return walletAddr;
                    }

                    public void setWalletAddr(String walletAddr) {
                        this.walletAddr = walletAddr;
                    }

                    public String getCompletTime() {
                        return completTime;
                    }

                    public void setCompletTime(String completTime) {
                        this.completTime = completTime;
                    }
                }
            }
            public class NftHolderLog{
                private List<DataHolder> data;

                public List<DataHolder> getData() {
                    return data;
                }

                public void setData(List<DataHolder> data) {
                    this.data = data;
                }

                public class DataHolder{
                    private int id;
                    private String walletAddr;
                    private String completTime;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getWalletAddr() {
                        return walletAddr;
                    }

                    public void setWalletAddr(String walletAddr) {
                        this.walletAddr = walletAddr;
                    }

                    public String getCompletTime() {
                        return completTime;
                    }

                    public void setCompletTime(String completTime) {
                        this.completTime = completTime;
                    }
                }
            }
            public class NftMaxWalletAddr{
                private DataMax data;

                public DataMax getData() {
                    return data;
                }

                public void setData(DataMax data) {
                    this.data = data;
                }

                public class DataMax{
                    private String walletAddr;
                    private String sectionCountdownTime;
                    private String overCountdownTime;
                    private int sellMode;

                    public String getWalletAddr() {
                        return walletAddr;
                    }

                    public void setWalletAddr(String walletAddr) {
                        this.walletAddr = walletAddr;
                    }

                    public String getSectionCountdownTime() {
                        return sectionCountdownTime;
                    }

                    public void setSectionCountdownTime(String sectionCountdownTime) {
                        this.sectionCountdownTime = sectionCountdownTime;
                    }

                    public String getOverCountdownTime() {
                        return overCountdownTime;
                    }

                    public void setOverCountdownTime(String overCountdownTime) {
                        this.overCountdownTime = overCountdownTime;
                    }

                    public int getSellMode() {
                        return sellMode;
                    }

                    public void setSellMode(int sellMode) {
                        this.sellMode = sellMode;
                    }
                }
            }
        }
    }

}
