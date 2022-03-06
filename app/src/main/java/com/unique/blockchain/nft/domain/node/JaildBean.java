package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

//监狱状态
public class JaildBean extends BaseBean {

    private Validator validator;

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public class Validator{
        private boolean jailed;

        public boolean isJailed() {
            return jailed;
        }

        public void setJailed(boolean jailed) {
            this.jailed = jailed;
        }
    }

}
