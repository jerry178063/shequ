package com.unique.blockchain.nft.domain;

import java.util.List;

/**
 * socket数据 发送给服务的bean文件
 * */
public class Skbuff extends BaseBean {

    private String flags;
    private String magic;
    private String version;
    private String reserve;
    private String cmd;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }


    public static class Data {
        private Group group;


        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public static class Group {
            private String gid;
            private String coupleId;//交易对id
            private String did;
            private List<ModuleList> moduleList;
            private int noticeType;
            private String walletAddr;

            public String getWalletAddr() {
                return walletAddr;
            }

            public void setWalletAddr(String walletAddr) {
                this.walletAddr = walletAddr;
            }

            public int getNoticeType() {
                return noticeType;
            }

            public void setNoticeType(int noticeType) {
                this.noticeType = noticeType;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getCoupleId() {
                return coupleId;
            }

            public void setCoupleId(String coupleId) {
                this.coupleId = coupleId;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public List<ModuleList> getModuleList() {
                return moduleList;
            }

            public void setModuleList(List<ModuleList> moduleList) {
                this.moduleList = moduleList;
            }

            public static class ModuleList {
                private String moduleId;
                private int pageSize;
                private int page;


                public String getModuleId() {
                    return moduleId;
                }

                public void setModuleId(String moduleId) {
                    this.moduleId = moduleId;
                }


                public int getPageSize() {
                    return pageSize;
                }

                public void setPageSize(int pageSize) {
                    this.pageSize = pageSize;
                }

                public int getPage() {
                    return page;
                }

                public void setPage(int page) {
                    this.page = page;
                }
            }
        }
    }
}
