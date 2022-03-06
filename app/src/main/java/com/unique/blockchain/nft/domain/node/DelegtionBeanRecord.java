package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

//委托记录-委托
public class DelegtionBeanRecord extends BaseBean {

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
            private String agreeOrder;
            private String amount;
            private String blockHeight;
            private String coin;
            private String event;
            private String fee;
            private String fromAddress;
            private int gas;
            private String txTime;
            private int transationStatus;
            private Validatorsinfo validatorsinfo;
            private DelegatorTransfer delegatorTransfer;

            public DelegatorTransfer getDelegatorTransfer() {
                return delegatorTransfer;
            }

            public void setDelegatorTransfer(DelegatorTransfer delegatorTransfer) {
                this.delegatorTransfer = delegatorTransfer;
            }

            public int getTransationStatus() {
                return transationStatus;
            }

            public void setTransationStatus(int transationStatus) {
                this.transationStatus = transationStatus;
            }

            public String getAgreeOrder() {
                return agreeOrder;
            }

            public void setAgreeOrder(String agreeOrder) {
                this.agreeOrder = agreeOrder;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBlockHeight() {
                return blockHeight;
            }

            public void setBlockHeight(String blockHeight) {
                this.blockHeight = blockHeight;
            }

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }

            public String getEvent() {
                return event;
            }

            public void setEvent(String event) {
                this.event = event;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getFromAddress() {
                return fromAddress;
            }

            public void setFromAddress(String fromAddress) {
                this.fromAddress = fromAddress;
            }

            public int getGas() {
                return gas;
            }

            public void setGas(int gas) {
                this.gas = gas;
            }

            public String getTxTime() {
                return txTime;
            }

            public void setTxTime(String txTime) {
                this.txTime = txTime;
            }

            public Validatorsinfo getValidatorsinfo() {
                return validatorsinfo;
            }

            public void setValidatorsinfo(Validatorsinfo validatorsinfo) {
                this.validatorsinfo = validatorsinfo;
            }

            public class Validatorsinfo{
                private String moniker;
                private String pubKey;

                public String getMoniker() {
                    return moniker;
                }

                public void setMoniker(String moniker) {
                    this.moniker = moniker;
                }

                public String getPubKey() {
                    return pubKey;
                }

                public void setPubKey(String pubKey) {
                    this.pubKey = pubKey;
                }
            }
            public class DelegatorTransfer{
                private String completion_time;

                public String getCompletion_time() {
                    return completion_time;
                }

                public void setCompletion_time(String completion_time) {
                    this.completion_time = completion_time;
                }
            }
        }

    }


}
