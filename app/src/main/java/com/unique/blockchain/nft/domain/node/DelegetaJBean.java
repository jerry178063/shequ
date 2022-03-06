package com.unique.blockchain.nft.domain.node;

import com.google.gson.Gson;
import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class DelegetaJBean extends BaseBean {
    /**
     * result : [{"shares":"43000000.000000000000000000","reward":[{"amount":"256518955633.052090560528000000","denom":"ugauss"}],"delegator_address":"gauss18rk6lf3zajrj64d6ujvxflmufrymz2tk46lsau","validator_address":"gaussvaloper1yqwrcyqytnf3tsfqlr2rsczxc4964frrjydqg8","commissions":{},"validatorsinfo":{"moniker":"gauss","pubKey":"gaussvalcons15kuauhy93r7zh9sjm3dnkgtkceaem5qnp0ng3r"},"recommander_address":"gauss18rk6lf3zajrj64d6ujvxflmufrymz2tk46lsau"}]
     * jsonrpc :
     */

    private String jsonrpc;
    /**
     * shares : 43000000.000000000000000000
     * reward : [{"amount":"256518955633.052090560528000000","denom":"ugauss"}]
     * delegator_address : gauss18rk6lf3zajrj64d6ujvxflmufrymz2tk46lsau
     * validator_address : gaussvaloper1yqwrcyqytnf3tsfqlr2rsczxc4964frrjydqg8
     * commissions : {}
     * validatorsinfo : {"moniker":"gauss","pubKey":"gaussvalcons15kuauhy93r7zh9sjm3dnkgtkceaem5qnp0ng3r"}
     * recommander_address : gauss18rk6lf3zajrj64d6ujvxflmufrymz2tk46lsau
     */
    private int code;
    private String message;

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

    private List<ResultBean> result;


    public static DelegetaJBean objectFromData(String str) {

        return new Gson().fromJson(str, DelegetaJBean.class);
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String shares;
        private String delegator_address;
        private String validator_address;
        /**
         * moniker : gauss
         * pubKey : gaussvalcons15kuauhy93r7zh9sjm3dnkgtkceaem5qnp0ng3r
         */

        private ValidatorsinfoBean validatorsinfo;
        private String recommander_address;
        /**
         * amount : 256518955633.052090560528000000
         * denom : ugauss
         */

        private List<RewardBean> reward;
        private List<UBond> unbond;
        private Commissions  commissions;

        public Commissions getCommissions() {
            return commissions;
        }

        public void setCommissions(Commissions commissions) {
            this.commissions = commissions;
        }

        public List<UBond> getUnbond() {
            return unbond;
        }

        public void setUnbond(List<UBond> unbond) {
            this.unbond = unbond;
        }

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getShares() {
            return shares;
        }

        public void setShares(String shares) {
            this.shares = shares;
        }

        public String getDelegator_address() {
            return delegator_address;
        }

        public void setDelegator_address(String delegator_address) {
            this.delegator_address = delegator_address;
        }

        public String getValidator_address() {
            return validator_address;
        }

        public void setValidator_address(String validator_address) {
            this.validator_address = validator_address;
        }

        public ValidatorsinfoBean getValidatorsinfo() {
            return validatorsinfo;
        }

        public void setValidatorsinfo(ValidatorsinfoBean validatorsinfo) {
            this.validatorsinfo = validatorsinfo;
        }

        public String getRecommander_address() {
            return recommander_address;
        }

        public void setRecommander_address(String recommander_address) {
            this.recommander_address = recommander_address;
        }

        public List<RewardBean> getReward() {
            return reward;
        }

        public void setReward(List<RewardBean> reward) {
            this.reward = reward;
        }

        public static class ValidatorsinfoBean {
            private String moniker;
            private String pubKey;

            public static ValidatorsinfoBean objectFromData(String str) {

                return new Gson().fromJson(str, ValidatorsinfoBean.class);
            }

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

        public static class RewardBean {
            private String amount;
            private String denom;

            public static RewardBean objectFromData(String str) {

                return new Gson().fromJson(str, RewardBean.class);
            }

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

        public static class UBond {
            private String balance;

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }
        }
        public static class Commissions {
            private List <Commission>commission;

            public List<Commission> getCommission() {
                return commission;
            }

            public void setCommission(List<Commission> commission) {
                this.commission = commission;
            }

            public static class Commission{
                private  String amount;

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

                private  String denom;
            }
        }
    }
}
