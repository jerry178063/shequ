package com.unique.blockchain.nft.view.fragment.mark.view;

import com.unique.blockchain.nft.domain.mark.QuanDatabase;

public interface IMarkTicketCallBack {
    void loadTicketData(QuanDatabase quanDatabase);
    void loadTicketFail();
}
