package com.etterna.multi.socket.message.handler.incoming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.etterna.multi.services.SessionService;
import com.etterna.multi.socket.message.EttpMessage;
import com.etterna.multi.socket.message.IncomingMessageHandler;
import com.etterna.multi.socket.payload.incoming.HelloMessage;

@Component
public class HelloMessageHandler extends IncomingMessageHandler {
	
	private static final String SERVER_NAME = "AltEOMulti";
	private static final int SERVER_VERSION = 5;
	
	@Autowired
	private SessionService sessions;
	
	@Override
	public void handle(WebSocketSession session, EttpMessage message) {
		HelloMessage msg = readPayload(message, HelloMessage.class);
		
		sessions.clientHello(session, msg);
	}
	
	/**
	 * Sent on connect by a client
	 */
	public void hello(WebSocketSession session) {
		respond(session, "hello", new HelloResponseMessage());
	}
	
	public class HelloResponseMessage {
		private int version = SERVER_VERSION;
		private String name = SERVER_NAME;
		public int getVersion() {
			return version;
		}
		public String getName() {
			return name;
		}
	}

}
