package com.realtimechat.chatroom.model;

public class ChatInfoVO {
	private int roomNumber;
	private int roomUsers;
	
	public void setroomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	};
	public void setroomUsers(int roomUsers) {
		this.roomUsers = roomUsers;
	};
	public int getroomNumber() {
		return this.roomNumber;
	}
	public int getroomUser() {
		return this.roomUsers;
	}
}
