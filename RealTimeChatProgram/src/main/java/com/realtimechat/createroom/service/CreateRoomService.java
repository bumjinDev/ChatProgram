package com.realtimechat.createroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;

@Service
public class CreateRoomService implements ICreateRoomService{

	@Autowired
	public  ChatRepo chatRepo;
	
	@Override
	public void createChatRoom(String roomName, int roomMax) {
		
		
	}
}
