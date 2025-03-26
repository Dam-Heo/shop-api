package com.example.allrabackendassignment.handler;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("클라이언트 연결됨: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("받은 메시지: " + message.getPayload());
        // 메시지 브로드캐스트
        session.sendMessage(new TextMessage("서버로부터 응답: " + message.getPayload()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /*@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("받은 메시지: " + message.getPayload());
        // 메시지 브로드캐스트
        session.sendMessage(new TextMessage("서버로부터 응답: " + message.getPayload()));
    }*/

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("클라이언트 연결 종료됨: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
