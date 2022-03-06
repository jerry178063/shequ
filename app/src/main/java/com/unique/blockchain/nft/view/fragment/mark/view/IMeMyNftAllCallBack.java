package com.unique.blockchain.nft.view.fragment.mark.view;


import com.unique.blockchain.nft.domain.mark.MarkHotBean;

public interface IMeMyNftAllCallBack {
    void loadMyNftAllData(MarkHotBean markHotBean);
    void loadMyNftAllFail();
}
