package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

/**
 * 转账资产交易记录
 * */
public class TransactionRecordNewBean extends BaseBean {

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
            private String address;//来源地址
            private long timestamp;//交易时间
            private String otherAdress;
            private String fee;
            private int isInput;
            private int status;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }


            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            public String getOtherAdress() {
                return otherAdress;
            }

            public void setOtherAdress(String otherAdress) {
                this.otherAdress = otherAdress;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public int getIsInput() {
                return isInput;
            }

            public void setIsInput(int isInput) {
                this.isInput = isInput;
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
