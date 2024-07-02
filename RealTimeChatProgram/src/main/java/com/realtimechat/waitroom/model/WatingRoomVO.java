package com.realtimechat.waitroom.model;

public class WatingRoomVO {
	
	private int roomNumber;
	private String roomTitle;
	private int roomUsers; 
	private int maxPeople;
	
	public void setroomNumber(int roomNumber) {
		
		this.roomNumber = roomNumber;
	};
	public void setroomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	};
	public void setroomUsers(int roomUsers) {
		this.roomUsers = roomUsers;
	};
	public void setmaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	};
	public int getroomNumber() {
		return this.roomNumber;
	};
	public String getroomTitle() {
		return this.roomTitle;
	};
	public int getroomUser() {
		return this.roomUsers;
	};
	public int getmaxPeople() {
		return this.maxPeople;
	};
}
