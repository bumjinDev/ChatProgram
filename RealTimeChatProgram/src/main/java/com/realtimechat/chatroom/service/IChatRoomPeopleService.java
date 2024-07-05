package com.realtimechat.chatroom.service;

import com.realtimechat.chatroom.model.ChatRoomPeopleVO;

/* 컨트롤러 메소드 'loadChatPage'의 채팅 페이지 불러올 때에 해당 채팅방 번호 사용하여 채팅 방 내 실시간 인원 수 조회 */
public interface IChatRoomPeopleService {
	
	public ChatRoomPeopleVO readChatPeople(int roomNumber);
}
