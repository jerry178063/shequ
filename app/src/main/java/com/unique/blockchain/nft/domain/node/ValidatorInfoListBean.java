package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class ValidatorInfoListBean extends BaseBean {

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

        private List<Validators> validators;

        public List<Validators> getValidators() {
            return validators;
        }

        public void setValidators(List<Validators> validators) {
            this.validators = validators;
        }

        public class Validators{
            private String operatorAddress;
            private String pledge;
            private String moniker;
            private String yearRate;
            private String delegationsCount;
            private String pubKey;

            public String getOperatorAddress() {
                return operatorAddress;
            }

            public void setOperatorAddress(String operatorAddress) {
                this.operatorAddress = operatorAddress;
            }

            public String getPledge() {
                return pledge;
            }

            public void setPledge(String pledge) {
                this.pledge = pledge;
            }

            public String getMoniker() {
                return moniker;
            }

            public void setMoniker(String moniker) {
                this.moniker = moniker;
            }

            public String getYearRate() {
                return yearRate;
            }

            public void setYearRate(String yearRate) {
                this.yearRate = yearRate;
            }

            public String getDelegationsCount() {
                return delegationsCount;
            }

            public void setDelegationsCount(String delegationsCount) {
                this.delegationsCount = delegationsCount;
            }

            public String getPubKey() {
                return pubKey;
            }

            public void setPubKey(String pubKey) {
                this.pubKey = pubKey;
            }
        }

    }

}
