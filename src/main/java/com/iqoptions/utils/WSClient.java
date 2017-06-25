package com.iqoptions.utils;


import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import io.restassured.http.Cookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiF on 24.06.2017.
 */
public class WSClient  {

    private final WebSocket webSocket;
    private List<String> messages = new ArrayList<String>();

    public WSClient(String url, Cookie cookie)  {
        try {
            webSocket = new WebSocketFactory()
                    .createSocket(url)
                    .addHeader("Cookie", cookie.toString())
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

    public WebSocket socket(){
        return webSocket;
    }

    public List<String> messages(){
        return this.messages;
    }


}
