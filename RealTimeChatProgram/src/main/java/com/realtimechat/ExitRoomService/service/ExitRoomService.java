package com.realtimechat.ExitRoomService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;

@Service
/* 채팅방 페이지 'chatpage.jsp'의 채팅방 나가기 기능 구현 */
public class ExitRoomService implements IExitRoomService{

	@Autowired
	ChatRepo chatRepo; 
	
	@Override
	public void exitChatRoom(int roomMax) {
		System.out.println("ExitRoomService.exitChatRoom() 실행! ");
		
		chatRepo.exitChatPage(roomMax);
	}
}
