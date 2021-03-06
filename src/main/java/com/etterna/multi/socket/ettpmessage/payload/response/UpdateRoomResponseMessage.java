package com.etterna.multi.socket.ettpmessage.payload.response;


import com.etterna.multi.data.state.Lobby;

public class UpdateRoomResponseMessage {
	
	private RoomDTO room;
	
	public UpdateRoomResponseMessage() {}
	public UpdateRoomResponseMessage(Lobby lobby) {
		room = new RoomDTO(lobby);
	}
	public RoomDTO getRoom() {
		return room;
	}
	public void setRoom(RoomDTO room) {
		this.room = room;
	}

}
