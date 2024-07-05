package com.realtimechat.waitroom.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;
import com.realtimechat.waitroom.model.WatingRoomVO;

/* 메인 페이지 'index.jsp' 에서 닉네임 입력 후 채팅 서비스 시작함으로써 채팅 대기방 페이지 내 포함될 채팅방 목록을 제공하기 위한 서비스 빈 */
@Service
public class WatingRoomService implements IWatingRoomService{
	
	@Autowired
	ChatRepo chatRepo;

	/* 전체 채팅 방 목록을 조회 후 반환한다. */
	@Override
	public List<WatingRoomVO> loadWatingRoom() {
		System.out.println("loadWatingRoom() 호출");
		return chatRepo.getWatingRoom();
	}
}
