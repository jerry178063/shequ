package com.unique.blockchain.nft.domain.trade;

import com.unique.blockchain.nft.domain.BaseBean;

public class GoTradeCationBean extends BaseBean {

    private int code;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class Data{
        private String cautionMoney;

        public String getCautionMoney() {
            return cautionMoney;
        }

        public void setCautionMoney(String cautionMoney) {
            this.cautionMoney = cautionMoney;
        }
    }

}
