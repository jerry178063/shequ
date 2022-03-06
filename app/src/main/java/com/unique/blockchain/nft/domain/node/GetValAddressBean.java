package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

public class GetValAddressBean extends BaseBean {

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
        private String validatorsAddress;

        public String getValidatorsAddress() {
            return validatorsAddress;
        }

        public void setValidatorsAddress(String validatorsAddress) {
            this.validatorsAddress = validatorsAddress;
        }
    }
}
