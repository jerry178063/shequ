package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class AllValiMoneryBean extends BaseBean {

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
        private List<Rewards> rewards;

        public List<Rewards> getRewards() {
            return rewards;
        }

        public void setRewards(List<Rewards> rewards) {
            this.rewards = rewards;
        }

        public class Rewards{
            private String amount;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }
    }

}
