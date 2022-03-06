package com.unique.blockchain.nft.infrastructure.net;

//网络层向上分发数据
public interface INetUpper{
	// 模块ID
	 String id();
	 void recv(String skb);
	 void notify(NetEvent event);
}