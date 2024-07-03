package com.realtimechat.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;
import com.realtimechat.main.model.MainPageVO;

/* 사용자의 메인 페이지 'index.jsp' 요청 시 포함되는 전체 채팅 방 개수 및 전체 채팅 방 내 전체 사용자 반환 목적 클래스 */
@Service
public class MainPageService implements IMainPageSerivce{

	@Autowired
	ChatRepo chatRepo;
	
	@Override
	public MainPageVO loadMainInfo() {
		
		return chatRepo.getMain();
	}

}
