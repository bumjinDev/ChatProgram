package com.realtimechat.createroom.service;

public interface ICreateRoomService {
	
	public int createChatRoom(String roomName, int roomMax);	/* 방 생성 할 시 사용자 입력 받은 방 제목과 방 최대 인원수를 매개변수로 받음 */
}
