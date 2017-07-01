package com.iqoptions.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqoptions.dto.SocketData;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiF on 24.06.2017.
 */
public class WSClient  {

    private final WebSocket webSocket;
    private List<String> messages = new ArrayList<String>();

    public WSClient(String url)  {
        try {
            webSocket = new WebSocketFactory()
                    .createSocket(url)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            messages.add(message);
                            System.out.println(message);
                        }
                    });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public WSClient send(SocketData request){
        try {
            if(!socket().isOpen()){
                socket().connect();
            }
            String message = new ObjectMapper().writeValueAsString(request);
            socket().sendText(message);
        } catch (JsonProcessingException | WebSocketException e) {
            throw new AssertionError(e);
        }
        return this;
    }

    private WebSocket socket(){
        return webSocket;
    }

    public List<String> messages(){
        return this.messages;
    }


}
