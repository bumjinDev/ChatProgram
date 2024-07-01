package com.realtimechat.main.model;

public class MainPageVO {
	
	private int totalRoom;
	private int totalUser;
	
	public void setTotalRoom(int totalRoom) {
		this.totalRoom = totalRoom;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	
	public int getTotalRoom() {	
		return this.totalRoom;
	}
	public int getUser() {
		return this.totalUser;
	}
}
