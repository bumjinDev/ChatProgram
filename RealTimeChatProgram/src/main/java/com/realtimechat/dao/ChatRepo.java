package com.realtimechat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.realtimechat.chatroom.model.ChatCommunicationServiceVO;
import com.realtimechat.chatroom.model.ChatRoomPeopleVO;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.waitroom.model.WatingRoomVO;

@Repository
public class ChatRepo implements IChatRepo{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
//	/* 웹 어플리케이션 재 실행 시 현재 채팅 방 목록은 초기화로 진행.(log 테이블인 'RooMLogtbl' 은 상관 없음.) */
	@PostConstruct
	void init() {
		
		String sql = "delete from waitingroomtbl";
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

	@Override
	public void inputChatValue(String chatValue, int roomNumber) {
		// TODO Auto-generated method stub
		
	}

	/* createRoom : 채팅 방 생성 요청 메소드 (메소드 'getChatRoom' 에서 조회수 증가하므로 현재 메소드에서는 작업하지 않음.)*/
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
	
	
	/* 컨트롤러 메소드 'loadChatPage' 에서 요청 따라 해당 채팅 방에 접속 요청 시 "방의 현재 채팅 참여 인원수"를 증가 및 조회.
	 * 해당 로직은 방 생성 및 생성 된 방에 대해서 공통으로 적용되어 채팅 방 접속 가능 여부 판단한다. */
	@Override
	public ChatRoomPeopleVO getChatRoom(int roomNumber) {
		
		ChatRoomPeopleVO chatRoomPeopleVO = new ChatRoomPeopleVO();
		
		/* 요청된 테이블 번호를 기준으로 해당 테이블의 현재 참여 인원 수 및 최대 인원수를 파악. */
		String selectRoom = "select CURRENTPEOPLE, MAXPEOPLE from waitingroomtbl where ROMNUM = ?";
		List<Map<String, Object>> users = jdbcTemplate.queryForList(selectRoom, new Object[] {roomNumber});
		
		/* 현재 요청한 인원수를 받아도 괜찮다면 진행 */
		if(Integer.parseInt(users.get(0).get("CURRENTPEOPLE").toString())+1 <= Integer.parseInt(users.get(0).get("MAXPEOPLE").toString())){
			/* 요청 받은 채팅 페이지인 "chatPage" 내 표현될 'roomNumber' 번호에 해당하는 방 내 조회수 증가 */
			String peopleSql = "update waitingroomtbl set CURRENTPEOPLE = CURRENTPEOPLE + 1 where ROMNUM = ?";
			jdbcTemplate.update(peopleSql, new Object[] {roomNumber});
		
			/* 요청 받은 방 번호를 기준으로 해당 방 내 채팅 참여 인원수 및 성공 boolean 저장 후 반환 */
			chatRoomPeopleVO.setRoomPeople((Integer.parseInt(users.get(0).get("CURRENTPEOPLE").toString())+1));		// 참여 인원수 1 증가
			chatRoomPeopleVO.setIsAllowed(true);												// 결과 값 참으로 변경
		}
		/* 현재 요청한 인원수를 받으면 안된다면 false 입력. 진행 */
		else {
			chatRoomPeopleVO.setRoomPeople(0);		// 참여 인원수 변동 없음. (VO 객체 채우기 위한 가비지 값)
			chatRoomPeopleVO.setIsAllowed(false);	// 결과 값 거짓으로 변경.
		}
		return chatRoomPeopleVO;
	}

	@Override
	public ChatCommunicationServiceVO outputChatValue() {
		// TODO Auto-generated method stub
		return null;
	};
	

	/* 채팅방 페이지 'chatpage.jsp'의 채팅방 나가기 기능 위한 방 번호를 기준으로 해당 테이블 내 현재 참여 인원 수 컬럼인 "CURRENTPEOPLE" 개수 -1 */
	@Override
	public void exitChatPage(int roomNumber) {
		
		/* 방 인원 수 감소 */
		String decreaseSql = "update waitingroomtbl set CURRENTPEOPLE = CURRENTPEOPLE - 1 where ROMNUM = ?";
		jdbcTemplate.update(decreaseSql, new Object[] {roomNumber});
		
		/* 지정된 방 내 현재 인원수가 0일 경우 해당 방에 해당하는 컬럼은 삭제 */
		String selectSql = "select * from waitingroomtbl where ROMNUM = ?";
		WatingRoomVO watingRoomVO = jdbcTemplate.queryForObject(selectSql, new Object[] {roomNumber}, new watingRoomRowMapper());
		
		/* 해당 방 번호에 해당하는 튜플을 조회했을 때 현재 방 내 접속 인원 수가 없다면 방 삭제. */
		if(watingRoomVO.getCurrentPeople() <= 0) {	
			String deleteQuery = "delete from waitingroomtbl where ROMNUM = ?";
			jdbcTemplate.update(deleteQuery, new Object[] {roomNumber});
		}
		
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
