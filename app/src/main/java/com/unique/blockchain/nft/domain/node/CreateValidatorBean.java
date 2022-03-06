package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

/**
 * 成为验证人bean
 * */
public class CreateValidatorBean extends BaseBean {

    private Description description;
    private Commission commission;
    private String min_self_delegation;
    private String delegator_address;
    private String validator_address;
    private Pubkey pubkey;
    private Value value;
    private Commission_reallocation commission_reallocation;

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
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

    public Pubkey getPubkey() {
        return pubkey;
    }

    public void setPubkey(Pubkey pubkey) {
        this.pubkey = pubkey;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Commission_reallocation getCommission_reallocation() {
        return commission_reallocation;
    }

    public void setCommission_reallocation(Commission_reallocation commission_reallocation) {
        this.commission_reallocation = commission_reallocation;
    }

    public static class Description{
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

    public static class Commission{
        private String max_change_rate;
        private String max_rate;
        private String rate;


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

    public static class Pubkey{
        private String type;
        private String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Value{
        private String denom;
        private String amount;

        public String getDenom() {
            return denom;
        }

        public void setDenom(String denom) {
            this.denom = denom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
    public static class Commission_reallocation{
        private String reallocated_rate;
        private String reserve_rate;
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
