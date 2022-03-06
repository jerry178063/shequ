package com.unique.blockchain.nft.domain.node;

import java.util.List;

public class HomeAsstesBean {

    private String code;
    private String message;
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private List<Balances> balances;

        public List<Balances> getBalances() {
            return balances;
        }

        public void setBalances(List<Balances> balances) {
            this.balances = balances;
        }

        public class Balances{
            private String amount;
            private String denom;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDenom() {
                return denom;
            }

            public void setDenom(String denom) {
                this.denom = denom;
            }
        }
    }

}
