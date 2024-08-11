package com.realtimechat.ExitRoom.service;

import javax.servlet.http.HttpSession;


public interface IExitRoomService {

	void exitChatRoom(int roomMax, HttpSession httpSession);
}
