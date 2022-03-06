package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class GoTradeOwnerBean extends BaseBean {

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

      private String walletAddr;
      private String completTime;

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
