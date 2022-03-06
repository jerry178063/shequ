package com.unique.blockchain.nft.infrastructure.net;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.unique.blockchain.nft.infrastructure.utils.Util;
import com.unique.blockchain.nft.websocket.JWebSocketClient;

import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import javax.net.ssl.SSLSocket;


public class Socket {
    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测

    private JWebSocketClient websocketClient = null;
    private NetClient netClient = null;
    SSLSocket sslSocket;
    Context context;

    public Socket(NetClient netClient,Context context) {
        this.netClient = netClient;
        this.context = context;
        initSocket(context);
        startHeartBeat();
    }

    private static final int SERVER_PORT = 8090;//端口号
    private static final String SERVER_IP = "192.168.2.195";//连接IP
    private static final String CLIENT_KET_PASSWORD = "123456";//私钥密码
    private static final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器
    private static final String CLIENT_TRUST_MANAGER = "X509";//
    private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";//
    private static final String ENCONDING = "utf-8";//字符集
    private SSLSocket Client_sslSocket;
    private String TAG = "Socket";
    private void initSocket(Context context) {
        URI uri = URI.create(Util.ws2);
//        URI uri = URI.create("");
        this.websocketClient = new JWebSocketClient(uri) {

            @Override
            public void onMessage(String message) {
                Log.e("FF3442355", "原始数据:" + message);
                Log.e("FF23444343g", "原始数据:"+ message);
                netClient.recv(message);
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                Log.e("FF34423", "websocket连接成功");

                netClient.notify(NetEvent.EVENT_SOCK_OPENED);
            }


        };


    }

    protected void Send(String message) {
        if (this.websocketClient != null) {
            if (websocketClient.isOpen()) {
                this.websocketClient.send(message);
            }
        }
    }


    public void connect() {//连接socket
        new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    if (websocketClient.getReadyState() == ReadyState.NOT_YET_CONNECTED) {
                        if (websocketClient.isClosed()) {
                            try {
                                websocketClient.reconnectBlocking();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                websocketClient.connectBlocking();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (websocketClient.getReadyState() == ReadyState.CLOSED) {
                        try {
                            websocketClient.reconnectBlocking();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }


    public void close() {
        try {
            if (null != websocketClient) {
                websocketClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            websocketClient = null;
        }
    }

    //    -------------------------------------websocket心跳检测------------------------------------------------
    private Handler heartBeatHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("Socket", "心跳包检测websocket连接状态");
            if (websocketClient != null) {
                if (websocketClient.isClosed()) {
                    reconnectWs();
                }
            } else {
                //如果client已为空，重新初始化连接
                Log.e("Socket", "client已为空，重新初始化连接");
                websocketClient = null;
                //重新开始初始化socket
                initSocket(context);
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            heartBeatHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    public void startHeartBeat() {
        heartBeatHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        heartBeatHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("Socket", "开启重连");
                    websocketClient.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}