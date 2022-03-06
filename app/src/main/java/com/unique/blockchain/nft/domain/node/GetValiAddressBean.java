package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

/**
 * 获取验证人信息
 * */
public class GetValiAddressBean extends BaseBean {

    private List<Validator> validators;

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public class Validator{
        private String operator_address;
        private Consensus consensus_pubkey;
        private boolean jailed;
        private String status;
        private String tokens;
        private String delegator_shares;
        private String unbonding_height;
        private String unbonding_time;
        private Commission commission;
        private String min_self_delegation;
        private Commission_reallocation commission_reallocation;
        private Description description;

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public class Description{
            private String moniker;
            private String details;

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getMoniker() {
                return moniker;
            }

            public void setMoniker(String moniker) {
                this.moniker = moniker;
            }
        }

        public String getOperator_address() {
            return operator_address;
        }

        public void setOperator_address(String operator_address) {
            this.operator_address = operator_address;
        }

        public Consensus getConsensus_pubkey() {
            return consensus_pubkey;
        }

        public void setConsensus_pubkey(Consensus consensus_pubkey) {
            this.consensus_pubkey = consensus_pubkey;
        }

        public boolean isJailed() {
            return jailed;
        }

        public void setJailed(boolean jailed) {
            this.jailed = jailed;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTokens() {
            return tokens;
        }

        public void setTokens(String tokens) {
            this.tokens = tokens;
        }

        public String getDelegator_shares() {
            return delegator_shares;
        }

        public void setDelegator_shares(String delegator_shares) {
            this.delegator_shares = delegator_shares;
        }

        public String getUnbonding_height() {
            return unbonding_height;
        }

        public void setUnbonding_height(String unbonding_height) {
            this.unbonding_height = unbonding_height;
        }

        public String getUnbonding_time() {
            return unbonding_time;
        }

        public void setUnbonding_time(String unbonding_time) {
            this.unbonding_time = unbonding_time;
        }

        public Commission getCommission() {
            return commission;
        }

        public void setCommission(Commission commission) {
            this.commission = commission;
        }

        public String getMin_self_delegation() {
            return min_self_delegation;
        }

        public void setMin_self_delegation(String min_self_delegation) {
            this.min_self_delegation = min_self_delegation;
        }

        public Commission_reallocation getCommission_reallocation() {
            return commission_reallocation;
        }

        public void setCommission_reallocation(Commission_reallocation commission_reallocation) {
            this.commission_reallocation = commission_reallocation;
        }

        public class Consensus{
            private String key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
        public class Commission{
            private Commission_rates commission_rates;
            private String update_time;

            public Commission_rates getCommission_rates() {
                return commission_rates;
            }

            public void setCommission_rates(Commission_rates commission_rates) {
                this.commission_rates = commission_rates;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public class Commission_rates{
                private String rate;
                private String max_rate;
                private String max_change_rate;

                public String getRate() {
                    return rate;
                }

                public void setRate(String rate) {
                    this.rate = rate;
                }

                public String getMax_rate() {
                    return max_rate;
                }

                public void setMax_rate(String max_rate) {
                    this.max_rate = max_rate;
                }

                public String getMax_change_rate() {
                    return max_change_rate;
                }

                public void setMax_change_rate(String max_change_rate) {
                    this.max_change_rate = max_change_rate;
                }
            }
        }
        public class Commission_reallocation{
            private String reserve_rate;
            private String reallocated_rate;
            private String update_time;

            public String getReserve_rate() {
                return reserve_rate;
            }

            public void setReserve_rate(String reserve_rate) {
                this.reserve_rate = reserve_rate;
            }

            public String getReallocated_rate() {
                return reallocated_rate;
            }

            public void setReallocated_rate(String reallocated_rate) {
                this.reallocated_rate = reallocated_rate;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }
    }

}
