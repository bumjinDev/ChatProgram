package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.realtimechat.dao.ChatRepo;
import com.singleton.SessionResourceVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
/* 소켓 핸들러 : 스프링 프레임 워크는 'WebSocket' 클래스를 빈으로 구현할 때 '@EnableWebSocket' 을 사용해서 구현.. 하며
 * 	이때 여러 통신 메시지 종류에 따라 다양한 핸들러를 지원하나 나는 채팅 프로그램이므로 'TextWebSocketHandler'를 구현하여 텍스트를 처리하는 핸들러
 *  'handleTextMessage'을 사용. */



@EnableWebSocket
@Configuration
public class MyWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	SessionResourceVO sessionResource;
	
	@Autowired
	ChatRepo chatRepo;
	
	/* 실시간으로 각 세션 맺은 사용자에 받은 데이터를 모든 사용자에게 동일하게 전송 한다!*/
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		/* 'roomWebsocks' 전체에서 현재 Websocket 세션 객체가 속한 방 번호만 추출해서 해당 방 번호로 조회 */
		String currentRoomNum = (String) session.getAttributes().get("roomnumber");
		
		ArrayList<WebSocketSession> lists = sessionResource.roomWebsocks.get(currentRoomNum);
		
		for(WebSocketSession obj : lists)
			obj.sendMessage(message);
		
