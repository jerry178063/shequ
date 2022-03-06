package com.unique.blockchain.nft.service.biz_router;


import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.websocket.Skbuff;

//网络层向下发送数据
public interface IBizRouter{
	 void init();
	 void exit();

	 int send(Skbuff skb);

	// iBizUpper can be null
	 void register(IBizUpper iBizUpper);//向网络层注册服务 告知需要请求的页面

	 INetUpper inetUpper();
}