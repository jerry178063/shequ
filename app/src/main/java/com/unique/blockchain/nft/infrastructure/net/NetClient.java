package com.unique.blockchain.nft.infrastructure.net;

import android.content.Context;
import android.util.Log;

import com.unique.blockchain.nft.domain.Skbuff;
import com.unique.blockchain.nft.domain.SkbuffTwo;
import com.unique.blockchain.nft.websocket.Skbuffs;


public class NetClient implements INet{
    private INetUpper iNetUpper = null;
    private Socket socket = null;

    // 单体实例
    private static NetClient netClient = null;
    public static NetClient getInstance() {
        if (netClient == null) {
            synchronized (NetClient.class) {
                if (netClient == null) {
                    netClient = new NetClient();
                }
            }
        }
        return netClient;
    }

    public static void destroy() {
        if (netClient != null) {
            synchronized (NetClient.class) {
                if (netClient != null) {
                    netClient.exit();
                    netClient = null;
                }
            }
        }
    }

    // 实例内容
    private NetClient() {}

    public int init(Context context) {
        int rc = -1;

        this.socket = new Socket(this,context);
        this.socket.connect();

        return rc;
    }


    @Override
    public void close() {

    }


    /**
     * 断开连接
     */
    public void exit() {
        if(socket != null){
            socket.close();
            socket = null;
        }
    }

    public int register(INetUpper iNetUpper) {
        synchronized (this) {
            this.iNetUpper = iNetUpper;
        }

        return 0;
    }
    public int unregister(String id) {
        if(iNetUpper != null){
            iNetUpper = null;
        }
        return 0;
    }

    public int send(Skbuff skb) {
        int rc = -1;
//        if (null != socket) {
            String message = skb.toString();
        Log.e("FFS3325errr", "send: "+message);
        if(socket!=null){
            Log.d("message", "send:111 "+message);
            socket.Send(message);

        }
//        }

        return rc;
    }
    public int sendTwo(SkbuffTwo skb) {
        int rc = -1;
//        if (null != socket) {
        String message = skb.toString();
        Log.e("FFS3325errr22", "send: "+message);
        if(socket!=null){
            Log.d("message", "send:111 "+message);
            socket.Send(message);

        }
//        }

        return rc;
    }

    public void recv(String skb) {
//        String skbL = new Gson().toJson(skb);

        if (check(skb) == 0) {
            return;
        }
        if (iNetUpper != null) {
            Log.e("fifteenklinefragment","iNetUpper != null");
            iNetUpper.recv(skb);
        }else {
        }
    }

    protected int check(String skb){
        if(skb == null){
            return  0;
        }else {
            return 1;
        }
    }
    public void notify(int i){

    }
}