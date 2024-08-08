package com.realtimechat.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.realtimechat.chatroom.model.ChatRoomPeopleVO;
import com.realtimechat.createroom.dao.RoomLogVO;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.waitroom.model.WatingRoomVO;

public interface IChatRepo {
	
	public MainPageVO getMain();	/* 페이지 'index.jsp' 랜더링 */
	public List<WatingRoomVO> getWatingRoom();	/* 대기방 페이지 'waitpage.jsp' 내 방 목록 가져오기 */
	public ChatRoomPeopleVO getChatRoom(int roomNumber, HttpServletRequest httpRequest); 	/* 채팅 페이지 'chatpage.jsp' 내 포함될 닉네임, 참여 인원수, 방 번호가 필요하고 그 중 참여 인원수를 해당 방 번호로 조회 수 반환. */
	public void pathCurrentPeople(int roomNumber, int people, boolean bool); 	
	public int createRoom(String roomName, int roomMax);	/* 채팅 방 생성 요청 처리 */
	public void exitChatPage(int roomNumber);
	public void chatLog(RoomLogVO roomLogVO);
}
