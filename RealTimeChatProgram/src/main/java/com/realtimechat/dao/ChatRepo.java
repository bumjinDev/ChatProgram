package com.realtimechat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.realtimechat.chatroom.model.ChatValueVO;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.waitroom.model.WatingRoomVO;

@Repository
public class ChatRepo implements IChatRepo{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
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

	@Override
	public ChatValueVO outputChatValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRoom(String roomName, int roomMax) {
		// TODO Auto-generated method stub
		
	}

}
