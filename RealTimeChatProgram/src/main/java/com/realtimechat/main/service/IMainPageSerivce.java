package com.realtimechat.main.service;

import com.realtimechat.main.model.MainPageVO;

public interface IMainPageSerivce {
	
	public MainPageVO loadMainInfo(); 	// 사용자의 메인 페이지 'index.jsp' 요청 시 포함되는 전체 채팅 방 개수 및 전체 채팅 방 내 전체 사용자 반환
}
