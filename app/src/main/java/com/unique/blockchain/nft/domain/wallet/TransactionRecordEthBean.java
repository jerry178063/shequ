package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

/**
 * 转账资产交易记录-eth
 * */
public class TransactionRecordEthBean extends BaseBean {

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
            private String from;//来源地址
            private String saveTime;//交易时间
            private String to;
            private String fee;
            private int status;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getSaveTime() {
                return saveTime;
            }

            public void setSaveTime(String saveTime) {
                this.saveTime = saveTime;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }


            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

        }

    }

}
