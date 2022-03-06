package com.unique.blockchain.nft.service.biz_router;

public  class BizRouterFactory{
	public static IBizRouter createDexBizRouter(){
		IBizRouter iBizRouter = new DexBizRouter();
		return iBizRouter;
	}
}