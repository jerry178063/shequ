package com.unique.blockchain.nft.view.activity.mark.view;


import com.unique.blockchain.nft.domain.mark.CollectionBean;

public interface IMarkCollectionCallBack {
    void loadCollectionPostData(CollectionBean markOutPriceBean);
    void loadCollectionPostFail();
}
