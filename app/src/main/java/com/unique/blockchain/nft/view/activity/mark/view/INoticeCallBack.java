package com.unique.blockchain.nft.view.activity.mark.view;


import com.unique.blockchain.nft.domain.mark.NoticeMessageBean;

public interface INoticeCallBack {
    void loadNoticeData(NoticeMessageBean noticeMessageBean);
    void loadNoticeFail();
}
