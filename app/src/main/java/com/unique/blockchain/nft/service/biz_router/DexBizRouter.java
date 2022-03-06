package com.unique.blockchain.nft.service.biz_router;


import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.websocket.Skbuff;

public class DexBizRouter extends BizRouter{
	
	protected boolean checkProtocol(Skbuff skb){

		return false;
	}

	@Override
	public String id() {
		return null;
	}

	@Override
	public void recv(String skb) {

    }

	@Override
	public void notify(NetEvent event) {

	}

	@Override
	public void init() {

	}

	@Override
	public void exit() {

	}

	@Override
	public int send(Skbuff skb) {
		return 0;
	}

}