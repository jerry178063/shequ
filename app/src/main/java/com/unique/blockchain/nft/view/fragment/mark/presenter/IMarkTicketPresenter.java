package com.unique.blockchain.nft.view.fragment.mark.presenter;

import com.unique.blockchain.nft.view.fragment.mark.view.IMarkTicketCallBack;

public interface IMarkTicketPresenter {
    void getData(int page,int pageSum,String sellMode,String type);

    void registerViewCallback(IMarkTicketCallBack callback);

    void unRegisterViewCallback(IMarkTicketCallBack callback);
}
