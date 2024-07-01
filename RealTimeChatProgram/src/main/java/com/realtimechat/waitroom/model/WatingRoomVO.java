package com.realtimechat.waitroom.model;

public class WatingRoomVO {
	
	private int roomNumber;
	private String roomTitle;
	private int roomUsers; 
	
	public void setroomNumber(int roomNumber) {
		
		this.roomNumber = roomNumber;
	};
	public void setroomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	};
	public void setroomUsers(int roomUsers) {
		this.roomUsers = roomUsers;
	};
	public int getroomNumber() {
		return this.roomNumber;
	};
	public String getroomTitle() {
		return this.roomTitle;
	};
	public int getroomUser() {
		return roomUsers;
	};
}
