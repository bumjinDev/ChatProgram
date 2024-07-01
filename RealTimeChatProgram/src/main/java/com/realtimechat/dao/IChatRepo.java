package com.realtimechat.dao;

import com.realtimechat.chatroom.model.ChatValueVO;
import com.realtimechat.main.model.MainPageVO;
import com.realtimechat.waitroom.model.WatingRoomVO;

public interface IChatRepo {
	
	public MainPageVO getMain();
	public  WatingRoomVO getWatingRoom();
	public  void inputChatValue(String chatValue, int roomNumber);
	public ChatValueVO outputChatValue(); 
	public void createRoom(String roomName, int roomMax);
}
