package com.realtimechat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.realtimechat.ExitRoom.service.ExitRoomService;
import com.realtimechat.SessionResource.SessionResource;
import com.realtimechat.chatroom.model.ChatRoomPeopleVO;
import com.realtimechat.chatroom.service.LoadChatRoomService;
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
	LoadChatRoomService loadChatRoomService;
	
	@Autowired
	WatingRoomService watingRoomService;
	
	@Autowired
	ExitRoomService exitRoomService;		
	
	@Autowired
	SessionResource sessionResource;	// singleton 활용하여 닉네임 설정. 
	
	
	/* MainPage 요청 처리 : 현재 전체 채팅 방 개수 및 전체 채팅 인원 수를 반환. */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String loadMainPage(Model model, HttpServletRequest httpRequest) {
		
		/* 전체 채팅 방 개수 및  현재 전체 채팅방 내 인원 수 반환 */
		MainPageVO mainPageVO = mainPageSerivce.loadMainInfo();
		
		model.addAttribute("totalRoom", mainPageVO.getTotalRoom());	// 전체 채팅방 개수
		model.addAttribute("totalUser", mainPageVO.getUser());		// 전체 채팅 방 내 전체 인원
		
		/* 채팅방 요청, 즉 '/loadChatPage/{roomNumber}'의 getChatRoom 호출 시 실제 리다이렉트 요청인지 아닌 지 판별. */
		sessionResource.refererList.put(httpRequest.getRequestedSessionId(), null);	
		
		return "index";
	}
	
	/* 채팅 페이지인 'chatpage.jsp' 내 버튼 '채팅 방 나가기' 구현, 즉 나가기 버튼을 누를 시 DBMS 내에서 현재 채팅 방 인원 수 컬럼 'CURRENTPEOPLE' 수 1 감소.*/
	@RequestMapping(value="/exitChatPage/{roomNumber}", method = RequestMethod.GET)
	public String exitChatPage(@PathVariable("roomNumber")int roomNumber, HttpServletRequest httpRequest) {
		
		System.out.println("메소드 'exitChatPage()' 호출! ");
		
		exitRoomService.exitChatRoom(roomNumber, httpRequest.getSession());
		
		System.out.println("메소드 'loadWaitPage()' 리다이렉트 실행! \n");
	
		return "redirect:/loadWaitPage";
	}
	
	/* loadWaitPage() : 채팅 대기방 목록 제공 페이지 "waitpage.jsp" 페이지 제공.
	 * 1. 사용자 닉네임을 WebSocket 에서 생성한 싱글톤 패턴으로부터 가져온다.
	 * 2. 생성된 채팅 방들의 정보를 테이블 'watingroomtbl'로부터 가져온다.(List<WatingRoomVO>로써 반환)
	 * 3. 닉네임 및 채팅 방 정보(방 번호, 방 제목, 참여 인원수)를 Model 객체에 담아서 디스패처 서블릿 통해 HttpRequest 객체로써 전달(Srping Framework MVC 동작 로직).
	*/
	
	/* 현재 메소드는 채팅 대기방 페이지를 로드하는 페이지이며 초기 페이지에서 사용자가 닉네임을 입력하고 들어올 때에 WebScoekt 세션을 index.js 에서 생성 후 들어오는 페이지. */
	@RequestMapping(value="/loadWaitPage", method = RequestMethod.GET)
	public String loadWaitPage(HttpServletRequest httpRequest, Model model) {
		
		System.out.println("메소드 'loadWaitPage()' 호출! ");
		
		HttpSession httpSession = httpRequest.getSession();	/* Http 세션 객체 생성 : 모든 컨트롤러 빈즈에서  */
		
		if(httpRequest.getParameter("username") != null)	/* 즉 해당 메소드 최초 요청일 시에만 */
			httpSession.setAttribute("username", httpRequest.getParameter("username"));
		
		String username = (String) httpSession.getAttribute("username");
		
		List<WatingRoomVO> loadWaitRooms = watingRoomService.loadWatingRoom();
	    
		/* 닉네임 및 방 목록 정보들을 Model 객체 내 저장 및 반환. */
		model.addAttribute("nickName", username);	// 닉네임 저장.
		model.addAttribute("loadWaitRooms", loadWaitRooms);
		
		sessionResource.refererList.put(httpRequest.getSession().getId(), httpRequest.getHeader("Referer"));
		
		return "waitpage";
	}
	
	/* "chatpage.jsp" 요청 처리 컨트롤러 메소드.
	 * 	1. "waitpage.jsp" 에서 선택적으로 방을 선택 요청에 대한 응답으로 반환.
	 * 	2. "createpage.jsp" 에서 방을 생성함으로써 컨트롤러 메소드에서 리다이렉트 함으로써 페이지 생성 후의 요청을 응답으로 반환.
	 * */
	@RequestMapping(value="/loadChatPage/{roomNumber}", method = RequestMethod.GET)
	public String loadChatPage(HttpServletRequest httpRequest, @PathVariable("roomNumber") int roomNumber, Model model) {
		
		System.out.println("메소드 'Controller.loadChatPage' 실행! ");
		System.out.println("요청된 방 번호 : " + roomNumber);
		
		ChatRoomPeopleVO chatRoomPeopleVO = loadChatRoomService.readChatPeople(roomNumber, httpRequest);
		
		String returnPage = null;
		String username = (String) httpRequest.getSession().getAttribute("username");
		
		System.out.println("username : " + username);
		System.out.println("chatRoomPeopleVO.getRoomPeople() : " + chatRoomPeopleVO.getRoomPeople());
		
		/* 현재 방 내 인원수와 최대 인원수를 비교해서 입장 가능 여부 확인. */
		if(chatRoomPeopleVO.getIsAllowed()) {	// 여유가 1명 이상 있다면 페이지 랜더링.
			
			/* 요청 또는 생성 된 방 번호를 기준으로 다음과 같은 정보를 'chatpage.jsp' 페이지 내 랜더링 하여 제공한다.
			 * 1. 닉네임
			 * 2. 현재 방 참여 인원수 : 방 번호 'roomNumber' 기준 테이블 'WATINGROOM.TBL' 조회 결과 사용
			 * 3. 방 번호 : 변수 'roomNumber' 적용.
			*/
			System.out.println("");
			model.addAttribute("nickName", username);			// 닉네임 설정
			model.addAttribute("currentPeople", chatRoomPeopleVO.getRoomPeople());// 현재 방 참여 인원수, 방 생성 당시인 페이지 랜더링 과정에서는 당연히 0명, 이유는 페이지 랜더링 이후 웹 소켓 맺기 때문.
			System.out.println("현재 currnetPoepole!! : " + chatRoomPeopleVO.getRoomPeople());
			model.addAttribute("roomNumber", roomNumber);	// 방 번호 설정 : 대기방 페이지 'watingpage.jsp' 내 입장 버튼 또는 방 생성 페이지 'createpage.jsp' 통한 생성 방 번호.
			
			returnPage = "chatpage";
			
		} else if(chatRoomPeopleVO.getRoomPeople() == 11){	// 방을 초과했다면 그에 맞는 경고창 화면 띄우기.
			 returnPage = "redirect:/errorAlertMax";
		} else if (chatRoomPeopleVO.getRoomPeople() == 0){											// 아에 없는 방 이므로 그에 맞는 경고창 화면 띄우기.
			returnPage = "redirect:/errorAlertNone";
		}
		
		return returnPage;
	}
	
	/* 단순 "방 생성 페이지 요청"을 처리 메소드 */
	@RequestMapping(value="/loadCreatePage", method = RequestMethod.GET)
	public String loadCreatePage() {
		System.out.println("reqCreatePage() 실행.");
		return "createpage";
	}
	
	/* "createpage.jsp"의 '채팅 방 생성 요청' 따른 페이지 제공 메소드. */
	@RequestMapping(value="/createChatRoom", method = RequestMethod.POST)
	public String createChatRoom(@RequestParam("roomName") String roomName, @RequestParam("roomMax") String roomMax, HttpServletRequest httpRequest) {
		
		System.out.println("createChatRoom() 실행.");
		
		int roomNumber = createRoomService.createChatRoom(roomName, Integer.parseInt(roomMax));
		
		System.out.println("생성 결과로 반환된 방 번호 : " + roomNumber);
		System.out.println("생성 결과로 반환된 방 이름 : " + roomName);
		/* 방 생성 서비스 빈 'createRoomService' 결과로 생성된 방 번호를 가지고 메소드 'loadChatPage' 로 요청을 분기하여 해당 생성된 방을 "chatpage.jsp" 페이지로 랜더링 후 사용자 브라우저에 응답으로 전달.  */
		return "redirect:/loadChatPage/"+roomNumber; // Repository 'ChatRepo' 방 생성(방 번호는 랜덤 생성하여 반환).
	}
	
	
	@RequestMapping(value="/errorAlertMax", method = RequestMethod.GET)
	public String errorAlertMax() {
		System.out.println("메소드 errorAlert() 실행");
		
		return "errorAlertMax";
	}
	
	@RequestMapping(value="/errorAlertNone", method = RequestMethod.GET)
	public String errorAlertNone() {
		System.out.println("메소드 errorAlert() 실행");
		
		return "errorAlertNone";
	}
}