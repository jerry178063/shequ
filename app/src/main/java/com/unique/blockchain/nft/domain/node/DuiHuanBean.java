package com.unique.blockchain.nft.domain.node;

import java.util.List;

public class DuiHuanBean {

    private String code;
    private String message;
    private List<Result> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        private String name;
        private String ibc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIbc() {
            return ibc;
        }

        public void setIbc(String ibc) {
            this.ibc = ibc;
        }
    }

}
