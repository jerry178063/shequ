package com.unique.blockchain.nft.service.biz_router;


import com.unique.blockchain.nft.infrastructure.net.INetUpper;
import com.unique.blockchain.nft.infrastructure.net.NetEvent;
import com.unique.blockchain.nft.websocket.Skbuff;

public abstract class BizRouter implements INetUpper, IBizRouter {
    private static IBizRouter iBizRouter = null;
    private IBizUpper iBizUpper = null;
    private NetEvent netEvent;

    public static void setInstance(IBizRouter iBizRouter) {
        synchronized (iBizRouter) {
            BizRouter.iBizRouter = iBizRouter;
        }
    }

    public static IBizRouter getInstance() {
        return BizRouter.iBizRouter;
    }

    public INetUpper inetUpper() {
        return this;
    }

    protected boolean checkProtocol(Skbuff skb) {//检测协议是否正确

//		return (equal(skb.getData().getGroup().getGid(), iBizUpper.Id()) == 0);
        return equal(skb.getData().getGroup().getGid() + "", "444");
    }

    private boolean equal(String gid, String ibizUpperId) {
        if (gid != null && ibizUpperId != null) {
            if (gid.equals(ibizUpperId)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void recv(Skbuff skb) {//接收消息
        if (this.iBizUpper == null) {
            return;
        }

        if (checkProtocol(skb)) {
            this.iBizUpper.recv(skb);
        }
    }

    public void notify(int event) {//通知给上层
//        if (this.iBizUpper != null) {
//            switch (event) {
//				case NetEvent.EVENT_SOCK_OPENED:
//                    this.iBizUpper.notify(netEvent);
//                    break;
//				case NetEvent.EVENT_SOCK_CLOSED:
//				case NetEvent.EVENT_SOCK_CONNECTED:
//				case NetEvent.EVENT_SOCK_DISCONNECTED:
//
//                    break;
//                break;
//            }
//        }
    }

    public void register(IBizUpper iBizUpper) {//注册
        synchronized (this) {
            this.iBizUpper = iBizUpper;
        }
    }
}