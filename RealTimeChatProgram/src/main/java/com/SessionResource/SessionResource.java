package com.SessionResource;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;


/* VO 객체인 SessionResourceVO 내 동일한 세션 객체를 두개의 컬렉션에 저장하는 이유는 하나는 키 값으로 닉네임 등을 가진 세션 객체르 ㄹ불러오기 함, 즉 컨트롤러 빈에서 사용하고
 * session id 로 저장하는 컬렉션은 아에 . */
@Component
public class SessionResource {
	
	/* 방 번호 별 세션 객체 저장 :
	 * 	1. 클라이언트 WebSocket 에서 데이터 전달 시 이를 모든 클라이언트에 재 전송
	 * 	2. 단순 채팅 방 페이지 접속 아닌 실제 WebSocket 세션 객체 생성 후에 방 인원 수 증가. : afterConnectionEstablished
	 * 	3. 해당 WebSocket 세션 객체 내 세션 종료 시 해당 방 번호 따른 방 인원 수 감소. : afterConnectionClosed
	 *  */
	public HashMap <String, ArrayList<WebSocketSession>> roomWebsocks = new HashMap <String, ArrayList<WebSocketSession>>();
	
	/* 'chatRepo.getChatRoom' 에서 사용되며 현재 채팅 방 페이지 요청 시 새로고침인지 아닌지 판별하기 위한
	 * 각 HttpServlet ID 별 헤더 'referer' 저장하는 HashMap 컬렉션 */
	public HashMap <String, String> refererList = new HashMap<String, String>();
}