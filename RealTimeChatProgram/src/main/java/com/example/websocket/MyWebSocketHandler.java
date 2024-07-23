package com.example.websocket;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.SessionResource.SessionResource;
import com.realtimechat.dao.ChatRepo;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

/* 소켓 핸들러 : 스프링 프레임 워크는 'WebSocket' 클래스를 빈으로 구현할 때 '@EnableWebSocket' 을 사용해서 구현.. 하며
 * 	이때 여러 통신 메시지 종류에 따라 다양한 핸들러를 지원하나 나는 채팅 프로그램이므로 'TextWebSocketHandler'를 구현하여 텍스트를 처리하는 핸들러
 *  'handleTextMessage'을 사용. */

@EnableWebSocket
@Configuration
public class MyWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	SessionResource sessionResource;
	
	@Autowired
	ChatRepo chatRepo;
	
	/* 실시간으로 각 세션 맺은 사용자에 받은 데이터를 모든 사용자에게 동일하게 전송 한다!*/
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		/* 'roomWebsocks' 전체에서 현재 Websocket 세션 객체가 속한 방 번호만 추출해서 해당 방 번호로 조회하여 해당 방 번호에 해당하는 웹 소켓 리스트들을 추려서
		 * 해당 리스트에 해당하는 웹 소켓에 대해서만 닉네임과 채팅 내용을 JSON 내용을 파싱해서 결과로 전달. */
		String currentRoomNum = (String) session.getAttributes().get("roomnumber");
		ArrayList<WebSocketSession> lists = sessionResource.roomWebsocks.get(currentRoomNum);
		
		String currentUserName = (String) session.getAttributes().get("username");		// 사용자 이름을 JSON 데이터 내 포함
		
		JSONObject jObj = new JSONObject();
		jObj.put("user", currentUserName);
		jObj.put("chatValues", message.getPayload());	
		
		TextMessage sendMessage = new TextMessage(jObj.toString());
		
		for(WebSocketSession webSocket : lists)
			webSocket.sendMessage(sendMessage);
		
		String chatUser = session.getId();			// 대화 사용자를 로그 파일에 담기 위함
		String chatValue = message.getPayload();	// 대화 내용을 로그 파일에 담기 위함.
	}
	
	/* 클라이언트가 WebSocke 세션 생성 시의 seesion 객체 저장 및 요청한 url 인 js 탭 구분 번호 및 사용자 닉네임 저장.  */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {		
		
		System.out.println("afterConnectionEstablishd 메소드 실행 ");
		
		String curruentRoomNumber = (String) session.getAttributes().get("roomnumber");	// 세션 핸드 쉐이크 전, 즉 http socket 세션 과정 전 저장한 요청 방 번호.
		
		System.out.println("현재 생성된 session.getId() : " + session.getId());
		System.out.println("현재 생성 요청 받은 방 번호 : " + curruentRoomNumber);
		
		/* 방 생성 또는 입장하는 동작 즉, 세션 객체 생성 시 현재 방 번호로 세션 객체 리스트를 담고 있는 HashMap 요소가 없다면 새로 생성, 있다면 기존 키 값 따른 추가.*/
		if(sessionResource.roomWebsocks.get(curruentRoomNumber) == null)	/* 세션 객체 생성 당 시 만약 처음 생성이라면 무조건 방 생성 간주 로직. */	
			sessionResource.roomWebsocks.put(curruentRoomNumber, new ArrayList<WebSocketSession>());
		// 초기 생성이 아니라면 방 입장 으로 간주 후 로직 작성.
		sessionResource.roomWebsocks.get(curruentRoomNumber).add(session);
		System.out.println("sessionResource.roomWebsocks.get(" + curruentRoomNumber + ").size : " + sessionResource.roomWebsocks.get(curruentRoomNumber).size());
		
		chatRepo.pathCurrentPeople(Integer.parseInt(curruentRoomNumber),sessionResource.roomWebsocks.get(curruentRoomNumber).size(), false);
		System.out.println("세션 생성 시점에서의 현재 채팅 방 이용자 수 : " + sessionResource.roomWebsocks.get(curruentRoomNumber).size());
	}
	
	/* index.js 에서 맺은 세션은 페이지 새로고침 뿐만 아니라 페이지 랜더링 시에서 js 입장에서는 컨텍스트 환경이 변경되므로 페이지 이동 마다 브라우저가 웹 소켓을 종료한다. */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		System.out.println("afterConnectionClosed() 호출");
		
		String closeSessionId = session.getId();		// 현재 세션 종료 직후 Websocket 세션 객체의 id 를 가져온다. 이후 리스트에서 해당 id를 기준으로 HashMap 컬렉션 내 제거.
		System.out.println("반환된 closeSessionId : " + closeSessionId);
		
		/* 현재 방 번호를 키 값으로 참조 변수 리스트가 저장된 ArrayList 컬렉션의 WebsocketSession 리스트를 가져온 후 해당 세션 id 요소를
		 * 해당 방 번호 별 세션 리스트에서 제거한다. */
		String closeSessionRoomNum = (String) session.getAttributes().get("roomnumber");
		ArrayList<WebSocketSession> lists = sessionResource.roomWebsocks.get(closeSessionRoomNum);
		
		/* 컬렉션이 ArrayList 이므로 순회하며 삭제. */
		for(int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getId() == closeSessionId) {
				System.out.println("삭제되는 Session 객체 ID : " + closeSessionId);
				sessionResource.roomWebsocks.get(closeSessionRoomNum).remove(i);
			}
		}
		/* 삭제하는 로직 !
		 * 1. 페이지 나가기 리다에렉션 요청 : 페이지 랜더링 데이터를 받은 후에 close -> establised 메소드 호출 될 때 어차피 exitChatpge() 호출 해서 내부적으로
		 * 							db 내 -1을 먼저 한다.(왠나면 페이지 랜더링 해서 내 브라우저에 보인 후에 close 를 하기 때문에 인원 수 실시간 갱신이 필요하므로!)
		 * 							그리고 cloase 웹소켓 내부적으로 close 발생해서 db 테이블 갱신(두번 하는 이유는 해당 close 메소드는 모든 소켓 종료 시 "공통적으로" 호출되기 때문에 어차피 해야됨)
		 * 2. 단순 브라우저 또는 탭 x 버튼 : closed 메소드 호출 후 close 내부적으로 갱신된 리스트를 db 내 직접 적용한다.
		 * 3. 새로고침 : 이게 제일 문제다! closed 메소드 호출 입장에서 단순 x 눌러서 나가기 요청인지 아니면 리다이렉션으로 나가기 요청인지, 새로고침
		 * 		인지 분간이 안되기 때문에 만약에 위 리다이렉션 또는 x 표시 로직 그대로 db 내 이용자수가 0이라고 지워버리면 새로고침 시 에러 뜬다..
		 * 		고로 브라우저에서 리프레시(새로고침) 할 때 를 구분하여 진짜 리프레쉬 
		 * */
		System.out.println("sessionResource.roomWebsocks.get(closeSessionRoomNum).size() : " + sessionResource.roomWebsocks.get(closeSessionRoomNum).size());
		
		HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
		
		boolean bool = false;
		if (httpSession != null) {
	        Boolean refresh = (Boolean) httpSession.getAttribute("refresh");
	        System.out.println("Boolean refresh : " + refresh);
	        
	        if (refresh != null && refresh) {  /* 현재 페이지가 새로고침일 때 만약에 현재 방 내 인원수가 혼자일 때 현재 방 인원수가 0으로 바뀌엇다고 그 즉시 방 삭제 안되게 하기 위함. */
	            bool = true;
	            chatRepo.pathCurrentPeople(Integer.parseInt(closeSessionRoomNum), sessionResource.roomWebsocks.get(closeSessionRoomNum).size(), bool);
	            System.out.println("새로 고침 됐어요 !");
	        }
	        httpSession.setAttribute("refresh", false);
	    }
		bool = false;	// 다시 원상복구
		
		
		if(sessionResource.roomWebsocks.get(closeSessionRoomNum).size() == 0)	// 해당 방 번호 내 세션 리스트가 없을 경우 아에 방 번호 리스트 HashMap 삭제.		
			sessionResource.roomWebsocks.remove(closeSessionRoomNum);	// 현재 방 번호 키 에 대한 세션 리스트가 없으니 해당 키 셋 삭제, 채팅 했을 때 동시에 전달 받는 리스트 삭제.
	}
}
