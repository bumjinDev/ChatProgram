package com.realtimechat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.realtimechat.SessionResource.SessionResource;
import com.realtimechat.chatroom.model.ChatRoomPeopleVO;
import com.realtimechat.createroom.model.RoomLogVO;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.waitroom.model.WatingRoomVO;

@Repository
public class ChatRepo implements IChatRepo{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	SessionResource sessionResource;
	
	//	/* 웹 어플리케이션 재 실행 시 현재 채팅 방 목록은 초기화로 진행.(log 테이블인 'RooMLogtbl' 은 상관 없음.) */
	@PostConstruct
	void init() {
		
		String sqlLog = "delete from roomlogtbl";
		String sql = "delete from waitingroomtbl";
		jdbcTemplate.update(sqlLog);
		jdbcTemplate.update(sql);
	}
	
	/* roomNumbers : 유저들이 방 생성 시 마다 방 번호 리스트를 가지고 있어 중복된 방 번호 생성 방지 및 모든 요청 시 마다 테이블 조회하는 리소스 및 시간 절감 목적. */
	static List<Integer> roomNumbers = new ArrayList<Integer>();
	
	/* getMain() : MainPageService.loadMainInofo() 에서 전체 채팅 방 개수 및 전체 채팅 방 내 사용자 수 측정해서 반환. */
	@Override
	public MainPageVO getMain() {
		
		String roomTotalsql = "SELECT COUNT(*) FROM waitingroomtbl";
		
		/* 전체 채팅 방 개수 반환 */
		MainPageVO mainPageVO = new MainPageVO();
		mainPageVO.setTotalRoom(jdbcTemplate.queryForObject(roomTotalsql, Integer.class));
		
		/* 전체 채팅 방 내 전체 사용자 수 반환, 만약 현재 채팅 방이 없으면 NullPointException 방생하므로 0 처리 */
		String userTotalsql = "SELECT SUM(CURRENTPEOPLE) FROM waitingroomtbl";
		try {
			mainPageVO.setTotalUser(jdbcTemplate.queryForObject(userTotalsql, Integer.class));
		} catch(Exception NullPointerException) {
			mainPageVO.setTotalUser(0);
		}
		
		return mainPageVO;
	}

	/* getWatingRoom() : 메인 페이지 'index.jsp' 에서 닉네임 입력 후 채팅 서비스 시작함으로써 채팅 대기방 페이지 내 포함될 채팅방 목록을 제공 목적 */
	@Override
	public List<WatingRoomVO> getWatingRoom() {
		System.out.println("getWatingRoom() 호출");
		
		String query = "select * from waitingroomtbl";
		return jdbcTemplate.query(query, new watingRoomRowMapper());
	}

	/* 세션 생성 또는 변경 시 개수 적용. */
	@Override
	public void pathCurrentPeople(int roomNumber, int people, boolean bool) {
		
		System.out.println("pathCurrentPeople() 실행");
		System.out.println("방 번호 : " + roomNumber + ", 방 번호 내 수정 요청 인원 수 : " + people);
		
		/* WebSocket 세션 생성 또는 해제 따른 업데이트 적용 */
		String updateSql = "update waitingroomtbl set CURRENTPEOPLE = ? where romnum = ?";
		jdbcTemplate.update(updateSql, new Object[] {people, roomNumber});
		
		/* 조회하여 해당 채팅방 테이블 내 현재 참여 인원수가 0이라면 해당 채팅 방 목록 삭제. */
		String selectSql = "SELECT * FROM waitingroomtbl where romnum = ?";
		WatingRoomVO watingRoomVO = jdbcTemplate.queryForObject(selectSql, new Object[] {roomNumber}, new watingRoomRowMapper());
		
		System.out.println("update 직후 해당 방의 현재 인원 수 : " + watingRoomVO.getCurrentPeople());
		
		/* 나가기 요청이나 혹은 단순 x 표시 등을 눌러서 페이지를 나갈 시에 방 인원수가 없다면 방을 페지 하는게 맞으나 새로 고침이면 해당 방 튜플 하면 안됨!. */
		if (watingRoomVO.getCurrentPeople() == 0 && !bool) {
			
			System.out.println("현재 세션 삭제 요청 시 새로고침이 아니므로(bool : " + bool+ ")해당 테이블 삭제 쿼리 시작");
		    
			String deleteSql = "delete from waitingroomtbl where romnum = ?";
		    int res = jdbcTemplate.update(deleteSql, new Object[] {roomNumber});
		    System.out.println("영향 받은 쿼리 개수 : " + res);
		}
	}

