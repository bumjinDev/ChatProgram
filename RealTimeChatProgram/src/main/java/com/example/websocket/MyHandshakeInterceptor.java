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
        * 방 번호 별 세션 객체 리스트를 생성하는 데 사용. 또한 해당 WebSocket 세션 객체가 종료 되었을 때 */
       String reqRoomNum = extractHttpSessionIdFromRequest(request);
       System.out.println("reqRoomNum : " + reqRoomNum);
       attributes.put("roomnumber", reqRoomNum);
       
//       String getSessionId = extractHttpSessionIdFromRequest(request);
//       attributes.put("httpReuqestId", getSessionId);
//       
//       System.out.println("attributes.get('httpReuqestId')" + attributes.get("httpReuqestId"));
       
       return true;
    }

    private String extractHttpSessionIdFromRequest(ServerHttpRequest request) {
       
    	System.out.println("extractHttpSessionIdFromRequest() 호출");
    	ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        String roomnumber = (String) servletRequest.getServletRequest().getParameter("roomnumber");
        
        System.out.println("roomNumber : " + roomnumber);
        return roomnumber;
//        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//        return servletRequest.getServletRequest().getSession().getId();
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    	
    }
}
