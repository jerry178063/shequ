package com.unique.blockchain.nft.websocket;


import com.unique.blockchain.nft.domain.BaseBean;

/**
 * socket数据 发送给服务的bean文件
 * */
public class Skbuffs extends BaseBean {
    private Data data;
    private String flags;
    private String reserve;
    private String magic;
    private String version;
    private String cmd;

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

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
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

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
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
            private String coupleId;
            private String did;
            private String gid;
            private String noticeType;
            private String walletAddr;

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

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getNoticeType() {
                return noticeType;
            }

            public void setNoticeType(String noticeType) {
                this.noticeType = noticeType;
            }

            public String getWalletAddr() {
                return walletAddr;
            }

            public void setWalletAddr(String walletAddr) {
                this.walletAddr = walletAddr;
            }
        }
    }
}
