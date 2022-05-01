package com.etterna.multi.socket.message;

import com.etterna.multi.socket.message.handler.incoming.*;

/**
 * Messages sent by the client to the server
 */
public enum IncomingMessageType {
	
	LOGIN(LoginMessageHandler.class),
	PING(PingMessageHandler.class),
	CHAT(ChatMessageHandler.class),
	SCORE(ScoreMessageHandler.class),
	GAMEPLAYUPDATE(GameplayUpdateMessageHandler.class),
	CREATEROOM(CreateRoomMessageHandler.class),
	ENTERROOM(EnterRoomMessageHandler.class),
	SELECTCHART(SelectChartMessageHandler.class),
	STARTCHART(StartChartMessageHandler.class),
	LEAVEROOM(LeaveRoomMessageHandler.class),
	GAMEOVER(GameoverMessageHandler.class),
	HASCHART(HasChartMessageHandler.class),
	MISSINGCHART(MissingChartMessageHandler.class),
	STARTINGCHART(StartingChartMessageHandler.class),
	NOTSTARTINGCHART(NotStartingChartMessageHandler.class),
	OPENOPTIONS(OpenOptionsMessageHandler.class),
	CLOSEOPTIONS(CloseOptionsMessageHandler.class),
	OPENEVAL(OpenEvalMessageHandler.class),
	CLOSEEVAL(CloseEvalMessageHandler.class),
	LOGOUT(LogoutMessageHandler.class),
	HELLO(HelloMessageHandler.class);
	
	private IncomingMessageType() {
		linkedClass = null;
	}
	
	private IncomingMessageType(Class<? extends IncomingMessageHandler> cla) {
		linkedClass = cla;
	}
	
	private Class<? extends IncomingMessageHandler> linkedClass;
	
	public Class<? extends IncomingMessageHandler> getLinkedClass() {
		return linkedClass;
	}

}
