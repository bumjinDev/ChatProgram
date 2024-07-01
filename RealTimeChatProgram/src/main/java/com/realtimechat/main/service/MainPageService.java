package com.realtimechat.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimechat.dao.ChatRepo;
import com.realtimechat.main.model.MainPageVO;

@Service
public class MainPageService implements IMainPageSerivce{

	@Autowired
	ChatRepo chatRepo;
	
	@Override
	public MainPageVO loadMainInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
