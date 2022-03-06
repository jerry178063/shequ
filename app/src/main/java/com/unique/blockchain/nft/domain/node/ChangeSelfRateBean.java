package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

//提取收益
public class ChangeSelfRateBean extends BaseBean {

        private String commission_rate;
        private String validator_address;
        private String commission_reserve_rate;

        private Description description;

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
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

        public String getCommission_reserve_rate() {
            return commission_reserve_rate;
        }

        public void setCommission_reserve_rate(String commission_reserve_rate) {
            this.commission_reserve_rate = commission_reserve_rate;
        }

        public String getCommission_rate() {
            return commission_rate;
        }

        public void setCommission_rate(String commission_rate) {
            this.commission_rate = commission_rate;
        }

        public String getValidator_address() {
            return validator_address;
        }

        public void setValidator_address(String validator_address) {
            this.validator_address = validator_address;
        }

}
