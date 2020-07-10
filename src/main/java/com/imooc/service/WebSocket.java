package com.imooc.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        System.out.println("[websocket有新消息]新建连接 总数为：" + webSockets.size());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        System.out.println("[websocket有新消息]连接断开 总数为："+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("[websocket有新消息]有客户端发来的消息 "+ message);
    }

    public void sendMessage(String message){
        for(WebSocket webSocket:webSockets){
            System.out.println("[websocket消息]广播消息"+message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
