package com.realtimechat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.realtimechat.chatroom.model.ChatValueVO;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.waitroom.model.WatingRoomVO;

@Repository
public class ChatRepo implements IChatRepo{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/* roomNumbers : 유저들이 방 생성 시 마다 방 번호 리스트를 가지고 있어 중복된 방 번호 생성 방지 및 모든 요청 시 마다 테이블 조회하는 리소스 및 시간 절감 목적. */
	static List<Integer> roomNumbers = new ArrayList<Integer>();
	
	@Override
	public MainPageVO getMain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WatingRoomVO getWatingRoom() {
		// TODO Auto-generated method stub
		return null;
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
		
		/* 실제 방 생성하는 쿼리 실행 */
		String sql = "Insert into WATINGROOMTBL values (?,?,?,?)";
		jdbcTemplate.update(sql, new Object [] { roomNumber, roomName, 1, roomMax});
		
		roomNumbers.add(roomNumber);	// 생성한 채팅 방 번호를 static 저장.
		
		return roomNumber;		/* 생성한 방 번호를 반환하고 이를 컨트롤러에서 받아서 생성한 방을 토대로 채팅 방 페이지인 'chatpage.jsp'를 반환하여 즉시 채팅 시작. */
	}
	
	
	/* 컨트롤러 메소드 'loadChatPage' 에서 요청 받은 "방의 현재 채팅 참여 인원수"를 증가 및 조회. */
	@Override
	public int getChatRoom(int roomNumber) {
		
		/* 요청 받은 채팅 페이지인 "chatPage" 내 표현될 'roomNumber' 번호에 해당하는 방 내 조회수 증가 */
		String peopleSql = "update watingroomtbl set CURRENTPEOPLE = CURRENTPEOPLE + 1 where ROMNUM = ?";
		jdbcTemplate.update(peopleSql, new Object[] {roomNumber});
	
		/* 요청 받은 방 번호를 기준으로 해당 방 내 채팅 참여 인수 조회 및 반환 */
		String selectSql = "select CURRENTPEOPLE from watingroomtbl where ROMNUM = ?";
		return jdbcTemplate.queryForObject(selectSql, new Object[] {roomNumber}, Integer.class);
	}

	@Override
	public ChatValueVO outputChatValue() {
		// TODO Auto-generated method stub
		return null;
	};
	
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
				
				watingRoomVO.setroomNumber(rs.getInt("ROMNUM"));
				watingRoomVO.setroomTitle(rs.getString("TITLE"));
				watingRoomVO.setmaxPeople(rs.getInt("CURRENTPEOPLE"));
				watingRoomVO.setmaxPeople(rs.getInt("MAXPEOPLE"));
				
				return watingRoomVO;
			}
		}
}
