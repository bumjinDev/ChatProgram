package com.realtimechat.chatroom.model;

public class ChatRoomPeopleVO {
	
	    private boolean isAllowed;
	    private int roomPeople;

	    // isAllowed에 대한 getter
	    public boolean getIsAllowed() {
	        return isAllowed;
	    }

	    // isAllowed에 대한 setter
	    public void setIsAllowed(boolean isAllowed) {
	        this.isAllowed = isAllowed;
	    }

	    // roomPeople에 대한 getter
	    public int getRoomPeople() {
	        return roomPeople;
	    }

	    // roomPeople에 대한 setter
	    public void setRoomPeople(int roomPeople) {
	        this.roomPeople = roomPeople;
	    }
}
