package com.realtimechat.chatroom.service;

import com.realtimechat.chatroom.model.ChatInfoVO;
import com.realtimechat.chatroom.model.ChatValueVO;

public interface IChatRoomService {
	public ChatInfoVO inChatRoom(int roomNumber);
	public void inputChatValue(ChatValueVO chatValue);

}
