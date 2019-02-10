package com.example.mrrobot.ihome.Services;

import com.example.mrrobot.ihome.Config.ServerSocketIO;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketIO {
    private static Socket mSocket;
    private static final String CHAT_SERVER_URL=ServerSocketIO.URL;

    public static Socket getSocket() {
        if (mSocket==null){
            try {
                mSocket = IO.socket(CHAT_SERVER_URL);

                if(!mSocket.connected()){

                    mSocket.connect();



                }
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return mSocket;
    }
    public void connect(){
        mSocket.connect();
    }
}