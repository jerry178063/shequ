package com.unique.blockchain.nft.domain.node;

import java.util.List;

public class WalletAsstesBean {

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
            private String coinType;
            private String balance;

            public String getCoinType() {
                return coinType;
            }

            public void setCoinType(String coinType) {
                this.coinType = coinType;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }
        }
    }

}
