package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class ChainConfigBean extends BaseBean {

    private int code;
    private String msg;
    private int total;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public class Rows{
        private int id;
        private String assetsLogo;
        private String assetsName;
        private String chainPre;
        private int status;
        private String minUnit;

        public String getMinUnit() {
            return minUnit;
        }

        public void setMinUnit(String minUnit) {
            this.minUnit = minUnit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAssetsLogo() {
            return assetsLogo;
        }

        public void setAssetsLogo(String assetsLogo) {
            this.assetsLogo = assetsLogo;
        }

        public String getAssetsName() {
            return assetsName;
        }

        public void setAssetsName(String assetsName) {
            this.assetsName = assetsName;
        }

        public String getChainPre() {
            return chainPre;
        }

        public void setChainPre(String chainPre) {
            this.chainPre = chainPre;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
