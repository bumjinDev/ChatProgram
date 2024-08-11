package com.realtimechat.createroom.model;

import java.sql.Date;

public class RoomLogVO {
	
	private int romNum;
	private Date converSationTime;
	private String chatNickName;
	private String chatContent;
	
    public int getRomNum() {
        return romNum;
    }
    public void setRomNum(int romNum) {
        this.romNum = romNum;
    }

    public Date getConverSationTime() {
        return converSationTime;
    }

    public void setConverSationTime(Date converSationTime) {
        this.converSationTime = converSationTime;
    }

    public String getChatNickName() {
        return chatNickName;
    }

    public void setChatNickName(String chatNickName) {
        this.chatNickName = chatNickName;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}