package com.unique.blockchain.nft.domain.wallet;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class AssetsInfoBean extends BaseBean {

    private int code;
    private String message;
    private List<Results> result;

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

    public List<Results> getResult() {
        return result;
    }

    public void setResult(List<Results> result) {
        this.result = result;
    }

    public static class Results{
        private String chain;
        private String util;
        private String circulation;

        public String getChain() {
            return chain;
        }

        public void setChain(String chain) {
            this.chain = chain;
        }

        public String getUtil() {
            return util;
        }

        public void setUtil(String util) {
            this.util = util;
        }

        public String getCirculation() {
            return circulation;
        }

        public void setCirculation(String circulation) {
            this.circulation = circulation;
        }
    }

}
