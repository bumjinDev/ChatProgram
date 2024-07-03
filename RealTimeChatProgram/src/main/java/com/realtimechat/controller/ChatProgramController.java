package com.realtimechat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.realtimechat.chatroom.service.ChatRoomPeopleService;
import com.realtimechat.createroom.service.CreateRoomService;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.main.service.MainPageService;
import com.realtimechat.waitroom.model.WatingRoomVO;
import com.realtimechat.waitroom.service.WatingRoomService;

@Controller
public class ChatProgramController {
	
	@Autowired
	MainPageService mainPageSerivce;
	
	@Autowired
	CreateRoomService createRoomService;
	
	@Autowired
	ChatRoomPeopleService chatRoomPeopleService;
	
	@Autowired
	WatingRoomService watingRoomService;
	
	/* MainPage 요청 처리 : 현재 전체 채팅 방 개수 및 전체 채팅 인원 수를 반환. */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String loadMainPage(Model model) {
		
		/* 전체 채팅 방 개수 및  현재 전체 채팅방 내 인원 수 반환 */
		MainPageVO mainPageVO = mainPageSerivce.loadMainInfo();
		
		model.addAttribute("totalRoom", mainPageVO.getTotalRoom());	// 전체 채팅방 개수
		model.addAttribute("totalUser", mainPageVO.getUser());		// 전체 채팅 방 내 전체 인원
		
		return "index";
	}
	
	/* loadWaitPage() : 채팅 대기방 목록 제공 페이지 "waitpage.jsp" 페이지 제공.
	 * 1. 사용자 닉네임을 HttpRequest 세션 객체 내 속성으로부터 가져온다.
	 * 2. 생성된 채팅 방들의 정보를 테이블 'watingroomtbl'로부터 가져온다.(List<WatingRoomVO>로써 반환)
	 * 3. 닉네임 및 채팅 방 정보(방 번호, 방 제목, 참여 인원수)를 Model 객체에 담아서 디스패처 서블릿 통해 HttpRequest 객체로써 전달(Srping Framework MVC 동작 로직).
	*/
	
	@RequestMapping(value="/loadWaitPage", method = RequestMethod.GET)
	public String loadWaitPage(HttpServletRequest httpRequest, Model model) {
		
		System.out.println("메소드 'loadWaitPage()' 호출! ");
		System.out.println("전달 받은 닉네임 : " + httpRequest.getAttribute("nickName"));
		
		/* 1. 닉네임 가져와서 세션 객체 내 저장. */
		HttpSession session = httpRequest.getSession(false);
	    if (session == null) {
	    	session = httpRequest.getSession();
	        System.out.println("현재 사용자의 브라우저 내 쿠키 값을 근거로 해당 사용자(브라우저)에 대한 세션 객체 생성 내용이 없으므로 신규 생성 합니다.");
	    } else {
	        System.out.println("현재 세션이 존재하므로 추가로 생성하지 않습니다.");
	    }
		System.out.println("생성된 session 객체 ID : " + httpRequest.getSession());
		
		/* 생성된 채팅 방들의 정보들을 List<WatingRoomVO> 으로써 반환 받는다.*/
		List<WatingRoomVO> loadWaitRooms = watingRoomService.loadWatingRoom();
	    
		/* 닉네임 및 방 목록 정보들을 Model 객체 내 저장 및 반환. */
		model.addAttribute("nickName", session.getAttribute("nickName"));	// 닉네임 저장.
		model.addAttribute("loadWaitRooms", loadWaitRooms);
		
		return "waitpage";
	}
	
	/* "chatpage.jsp" 요청 처리 컨트롤러 메소드.
	 * 	1. "waitpage.jsp" 에서 선택적으로 방을 선택 요청에 대한 응답으로 반환.
	 * 	2. "createpage.jsp" 에서 방을 생성함으로써 컨트롤러 메소드에서 리다이렉트 함으로써 페이지 생성 후의 요청을 응답으로 반환.
	 * */
	@RequestMapping(value="/loadChatPage/{roomNumber}", method = RequestMethod.GET)
	public String loadChatPage(@PathVariable("roomNumber") int roomNumber, Model model) {
		
		System.out.println("메소드 'loadChatPage' 실행! ");
		/* 요청 또는 생성 된 방 번호를 기준으로 다음과 같은 정보를 'chatpage.jsp' 페이지 내 랜더링 하여 제공한다.
		 * 1. 닉네임 : 세션 객체 내 저장된 속성 값 불러오기 (현재 'index.jsp' 페이지 미 개발이니 임시 데이터 "abcd" 적용)
		 * 2. 현재 방 참여 인원수 : 방 번호 'roomNumber' 기준 테이블 'WATINGROOM.TBL' 조회 결과 사용
		 * 3. 방 번호 : 변수 'roomNumber' 적용.
		*/

		int roomPeople = chatRoomPeopleService.readChatPeople(roomNumber);
		
		model.addAttribute("nickName", "abcd");			// 닉네임 설정
		model.addAttribute("currentPeople", roomPeople);// 현재 방 참여 인원수
		model.addAttribute("roomNumber", roomNumber);	// 방 번호 설정 : 대기방 페이지 'watingpage.jsp' 내 입장 버튼 또는 방 생성 페이지 'createpage.jsp' 통한 생성 방 번호.
		
		return "chatpage";
	}
	
	/* 단순 "방 생성 페이지 요청"을 처리 메소드 */
	@RequestMapping(value="/loadCreatePage", method = RequestMethod.GET)
	public String loadCreatePage() {
		System.out.println("reqCreatePage() 실행.");
		return "createpage";
	}
	
	/* "createpage.jsp"의 '채팅 방 생성 요청' 따른 페이지 제공 메소드. */
	@RequestMapping(value="/createChatRoom", method = RequestMethod.POST)
	public String createChatRoom(@RequestParam("roomName") String roomName, @RequestParam("roomMax") String roomMax) {
		System.out.println("roomCreatePage() 실행.");
		
		System.out.println("title : " + roomName + ", people : " + roomMax);
		int roomNumber = createRoomService.createChatRoom(roomName, Integer.parseInt(roomMax));
		
		System.out.println("생성 결과로 반환된 roomNumber : " + roomNumber);
		/* 방 생성 서비스 빈 'createRoomService' 결과로 생성된 방 번호를 가지고 메소드 'loadChatPage' 로 요청을 분기하여 해당 생성된 방을 "chatpage.jsp" 페이지로 랜더링 후 사용자 브라우저에 응답으로 전달.  */
		return "redirect:/loadChatPage/"+roomNumber; // Repository 'ChatRepo' 방 생성(방 번호는 랜덤 생성하여 반환).
	}
}