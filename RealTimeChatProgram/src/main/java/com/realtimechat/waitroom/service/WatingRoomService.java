package com.realtimechat.waitroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;
import com.realtimechat.waitroom.model.WatingRoomVO;

@Service
public class WatingRoomService implements IWatingRoomService{
	
	ChatRepo chatRepo;

	@Override
	public List<WatingRoomVO> loadWatingRoom() {
		// TODO Auto-generated method stub
		return null;
	}
}
