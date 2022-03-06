package com.unique.blockchain.nft.view.fragment.mark.view;


import com.unique.blockchain.nft.domain.mark.MarkHotBean;

public interface IMeMyNftSearchCallBack {
    void loadMyNftSearchData(MarkHotBean markHotBean);
    void loadMyNftSearchFail();
}
