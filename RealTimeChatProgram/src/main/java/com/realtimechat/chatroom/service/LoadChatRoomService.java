package com.realtimechat.chatroom.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.chatroom.model.ChatRoomPeopleVO;
import com.realtimechat.dao.ChatRepo;

/* 컨트롤러 메소드 'loadChatPage'의 채팅 페이지 불러올 때에 해당 채팅방 번호 사용하여 채팅 방 내 실시간 인원 수 조회 */
@Service
public class LoadChatRoomService implements ILoadChatRoomService {

	@Autowired
	public ChatRepo chatRepo;
	
	@Override
	public ChatRoomPeopleVO readChatPeople(int roomNumber, HttpServletRequest httpRequest) {
		
		return chatRepo.getChatRoom(roomNumber, httpRequest);
	}
}
