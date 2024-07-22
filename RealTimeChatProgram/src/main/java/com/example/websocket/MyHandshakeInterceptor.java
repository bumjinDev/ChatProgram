package com.example.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class MyHandshakeInterceptor implements HandshakeInterceptor {
	
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
       System.out.println("beforeHandshake() 실행!");
     
       /* 방 번호를 httpRequest 객체로부터 받아서 session 객체 내 저장한다. 이후 해당 방 번호는 WebSocket 세션 객체 성립 이후
        * 방 번호 별 세션 객체 리스트를 생성하는 데 사용. 또한 해당 WebSocket 세션 객체가 종료 되었을 때 해당 방 번호를 기준으로 방 번호 별 세션 리스트를
        * 조회하며 해당 세션 객체가 속한 방 번호를 기준으로 방 리스트가 없을 경우 리스트 삭제한다. */
       String reqRoomNum = extractHttpSessionIdFromRequest(request);
       System.out.println("reqRoomNum : " + reqRoomNum);
       attributes.put("roomnumber", reqRoomNum);
       
       return true;
    }

    private String extractHttpSessionIdFromRequest(ServerHttpRequest request) {
       
    	System.out.println("extractHttpSessionIdFromRequest() 호출 (session 생성 전 http 내 roomnumber 확인) ");
    	ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        String roomnumber = (String) servletRequest.getServletRequest().getParameter("roomnumber");
        
        System.out.println("roomNumber : " + roomnumber);
        return roomnumber;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    	
    }
}
