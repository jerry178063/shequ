package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

//提取收益
public class GetTiquValitionBean extends BaseBean {

        private String delegator_address;
        private String validator_address;

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


}
