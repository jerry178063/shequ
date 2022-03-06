package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class ValidatorsTwoBean extends BaseBean {

    private int code;
    private String message;
    private List<Result> result;

    public class Result{
        private String shares;
        private String delegator_address;
        private String validator_address;
        private String recommander_address;


    }

}
