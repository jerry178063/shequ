package com.unique.blockchain.nft.service.biz_router;


import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.websocket.Skbuff;

public interface IBizUpper {
	 String id();
	 void recv(Skbuff skb);
	 void notify(NetEvent event);
}