package com.etterna.multi.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.amazonaws.util.json.Jackson;
import com.etterna.multi.services.SessionService;
import com.etterna.multi.socket.ettpmessage.EttpMessage;
import com.etterna.multi.socket.ettpmessage.EttpMessageHandler;
import com.etterna.multi.socket.ettpmessage.EttpMessageType;
import com.etterna.multi.socket.ettpmessage.handler.HelloMessageHandler;

@Component
@Scope("prototype")
public class SocketTextHandler extends TextWebSocketHandler {
	
	private static final Logger m_logger = LoggerFactory.getLogger(SocketTextHandler.class);
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private SessionService sessions;
	
	@Autowired
	private HelloMessageHandler hello;
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		
		EttpMessage ettpMessage = Jackson.fromJsonString(message.getPayload(), EttpMessage.class);
		if (ettpMessage == null) {
			m_logger.warn("Failed to parse incoming message: {}", message.getPayload());
			return;
		}
		
		if (message.getPayload() != null && message.getPayload().contains("pass")) {
			m_logger.info("Got message type: {}", ettpMessage.getType());
		} else {
			m_logger.info("Got message: {}", message.getPayload());
		}
		
		try {
			Class<? extends EttpMessageHandler> handlerClass = EttpMessageType.valueOf(ettpMessage.getType().toUpperCase()).getLinkedClass();
			try {
				m_logger.debug("Handling incoming message type: {}", ettpMessage.getType());
				ctx.getBean(handlerClass).handle(session, ettpMessage);
			} catch (Exception e) {
				m_logger.error(e.getMessage(), e);
				return;
			}
		} catch (Exception e) {
			m_logger.warn("Unknown incoming message type: {}", ettpMessage.getType());
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		m_logger.info("Session closed - status {} - {}", status.getCode(), status.getReason());
		sessions.killSession(session);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		m_logger.info("New session received - {}", session.getId());
		sessions.registerGeneralSession(session);
		hello.hello(session);
	}

}