	/* createRoom : 채팅 방 생성 요청 메소드이다.
	 * Webscoket 새선 생성 타이밍과의 동기화 문제 때문에 'afterConnectionEstablished' 새선 생성 직후 메소드 'increaseCurrentPeople' 호출하여 현재 접속자 수
	 * 증가하므로 현재 메소드에서는 작업하지 않음. */
	@Override
	public int createRoom(String roomName, int roomMax) {
		
		/* 방 생성 시 방 번호를 랜덤으로 지정하며 이 때 이미 생성된 방 번호와 중복 생성 되지 않게 한다. */
		int roomNumber = generateUniqueRandomNumber(roomNumbers, 1, 999);
		
		/* 해당 번호로 방 생성. */
		String insertQuery = "insert into waitingroomtbl values(?, ?, ?, ?)";
		jdbcTemplate.update(insertQuery, new Object[] {roomNumber, roomName, 0, roomMax});	// 생성 직후 방의 인원수를 0으로 하는 이유는 생성 하더라도 입장하는 로직을 동일하게 추가 구현 한다.
		
		roomNumbers.add(roomNumber);	// 생성한 채팅 방 번호를 static 저장.
		
		return roomNumber;		/* 생성한 방 번호를 반환하고 이를 컨트롤러에서 받아서 생성한 방을 토대로 채팅 방 페이지인 'chatpage.jsp'를 반환하여 즉시 채팅 시작. */
	}
	
	/* 컨트롤러 메소드 'loadChatPage' 에서 요청 따라 해당 채팅 방에 접속 요청 시 현 방 참여 인원수 + 1 과 전체 참여 인원수를 고려하여 적절한 요청이면 허용
	 * (방 테이블 내 방 참여 인원 수 자체를 증가시키는 로직은 WebSocket 핸들러에서 실행하므로 여기서는 별도의 로직 적용하지 않으며 방 인원 증가 표시는 jsp 내 자체적으로 + 1 적용 후 소켓 세션 생성 및 개수 증가)  */
	@Override
	public ChatRoomPeopleVO getChatRoom(int roomNumber, HttpServletRequest httpRequest) {
		System.out.println("ChatRepo.getChatRoom() 호출!");
		
		ChatRoomPeopleVO chatRoomPeopleVO = new ChatRoomPeopleVO();
		
		/* 요청된 테이블 번호를 기준으로 해당 테이블의 현재 참여 인원 수 및 최대 인원수를 파악. */
		String selectRoom = "select CURRENTPEOPLE, MAXPEOPLE from waitingroomtbl where ROMNUM = ?";
		
		List<Map<String, Object>> users;
		users = jdbcTemplate.queryForList(selectRoom, new Object[] {roomNumber});
		
		String httpRequestId = httpRequest.getRequestedSessionId();
		
		/* 우선 방이 있나 없나 부터 확인, 즉 대기방 페이지가 갱신이 안되서 이미 사라진 방 번호를 가지고 요청 했을 때 디비.(OutBoundException 예방) */
		if(users.size() == 0) {
			
			System.out.println("user.size() == 0 실행!");
			
			chatRoomPeopleVO.setRoomPeople(0);		// 참여 인원수 변동 없음. (VO 객체 채우기 위한 가비지 값)
			chatRoomPeopleVO.setIsAllowed(false);	// 결과 값 거짓으로 변경.
		
		/* 새로 고침 요청 이라면 별다른 계산(현재 방 인원수 + 1)을 안하고 그냥 현재 방 페이지 인원 수 그대로 돌려줌  */
		} else if(httpRequest.getHeader("Referer").equals(null) || httpRequest.getHeader("Referer").equals(sessionResource.refererList.get(httpRequestId))) {
			System.out.println("새로고침 페이지 반환 과정 실행!");
			
			chatRoomPeopleVO.setRoomPeople(Integer.parseInt(users.get(0).get("CURRENTPEOPLE").toString()));
			chatRoomPeopleVO.setIsAllowed(true);
			
			sessionResource.refererList.put(httpRequestId, httpRequest.getHeader("Referer"));
			
		/* 새로 고침 아닌 새로 방 입장 시 확인하는 if 문, 인원수가 적절하면 허용. */
		} else if(Integer.parseInt(users.get(0).get("CURRENTPEOPLE").toString()) + 1 <= Integer.parseInt(users.get(0).get("MAXPEOPLE").toString())){
			System.out.println("요청된 chat 페이지 반환 과정 실행\n");
			
			/* jsp 페이지 랜더링 시에 만약에 새로 고침이 아니라면 현재 db 내 인원수에 +1 을 하여 jsp 랜더링 하여 브라우저에서 랜더링 받은 직후의 값과 소켓 생성 시 값을 일치시키고
			 * 만약에 단순 페이지 새로고침이면 '+1' 을 하지 않음으로써 현재 인원수 그대로 jsp 페이지 내 랜더링 하게 끔 함. */
			chatRoomPeopleVO.setRoomPeople(Integer.parseInt(users.get(0).get("CURRENTPEOPLE").toString())+1);
			chatRoomPeopleVO.setIsAllowed(true);	// 추가적으로 해당 채팅 페이지에 접속 가능.
			
			sessionResource.refererList.put(httpRequestId, httpRequest.getHeader("Referer"));		/* 현재 브라우저가 페이지 방문 완료 했으니 헤더 Referer 갱신! */
			
		} else if(Integer.parseInt(users.get(0).get("CURRENTPEOPLE").toString())+1 >= Integer.parseInt(users.get(0).get("MAXPEOPLE").toString())){	/* 현재 요청한 인원수를 받으면 안된다면 false 적용. */
			System.out.println("페이지 요청 처리 중 인원수 초과하여 불가능 처리.");
			
			chatRoomPeopleVO.setRoomPeople(11);		// 참여 인원수 변동 없음. (VO 객체 채우기 위한 가비지 값)
			chatRoomPeopleVO.setIsAllowed(false);	// 결과 값 거짓으로 변경.
			
		} 
		return chatRoomPeopleVO;
	}	

