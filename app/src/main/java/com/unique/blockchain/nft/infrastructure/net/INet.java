package com.unique.blockchain.nft.infrastructure.net;

import android.content.Context;

import com.unique.blockchain.nft.domain.Skbuff;
import com.unique.blockchain.nft.websocket.Skbuffs;


public interface INet{

	// 初始化网络组件
	 int init(Context context);
	// 释放网络资源
	 void close();

	// 发送数据
	int send(Skbuff skb);

	// 管理上层模块
	int register(INetUpper iNetUpper);
}