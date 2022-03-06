package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

/**
 * 转账资产交易记录
 * */
public class TransactionRecordBean extends BaseBean {

    private int code;
    private String message;
    private Result result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private int page;
        private int pages;
        private List<Records> records;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<Records> getRecords() {
            return records;
        }

        public void setRecords(List<Records> records) {
            this.records = records;
        }

        public class Records{
            private String amount;//转账的数量
            private String event;//转入还是转出
            private String fromAddress;//来源地址
            private String ids;//订单号
            private String txTime;//交易时间
            private String toAddress;
            private String fee;
            private int transationStatus;//转账状态 1成功 2失败

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getEvent() {
                return event;
            }

            public void setEvent(String event) {
                this.event = event;
            }

            public String getFromAddress() {
                return fromAddress;
            }

            public void setFromAddress(String fromAddress) {
                this.fromAddress = fromAddress;
            }

            public String getToAddress() {
                return toAddress;
            }

            public void setToAddress(String toAddress) {
                this.toAddress = toAddress;
            }

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public String getTxTime() {
                return txTime;
            }

            public void setTxTime(String txTime) {
                this.txTime = txTime;
            }

            public int getTransationStatus() {
                return transationStatus;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public void setTransationStatus(int transationStatus) {
                this.transationStatus = transationStatus;
            }
        }

    }

}
