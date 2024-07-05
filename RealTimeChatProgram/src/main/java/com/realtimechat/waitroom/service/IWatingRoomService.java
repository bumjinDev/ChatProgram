package com.realtimechat.waitroom.service;

import java.util.List;


import com.realtimechat.waitroom.model.WatingRoomVO;

public interface IWatingRoomService {

	List<WatingRoomVO> loadWatingRoom();
}
