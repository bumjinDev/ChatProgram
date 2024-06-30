package com.realtimechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatProgramController {
	
	/* MainPage 요청 처리 : 현재 전체 채팅 방 개수 및 전체 채팅 인원 수를 반환. */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String indexPage() {
		
		return "index";
	}
	
	/* WatingPage 요청 처리 : MainPage 내 닉네임 입력 따른 WatingRoom 처리
	 * 사용자에게 WatingPage를 응답으로 전달 시 닉네임 정보를 세션 객체 생성해서 저장
	 * */
	@RequestMapping(value="/waitroom", method = RequestMethod.GET)
	public String waitroomPage() {
		
		return "waitroom";
	}
	
	/* "waitroom.jsp" 에서 선택 한 방으로 입장 위한 페이지 처리 컨트롤러 메소드 */
	@RequestMapping(value="/chatroom", method = RequestMethod.GET)
	public String chatroomPage() {
		
		return "chatroom";
	}
		
}
