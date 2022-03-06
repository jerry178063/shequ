package com.unique.blockchain.nft.domain.mark;

import com.unique.blockchain.nft.domain.BaseBean;

/**
 * 最后一次出价时间
 * */
public class MarkConfirmationBean extends BaseBean {

    private String confirmationDate;
    private String curSystemTime;
    private String auctionTimeInterval;//时间间隔

    public String getAuctionTimeInterval() {
        return auctionTimeInterval;
    }

    public void setAuctionTimeInterval(String auctionTimeInterval) {
        this.auctionTimeInterval = auctionTimeInterval;
    }

    public String getCurSystemTime() {
        return curSystemTime;
    }

    public void setCurSystemTime(String curSystemTime) {
        this.curSystemTime = curSystemTime;
    }

    public String getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
}
