package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

//提取收益
public class ChangeRateBean extends BaseBean {

        private String commission_rate;
        private String validator_address;
        private Description description;

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
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

        public static class Description{
            private String moniker;
            private String details;

            public String getMoniker() {
                return moniker;
            }

            public void setMoniker(String moniker) {
                this.moniker = moniker;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }
        }

}