//		/* WebSocket 세션을 맺고 있는 모든 객체를 순회하면서 동일하게 채팅 내용 및 각 사용자 정보를 json으로 전송. */
//		JSONObject jsonMessage = new JSONObject();
//		
//		System.out.println("디버깅 - message : " + message.getPayload());
//		
//		for(String keys : sessionResource.sessionList.keySet()) {
//			
//			jsonMessage.put("user", sessionResource.sessionList.get(session.getId()).getAttributes().get("username"));
//			jsonMessage.put("chatValues", message.getPayload());
//			
//			sessionResource.sessionList.get(keys).sendMessage(new TextMessage(jsonMessage.toString()));
//		}
		
		String chatUser = session.getId();			// 대화 사용자를 로그 파일에 담기 위함
		String chatValue = message.getPayload();	// 대화 내용을 로그 파일에 담기 위함.
	}
	
	/* 클라이언트가 WebSocke 세션 생성 시의 seesion 객체 저장 및 요청한 url 인 js 탭 구분 번호 및 사용자 닉네임 저장.  */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {		
		
		System.out.println("afterConnectionEstablishd 메소드 실행 ");
		
		String curruentRoomNumber = (String) session.getAttributes().get("roomnumber");
		
		/* 방 생성 또는 입장하는 동작 즉, 세션 객체 생성 시 현재 방 번호로 세션 객체 리스트를 담고 있는 HashMap 요소가 없다면 새로 생성, 있다면 기존 키 값 따른 추가.*/
		if(sessionResource.roomWebsocks.get(curruentRoomNumber) == null) {	/* 세션 객체 생성 당 시 만약 처음 생성이라면 무조건 방 생성 간주 로직. */
			sessionResource.roomWebsocks.put(curruentRoomNumber, new ArrayList<WebSocketSession>());
			System.out.println("방 새로 생성!");
		}
		else 	// 초기 생성이 아니라면 방 입장 으로 간주 후 로직 작성.
			sessionResource.roomWebsocks.get(curruentRoomNumber).add(session);
		
		chatRepo.updateCurrentUser(Integer.parseInt(curruentRoomNumber), sessionResource.roomWebsocks.get(curruentRoomNumber).size());
		
//		String query = session.getUri().getQuery();
//	    String[] pairs = query.split("&");
//	    Map<String, String> queryPairs = new HashMap<String, String>();
//	    
//	    /* 세션 GET 요청 시 파라메터 'username' 저장. */
//	    for (String pair : pairs) {
//	        int idx = pair.indexOf("=");
//	        queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
//	    }
//	    /* HTTP 리퀘스트 세션 id 기준으로 WebSocket 참조 변수 저장 : 컨트롤러 빈에서 페이지 랜더링 요청 및 탭 채로딩(ctrl + f5) 요청 시에 별도의 추가적인 닉네임 파라메터 필요 없게끔 하기 위함. */
//	    
//	    /* WebSocket 연결 요청 시 웹 브라우저 js(탭 단위) 컨텍스트 환경 상 브라우저 자체 내 탭 재 로딩 등등 요청 시 한 브라우저 내에서 이전 컨텍스트 내 동작하고 있던 websocket 객체를
//		 * js 내에서 따로 보관이 불가능하기 때문에(같은 맥락으로 브라우저 로컬 스토리지 또한 문자열 데이터 밖에 저장 못함) js 는 단순 페이지 요청 랜더링이 아닌 이상 무조건 socket 을 재 생성 하는 과정을
//		 * 서버와 하기 때문에 서버는 이에 따라 이전 소켓을 새 소켓 위에 덮어 씌우기 해야 됨. */
//	    if(sessionResource.httpSessionList.get(httpRequestSessionId)!= null) {	// 만약 해당 브라우저 새션 객체 ID 에 해당하는 WebSocket 객체가 있다면 추가로 저장하지 않음.
//	    } else																	// 만약 해당 브라우저 세션 객체 Id 내 해당하는 WebSocket 객체가 없다면 새롭게 저장.	
//	    	sessionResource.httpSessionList.put(httpRequestSessionId, session);	
//	    
//	    sessionResource.sessionList.put(session.getId(), session);			/* 세션 객체 저장 : 채팅 방 및 채팅 내역 띄우는 것과 채팅 방 별 인원수 파악에 사용하며 현재 웹 소켓 맺은 대상들 리스트 별로 세션 아이로 저장 및 관리 하기 위함. */
	}
	
	/* index.js 에서 맺은 세션은 페이지 새로고침 뿐만 아니라 페이지 랜더링 시에서 js 입장에서는 컨텍스트 환경이 변경되므로 페이지 이동 마다 브라우저가 웹 소켓을 종료하낟.
	 * 그러므로 브라우저 전체에서 웹 소켓을 유지하기 위해서는 해당 'afterConnectionClosed' 메소드 내 별다른 로직 구현 안하고 별도의 각 세션 별 자체 타임아웃 적용. */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		System.out.println("afterConnectionClosed() 호출");
		
		String closeSessionId = session.getId();		// 현재 세션이 종료된 Websocket 세션 객체의 id 를 가져온다. 이후 리스트에서 해당 id를 기준으로 HashMap 컬렉션 내 제거.
		System.out.println("반환된 closeSessionId : " + closeSessionId);
		
		/* 현재 방 번호를 키 값으로 참조 변수 리스트가 저장된 ArrayList 컬렉션의 WebsocketSession 리스트를 가져온다. */
		String closeSessionRoomNum = (String) session.getAttributes().get("roomnumber");
		System.out.println("반환된 closeSessionRoomNum : " + closeSessionRoomNum);
		ArrayList<WebSocketSession> lists = sessionResource.roomWebsocks.get(closeSessionRoomNum);
		
		/* 컬렉션이 ArrayList 이므로 순회하며 삭제. */
		for(int i = 0; i < lists.size(); i++) {
			if(lists.get(i).getId() == closeSessionId) {
				System.out.println("삭제되는 Session 객체 ID : " + closeSessionId);
				sessionResource.roomWebsocks.get(closeSessionRoomNum).remove(i);
			}
		}
		/* 해당 WebSocketSession 객체를 HashMap 컬렉션에서 삭제 후 'sessionResource.roomWebsocks.get(closeSessionRoomNum)' 크기가 0 이면
		 * 아에 해당 키 값에 해당하는 현재 세션 객체가 없다면 HashMap 요소 삭제 후 테이블 까지 삭제하기 위해 방 번호를 가지고 레포지토리 메소드 'exitChatPage' 호출,
		 * 1 이상이라면 'sessionResource.roomWebsocks.get(closeSessionRoomNum)' 의 크기를 가지고 'updateCurrentUser' 호출 */
		
		if(sessionResource.roomWebsocks.get(closeSessionRoomNum).size() == 0) {
			
			sessionResource.roomWebsocks.remove(closeSessionRoomNum);
			chatRepo.exitChatPage(Integer.parseInt(closeSessionRoomNum));
		} else {
			System.out.println("현재 사용자 한명 나간 방 - 번호 : " + closeSessionRoomNum + ", 현재 방 인원 수 : " + Integer.parseInt(closeSessionRoomNum) );
			chatRepo.updateCurrentUser(Integer.parseInt(closeSessionRoomNum), sessionResource.roomWebsocks.get(closeSessionRoomNum).size());
		}
		
//		System.out.println("afterConnectionClosed() 실행");
//		
//		sessionResource.sessionList.remove(session.getId());	// 세션 리스트 삭제.    
//	    sessionResource.httpSessionList.remove((String) session.getAttributes().get("httpReuqestId"));	// 메모리 관리를 위한 Http 세션 종료(브라우저 종료) 에 대한 hTTP 세션 리스트 삭제.
	}
}
