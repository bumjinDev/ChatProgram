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
	@RequestMapping(value="/waitpage", method = RequestMethod.GET)
	public String waitroomPage() {
		
		return "waitroom";
	}
	
	/* "chatroom.jsp" 요청 처리 컨트롤러 메소드.
	 * 	1. "waitroom.jsp" 에서 선택적으로 방을 선택 요청에 대한 응답으로 반환.
	 * 	2. "createroom.jsp" 에서 방을 생성함으로써 컨트롤러 메소드에서 리다이렉트 함으로써 페이지 생성 후의 요청을 응답으로 반환.
	 * */
	@RequestMapping(value="/chatpage", method = RequestMethod.GET)
	public String chatroomPage() {
		
		return "chatroom";
	}
	
	/* 방 생성 페이지 요청을 처리 메소드 */
	@RequestMapping(value="/createpage", method = RequestMethod.GET)
	public String reqCreatePage() {
		System.out.println("reqCreatePage() 실행.");
		return "createroom";
	}
	
	/* "createroom.jsp"의 단순 '채팅 방 생성 요청' 따른 페이지 제공 메소드. */
	@RequestMapping(value="/roomcreate", method = RequestMethod.POST)
	public String roomCreatePage() {
		System.out.println("roomCreatePage() 실행.");
		
		/* 방 생성 로직 */
		
		
		
		/* 방 생성 후 페이지 제공하는 컨트롤러 메소드 'reqCreatePage()' 로 분기 */
		return "redirect:./chatpage";
	}
	
}
