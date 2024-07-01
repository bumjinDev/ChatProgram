package com.realtimechat.chatroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.chatroom.model.ChatInfoVO;
import com.realtimechat.chatroom.model.ChatValueVO;
import com.realtimechat.dao.ChatRepo;


@Service
public class ChatRoomService implements IChatRoomService {

	@Autowired
	public  ChatRepo chatRepo;
	
	@Override
	public ChatInfoVO inChatRoom(int roomNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inputChatValue(ChatValueVO chatValue) {
		// TODO Auto-generated method stub
		
	}

}
