package com.unique.blockchain.nft.view.activity.me.me_view;

import com.unique.blockchain.nft.domain.mark.QuanDatabase;

public interface IMeCollectionCallBack {
    void loadCollectionData(QuanDatabase quanDatabase);
    void loadCollectionFail();
}
