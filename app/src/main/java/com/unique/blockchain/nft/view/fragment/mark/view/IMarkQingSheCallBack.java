package com.unique.blockchain.nft.view.fragment.mark.view;

import com.unique.blockchain.nft.domain.mark.QuanDatabase;

public interface IMarkQingSheCallBack {
    void loadQingSheData(QuanDatabase quanDatabase);
    void loadQingSheFail();
}
