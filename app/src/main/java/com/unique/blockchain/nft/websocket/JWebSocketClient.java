package com.unique.blockchain.nft.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    static URI mServerUri;

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
        mServerUri = serverUri;
    }

    private static JWebSocketClient instance;

    public static synchronized JWebSocketClient getInstance() {
        if (instance == null)          //1
            instance = new JWebSocketClient(mServerUri);  //2
        return instance;               //3
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e("JWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Log.e("JWebSocketClient", "onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("JWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.e("JWebSocketClient", "onError()");
    }

}
