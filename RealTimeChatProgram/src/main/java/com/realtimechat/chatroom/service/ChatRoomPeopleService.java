package com.realtimechat.chatroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;

/* 컨트롤러 메소드 'loadChatPage'의 채팅 페이지 불러올 때에 해당 채팅방 번호 사용하여 채팅 방 내 실시간 인원 수 조회 */
@Service
public class ChatRoomPeopleService implements IChatRoomPeopleService {

	@Autowired
	public  ChatRepo chatRepo;
	
	@Override
	public int readChatPeople(int roomNumber) {
		
		return chatRepo.getChatRoom(roomNumber);
	}
}
