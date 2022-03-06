package com.unique.blockchain.nft.view.fragment.mark.view;

import com.unique.blockchain.nft.view.activity.database.ReDatabase;

public interface IMarkReMenzCallBack {
    void loadReMenzData(ReDatabase reDatabase);
    void loadReMenzFail();
}
