package com.realtimechat.waitroom.model;

public class WatingRoomVO {
	
	private int roomNumber;
	private String roomTitle;
	private int currentPeople; 
	private int maxPeople;
	
	public void setRoomNumber(int roomNumber) {
		
		this.roomNumber = roomNumber;
	};
	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	};
	public void setCurrentPeople(int roomUsers) {
		this.currentPeople = roomUsers;
	};
	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	};
	public int getRoomNumber() {
		return this.roomNumber;
	};
	public String getRoomTitle() {
		return this.roomTitle;
	};
	public int getCurrentPeople() {
		return this.currentPeople;
	};
	public int getMaxPeople() {
		return this.maxPeople;
	};
}
