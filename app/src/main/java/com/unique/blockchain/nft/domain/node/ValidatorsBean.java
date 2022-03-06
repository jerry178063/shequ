package com.unique.blockchain.nft.domain.node;


import com.unique.blockchain.nft.domain.BaseBean;

public class ValidatorsBean extends BaseBean {

    private Validator validator;

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public class Validator {


        private String operator_address;
        private String tokens;
        private String delegator_shares;
        private Description description;

        public String getOperator_address() {
            return operator_address;
        }

        public void setOperator_address(String operator_address) {
            this.operator_address = operator_address;
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

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public class Description {
            private String moniker;

            public String getMoniker() {
                return moniker;
            }

            public void setMoniker(String moniker) {
                this.moniker = moniker;
            }
        }
    }
}
