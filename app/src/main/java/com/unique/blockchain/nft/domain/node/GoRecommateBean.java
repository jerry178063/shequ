package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

public class GoRecommateBean extends BaseBean {

    private String delegator_address;
    private String validator_address;
    private String recommander_address;
    private Amount amount;

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

    public String getRecommander_address() {
        return recommander_address;
    }

    public void setRecommander_address(String recommander_address) {
        this.recommander_address = recommander_address;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public static class Amount{
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

}
