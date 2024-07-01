package com.realtimechat.chatroom.model;

public class ChatValueVO {
	private String userName;
	private String userChat;
	
	public void setuserName(String userName) {
		this.userName = userName;
	}
	public void setuserChat(String userChat) {
		this.userChat = userChat;
	}
	public String getuserName() {
		return this.userName;
	}
	public String getuserChat() {
		return this.userChat;
	}
}