	/*	기능 가지
	 *	1. 정상 적인 버튼 통해서 나간 경우 : 현재 방에 해당하는 행 찾아서 현재 접속 인원 수 1 감소.
	 *		브라우저 종료 또는 탭 x 버튼 눌러서 나간 경우 : 'afterConnectionClosed()' 에서 해당 레포지토리 메소드를 방 번호 사용해서 호출.
	 */
	
	@Override
	public void exitChatPage(int roomNumber) {
		
		System.out.println("exitChatPage() 호출");
		
		/* 현재 주어진 방 번호를 사용해서 현재 나간다고 했던 페이지 내 현재 이용자수 1 삭제 : 페이지 새로 고침이나 단순 나기기 요청이 아닌 나가기 요청을 버튼을 눌르면 바로 페이지 인원수가 페이지 대기방 목록에서 본인에게 보여야 하기 때문에
		 * 여기서 db 내 -1 을 적용함. */
		
		/* 지정된 방 내 현재 인원수가 0일 경우 해당 방에 해당하는 튜플을 삭제하기 위해 해당 방 정보 조회 쿼리 */
		String selectSql = "select * from waitingroomtbl where ROMNUM = ?";
		WatingRoomVO watingRoomVO = jdbcTemplate.queryForObject(selectSql, new Object[] {roomNumber}, new watingRoomRowMapper());
		
		/* 동기화 : 나가기 버튼 누를 시 순서 상 페이지 랜더링 과중 중의 현재 레포지토리 호출 후에 세션 close 작업(나기기 이므로 세션 재 생성은 하지 않음)이 발생하는데
		 * 이때 나가기 버튼을 실행한다면 "페이지 랜더링 받는 대기방 목록에서 db 테이블 통해 해당 방 목록을 보여주므로" 페이지 랜더링 작업 내에서 db 쿼리 작업이 일어 나야 됨.
		 * 그러므로 나가기 버튼을 누른 것 한정으로는 레포지토리에서 db 테이블 작업을 우선 한 다음에 특수한 파라메터 값(exitChatPage) 존재 여부 따라 단순 페이지 재 로딩인지
		 * 아니면 진짜 나가기 버튼이면 websocektcloased 에서 db 쿼리 작업 -1을 하지 않음
		 * 또한 0명이 되면 방 목록을 삭제하는데 이땨 단순 페이지 재 로딩이면 어차피 세션 맺을 때 동일한 방 번호로 인원 수 추가 해야 되는데 이때 나갈 때 무조건 0 명이라고 방 지워버리면
		 * 재 세션 맺을 때 에러 뜨기 때문에..이 로직은 websocket 핸들러에서 처리한다.
		 * */
		pathCurrentPeople(roomNumber, watingRoomVO.getCurrentPeople() - 1 , false);
		System.out.println("조회된 방 번호 : " + roomNumber + ", 방 인원수 : " + watingRoomVO.getCurrentPeople());
		
	}

	/* 방 번호 랜덤으로 뽑기. */
	 public static int generateUniqueRandomNumber(List<Integer> usedNumbers, int min, int max) {
		 Random random = new Random();
	     int newNumber;

	     // 사용되지 않은 숫자가 나올 때까지 랜덤 숫자를 생성합니다.
	     do {
	          newNumber = random.nextInt(max - min + 1) + min;
	     } while (usedNumbers.contains(newNumber));
	        return newNumber;
	 	 }
	 
	
	/* WebSocketHandler 에서 직접적으로 호출하여 db의 테이블 'RooMLogTBL' 내 채팅 내용 저장. */
	@Override
	public void chatLog(RoomLogVO roomLogVO) {
		System.out.println("ChatRepo.chatLog() 실행");
		
		String insertSql = "insert into roomlogtbl values(?,?,?,?)";	
		jdbcTemplate.update(insertSql, new Object[] {roomLogVO.getRomNum(), new java.sql.Date(roomLogVO.getConverSationTime().getTime()), String.valueOf(roomLogVO.getChatNickName()), String.valueOf(roomLogVO.getChatContent())});	
	}
	 
	 class watingRoomRowMapper implements RowMapper<WatingRoomVO>{

			@Override
			public WatingRoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				WatingRoomVO watingRoomVO = new WatingRoomVO();
				
				watingRoomVO.setRoomNumber(rs.getInt("ROMNUM"));
				watingRoomVO.setRoomTitle(rs.getString("ROOMNAME"));
				watingRoomVO.setCurrentPeople(rs.getInt("CURRENTPEOPLE"));
				watingRoomVO.setMaxPeople(rs.getInt("MAXPEOPLE"));
				
				return watingRoomVO;
			}
		}
}