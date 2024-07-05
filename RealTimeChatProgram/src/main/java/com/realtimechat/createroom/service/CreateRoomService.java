package com.realtimechat.createroom.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;

@Service
public class CreateRoomService implements ICreateRoomService{

	@Autowired
	public  ChatRepo chatRepo;
	
	/* createChatRoom : 방 생성 요청인 방 제목 및 최대 인원수를 기준으로 DBMS 쿼리. */
	@Override
	public int createChatRoom(String roomName, int roomMax) {
		return chatRepo.createRoom(roomName, roomMax);	/* 생성한 채팅방 번호 반환. */
	}
}
